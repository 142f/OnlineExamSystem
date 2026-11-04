package com.rabbiter.oes.serviceimpl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rabbiter.oes.entity.DaQuestion;
import com.rabbiter.oes.entity.Student;
import com.rabbiter.oes.mapper.DaQuestionMapper;
import com.rabbiter.oes.service.DaQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@Service
public class DaQuestionServiceImpl implements DaQuestionService {
    @Autowired
    private DaQuestionMapper daQuestionMapper;


    @Override
    public IPage<DaQuestion> findAll(Page<DaQuestion> page, Integer studentId) {
        return daQuestionMapper.findAll(page, studentId);
    }
    @Override
    public IPage<DaQuestion> findAllTea(Page<DaQuestion> page, Integer studentId) {
        return daQuestionMapper.findAllTea(page, studentId);
    }

    @Override
    public IPage<DaQuestion> findAll2(Page<DaQuestion> page, Integer studentId) {
        return daQuestionMapper.findAll2(page, studentId);
    }

    @Override
    public DaQuestion findOnlyQuestionId(Integer studentIdd, Integer questionIdd) {

        return daQuestionMapper.findOnlyQuestionId(studentIdd, questionIdd);
    }

//    @Override
//    public void update2(Integer studentId, Integer questionId, String answerMe,Integer examCode) {
//        daQuestionMapper.update2(studentId,questionId,answerMe,examCode);
//    }

    @Override
    public void update2(Integer studentId, Integer questionId, String answerMe) {
        daQuestionMapper.update2(studentId,questionId,answerMe);
    }

    @Override
    public int update(DaQuestion daQuestion) {
        return daQuestionMapper.update(daQuestion);
    }


    @Override
    public int update3(DaQuestion daQuestion) {
        return daQuestionMapper.update3(daQuestion);
    }


    @Override
    public void updateScore(Integer studentId,Integer examCode) {
         daQuestionMapper.updateScore(studentId,examCode);
    }


    @Override
    public List<DaQuestion> findByIdAndType(Integer paperId) {
        return daQuestionMapper.findByIdAndType(paperId);
    }


    @Override
    public int add(DaQuestion daQuestion) {
        return daQuestionMapper.add(daQuestion);
    }

    @Override
    public List<Integer> findBySubject(String subject, Integer pageNo) {
        return daQuestionMapper.findBySubject(subject, pageNo);
    }

    @Override
    public int edit(DaQuestion daQuestion) {
        return daQuestionMapper.edit(daQuestion);
    }

}