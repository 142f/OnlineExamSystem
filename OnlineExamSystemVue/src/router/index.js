import Vue from 'vue'
import Router from 'vue-router'
Vue.use(Router)

const VueRouterPush = Router.prototype.push

// 避免重复跳转同一路由时抛出 NavigationDuplicated
Router.prototype.push = function push(to) {
	return VueRouterPush.call(this, to).catch(err => err)
}

/**
 * 路由表：
 *  - 路径保持与 SEO / 旧链接一致，不做变更
 *  - meta.title 供面包屑与浏览器标题使用
 *  - 页面统一放在 views/ 下，按角色分目录
 */
export default new Router({
	routes: [{
			path: '/',
			name: 'login',
			component: () => import('@/views/login/LoginView'),
			meta: { title: '登录' }
		},
		{
			// 教师端与管理端复用同一套布局，管理员多出教师管理入口
			path: '/index',
			component: () => import('@/views/admin/AdminLayout'),
			meta: { title: '管理后台' },
			children: [{
					path: '/',
					component: () => import('@/views/admin/WelcomeView'),
					meta: { title: '首页' }
				},
				{
					path: '/hupingTea',
					component: () => import('@/views/teacher/GradeReviewView'),
					meta: { title: '作业批改' }
				},
				{
					path: '/selectExam',
					component: () => import('@/views/teacher/SelectExamView'),
					meta: { title: '作业查询' }
				},
				{
					path: '/addExam',
					component: () => import('@/views/teacher/AddExamView'),
					meta: { title: '添加作业' }
				},
				{
					path: '/selectAnswer',
					component: () => import('@/views/teacher/SelectAnswerView'),
					meta: { title: '题目列表' }
				},
				{
					path: '/addAnswer',
					component: () => import('@/views/teacher/AddAnswerView'),
					meta: { title: '新增题目' }
				},
				{
					path: '/editAnswerChildren',
					component: () => import('@/views/teacher/EditAnswerChildrenView'),
					meta: { title: '编辑题目' }
				},
				{
					path: '/addAnswerChildren',
					component: () => import('@/views/teacher/AddAnswerChildrenView'),
					meta: { title: '试卷组题' }
				},
				{
					path: '/grade',
					component: () => import('@/views/teacher/GradeChartView'),
					meta: { title: '成绩查询' }
				},
				{
					path: '/selectExamToPart',
					component: () => import('@/views/teacher/SelectExamToPartView'),
					meta: { title: '成绩分段' }
				},
				{
					path: '/scorePart',
					component: () => import('@/views/teacher/ScorePartChartView'),
					meta: { title: '分数分布' }
				},
				{
					path: '/allStudentsGrade',
					component: () => import('@/views/teacher/AllStudentsGradeView'),
					meta: { title: '学生成绩' }
				},
				{
					path: '/studentManage',
					component: () => import('@/views/teacher/StudentManageView'),
					meta: { title: '学生管理' }
				},
				{
					path: '/addStudent',
					component: () => import('@/views/teacher/AddStudentView'),
					meta: { title: '添加学生' }
				},
				{
					path: '/teacherManage',
					component: () => import('@/views/admin/TeacherManageView'),
					meta: { title: '教师管理' }
				},
				{
					path: '/addTeacher',
					component: () => import('@/views/admin/AddTeacherView'),
					meta: { title: '添加教师' }
				}
			]
		},
		{
			path: '/student',
			component: () => import('@/views/student/StudentLayout'),
			meta: { title: '学生端' },
			children: [{
					path: '/',
					component: () => import('@/views/student/MyExamView'),
					meta: { title: '我的作业' }
				},
				{
					path: '/startExam',
					component: () => import('@/views/student/StartExamView'),
					meta: { title: '开始答题' }
				},
				{
					path: '/manager',
					component: () => import('@/views/student/ChangePasswordView'),
					meta: { title: '修改密码' }
				},
				{
					path: '/examMsg',
					component: () => import('@/views/student/ExamMsgView'),
					meta: { title: '作业信息' }
				},
				{
					path: '/message',
					component: () => import('@/views/student/MessageView'),
					meta: { title: '留言板' }
				},
				{
					path: '/huping',
					component: () => import('@/views/student/PeerReviewView'),
					meta: { title: '作业互评' }
				},
				{
					path: '/studentScore',
					component: () => import('@/views/student/AnswerScoreView'),
					meta: { title: '答题得分' }
				},
				{
					path: '/scoreTable',
					component: () => import('@/views/student/ScoreTableView'),
					meta: { title: '成绩单' }
				},
				{
					path: '/scoreTable2',
					component: () => import('@/views/student/ScoreTable2View'),
					meta: { title: '详细成绩' }
				}
			]
		},
		{
			path: '/answer',
			component: () => import('@/views/student/AnswerView'),
			meta: { title: '在线答题' }
		}
	]
})
