package com.rabbiter.oes.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rabbiter.oes.entity.FillQuestion;

import java.util.List;

public interface FillQuestionService {

    List<FillQuestion> findByIdAndType(Integer paperId);

    IPage<FillQuestion> findAll(Page<FillQuestion> page);

    FillQuestion findOnlyQuestionId();

    int add(FillQuestion fillQuestion);

    int add2(FillQuestion fillQuestion);
    List<Integer> findBySubject(String subject,Integer pageNo);
//大题
    List<Integer> findBySubject2(String subject,Integer pageNo);
    int edit(FillQuestion fillQuestion);
}
