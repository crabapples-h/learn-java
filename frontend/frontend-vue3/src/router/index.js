import { h } from 'vue'
import { createRouter, createWebHistory, RouterView } from 'vue-router'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import { Modal } from 'ant-design-vue'
import storage from '@/store/storage'

NProgress.configure({ showSpinner: false })

const pageModules = import.meta.glob('@/views/**/*.vue')

const staticRouter = [
  {
    path: '/',
    redirect: '/login',
    meta: { title: '首页', icon: 'clipboard' },
    hidden: true,
  },
  {
    path: '/login',
    name: 'login',
    component: () => import('@/views/base/Login.vue'),
    meta: { title: '登录', icon: 'clipboard' },
    hidden: true,
  },
  {
    path: '/manage/index',
    name: 'manage-index',
    component: () => import('@/views/manage/Index.vue'),
    redirect: '/manage/welcome',
    meta: { title: '首页', icon: 'clipboard' },
    children: [
      {
        path: '/manage/welcome',
        name: 'manage-welcome',
        component: () => import('@/views/manage/Welcome.vue'),
        meta: { title: '首页', icon: 'clipboard' },
      },
    ],
  },
  {
    path: '/loading',
    name: 'loading',
    component: () => import('@/views/base/Loading.vue'),
    meta: { title: '页面初始化中', icon: 'clipboard' },
    hidden: true,
  },
  {
    path: '/401',
    name: 'error-401',
    component: () => import('@/views/base/Error-401.vue'),
    meta: { title: '未获授权', icon: 'clipboard' },
    hidden: true,
  },
  {
    path: '/404',
    name: 'error-404',
    component: () => import('@/views/base/Error-404.vue'),
    meta: { title: '找不到页面', icon: 'clipboard' },
    hidden: true,
  },
]

const whiteList = ['/login', '/404', '/401', '']

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: staticRouter,
})

const resolveView = filePath => {
  const normalized = filePath?.endsWith('.vue') ? filePath : `${filePath}.vue`
  return pageModules[`/src/views/${normalized}`] || (() => import('@/views/base/Error-404.vue'))
}

const layoutRoute = {
  path: '/',
  name: 'dynamic-layout',
  component: { render: () => h(RouterView) },
  children: [],
}

function initRouter(menus = []) {
  if (router.hasRoute('dynamic-layout')) {
    router.removeRoute('dynamic-layout')
  }

  layoutRoute.children = menus
    .filter(item => item.menusType !== 2)
    .map(item => ({
      path: item.path || '',
      name: item.path || item.name,
      component: resolveView(item.filePath),
      meta: {
        title: item.name,
        icon: item.icon || 'clipboard',
        id: item.id,
        pid: item.pid,
      },
      hidden: item.showFlag === 1,
    }))

  router.addRoute(layoutRoute)
}

router.beforeEach((to, from, next) => {
  Modal.destroyAll()
  window.document.title = to.meta.title || '管理系统'
  NProgress.start()

  const token = storage.getToken()
  if (whiteList.includes(to.path)) {
    NProgress.done()
    next()
    return
  }

  if (!token) {
    next({ path: '/login' })
    return
  }

  next()
})

router.afterEach(() => {
  NProgress.done()
})

router.addRoute({
  path: '/:pathMatch(.*)*',
  redirect: '/404',
  hidden: true,
})

export { initRouter, staticRouter, whiteList }
export default router
