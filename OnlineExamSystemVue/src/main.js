// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import echarts from 'echarts'
import axios from 'axios'
import './assets/font/iconfont.css'
import ElementUI, { Message } from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import VueCookies from 'vue-cookies'
import '@/../config/initialize'
import store from '@/vuex/store'

Vue.use(ElementUI)
Vue.use(VueCookies)

Vue.config.productionTip = false
Vue.prototype.bus = new Vue()
Vue.prototype.$echarts = echarts
Vue.prototype.$axios = axios

// 全局响应拦截：会话失效（401）时清理本地登录痕迹并回到登录页，
// 越权（403）给出统一提示，避免每个组件各自处理。
axios.interceptors.response.use(
  response => response,
  error => {
    const status = error.response && error.response.status
    if (status === 401) {
      document.cookie = 'rb_token=; path=/; max-age=0'
      document.cookie = 'rb_role=; path=/; max-age=0'
      if (router.currentRoute.path !== '/') {
        Message.warning('登录状态已失效，请重新登录')
        router.replace('/')
      }
    } else if (status === 403) {
      Message.error('无权限访问该资源')
    }
    return Promise.reject(error)
  }
)

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App),
  components: { App },
  template: '<App/>'
})
