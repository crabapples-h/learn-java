import Vue from 'vue'
import VueRouter from 'vue-router'
import Index from "@/views/manage/Index";
import Login from "@/views/base/Login";

Vue.use(VueRouter)
let router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes: baseRoutes,
});
// import {createRouter, createWebHistory} from 'vue-router'
// // 还有 createWebHashHistory 和 createMemoryHistory
//
// let router = createRouter({
//     history: createWebHistory(),
//     routes: [],
// })
const baseRoutes = [
    {
        path: '/',
        redirect: '/login',
        hidden: true
    },
    {
        path: '/login',
        component: Login,
        hidden: true
    },
    {
        path: '/manage/index',
        name: '/manage-index',
        component: Index,
    },
    {
        path: '/404',
        component: () => import('@/views/base/404'),
        hidden: true
    },
    {
        path: '/401',
        component: () => import('@/views/base/401'),
        hidden: true
    },
    {path: '*', redirect: '/404', hidden: true}
]

//获取原型对象上的push函数
const originalPush = VueRouter.prototype.push
//修改原型对象中的push方法
VueRouter.prototype.push = function push(location) {
    return originalPush.call(this, location).catch(err => err)
}
export default router
