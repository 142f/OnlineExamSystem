<template>
  <div class="oes-page">
    <PageHeader title="成绩分段" subtitle="选择一份作业，查看各分数段的学生分布" />

    <div class="oes-table-card">
      <el-table v-loading="loading" :data="pagination.records" size="medium">
        <el-table-column prop="source" label="作业名称" min-width="140" show-overflow-tooltip />
        <el-table-column prop="description" label="介绍" min-width="160" show-overflow-tooltip />
        <el-table-column prop="institute" label="学院" width="140" show-overflow-tooltip />
        <el-table-column prop="major" label="专业" width="140" show-overflow-tooltip />
        <el-table-column prop="grade" label="年级" width="90" align="center" />
        <el-table-column prop="examDate" label="日期" width="110" />
        <el-table-column prop="totalTime" label="时长" width="80" align="center" />
        <el-table-column prop="totalScore" label="总分" width="80" align="center" />
        <el-table-column label="操作" width="110" fixed="right">
          <template slot-scope="scope">
            <el-button type="text" icon="el-icon-pie-chart" @click="toPart(scope.row.examCode, scope.row.source)">查看分段</el-button>
          </template>
        </el-table-column>
        <template slot="empty">
          <EmptyState text="暂无作业数据" />
        </template>
      </el-table>

      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pagination.current"
        :page-sizes="[4, 8, 10]"
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
      pagination: { current: 1, total: null, size: 4, records: [] }
    }
  },
  created() {
    this.getExamInfo()
  },
  methods: {
    getExamInfo() {
      this.loading = true
      this.$axios(`/api/exams/${this.pagination.current}/${this.pagination.size}`)
        .then(res => {
          this.pagination = res.data.data
        })
        .catch(() => {})
        .finally(() => {
          this.loading = false
        })
    },
    handleSizeChange(val) {
      this.pagination.size = val
      this.getExamInfo()
    },
    handleCurrentChange(val) {
      this.pagination.current = val
      this.getExamInfo()
    },
    toPart(examCode, source) {
      this.$router.push({ path: '/scorePart', query: { examCode: examCode, source: source } })
    }
  }
}
</script>
