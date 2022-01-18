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
import Login from "@/views/base/Login";
import Welcome from "@/views/manage/Welcome";
import RolesList from "@/views/manage/RolesList";
import NProgress from "nprogress";
import 'nprogress/nprogress.css'
import storage from "@/store/storage";
import store from "@/store";

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
        meta: {title: '首页', icon: 'clipboar d'},
        component: Index,
        children: [
            {
                path: '/manage/roles-list',
                name: '/manage-roles-list',
                meta: {title: '欢迎使用22', icon: 'clipboard'},
                components: {innerView: RolesList},
            },
        ]
    },
    {
        path: '/manage/welcome',
        name: '/manage-welcome',
        meta: {title: '欢迎使用', icon: 'clipboard'},
        component: Welcome,
    },

]
const errorRouter = [
    {
        path: '/404',
        meta: {title: '找不到页面', icon: 'clipboard'},
        component: () => import('@/views/base/404'),
        hidden: true
    },
    {
        path: '/401',
        meta: {title: '未获授权', icon: 'clipboard'},
        component: () => import('@/views/base/401'),
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
    data.map(e => {
        if (e.path) {
            if (e.path !== '/manage/roles-list') {
                array.push({
                    path: e.path,
                    component: resolve => require([`@/views/${e.filePath}.vue`], resolve),
                    name: e.name,
                })
            }
        }
        if (e.children) {
            array.push(...tree2list(e.children))
            e.children = null
        }
    })
    return array
}

const whiteList = ['/login'] // 白名单

router.beforeEach((to, from, next) => {
    NProgress.start()
    const path = to.path
    console.log('路由地址----->path:', path)
    if (storage.getToken()) {
        console.log('token:', store.getters.TOKEN)
    } else {
        if (whiteList.includes(path)) {
            console.log('访问地址在白名单中：', path)
            NProgress.done()
            return next();
        } else {
            console.error('t1oken不存在，且path不在白名单中，跳转重新登陆')
            next({path: '/login'})
        }
    }
    next()
})
router.afterEach(() => {
    NProgress.done() // finish progress bar
})
router.onReady(() => {
    console.log('onReady()----------------->加载路由')
    customRouter.children = tree2list(storage.getUserMenus())
    router.addRoute(customRouter)
    errorRouter.forEach(e => {
        router.addRoute(e)
    })
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
export default router
