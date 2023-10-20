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
import Index from '@/views/manage/Index'
import Welcome from '@/views/manage/Welcome'
import Login from '@/views/base/Login'
import Error404 from '@/views/base/Error-404'
import Error401 from '@/views/base/Error-401'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import storage from '@/store/storage'
import store from '@/store'

Vue.use(VueRouter)
// 不显示进度环
NProgress.configure({ showSpinner: false })
// 静态路由表
const staticRouter = [
  {
    path: '/',
    meta: { title: '首页', icon: 'clipboard' },
    redirect: '/login',
    hidden: true
  },
  {
    path: '/login',
    meta: { title: '登陆', icon: 'clipboard' },
    component: Login,
    hidden: true
  },
  {
    path: '/manage/index',
    name: '/manage-index',
    meta: { title: '首页', icon: 'clipboard' },
    component: Index,
    children: [
      {
        path: '/manage/welcome',
        name: '/manage-welcome',
        meta: { title: '首页', icon: 'clipboard' },
        component: Welcome,
      },
    ]
  },
  {
    path: '/loading',
    meta: { title: '页面初始化中', icon: 'clipboard' },
    component: () => import('@/views/base/Loading'),
    hidden: true
  },
  {
    path: '/404',
    meta: { title: '找不到页面', icon: 'clipboard' },
    component: Error404,
    hidden: true
  },
  {
    path: '/401',
    meta: { title: '未获授权', icon: 'clipboard' },
    component: Error401,
    hidden: true
  },
  // 处理刷新页面404问题，添加通配符跳转到404页面后会导致页面刷新时，动态路由还未初始化，但是页面直接访问刷新前的url，就会匹配到404页面
  // { path: '*', redirect: '/404', hidden: true }
]
// 白名单路由
const whiteList = ['/login', '/404', '/401']
// 错误页面
const errorRouter = [
  {
    path: '/404',
    meta: { title: '找不到页面', icon: 'clipboard' },
    component: resolve => require(['@/views/base/Error-404.vue'], resolve),
    hidden: true
  },
  {
    path: '/401',
    meta: { title: '未获授权', icon: 'clipboard' },
    component: Error401,
    hidden: true
  },
  { path: '*', redirect: '/404', hidden: true }
]
let customRouter = {
  path: '/',
  component: resolve => require(['@/views/manage/Index.vue'], resolve),
  name: 'layout',
  meta: { title: '首页', icon: 'clipboard' },
  children: null
}
let router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: staticRouter,
})

// 森林转list
function forestToList(forest) {
  const result = []

  function traverse(node) {
    if (node.hasOwnProperty('path')) {
      if (node.menusType !== 2) {
        result.push({
          path: node.path,
          component: resolve => require([`@/views/${node.filePath}.vue`], resolve),
          name: node.name,
          meta: { title: node.name, icon: 'clipboard' },
          hidden: node.showFlag === 1
        })
      }
    }
    if (node && node.children) {
      node.children.forEach(child => traverse(child))
    }
  }

  forest.forEach(tree => traverse(tree))
  return result
}

// 修改页面title
function changePageTitle(e) {
  window.document.title = e.meta.title
}

// 修改页面icon
function changePageIcon(e) {
  let link = document.querySelector('link[rel*=\'icon\']')
  link.href = 'https://www.baidu.com/img/flexible/logo/pc/result.png'
  document.getElementsByTagName('head')[0].appendChild(link)
}

router.beforeEach((to, from, next) => {
  changePageTitle(to)
  // changePageIcon(to)
  NProgress.start()
  const path = to.path
  const token = store.getters.TOKEN || storage.getToken()
  // console.log('路由地址----->path:', path)
  if (whiteList.includes(path)) {
    NProgress.done()
    return next()
  } else if (!token) {
    console.warn('token不存在，且path不在白名单中，跳转登陆页面')
    next({ path: '/login' })
    return
  }
  next()
})
router.afterEach((route) => {
  NProgress.done() // finish progress bar
})

//渲染动态路由
function initRouter(menus) {
  store.dispatch('LOAD_FINISH', false)
  // console.log('开始初始化路由表,菜单数据:', menus)
  const newRouter = forestToList(menus)
  newRouter.push({ path: '*', redirect: '/404', hidden: true })
  customRouter.children = newRouter
  customRouter.children.push(...errorRouter)
  router.addRoute(customRouter)
  store.dispatch('LOAD_FINISH', true)
  console.log('动态路由初始化完成')
  console.log('新的路由表:-->', customRouter)
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
export { initRouter, whiteList }
export default router
