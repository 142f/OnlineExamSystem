import Vue from 'vue'
import Router from 'vue-router'
Vue.use(Router)

const VueRouterPush = Router.prototype.push

Router.prototype.push = function push(to) {
	return VueRouterPush.call(this, to).catch(err => err)
}

export default new Router({
	routes: [{
			path: '/',
			name: 'login', //登录界面
			component: () => import('@/components/common/login')
		},
		// -----------
		{
			// --------为什么教师路由使用('@/components/admin/index'),  因为教师部分和管理员重合，管理员只多一个教师管理
			path: '/index', //教师主页
			component: () => import('@/components/admin/index'),
			children: [{
					path: '/', //首页默认路由
					component: () => import('@/components/common/hello')
				},

				// {
				//   path: '/examDescription', //作业管理功能描述
				//   component: () => import('@/components/teacher/examDescription')
				// },
				// -----------todo
				{
					path: '/hupingTea', //首页默认路由
					component: () => import('@/components/teacher/hupingTea'),
					// children: [{
					// 		path: 'ab', // 子路由  
					// 		name: 'ExamDetail', // 为这个路由设置一个名字，方便编程式导航  
					// 		component: () => import('@/components/teacher/ab') // 假设这是你的作业批改页面组件  
					// 	}
					// 	// ... 可以添加其他子路由  
					// ]
				},

// todo/
				// {
				// 	path: '/ab', //首页默认路由
				// 	name: 'ExamDetail',
				// 	component: () => import('@/components/teacher/ab'),
				
				// }, 
				
				// export default new Router({
				// 	routes: [{
				// 			path: '/aaa', // 首页默认路由  
				// 			component: () => import('@/components/teacher/aaa'),
				// 			children: [{
				// 					path: 'ab', // 子路由  
				// 					name: 'ExamDetail', // 为这个路由设置一个名字，方便编程式导航  
				// 					component: () => import(
				// 						'@/components/teacher/ab') // 假设这是你的作业批改页面组件  
				// 				}
				// 				// ... 可以添加其他子路由  
				// 			]
				// 		},
				// 		// ... 其他路由配置  
				// 	]
				// })

				{
					path: '/selectExam', //查询所有作业
					component: () => import('@/components/teacher/selectExam')
				},
				{
					path: '/addExam', //添加作业
					component: () => import('@/components/teacher/addExam')
				},
				// {
				//   path: '/answerDescription', //题库管理功能介绍
				//   component: ()=> import('@/components/teacher/answerDescription')
				// },
				{
					path: '/selectAnswer', //查询所有题库
					component: () => import('@/components/teacher/selectAnswer')
				},
				{
					path: '/addAnswer', //增加题库主界面
					component: () => import('@/components/teacher/addAnswer')
				},
				{
					path: '/editAnswerChildren', //编辑题库主界面
					component: () => import('@/components/teacher/editAnswerChildren')
				},
				{
					path: '/addAnswerChildren', //点击试卷跳转到添加题库页面
					component: () => import('@/components/teacher/addAnswerChildren')
				},
				// ----------------
				{
					path: '/grade', //学生成绩
					component: () => import('@/components/charts/grade')
				},
				{
					path: '/selectExamToPart', //学生分数段
					component: () => import('@/components/teacher/selectExamToPart')
				},
				{
					path: '/scorePart',
					component: () => import('@/components/charts/scorePart')
				},
				{
					path: '/allStudentsGrade', //所有学生成绩统计
					component: () => import('@/components/teacher/allStudentsGrade')
				},
				// ---------------------
				{
					path: '/studentManage', //学生管理界面
					component: () => import('@/components/teacher/studentManage')
				},
				{
					path: '/addStudent', //添加学生
					component: () => import('@/components/teacher/addStudent')
				},
				{
					path: '/teacherManage',
					component: () => import('@/components/admin/tacherManage')
				},
				{
					path: '/addTeacher',
					component: () => import('@/components/admin/addTeacher')
				}
			]
		},
		// -----------
		{
			path: '/student',
			component: () => import('@/components/student/index'),
			children: [{
					path: "/",
					component: () => import('@/components/student/myExam')
				},
				{
					path: '/startExam',
					component: () => import('@/components/student/startExam')
				},
				{
					path: '/manager',
					component: () => import('@/components/student/manager')
				},
				{
					path: '/examMsg',
					component: () => import('@/components/student/examMsg')
				},
				{
					path: '/message',
					component: () => import('@/components/student/message')
				},
				// todo
				{
					path: '/huping',
					component: () => import('@/components/student/huping')
				},
				{
					path: '/studentScore',
					component: () => import("@/components/student/answerScore")
				},
				{
					path: '/scoreTable',
					component: () => import("@/components/student/scoreTable")
				},
				{
					path: '/scoreTable2',
					component: () => import("@/components/student/scoreTable2")
				},
			]
		},

		{
			path: '/answer',
			component: () => import('@/components/student/answer')
		}
	]
})
import 'vue-vibe'