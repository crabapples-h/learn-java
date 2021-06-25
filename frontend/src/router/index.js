import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/base/Login'
import Index from '@/views/manage/Index'

Vue.use(VueRouter)
const baseRoutes = [
    {
        path: '/',
        redirect: '/index',
    },
    {
        path: '/manage/index',
        name: 'manage-index',
        component: Index
    },
    {
        path: '/login',
        name: 'login',
        component: Login,
        meta: {title: '登录', icon: ''},
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

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes: baseRoutes,
});

//获取原型对象上的push函数
const originalPush = VueRouter.prototype.push
//修改原型对象中的push方法
VueRouter.prototype.push = function push(location) {
    return originalPush.call(this, location).catch(err => err)
}
export {router}
