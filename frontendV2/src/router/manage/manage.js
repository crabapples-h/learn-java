import Index from '@/views/manage/Index'
import UserList from '@/views/manage/UsersList'
import RolesList from '@/views/manage/RolesList'
import MenusList from '@/views/manage/MenusList'
import AxiosUtils from "@/utils/request";

// AxiosUtils.get('/api/user/info').then(res => {
//     console.log('测试123--->', res)
// })
const manageRouter = {
    path: '/',
    component: Index,
    name: 'layout',
    meta: {title: '首页', icon: 'clipboard'},
    children: [
        {
            path: '/sys/user-list',
            components: {innerView: () => import('@/views/manage/UsersList')},
            // components: {innerView: RolesList},
            name: 'user-list',
        },
        {
            path: '/sys/roles-list',
            components: {innerView: RolesList},
            name: 'roles-list',
        },
        {
            path: '/sys/menus-list',
            components: {innerView: MenusList},
            name: 'menus-list',
        },
    ]
}
const manageRouter1 = [
    {
        path: '/sys/user-list',
        component: UserList,
        name: 'user-list',
    },
    {
        path: '/sys/roles-list',
        component: import('@/views/manage/RolesList'),
        name: 'roles-list',
    },
    {
        path: '/sys/menus-list',
        component: import('@/views/manage/MenusList'),
        name: 'menus-list',
    },
]

export {manageRouter, manageRouter1}
