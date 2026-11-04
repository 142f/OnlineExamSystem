package com.rabbiter.oes.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rabbiter.oes.entity.ApiResult;
import com.rabbiter.oes.entity.DaQuestion;
import com.rabbiter.oes.entity.Student;
import com.rabbiter.oes.serviceimpl.DaQuestionServiceImpl;
import com.rabbiter.oes.util.ApiResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
//////
// TODO: 2024/3/25
@RestController
public class DaController {

    @Autowired
    private DaQuestionServiceImpl daQuestionService;

    @GetMapping("/daQuestionall2/{page}/{size}/{studentId}")
    public ApiResult findAll(@PathVariable Integer page, @PathVariable Integer size,
                             @PathVariable Integer studentId) {
        Page<DaQuestion> DaPage = new Page<>(page, size);

        IPage<DaQuestion> res = daQuestionService.findAll(DaPage, studentId);

        return ApiResultHandler.buildApiResult(200, "分页查询所有学生da", res);
    }

    // TODO: 2024/3/27 处理前端大题的答题内容
//    @GetMapping("/daQuestionUpdate2/{studentId}/{questionId}/{answerMe}")
//    public void update2(@PathVariable Integer studentId, @PathVariable Integer questionId,
//                        @PathVariable String answerMe) {
//
//        daQuestionService.update2(studentId, questionId, answerMe);
//    }
//

    // TODO: 2024/3/26 更新数据库
    @PutMapping("/daQuestion2")
    public ApiResult update(@RequestBody DaQuestion daQuestion) {
        Integer studentId = daQuestion.getStudentId();
        Integer examCode = daQuestion.getExamCode();
        int res = daQuestionService.update(daQuestion);
        daQuestionService.updateScore(studentId,examCode);

        System.out.println(daQuestion.getStudentId() + "---::1");
        System.out.println(daQuestion.getExamCode() + "---::1");

        if (res != 0) {
            return ApiResultHandler.buildApiResult(200, "更新成功", res);
        }

        return ApiResultHandler.buildApiResult(400, "更新失败", res);
    }
    @GetMapping("/daQuestionId2/{studentIdd}/{questionIdd}")
    public ApiResult findOnlyQuestionId(@PathVariable Integer studentIdd, @PathVariable Integer questionIdd) {
        System.out.println(studentIdd + "-----1");
        // 直接输出questionId的值，它也会自动调用Integer的toString方法
        System.out.println(questionIdd + "-----1");
        DaQuestion res = daQuestionService.findOnlyQuestionId(studentIdd, questionIdd);
        return ApiResultHandler.buildApiResult(200, "查询成功", res);
    }
    @PostMapping("/daQuestion2")
    public ApiResult add(@RequestBody DaQuestion daQuestion) {
        int res = daQuestionService.add(daQuestion);
        if (res != 0) {
            return ApiResultHandler.buildApiResult(200, "添加成功", res);
        }
        return ApiResultHandler.buildApiResult(400, "添加失败", res);
    }

    @PostMapping("/editDaQuestion2")
    public ApiResult edit(@RequestBody DaQuestion daQuestion) {
        int res = daQuestionService.edit(daQuestion);
        if (res != 0) {
            return ApiResultHandler.buildApiResult(200, "修改成功", res);
        }
        return ApiResultHandler.buildApiResult(400, "修改失败", res);
    }






    @GetMapping("/daQuestionId3/{studentIdd}/{questionIdd}")
    public ApiResult findOnlyQuestionId3(@PathVariable Integer studentIdd, @PathVariable Integer questionIdd) {
        System.out.println(studentIdd + "-----1");
        // 直接输出questionId的值，它也会自动调用Integer的toString方法
        System.out.println(questionIdd + "-----1");
        DaQuestion res = daQuestionService.findOnlyQuestionId(studentIdd, questionIdd);
        return ApiResultHandler.buildApiResult(200, "查询成功", res);
    }

    /*
    *  @PostMapping("/daQuestion3")
    * */
    @PostMapping("/daQuestion3")
    public ApiResult add3(@RequestBody DaQuestion daQuestion) {
        int res = daQuestionService.add(daQuestion);
        if (res != 0) {
            return ApiResultHandler.buildApiResult(200, "添加成功", res);
        }
        return ApiResultHandler.buildApiResult(400, "添加失败", res);
    }


    /*
    *     @PostMapping("/editDaQuestion3")

     * */
    @PostMapping("/editDaQuestion3")
    public ApiResult edit3(@RequestBody DaQuestion daQuestion) {
        int res = daQuestionService.edit(daQuestion);
        if (res != 0) {
            return ApiResultHandler.buildApiResult(200, "修改成功", res);
        }
        return ApiResultHandler.buildApiResult(400, "修改失败", res);
    }


}
