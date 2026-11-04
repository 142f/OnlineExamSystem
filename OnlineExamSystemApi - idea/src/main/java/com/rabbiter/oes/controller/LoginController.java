package com.rabbiter.oes.controller;

import com.rabbiter.oes.entity.Admin;
import com.rabbiter.oes.entity.ApiResult;
import com.rabbiter.oes.entity.Login;
import com.rabbiter.oes.entity.Student;
import com.rabbiter.oes.entity.Teacher;
import com.rabbiter.oes.security.LoginAttemptService;
import com.rabbiter.oes.security.LoginUser;
import com.rabbiter.oes.service.LoginService;
import com.rabbiter.oes.util.ApiResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.UUID;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private LoginAttemptService loginAttemptService;

    @PostMapping("/login")
    public ApiResult login(@RequestBody Login login, HttpServletRequest request, HttpServletResponse response) {
        Integer username = login.getUsername();
        String password = login.getPassword();
        String attemptKey = username + "@" + clientIp(request);

        // 连续失败过多：直接拒绝，避免在线暴力破解
        if (loginAttemptService.isBlocked(attemptKey)) {
            long minutes = Math.max(1, (loginAttemptService.remainingSeconds(attemptKey) + 59) / 60);
            return ApiResultHandler.buildApiResult(429, "登录失败次数过多，请 " + minutes + " 分钟后重试", null);
        }

        Admin adminRes = loginService.adminLogin(username, password);
        if (adminRes != null) {
            loginAttemptService.loginSucceeded(attemptKey);
            establishSession(request, new LoginUser(adminRes.getAdminId(), adminRes.getAdminName(), LoginUser.ROLE_ADMIN));
            writeCookies(response, "0");
            return ApiResultHandler.buildApiResult(200, "请求成功", adminRes);
        }

        Teacher teacherRes = loginService.teacherLogin(username, password);
        if (teacherRes != null) {
            loginAttemptService.loginSucceeded(attemptKey);
            establishSession(request, new LoginUser(teacherRes.getTeacherId(), teacherRes.getTeacherName(), LoginUser.ROLE_TEACHER));
            writeCookies(response, "1");
            return ApiResultHandler.buildApiResult(200, "请求成功", teacherRes);
        }

        Student studentRes = loginService.studentLogin(username, password);
        if (studentRes != null) {
            loginAttemptService.loginSucceeded(attemptKey);
            establishSession(request, new LoginUser(studentRes.getStudentId(), studentRes.getStudentName(), LoginUser.ROLE_STUDENT));
            writeCookies(response, "2");
            return ApiResultHandler.buildApiResult(200, "请求成功", studentRes);
        }
        loginAttemptService.loginFailed(attemptKey);
        return ApiResultHandler.buildApiResult(400, "请求失败", null);
    }

    @PostMapping("/logout")
    public ApiResult logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        // Cookie 的 value 传 null 在部分容器会抛异常，这里用空串 + maxAge=0 表示删除
        clearCookie(response, "rb_token");
        clearCookie(response, "rb_role");
        return ApiResultHandler.buildApiResult(200, "退出成功", null);
    }

    /**
     * 登录成功后重建会话（防止会话固定攻击），并把身份写入服务端会话。
     */
    private void establishSession(HttpServletRequest request, LoginUser loginUser) {
        HttpSession existing = request.getSession(false);
        if (existing != null) {
            existing.invalidate();
        }
        HttpSession session = request.getSession(true);
        session.setAttribute(LoginUser.SESSION_KEY, loginUser);
    }

    /**
     * 保留原有 Cookie 名称（前端路由依赖其存在性），但鉴权不再依据它们。
     * rb_token 改为随机串：原本写入的是身份证号（cardId），属敏感信息暴露。
     */
    private void writeCookies(HttpServletResponse response, String role) {
        Cookie tokenCookie = new Cookie("rb_token", UUID.randomUUID().toString().replace("-", ""));
        tokenCookie.setPath("/");
        Cookie roleCookie = new Cookie("rb_role", role);
        roleCookie.setPath("/");
        response.addCookie(tokenCookie);
        response.addCookie(roleCookie);
    }

    private void clearCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, "");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    private String clientIp(HttpServletRequest request) {
        String forwarded = request.getHeader("X-Forwarded-For");
        if (forwarded != null && !forwarded.trim().isEmpty()) {
            return forwarded.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}
