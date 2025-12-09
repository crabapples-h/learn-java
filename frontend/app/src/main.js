import App from './App.vue'
import {createApp} from 'vue';
import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/reset.css';
import {createPinia} from "pinia";
import {createMemoryHistory, createRouter} from 'vue-router'
import {routes} from "@/router/index.js";

const router = createRouter({
    history: createMemoryHistory(),
    routes,
})
const pinia = createPinia()

// 提供全局配置
const app = createApp(App);
app.use(Antd)
    .use(pinia)
    .use(router)
    .mount('#app');
