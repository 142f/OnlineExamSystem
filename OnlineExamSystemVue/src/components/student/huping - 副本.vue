<!-- //查询所有题库  保底可运行 -->
<template>
	<div class="exam">
		<el-form :inline="true" class="demo-form-inline">
			<el-form-item label="作业名称">
				<el-input v-model="subject" placeholder="作业名称"></el-input>
			</el-form-item>
			<el-form-item label="章节">
				<el-input v-model="section" placeholder="章节"></el-input>
			</el-form-item>
			<el-form-item label="题目内容">
				<el-input v-model="question" placeholder="题目内容"></el-input>
			</el-form-item><br>
			<el-form-item>
				<el-button type="primary" @click="getAnswerInfo" style="font-size: 20px;"> 查询</el-button>
			</el-form-item>
		</el-form>

		<el-table :data="pagination.records" border :row-class-name="tableRowClassName">
			<el-table-column fixed="left" prop="subject" label="学生姓名" width="150"></el-table-column>
			<el-table-column fixed="left" prop="subject" label="作业名称" width="150"></el-table-column>
			<el-table-column prop="question" label="题目信息" width="440"></el-table-column>
			<el-table-column prop="question" label="参考答案和评分标准" width="440"></el-table-column>
	
			<el-table-column fixed="right" label="操作" width="160">
				<template slot-scope="scope">
					<!-- <el-button @click="toEdit(scope.row.type, scope.row.questionId)" type="primary"> 编辑</el-button> -->
					<el-button @click="checkGrade(scope.row.studentId)" type="primary"> 评分</el-button>
				</template>
			</el-table-column>
		</el-table>

		<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
			:current-page="pagination.current" :page-sizes="[6, 10]" :page-size="pagination.size"
			layout="total, sizes, prev, pager, next, jumper" :total="pagination.total" class="page"></el-pagination>

		<el-dialog title="编辑学生信息" :visible.sync="dialogVisible" width="30%" :before-close="handleClose">
			<section class="update">
				<el-form ref="form" :model="form" label-width="80px">
					<el-form-item label="姓名">
						<el-input v-model="form.studentName"></el-input>
					</el-form-item>
					<el-form-item label="学院">
						<el-input v-model="form.institute"></el-input>
					</el-form-item>
					<el-form-item label="专业">
						<el-input v-model="form.major"></el-input>
					</el-form-item>
					<el-form-item label="年级">
						<el-input v-model="form.grade"></el-input>
					</el-form-item>
					<el-form-item label="班级">
						<el-input v-model="form.clazz"></el-input>
					</el-form-item>
					<el-form-item label="性别">
						<el-input v-model="form.sex"></el-input>
					</el-form-item>
					<el-form-item label="电话号码">
						<el-input v-model="form.tel"></el-input>
					</el-form-item>
				</el-form>
			</section>
			<span slot="footer" class="dialog-footer">
				<el-button @click="dialogVisible = false" style="font-size: 20px;"> 取 消</el-button>
				<el-button type="primary" @click="submit()"> 确 定</el-button>
			</span>
		</el-dialog>
	</div>



</template>



<script>
	export default {
		data() {
			return {
				pagination: {
					//分页后的作业信息
					current: 1, //当前页
					total: null, //记录条数
					size: 6 //每页条数
				},
				form: {},
				dialogVisible: false, //对话框
				section: "",
				subject: "",
				question: "",
			};
		},
		created() {
			this.getAnswerInfo();
		},

		methods: {
			getStudentGrade() {
				// 根据条件获取学生成绩
				var name = (this.condition.name.trim() == "" ? "@" : this.condition.name);
				var grade = (this.condition.grade.trim() == "" ? "@" : this.condition.grade);
				var tel = (this.condition.tel.trim() == "" ? "@" : this.condition.tel);
				var institute = (this.condition.institute.trim() == "" ? "@" : this.condition.institute);
				var major = (this.condition.major.trim() == "" ? "@" : this.condition.major);
				var clazz = (this.condition.clazz.trim() == "" ? "@" : this.condition.clazz);
				this.$axios(
					`/api/students/${this.pagination.current}/${this.pagination.size}/${name}/${grade}/${tel}/${institute}/${major}/${clazz}`
				).then(res => {
					this.pagination = res.data.data;
					console.log(this.pagination)
				}).catch(error => {});
			},
			getAnswerInfo(size, current) {
				//分页查询所有试卷信息
				if (typeof size === 'number' && !isNaN(size)) {
					this.pagination.size = size;
				}
				if (typeof current === 'number' && !isNaN(current)) {
					this.pagination.current = current;
				} else {
					this.pagination.current = 1;
				}
				var subject = this.subject;
				if (this.subject.trim() == "") {
					subject = "@";
				}
				var section = this.section;
				if (this.section.trim() == "") {
					section = "@";
				}
				var question = this.question;
				if (this.question.trim() == "") {
					question = "@";
				}
				this.$axios(
						`/api/answers/${this.pagination.current}/${this.pagination.size}/${subject}/${section}/${question}`
					)
					.then(res => {
						this.pagination = res.data.data;
					})
					.catch(error => {});
			},
			toEdit(type, id) {
				// 前往编辑题目
				this.$router.push({
					path: '/editAnswerChildren',
					query: {
						type: type,
						questionId: id
					}
				})
			},
			// todo路径修改
			checkGrade(studentId) { //修改学生信息
				this.dialogVisible = true
				this.$axios(`/api/student/${studentId}`).then(res => {
					this.form = res.data.data
				})
			},
			submit() { //提交更改
				this.dialogVisible = false
				this.$axios({
					url: '/api/student',
					method: 'put',
					data: {
						...this.form
					}
				}).then(res => {
					if (res.data.code == 200) {
						this.$message({
							message: '更新成功',
							type: 'success'
						})
					}
					this.getStudentGrade()
				})
			},

			//改变当前记录条数
			handleSizeChange(val) {
				this.getAnswerInfo(val);
			},
			//改变当前页码，重新发送请求
			handleCurrentChange(val) {
				this.getAnswerInfo(undefined, val);
			},	//改变当前记录条数
			handleSizeChange(val) {
				this.pagination.size = val;
				this.getStudentGrade();
			},
			//改变当前页码，重新发送请求
			handleCurrentChange(val) {
				this.pagination.current = val;
				this.getStudentGrade();
			},
			checkGrade(studentId) { //修改学生信息
				this.dialogVisible = true
				this.$axios(`/api/student/${studentId}`).then(res => {
					this.form = res.data.data
				})
			},
			deleteById(studentId) { //删除当前学生
				var _this = this;
				this.$confirm("确定删除当前学生吗？删除后无法恢复", "Warning", {
					confirmButtonText: '确定删除',
					cancelButtonText: '算了,留着吧',
					type: 'danger'
				}).then(() => { //确认删除
					this.$axios({
						url: `/api/student/${studentId}`,
						method: 'delete',
					}).then(res => {
						this.getStudentGrade()
					})
				}).catch(() => {

				})
			},
			submit() { //提交更改
				this.dialogVisible = false
				this.$axios({
					url: '/api/student',
					method: 'put',
					data: {
						...this.form
					}
				}).then(res => {
					if (res.data.code == 200) {
						this.$message({
							message: '更新成功',
							type: 'success'
						})
					}
					this.getStudentGrade()
				})
			},
			handleClose(done) { //关闭提醒
				this.$confirm('确认关闭？')
					.then(_ => {
						done();
					}).catch(_ => {});
			},
			tableRowClassName({
				row,
				rowIndex
			}) {
				if (rowIndex % 2 == 0) {
					return "warning-row";
				} else {
					return "success-row";
				}
			}
		}
	};
</script>
<style lang="less" scoped>
	.exam {
		padding: 0px 40px;

		.page {
			margin-top: 20px;
			display: flex;
			justify-content: center;
			align-items: center;
		}

		.edit {
			margin-left: 20px;
		}

		.el-table tr {
			background-color: #DD5862 !important;
		}
	}

	.el-table .warning-row {
		background: #000 !important;

	}

	.el-table .success-row {
		background: #DD5862;
	}
</style>