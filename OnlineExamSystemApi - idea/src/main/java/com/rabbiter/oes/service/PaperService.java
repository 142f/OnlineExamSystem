package com.rabbiter.oes.service;

import com.rabbiter.oes.entity.ExamManage;
import com.rabbiter.oes.entity.PaperManage;
import com.rabbiter.oes.vo.Item;

import java.util.List;
import java.util.Map;

public interface PaperService {

    List<PaperManage> findAll();

    List<PaperManage> findById(Integer paperId);

    int add(PaperManage paperManage);

    // 获取试卷总分
    ExamManage findByIdToExcode(Integer paperId);

    Integer updateMaxScore(ExamManage exammanage);
    // 获取试卷总分
    Integer getMaxScore(Integer paperId);

    /**
     * 批量获取试卷满分（paperId -> 满分），用于列表页一次计算多份试卷总分。
     */
    Map<Integer, Integer> findTotalScores(List<Integer> paperIds);

    /**
     * 组卷：按题型数量从题库中随机抽题写入试卷，并同步试卷总分与成绩表中的“试卷满分”。
     * 抽题、写入、总分同步在同一事务中完成，任一环节失败整体回滚。
     *
     * @param item 组卷参数（试卷编号、科目、各题型数量）
     * @throws IllegalArgumentException 参数不完整或题库题量不足，消息可直接展示给用户
     */
    void composePaper(Item item);

    /**
     * 删除试卷中的某条试题，并同步试卷总分。
     *
     * @param paperId    试卷id
     * @param type       题目类型。1选择，2填空，3判断，4大题
     * @param questionId 题目id
     * @throws IllegalArgumentException 试卷编号格式错误
     */
    void removeQuestion(String paperId, String type, String questionId);

    /**
     * 根据试卷id删除题目关联
     *
     * @param paperId 试卷id
     */
    void deleteByPaperId(Integer paperId);
}
