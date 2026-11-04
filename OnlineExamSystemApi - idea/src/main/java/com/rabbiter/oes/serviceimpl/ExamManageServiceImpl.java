package com.rabbiter.oes.serviceimpl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rabbiter.oes.entity.ExamManage;
import com.rabbiter.oes.mapper.ExamManageMapper;
import com.rabbiter.oes.service.ExamManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ExamManageServiceImpl implements ExamManageService {
    @Autowired
    private ExamManageMapper examManageMapper;
    @Autowired
    private PaperServiceImpl paperService;

    /**
     * 批量回填作业总分。
     * 原实现为每条作业调用一次 getMaxScore（每次 4 条查询），列表页共 4N 次查询；
     * 现改为一次聚合查询，复杂度由 O(4N) 降为 O(1) 次 SQL。
     */
    private void setMaxScore(List<ExamManage> examManageList) {
        if (examManageList == null || examManageList.isEmpty()) {
            return;
        }
        List<Integer> paperIds = new ArrayList<>();
        for (ExamManage examManage : examManageList) {
            Integer paperId = examManage.getPaperId();
            if (paperId != null && !paperIds.contains(paperId)) {
                paperIds.add(paperId);
            }
        }
        if (paperIds.isEmpty()) {
            return;
        }
        Map<Integer, Integer> totalScores = paperService.findTotalScores(paperIds);
        for (ExamManage examManage : examManageList) {
            Integer totalScore = totalScores.get(examManage.getPaperId());
            examManage.setTotalScore(totalScore == null ? 0 : totalScore);
        }
    }

    @Override
    public List<ExamManage> findAll() {
        Page<ExamManage> examManage = new Page<>(0,9999);
        List<ExamManage> examManageList = examManageMapper.findAll(examManage).getRecords();
        //给当前作业计算总分,只是记录到了传递给前端，数据库没有修改
        setMaxScore(examManageList);

        return examManageList;
    }

    @Override
    public IPage<ExamManage> findAll(Page<ExamManage> page) {
        IPage<ExamManage> iPage = examManageMapper.findAll(page);
        setMaxScore(iPage.getRecords());
        return iPage;
    }

    @Override
    public ExamManage findById(Integer examCode) {
        ExamManage examManage = examManageMapper.findById(examCode);
        if (examManage == null) {
            return null;
        }
        //计算总分
        examManage.setTotalScore(paperService.getMaxScore(examManage.getPaperId()));
        return examManage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(Integer examCode) {
        // 移除题目关联
        ExamManage examManage = examManageMapper.findById(examCode);
        if(examManage == null) {
            return 0;
        }
        paperService.deleteByPaperId(examManage.getPaperId());
        return examManageMapper.delete(examCode);
    }

    /////
//    @Override
//    public int update(ExamManage exammanage) {
//        return examManageMapper.update(exammanage);
//    }



    @Override
    public int update(ExamManage exammanage) {
        return examManageMapper.update(exammanage);
    }


    @Override
    public int updateMaxScore(ExamManage exammanage) {
        return examManageMapper.updateMaxScore(exammanage);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int add(ExamManage exammanage) {
        // 总分由试卷构成决定：入库前统一计算，避免“先插入再更新”的两次写
        exammanage.setTotalScore(paperService.getMaxScore(exammanage.getPaperId()));
        return examManageMapper.add(exammanage);
    }

    @Override
    public ExamManage findOnlyPaperId() {
//        int max=999999;
//        int min=1;
//        Random random = new Random();
//        ExamManage examManage = new ExamManage();
//        examManage.setPaperId(random.nextInt(max)%(max-min+1) + min);
//        return examManage;
        return examManageMapper.findOnlyPaperId();
    }
}
