import Index from '@/views/manage/Index'
import UserList from '@/views/manage/UserList'

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
    ]
}

export default manageRouter
