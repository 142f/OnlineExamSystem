package com.rabbiter.oes.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;

/**
 * 登录态 + 角色鉴权拦截器。
 *
 * 身份来源为服务端 HttpSession（登录时写入），不再信任前端可伪造的 rb_role Cookie。
 *
 * 权限模型：
 *  - 管理员(0)：全部接口
 *  - 教师(1)：除“管理员专属”外的全部接口
 *  - 学生(2)：只读考试/试卷/题库，以及仅限本人的成绩与资料；不能管理学生、教师，不能改卷
 *
 * 未登录返回 401，越权返回 403，均为纯文本，便于前端直接展示。
 */
public class RoleAuthInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(RoleAuthInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            return true;
        }

        HttpSession session = request.getSession(false);
        LoginUser user = session == null ? null : (LoginUser) session.getAttribute(LoginUser.SESSION_KEY);
        if (user == null) {
            return reject(response, HttpServletResponse.SC_UNAUTHORIZED, "登录状态已失效，请重新登录");
        }

        String path = resolvePath(request);
        String method = request.getMethod();
        if (!isAllowed(user, method, path)) {
            log.warn("拦截越权请求 user={} {} {}", user, method, request.getRequestURI());
            return reject(response, HttpServletResponse.SC_FORBIDDEN, "无权限访问该资源");
        }
        return true;
    }

    private String resolvePath(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String context = request.getContextPath();
        if (context != null && !context.isEmpty() && uri.startsWith(context)) {
            uri = uri.substring(context.length());
        }
        return uri;
    }

    private boolean isAllowed(LoginUser user, String method, String path) {
        if (user.isAdmin()) {
            return true;
        }
        // 管理员专属：管理员/教师账号管理
        if (isAdminOnly(path)) {
            return false;
        }
        // 教师专属：组卷、改卷、题库维护、学生管理、全员成绩
        if (isTeacherScope(method, path)) {
            return user.isTeacher();
        }
        // 个人资源：学生只能访问自己的
        if (path.startsWith("/student/") || path.startsWith("/score/")) {
            return user.isTeacher() || isSelf(path, user);
        }
        return true;
    }

    private boolean isAdminOnly(String path) {
        return "/admins".equals(path)
                || "/admin".equals(path)
                || path.startsWith("/admin/")
                || "/teachers".equals(path)
                || "/teacher".equals(path)
                || path.startsWith("/teacher/");
    }

    private boolean isTeacherScope(String method, String path) {
        if ("/item".equals(path) || "/paperManage".equals(path) || "/examManagePaperId".equals(path)) {
            return true;
        }
        if (path.startsWith("/paper/") && !"GET".equalsIgnoreCase(method)) {
            return true;
        }
        if ("/exam".equals(path) && !"GET".equalsIgnoreCase(method)) {
            return true;
        }
        if (path.startsWith("/exam/") && !"GET".equalsIgnoreCase(method)) {
            return true;
        }
        if ("/student".equals(path) || "/students".equals(path) || path.startsWith("/students/")) {
            return true;
        }
        if ("/fillQuestion".equals(path) || "/fillQuestion2".equals(path)
                || "/judgeQuestion".equals(path) || "/MultiQuestion".equals(path)) {
            return true;
        }
        if (path.startsWith("/edit")) {
            return true;
        }
        if (path.startsWith("/answers/")) {
            return true;
        }
        if ("/scores".equals(path) || path.startsWith("/scores/")) {
            return true;
        }
        return path.startsWith("/daQuestionallTea") || "/daQuestionTea".equals(path);
    }

    /**
     * 判断路径最后一段是否等于当前用户 id（用于 /student/{id}、/score/{...}/{studentId}）。
     */
    private boolean isSelf(String path, LoginUser user) {
        int idx = path.lastIndexOf('/');
        String last = idx >= 0 ? path.substring(idx + 1) : path;
        return String.valueOf(user.getId()).equals(last);
    }

    private boolean reject(HttpServletResponse response, int status, String message) throws Exception {
        response.setStatus(status);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8");
        response.getWriter().write(message);
        return false;
    }
}
