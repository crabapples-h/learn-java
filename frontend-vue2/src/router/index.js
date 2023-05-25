/**
 * TODO
 *
 * @author Mr.He
 * 2021/8/12 23:54
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mrhe
 */
import Vue from 'vue'
import VueRouter from 'vue-router'
import Index from "@/views/manage/Index";
import Welcome from "@/views/manage/Welcome";
import Login from "@/views/base/Login";
import Error404 from "@/views/base/Error-404";
import Error401 from "@/views/base/Error-401";
import NProgress from "nprogress";
import 'nprogress/nprogress.css'
import storage from "@/store/storage";
import store from "@/store";
import settings, {log, error, warn} from "../../settings";
import axios from "axios";
import CommonApi from "@/api/CommonApi";

Vue.use(VueRouter)

NProgress.configure({showSpinner: false}) // 不显示进度环
const staticRouter = [
    {
        path: '/',
        meta: {title: '首页', icon: 'clipboard'},
        redirect: '/login',
        hidden: true
    },
    {
        path: '/login',
        meta: {title: '登陆', icon: 'clipboard'},
        component: Login,
        hidden: true
    },
    {
        path: '/manage/index',
        name: '/manage-index',
        meta: {title: '首页', icon: 'clipboard'},
        component: Index,
        children: [
            {
                path: '/manage/welcome',
                name: '/manage-welcome',
                meta: {title: '首页', icon: 'clipboard'},
                component: Welcome,
            },
        ]
    },
    {
        path: '/loading',
        meta: {title: '页面初始化中', icon: 'clipboard'},
        component: () => import('@/views/base/Loading'),
        hidden: true
    },
    {
        path: '/404',
        meta: {title: '找不到页面', icon: 'clipboard'},
        component: Error404,
        hidden: true
    },
    {
        path: '/401',
        meta: {title: '未获授权', icon: 'clipboard'},
        component: Error401,
        hidden: true
    },
    {path: '*', redirect: '/404', hidden: true}
]
const errorRouter = [
    {
        path: '/404',
        meta: {title: '找不到页面', icon: 'clipboard'},
        component: resolve => require(['@/views/base/Error-404.vue'], resolve),
        hidden: true
    },
    {
        path: '/401',
        meta: {title: '未获授权', icon: 'clipboard'},
        component: Error401,
        hidden: true
    },
    {path: '*', redirect: '/404', hidden: true}
]
let customRouter = {
    path: '/',
    component: resolve => require(['@/views/manage/Index.vue'], resolve),
    name: 'layout',
    meta: {title: '首页', icon: 'clipboard'},
    children: null
}
let router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes: staticRouter,
});

//将树状路由转换为list
function tree2list(data) {
    if (!data) {
        return []
    }
    let array = []
    data.forEach(e => {
        if (e && e.url) {
            array.push({
                path: e.url,
                component: resolve => require([`@/views/${e.componentPath}.vue`], resolve),
                name: e.name,
                meta: {title: e.name, icon: 'clipboard'},
                hidden: e.hidden
            })
        }
        if (e && e.children) {
            array.push(...tree2list(e.children))
            e.children = null
        }
    })
    return array
}

const whiteList = ['/login', '/404', '/401'] // 白名单

function changePageTitle(e) {
    window.document.title = e.meta.title
}

function changePageIcon(e) {
    let link = document.querySelector("link[rel*='icon']")
    link.href = 'https://www.baidu.com/img/flexible/logo/pc/result.png'
    document.getElementsByTagName('head')[0].appendChild(link)
}

router.beforeEach((to, from, next) => {
    changePageTitle(to)
    // changePageIcon(to)
    NProgress.start()
    const path = to.path
    const token = localStorage.getItem("TOKEN")
    if (whiteList.includes(path)) {
        log('访问地址在白名单中：', path)
        NProgress.done()
        return next();
    } else if (!token) {
        warn('token不存在，且path不在白名单中，跳转重新登陆')
        next({path: '/login'})
    }
    next()
})
router.afterEach(() => {
    NProgress.done() // finish progress bar
})

function initRouter() {
    CommonApi.getUserMenus().then(result => {
        customRouter.children = tree2list(result.data)
        customRouter.children.push(...errorRouter)
        router.addRoute(customRouter)
        console.log('初始化动态路由表完成',customRouter)
    })
}

router.onReady(() => {
    // console.log('onReady()----------------->加载路由')
})
//获取原型对象上的push函数
const originalPush = VueRouter.prototype.push
const originalReplace = VueRouter.prototype.replace
//修改原型对象中的push方法
VueRouter.prototype.push = function push(location) {
    return originalPush.call(this, location).catch(err => err)
}
// replace
VueRouter.prototype.replace = function push(location, onResolve, onReject) {
    if (onResolve || onReject) return originalReplace.call(this, location, onResolve, onReject)
    return originalReplace.call(this, location).catch(err => err)
}
export {initRouter}
export default router
