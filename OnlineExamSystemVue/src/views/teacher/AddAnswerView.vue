<!--
 * @Description: 
 * @Author: Rabbiter
-->
//获取试卷并跳转到添加题库
<template>
  <div class="exam oes-page">
    <PageHeader title="新增题目" subtitle="选择试卷后进入对应题型的录题页面" />
    <el-table :data="pagination.records" border>
      <el-table-column fixed="left" prop="source" label="作业名称" width="180"></el-table-column>
      <el-table-column prop="description" label="介绍" width="200"></el-table-column>
      <el-table-column prop="institute" label="所属学院" width="120"></el-table-column>
      <el-table-column prop="major" label="所属专业" width="200"></el-table-column>
      <el-table-column prop="grade" label="年级" width="100"></el-table-column>
      <el-table-column prop="examDate" label="作业提交日期" width="120"></el-table-column>
      <el-table-column prop="totalTime" label="持续时间" width="120"></el-table-column>
      <!-- <el-table-column prop="totalScore" label="总分" width="120"></el-table-column> -->
      <el-table-column prop="type" label="作业类型" width="120"></el-table-column>
      <el-table-column prop="tips" label="作业提示" width="400"></el-table-column>
      <el-table-column fixed="right" label="操作" width="180">
        <template slot-scope="scope">
          <el-button @click="add(scope.row.paperId,scope.row.source)" type="primary"> 录入题目</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="pagination.current"
      :page-sizes="[4, 8, 10]"
      :page-size="pagination.size"
      layout="total, sizes, prev, pager, next, jumper"
      :total="pagination.total" class="page">
    </el-pagination>
  </div>
</template>

<script>
import PageHeader from '@/components/common/PageHeader'

export default {
  components: { PageHeader },
  data() {
    return {
      form: {}, //保存点击以后当前试卷的信息
      pagination: { //分页后的作业信息
        current: 1, //当前页
        total: null, //记录条数
        size: 4 //每页条数
      },
    }
  },
  created() {
    this.getExamInfo()
  },
  methods: {
    getExamInfo() { //分页查询所有试卷信息
      this.$axios(`/api/exams/${this.pagination.current}/${this.pagination.size}`).then(res => {
        this.pagination = res.data.data
      }).catch(error => {
      })
    },
    //改变当前记录条数
    handleSizeChange(val) {
      this.pagination.size = val
      this.getExamInfo()
    },
    //改变当前页码，重新发送请求
    handleCurrentChange(val) {
      this.pagination.current = val
      this.getExamInfo()
    },
    add(paperId,source) { //增加题库
      this.$router.push({path:'/addAnswerChildren',query: {paperId: paperId,subject:source}})
    }
  },
};
</script>
<style lang="less" scoped>
.exam {
  padding: 0px 40px;
  .page {
    margin-top: 20px;
    display: flex;
    justify-content: center;
    align-items: center;
  }
  .edit{
    margin-left: 20px;
  }
}
</style>
