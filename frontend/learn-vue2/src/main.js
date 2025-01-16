// 注：useComponents 需放最上面，优先注册懒加载组件
import Antd from 'ant-design-vue';
Vue.use(Antd);

import Vue from 'vue'
import router from './router'
import store from './store'
import axios from 'axios'

import App from './App.vue'
import VueCanvasPoster from 'vue-canvas-poster'
// 在main.js引入
import { useAntd } from 'k-form-design/packages/core/useComponents'
import KFormDesign from 'k-form-design/packages/use.js'
Vue.use(KFormDesign)
import 'k-form-design/lib/k-form-design.css'
import 'ant-design-vue/dist/antd.css';
useAntd(Vue)

Vue.config.productionTip = false;

Vue.prototype.$axios = axios
Vue.config.productionTip = false

import VConsole from "vconsole";
new VConsole()

Vue.use(VueCanvasPoster)
new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')
