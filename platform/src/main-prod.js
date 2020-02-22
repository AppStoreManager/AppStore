import Vue from 'vue'
import App from './App.vue'
import router from './router'
// import './plugins/element.js'
// 导入全局样式表
import './assets/css/global.css'
import axios from 'axios'

// 配置请求的根路径
axios.defaults.baseURL = 'http://127.0.0.1:8888/api/private/v1/'
// 请求拦截器，其他api必须有token才能获取值
axios.interceptors.request.use(config => {
  // console.log(config)
  config.headers.Authorization = window.sessionStorage.getItem('token')
  // 在最后必须返回config
  return config
})
Vue.prototype.$http = axios

Vue.config.productionTip = false

Vue.filter('dateFormat', function (originVal) {
  const dt = new Date(originVal)
  const y = dt.getFullYear()
  const m = (dt.getMonth() + 1 + '').padStart(2, '0')
  const d = (dt.getDay() + '').padStart(2, '0')
  const hh = (dt.getHours() + '').padStart(2, '0')
  const mm = (dt.getMinutes() + '').padStart(2, '0')
  const ss = (dt.getSeconds() + '').padStart(2, '0')

  return y + '-' + m + '-' + d + ' ' + hh + ':' + mm + ':' + ss
})

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
