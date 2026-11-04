<template>
	<div id="login">
		<div class="bg"></div>

		<div class="login-shell">
			<!-- 品牌区 -->
			<section class="login-brand">
				<div class="login-brand__logo">
					<i class="iconfont icon-r-team"></i>
					<span>作业互评系统</span>
				</div>
				<h1 class="login-brand__title">在线作业与互评平台</h1>
				<p class="login-brand__desc">
					面向教师与学生的作业发布、在线作答、互评与成绩管理一体化平台。
				</p>
				<ul class="login-brand__features">
					<li><i class="el-icon-document-checked"></i> 作业发布与在线作答</li>
					<li><i class="el-icon-data-analysis"></i> 成绩统计与学情分析</li>
					<li><i class="el-icon-user"></i> 学生互评与教师复核</li>
				</ul>
			</section>

			<!-- 表单区 -->
			<section class="login-panel">
				<h2 class="login-panel__title">账号登录</h2>
				<p class="login-panel__subtitle">请使用学号 / 工号登录</p>

				<el-form :model="formLabelAlign" label-position="top" @submit.native.prevent>
					<el-form-item label="用户名">
						<el-input
							v-model.number="formLabelAlign.username"
							placeholder="请输入用户名"
							prefix-icon="el-icon-user"
							size="medium"
						></el-input>
					</el-form-item>
					<el-form-item label="密码">
						<el-input
							v-model="formLabelAlign.password"
							type="password"
							placeholder="请输入密码"
							prefix-icon="el-icon-lock"
							show-password
							size="medium"
							@keyup.enter.native="login()"
						></el-input>
					</el-form-item>

					<el-button
						type="primary"
						class="login-panel__submit"
						:loading="loading"
						@click="login()"
					>
						登 录
					</el-button>
				</el-form>

				<p class="login-panel__tip">默认账号：管理员 9991 / 教师 20081001 / 学生 20224001，密码 123456</p>
			</section>
		</div>

		<footer class="login-footer">© 2026 作业互评系统 · 课程设计演示环境</footer>
	</div>
</template>

<script>
	import { mapState } from "vuex";
	export default {
		name: "login",
		data() {
			return {
				role: 2,
				formLabelAlign: {
					username: "",
					password: "",
				},
				loading: false,
			};
		},
		methods: {
			// 用户登录请求后台处理
			login() {
				if (this.formLabelAlign.username == undefined || this.formLabelAlign.username == "") {
					this.$message("请输入用户名");
					return;
				}
				if (!/^\d+$/.test(this.formLabelAlign.username) || this.formLabelAlign.username.toString().length > 10) {
					this.$message("用户名有误");
					return;
				}
				if (this.formLabelAlign.password == "") {
					this.$message("请输入密码");
					return;
				}
				this.loading = true;
				this.$axios({
						url: `/api/login`,
						method: "post",
						data: { ...this.formLabelAlign },
					})
					.then((res) => {
						let resData = res.data.data;
						if (resData != null) {
							switch (resData.role) {
								case "0": // 管理员
									this.$cookies.set("cname", resData.adminName);
									this.$cookies.set("cid", resData.adminId);
									this.$cookies.set("role", 0);
									this.$router.push({ path: "/index" });
									break;
								case "1": // 教师
									this.$cookies.set("cname", resData.teacherName);
									this.$cookies.set("cid", resData.teacherId);
									this.$cookies.set("role", 1);
									this.$router.push({ path: "/index" });
									break;
								case "2": // 学生
									this.$cookies.set("cname", resData.studentName);
									this.$cookies.set("cid", resData.studentId);
									this.$router.push({ path: "/student" });
									break;
							}
						}
						if (resData == null) {
							this.$message({ showClose: true, type: "error", message: "用户名或者密码错误" });
						}
					})
					.catch((e) => {
						if (e.response == undefined || e.response.data == undefined) {
							this.$message({ showClose: true, message: e, type: "error", duration: 5000 });
						} else {
							this.$message({ showClose: true, message: e.response.data, type: "error", duration: 5000 });
						}
					})
					.finally(() => {
						this.loading = false;
					});
			},
			clickTag(key) {
				this.role = key;
			},
		},
		computed: mapState(["userInfo"]),
	};
</script>

<style lang="less" scoped>
#login {
	position: relative;
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;
	min-height: 100vh;
	padding: 32px 24px;
	box-sizing: border-box;
	overflow: hidden;
}

#login .bg {
	position: fixed;
	inset: 0;
	background: url("../../assets/img/loginbg.png") center / cover no-repeat;
	filter: blur(1px) brightness(1.02);
	transform: scale(1.02);
	z-index: 0;
}

.login-shell {
	position: relative;
	z-index: 1;
	display: flex;
	width: 100%;
	max-width: 960px;
	min-height: 460px;
	background: rgba(255, 255, 255, 0.96);
	border-radius: 20px;
	box-shadow: 0 24px 64px rgba(23, 43, 99, 0.22);
	overflow: hidden;
}

.login-brand {
	flex: 1 1 52%;
	display: flex;
	flex-direction: column;
	justify-content: center;
	padding: 48px 44px;
	color: #fff;
	background: linear-gradient(150deg, #2b4de0 0%, #3d7eff 55%, #5c93ff 100%);
}

.login-brand__logo {
	display: flex;
	align-items: center;
	gap: 10px;
	font-size: 20px;
	font-weight: 600;
	letter-spacing: 1px;
	margin-bottom: 32px;
}

.login-brand__logo .iconfont {
	font-size: 30px;
}

.login-brand__title {
	margin: 0 0 14px;
	font-size: 28px;
	font-weight: 700;
	letter-spacing: 1px;
}

.login-brand__desc {
	margin: 0 0 28px;
	font-size: 14px;
	line-height: 1.8;
	color: rgba(255, 255, 255, 0.86);
}

.login-brand__features {
	list-style: none;
	padding: 0;
	margin: 0;
}

.login-brand__features li {
	display: flex;
	align-items: center;
	gap: 10px;
	margin-bottom: 14px;
	font-size: 14px;
	color: rgba(255, 255, 255, 0.92);
}

.login-brand__features i {
	font-size: 16px;
	opacity: 0.9;
}

.login-panel {
	flex: 1 1 48%;
	display: flex;
	flex-direction: column;
	justify-content: center;
	padding: 48px 44px;
}

.login-panel__title {
	margin: 0;
	font-size: 24px;
	font-weight: 600;
	color: var(--oes-text);
}

.login-panel__subtitle {
	margin: 8px 0 28px;
	font-size: 13px;
	color: var(--oes-text-muted);
}

.login-panel__submit {
	width: 100%;
	height: 44px;
	margin-top: 8px;
	font-size: 15px;
	letter-spacing: 4px;
	border-radius: 8px;
	background: linear-gradient(90deg, #2b4de0 0%, #3d7eff 100%);
	border: none;
	box-shadow: 0 8px 18px rgba(61, 126, 255, 0.28);
}

.login-panel__tip {
	margin-top: 20px;
	font-size: 12px;
	line-height: 1.7;
	color: var(--oes-text-muted);
}

.login-footer {
	position: relative;
	z-index: 1;
	margin-top: 24px;
	font-size: 12px;
	color: #48607f;
}

@media (max-width: 860px) {
	.login-shell {
		flex-direction: column;
		max-width: 460px;
	}
	.login-brand {
		padding: 28px;
	}
	.login-brand__features {
		display: none;
	}
	.login-brand__title {
		font-size: 22px;
	}
	.login-panel {
		padding: 28px;
	}
}
</style>
