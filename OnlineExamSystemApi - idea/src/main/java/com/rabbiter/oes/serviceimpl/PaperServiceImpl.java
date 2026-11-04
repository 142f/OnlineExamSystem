package com.rabbiter.oes.serviceimpl;

import com.rabbiter.oes.entity.ExamManage;
import com.rabbiter.oes.entity.PaperManage;
import com.rabbiter.oes.mapper.PaperMapper;
import com.rabbiter.oes.service.FillQuestionService;
import com.rabbiter.oes.service.JudgeQuestionService;
import com.rabbiter.oes.service.MultiQuestionService;
import com.rabbiter.oes.service.PaperService;
import com.rabbiter.oes.service.ScoreService;
import com.rabbiter.oes.vo.Item;
import com.rabbiter.oes.vo.PaperTotalScoreVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PaperServiceImpl implements PaperService {

    @Autowired
    private PaperMapper paperMapper;

    @Autowired
    private JudgeQuestionService judgeQuestionService;

    @Autowired
    private MultiQuestionService multiQuestionService;

    @Autowired
    private FillQuestionService fillQuestionService;

    @Autowired
    private ScoreService scoreService;

    @Override
    public List<PaperManage> findAll() {
        return paperMapper.findAll();
    }

    @Override
    public List<PaperManage> findById(Integer paperId) {
        return paperMapper.findById(paperId);
    }

    @Override
    public int add(PaperManage paperManage) {
        return paperMapper.add(paperManage);
    }

    @Override
    public ExamManage findByIdToExcode(Integer paperId) {
        return paperMapper.findByIdToExcode(paperId);
    }

    @Override
    public Integer updateMaxScore(ExamManage exammanage) {
        return paperMapper.updateMaxScore(exammanage);
    }

    @Override
    //计算----- 总分：单条聚合 SQL，替代原来的 4 次题库查询
    public Integer getMaxScore(Integer paperId) {
        if (paperId == null) {
            return 0;
        }
        Integer total = paperMapper.findTotalScoreByPaperId(paperId);
        return total == null ? 0 : total;
    }

    @Override
    public Map<Integer, Integer> findTotalScores(List<Integer> paperIds) {
        Map<Integer, Integer> result = new HashMap<>();
        if (paperIds == null || paperIds.isEmpty()) {
            return result;
        }
        for (PaperTotalScoreVO vo : paperMapper.findTotalScores(paperIds)) {
            result.put(vo.getPaperId(), vo.getTotalScore() == null ? 0 : vo.getTotalScore());
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void composePaper(Item item) {
        Integer paperId = item.getPaperId();
        Integer changeNumber = item.getChangeNumber();
        Integer fillNumber = item.getFillNumber();
        Integer daNumber = item.getFillNumber2();
        Integer judgeNumber = item.getJudgeNumber();

        if (paperId == null || changeNumber == null || fillNumber == null
                || daNumber == null || judgeNumber == null) {
            throw new IllegalArgumentException("组卷参数不完整：试卷编号或各题型数量为空，组卷失败");
        }
        if (changeNumber < 0 || fillNumber < 0 || daNumber < 0 || judgeNumber < 0) {
            throw new IllegalArgumentException("题型数量不能为负数，组卷失败");
        }

        // 按科目随机抽题
        List<Integer> changeNumbers = multiQuestionService.findBySubject(item.getSubject(), changeNumber);
        List<Integer> fills = fillQuestionService.findBySubject(item.getSubject(), fillNumber);
        List<Integer> judges = judgeQuestionService.findBySubject(item.getSubject(), judgeNumber);
        List<Integer> das = fillQuestionService.findBySubject2(item.getSubject(), daNumber);

        if (changeNumbers == null || changeNumbers.size() != changeNumber) {
            throw new IllegalArgumentException("科目【" + item.getSubject() + "】题库【选择题】题目数量不足【" + changeNumber + "】，组卷失败");
        }
        if (fills == null || fills.size() != fillNumber) {
            throw new IllegalArgumentException("科目【" + item.getSubject() + "】题库【填空题】题目数量不足【" + fillNumber + "】，组卷失败");
        }
        if (judges == null || judges.size() != judgeNumber) {
            throw new IllegalArgumentException("科目【" + item.getSubject() + "】题库【判断题】题目数量不足【" + judgeNumber + "】，组卷失败");
        }
        if (das == null || das.size() != daNumber) {
            throw new IllegalArgumentException("科目【" + item.getSubject() + "】题库【大题】题目数量不足【" + daNumber + "】，组卷失败");
        }

        // 符合组题条件，执行组题
        addAll(paperId, 1, changeNumbers);
        addAll(paperId, 2, fills);
        addAll(paperId, 4, das);
        addAll(paperId, 3, judges);

        // 组卷后重新计算总分
        syncTotalScore(paperId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeQuestion(String paperId, String type, String questionId) {
        int id;
        try {
            id = Integer.parseInt(paperId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("试卷编号格式错误");
        }
        paperMapper.delete(paperId, type, questionId);
        syncTotalScore(id);
    }

    @Override
    public void deleteByPaperId(Integer paperId) {
        paperMapper.deleteByPaperId(paperId);
    }

    /**
     * 批量写入试卷-题目关联。
     */
    private void addAll(Integer paperId, int questionType, List<Integer> questionIds) {
        for (Integer questionId : questionIds) {
            int index = paperMapper.add(new PaperManage(paperId, questionType, questionId));
            if (index == 0) {
                throw new IllegalStateException("组卷保存失败：题型" + questionType + "，题目" + questionId);
            }
        }
    }

    /**
     * 同步试卷总分：先写回 exam_manage.totalScore，再同步成绩表中该作业的“试卷满分”。
     * 试卷尚未绑定作业时跳过（例如刚创建、还未新增作业的试卷）。
     */
    private void syncTotalScore(Integer paperId) {
        ExamManage examManage = paperMapper.findByIdToExcode(paperId);
        if (examManage == null) {
            return;
        }
        examManage.setTotalScore(getMaxScore(paperId));
        paperMapper.updateMaxScore(examManage);
        scoreService.updateMaxScore(examManage);
    }
}
