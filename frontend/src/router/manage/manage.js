import Index from '@/views/manage/Index'
import UserList from '@/views/manage/UserList'
import RolesList from '@/views/manage/RolesList'

const manageRouter = {
    path: '/manage-index',
    component: Index,
    name: 'manage-index',
    meta: {title: '首页', icon: 'clipboard'},
    children: [
        {
            path: 'user-list',
            components: {innerView: UserList},
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
