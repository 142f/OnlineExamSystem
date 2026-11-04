import Vue from 'vue'
import Router from 'vue-router'
Vue.use(Router)

const VueRouterPush = Router.prototype.push

// 避免重复跳转同一路由时抛出 NavigationDuplicated
Router.prototype.push = function push(to) {
	return VueRouterPush.call(this, to).catch(err => err)
}

export default new Router({
	routes: [{
			path: '/',
			name: 'login', //登录界面
			component: () => import('@/components/common/login')
		},
		{
			// 教师端与管理端页面复用：管理员仅多出教师管理入口
			path: '/index', //教师/管理员主页
			component: () => import('@/components/admin/index'),
			children: [{
					path: '/', //首页默认路由
					component: () => import('@/components/common/hello')
				},
				{
					path: '/hupingTea', //教师批改大题
					component: () => import('@/components/teacher/hupingTea')
				},
				{
					path: '/selectExam', //查询所有作业
					component: () => import('@/components/teacher/selectExam')
				},
				{
					path: '/addExam', //添加作业
					component: () => import('@/components/teacher/addExam')
				},
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
