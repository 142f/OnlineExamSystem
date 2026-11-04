package com.rabbiter.oes.serviceimpl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rabbiter.oes.entity.Student;
import com.rabbiter.oes.mapper.StudentMapper;
import com.rabbiter.oes.security.PasswordSupport;
import com.rabbiter.oes.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

//"": 如果name的值等于@，则name被重新赋值为空字符串""。
//    name: 如果name的值不等于@，则name的值保持不变。
//    那么实际上前端传的数据无意义
    @Override
    public IPage<Student> findAll(
            Page<Student> page, String name, String grade,
            String tel, String institute, String major, String clazz) {
        name = ("@".equals(name) ? "" : name);
        grade = ("@".equals(grade) ? "" : grade);
        tel = ("@".equals(tel) ? "" : tel);
        institute = ("@".equals(institute) ? "" : institute);
        major = ("@".equals(major) ? "" : major);
        clazz = ("@".equals(clazz) ? "" : clazz);
        return studentMapper.findAll(page, name, grade, tel, institute, major, clazz);
    }


    @Override
    public Student findById(Integer studentId) {
        return studentMapper.findById(studentId);
    }

    @Override
    public int deleteById(Integer studentId) {
        return studentMapper.deleteById(studentId);
    }

    @Override
    public int update(Student student) {
        // 兼容前端回传密文的情况，避免二次加密
        encodeIfPlaintext(student.getPwd(), student::setPwd);
        return studentMapper.update(student);
    }

    @Override
    public int updatePwd(Student student) {
        encodeIfPlaintext(student.getPwd(), student::setPwd);
        return studentMapper.updatePwd(student);
    }

    @Override
    public int add(Student student) {
        encodeIfPlaintext(student.getPwd(), student::setPwd);
        return studentMapper.add(student);
    }

    private void encodeIfPlaintext(String rawPwd, java.util.function.Consumer<String> setter) {
        if (rawPwd != null && !rawPwd.isEmpty() && !PasswordSupport.isEncoded(rawPwd)) {
            setter.accept(passwordEncoder.encode(rawPwd));
        }
    }
}
