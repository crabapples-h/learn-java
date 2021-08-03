import {createRouter, createWebHistory} from 'vue-router'
import Index from '@/views/manage/Index1'

const routes = [
    {
        path: '/',
        // hidden: true,
        // redirect: '/login',
        component: Index,
    },
    {
        path: '/login',
        hidden: true,
        component: () => import(/* webpackChunkName: "static" */ '@/views/manage/Index'),
    },
    {
        path: '/manage/index',
        name: '/manage-index',
        component: () => import(/* webpackChunkName: "static" */ '@/views/About.vue'),
    },
    {
        path: '/404',
        hidden: true,
        component: () => import(/* webpackChunkName: "static" */'@/views/base/404'),
    },
    {
        path: '/401',
        hidden: true,
        component: () => import(/* webpackChunkName: "static" */'@/views/base/401'),
    },
    // {path: '*', redirect: '/404', hidden: true},
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

// //获取原型对象上的push函数
// const originalPush = router.push
// //修改原型对象中的push方法
// router.push = function push(location) {
//     return originalPush.call(this, location).catch(err => err)
// }

export default router
