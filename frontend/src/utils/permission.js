import Vue from 'vue'
import router from '@/router'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import storage from '@/store/storage'
import Login from '@/views/base/Login'
import Index from '@/views/manage/Index'
import notification from 'ant-design-vue/es/notification'

let staticRouter = [
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
NProgress.configure({showSpinner: false}) // 不显示进度环
// router.addRoutes(staticRouter)
let parent = {
    path: "/",
    component: Index,
    name: "首页",
    iconCls: "#icon-shouye", //图标样式class
    children: []
}

//将所有树状路由信息转换为list
function tree2list(children, routerList) {
    if (children)
        children.forEach(e => {
            let router = {
                path: e.path,
                components: {innerView: resolve => require([`@/views/${e.filePath}.vue`], resolve)},
                name: e.name,
            }
            routerList.push(router)
            if (e.children && e.children.length > 0) {
                tree2list(e.children, routerList)
            }
        })
}

router.onError((a, b, c) => {
    console.log('onError--->', a, b, c)
})
const whiteList = ['/login'] // 白名单
router.beforeEach((to, from, next) => {
    NProgress.start() // 开始进度条
    const path = to.path
    let token = storage.getToken()
    console.log('path--->', path)
    if (token) {
        if (path === '/login') {
            console.log('已经登陆过，直接进入首页')
            return next({path: '/manage/index'})
        }
        let routerList = []
        tree2list(storage.getUserMenus(), routerList)
        parent.children = routerList
        router.addRoutes(routerList)
        NProgress.done()
        console.log(1)
        const redirect = to.path
        if (to.path === redirect) {
            console.log('页面地址:-->', to)
            next()
        } else {
            next({path: redirect})
        }
    } else {
        if (whiteList.includes(path)) {
            console.log('访问地址在白名单中：', path)
            NProgress.done()
            return next();
        }
        notification.error({message: '未检测到token且访问地址不在白名单中，即将跳转到登陆页面'});
        return next({path: '/login'});
    }

});
router.afterEach(() => {
    // notification.info({message: '路由完成'})
    NProgress.done() // finish progress bar
})
router.onReady(() => {
    console.log('onReady()----------------->')
    let routerList = []
    tree2list(storage.getUserMenus(), routerList)
    // router.addRoutes(routerList)
})

/**
 * TODO vue自定义指令
 *
 * @author Mr.He
 * 2021/4/15 14:40
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mrhe
 */

// bind：只调用一次，指令第一次绑定到元素时调用。在这里可以进行一次性的初始化设置。
// inserted：被绑定元素插入父节点时调用 (仅保证父节点存在，但不一定已被插入文档中)。
// update：所在组件的 VNode 更新时调用，但是可能发生在其子 VNode 更新之前。指令的值可能发生了改变，也可能没有。但是你可以通过比较更新前后的值来忽略不必要的模板更新 (详细的钩子函数参数见下)
// 注册一个全局自定义指令 `v-auth`
const auth = Vue.directive('auth', {
    bind: (el, binding) => {
        return;
        let permissions = getPermissions()
        if (!permissions) {
            return
        }
        let exist = permissions.includes(binding.arg)
        if (!exist) {
            setTimeout(() => {
                if (el.parentNode == null) {
                    el.style.display = 'none'
                } else {
                    el.parentNode.removeChild(el)
                }
            }, 10)
        }
    },
})
export {auth}
