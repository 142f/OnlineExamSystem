<template>
  <div class="oes-page">
    <PageHeader title="题目列表" subtitle="按作业、章节与题干检索题库内容">
      <template #actions>
        <el-button type="primary" icon="el-icon-plus" @click="$router.push('/addAnswer')">新增题目</el-button>
      </template>
    </PageHeader>

    <div class="oes-toolbar">
      <el-input v-model="subject" placeholder="作业名称" clearable />
      <el-input v-model="section" placeholder="章节" clearable />
      <el-input v-model="question" placeholder="题目内容关键字" clearable />
      <el-button type="primary" icon="el-icon-search" @click="search()">查询</el-button>
      <el-button icon="el-icon-refresh-left" @click="reset()">重置</el-button>
    </div>

    <div class="oes-table-card">
      <el-table v-loading="loading" :data="pagination.records" size="medium">
        <el-table-column prop="subject" label="作业名称" width="160" show-overflow-tooltip />
        <el-table-column prop="question" label="题目信息" min-width="320" show-overflow-tooltip />
        <el-table-column prop="section" label="章节" width="120" />
        <el-table-column prop="type" label="类型" width="110">
          <template slot-scope="scope">
            <span class="oes-tag">{{ scope.row.type }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="score" label="分值" width="80" align="center" />
        <el-table-column prop="level" label="难度" width="80" align="center" />
        <el-table-column label="操作" width="100" fixed="right">
          <template slot-scope="scope">
            <el-button type="text" icon="el-icon-edit" @click="toEdit(scope.row.type, scope.row.questionId)">编辑</el-button>
          </template>
        </el-table-column>
        <template slot="empty">
          <EmptyState text="题库中暂无匹配的题目" />
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
      section: '',
      subject: '',
      question: ''
    }
  },
  created() {
    this.getAnswerInfo()
  },
  methods: {
    toEdit(type, id) {
      this.$router.push({ path: '/editAnswerChildren', query: { type: type, questionId: id } })
    },
    getAnswerInfo(size, current) {
      if (typeof size === 'number' && !isNaN(size)) this.pagination.size = size
      this.pagination.current = (typeof current === 'number' && !isNaN(current)) ? current : 1
      const fmt = v => (v.trim() === '' ? '@' : v)
      this.loading = true
      this.$axios(
        `/api/answers/${this.pagination.current}/${this.pagination.size}/${fmt(this.subject)}/${fmt(this.section)}/${fmt(this.question)}`
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
      this.getAnswerInfo(this.pagination.size, 1)
    },
    reset() {
      this.subject = ''
      this.section = ''
      this.question = ''
      this.search()
    },
    handleSizeChange(val) {
      this.getAnswerInfo(val, 1)
    },
    handleCurrentChange(val) {
      this.getAnswerInfo(this.pagination.size, val)
    }
  }
}
</script>
