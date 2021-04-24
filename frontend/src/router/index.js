import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/sys/Login'
import Index from '@/views/manage/Index'
import RoleList from '@/views/manage/RolesList'
import {getRouterMap, getToken} from '@/utils/sessionUtils'

Vue.use(VueRouter)
const baseRoutes = [
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
]
const errorRoutes = [
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
let customRouter = {
    path: '/',
    component: resolve => require(['@/views/manage/Index.vue'], resolve),
    name: 'layout',
    meta: {title: '首页', icon: 'clipboard'},
    children: null
}

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes: baseRoutes,
});

export function $addRouters(children) {
    // 重置路由规则
    router.matcher = new VueRouter({
        routes: baseRoutes
    }).matcher
    if (!children) {
        return
    }
    console.log('重置路由-->', children)
    customRouter.children = children.map(e => {
        return {
            path: e.path,
            components: {innerView: resolve => require([`@/views/${e.filePath}.vue`], resolve)},
            name: e.name,
        }
    })
    customRouter.children.push({
        path: '/sys/role-list',
        components: {innerView: RoleList},
        name: 'e.name',
    })
    let routerMap = [customRouter]
    router.addRoutes(routerMap)
    router.addRoutes(errorRoutes)
}

router.onReady(() => {
    let routerMap = getRouterMap()
    if (!!routerMap) {
        $addRouters(routerMap)
    }
})

router.beforeEach((to, form, next) => {
    let token = getToken()
    //动态修改页面title
    document.title = to.meta.title ? to.meta.title : document.title
    // 当没有本地token ， 并且目标路由不是login时执行
    if (to.path !== "/login" && !token) {
        return next({path: '/login'});
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
export {router}
