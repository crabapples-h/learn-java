import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/sys/Login.vue'
import manageRouter from "@/router/manage/manage"
// export const constantRoutes = [
//     {
//         path: '/datas',
//         component: Layout,
//         redirect: '/datas',
//         name: 'Datas',
//         meta: {
//             title: '数据',
//             icon: 'clipboard'
//         }
//     {
//         path: '/401',
//         component: () => import('@/views/error-page/401'),
//         hidden: true
//     },
// ]
Vue.use(VueRouter)

const routes = [
    manageRouter,
    {
        path: '/',
        name: 'Home',
        component: Home
    },
    {
        path: '/login',
        name: 'Login',
        component: Login,
        meta: {title: '积分', icon: ''},
    },
    {
        path: '/404',
        // redirect: '/login',
        component: () => import('@/views/sys/404'),
        hidden: true
    },
    {path: '*', redirect: '/404', hidden: true}
    // 404 page must be placed at the end !!!
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})
//获取原型对象上的push函数
const originalPush = VueRouter.prototype.push
//修改原型对象中的push方法
VueRouter.prototype.push = function push(location) {
    return originalPush.call(this, location).catch(err => err)
}
export default router
