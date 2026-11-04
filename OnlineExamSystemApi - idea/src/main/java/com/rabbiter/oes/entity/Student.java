package com.rabbiter.oes.entity;

import java.util.Objects;

public class Student {
    private Integer studentId;

    private String studentName;

    private String grade;

    private String major;

    private String clazz;

    private String institute;

    private String tel;

    private String email;

    private String pwd;

    private String cardId;

    private String sex;

    private String role;

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName == null ? null : studentName.trim();
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade == null ? null : grade.trim();
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major == null ? null : major.trim();
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz == null ? null : clazz.trim();
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute == null ? null : institute.trim();
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd == null ? null : pwd.trim();
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId == null ? null : cardId.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role == null ? null : role.trim();
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", studentName='" + studentName + '\'' +
                ", grade='" + grade + '\'' +
                ", major='" + major + '\'' +
                ", clazz='" + clazz + '\'' +
                ", institute='" + institute + '\'' +
                ", tel='" + tel + '\'' +
                ", email='" + email + '\'' +
                ", pwd='" + pwd + '\'' +
                ", cardId='" + cardId + '\'' +
                ", sex='" + sex + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(studentId, student.studentId) && Objects.equals(studentName, student.studentName) && Objects.equals(grade, student.grade) && Objects.equals(major, student.major) && Objects.equals(clazz, student.clazz) && Objects.equals(institute, student.institute) && Objects.equals(tel, student.tel) && Objects.equals(email, student.email) && Objects.equals(pwd, student.pwd) && Objects.equals(cardId, student.cardId) && Objects.equals(sex, student.sex) && Objects.equals(role, student.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, studentName, grade, major, clazz, institute, tel, email, pwd, cardId, sex, role);
    }

    public Student(Integer studentId, String studentName, String grade, String major, String clazz, String institute, String tel, String email, String pwd, String cardId, String sex, String role) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.grade = grade;
        this.major = major;
        this.clazz = clazz;
        this.institute = institute;
        this.tel = tel;
        this.email = email;
        this.pwd = pwd;
        this.cardId = cardId;
        this.sex = sex;
        this.role = role;
    }

    public Student() {
    }
}