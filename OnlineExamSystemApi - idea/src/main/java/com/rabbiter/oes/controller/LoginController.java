package com.rabbiter.oes.controller;

import com.rabbiter.oes.entity.Admin;
import com.rabbiter.oes.entity.ApiResult;
import com.rabbiter.oes.entity.Login;
import com.rabbiter.oes.entity.Student;
import com.rabbiter.oes.entity.Teacher;
import com.rabbiter.oes.security.LoginUser;
import com.rabbiter.oes.service.LoginService;
import com.rabbiter.oes.util.ApiResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ApiResult login(@RequestBody Login login, HttpServletRequest request, HttpServletResponse response) {
        Integer username = login.getUsername();
        String password = login.getPassword();

        Admin adminRes = loginService.adminLogin(username, password);
        if (adminRes != null) {
            establishSession(request, new LoginUser(adminRes.getAdminId(), adminRes.getAdminName(), LoginUser.ROLE_ADMIN));
            writeCookies(response, adminRes.getCardId(), "0");
            return ApiResultHandler.buildApiResult(200, "请求成功", adminRes);
        }

        Teacher teacherRes = loginService.teacherLogin(username, password);
        if (teacherRes != null) {
            establishSession(request, new LoginUser(teacherRes.getTeacherId(), teacherRes.getTeacherName(), LoginUser.ROLE_TEACHER));
            writeCookies(response, teacherRes.getCardId(), "1");
            return ApiResultHandler.buildApiResult(200, "请求成功", teacherRes);
        }

        Student studentRes = loginService.studentLogin(username, password);
        if (studentRes != null) {
            establishSession(request, new LoginUser(studentRes.getStudentId(), studentRes.getStudentName(), LoginUser.ROLE_STUDENT));
            writeCookies(response, studentRes.getCardId(), "2");
            return ApiResultHandler.buildApiResult(200, "请求成功", studentRes);
        }
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
     * 保留原有 Cookie（前端路由与用户名展示依赖），但鉴权不再依据它们。
     */
    private void writeCookies(HttpServletResponse response, String token, String role) {
        Cookie tokenCookie = new Cookie("rb_token", token);
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
}
