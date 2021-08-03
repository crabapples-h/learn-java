import Vue from 'vue'
import App from './App'
import Antd from 'ant-design-vue';
import router from '@/router'
import store from '@/store'
import axios from '@/utils/axios'
import 'ant-design-vue/dist/antd.css';
import '@public/color.less'
import '@/utils/permission'

Vue.config.productionTip = false
Vue.use(Antd)
Vue.prototype.$http = axios
new Vue({
    router,
    store,
    render: h => h(App),
}).$mount('#app')
