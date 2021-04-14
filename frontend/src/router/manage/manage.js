import Index from '@/views/manage/Index'
import UserList from '@/views/manage/UserList'
import RolesList from '@/views/manage/RolesList'
import MenusList from '@/views/manage/MenusList'
import AxiosUtils from "@/utils/AxiosUtils";

AxiosUtils.get('/api/user/info').then(res => {
    console.log('测试123--->', res)
})
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
        {
            path: 'menus-list',
            components: {innerView: MenusList},
            name: 'menus-list',
        },
    ]
}

export default manageRouter
