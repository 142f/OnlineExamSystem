<template>
  <div class="oes-page">
    <PageHeader title="成绩查询" subtitle="按作业查看该学生的历次成绩走势">
      <template #actions>
        <el-button icon="el-icon-back" @click="$router.push('/allStudentsGrade')">返回成绩列表</el-button>
      </template>
    </PageHeader>

    <div class="oes-card">
      <div v-show="!isNull" ref="box" class="chart"></div>
      <EmptyState v-if="isNull" text="该考生未参加作业" icon="el-icon-data-line" />
    </div>
  </div>
</template>

<script>
import PageHeader from '@/components/common/PageHeader'
import EmptyState from '@/components/common/EmptyState'

export default {
  name: 'grade',
  components: { PageHeader, EmptyState },
  data() {
    return {
      isNull: false,
      tableDataX: [],
      tableDataY: []
    }
  },
  mounted() {
    this.score()
    window.addEventListener('resize', this.handleResize)
  },
  beforeDestroy() {
    window.removeEventListener('resize', this.handleResize)
  },
  methods: {
    handleResize() {
      const box = this.$refs.box
      if (box && this.chart) this.chart.resize()
    },
    score() {
      const studentId = this.$route.query.studentId
      if (!studentId) {
        this.isNull = true
        return
      }
      this.$axios(`/api/score/${studentId}`).then(res => {
        if (res.data.code == 200 && res.data.data && res.data.data.length) {
          const rootData = res.data.data
          rootData.forEach(element => {
            this.tableDataX.push((element.subject || '').replace(/(.{3})/g, '$1\n'))
            this.tableDataY.push((element.etScore || 0) + (element.dScore || 0))
          })
          const boxDom = this.$refs.box
          this.chart = this.$echarts.init(boxDom)
          this.chart.setOption({
            grid: { left: 40, right: 24, top: 40, bottom: 40 },
            tooltip: { trigger: 'axis' },
            xAxis: { type: 'category', data: this.tableDataX, axisLine: { lineStyle: { color: '#e8ecf3' } }, axisLabel: { color: '#6b7280' } },
            yAxis: { type: 'value', splitLine: { lineStyle: { color: '#f1f4f9' } }, axisLabel: { color: '#6b7280' } },
            series: [{
              data: this.tableDataY,
              type: 'line',
              smooth: true,
              symbolSize: 8,
              lineStyle: { width: 3, color: '#3d7eff' },
              itemStyle: { color: '#3d7eff' },
              areaStyle: { color: 'rgba(61,126,255,.12)' },
              label: { show: true, color: '#3d7eff' }
            }]
          })
        } else {
          this.isNull = true
        }
      }).catch(() => {
        this.isNull = true
      })
    }
  }
}
</script>

<style scoped>
.chart {
  width: 100%;
  height: 420px;
}
</style>
