<template>
	<div class="oes-page">
		<PageHeader title="学生管理" subtitle="按条件检索学生，维护基础信息与联系方式">
			<template #actions>
				<el-button type="primary" icon="el-icon-plus" @click="$router.push('/addStudent')">添加学生</el-button>
			</template>
		</PageHeader>

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
				<el-table-column prop="studentId" label="学号" width="120" />
				<el-table-column prop="studentName" label="姓名" width="110">
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
				<el-table-column label="操作" width="170" fixed="right">
					<template slot-scope="scope">
						<el-button type="text" icon="el-icon-edit" @click="checkGrade(scope.row.studentId)">编辑</el-button>
						<el-button type="text" class="danger-text" icon="el-icon-delete" @click="deleteById(scope.row.studentId)">删除</el-button>
					</template>
				</el-table-column>
				<template slot="empty">
					<EmptyState text="没有符合条件的学生" />
				</template>
			</el-table>

			<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
				:current-page="pagination.current" :page-sizes="[6, 10]" :page-size="pagination.size"
				layout="total, sizes, prev, pager, next, jumper" :total="pagination.total">
			</el-pagination>
		</div>

		<el-dialog title="编辑学生信息" :visible.sync="dialogVisible" width="560px" :before-close="handleClose">
			<el-form ref="form" :model="form" label-width="90px">
				<el-row :gutter="12">
					<el-col :span="12"><el-form-item label="姓名"><el-input v-model="form.studentName" /></el-form-item></el-col>
					<el-col :span="12"><el-form-item label="学号"><el-input v-model="form.studentId" disabled /></el-form-item></el-col>
					<el-col :span="12"><el-form-item label="学院"><el-input v-model="form.institute" /></el-form-item></el-col>
					<el-col :span="12"><el-form-item label="专业"><el-input v-model="form.major" /></el-form-item></el-col>
					<el-col :span="12"><el-form-item label="年级"><el-input v-model="form.grade" /></el-form-item></el-col>
					<el-col :span="12"><el-form-item label="班级"><el-input v-model="form.clazz" /></el-form-item></el-col>
					<el-col :span="12"><el-form-item label="性别"><el-input v-model="form.sex" /></el-form-item></el-col>
					<el-col :span="12"><el-form-item label="联系方式"><el-input v-model="form.tel" /></el-form-item></el-col>
					<el-col :span="24"><el-form-item label="邮箱"><el-input v-model="form.email" /></el-form-item></el-col>
				</el-row>
			</el-form>
			<span slot="footer" class="dialog-footer">
				<el-button @click="dialogVisible = false">取 消</el-button>
				<el-button type="primary" :loading="submitting" @click="submit()">确 定</el-button>
			</span>
		</el-dialog>
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
			submitting: false,
			pagination: {
				current: 1,
				total: null,
				size: 6,
				records: []
			},
			dialogVisible: false,
			form: {},
			condition: {
				name: '',
				grade: '',
				tel: '',
				institute: '',
				major: '',
				clazz: ''
			}
		}
	},
	created() {
		this.getStudentGrade()
	},
	methods: {
		getStudentGrade() {
			const fill = v => (v.trim() === '' ? '@' : v)
			const { name, grade, tel, institute, major, clazz } = this.condition
			this.loading = true
			this.$axios(
				`/api/students/${this.pagination.current}/${this.pagination.size}/${fill(name)}/${fill(grade)}/${fill(tel)}/${fill(institute)}/${fill(major)}/${fill(clazz)}`
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
			this.condition = { name: '', grade: '', tel: '', institute: '', major: '', clazz: '' }
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
			this.dialogVisible = true
			this.$axios(`/api/student/${studentId}`).then(res => {
				this.form = res.data.data
			})
		},
		deleteById(studentId) {
			this.$confirm('确定删除当前学生吗？删除后无法恢复', 'Warning', {
				confirmButtonText: '确定删除',
				cancelButtonText: '算了，留着吧',
				type: 'warning'
			})
				.then(() => {
					this.$axios({ url: `/api/student/${studentId}`, method: 'delete' }).then(() => {
						this.$message.success('删除成功')
						this.getStudentGrade()
					})
				})
				.catch(() => {})
		},
		submit() {
			this.submitting = true
			this.$axios({ url: '/api/student', method: 'put', data: { ...this.form } })
				.then(res => {
					if (res.data.code == 200) {
						this.$message.success('更新成功')
						this.dialogVisible = false
					} else {
						this.$message.error(res.data.message || '更新失败')
					}
					this.getStudentGrade()
				})
				.catch(() => this.$message.error('更新失败'))
				.finally(() => {
					this.submitting = false
				})
		},
		handleClose(done) {
			this.$confirm('确认关闭？')
				.then(() => {
					done()
				})
				.catch(() => {})
		}
	}
}
</script>

<style scoped>
.cell-strong {
	font-weight: 600;
	color: var(--oes-text);
}
.danger-text {
	color: var(--oes-danger);
}
</style>
