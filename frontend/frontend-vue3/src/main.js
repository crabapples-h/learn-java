import { createApp } from 'vue'
import Antd from 'ant-design-vue'
import axios from 'axios'
import App from './App.vue'
import router from '@/router'
import { pinia } from '@/store'
import request from '@/utils/request'
import '@/utils/permission'
import 'ant-design-vue/dist/reset.css'
import '@public/color.less'
import '@public/iconfont/icon-antd'
import '@public/iconfont/icon-lolita'
import '@public/iconfont/icon-cute'

const app = createApp(App)

app.use(pinia)
app.use(router)
app.use(Antd)
app.config.globalProperties.$http = request
app.config.globalProperties.$axios = axios

app.mount('#app')
