package com.rabbiter.oes.mapper;

import com.rabbiter.oes.entity.Admin;
import com.rabbiter.oes.entity.Student;
import com.rabbiter.oes.entity.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 登录相关查询。
 *
 * 注意：密码不再参与 SQL 比较（避免明文比对与 SQL 侧泄露），
 * 改为取出密文后由 PasswordEncoder 在 Java 侧校验。
 */
@Mapper
public interface LoginMapper {

    @Select("select adminId, adminName, sex, tel, email, cardId, role from `admin` where adminId = #{id}")
    Admin findAdminById(@Param("id") Integer id);

    @Select("select pwd from `admin` where adminId = #{id}")
    String findAdminPwd(@Param("id") Integer id);

    @Update("update `admin` set pwd = #{pwd} where adminId = #{id}")
    int updateAdminPwd(@Param("id") Integer id, @Param("pwd") String pwd);

    @Select("select teacherId, teacherName, institute, sex, tel, email, cardId, type, role " +
            "from teacher where teacherId = #{id}")
    Teacher findTeacherById(@Param("id") Integer id);

    @Select("select pwd from teacher where teacherId = #{id}")
    String findTeacherPwd(@Param("id") Integer id);

    @Update("update teacher set pwd = #{pwd} where teacherId = #{id}")
    int updateTeacherPwd(@Param("id") Integer id, @Param("pwd") String pwd);

    @Select("select studentId, studentName, grade, major, clazz, institute, tel, email, " +
            "cardId, sex, role from student where studentId = #{id}")
    Student findStudentById(@Param("id") Integer id);

    @Select("select pwd from student where studentId = #{id}")
    String findStudentPwd(@Param("id") Integer id);

    @Update("update student set pwd = #{pwd} where studentId = #{id}")
    int updateStudentPwd(@Param("id") Integer id, @Param("pwd") String pwd);
}
