package com.rabbiter.oes.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rabbiter.oes.entity.DaQuestion;
import com.rabbiter.oes.entity.Score;
import com.rabbiter.oes.entity.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DaQuestionMapper {
    // TODO: 2024/3/26   还可以修改为不含自己
/*    @Select("SELECT * FROM da_question " +
            "JOIN da ON da_question.questionId = da.questionId " +
            "JOIN student ON da.studentId = student.studentId " +
            "WHERE student.institute = (SELECT institute FROM student WHERE studentId = #{studentId})")
    IPage<DaQuestion> findAll(Page page, @Param("studentId") Integer studentId);*/

    // TODO: 2024/4/17 添加标识符后
    //  todo还可以修改为不含自己 ，评分搜索除了自己的所有 student.studentId!=#{studentId}
    @Select("SELECT * FROM da_question " +
            "JOIN da ON da_question.questionId = da.questionId " +
            "JOIN student ON da.studentId = student.studentId and student.studentId!=#{studentId} " +
            "WHERE student.institute = (SELECT institute FROM student WHERE studentId = #{studentId}) " +
            "AND da.isTag = 0 ")
    IPage<DaQuestion> findAll(Page page, @Param("studentId") Integer studentId);

//查看自己成绩  student.studentId=#{studentId}
    //WHERE student.institute = (SELECT institute FROM student WHERE studentId = #{studentId})  必须同一个学院
    @Select("SELECT * FROM da_question " +
            "JOIN da ON da_question.questionId = da.questionId " +
            "JOIN student ON da.studentId = student.studentId and student.studentId=#{studentId} " +
            "WHERE student.institute = (SELECT institute FROM student WHERE studentId = #{studentId}) " +
            "AND da.isTag = 1 ")
    IPage<DaQuestion> findAll2(Page page, @Param("studentId") Integer studentId);

    // TODO: 教师重新评分
    @Update("update da set isTag =3 " +
            "where studentId = #{studentId} and questionId= #{questionId} ")
    int update3(DaQuestion daQuestion);

    @Select("SELECT * FROM da_question " +
            "JOIN da ON da_question.questionId = da.questionId " +
            "JOIN student ON da.studentId = student.studentId  " +
            "WHERE  da.isTag = 3 ")
    IPage<DaQuestion> findAllTea(Page page, @Param("studentId") Integer studentId);


    /*
    // TODO: 2024/4/17 添加标识符后   还可以修改为不含自己
    @Select("SELECT * FROM da_question " +
            "JOIN da ON da_question.questionId = da.questionId " +
            "JOIN student ON da.studentId = student.studentId  and student.studentId!=#{studentId}" +
            "WHERE student.institute = (SELECT institute FROM student WHERE studentId = #{studentId}) " +
            "AND da.isTag = 0 ")
    IPage<DaQuestion> findAll(Page page, @Param("studentId") Integer studentId);

*/


    @Select("select * from da_question where questionId in (select questionId from paper_manage " +
            "where questionType = 4 and paperId = #{paperId})")
    List<DaQuestion> findByIdAndType(Integer paperId);


    @Select("SELECT * " +
            "FROM da_question " +
            "JOIN da ON da_question.questionId = da.questionId " +
            "JOIN student ON da.studentId = student.studentId " +
            "WHERE da.studentId = #{studentIdd} AND da.questionId = #{questionIdd}")
    DaQuestion findOnlyQuestionId(@Param("studentIdd") Integer studentIdd,
                                  @Param("questionIdd") Integer questionIdd);


    /*   @Update("update da set answerMe = #{answerMe}" +
               "where studentId = #{studentId} and questionId= #{questionId} ")
       void update2(@Param("studentId") Integer studentId,
                   @Param("questionId") Integer questionId,
                   @Param("answerMe") String answerMe    );*/
// TODO: 2024/4/17     新增或者修改 没有记录时新增  ，有该记录-根据主键判断 时 修改数据answerMe
//    @Update("INSERT INTO da (studentId, questionId, answerMe, isTag) " +
//            "VALUES ( #{examCode},  #{studentId}, #{questionId}, #{answerMe}, 0) " +
//            "ON DUPLICATE KEY UPDATE answerMe = #{answerMe}")
//    void update2(@Param("studentId") Integer studentId,
//                 @Param("questionId") Integer questionId,
//                 @Param("answerMe") String answerMe,
//                 @Param("examCode") Integer examCode );

    @Update("INSERT INTO da (studentId, questionId, answerMe, isTag) " +
            "VALUES (   #{studentId}, #{questionId}, #{answerMe}, 0) " +
            "ON DUPLICATE KEY UPDATE answerMe = #{answerMe}")
    void update2(@Param("studentId") Integer studentId,
                 @Param("questionId") Integer questionId,
                 @Param("answerMe") String answerMe );


    /**
     * 查询最后一条questionId
     *
     * @return DaQuestion
     */

    @Update("update da set scoreMe = #{scoreMe} ,isTag =1 " +
            "where studentId = #{studentId} and questionId= #{questionId} ")
    int update(DaQuestion daQuestion);

    // todo更新大题分数
//    @Update({
//            "UPDATE score ",
//            "SET dScore = (",
//            "    SELECT SUM(dq.ScoreMe)",
//            "    FROM da dq",
//            "    JOIN paper_manage pm ON dq.questionId = pm.questionId",
//            "    JOIN exam_manage em ON pm.paperId = em.paperId",
//            "    WHERE da.studentId = #{studentId}",
//            "    AND da.examCode = #{examCode}",
//            "    AND pm.questionType = 4", // 新增的条件
//            ")",
//            "WHERE score.studentId = #{studentId}",
//            "AND score.examCode = #{examCode}"
//    })
//    void updateScore(@Param("studentId") Integer studentId, @Param("examCode") Integer examCode);

//    @Update({
//            "UPDATE score ",
//            "SET dScore = (",
//            "    SELECT SUM(da.ScoreMe)",
//            "    FROM da, paper_manage pm, exam_manage em, score",
//            "    WHERE score.examCode = em.examCode",
//            "    AND em.paperId = pm.paperId",
//            "    AND da.questionId = pm.questionId",
//            "    AND pm.questionType = 4", // 这里应该是 paper_manage 的别名
//            "    AND score.studentId = #{studentId}",
//            "    AND score.examCode = #{examCode}",
//            ")",
//    })
//    void updateScore(@Param("studentId") Integer studentId, @Param("examCode") Integer examCode);

    // TODO: 2024/4/17 数据出错
    /**
     * 汇总某学生某次作业的主观题得分。
     *
     * 原实现依赖 da.examCode，但 da 表的该列从未被写入（insert 语句不含 examCode），
     * 导致子查询恒为 NULL、dScore 永远算不出来。这里改为通过
     * paper_manage(questionType=4) -> exam_manage.paperId 反查该作业包含的大题。
     */
    @Update({
            "UPDATE score",
            "SET dScore = (",
            "    SELECT COALESCE(SUM(CAST(NULLIF(TRIM(d.scoreMe), '') AS SIGNED)), 0)",
            "    FROM da d",
            "    WHERE d.studentId = #{studentId}",
            "      AND d.questionId IN (",
            "          SELECT pm.questionId FROM paper_manage pm",
            "          JOIN exam_manage em ON em.paperId = pm.paperId",
            "          WHERE em.examCode = #{examCode} AND pm.questionType = 4",
            "      )",
            ")",
            "WHERE score.examCode = #{examCode}",
            "  AND score.studentId = #{studentId}"
    })
    void updateScore(@Param("studentId") Integer studentId, @Param("examCode") Integer examCode);



//    @Update({
//            "UPDATE score",
//            "SET dScore = (",
//            "    SELECT SUM(da.ScoreMe)",
//            "    FROM da",
//            "    JOIN paper_manage pm ON da.questionId = pm.questionId",
//            "    JOIN exam_manage em ON em.paperId = pm.paperId",
//            "    WHERE",
//            "        pm.questionType = 4",
//            "        AND em.examCode = score.examCode", // 这里要确保是em.examCode而不是score.examCode
//            "        AND score.studentId = #{studentId}",
//            ")",
//            "WHERE score.examCode = #{examCode}"
//    })

    //    @Select("select questionId from da_question order by questionId desc limit 1")
//    DaQuestion findOnlyQuestionId();
    @Options(useGeneratedKeys = true, keyProperty = "questionId")
    @Insert("insert into da_question(subject,question,answer,analysis,level,section) values " +
            "(#{subject},#{question},#{answer},#{analysis},#{level},#{section})")
    int add(DaQuestion daQuestion);

    @Select("select questionId from da_question where subject = #{subject} order by rand() desc limit #{pageNo}")
    List<Integer> findBySubject(@Param("subject") String subject, @Param("pageNo") Integer pageNo);

    /**
     * 查询大题题库中最后一条记录的 questionId（前端新增大题后需要用它建立试卷关联）。
     */
    @Select("select questionId from da_question order by questionId desc limit 1")
    DaQuestion findLastQuestionId();

    @Update("update da_question set section = #{section}, question = #{question}, answer = #{answer}, level = #{level}, analysis = #{analysis} where questionId = #{questionId}")
    int edit(DaQuestion daQuestion);
}
