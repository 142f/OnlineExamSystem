/*
 * @Description: 
 * @Author: Rabbiter
 * @Date: 2023-03-08 20:38:49
 */
import VUE from 'vue'
import VUEX from 'vuex'

VUE.use(VUEX)

const state = {
	isPractice: false, //练习模式标志
	flag: false, //菜单栏左右滑动标志
	userInfo: null,
	menu: [{
			index: '1',
			title: '作业管理',
			icon: 'icon-r-paper',
			content: [{
				item2: '作业查询',
				path: '/selectExam',
				icon: "iconfont icon-r-find"
			}, {
				item3: '添加作业',
				path: '/addExam',
				icon: "iconfont icon-r-add"
			}],
		},
		{
			index: '2',
			title: '题库管理',
			icon: 'icon-r-building',
			content: [{
				item2: '题目列表',
				path: '/selectAnswer',
				icon: "iconfont icon-r-list"
			}, {
				item3: '新增题目',
				path: '/addAnswer',
				icon: "iconfont icon-r-add"
			}, {
				path: '/addAnswerChildren'
			}],
		},
		{
			index: '3',
			title: '成绩查询',
			icon: 'icon-r-find',
			content: [{
				item1: '学生成绩查询',
				path: '/allStudentsGrade',
				icon: "iconfont icon-r-find"
			}, {
				path: '/grade'
			}, {
				item2: '成绩分段查询',
				path: '/selectExamToPart',
				icon: "iconfont icon-r-find"
			}, {
				path: '/scorePart'
			}],
		},
		{
			index: '4',
			title: '学生管理',
			icon: 'icon-r-team',
			content: [{
				item1: '学生管理',
				path: '/studentManage',
				icon: "iconfont icon-r-user2"
			}, {
				item2: '添加学生',
				path: '/addStudent',
				icon: "iconfont icon-r-add"
			}],
		},
		// {
		//   index: '5',
		//   title: '教师管理',
		//   icon: 'icon-Userselect',
		//   content:[{item1:'教师管理',path:'/teacherManage'},{item2: '添加教师',path: '/addTeacher'}],
		// },
		// {
		//   index: '7',
		//   title: '模块管理',
		//   icon: 'icon-module4mokuai',
		//   content:[{item1:'模块操作',path:'/module'}],
		// }
	],
}
const mutations = {
	practice(state, status) {
		state.isPractice = status
	},
	toggle(state) {
		state.flag = !state.flag
	},
	changeUserInfo(state, info) {
		state.userInfo = info
	}
}
const getters = {

}

import '@/../config/initialize'
const actions = {
	getUserInfo(context, info) {
		context.commit('changeUserInfo', info)
	},
	getPractice(context, status) {
		context.commit('practice', status)
	}
}

// export default new VUEX.Store({
//   state,
//   mutations,
//   getters,
//   actions,
//   // store
// })
// Vue.prototype.$global = {  
//   cid: null  
// };

export default new VUEX.Store({
  state,
  mutations,
  getters,
  actions,
  // store
})

// export default new VUEX.Store({
// 	state: {
// 		cid: null,
// 		// 其他状态...  
// 	},
// 	mutations: {
// 		setCid(state, cid) {
// 			state.cid = cid;
// 		},
// 		// 其他 mutations...  
// 	},
// 	getters,
// 	actions,
// 	// store

// })



// // 在组件中设置 cid  
// this.$store.commit('setCid', resData.adminId); // 或者 teacherId, studentId  

// // 在其他组件中获取 cid  
// this.$store.state.cid;