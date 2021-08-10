import Vue from 'vue'
import VueRouter from 'vue-router'
import Index from "@/views/manage/Index";
import Login from "@/views/base/Login";
import Welcome from "@/views/manage/Welcome";
import RolesList from "@/views/manage/RolesList";
import NProgress from "nprogress";
import storage from "@/store/storage";
import state from "@/store/index";
import notification from "ant-design-vue/lib/notification";

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
Vue.use(VueRouter)
let router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes: staticRouter,
});

//将树状路由转换为list
function tree2list(data) {
    let array = []
    data.map(e => {
        if (e.path) {
            console.log(e.path, `@/views/${e.filePath}.vue`)
            if (e.path !== '/manage/roles-list') {
                array.push({
                    path: e.path,
                    components: {innerView: require([`@/views/${e.filePath}.vue`])},
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


router.beforeEach((to, from, next) => {
    const path = to.path
    console.log('path--->', path)
    console.log('storage.getUserMenus()-->',storage.getUserMenus())
    console.log('storage.getUserMenus(1111111111111)-->',state.getters.token)
    // let routerList = tree2list(state.getUserMenus())
    // console.log('routerList-->', routerList)
    // router.options.routes = staticRouter.concat(routerList)
    // routerList.forEach(e => {
    //     router.addRoute(e)
    // })
    // router.addRoutes(routerList)
    console.log('routes-->', router.options.routes)
    next()

})
router.afterEach(() => {
    // notification.info({message: '路由完成'})
    NProgress.done() // finish progress bar
})
router.onReady(() => {
    console.log('onReady()----------------->')
    let routerList = []
    // tree2list(storage.getUserMenus(), routerList)
    // router.addRoutes(routerList)
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
