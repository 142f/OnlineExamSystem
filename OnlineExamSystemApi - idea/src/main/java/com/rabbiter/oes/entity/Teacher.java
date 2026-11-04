package com.rabbiter.oes.entity;

import java.util.Objects;

public class Teacher {
    private Integer teacherId;

    private String teacherName;

    private String institute;

    private String sex;

    private String tel;

    private String email;

    private String pwd;

    private String cardId;

    private String type;

    private String role;

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "teacherId=" + teacherId +
                ", teacherName='" + teacherName + '\'' +
                ", institute='" + institute + '\'' +
                ", sex='" + sex + '\'' +
                ", tel='" + tel + '\'' +
                ", email='" + email + '\'' +
                ", pwd='" + pwd + '\'' +
                ", cardId='" + cardId + '\'' +
                ", type='" + type + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        return Objects.equals(teacherId, teacher.teacherId) && Objects.equals(teacherName, teacher.teacherName) && Objects.equals(institute, teacher.institute) && Objects.equals(sex, teacher.sex) && Objects.equals(tel, teacher.tel) && Objects.equals(email, teacher.email) && Objects.equals(pwd, teacher.pwd) && Objects.equals(cardId, teacher.cardId) && Objects.equals(type, teacher.type) && Objects.equals(role, teacher.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teacherId, teacherName, institute, sex, tel, email, pwd, cardId, type, role);
    }

    public Teacher(Integer teacherId, String teacherName, String institute, String sex, String tel, String email, String pwd, String cardId, String type, String role) {
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.institute = institute;
        this.sex = sex;
        this.tel = tel;
        this.email = email;
        this.pwd = pwd;
        this.cardId = cardId;
        this.type = type;
        this.role = role;
    }

    public Teacher() {
    }
}