import {createApp, h} from 'vue'
import App from './App.vue'
import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/antd.css';
import '@public/color.less'

import router from './router'
import store from './store'

// import axios from '@/utils/axios'

const vue = createApp({
    render: () => h(App)
});
vue.use(Antd)
vue.use(store)
vue.use(router)
vue.mount('#app')
