package com.rabbiter.oes.vo;

import java.util.Objects;

/**
 * 答案视图对象
 * 用于题库管理和答题展示
 */
public class AnswerVO {
    private Long questionId;
    private String question;
    private String subject;
    private Integer score; // 修改为Integer类型，更适合表示分数
    private String section;
    private String level;
    private String type;

    /**
     * 默认构造函数
     */
    public AnswerVO() {
    }

    /**
     * 全参构造函数
     */
    public AnswerVO(Long questionId, String question, String subject, Integer score, 
                   String section, String level, String type) {
        this.questionId = questionId;
        this.question = question;
        this.subject = subject;
        this.score = score;
        this.section = section;
        this.level = level;
        this.type = type;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question != null ? question.trim() : null;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject != null ? subject.trim() : null;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    // 兼容旧版本的字符串分数设置
    public void setScore(String score) {
        if (score != null && !score.trim().isEmpty()) {
            try {
                this.score = Integer.parseInt(score.trim());
            } catch (NumberFormatException e) {
                // 如果转换失败，设置为null
                this.score = null;
            }
        }
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section != null ? section.trim() : null;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level != null ? level.trim() : null;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type != null ? type.trim() : null;
    }

    @Override
    public String toString() {
        return "AnswerVO{" +
                "questionId=" + questionId +
                ", question='" + question + '\'' +
                ", subject='" + subject + '\'' +
                ", score=" + score +
                ", section='" + section + '\'' +
                ", level='" + level + '\'' +
                ", type='" + type + '\'' +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnswerVO answerVO = (AnswerVO) o;
        return Objects.equals(questionId, answerVO.questionId) &&
                Objects.equals(question, answerVO.question) &&
                Objects.equals(subject, answerVO.subject) &&
                Objects.equals(score, answerVO.score) &&
                Objects.equals(section, answerVO.section) &&
                Objects.equals(level, answerVO.level) &&
                Objects.equals(type, answerVO.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionId, question, subject, score, section, level, type);
    }
}';
    }
}