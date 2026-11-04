<template>
  <div class="oes-page">
    <PageHeader
      title="工作台"
      subtitle="作业、题库、成绩与学生数据总览"
    >
      <template #actions>
        <el-button type="primary" icon="el-icon-plus" @click="$router.push('/addExam')">新建作业</el-button>
      </template>
    </PageHeader>

    <div class="oes-stat-grid">
      <StatCard label="作业总数" :value="stats.exams" icon="el-icon-document" color="#3d7eff" />
      <StatCard label="学生人数" :value="stats.students" icon="el-icon-user" color="#22c55e" />
      <StatCard label="教师人数" :value="stats.teachers" icon="el-icon-s-custom" color="#f59e0b" />
      <StatCard label="留言条数" :value="stats.messages" icon="el-icon-chat-dot-round" color="#8b5cf6" />
    </div>

    <div class="dashboard">
      <div class="oes-table-card dashboard__table">
        <div class="dashboard__head">
          <h3>最近作业</h3>
          <el-button type="text" @click="$router.push('/selectExam')">查看全部</el-button>
        </div>
        <el-table v-loading="loading" :data="recentExams" size="small" :show-header="true">
          <el-table-column prop="source" label="作业名称" min-width="120" />
          <el-table-column prop="description" label="介绍" min-width="160" show-overflow-tooltip />
          <el-table-column prop="institute" label="学院" width="140" />
          <el-table-column prop="examDate" label="日期" width="110" />
          <el-table-column prop="totalScore" label="总分" width="80" align="center" />
        </el-table>
        <EmptyState v-if="!loading && recentExams.length === 0" text="暂无作业数据" />
      </div>

      <div class="dashboard__side">
        <div class="oes-card dashboard__quick">
          <h3>快捷入口</h3>
          <div class="quick-grid">
            <div class="quick-item" @click="$router.push('/selectExam')">
              <i class="el-icon-search"></i><span>作业查询</span>
            </div>
            <div class="quick-item" @click="$router.push('/addAnswer')">
              <i class="el-icon-edit-outline"></i><span>新增题目</span>
            </div>
            <div class="quick-item" @click="$router.push('/allStudentsGrade')">
              <i class="el-icon-data-line"></i><span>成绩查询</span>
            </div>
            <div class="quick-item" @click="$router.push('/studentManage')">
              <i class="el-icon-user"></i><span>学生管理</span>
            </div>
          </div>
        </div>

        <div class="oes-card dashboard__notice">
          <h3>使用提示</h3>
          <ul>
            <li>先创建题目 → 再组卷 → 最后发布作业</li>
            <li>学生提交后可在大题批改中评分</li>
            <li>成绩统计会随组卷变化自动重算</li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import PageHeader from '@/components/common/PageHeader'
import StatCard from '@/components/common/StatCard'
import EmptyState from '@/components/common/EmptyState'

export default {
  name: 'WelcomeView',
  components: { PageHeader, StatCard, EmptyState },
  data() {
    return {
      loading: false,
      exams: [],
      stats: { exams: '-', students: '-', teachers: '-', messages: '-' }
    }
  },
  computed: {
    recentExams() {
      return this.exams.slice(0, 5)
    }
  },
  created() {
    this.loadData()
  },
  methods: {
    loadData() {
      this.loading = true
      const safe = (promise, map) => promise.then(map).catch(() => '-')
      Promise.all([
        safe(this.$axios.get('/api/exams'), res => {
          this.exams = (res.data && res.data.data) || []
          return this.exams.length
        }),
        safe(this.$axios.get('/api/students/1/1/@/@/@/@/@/@'), res => res.data.data.total),
        safe(this.$axios.get('/api/teachers/1/1'), res => res.data.data.total),
        safe(this.$axios.get('/api/messages/1/1'), res => res.data.data.total)
      ]).then(([exams, students, teachers, messages]) => {
        this.stats = { exams, students, teachers, messages }
      }).finally(() => {
        this.loading = false
      })
    }
  }
}
</script>

<style scoped>
.dashboard {
  display: grid;
  grid-template-columns: minmax(0, 1fr) 320px;
  gap: 16px;
}

.dashboard__head {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 12px 0 4px;
}

.dashboard__head h3,
.dashboard__quick h3,
.dashboard__notice h3 {
  margin: 0;
  font-size: 15px;
  font-weight: 600;
  color: var(--oes-text);
}

.dashboard__side {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.dashboard__quick h3,
.dashboard__notice h3 {
  margin-bottom: 14px;
}

.quick-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 10px;
}

.quick-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 8px;
  padding: 16px 8px;
  font-size: 13px;
  color: var(--oes-text-secondary);
  background: #f8fafd;
  border-radius: var(--oes-radius-sm);
  cursor: pointer;
  transition: all 0.2s;
}

.quick-item:hover {
  color: var(--oes-primary);
  background: var(--oes-primary-light);
}

.quick-item i {
  font-size: 20px;
}

.dashboard__notice ul {
  margin: 0;
  padding-left: 18px;
  color: var(--oes-text-secondary);
  font-size: 13px;
  line-height: 2;
}

@media (max-width: 1100px) {
  .dashboard {
    grid-template-columns: 1fr;
  }
}
</style>
