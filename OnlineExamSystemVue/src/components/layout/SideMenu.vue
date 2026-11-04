<!-- 左侧导航菜单 -->
<template>
	<div class="oes-side">
		<el-menu
			:default-active="$route.path"
			:collapse="flag"
			class="oes-side__menu"
			background-color="transparent"
			text-color="rgba(255,255,255,.78)"
			active-text-color="#ffffff"
			menu-trigger="click"
			router
		>
			<el-submenu v-for="(item, index) in menu" :index="item.index" :key="index">
				<template slot="title">
					<div class="oes-side__group">
						<i class="iconfont" :class="item.icon"></i>
						<span class="oes-side__group-title">{{ item.title }}</span>
					</div>
				</template>
				<el-menu-item
					v-for="(list, idx) in visibleItems(item)"
					:key="idx"
					:index="list.path"
					@click="handleTitle(item.index)"
				>
					<i :class="list.icon" class="oes-side__item-icon"></i>
					<span class="oes-side__item-label">{{ labelOf(list) }}</span>
				</el-menu-item>
			</el-submenu>
		</el-menu>
	</div>
</template>

<script>
import { mapState } from 'vuex'
export default {
	name: 'mainLeft',
	data() {
		return {}
	},
	computed: mapState(['flag', 'menu']),
	created() {
		this.addData()
	},
	methods: {
		// 只渲染带文案的条目，保持原有“无文案不展示”的行为
		visibleItems(item) {
			return (item.content || []).filter(list => list.item1 || list.item2 || list.item3)
		},
		labelOf(list) {
			return list.item1 || list.item2 || list.item3
		},
		// 点击标题传递参数给面包屑组件
		handleTitle(index) {
			this.bus.$emit('sendIndex', index)
		},
		// 按角色动态追加菜单
		addData() {
			let role = this.$cookies.get('role')
			if (role == 0) {
				this.menu.push({
					index: '5',
					title: '教师管理',
					icon: 'icon-r-user3',
					content: [
						{ item1: '教师管理', path: '/teacherManage', icon: 'el-icon-a-041' },
						{ item2: '添加教师', path: '/addTeacher', icon: 'iconfont icon-r-add' }
					]
				})
			}
			if (role == 1) {
				this.menu.push({
					index: '6',
					title: '作业批改',
					icon: 'icon-r-user3',
					content: [{ item1: '作业评分', path: '/hupingTea', icon: 'el-icon-a-041' }]
				})
			}
		}
	}
}
</script>

<style>
/* 说明：Element UI 的子元素不带本组件的 scopeId，故此处使用非 scoped 样式，
   全部选择器以 .oes-side 前缀收敛，避免污染其它页面。 */
.oes-side {
	width: 220px;
	height: 100%;
	min-height: calc(100vh - var(--oes-header-height));
	background: linear-gradient(180deg, #27408b 0%, #1f326e 100%);
	transition: width 0.2s ease;
	display: flex;
	flex-direction: column;
}

.oes-side .el-menu {
	border-right: none;
	padding: 8px 0;
	background: transparent;
}

.oes-side .el-menu:not(.el-menu--collapse) {
	width: 220px;
	flex: 1;
}

.oes-side .oes-side__group {
	display: flex;
	align-items: center;
	gap: 12px;
	width: 100%;
	overflow: hidden;
}

.oes-side .oes-side__group .iconfont {
	font-size: 18px;
	color: rgba(255, 255, 255, 0.85);
}

.oes-side .oes-side__group-title {
	font-size: 15px;
	font-weight: 600;
	letter-spacing: 0.5px;
}

.oes-side .oes-side__item-icon {
	margin-right: 8px;
	font-size: 16px;
}

.oes-side .oes-side__item-label {
	font-size: 14px;
}

.oes-side .el-submenu__title {
	height: 52px;
	line-height: 52px;
}

.oes-side .el-submenu__title:hover,
.oes-side .el-menu-item:hover {
	background: rgba(255, 255, 255, 0.08) !important;
}

.oes-side .el-menu-item {
	height: 44px;
	line-height: 44px;
	padding-left: 42px !important;
	border-left: 3px solid transparent;
}

.oes-side .el-menu-item.is-active {
	background: rgba(255, 255, 255, 0.14) !important;
	border-left-color: #7fa8ff;
}

.oes-side .el-submenu.is-opened > .el-submenu__title {
	color: #fff;
}
</style>