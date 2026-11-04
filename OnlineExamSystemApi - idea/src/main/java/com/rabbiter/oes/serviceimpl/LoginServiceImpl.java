package com.rabbiter.oes.serviceimpl;

import com.rabbiter.oes.entity.Admin;
import com.rabbiter.oes.entity.Student;
import com.rabbiter.oes.entity.Teacher;
import com.rabbiter.oes.mapper.LoginMapper;
import com.rabbiter.oes.security.PasswordSupport;
import com.rabbiter.oes.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

/**
 * 登录校验。
 *
 * 密码使用 BCrypt 校验；对历史明文数据做了向后兼容：
 * 当库中仍是明文且比对成功时，自动把该条记录升级为 BCrypt 密文（一次性平滑迁移）。
 */
@Service
public class LoginServiceImpl implements LoginService {

    private static final Logger log = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Autowired
    private LoginMapper loginMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Admin adminLogin(Integer username, String password) {
        if (username == null || password == null) {
            return null;
        }
        String stored = loginMapper.findAdminPwd(username);
        if (!matches(password, stored)) {
            return null;
        }
        upgrade(password, stored, encoded -> loginMapper.updateAdminPwd(username, encoded));
        return loginMapper.findAdminById(username);
    }

    @Override
    public Teacher teacherLogin(Integer username, String password) {
        if (username == null || password == null) {
            return null;
        }
        String stored = loginMapper.findTeacherPwd(username);
        if (!matches(password, stored)) {
            return null;
        }
        upgrade(password, stored, encoded -> loginMapper.updateTeacherPwd(username, encoded));
        return loginMapper.findTeacherById(username);
    }

    @Override
    public Student studentLogin(Integer username, String password) {
        if (username == null || password == null) {
            return null;
        }
        String stored = loginMapper.findStudentPwd(username);
        if (!matches(password, stored)) {
            return null;
        }
        upgrade(password, stored, encoded -> loginMapper.updateStudentPwd(username, encoded));
        return loginMapper.findStudentById(username);
    }

    /**
     * 校验密码：BCrypt 密文用 encoder 比对，历史明文用等值比对。
     */
    private boolean matches(String rawPassword, String storedPassword) {
        if (storedPassword == null) {
            return false;
        }
        if (PasswordSupport.isEncoded(storedPassword)) {
            return passwordEncoder.matches(rawPassword, storedPassword);
        }
        return storedPassword.equals(rawPassword);
    }

    /**
     * 历史明文密码在登录成功后升级为 BCrypt 密文。
     */
    private void upgrade(String rawPassword, String storedPassword, Consumer<String> updater) {
        if (PasswordSupport.isEncoded(storedPassword)) {
            return;
        }
        try {
            updater.accept(passwordEncoder.encode(rawPassword));
            log.info("检测到历史明文密码，已升级为 BCrypt 密文");
        } catch (Exception e) {
            // 升级失败不影响本次登录
            log.warn("密码升级失败：{}", e.getMessage());
        }
    }
}
