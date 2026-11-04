package com.rabbiter.oes.controller;

import com.rabbiter.oes.entity.ApiResult;
import com.rabbiter.oes.entity.DaQuestion;
import com.rabbiter.oes.entity.FillQuestion;
import com.rabbiter.oes.entity.JudgeQuestion;
import com.rabbiter.oes.entity.MultiQuestion;
import com.rabbiter.oes.entity.PaperManage;
import com.rabbiter.oes.service.DaQuestionService;
import com.rabbiter.oes.service.FillQuestionService;
import com.rabbiter.oes.service.JudgeQuestionService;
import com.rabbiter.oes.service.MultiQuestionService;
import com.rabbiter.oes.service.PaperService;
import com.rabbiter.oes.util.ApiResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PaperController {

    @Autowired
    private PaperService paperService;

    @Autowired
    private JudgeQuestionService judgeQuestionService;

    @Autowired
    private MultiQuestionService multiQuestionService;

    @Autowired
    private FillQuestionService fillQuestionService;

    @Autowired
    private DaQuestionService daQuestionService;

    /**
     * 删除试卷中的某条试题，并重新计算试卷总分。
     *
     * @param paperId    试卷id
     * @param type       题目类型。1选择，2填空，3判断，4大题
     * @param questionId 题目id
     */
    /**
     * RESTful 版本：删除试卷中的某道题。
     * 旧的 GET /paper/delete/... 保留一个版本用于兼容历史前端调用，后续可下线。
     */
    @DeleteMapping("/paper/{paperId}/{type}/{questionId}")
    public ApiResult deleteRest(
            @PathVariable("paperId") String paperId,
            @PathVariable("type") String type,
            @PathVariable("questionId") String questionId
    ) {
        return removeQuestion(paperId, type, questionId);
    }

    /**
     * @deprecated 请改用 DELETE /paper/{paperId}/{type}/{questionId}
     */
    @Deprecated
    @GetMapping("/paper/delete/{paperId}/{type}/{questionId}")
    public ApiResult delete(
            @PathVariable("paperId") String paperId,
            @PathVariable("type") String type,
            @PathVariable("questionId") String questionId
    ) {
        return removeQuestion(paperId, type, questionId);
    }

    private ApiResult removeQuestion(String paperId, String type, String questionId) {
        try {
            paperService.removeQuestion(paperId, type, questionId);
        } catch (IllegalArgumentException e) {
            return ApiResultHandler.buildApiResult(400, e.getMessage(), null);
        }
        return ApiResultHandler.buildApiResult(200, "删除成功", null);
    }

    @GetMapping("/papers")
    public ApiResult<PaperManage> findAll() {
        return ApiResultHandler.buildApiResult(200, "请求成功", paperService.findAll());
    }

    @GetMapping("/paper/{paperId}")
    public Map<Integer, List<?>> findById(@PathVariable("paperId") Integer paperId) {
        List<MultiQuestion> multiQuestionRes = multiQuestionService.findByIdAndType(paperId);   //选择题题库 1
        List<FillQuestion> fillQuestionsRes = fillQuestionService.findByIdAndType(paperId);     //填空题题库 2
        List<JudgeQuestion> judgeQuestionRes = judgeQuestionService.findByIdAndType(paperId);   //判断题题库 3
        List<DaQuestion> daQuestion = daQuestionService.findByIdAndType(paperId);               //大题 4

        Map<Integer, List<?>> map = new HashMap<>();
        map.put(1, multiQuestionRes);
        map.put(2, fillQuestionsRes);
        map.put(3, judgeQuestionRes);
        map.put(4, daQuestion);
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
