import Vue from 'vue'
import App from './App'
import VueAxios from 'vue-axios'
import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/antd.css';
import router from './router'
import AxiosUtils from '@/utils/AxiosUtils'
// import settings from 'settings'

Vue.config.productionTip = false
Vue.use(
    Antd,
    VueAxios,
    // settings,
)

Vue.prototype.$http = AxiosUtils
new Vue({
    router,
    render: h => h(App)
}).$mount('#app')
