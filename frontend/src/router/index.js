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
let routers = []

//将所有路由数据存放在一个数组中(包括子路由也存放在这个数组中)
function createRouteMap(children) {
    children.forEach(e => {
        let router = {
            path: e.path,
            components: {innerView: resolve => require([`@/views/${e.filePath}.vue`], resolve)},
            name: e.name,
        }
        routers.push(router)
        if (e.children && e.children.length > 0) {
            createRouteMap(e.children)
        }
    })
}

export function $addRouters(children) {
    // 重置基本路由
    router.matcher = new VueRouter({
        routes: baseRoutes
    }).matcher
    if (!children) {
        return
    }
    // 重置自定义路由
    routers = []
    createRouteMap(children)
    customRouter.children = routers
    let routerMap = [customRouter]
    console.log('routerMap-->', routerMap)
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
    let path = to.path
    if (path.startsWith('/manage') && path !== "/login" && !token) {
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
