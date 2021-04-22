import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/sys/Login'
import Index from '@/views/manage/Index'
import AxiosUtils from '@/utils/AxiosUtils'

Vue.use(VueRouter)
const routes = [
    {
        path: '/',
        redirect: '/index',
    },
    {
        path: '/index',
        name: 'index',
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
        component: () => import('@/views/sys/404'),
        hidden: true
    },
    {
        path: '/401',
        component: () => import('@/views/sys/401'),
        hidden: true
    },
    {path: '*', redirect: '/404', hidden: true}
    // 404 page must be placed at the end !!!
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
});

router.beforeEach((to, form, next) => {
    let token = sessionStorage.getItem("crabapples-token")
    console.log('路由守卫.to-->', to.path)
    // console.log(sessionStorage.getItem('token'))
    //动态修改页面title
    document.title = to.meta.title ? to.meta.title : document.title
    // 当没有本地token ， 并且目标路由不是login时执行
    if (to.path !== "/login" && !token) {
        console.log(12333)
        // return next({path: '/login'});
        // 当有token 没有数据时执行
    }
    next();
});
//获取原型对象上的push函数
const originalPush = VueRouter.prototype.push
//修改原型对象中的push方法
VueRouter.prototype.push = function push(location) {
    return originalPush.call(this, location).catch(err => err)
}
export default router
