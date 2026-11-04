package com.rabbiter.oes.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rabbiter.oes.entity.FillQuestion;
import org.apache.ibatis.annotations.*;

import java.util.List;

//填空题
@Mapper
public interface FillQuestionMapper {

    @Select("select * from fill_question " +
            "where questionId in (select questionId from paper_manage " +
            "where questionType = 2 and paperId = #{paperId})")
    List<FillQuestion> findByIdAndType(Integer paperId);

    @Select("select * from fill_question")
    IPage<FillQuestion> findAll(Page page);

    /**
     * 查询最后一条questionId
     * @return FillQuestion
     */
    @Select("select questionId from fill_question order by questionId desc limit 1")
    FillQuestion findOnlyQuestionId();

    //主键主动递增
    @Options(useGeneratedKeys = true,keyProperty ="questionId" )
    @Insert("insert into fill_question(subject,question,answer,analysis,level,section) values " +
            "(#{subject},#{question},#{answer},#{analysis},#{level},#{section})")
    int add(FillQuestion fillQuestion);

    // TODO: 2024/4/18 大题
    @Options(useGeneratedKeys = true,keyProperty ="questionId" )
    @Insert("insert into da_question(subject,question,answer,analysis,level,section) values " +
            "(#{subject},#{question},#{answer},#{analysis},#{level},#{section})")
    int add2(FillQuestion fillQuestion);


    @Select("select questionId from fill_question where subject = #{subject} order by rand() desc limit #{pageNo}")
    List<Integer> findBySubject(@Param("subject") String subject, @Param("pageNo") Integer pageNo);

   //大题
    @Select("select questionId from da_question where subject = #{subject} order by rand() desc limit #{pageNo}")
    List<Integer> findBySubject2(@Param("subject") String subject, @Param("pageNo") Integer pageNo);

    @Update("update fill_question set section = #{section}, question = #{question}, answer = #{answer}, level = #{level}, analysis = #{analysis} where questionId = #{questionId}")
    int edit(FillQuestion fillQuestion);
}
