package com.rabbiter.oes.serviceimpl;

import com.rabbiter.oes.mapper.PaperMapper;
import com.rabbiter.oes.vo.Item;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * PaperServiceImpl 单元测试（不依赖数据库）：
 * 覆盖总分计算的空值边界、组卷参数校验、删题的编号校验与“未绑定作业”分支。
 *
 * 说明：Spring Boot 4 的 starter-test 默认携带 JUnit Jupiter 6，
 * 故使用 JUnit 5 扩展模型（@ExtendWith）+ Mockito；strictness 设为 LENIENT 以保持
 * 原 MockitoJUnitRunner.Silent 的宽松语义（允许存在未被使用的 stub）。
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class PaperServiceImplTest {

    @Mock
    private PaperMapper paperMapper;

    @InjectMocks
    private PaperServiceImpl paperService;

    @Test
    public void getMaxScoreWithNullPaperIdReturnsZero() {
        assertEquals(0, (int) paperService.getMaxScore(null));
        verify(paperMapper, never()).findTotalScoreByPaperId(anyInt());
    }

    @Test
    public void getMaxScoreWhenNoRecordReturnsZero() {
        when(paperMapper.findTotalScoreByPaperId(1001)).thenReturn(null);
        assertEquals(0, (int) paperService.getMaxScore(1001));
    }

    @Test
    public void getMaxScoreReturnsAggregate() {
        when(paperMapper.findTotalScoreByPaperId(1001)).thenReturn(118);
        assertEquals(118, (int) paperService.getMaxScore(1001));
    }

    @Test
    public void composePaperRejectsMissingPaperId() {
        Item item = new Item();
        item.setChangeNumber(1);
        item.setFillNumber(0);
        item.setFillNumber2(0);
        item.setJudgeNumber(0);

        try {
            paperService.composePaper(item);
            fail("应当抛出 IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("组卷参数不完整：试卷编号或各题型数量为空，组卷失败", e.getMessage());
        }
        verify(paperMapper, never()).add(any());
    }

    @Test
    public void composePaperRejectsNegativeCount() {
        Item item = new Item();
        item.setPaperId(1001);
        item.setChangeNumber(-1);
        item.setFillNumber(0);
        item.setFillNumber2(0);
        item.setJudgeNumber(0);

        try {
            paperService.composePaper(item);
            fail("应当抛出 IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            // ok
        }
        verify(paperMapper, never()).add(any());
    }

    @Test
    public void removeQuestionRejectsInvalidPaperId() {
        try {
            paperService.removeQuestion("abc", "1", "10000");
            fail("应当抛出 IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("试卷编号格式错误", e.getMessage());
        }
        verify(paperMapper, never()).delete(anyString(), anyString(), anyString());
    }

    @Test
    public void removeQuestionSkipsScoreSyncWhenPaperNotBound() {
        when(paperMapper.findByIdToExcode(1001)).thenReturn(null);

        paperService.removeQuestion("1001", "1", "10000");

        verify(paperMapper).delete("1001", "1", "10000");
        verify(paperMapper, never()).updateMaxScore(any());
    }
}
