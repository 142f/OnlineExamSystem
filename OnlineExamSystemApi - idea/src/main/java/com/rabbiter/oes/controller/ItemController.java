package com.rabbiter.oes.controller;

import com.rabbiter.oes.entity.ApiResult;
import com.rabbiter.oes.entity.ExamManage;
import com.rabbiter.oes.entity.PaperManage;
import com.rabbiter.oes.service.PaperService;
import com.rabbiter.oes.serviceimpl.FillQuestionServiceImpl;
import com.rabbiter.oes.serviceimpl.JudgeQuestionServiceImpl;
import com.rabbiter.oes.serviceimpl.MultiQuestionServiceImpl;
import com.rabbiter.oes.serviceimpl.ScoreServiceImpl;
import com.rabbiter.oes.util.ApiResultHandler;
import com.rabbiter.oes.vo.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ItemController {

    @Autowired
    MultiQuestionServiceImpl multiQuestionService;

    @Autowired
    FillQuestionServiceImpl fillQuestionService;

    @Autowired
    JudgeQuestionServiceImpl judgeQuestionService;

    @Autowired
    PaperService paperService;

    @Autowired
    private ScoreServiceImpl scoreService;
    @PostMapping("/item")

    /////////组卷
    public ApiResult ItemController(@RequestBody Item item) {
        // 选择题
        Integer changeNumber = item.getChangeNumber();
        // 填空题
        Integer fillNumber = item.getFillNumber();

        //大题
        Integer fillNumber2 = item.getFillNumber2();

        // 判断题
        Integer judgeNumber = item.getJudgeNumber();
        //出卷id
        Integer paperId = item.getPaperId();

        // 数据库获取数据
        System.out.println("::--------1");
        List<Integer> changeNumbers = multiQuestionService.findBySubject(item.getSubject(), changeNumber);
        System.out.println("::--------2");
        List<Integer> fills = fillQuestionService.findBySubject(item.getSubject(), fillNumber);
        List<Integer> judges = judgeQuestionService.findBySubject(item.getSubject(), judgeNumber);
        List<Integer> fills2 = fillQuestionService.findBySubject2(item.getSubject(), fillNumber2);


        if (changeNumbers == null || changeNumbers.size() != changeNumber) {
            return ApiResultHandler.buildApiResult(400, "科目【" + item.getSubject() + "】题库【选择题】题目数量不足【" + changeNumber + "】，组卷失败", null);
        }
        if (fills == null || fills.size() != fillNumber) {
            return ApiResultHandler.buildApiResult(400, "科目【" + item.getSubject() + "】题库【填空题】题目数量不足【" + fillNumber + "】，组卷失败", null);
        }
        if (judges == null || judges.size() != judgeNumber) {
            return ApiResultHandler.buildApiResult(400, "科目【" + item.getSubject() + "】题库【判断题】题目数量不足【" + judgeNumber + "】，组卷失败", null);
        }
        if (fills2 == null || fills2.size() != fillNumber2) {
            return ApiResultHandler.buildApiResult(400, "科目【" + item.getSubject() + "】题库【大题】题目数量不足【" + fillNumber2 + "】，组卷失败", null);
        }
        // 符合组题条件，执行组题
        // 选择题
        for (Integer number : changeNumbers) {
            PaperManage paperManage = new PaperManage(paperId, 1, number);
            int index = paperService.add(paperManage);
            if (index == 0)
                return ApiResultHandler.buildApiResult(400, "选择题组卷保存失败", null);
        }

        // 填空题
        for (Integer fillNum : fills) {
            PaperManage paperManage = new PaperManage(paperId, 2, fillNum);
            int index = paperService.add(paperManage);
            if (index == 0)
                return ApiResultHandler.buildApiResult(400, "填空题题组卷保存失败", null);
        }

        // 大题
        for (Integer fillNum : fills2) {
            PaperManage paperManage = new PaperManage(paperId, 4, fillNum);
            int index = paperService.add(paperManage);
            if (index == 0)
                return ApiResultHandler.buildApiResult(400, "填空题题组卷保存失败", null);
        }
        // 判断题
        for (Integer judge : judges) {
            PaperManage paperManage = new PaperManage(paperId, 3, judge);
            int index = paperService.add(paperManage);
            if (index == 0)
                return ApiResultHandler.buildApiResult(400, "判断题题组卷保存失败", null);
        }
/////////组卷时重新计算

        ExamManage exammanage = paperService.findByIdToExcode(paperId);
        Integer maxScore = paperService.getMaxScore(paperId);
        exammanage.setTotalScore(maxScore);
        System.out.println("-----------组卷成功");
        System.out.println(exammanage);
        System.out.println(maxScore);
        int res = paperService.updateMaxScore( exammanage);
        System.out.println(res);
        System.out.println("----------1组卷成功");

        ///如果未有成绩怎么处理
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
        System.out.println("----------3组卷成功");

        return ApiResultHandler.buildApiResult(200, "试卷组卷成功", null);
    }
}
