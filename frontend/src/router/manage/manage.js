import Index from '@/views/manage/Index'
import UserList from '@/views/manage/UserList'
import RolesList from '@/views/manage/RolesList'
console.log(666666666666)
const manageRouter = {
    path: '/manage-index',
    component: Index,
    name: 'manage-index',
    meta: {title: '首页', icon: 'clipboard'},
    children: [
        {
            path: 'user-list',
            components: {innerView: () => import('@/views/manage/UserList')},
            // components: {innerView: RolesList},
            name: 'user-list',
        },
        {
            path: 'roles-list',
            components: {innerView: RolesList},
            name: 'roles-list',
        },
    ]
}

export default manageRouter
