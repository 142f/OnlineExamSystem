package com.rabbiter.oes.vo;

import com.rabbiter.oes.entity.FillQuestion;
import com.rabbiter.oes.entity.JudgeQuestion;
import com.rabbiter.oes.entity.MultiQuestion;
import java.util.Objects;

/**
 * 题目视图对象
 * 用于封装不同类型的题目信息
 */
public class QuestionVO {
    private String type; // 题目类型：选择题、判断题、填空题

    private FillQuestion fillQuestion; // 填空题

    private JudgeQuestion judgeQuestion; // 判断题

    private MultiQuestion multiQuestion; // 选择题

    /**
     * 默认构造函数
     */
    public QuestionVO() {
    }

    /**
     * 指定类型的构造函数
     */
    public QuestionVO(String type) {
        this.type = type;
    }

    /**
     * 获取当前类型对应的题目对象
     * @return 题目对象
     */
    public Object getCurrentQuestion() {
        switch (type) {
            case "选择题":
                return multiQuestion;
            case "判断题":
                return judgeQuestion;
            case "填空题":
                return fillQuestion;
            default:
                return null;
        }
    }

    /**
     * 判断当前题目是否有值
     * @return 是否有值
     */
    public boolean hasQuestion() {
        Object question = getCurrentQuestion();
        return question != null;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type != null ? type.trim() : null;
    }

    public FillQuestion getFillQuestion() {
        return fillQuestion;
    }

    public void setFillQuestion(FillQuestion fillQuestion) {
        this.fillQuestion = fillQuestion;
    }

    public JudgeQuestion getJudgeQuestion() {
        return judgeQuestion;
    }

    public void setJudgeQuestion(JudgeQuestion judgeQuestion) {
        this.judgeQuestion = judgeQuestion;
    }

    public MultiQuestion getMultiQuestion() {
        return multiQuestion;
    }

    public void setMultiQuestion(MultiQuestion multiQuestion) {
        this.multiQuestion = multiQuestion;
    }

    @Override
    public String toString() {
        return "QuestionVO{" +
                "type='" + type + '\'' +
                ", hasQuestion=" + hasQuestion() +
                ", currentQuestionType=" + (hasQuestion() ? getCurrentQuestion().getClass().getSimpleName() : "null") +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionVO that = (QuestionVO) o;
        return Objects.equals(type, that.type) &&
                Objects.equals(fillQuestion, that.fillQuestion) &&
                Objects.equals(judgeQuestion, that.judgeQuestion) &&
                Objects.equals(multiQuestion, that.multiQuestion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, fillQuestion, judgeQuestion, multiQuestion);
    }
}