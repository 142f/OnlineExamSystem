package com.rabbiter.oes.vo;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * 学生信息视图对象
 * 用于学生信息的展示和操作
 */
public class daVo { // 暂时保留原类名，后续可以考虑重命名为StudentInfoVO
    private Integer studentId;     // 学生ID
    private String studentName;    // 学生姓名
    private String grade;          // 年级
    private String major;          // 专业
    private String clazz;          // 班级
    private String institute;      // 学院
    private String tel;            // 电话号码
    private String email;          // 邮箱
    private String pwd;            // 密码
    private String cardId;         // 身份证号
    private String sex;            // 性别
    private String role;           // 角色

    // 邮箱正则表达式
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    // 手机号正则表达式（简单匹配中国大陆手机号）
    private static final Pattern TEL_PATTERN = Pattern.compile("^1[3-9]\\d{9}$");

    /**
     * 默认构造函数
     */
    public daVo() {
    }

    /**
     * 根据学生ID构造
     */
    public daVo(Integer studentId) {
        this.studentId = studentId;
    }

    /**
     * 验证手机号格式
     * @return 是否有效
     */
    public boolean isValidTel() {
        return tel == null || TEL_PATTERN.matcher(tel).matches();
    }

    /**
     * 验证邮箱格式
     * @return 是否有效
     */
    public boolean isValidEmail() {
        return email == null || EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * 获取学生基本信息摘要（不包含敏感信息）
     * @return 基本信息摘要
     */
    public String getBasicInfoSummary() {
        return "学生ID: " + studentId + ", 姓名: " + studentName + ", 年级: " + grade + ", 专业: " + major;
    }

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
        // 密码字段做脱敏处理
        String maskedPwd = pwd != null ? "******" : null;
        return "daVo{" +
                "studentId=" + studentId +
                ", studentName='" + studentName + '\'' +
                ", grade='" + grade + '\'' +
                ", major='" + major + '\'' +
                ", clazz='" + clazz + '\'' +
                ", institute='" + institute + '\'' +
                ", tel='" + tel + '\'' +
                ", email='" + email + '\'' +
                ", pwd='" + maskedPwd + '\'' +
                ", cardId='" + cardId + '\'' +
                ", sex='" + sex + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        daVo daVo = (daVo) o;
        return Objects.equals(studentId, daVo.studentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId);
    }
}