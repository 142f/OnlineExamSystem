<template>
	<div class="oes-page">
		<PageHeader title="作业中心" subtitle="查看可参加的作业，点击卡片查看详情并开始答题">
			<template #actions>
				<el-input v-model="key" placeholder="按作业名称搜索" prefix-icon="el-icon-search" clearable style="width: 220px" @keyup.enter.native="search()" />
				<el-button type="primary" icon="el-icon-search" @click="search()">搜索</el-button>
			</template>
		</PageHeader>

		<div v-loading="loading" class="exam-grid">
			<div class="exam-card" v-for="(item, index) in pagination.records" :key="index" @click="toExamMsg(item)">
				<div class="exam-card__top">
					<span class="exam-card__subject">{{ item.source }}</span>
					<span class="oes-tag oes-tag--success">满分 {{ item.totalScore }}</span>
				</div>
				<p class="exam-card__desc">{{ item.description || '暂无作业说明' }}</p>
				<div class="exam-card__meta">
					<span><i class="el-icon-date"></i> {{ (item.examDate || '').substr(0, 10) }}</span>
					<span v-if="item.totalTime != null"><i class="el-icon-time"></i> 限时 {{ item.totalTime }} 分钟</span>
				</div>
				<div class="exam-card__footer">进入作业 <i class="el-icon-arrow-right"></i></div>
			</div>
			<EmptyState v-if="!loading && (!pagination.records || pagination.records.length === 0)" text="暂无可参加的作业" />
		</div>

		<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
			:current-page="pagination.current" :page-sizes="[6, 10, 20, 40]" :page-size="pagination.size"
			layout="total, sizes, prev, pager, next, jumper" :total="pagination.total">
		</el-pagination>
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
			key: '',
			pagination: { current: 1, total: null, size: 6, records: [] }
		}
	},
	created() {
		this.loading = true
		this.getExamInfo()
	},
	methods: {
		getExamInfo() {
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
		search() {
			if (!this.key) {
				this.getExamInfo()
				return
			}
			this.loading = true
			this.$axios('/api/exams').then(res => {
				if (res.data.code == 200) {
					const all = res.data.data || []
					this.pagination.records = all.filter(item => (item.source || '').indexOf(this.key) >= 0)
				}
			}).finally(() => {
				this.loading = false
			})
		},
		toExamMsg(exam) {
			if (exam.examDate > this.formatDateNow()) {
				this.$message({ message: '作业时间未到', type: 'error' })
				return
			}
			this.$router.push({ path: '/examMsg', query: { examCode: exam.examCode } })
		},
		formatDateNow() {
			const d = new Date()
			const m = ('0' + (d.getMonth() + 1)).slice(-2)
			const day = ('0' + d.getDate()).slice(-2)
			return `${d.getFullYear()}-${m}-${day}`
		}
	}
}
</script>

<style scoped>
.exam-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 16px;
  min-height: 120px;
}

.exam-card {
  display: flex;
  flex-direction: column;
  gap: 10px;
  padding: 20px;
  background: var(--oes-surface);
  border-radius: var(--oes-radius);
  border: 1px solid var(--oes-border);
  box-shadow: var(--oes-shadow-sm);
  cursor: pointer;
  transition: all 0.2s ease;
}

.exam-card:hover {
  transform: translateY(-3px);
  border-color: var(--oes-primary);
  box-shadow: var(--oes-shadow);
}

.exam-card__top {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 8px;
}

.exam-card__subject {
  font-size: 17px;
  font-weight: 600;
  color: var(--oes-text);
}

.exam-card__desc {
  margin: 0;
  font-size: 13px;
  line-height: 1.6;
  color: var(--oes-text-secondary);
  min-height: 42px;
}

.exam-card__meta {
  display: flex;
  gap: 16px;
  font-size: 12px;
  color: var(--oes-text-muted);
}

.exam-card__footer {
  display: flex;
  align-items: center;
  justify-content: flex-end;
  gap: 4px;
  padding-top: 10px;
  font-size: 13px;
  color: var(--oes-primary);
  border-top: 1px dashed var(--oes-border);
}

.exam-grid .oes-empty {
  grid-column: 1 / -1;
}
</style>
