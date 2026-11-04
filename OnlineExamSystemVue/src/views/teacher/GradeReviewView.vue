//教师作业评分
   
   <template>
   	<div class="all">
   		<!-- todo通过cookie得到当前用户姓名，id -->
   		<div class="title" style="font-size: 24px;">
   			<i class="iconfont icon-r-refresh" style="font-size: 30px;"></i>
   		作业评分
   		</div>
   		<p class="top-title"></p>
		
   		 	<!-- todo搜索没有效果 getStudentGrade -->
   		<!-- <el-form :inline="true" class="demo-form-inline">
   			<el-form-item label="姓名">
   				<el-input v-model="condition.name" placeholder="姓名"></el-input>
   			</el-form-item>
   			<el-form-item label="学院">
   				<el-input v-model="condition.institute" placeholder="学院"></el-input>
   			</el-form-item>
   			<el-form-item label="专业">
   				<el-input v-model="condition.major" placeholder="专业"></el-input>
   			</el-form-item>

   			<el-form-item>
   				<el-button type="primary" @click="getStudentGrade" style="font-size: 20px;"> 查询</el-button>
   			</el-form-item>
   		</el-form> -->

   		<el-table :data="pagination.records" border  style="margin-top: 60px;">
   			<!-- <el-table-column prop="questionId" label="题目ID" width="120"></el-table-column> -->
   			<el-table-column prop="studentId" label="stuID" width="120"></el-table-column>
   			<el-table-column prop="studentName" label="姓名" width="120"></el-table-column>
   			<el-table-column prop="institute" label="学院" width="120"></el-table-column>
   			<el-table-column prop="question" label="问题" width="250"></el-table-column>
   			<!-- todo 修改prop -->
   			<el-table-column prop="answer" label="解析和评分规则" width="250"></el-table-column>
   			<el-table-column prop="answerMe" label="答题" width="120"></el-table-column>
   			<el-table-column prop="scoreMe" label="评分" width="120"></el-table-column>

   			<el-table-column fixed="right" label="操作" width="100">
   				<template slot-scope="scope">
   					<!-- <el-button @click="checkGrade(scope.row.studentId)" type="primary"> 编辑</el-button> -->
   					<el-button @click="checkGrade(scope.row.studentId,scope.row.questionId)" type="primary">
   						评分</el-button>
   					<!-- <el-button @click="deleteById(scope.row.studentId)" type="danger"> 删除</el-button> -->
   				</template>
   			</el-table-column>
   		</el-table>


   		<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
   			:current-page="pagination.current" :page-sizes="[6, 10]" :page-size="pagination.size"
   			layout="total, sizes, prev, pager, next, jumper" :total="pagination.total" class="page">
   		</el-pagination>
   		<!-- 编辑对话框-->
   		<el-dialog title="编辑学生信息" :visible.sync="dialogVisible" width="80%" :before-close="handleClose">
   			<section class="update">
   				<el-form ref="form" :model="form" label-width="150px">
   					<el-form-item label="姓名">
   						<el-input v-model="form.studentName"></el-input>
   					</el-form-item>
   					<el-form-item label="问题">
   						<el-input v-model="form.question"></el-input>
   					</el-form-item>

   					<el-form-item label="解析和评分规则">
   						<el-input v-model="form.answer"></el-input>
   					</el-form-item>
   					<el-form-item label="答题">
   						<el-input v-model="form.answerMe"></el-input>
   					</el-form-item>
   					<el-form-item label="评分">
   						<el-input v-model="form.scoreMe"></el-input>
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
   					size: 6, //每页条数
   					questionId: "",
   				},
   				user: {
   					//用户信息
   					userName: null,
   					userId: null,
   				},
   				dialogVisible: false, //对话框
   				form: {}, //保存点击以后当前作业的评分,

   				condition: {
   					name: "",
   					grade: "",
   					tel: "",
   					institute: "",
   					major: "",
   					clazz: "",


   				}
   			};
   		},

   		created() {
   			this.getUserInfo();
   			this.getStudentGrade();

   		},
   		methods: {

   			getUserInfo() {
   				//获取用户信息
   				let userName = this.$cookies.get("cname");
   				let userId = this.$cookies.get("cid");
   				this.user.userName = userName;
   				this.user.userId = userId;
   			},
   			// todo 修改需要的属性
   			getStudentGrade() {
   				// 根据条件获取学生成绩
   				// 如果this.condition.name（去除空白后）是一个空字符串，那么name被赋值为@；
   				// 否则，name被赋值为this.condition.name的原始值。
   				// ----------查询条件
   				var name = (this.condition.name.trim() == "" ? "@" : this.condition.name);
   				var grade = (this.condition.grade.trim() == "" ? "@" : this.condition.grade);
   				var tel = (this.condition.tel.trim() == "" ? "@" : this.condition.tel);
   				var institute = (this.condition.institute.trim() == "" ? "@" : this.condition.institute);
   				var major = (this.condition.major.trim() == "" ? "@" : this.condition.major);
   				var clazz = (this.condition.clazz.trim() == "" ? "@" : this.condition.clazz);
   				this.$axios(
   					`/api/daQuestionallTea/${this.pagination.current}/${this.pagination.size}/${this.user.userId }`
   				).then(res => {
   					this.pagination = res.data.data;
   					console.log(this.pagination)
   				}).catch(error => {});
   			},

   			// todo	修改评分
   			checkGrade(studentId, questionId) { //修改学生信息
   				this.dialogVisible = true
   				this.$axios(`/api/daQuestionId/${studentId}/${questionId}`).then(res => {
   					this.form = res.data.data
   				})
   			},
   			submit() { //提交更改
   				this.dialogVisible = false
   				this.$axios({
   					url: '/api/daQuestion',
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
   				this.pagination.size = val;
   				this.getStudentGrade();
   			},
   			//改变当前页码，重新发送请求
   			handleCurrentChange(val) {
   				this.pagination.current = val;
   				this.getStudentGrade();
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

   			handleClose(done) { //关闭提醒
   				this.$confirm('确认关闭？')
   					.then(_ => {
   						done();
   					}).catch(_ => {});
   			},
   		}
   	};
   </script>
   <style lang="less" scoped>
   	.all {
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
   			background-color: #dd5862 !important;
   		}
   	}

   	.el-table .warning-row {
   		background: #000 !important;
   	}

   	.el-table .success-row {
   		background: #dd5862;
   	}
   </style>