// 应用入口：插件注册、全局实例挂载、样式引入
import Vue from 'vue'
import App from './App'
import router from './router'
import * as echarts from 'echarts'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import Cookies from 'js-cookie'
import '@/../config/initialize'
import store from '@/store'
import request from '@/api/request'
import './assets/font/iconfont.css'
import './styles/index.css'

Vue.use(ElementUI)
// 用 js-cookie 适配原 vue-cookies 的 API（get 缺失返回 null）
Vue.prototype.$cookies = {
  get: key => (Cookies.get(key) === undefined ? null : Cookies.get(key)),
  set: (key, value, options) => Cookies.set(key, value, options),
  remove: key => Cookies.remove(key)
}

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
