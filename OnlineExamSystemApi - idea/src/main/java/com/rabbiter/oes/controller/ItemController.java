package com.rabbiter.oes.controller;

import com.rabbiter.oes.entity.ApiResult;
import com.rabbiter.oes.service.PaperService;
import com.rabbiter.oes.util.ApiResultHandler;
import com.rabbiter.oes.vo.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemController {

    @Autowired
    private PaperService paperService;

    /**
     * 组卷：按题目类型数量随机抽题并生成试卷。
     * 抽题、写入、总分同步由 PaperService#composePaper 在同一事务内完成。
     */
    @PostMapping("/item")
    public ApiResult createPaper(@RequestBody Item item) {
        try {
            paperService.composePaper(item);
        } catch (IllegalArgumentException e) {
            return ApiResultHandler.buildApiResult(400, e.getMessage(), null);
        }
        return ApiResultHandler.buildApiResult(200, "试卷组卷成功", null);
    }
}
