package com.rabbiter.oes.vo;

import java.util.Objects;

/**
 * 管理员重置密码视图对象
 * 用于管理员重置用户密码的请求参数封装
 */
public class AdminResetPswVO {
    private Integer userId;      // 用户ID（学生/教师ID）
    private String userType;     // 用户类型：student/teacher
    private String newPassword;  // 新密码
    private String confirmPassword; // 确认密码

    /**
     * 默认构造函数
     */
    public AdminResetPswVO() {
    }

    /**
     * 基本构造函数
     */
    public AdminResetPswVO(Integer userId, String userType) {
        this.userId = userId;
        this.userType = userType;
    }

    /**
     * 验证密码一致性
     * @return 密码是否一致
     */
    public boolean isPasswordConsistent() {
        return Objects.equals(newPassword, confirmPassword);
    }

    /**
     * 验证密码强度（简单实现：长度至少6位）
     * @return 密码是否满足强度要求
     */
    public boolean isPasswordStrong() {
        return newPassword != null && newPassword.length() >= 6;
    }

    /**
     * 验证用户类型是否有效
     * @return 用户类型是否有效
     */
    public boolean isValidUserType() {
        return "student".equals(userType) || "teacher".equals(userType);
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType != null ? userType.trim() : null;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Override
    public String toString() {
        // 密码字段做脱敏处理
        String maskedPassword = newPassword != null ? "******" : null;
        return "AdminResetPswVO{" +
                "userId=" + userId +
                ", userType='" + userType + '\'' +
                ", newPassword='" + maskedPassword + '\'' +
                ", confirmPassword='" + maskedPassword + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdminResetPswVO that = (AdminResetPswVO) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(userType, that.userType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userType);
    }
}