<template>
  <div class="oes-page">
    <PageHeader title="学生成绩" subtitle="按学院 / 专业 / 班级检索学生，查看个人成绩明细" />

    <div class="oes-toolbar">
      <el-input v-model="condition.name" placeholder="姓名" clearable />
      <el-input v-model="condition.institute" placeholder="学院" clearable />
      <el-input v-model="condition.major" placeholder="专业" clearable />
      <el-input v-model="condition.grade" placeholder="年级" clearable />
      <el-input v-model="condition.clazz" placeholder="班级" clearable />
      <el-input v-model="condition.tel" placeholder="联系方式" clearable />
      <el-button type="primary" icon="el-icon-search" @click="search()">查询</el-button>
      <el-button icon="el-icon-refresh-left" @click="reset()">重置</el-button>
    </div>

    <div class="oes-table-card">
      <el-table v-loading="loading" :data="pagination.records" size="medium">
        <el-table-column prop="studentName" label="姓名" width="120">
          <template slot-scope="scope">
            <span class="cell-strong">{{ scope.row.studentName }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="institute" label="学院" min-width="150" show-overflow-tooltip />
        <el-table-column prop="major" label="专业" min-width="150" show-overflow-tooltip />
        <el-table-column prop="grade" label="年级" width="90" align="center" />
        <el-table-column prop="clazz" label="班级" width="90" align="center" />
        <el-table-column prop="sex" label="性别" width="80" align="center" />
        <el-table-column prop="tel" label="联系方式" width="140" />
        <el-table-column label="操作" width="120" fixed="right">
          <template slot-scope="scope">
            <el-button type="text" icon="el-icon-data-line" @click="checkGrade(scope.row.studentId)">查看成绩</el-button>
          </template>
        </el-table-column>
        <template slot="empty">
          <EmptyState text="没有符合条件的学生" />
        </template>
      </el-table>

      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pagination.current"
        :page-sizes="[6, 10]"
        :page-size="pagination.size"
        layout="total, sizes, prev, pager, next, jumper"
        :total="pagination.total">
      </el-pagination>
    </div>
  </div>
</template>

<script>
import PageHeader from '@/components/common/PageHeader'
import EmptyState from '@/components/common/EmptyState'

export default {
  components: { PageHeader, EmptyState },
  data() {
    return {
      loading: false,
      pagination: { current: 1, total: null, size: 6, records: [] },
      condition: { name: '', tel: '', grade: '', clazz: '', major: '', institute: '' }
    }
  },
  created() {
    this.getStudentGrade()
  },
  methods: {
    getStudentGrade() {
      const fmt = v => (v.trim() === '' ? '@' : v)
      const { name, grade, tel, institute, major, clazz } = this.condition
      this.loading = true
      this.$axios(
        `/api/students/${this.pagination.current}/${this.pagination.size}/${fmt(name)}/${fmt(grade)}/${fmt(tel)}/${fmt(institute)}/${fmt(major)}/${fmt(clazz)}`
      )
        .then(res => {
          this.pagination = res.data.data
        })
        .catch(() => {})
        .finally(() => {
          this.loading = false
        })
    },
    search() {
      this.pagination.current = 1
      this.getStudentGrade()
    },
    reset() {
      this.condition = { name: '', tel: '', grade: '', clazz: '', major: '', institute: '' }
      this.search()
    },
    handleSizeChange(val) {
      this.pagination.size = val
      this.getStudentGrade()
    },
    handleCurrentChange(val) {
      this.pagination.current = val
      this.getStudentGrade()
    },
    checkGrade(studentId) {
      this.$router.push({ path: '/grade', query: { studentId: studentId } })
    }
  }
}
</script>
