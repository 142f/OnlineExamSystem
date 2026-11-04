package com.rabbiter.oes.security;

import java.io.Serializable;

/**
 * 登录会话中的用户身份（保存在服务端 HttpSession 中，客户端不可伪造）。
 */
public class LoginUser implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String SESSION_KEY = "LOGIN_USER";

    public static final int ROLE_ADMIN = 0;
    public static final int ROLE_TEACHER = 1;
    public static final int ROLE_STUDENT = 2;

    private Integer id;

    private String name;

    private int role;

    public LoginUser() {
    }

    public LoginUser(Integer id, String name, int role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public boolean isAdmin() {
        return role == ROLE_ADMIN;
    }

    public boolean isTeacher() {
        return role == ROLE_TEACHER;
    }

    public boolean isStudent() {
        return role == ROLE_STUDENT;
    }

    @Override
    public String toString() {
        return "LoginUser{id=" + id + ", name='" + name + "', role=" + role + '}';
    }
}
