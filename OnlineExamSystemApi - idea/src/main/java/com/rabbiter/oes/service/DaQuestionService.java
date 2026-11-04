package com.rabbiter.oes.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rabbiter.oes.entity.DaQuestion;

import java.util.List;

public interface DaQuestionService {
    IPage<DaQuestion> findAll(Page<DaQuestion> page, Integer studentId);

    IPage<DaQuestion> findAll2(Page<DaQuestion> page, Integer studentId);

    IPage<DaQuestion> findAllTea(Page<DaQuestion> page, Integer studentId);

    DaQuestion findOnlyQuestionId(Integer studentId, Integer questionId);

///todo
//    void update2(Integer studentId, Integer questionId, String answerMe,Integer examCode);

    void update2(Integer studentId, Integer questionId, String answerMe);

    int update(DaQuestion daQuestion);

    int update3(DaQuestion daQuestion);

    void updateScore(Integer studentId,Integer examCode);

    List<DaQuestion> findByIdAndType(Integer paperId);


    int add(DaQuestion daQuestion);

    List<Integer> findBySubject(String subject, Integer pageNo);

    int edit(DaQuestion daQuestion);
}
