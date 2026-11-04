// 应用入口：插件注册、全局实例挂载、样式引入
import Vue from 'vue'
import App from './App'
import router from './router'
import echarts from 'echarts'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import VueCookies from 'vue-cookies'
import '@/../config/initialize'
import store from '@/store'
import request from '@/api/request'
import './assets/font/iconfont.css'
import './styles/index.css'

Vue.use(ElementUI)
Vue.use(VueCookies)

Vue.config.productionTip = false
Vue.prototype.bus = new Vue()
Vue.prototype.$echarts = echarts
Vue.prototype.$axios = request

// 统一维护浏览器标题（路由 meta.title）
router.afterEach(route => {
  const title = route.meta && route.meta.title
  document.title = title ? title + ' - 作业互评系统' : '作业互评系统'
})

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App),
  components: { App },
  template: '<App/>'
})
