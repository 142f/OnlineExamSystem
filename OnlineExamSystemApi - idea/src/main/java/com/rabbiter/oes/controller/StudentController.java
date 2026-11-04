package com.rabbiter.oes.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rabbiter.oes.entity.ApiResult;
import com.rabbiter.oes.entity.Student;
import com.rabbiter.oes.security.LoginUser;
import com.rabbiter.oes.service.StudentService;
import com.rabbiter.oes.util.ApiResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

// todo  /students/{page}/{size}/{name}/{grade}/{tel}/{institute}/{major}/{clazz}")
    @GetMapping("/students/{page}/{size}/{name}/{grade}/{tel}/{institute}/{major}/{clazz}")
    public ApiResult findAll(@PathVariable Integer page, @PathVariable Integer size,
                             @PathVariable  String name, @PathVariable String grade,
                             @PathVariable String tel, @PathVariable String institute,
                             @PathVariable String major, @PathVariable String clazz) {
        Page<Student> studentPage = new Page<>(page,size);
        IPage<Student> res = studentService.findAll(
                studentPage, name, grade, tel, institute, major, clazz
        );
        return  ApiResultHandler.buildApiResult(200,"分页查询所有学生stu",res);
    }


    @GetMapping("/student/{studentId}")
    public ApiResult findById(@PathVariable("studentId") Integer studentId) {
        Student res = studentService.findById(studentId);
        if (res != null) {
        return ApiResultHandler.buildApiResult(200,"请求成功",res);
        } else {
            return ApiResultHandler.buildApiResult(404,"查询的用户不存在",null);
        }
    }

    @DeleteMapping("/student/{studentId}")
    public ApiResult deleteById(@PathVariable("studentId") Integer studentId) {
        return ApiResultHandler.buildApiResult(200,"删除成功",studentService.deleteById(studentId));
    }

    @PutMapping("/studentPWD")
    public ApiResult updatePwd(@RequestBody Student student, HttpSession session) {
        Object attr = session == null ? null : session.getAttribute(LoginUser.SESSION_KEY);
        if (attr instanceof LoginUser) {
            LoginUser current = (LoginUser) attr;
            // 学生只能修改自己的密码，避免越权改他人密码
            if (current.isStudent() && !current.getId().equals(student.getStudentId())) {
                return ApiResultHandler.buildApiResult(403, "只能修改本人密码", null);
            }
        }
        studentService.updatePwd(student);
        return ApiResultHandler.buildApiResult(200,"密码更新成功",null);
    }
    /*
    * 修改条件必须在数据库范围内
    * */
    @PutMapping("/student")
    public ApiResult update(@RequestBody Student student) {
        int res = studentService.update(student);
        if (res != 0) {
            return ApiResultHandler.buildApiResult(200,"更新成功",res);
        }
        return ApiResultHandler.buildApiResult(400,"更新失败",res);
    }

    @PostMapping("/student")
    public ApiResult add(@RequestBody Student student) {
        int res = studentService.add(student);
        if (res == 1) {
            return ApiResultHandler.buildApiResult(200,"添加成功",null);
        }else {
            return ApiResultHandler.buildApiResult(400,"添加失败",null);
        }
    }




}
