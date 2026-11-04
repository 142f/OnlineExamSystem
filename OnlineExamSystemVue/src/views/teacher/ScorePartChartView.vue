<template>
  <div class="oes-page">
    <PageHeader :title="title" subtitle="按得分比例统计各分数段人数">
      <template #actions>
        <el-button icon="el-icon-back" @click="$router.push('/selectExamToPart')">返回作业列表</el-button>
      </template>
    </PageHeader>

    <div class="oes-card">
      <div v-show="!isEmpty" ref="box" class="chart"></div>
      <EmptyState v-if="isEmpty" text="该作业还没有学生参加" icon="el-icon-pie-chart" />
    </div>
  </div>
</template>

<script>
import PageHeader from '@/components/common/PageHeader'
import EmptyState from '@/components/common/EmptyState'

const LABELS = ['90 分及以上', '80-89 分', '70-79 分', '60-69 分', '60 分以下']

export default {
  name: 'part',
  components: { PageHeader, EmptyState },
  data() {
    return {
      title: '成绩分段',
      isEmpty: false,
      category: [0, 0, 0, 0, 0]
    }
  },
  mounted() {
    this.getScoreInfo()
    window.addEventListener('resize', this.handleResize)
  },
  beforeDestroy() {
    window.removeEventListener('resize', this.handleResize)
  },
  methods: {
    handleResize() {
      if (this.chart) this.chart.resize()
    },
    getScoreInfo() {
      const examCode = this.$route.query.examCode
      const source = this.$route.query.source
      if (source) this.title = source + ' · 分数段'
      if (!examCode) {
        this.isEmpty = true
        return
      }
      this.$axios(`/api/scores/${examCode}`).then(res => {
        const list = res.data.data || []
        if (!list.length) {
          this.isEmpty = true
          return
        }
        list.forEach(item => {
          const et = Number(item.etScore) || 0
          const d = Number(item.dScore) || 0
          const full = Number(item.score) || 0
          const ratio = full > 0 ? ((et + d) * 100) / full : 0
          if (ratio >= 90) this.category[0]++
          else if (ratio >= 80) this.category[1]++
          else if (ratio >= 70) this.category[2]++
          else if (ratio >= 60) this.category[3]++
          else this.category[4]++
        })

        const total = this.category.reduce((a, b) => a + b, 0)
        if (!total) {
          this.isEmpty = true
          return
        }

        this.chart = this.$echarts.init(this.$refs.box)
        this.chart.setOption({
          tooltip: { trigger: 'item', formatter: '{b}：{c} 人（{d}%）' },
          legend: { bottom: 0, icon: 'circle', textStyle: { color: '#4b5563' } },
          color: ['#3d7eff', '#22c55e', '#f59e0b', '#8b5cf6', '#ef4444'],
          series: [{
            name: '分数段',
            type: 'pie',
            radius: ['45%', '68%'],
            center: ['50%', '45%'],
            avoidLabelOverlap: true,
            itemStyle: { borderColor: '#fff', borderWidth: 2 },
            label: { formatter: '{b}\n{c} 人', color: '#4b5563' },
            data: LABELS.map((label, i) => ({ value: this.category[i], name: label }))
          }]
        })
      }).catch(() => {
        this.isEmpty = true
      })
    }
  }
}
</script>

<style scoped>
.chart {
  width: 100%;
  height: 460px;
}
</style>
