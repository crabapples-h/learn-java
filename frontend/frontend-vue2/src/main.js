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
// antd图标库
import '@public/iconfont/icon-antd'
// lolita图标库
import '@public/iconfont/icon-lolita'
// 可爱图标库
import '@public/iconfont/icon-cute'

Vue.use(VueCanvasPoster)

Vue.config.productionTip = false
Vue.use(Antd)
Vue.prototype.$http = request
Vue.prototype.$axios = axios
new Vue({
    router,
    store,
    render: h => h(App),
}).$mount('#app')
