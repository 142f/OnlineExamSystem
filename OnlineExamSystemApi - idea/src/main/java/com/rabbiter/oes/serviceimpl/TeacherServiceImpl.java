package com.rabbiter.oes.serviceimpl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rabbiter.oes.entity.Teacher;
import com.rabbiter.oes.mapper.TeacherMapper;
import com.rabbiter.oes.security.PasswordSupport;
import com.rabbiter.oes.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public IPage<Teacher> findAll(Page<Teacher> page) {
        return teacherMapper.findAll(page);
    }

    @Override
    public List<Teacher> findAll() {
        Page<Teacher> teacherPage = new Page<>(0,9999);
        return teacherMapper.findAll(teacherPage).getRecords();
    }

    @Override
    public Teacher findById(Integer teacherId) {
        return teacherMapper.findById(teacherId);
    }

    @Override
    public int deleteById(Integer teacherId) {
        return teacherMapper.deleteById(teacherId);
    }

    @Override
    public int update(Teacher teacher) {
        encodeIfPlaintext(teacher);
        return teacherMapper.update(teacher);
    }

    @Override
    public int add(Teacher teacher) {
        teacher.setRole("1");
        encodeIfPlaintext(teacher);
        return teacherMapper.add(teacher);
    }

    /**
     * 明文密码转 BCrypt；已是密文则保持原样（兼容前端回传密文）。
     */
    private void encodeIfPlaintext(Teacher teacher) {
        String pwd = teacher.getPwd();
        if (pwd != null && !pwd.isEmpty() && !PasswordSupport.isEncoded(pwd)) {
            teacher.setPwd(passwordEncoder.encode(pwd));
        }
    }
}
