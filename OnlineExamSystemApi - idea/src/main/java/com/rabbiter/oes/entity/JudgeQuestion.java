package com.rabbiter.oes.entity;

import java.util.Objects;

//判断题实体类
public class JudgeQuestion {
    private Integer questionId;

    private String subject;

    private String question;

    private String answer;

    private String level;

    private String section;

    private Integer score;

    private String analysis; //题目解析

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    @Override
    public String toString() {
        return "JudgeQuestion{" +
                "questionId=" + questionId +
                ", subject='" + subject + '\'' +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", level='" + level + '\'' +
                ", section='" + section + '\'' +
                ", score=" + score +
                ", analysis='" + analysis + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JudgeQuestion that = (JudgeQuestion) o;
        return Objects.equals(questionId, that.questionId) && Objects.equals(subject, that.subject)
                && Objects.equals(question, that.question)
                && Objects.equals(answer, that.answer)
                && Objects.equals(level, that.level)
                && Objects.equals(section, that.section)
                && Objects.equals(score, that.score)
                && Objects.equals(analysis, that.analysis);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionId, subject, question, answer, level, section, score, analysis);
    }

    public JudgeQuestion() {
    }

    public JudgeQuestion(Integer questionId, String subject, String question, String answer,
                         String level, String section, Integer score, String analysis) {
        this.questionId = questionId;
        this.subject = subject;
        this.question = question;
        this.answer = answer;
        this.level = level;
        this.section = section;
        this.score = score;
        this.analysis = analysis;
    }
}