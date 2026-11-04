package com.rabbiter.oes.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rabbiter.oes.entity.ApiResult;
import com.rabbiter.oes.entity.ExamManage;
import com.rabbiter.oes.service.ExamManageService;
import com.rabbiter.oes.util.ApiResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExamManageController {

    @Autowired
    private ExamManageService examManageService;

    //搜索作业
    @GetMapping("/exams")
    public ApiResult findAll() {
        return ApiResultHandler.buildApiResult(200, "请求成功！", examManageService.findAll());
    }

    //分页查询所有试卷
    @GetMapping("/exams/{page}/{size}")
    public ApiResult findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        Page<ExamManage> examManage = new Page<>(page, size);
        IPage<ExamManage> all = examManageService.findAll(examManage);
        return ApiResultHandler.buildApiResult(200, "请求成功！", all);
    }

    //通过examCode请求试卷详细信息
    @GetMapping("/exam/{examCode}")
    public ApiResult findById(@PathVariable("examCode") Integer examCode) {
        ExamManage res = examManageService.findById(examCode);
        if (res == null) {
            return ApiResultHandler.buildApiResult(10000, "作业编号不存在", null);
        }
        return ApiResultHandler.buildApiResult(200, "请求成功！", res);
    }

    //通过examCode 删除试卷详细信息
    @DeleteMapping("/exam/{examCode}")
    public ApiResult deleteById(@PathVariable("examCode") Integer examCode) {
        int res = examManageService.delete(examCode);
        return ApiResultHandler.buildApiResult(200, "删除成功", res);
    }

    @PutMapping("/exam")
    public ApiResult update(@RequestBody ExamManage exammanage) {
        int res = examManageService.update(exammanage);
        return ApiResultHandler.buildApiResult(200, "更新成功", res);
    }

    //增加作业：总分在 service 层随插入一次写入
    @PostMapping("/exam")
    public ApiResult add(@RequestBody ExamManage exammanage) {
        int res = examManageService.add(exammanage);
        if (res == 1) {
            return ApiResultHandler.buildApiResult(200, "作业添加成功", res);
        }
        return ApiResultHandler.buildApiResult(400, "作业添加失败", res);
    }

    @GetMapping("/examManagePaperId")
    public ApiResult findOnlyPaperId() {
        ExamManage res = examManageService.findOnlyPaperId();
        if (res != null) {
            return ApiResultHandler.buildApiResult(200, "请求成功", res);
        }
        return ApiResultHandler.buildApiResult(400, "请求失败", res);
    }
}
