package com.rabbiter.oes.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rabbiter.oes.entity.ApiResult;
import com.rabbiter.oes.entity.ExamManage;
import com.rabbiter.oes.mapper.PaperMapper;
import com.rabbiter.oes.serviceimpl.ExamManageServiceImpl;
import com.rabbiter.oes.serviceimpl.PaperServiceImpl;
import com.rabbiter.oes.serviceimpl.ScoreServiceImpl;
import com.rabbiter.oes.util.ApiResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ExamManageController {

    @Autowired
    private ExamManageServiceImpl examManageService;
    //更新总分
    @Autowired
    private PaperMapper paperMapper;

    @Autowired
    private PaperServiceImpl paperService;

    @Autowired
    private ScoreServiceImpl scoreService;

    //搜索作业
    @GetMapping("/exams")
    public ApiResult findAll() {
        System.out.println("不分页查询所有试卷");
        ApiResult apiResult;
        apiResult = ApiResultHandler.buildApiResult(200, "请求成功！", examManageService.findAll());
        return apiResult;
    }

//分页查询所有试卷
    @GetMapping("/exams/{page}/{size}")
    public ApiResult findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        System.out.println("分页查询所有试卷");
        ApiResult apiResult;
        Page<ExamManage> examManage = new Page<>(page, size);
        IPage<ExamManage> all = examManageService.findAll(examManage);
        apiResult = ApiResultHandler.buildApiResult(200, "请求成功！", all);
        return apiResult;
    }
    //通过examCode请求试卷详细信息
    @GetMapping("/exam/{examCode}")
    public ApiResult findById(@PathVariable("examCode") Integer examCode) {
        System.out.println("根据ID查找");
        ExamManage res = examManageService.findById(examCode);
        System.out.println(res.getTotalScore() + "--::1");
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
//        if (res == 0) {
//            return ApiResultHandler.buildApiResult(20000,"请求参数错误");
//        }
        System.out.print("更新操作执行---");
        return ApiResultHandler.buildApiResult(200, "更新成功", res);
    }

    //增加作业
    @PostMapping("/exam")
    public ApiResult add(@RequestBody ExamManage exammanage) {
        int res = examManageService.add(exammanage);

        Integer paperId = exammanage.getPaperId();
        //计算机操作后最新总分值
        Integer maxScore = paperService.getMaxScore(paperId);
        exammanage.setTotalScore(maxScore);
        System.out.println("-----------作业添加"+maxScore);
        System.out.println(exammanage);
        //传给后端修改对应 分数
        int res2 = examManageService.updateMaxScore(exammanage);
        System.out.println(res2);

        if (res == 1) {
            return ApiResultHandler.buildApiResult(200, "作业添加成功", res);
        } else {
            return ApiResultHandler.buildApiResult(400, "作业添加失败", res);
        }
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
