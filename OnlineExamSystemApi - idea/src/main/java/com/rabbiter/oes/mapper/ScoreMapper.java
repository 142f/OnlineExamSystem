package com.rabbiter.oes.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rabbiter.oes.entity.ExamManage;
import com.rabbiter.oes.entity.Score;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ScoreMapper {
    /**
     * @param score 添加一条成绩记录
     * @return
     */
    @Options(useGeneratedKeys = true, keyProperty = "scoreId")
    @Insert("insert into score(examCode,studentId,subject,ptScore,etScore,score,answerDate) values(#{examCode},#{studentId},#{subject},#{ptScore},#{etScore},#{score},#{answerDate})")
    int add(Score score);

    @Select("select scoreId,examCode,studentId,subject,ptScore,etScore,score,answerDate " +
            "from score order by scoreId desc")
    List<Score> findAll();

    // 分页 初版
    @Select("select scoreId,examCode,studentId,subject,ptScore,etScore,dScore,score,answerDate " +
            "from score where studentId = #{studentId} order by scoreId asc")
    IPage<Score> findById(Page<?> page, @Param("studentId") Integer studentId);

    // TODO: 2024/4/17  增加大题分数字段
//    @Select("select scoreId,examCode,studentId,subject,ptScore,etScore,dScore,score,answerDate " +
//            "from score ,exam_manage, paper_manage,da_question " +
//            "where studentId = #{studentId} " +
//            "and score.examCode=exam_manage.examCode " +
//            "and exam_manage.paperId = paper_manage.paperId " +
//            "AND paper_manage.questionId =da_question.questionId " +
//            " order by scoreId asc")
//    IPage<Score> findById(Page<?> page, @Param("studentId") Integer studentId);
//    @Select("SELECT score.scoreId, score.examCode, score.studentId, score.subject, score.ptScore, " +
//            "score.etScore, score.dScore, score.score, score.answerDate " +
//            "FROM score " +
//            "JOIN exam_manage ON score.examCode = exam_manage.examCode " +
//            "JOIN paper_manage ON exam_manage.paperId = paper_manage.paperId " +
//            "JOIN da_question ON paper_manage.questionId = da_question.questionId " +
//            "WHERE score.studentId = #{studentId} " +
//            "ORDER BY score.scoreId ASC")
//    IPage<Score> findById(Page<?> page, @Param("studentId") Integer studentId);


    // 不分页
//    @Select("select scoreId,examCode,studentId,subject,ptScore,etScore,score,answerDate from score where studentId = #{studentId}")
//    List<Score> findById(Integer studentId);

    /**
     * @return 查询每位学生的学科分数。 max其实是假的，为了迷惑老师，达到一次作业考生只参加了一次的效果
     */
//    @Select("select max(etScore) as etScore from score where examCode = #{examCode} group by studentId")

    @Select("select* from score where examCode = #{examCode} ")
    List<Score> findByExamCode(Integer examCode);
////////
    @Select("update score set score = #{totalScore}  where examCode = #{examCode} ")
    Integer updateMaxScore(ExamManage exammanage) ;

}
