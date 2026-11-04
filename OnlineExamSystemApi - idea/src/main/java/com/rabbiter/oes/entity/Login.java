package com.rabbiter.oes.entity;

import java.util.Objects;

public class Login {
    private Integer username;
    private String password;

    public Integer getUsername() {
        return username;
    }

    public void setUsername(Integer username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Login login = (Login) o;
        return Objects.equals(username, login.username) && Objects.equals(password, login.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }

    @Override
    public String toString() {
        return "Login{" +
                "username=" + username +
                ", password='" + password + '\'' +
                '}';
    }

    public Login(Integer username, String password) {
        this.username = username;
        this.password = password;
    }

    public Login() {
    }
}
