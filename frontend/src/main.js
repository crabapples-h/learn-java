import Vue from 'vue'
import Vuex from 'vuex'
import App from './App'
import VueAxios from 'vue-axios'
import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/antd.css';
import router from './router'
import AxiosUtils from '@/utils/AxiosUtils'
// import settings from 'settings'

Vue.config.productionTip = false
Vue.use(Vuex)
Vue.use(
    Antd,
    VueAxios,
    // settings,
)
const store = new Vuex.Store({
    state: {
        token: '',
        userInfo: {}
    }
})
Vue.prototype.$http = AxiosUtils
new Vue({
    router,
    render: h => h(App),
    store,
}).$mount('#app')
