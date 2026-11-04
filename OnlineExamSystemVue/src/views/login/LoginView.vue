<!-- 用户登录界面 -->
<template>
	<div id="login">
		<div class="bg"></div>
		<el-row class="main-container">
			<el-col :lg="8" :xs="16" :md="10" :span="10">
				<div class="top" style="color: black;font-size: 32px">
					<i class="iconfont icon-r-team title-icon"></i> 作业互评系统
					<span class="title"> </span>
				</div>
				<br />
				<div class="bottom">
					<div class="container">
						<p class="title">账号登录</p>
						<el-form :label-position="labelPosition" label-width="80px" :model="formLabelAlign">
							<el-form-item label="用户名">
								<el-input v-model.number="formLabelAlign.username" placeholder="请输入用户名"></el-input>
							</el-form-item>
							<el-form-item label="密码">
								<el-input v-model="formLabelAlign.password" placeholder="请输入密码"
									type="password"></el-input>
							</el-form-item>
							<div class="submit">
								<el-button type="primary" class="row-login" @click="login()"> 登录</el-button>
							</div>
							<!-- <div class="options">
                <p class="find"><a href="javascript:;">找回密码</a></p>
                <div class="register">
                  <span>没有账号?</span>
                  <span><a href="javascript:;">去注册</a></span>
                </div>
              </div> -->
						</el-form>
					</div>
				</div>
			</el-col>
		</el-row>
		<el-row class="footer">
			<el-col>

			</el-col>
		</el-row>
	</div>
</template>

<script>
	import {
		mapState
	} from "vuex";
	export default {
		name: "login",
		data() {
			return {
				role: 2,
				labelPosition: "left",
				formLabelAlign: {
					username: "",
					password: "",
				},
			};
		},
		methods: {
			//用户登录请求后台处理
			login() {
				if (
					this.formLabelAlign.username == undefined ||
					this.formLabelAlign.username == ""
				) {
					this.$message("请输入用户名");
					return;
				}
				if (
					!/^\d+$/.test(this.formLabelAlign.username) ||
					this.formLabelAlign.username.toString().length > 10
				) {
					this.$message("用户名有误");
					return;
				}
				if (this.formLabelAlign.password == "") {
					this.$message("请输入密码");
					return;
				}
				this.$axios({
						url: `/api/login`,
						method: "post",
						data: {
							...this.formLabelAlign,
						},
					})
					.then((res) => {
						let resData = res.data.data;
						if (resData != null) {
							switch (resData.role) {
								case "0": //管理员   cookies设置
									this.$cookies.set("cname", resData.adminName);
									this.$cookies.set("cid", resData.adminId);
									this.$cookies.set("role", 0);
									this.$router.push({
										path: "/index"
									}); //跳转到首页
									break;
								case "1": //教师
									this.$cookies.set("cname", resData.teacherName);
									this.$cookies.set("cid", resData.teacherId);
									this.$cookies.set("role", 1);
									this.$router.push({
										path: "/index"
									}); //跳转到教师用户
									break;
								case "2": //学生
									this.$cookies.set("cname", resData.studentName);
									this.$cookies.set("cid", resData.studentId);
									this.$router.push({
										path: "/student"
									});
									break;
							}
						}
						if (resData == null) {
							//错误提示
							this.$message({
								showClose: true,
								type: "error",
								message: "用户名或者密码错误",
							});
						}
					})
					.catch((e) => {
						console.log(e);
						if (
							e.response == undefined ||
							e.response.data == undefined
						) {
							this.$message({
								showClose: true,
								message: e,
								type: "error",
								duration: 5000,
							});
						} else {
							this.$message({
								showClose: true,
								message: e.response.data,
								type: "error",
								duration: 5000,
							});
						}
					});
			},
			clickTag(key) {
				this.role = key;
			},
		},
		computed: mapState(["userInfo"]),
		mounted() {},
	};
</script>

<style lang="less" scoped>
#login {
	font-size: 14px;
	color: #000;
	background-color: #fff;
	min-height: 100vh;
}

#login .bg {
	position: fixed;
	top: 0;
	left: 0;
	width: 100%;
	overflow-y: auto;
	height: 100%;
	background: url("../../assets/img/loginbg.png") center top / cover no-repeat;
	background-color: #b6bccdd1 !important;
}

#login .main-container {
	position: relative;
	z-index: 2;
	display: flex;
	justify-content: center;
	align-items: center;
	min-height: calc(100vh - 120px);
}

#login .main-container .top {
	display: flex;
	align-items: center;
	justify-content: center;
	margin-bottom: 18px;
	font-size: 26px;
	font-weight: 700;
	letter-spacing: 2px;
	color: #1f2d5a;
	text-shadow: 0 2px 6px rgba(255, 255, 255, 0.6);
}

#login .top .title-icon {
	font-size: 34px;
	margin-right: 10px;
	color: #3d7eff;
}

#login .bottom {
	display: flex;
	justify-content: center;
	background: rgba(255, 255, 255, 0.96);
	border-radius: 16px;
	padding: 32px 36px 26px;
	box-shadow: 0 18px 48px rgba(31, 45, 90, 0.18);
	backdrop-filter: blur(6px);
}

#login .bottom .container {
	width: 100%;
	min-width: 320px;
}

#login .bottom .title {
	margin: 4px 0 24px;
	font-size: 22px;
	font-weight: 600;
	text-align: center;
	color: #1f2d5a;
}

#login .bottom .el-form-item {
	margin-bottom: 22px;
}

#login .bottom .el-input__inner {
	height: 42px;
	line-height: 42px;
	border-radius: 8px;
}

#login .bottom .submit {
	display: flex;
	justify-content: center;
	margin-top: 8px;
}

#login .bottom .submit .row-login {
	width: 100%;
	height: 46px;
	font-size: 16px;
	letter-spacing: 4px;
	border: none;
	border-radius: 8px;
	background: linear-gradient(90deg, #2b4de0 0%, #3d7eff 100%);
	box-shadow: 0 8px 18px rgba(61, 126, 255, 0.32);
}

#login .bottom .submit .row-login:hover {
	opacity: 0.92;
}

#login .footer {
	margin-top: 24px;
	padding-bottom: 24px;
	text-align: center;
}

@media (max-width: 768px) {
	#login .bottom {
		padding: 24px;
	}
	#login .bottom .container {
		min-width: 0;
	}
}
</style>
