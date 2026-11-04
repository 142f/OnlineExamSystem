package com.rabbiter.oes.entity;

import java.util.Objects;

//填空题实体类
public class FillQuestion {
    private Integer questionId;

    private String subject;

    private String question;

    private String answer;

    private Integer score;

    private String level;

    private String section;

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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
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

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    @Override
    public String toString() {
        return "FillQuestion{" +
                "questionId=" + questionId +
                ", subject='" + subject + '\'' +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", score=" + score +
                ", level='" + level + '\'' +
                ", section='" + section + '\'' +
                ", analysis='" + analysis + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FillQuestion that = (FillQuestion) o;
        return Objects.equals(questionId, that.questionId) && Objects.equals(subject, that.subject) && Objects.equals(question, that.question) && Objects.equals(answer, that.answer) && Objects.equals(score, that.score) && Objects.equals(level, that.level) && Objects.equals(section, that.section) && Objects.equals(analysis, that.analysis);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionId, subject, question, answer, score, level, section, analysis);
    }

    public FillQuestion(Integer questionId, String subject, String question, String answer,
                        Integer score, String level, String section, String analysis) {
        this.questionId = questionId;
        this.subject = subject;
        this.question = question;
        this.answer = answer;
        this.score = score;
        this.level = level;
        this.section = section;
        this.analysis = analysis;
    }

    public FillQuestion() {
    }
}
