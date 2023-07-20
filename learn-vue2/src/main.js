import Vue from 'vue'
import router from './router'
import store from './store'

import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/antd.css';
import App from './App.vue'

Vue.config.productionTip = false;

Vue.use(Antd);
Vue.config.productionTip = false
import VueCanvasPoster from 'vue-canvas-poster'
Vue.use(VueCanvasPoster)
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
