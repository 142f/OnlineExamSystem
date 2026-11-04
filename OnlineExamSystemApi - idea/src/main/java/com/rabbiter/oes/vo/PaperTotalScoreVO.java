package com.rabbiter.oes.vo;

/**
 * 试卷满分聚合结果：paperId -> 满分。
 * 用于一次性计算多份试卷的总分，避免逐卷多次查询（N+1）。
 */
public class PaperTotalScoreVO {

    private Integer paperId;

    private Integer totalScore;

    public Integer getPaperId() {
        return paperId;
    }

    public void setPaperId(Integer paperId) {
        this.paperId = paperId;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }
}
