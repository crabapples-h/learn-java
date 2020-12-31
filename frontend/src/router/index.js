import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/Login.vue'
import manageRouter from "@/router/manage/manage"
// export const constantRoutes = [
//     // 商品
//     storeRouter,
//     // 数据
//     {
//         path: '/datas',
//         component: Layout,
//         redirect: '/datas',
//         name: 'Datas',
//         meta: {
//             title: '数据',
//             icon: 'clipboard'
//         },
//         children: [
//             {
//                 path: 'transaction',
//                 component: () => import('@/views/datas/transaction/index'),
//                 name: 'Transaction',
//                 meta: { title: '交易数据', icon: '' },
//                 children: [
//                     {
//                         path: 'transactionorder',
//                         component: () => import('@/views/datas/transaction/order/index'),
//                         name: 'transactionOrder',
//                         meta: { title: '订单统计', icon: '' }
//                     },
//                     {
//                         path: 'transactiongoods',
//                         component: () => import('@/views/datas/transaction/goods/index'),
//                         name: 'transactionGoods',
//                         meta: { title: '商品统计', icon: '' }
//                     }
//                 ]
//             },
//             {
//                 path: 'bargain',
//                 component: () => import('@/views/marketing/bargain/index'),
//                 name: 'datasBargain',
//                 meta: { title: '砍价管理', icon: '' },
//                 alwaysShow: true,
//                 children: [
//                     {
//                         path: 'bargainGoods',
//                         component: () => import('@/views/marketing/bargain/index'),
//                         name: 'datasBargainGoods',
//                         meta: { title: '砍价商品', icon: '' }
//                     }
//                 ]
//             },
//             {
//                 path: 'groupBuy',
//                 component: () => import('@/views/marketing/groupBuy/index'),
//                 name: 'datasGroupBuy',
//                 meta: { title: '拼团管理', icon: '' },
//                 children: [
//                     {
//                         path: 'groupGoods',
//                         component: () => import('@/views/marketing/groupBuy/goods/index'),
//                         name: 'DatasGroupGoods',
//                         meta: { title: '拼团商品', icon: '' }
//                     },
//                     {
//                         path: 'groupList',
//                         component: () => import('@/views/marketing/groupBuy/list/list'),
//                         name: 'DatasGroupList',
//                         meta: { title: '拼团列表', icon: '' }
//                     }
//                 ]
//             },
//             {
//                 path: 'spike',
//                 component: () => import('@/views/marketing/spike/index'),
//                 name: 'DatasSpike',
//                 meta: { title: '秒杀管理', icon: '' },
//                 children: [
//                     {
//                         path: 'spikeconfig',
//                         component: () => import('@/views/marketing/spike/config/index'),
//                         name: 'DatasSpikeConfig',
//                         meta: { title: '秒杀配置', icon: '' }
//                     },
//                     {
//                         path: 'bargainList',
//                         component: () => import('@/views/marketing/spike/googs/index'),
//                         name: 'DatasBargainList',
//                         meta: { title: '秒杀商品', icon: '' }
//                     }
//                 ]
//             },
//             {
//                 path: 'integral',
//                 component: () => import('@/views/marketing/integral/index'),
//                 name: 'DatasIntegral',
//                 meta: { title: '积分', icon: '' },
//                 children: [
//                     {
//                         path: 'integralconfig',
//                         component: () => import('@/views/marketing/integral/config/index'),
//                         name: 'DatasIntegralConfig',
//                         meta: { title: '积分配置', icon: '' }
//                     },
//                     {
//                         path: 'integrallog',
//                         component: () => import('@/views/marketing/integral/integralLog/index'),
//                         name: 'DatasIntegralLog',
//                         meta: { title: '积分日志', icon: '' }
//                     }
//                 ]
//             }
//         ]
//     },
//     {
//         path: '/404',
//         component: () => import('@/views/error-page/404'),
//         hidden: true
//     },
//     {
//         path: '/redirect',
//         component: Layout,
//         hidden: true,
//         children: [
//             {
//                 path: '/redirect/:path(.*)',
//                 component: () => import('@/views/redirect/index')
//             }
//         ]
//     },
//     {
//         path: '/auth-send',
//         component: () => import('@/views/mobile/auth-send'),
//         hidden: true
//     },
//     {
//         path: '/login',
//         component: () => import('@/views/login/index'),
//         hidden: true
//     },
//     {
//         path: '/auth-redirect',
//         component: () => import('@/views/login/auth-redirect'),
//         hidden: true
//     },
//     {
//         path: '/401',
//         component: () => import('@/views/error-page/401'),
//         hidden: true
//     },
//     {
//         path: '/',
//         component: Layout,
//         redirect: '/dashboard',
//         children: [
//             {
//                 path: 'dashboard',
//                 component: () => import('@/views/dashboard/index'),
//                 name: 'Dashboard',
//                 meta: { title: '控制台', icon: 'dashboard', affix: true }
//             }
//         ]
//     },
//     {
//         path: '/setting/uploadPicture',
//         component: () => import('@/components/uploadPicture/index.vue'),
//         name: 'uploadPicture'
//     },
//     // 404 page must be placed at the end !!!
//     { path: '*', redirect: '/404', hidden: true }
// ]
Vue.use(VueRouter)

const routes = [
    manageRouter,
    {
        path: '/',
        name: 'Home',
        component: Home
    },
    {
        path: '/login',
        name: 'Login',
        component: Login
    },
    {
        path: '/about',
        name: 'About',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
    },

]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export default router
