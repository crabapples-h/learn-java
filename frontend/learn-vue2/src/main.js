import Vue from 'vue'
import router from './router'
import store from './store'
import axios from 'axios'
import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/antd.css';
import App from './App.vue'
import VueCanvasPoster from 'vue-canvas-poster'
import VConsole from "vconsole";

Vue.config.productionTip = false;

Vue.use(Antd);
Vue.prototype.$axios = axios
Vue.config.productionTip = false

new VConsole()

Vue.use(VueCanvasPoster)
new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')
