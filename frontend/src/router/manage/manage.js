import Index from '@/views/manage/Index'

const manageRouter = {
    path: '/manage-index',
    component: Index,
    name: 'manage-index',
    meta: {
        title: '首页',
        icon: 'clipboard'
    },
    children: []
}

export default manageRouter
