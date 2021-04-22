import Vue from 'vue'
import App from './App'
import Antd from 'ant-design-vue';
import {router, $addRouters} from './router'
import AxiosUtils from '@/utils/AxiosUtils'
import {store} from '@/utils/store'
import {auth} from '@/utils/v-auth'
import 'ant-design-vue/dist/antd.css';
import '../public/color.less'

Vue.config.productionTip = false
Vue.use(Antd)
Vue.prototype.$http = AxiosUtils
new Vue({
    router,
    store,
    auth,
    render: h => h(App),
}).$mount('#app')
