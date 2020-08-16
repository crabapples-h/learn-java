import Vue from 'vue'
import App from './App'
import axios from 'axios'
import VueAxios from 'vue-axios'
import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/antd.css';
import router from './router'

Vue.config.productionTip = false
Vue.use(
    Antd,
    VueAxios,
)

Vue.prototype.$http = axios
new Vue({
    router,
    render: h => h(App)
}).$mount('#app')
