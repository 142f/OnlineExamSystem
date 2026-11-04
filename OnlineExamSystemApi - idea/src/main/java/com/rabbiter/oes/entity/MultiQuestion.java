package com.rabbiter.oes.entity;

import java.util.Objects;

// 选择题实体
public class MultiQuestion {
    private Integer questionId;

    private String subject;

    private String section;

    private String answerA;

    private String answerB;

    private String answerC;

    private String answerD;

    private String question;

    private String level;

    private String rightAnswer;

    private String analysis; //题目解析

    private Integer score;

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

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getAnswerA() {
        return answerA;
    }

    public void setAnswerA(String answerA) {
        this.answerA = answerA;
    }

    public String getAnswerB() {
        return answerB;
    }

    public void setAnswerB(String answerB) {
        this.answerB = answerB;
    }

    public String getAnswerC() {
        return answerC;
    }

    public void setAnswerC(String answerC) {
        this.answerC = answerC;
    }

    public String getAnswerD() {
        return answerD;
    }

    public void setAnswerD(String answerD) {
        this.answerD = answerD;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "MultiQuestion{" +
                "questionId=" + questionId +
                ", subject='" + subject + '\'' +
                ", section='" + section + '\'' +
                ", answerA='" + answerA + '\'' +
                ", answerB='" + answerB + '\'' +
                ", answerC='" + answerC + '\'' +
                ", answerD='" + answerD + '\'' +
                ", question='" + question + '\'' +
                ", level='" + level + '\'' +
                ", rightAnswer='" + rightAnswer + '\'' +
                ", analysis='" + analysis + '\'' +
                ", score=" + score +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MultiQuestion that = (MultiQuestion) o;
        return Objects.equals(questionId, that.questionId) && Objects.equals(subject, that.subject) && Objects.equals(section, that.section) && Objects.equals(answerA, that.answerA) && Objects.equals(answerB, that.answerB) && Objects.equals(answerC, that.answerC) && Objects.equals(answerD, that.answerD) && Objects.equals(question, that.question) && Objects.equals(level, that.level) && Objects.equals(rightAnswer, that.rightAnswer) && Objects.equals(analysis, that.analysis) && Objects.equals(score, that.score);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionId, subject, section, answerA, answerB, answerC, answerD, question, level, rightAnswer, analysis, score);
    }

    public MultiQuestion(Integer questionId, String subject, String section, String answerA, String answerB, String answerC, String answerD, String question,
                         String level, String rightAnswer, String analysis, Integer score) {
        this.questionId = questionId;
        this.subject = subject;
        this.section = section;
        this.answerA = answerA;
        this.answerB = answerB;
        this.answerC = answerC;
        this.answerD = answerD;
        this.question = question;
        this.level = level;
        this.rightAnswer = rightAnswer;
        this.analysis = analysis;
        this.score = score;
    }

    public MultiQuestion() {
    }
}