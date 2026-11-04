package com.rabbiter.oes.serviceimpl;

import com.rabbiter.oes.entity.Admin;
import com.rabbiter.oes.mapper.AdminMapper;
import com.rabbiter.oes.security.PasswordSupport;
import com.rabbiter.oes.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<Admin> findAll() {
        return adminMapper.findAll();
    }

    @Override
    public Admin findById(Integer adminId) {
        return adminMapper.findById(adminId);
    }

    @Override
    public int deleteById(int adminId) {
        return adminMapper.deleteById(adminId);
    }

    @Override
    public int update(Admin admin) {
        return adminMapper.update(admin);
    }

    @Override
    public int add(Admin admin) {
        return 0;
    }

    @Override
    public Object resetPsw(Integer adminId, String newPsw, String oldPsw) {
        Admin admin = findById(adminId);
        if (admin == null) {
            return "管理员不存在";
        }
        if (!matches(oldPsw, admin.getPwd())) {
            return "原密码错误";
        }
        admin.setPwd(passwordEncoder.encode(newPsw));
        update(admin);
        return true;
    }

    /**
     * 兼容历史明文密码的校验。
     */
    private boolean matches(String rawPassword, String storedPassword) {
        if (rawPassword == null || storedPassword == null) {
            return false;
        }
        if (PasswordSupport.isEncoded(storedPassword)) {
            return passwordEncoder.matches(rawPassword, storedPassword);
        }
        return storedPassword.equals(rawPassword);
    }


}
