package com.rabbiter.oes.controller;

import com.rabbiter.oes.entity.*;
import com.rabbiter.oes.serviceimpl.*;
import com.rabbiter.oes.util.ApiResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PaperController {

    @Autowired
    private PaperServiceImpl paperService;

    @Autowired
    private JudgeQuestionServiceImpl judgeQuestionService;

    @Autowired
    private MultiQuestionServiceImpl multiQuestionService;

    @Autowired
    private FillQuestionServiceImpl fillQuestionService;
    @Autowired
    private DaQuestionServiceImpl daQuestionServiceImpl;
    @Autowired
    private ScoreServiceImpl scoreService;


    /**
     * 删除试卷中的某条试题
     *
     * @param paperId    试卷id
     * @param type       题目类型。1选择，2填空，3判断，4
     * @param questionId 题目id
     */
    @GetMapping("/paper/delete/{paperId}/{type}/{questionId}")
    public ApiResult delete(
            @PathVariable("paperId") String paperId,
            @PathVariable("type") String type,
            @PathVariable("questionId") String questionId
    ) {
        paperService.delete(paperId, type, questionId);

        int integerId = Integer.parseInt(paperId);
        ExamManage exammanage = paperService.findByIdToExcode(integerId);
        Integer maxScore = paperService.getMaxScore(integerId);
        exammanage.setTotalScore(maxScore);
        System.out.println("-----------删除成功");
        System.out.println(exammanage);
        System.out.println(maxScore);
        int res = paperService.updateMaxScore(exammanage);
        System.out.println(res);

        System.out.println("----------1删除成功");
/////////组卷时重新计算
        try {
            // 尝试更新最高分数
            int i = scoreService.updateMaxScore(exammanage);
            // 如果成功，可以在这里添加处理成功后的逻辑
            System.out.println("最高分数已更新为: " + i);
        } catch (Exception e) {
            // 捕获并处理异常
            // 这里假设scoreService.updateMaxScore方法可能会抛出一般的Exception或其子类
            System.err.println("更新最高分数时发生错误: " + e.getMessage());

        }
        System.out.println("----------12删除成功");
        return ApiResultHandler.buildApiResult(200, "删除成功", null);
    }


    @GetMapping("/papers")
    public ApiResult<PaperManage> findAll() {
        ApiResult res = ApiResultHandler.buildApiResult(200, "请求成功", paperService.findAll());
        return res;
    }

    @GetMapping("/paper/{paperId}")
    public Map<Integer, List<?>> findById(@PathVariable("paperId") Integer paperId) {
        List<MultiQuestion> multiQuestionRes = multiQuestionService.findByIdAndType(paperId);   //选择题题库 1
        List<FillQuestion> fillQuestionsRes = fillQuestionService.findByIdAndType(paperId);     //填空题题库 2
        List<JudgeQuestion> judgeQuestionRes = judgeQuestionService.findByIdAndType(paperId);   //判断题题库 3
// TODO: 2024/3/26 大题
        List<DaQuestion> daQuestion = daQuestionServiceImpl.findByIdAndType(paperId);   //大题 4

        Map<Integer, List<?>> map = new HashMap<>();
        map.put(1, multiQuestionRes);
        map.put(2, fillQuestionsRes);
        map.put(3, judgeQuestionRes);
        map.put(4, daQuestion);
        System.out.println(daQuestion + "\n----findById题目内容");
        return map;
    }

    @PostMapping("/paperManage")
    public ApiResult add(@RequestBody PaperManage paperManage) {
        int res = paperService.add(paperManage);
        if (res != 0) {
            return ApiResultHandler.buildApiResult(200, "添加成功", res);
        }

        return ApiResultHandler.buildApiResult(400, "添加失败", res);
    }


}
