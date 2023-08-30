import Vue from 'vue'
import App from './App'
import Antd from 'ant-design-vue'
import router from '@/router'
import store from '@/store'
import request from '@/utils/request'
import axios from 'axios'
import 'ant-design-vue/dist/antd.css'
import '@public/color.less'
import '@/utils/permission'
import storage from '@/store/storage'
import VueCanvasPoster from 'vue-canvas-poster'

Vue.use(VueCanvasPoster)

Vue.config.productionTip = false
Vue.use(Antd)
Vue.prototype.$http = request
Vue.prototype.$axios = axios
// 自定义属性v-auth，用于判断用户是否有权限操作
Vue.directive('auth', {
  bind: (el, binding) => {
    let permissions = storage.getPermissions()
    if (!permissions) {
      return
    }
    let exist = permissions.includes(binding.arg)
    if (!exist) {
      setTimeout(() => {
        if (el.parentNode == null) {
          el.style.display = 'none'
        } else {
          el.parentNode.removeChild(el)
        }
      }, 10)
    }
  },
})
new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app')
