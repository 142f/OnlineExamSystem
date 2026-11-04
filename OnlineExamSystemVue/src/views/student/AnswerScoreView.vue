<template>
  <div class="oes-page">
    <PageHeader title="本次作业成绩" subtitle="提交成功，以下为本次答题结果" />

    <div class="score-card">
      <div class="score-ring">
        <span class="score-ring__value">{{ score }}</span>
        <span class="score-ring__unit">分</span>
      </div>
      <p class="score-tip">成绩已记录，可在「我的分数」中查看全部记录</p>

      <div class="score-meta">
        <div class="score-meta__item">
          <span class="label">开始时间</span>
          <span class="value">{{ startTime || '-' }}</span>
        </div>
        <div class="score-meta__item">
          <span class="label">结束时间</span>
          <span class="value">{{ endTime || '-' }}</span>
        </div>
      </div>

      <div class="score-actions">
        <el-button icon="el-icon-back" @click="$router.push('/student')">返回作业中心</el-button>
        <el-button type="primary" icon="el-icon-data-line" @click="$router.push('/scoreTable')">查看我的分数</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import PageHeader from '@/components/common/PageHeader'

export default {
  components: { PageHeader },
  data() {
    return {
      isTransition: false,
      score: 0,
      startTime: null,
      endTime: null
    }
  },
  created() {
    this.transiton()
    this.getScore()
  },
  methods: {
    transiton() {
      setTimeout(() => {
        this.isTransition = true
      }, 1000)
    },
    getScore() {
      this.score = this.$route.query.score || 0
      this.startTime = this.$route.query.startTime
      this.endTime = this.$route.query.endTime
    }
  }
}
</script>

<style scoped>
.score-card {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 48px 24px;
  background: var(--oes-surface);
  border-radius: var(--oes-radius);
  box-shadow: var(--oes-shadow-sm);
}

.score-ring {
  display: flex;
  align-items: baseline;
  justify-content: center;
  width: 180px;
  height: 180px;
  border-radius: 50%;
  background: radial-gradient(circle at 50% 30%, #eaf1ff 0%, #f7faff 70%);
  border: 8px solid #dbe7ff;
  color: var(--oes-primary);
}

.score-ring__value {
  font-size: 56px;
  font-weight: 700;
  line-height: 180px;
}

.score-ring__unit {
  margin-left: 4px;
  font-size: 16px;
  color: var(--oes-text-muted);
}

.score-tip {
  margin: 18px 0 28px;
  color: var(--oes-text-muted);
  font-size: 13px;
}

.score-meta {
  display: flex;
  gap: 16px;
  margin-bottom: 28px;
}

.score-meta__item {
  display: flex;
  flex-direction: column;
  gap: 6px;
  min-width: 160px;
  padding: 12px 16px;
  background: #f8fafd;
  border-radius: var(--oes-radius-sm);
}

.score-meta__item .label {
  font-size: 12px;
  color: var(--oes-text-muted);
}

.score-meta__item .value {
  font-size: 14px;
  color: var(--oes-text);
}

.score-actions {
  display: flex;
  gap: 12px;
}
</style>
