<template>
	<div id="student" class="oes-student">
		<header class="oes-stu-header">
			<div class="oes-stu-brand">
				<img src="@/assets/033.png" class="oes-stu-logo" />
				<span class="oes-stu-title">作业互评系统</span>
			</div>

			<nav class="oes-stu-nav">
				<a class="oes-stu-nav__item" @click="exam()">作业中心</a>
				<a class="oes-stu-nav__item" @click="practice()">试题练习</a>
				<router-link class="oes-stu-nav__item" :class="{ 'is-active': $route.path === '/scoreTable' }" to="/scoreTable">我的分数</router-link>
				<router-link class="oes-stu-nav__item" :class="{ 'is-active': $route.path === '/scoreTable2' }" to="/scoreTable2">分数详情</router-link>
				<router-link class="oes-stu-nav__item" :class="{ 'is-active': $route.path === '/message' }" to="/message">交流区</router-link>
				<router-link class="oes-stu-nav__item" :class="{ 'is-active': $route.path === '/huping' }" to="/huping">作业互评</router-link>
			</nav>

			<div class="oes-stu-user" @mouseenter="flag = true" @mouseleave="flag = false">
				<el-avatar shape="circle" :size="34" :src="require('@/assets/img/avatar.jpg')"></el-avatar>
				<span class="oes-stu-user__name">{{ user.userName }}</span>
				<transition name="fade">
					<div class="oes-stu-user__menu" v-if="flag">
						<p @click="manage()">修改密码</p>
						<p class="exit" @click="exit()">退出登录</p>
					</div>
				</transition>
			</div>
		</header>

		<main class="oes-stu-main">
			<router-view></router-view>
		</main>
		<v-footer></v-footer>
	</div>
</template>

<script>
	import myFooter from "@/components/layout/PageFooter";
	import { mapState } from "vuex";
	export default {
		components: {
			"v-footer": myFooter,
		},
		data() {
			return {
				flag: false,
				user: {},
			};
		},
		created() {
			this.userInfo();
		},
		methods: {
			exit() {
				this.$router.push({ path: "/" });
				this.$cookies.remove("cname");
				this.$cookies.remove("cid");
				this.$cookies.remove("rb_token");
				this.$cookies.remove("rb_role");
			},
			manage() {
				this.$router.push({ path: "/manager" });
			},
			userInfo() {
				let studentName = this.$cookies.get("cname");
				let studentId = this.$cookies.get("cid");
				this.user.userName = studentName;
				this.user.studentId = studentId;
			},
			practice() {
				let isPractice = true;
				this.$store.commit("practice", isPractice);
				this.$router.push({ path: "/startExam" });
			},
			exam() {
				let isPractice = false;
				this.$store.commit("practice", isPractice);
				this.$router.push({ path: "/student" });
			},
		},
		computed: mapState(["isPractice"]),
	};
</script>

<style scoped>
.oes-student {
	min-height: 100vh;
	background: var(--oes-bg);
}

.oes-stu-header {
	position: sticky;
	top: 0;
	z-index: 100;
	display: flex;
	align-items: center;
	gap: 24px;
	height: var(--oes-header-height);
	padding: 0 24px;
	background: #fff;
	box-shadow: var(--oes-shadow-sm);
}

.oes-stu-brand {
	display: flex;
	align-items: center;
	gap: 10px;
	flex-shrink: 0;
}

.oes-stu-logo {
	width: 34px;
	height: 34px;
}

.oes-stu-title {
	font-size: 18px;
	font-weight: 600;
	color: var(--oes-text);
	white-space: nowrap;
}

.oes-stu-nav {
	display: flex;
	align-items: center;
	gap: 4px;
	flex: 1;
	overflow-x: auto;
}

.oes-stu-nav__item {
	position: relative;
	padding: 8px 14px;
	font-size: 14px;
	color: var(--oes-text-secondary);
	border-radius: var(--oes-radius-sm);
	cursor: pointer;
	white-space: nowrap;
	transition: color 0.2s, background-color 0.2s;
}

.oes-stu-nav__item:hover {
	color: var(--oes-primary);
	background: var(--oes-primary-light);
}

.oes-stu-nav__item.is-active {
	color: var(--oes-primary);
	font-weight: 600;
	background: var(--oes-primary-light);
}

.oes-stu-user {
	position: relative;
	display: flex;
	align-items: center;
	gap: 8px;
	padding: 6px 10px;
	border-radius: var(--oes-radius-sm);
	cursor: pointer;
	flex-shrink: 0;
}

.oes-stu-user:hover {
	background: var(--oes-primary-light);
}

.oes-stu-user__name {
	font-size: 14px;
	color: var(--oes-text);
}

.oes-stu-user__menu {
	position: absolute;
	top: 52px;
	right: 0;
	min-width: 130px;
	padding: 6px;
	background: #fff;
	border-radius: var(--oes-radius);
	box-shadow: var(--oes-shadow);
	z-index: 10;
}

.oes-stu-user__menu p {
	margin: 0;
	padding: 9px 12px;
	font-size: 13px;
	color: var(--oes-text-secondary);
	border-radius: var(--oes-radius-sm);
}

.oes-stu-user__menu p:hover {
	color: var(--oes-primary);
	background: var(--oes-primary-light);
}

.oes-stu-user__menu .exit:hover {
	color: var(--oes-danger);
	background: #fef2f2;
}

.oes-stu-main {
	padding: 24px;
	max-width: 1280px;
	margin: 0 auto;
}

.fade-enter-active,
.fade-leave-active {
	transition: opacity 0.2s;
}
.fade-enter,
.fade-leave-to {
	opacity: 0;
}

@media (max-width: 992px) {
	.oes-stu-title {
		display: none;
	}
	.oes-stu-main {
		padding: 16px;
	}
}
</style>
