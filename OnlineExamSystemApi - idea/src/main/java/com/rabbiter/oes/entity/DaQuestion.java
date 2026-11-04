package com.rabbiter.oes.entity;

import java.util.Objects;

// TODO: 2024/3/25
public class DaQuestion {
    // TODO: 2024/3/26
    private String studentName;

    private Integer studentId;
    //答题内容
    private String answerMe;
    //评分
    private String scoreMe;

    private Integer isTag;

    // TODO: 2024/4/17  计算大题分数时新增 
    private Integer examCode;
    
    private Integer questionId;

    private String institute;



    private String subject;

    private String question;

    private String answer;

    private Integer score;

    private String level;

    private String section;

    private String analysis; //题目解析


    @Override
    public String toString() {
        return "DaQuestion{" +
                "studentName='" + studentName + '\'' +
                ", studentId=" + studentId +
                ", answerMe='" + answerMe + '\'' +
                ", scoreMe='" + scoreMe + '\'' +
                ", isTag=" + isTag +
                ", examCode=" + examCode +
                ", questionId=" + questionId +
                ", institute='" + institute + '\'' +
                ", subject='" + subject + '\'' +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", score=" + score +
                ", level='" + level + '\'' +
                ", section='" + section + '\'' +
                ", analysis='" + analysis + '\'' +
                '}';
    }

    public Integer getExamCode() {
        return examCode;
    }

    public void setExamCode(Integer examCode) {
        this.examCode = examCode;
    }

    public Integer getIsTag() {
        return isTag;
    }

    public void setIsTag(Integer isTag) {
        this.isTag = isTag;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }
    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getAnswerMe() {
        return answerMe;
    }

    public void setAnswerMe(String answerMe) {
        this.answerMe = answerMe;
    }

    public String getScoreMe() {
        return scoreMe;
    }

    public void setScoreMe(String scoreMe) {
        this.scoreMe = scoreMe;
    }

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DaQuestion that = (DaQuestion) o;
        return studentName.equals(that.studentName) && studentId.equals(that.studentId)
                && answerMe.equals(that.answerMe) && scoreMe.equals(that.scoreMe)
                && isTag.equals(that.isTag) && examCode.equals(that.examCode)
                && questionId.equals(that.questionId) && institute.equals(that.institute)
                && subject.equals(that.subject) && question.equals(that.question)
                && answer.equals(that.answer) && score.equals(that.score)
                && level.equals(that.level) && section.equals(that.section)
                && analysis.equals(that.analysis);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentName, studentId, answerMe, scoreMe, isTag, examCode, questionId, institute, subject, question, answer, score, level, section, analysis);
    }
}
