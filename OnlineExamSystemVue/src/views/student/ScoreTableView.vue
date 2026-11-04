<template>
	<div class="oes-page">
		<PageHeader title="我的分数" subtitle="按作业查看客观题、主观题与总分">
			<template #actions>
				<el-button icon="el-icon-refresh" @click="getScore()">刷新</el-button>
			</template>
		</PageHeader>

		<div class="oes-stat-grid">
			<StatCard label="考试次数" :value="pagination.total || 0" icon="el-icon-document" color="#3d7eff" />
			<StatCard label="最高得分" :value="maxScore" icon="el-icon-trophy" color="#22c55e" />
			<StatCard label="平均得分" :value="avgScore" icon="el-icon-data-line" color="#f59e0b" />
		</div>

		<div class="oes-table-card">
			<el-table ref="filterTable" :data="score" v-loading="loading" size="medium">
				<el-table-column prop="answerDate" label="提交日期" width="150" sortable />
				<el-table-column prop="subject" label="课程名称" min-width="160" />
				<el-table-column prop="etScore" label="客观题" width="110" align="center" />
				<el-table-column prop="dScore" label="主观题" width="110" align="center" />
				<el-table-column label="得分总分" width="120" align="center">
					<template slot-scope="scope">
						<span class="cell-strong">{{ (scope.row.etScore || 0) + (scope.row.dScore || 0) }}</span>
					</template>
				</el-table-column>
				<el-table-column prop="score" label="试卷满分" width="110" align="center" />
				<el-table-column label="状态" width="100" align="center">
					<template slot-scope="scope">
						<span class="oes-tag oes-tag--success">已出分</span>
					</template>
				</el-table-column>
				<template slot="empty">
					<EmptyState text="还没有成绩记录，完成一次作业后即可查看" />
				</template>
			</el-table>

			<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
				:current-page="pagination.current" :page-sizes="[4, 6, 8, 10]" :page-size="pagination.size"
				layout="total, sizes, prev, pager, next, jumper" :total="pagination.total">
			</el-pagination>
		</div>
	</div>
</template>

<script>
import PageHeader from '@/components/common/PageHeader'
import StatCard from '@/components/common/StatCard'
import EmptyState from '@/components/common/EmptyState'

export default {
	components: { PageHeader, StatCard, EmptyState },
	data() {
		return {
			pagination: { current: 1, total: null, size: 10 },
			user: { userName: null, userId: null },
			loading: false,
			score: []
		}
	},
	computed: {
		totals() {
			return (this.score || []).map(s => (s.etScore || 0) + (s.dScore || 0))
		},
		maxScore() {
			return this.totals.length ? Math.max(...this.totals) : '-'
		},
		avgScore() {
			if (!this.totals.length) return '-'
			return (this.totals.reduce((a, b) => a + b, 0) / this.totals.length).toFixed(1)
		}
	},
	created() {
		this.user.userId = this.$cookies.get('cid')
		this.user.userName = this.$cookies.get('cname')
		this.loading = true
		this.getScore()
	},
	methods: {
		getScore() {
			// 未取到学号（cookie 缺失）时不做请求，避免触发 403
			if (!this.user.userId) {
				this.user.userId = this.$cookies.get('cid')
			}
			if (!this.user.userId) {
				this.loading = false
				this.$message.warning('登录状态已失效，请重新登录')
				return
			}
			this.loading = true
			this.$axios(`/api/score/${this.pagination.current}/${this.pagination.size}/${this.user.userId}`)
				.then(res => {
					const data = res.data.data || {}
					this.score = data.records || []
					this.pagination.total = data.total || 0
				})
				.catch(() => {})
				.finally(() => {
					this.loading = false
				})
		},
		handleSizeChange(val) {
			this.pagination.size = val
			this.getScore()
		},
		handleCurrentChange(val) {
			this.pagination.current = val
			this.getScore()
		}
	}
}
</script>

<style scoped>
.cell-strong {
	font-weight: 600;
	color: var(--oes-text);
}
</style>
