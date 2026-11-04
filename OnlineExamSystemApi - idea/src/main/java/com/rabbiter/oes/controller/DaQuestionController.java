package com.rabbiter.oes.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rabbiter.oes.entity.ApiResult;
import com.rabbiter.oes.entity.DaQuestion;
import com.rabbiter.oes.entity.ExamManage;
import com.rabbiter.oes.entity.Student;
import com.rabbiter.oes.mapper.ExamManageMapper;
import com.rabbiter.oes.service.DaQuestionService;
import com.rabbiter.oes.service.ExamManageService;
import com.rabbiter.oes.service.PaperService;
import com.rabbiter.oes.util.ApiResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

// TODO: 2024/3/25  
@RestController
public class DaQuestionController {

    @Autowired
    private DaQuestionService daQuestionService;
    @Autowired
    private ExamManageService examManageService;
    @Autowired
    private PaperService paperService;

//互评需要的作业内容
    @GetMapping("/daQuestionall/{page}/{size}/{studentId}")
    public ApiResult findAll(@PathVariable Integer page, @PathVariable Integer size,
                             @PathVariable Integer studentId) {
        Page<DaQuestion> DaPage = new Page<>(page, size);
        IPage<DaQuestion> res = daQuestionService.findAll(DaPage, studentId);

        return ApiResultHandler.buildApiResult(200, "分页查询所有学生da", res);
    }

//学生--分数详情
    // TODO: 2024/5/10  分数详情页面
    @GetMapping("/daQuestionDetail/{page}/{size}/{studentId}")
    public ApiResult findAll2(@PathVariable Integer page, @PathVariable Integer size,
                             @PathVariable Integer studentId) {
        Page<DaQuestion> DaPage = new Page<>(page, size);

        IPage<DaQuestion> res = daQuestionService.findAll2(DaPage, studentId);

        return ApiResultHandler.buildApiResult(200, "分页查询所有学生da", res);
    }


    ///要获得examCoe 加入数据库
    // TODO: 2024/3/27 处理前端大题的答题内容
    @GetMapping("/daQuestionUpdate/{studentId}/{questionId}/{answerMe}")
    public void update2(@PathVariable Integer studentId, @PathVariable Integer questionId,
                        @PathVariable String answerMe) {
//        ExamManage res = examManageService.findOnlyPaperId();
//        Integer examCode = res.getExamCode();
//        System.out.println();
//        daQuestionService.update2(studentId, questionId, answerMe,examCode);
//        Integer examCode = res.getExamCode();
//        System.out.println();

        daQuestionService.update2(studentId, questionId, answerMe);

    }
/*
*更新作业评分
* */
    // TODO: 2024/3/26 更新数据库
    @PutMapping("/daQuestion")
    public ApiResult update(@RequestBody DaQuestion daQuestion) {
        Integer studentId = daQuestion.getStudentId();
        Integer examCode = daQuestion.getExamCode();


        int res = daQuestionService.update(daQuestion);
        daQuestionService.updateScore(studentId,examCode);
        if (res != 0) {
            return ApiResultHandler.buildApiResult(200, "更新成功", res);
        }
        return ApiResultHandler.buildApiResult(400, "更新失败", res);
    }


    // TODO: 2024   教师可重新评分
    @GetMapping("/daQuestionallTea/{page}/{size}/{studentId}")
    public ApiResult findAllTea(@PathVariable Integer page, @PathVariable Integer size,
                             @PathVariable Integer studentId) {
        Page<DaQuestion> DaPage = new Page<>(page, size);
        IPage<DaQuestion> res = daQuestionService.findAllTea(DaPage, studentId);
        return ApiResultHandler.buildApiResult(200, "分页查询所有学生da", res);
    }

    // TODO: 教师端可看，重新评分
    @PutMapping("/daQuestionTea")
    public ApiResult update3(@RequestBody DaQuestion daQuestion) {
        Integer studentId = daQuestion.getStudentId();
        Integer examCode = daQuestion.getExamCode();
        int res = daQuestionService.update3(daQuestion);
        //更新成绩表的主观题分数
        daQuestionService.updateScore(studentId,examCode);
        if (res != 0) {
            return ApiResultHandler.buildApiResult(200, "更新成功", res);
        }
        return ApiResultHandler.buildApiResult(400, "更新失败", res);
    }




/*
*
* */
    @GetMapping("/daQuestionId/{studentIdd}/{questionIdd}")
    public ApiResult findOnlyQuestionId(@PathVariable Integer studentIdd, @PathVariable Integer questionIdd) {
        // 直接输出questionId的值，它也会自动调用Integer的toString方法
        DaQuestion res = daQuestionService.findOnlyQuestionId(studentIdd, questionIdd);
        return ApiResultHandler.buildApiResult(200, "查询成功", res);
    }


    @PostMapping("/daQuestion")
    public ApiResult add(@RequestBody DaQuestion daQuestion) {
        int res = daQuestionService.add(daQuestion);
        if (res != 0) {
            return ApiResultHandler.buildApiResult(200, "添加成功", res);
        }
        return ApiResultHandler.buildApiResult(400, "添加失败", res);
    }


    /**
     * 新增大题后前端会调用该接口获取最新 questionId，再写入 paper_manage。
     * 该接口此前缺失，导致前端 404、大题无法自动关联到试卷。
     */
    @GetMapping("/fillQuestionId2")
    public ApiResult findLastQuestionId() {
        DaQuestion res = daQuestionService.findLastQuestionId();
        return ApiResultHandler.buildApiResult(200, "查询成功", res);
    }

    @PostMapping("/editDaQuestion")
    public ApiResult edit(@RequestBody DaQuestion daQuestion) {
        int res = daQuestionService.edit(daQuestion);
        if (res != 0) {
            return ApiResultHandler.buildApiResult(200, "修改成功", res);
        }
        return ApiResultHandler.buildApiResult(400, "修改失败", res);
    }
}
