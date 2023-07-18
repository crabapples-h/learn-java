import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

Vue.config.productionTip = false
import VueCanvasPoster from 'vue-canvas-poster'
Vue.use(VueCanvasPoster)
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
