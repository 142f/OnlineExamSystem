import axios from 'axios'
import { Message } from 'element-ui'
import router from '@/router'

/**
 * 全局 axios 实例。
 * 所有页面统一通过 Vue.prototype.$axios 使用该实例，避免各处重复配置。
 */
const request = axios.create({
  baseURL: '',
  timeout: 15000
})

request.interceptors.response.use(
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
    } else if (status >= 500) {
      Message.error('服务异常，请稍后重试')
    }
    return Promise.reject(error)
  }
)

export default request
