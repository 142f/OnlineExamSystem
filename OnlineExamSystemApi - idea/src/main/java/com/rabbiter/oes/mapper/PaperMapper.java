package com.rabbiter.oes.mapper;

import com.rabbiter.oes.entity.ExamManage;
import com.rabbiter.oes.entity.PaperManage;
import com.rabbiter.oes.vo.PaperTotalScoreVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PaperMapper {
    @Select("select paperId, questionType,questionId from paper_manage")
    List<PaperManage> findAll();

    @Select("select paperId, questionType,questionId from paper_manage where paperId = #{paperId}")
    List<PaperManage> findById(Integer paperId);

    @Insert("insert into paper_manage(paperId,questionType,questionId) values " +
            "(#{paperId},#{questionType},#{questionId})")
    int add(PaperManage paperManage);

/////////
    @Select("select * from exam_manage where paperId = #{paperId}")
    ExamManage findByIdToExcode(@Param("paperId") Integer paperId);


    @Update("update exam_manage set totalScore = #{totalScore} where examCode = #{examCode}")
    int updateMaxScore(ExamManage exammanage);


    @Delete("delete from paper_manage where paperId = #{paperId} and questionType = #{type} and questionId = #{questionId}")
    void delete(@Param("paperId") String paperId, @Param("type") String type, @Param("questionId") String questionId);



    /**
     * 根据试卷id删除题目关联
     *
     * @param paperId 试卷id
     */
    @Delete("DELETE FROM paper_manage WHERE paperId = #{paperId}")
    void deleteByPaperId(@Param("paperId") Integer paperId);

    /**
     * 计算单份试卷的满分。
     * 计分规则与原 PaperService#getMaxScore 保持一致：
     * 选择/填空/判断 每题 2 分，大题每题 5 分；仅统计题库中仍然存在的题目。
     */
    @Select("select sum(case when pm.questionType = 4 then 5 else 2 end) " +
            "from paper_manage pm where pm.paperId = #{paperId} and (" +
            "  (pm.questionType = 1 and exists (select 1 from multi_question q where q.questionId = pm.questionId)) or " +
            "  (pm.questionType = 2 and exists (select 1 from fill_question q where q.questionId = pm.questionId)) or " +
            "  (pm.questionType = 3 and exists (select 1 from judge_question q where q.questionId = pm.questionId)) or " +
            "  (pm.questionType = 4 and exists (select 1 from da_question q where q.questionId = pm.questionId)))")
    Integer findTotalScoreByPaperId(@Param("paperId") Integer paperId);

    /**
     * 批量计算多份试卷满分（替代逐卷 4 次查询的 N+1 模式）。
     */
    @Select("<script>select pm.paperId as paperId, " +
            "sum(case when pm.questionType = 4 then 5 else 2 end) as totalScore " +
            "from paper_manage pm where pm.paperId in " +
            "<foreach collection='paperIds' item='id' open='(' separator=',' close=')'>#{id}</foreach> " +
            "and (" +
            "  (pm.questionType = 1 and exists (select 1 from multi_question q where q.questionId = pm.questionId)) or " +
            "  (pm.questionType = 2 and exists (select 1 from fill_question q where q.questionId = pm.questionId)) or " +
            "  (pm.questionType = 3 and exists (select 1 from judge_question q where q.questionId = pm.questionId)) or " +
            "  (pm.questionType = 4 and exists (select 1 from da_question q where q.questionId = pm.questionId))) " +
            "group by pm.paperId</script>")
    List<PaperTotalScoreVO> findTotalScores(@Param("paperIds") List<Integer> paperIds);
}
