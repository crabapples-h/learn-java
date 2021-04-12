import Index from '@/views/manage/Index'
import subApply from '@/views/manage/sub-apply'

const manageRouter = {
    path: '/manage-index',
    component: Index,
    name: 'manage-index',
    meta: {title: '首页', icon: 'clipboard'},
    children: [
        {
            path: 'sub-apply',
            components: {innerView: subApply},
            name: 'sub-apply',
        },
    ]
}

export default manageRouter
