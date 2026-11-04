package com.rabbiter.oes.vo;

import java.util.Objects;

/**
 * 试卷题目配置模型
 * 用于配置试卷中各类题目的数量
 */
public class Item {

    private String subject;              // 科目
    private Integer paperId;             // 试卷ID
    private Integer changeNumber;        // 选择题数量
    private Integer fillNumber;          // 填空题数量
    private Integer daQuestionNumber;    // 大题数量（重命名更清晰）
    private Integer judgeNumber;         // 判断题数量

    /**
     * 默认构造函数
     */
    public Item() {
    }

    /**
     * 科目构造函数
     */
    public Item(String subject) {
        this.subject = subject;
    }

    /**
     * 计算题目总数
     * @return 题目总数
     */
    public int getTotalQuestionCount() {
        int total = 0;
        if (changeNumber != null) total += changeNumber;
        if (fillNumber != null) total += fillNumber;
        if (daQuestionNumber != null) total += daQuestionNumber;
        if (judgeNumber != null) total += judgeNumber;
        return total;
    }

    /**
     * 检查是否所有数量都有效（非负数）
     * @return 是否有效
     */
    public boolean isValidCount() {
        return (changeNumber == null || changeNumber >= 0) &&
               (fillNumber == null || fillNumber >= 0) &&
               (daQuestionNumber == null || daQuestionNumber >= 0) &&
               (judgeNumber == null || judgeNumber >= 0);
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject != null ? subject.trim() : null;
    }

    public Integer getPaperId() {
        return paperId;
    }

    public void setPaperId(Integer paperId) {
        this.paperId = paperId;
    }

    public Integer getChangeNumber() {
        return changeNumber;
    }

    public void setChangeNumber(Integer changeNumber) {
        this.changeNumber = changeNumber;
    }

    public Integer getFillNumber() {
        return fillNumber;
    }

    public void setFillNumber(Integer fillNumber) {
        this.fillNumber = fillNumber;
    }

    public Integer getJudgeNumber() {
        return judgeNumber;
    }

    public void setJudgeNumber(Integer judgeNumber) {
        this.judgeNumber = judgeNumber;
    }

    public Integer getFillNumber2() {
        return daQuestionNumber; // 兼容旧的getter方法
    }

    public void setFillNumber2(Integer fillNumber2) {
        this.daQuestionNumber = fillNumber2; // 兼容旧的setter方法
    }

    // 新的更清晰的getter和setter方法
    public Integer getDaQuestionNumber() {
        return daQuestionNumber;
    }

    public void setDaQuestionNumber(Integer daQuestionNumber) {
        this.daQuestionNumber = daQuestionNumber;
    }

    @Override
    public String toString() {
        return "Item{" +
                "subject='" + subject + '\'' +
                ", paperId=" + paperId +
                ", changeNumber=" + changeNumber +
                ", fillNumber=" + fillNumber +
                ", daQuestionNumber=" + daQuestionNumber +
                ", judgeNumber=" + judgeNumber +
                ", totalQuestionCount=" + getTotalQuestionCount() +
                '}'
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(subject, item.subject) &&
                Objects.equals(paperId, item.paperId) &&
                Objects.equals(changeNumber, item.changeNumber) &&
                Objects.equals(fillNumber, item.fillNumber) &&
                Objects.equals(daQuestionNumber, item.daQuestionNumber) &&
                Objects.equals(judgeNumber, item.judgeNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject, paperId, changeNumber, fillNumber, daQuestionNumber, judgeNumber);;
    }
}