import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/sys/Login'
import Index from '@/views/manage/Index'
import {getRouterMap, getToken} from '@/utils/sessionUtils'

Vue.use(VueRouter)
const sysRoutes = [
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
    sysRoutes
});

export function $addRouters(children) {
    console.log('$addRouters-->', {children})
    customRouter.children = children.map(e => {
        return {
            path: e.path,
            components: {innerView: resolve => require([`@/views/${e.filePath}.vue`], resolve)},
            name: e.name,
        }
    })
    let routerMap = [customRouter]
    router.addRoutes(routerMap)
}

router.onReady(() => {
    console.log('路由加载-->', 'router.onReady')
    let routerMap = getRouterMap()
    if (!!routerMap) {
        $addRouters(routerMap)
    }
    window.addEventListener("beforeunload", () => {
        //重点：暂存页面刷新的地址
        sessionStorage.setItem("pathName", window.location.pathname)
        let path = sessionStorage.getItem("pathName")
        console.log(path)
        // debugger
        if (!!path) {
            return next({path: path});
        }
        // sessionStorage.setItem("routesInfo", JSON.stringify(this.$store.getters.getRouterArray))
    })
    // const status = true // 判断用户已登录且已有权限
    // if (status) {
    //     store.dispatch('getJurisdiction') // 请求动态路由
    //         .then(e => {
    //             router.addRoutes(e.list) // 添加动态路由,这里不必用$addRoutes，因为刷新后就没有上一次的动态路由，故不必清除
    //         })
    // }
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
