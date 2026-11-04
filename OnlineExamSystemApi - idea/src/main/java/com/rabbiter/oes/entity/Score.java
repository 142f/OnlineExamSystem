package com.rabbiter.oes.entity;

import java.util.Objects;

public class Score {
    // TODO: 2024/3/26 客观题 总分
    private Integer Score1;
    private Integer Score2;
    private Integer examCode;

    private Integer studentId;

    private String subject;

    private Integer ptScore;

    private Integer etScore;

    //大题分数
    private Integer dScore;


    private Integer score;

    private Integer scoreId;

    private String answerDate;

    public Integer getExamCode() {
        return examCode;
    }

    public void setExamCode(Integer examCode) {
        this.examCode = examCode;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Integer getPtScore() {
        return ptScore;
    }

    public void setPtScore(Integer ptScore) {
        this.ptScore = ptScore;
    }

    public Integer getEtScore() {
        return etScore;
    }

    public void setEtScore(Integer etScore) {
        this.etScore = etScore;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getScoreId() {
        return scoreId;
    }

    public void setScoreId(Integer scoreId) {
        this.scoreId = scoreId;
    }

    public String getAnswerDate() {
        return answerDate;
    }

    public void setAnswerDate(String answerDate) {
        this.answerDate = answerDate;
    }

    public Integer getScore1() {
        return Score1;
    }

    public void setScore1(Integer score1) {
        Score1 = score1;
    }

    public Integer getScore2() {
        return Score2;
    }

    public void setScore2(Integer score2) {
        Score2 = score2;
    }

    public Integer getdScore() {
        return dScore;
    }

    public void setdScore(Integer dScore) {
        this.dScore = dScore;
    }

    @Override
    public String toString() {
        return "Score{" +
                "Score1=" + Score1 +
                ", Score2=" + Score2 +
                ", examCode=" + examCode +
                ", studentId=" + studentId +
                ", subject='" + subject + '\'' +
                ", ptScore=" + ptScore +
                ", etScore=" + etScore +
                ", dScore=" + dScore +
                ", score=" + score +
                ", scoreId=" + scoreId +
                ", answerDate='" + answerDate + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Score score1 = (Score) o;
        return Objects.equals(Score1, score1.Score1) && Objects.equals(Score2, score1.Score2) && Objects.equals(examCode, score1.examCode) && Objects.equals(studentId, score1.studentId) && Objects.equals(subject, score1.subject) && Objects.equals(ptScore, score1.ptScore) && Objects.equals(etScore, score1.etScore) && Objects.equals(dScore, score1.dScore) && Objects.equals(score, score1.score) && Objects.equals(scoreId, score1.scoreId) && Objects.equals(answerDate, score1.answerDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Score1, Score2, examCode, studentId, subject, ptScore, etScore, dScore, score, scoreId, answerDate);
    }

    public Score(Integer score1, Integer score2, Integer examCode, Integer studentId,
                 String subject, Integer ptScore, Integer etScore, Integer dScore,
                 Integer score, Integer scoreId, String answerDate) {
        Score1 = score1;
        Score2 = score2;
        this.examCode = examCode;
        this.studentId = studentId;
        this.subject = subject;
        this.ptScore = ptScore;
        this.etScore = etScore;
        this.dScore = dScore;
        this.score = score;
        this.scoreId = scoreId;
        this.answerDate = answerDate;
    }

    public Score() {
    }
}

