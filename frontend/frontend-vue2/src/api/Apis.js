const SysApis = {
    /**
     * /system开头的是系统接口,经网关后转发到/api/system调用api-system服务
     */

    //系统接口 system
    login: '/system/api/system/login',
    logout: '/system/api/system/logout',
    userInfo: '/system/api/system/userInfo',
    permissions: '/system/api/system/permissions',
    checkUsername: '/system/api/system/checkUsername',
    resetPassword: '/system/api/system/user/password/reset',
    updatePassword: '/system/api/system/user/updatePassword',
    serverAddress: '/system/api/system/server/address',
    filePreviewAddress: '/system/api/system/preview/address',

    //用户 user
    userPage: '/system/api/system/user/page',
    userList: '/system/api/system/user/list',
    delUser: '/system/api/system/user/remove',
    lockUser: '/system/api/system/user/lock',
    unlockUser: '/system/api/system/user/unlock',
    saveUser: '/system/api/system/user/save',

    //菜单 menu
    menuTreeList: '/system/api/system/menu/tree/list',
    menuTreePage: '/system/api/system/menu/tree/page',
    menuList: '/system/api/system/menu/tree/list',
    menuListPage: '/system/api/system/menu/list/page',
    childMenuList: '/system/api/system/menu/child/list',

    roleMenusTree: '/system/api/system/menu/tree/role',
    menusTreeUser: '/system/api/system/menu/tree/user',
    menusListUser: '/system/api/system/menu/list/user',
    delMenus: '/system/api/system/menu/remove',
    saveMenus: '/system/api/system/menu/save',
    roleMenus: '/system/api/system/menu/tree/role',

    //角色 role
    roleList: '/system/api/system/role/list',
    rolePage: '/system/api/system/role/page',
    delRoles: '/system/api/system/role/remove',
    saveRoles: '/system/api/system/role/save',
    userRoles: '/system/api/system/role/user',
    //字典 dict
    dictList: '/system/api/system/dict/list',
    dictPage: '/system/api/system/dict/page',
    delDicts: '/system/api/system/dict/remove',
    saveDicts: '/system/api/system/dict/save',
    saveDictItems: '/system/api/system/dict/item/save',
    dictItemListByCode: '/system/api/system/dict/item/list/code',
    dictItemListById: '/system/api/system/dict/item/list/id',
    delDictItems: '/system/api/system/dict/item/remove',
    //租户 tenant
    tenantList: '/system/api/system/tenant/list',
    tenantPage: '/system/api/system/tenant/page',
    delTenants: '/system/api/system/tenant/remove',
    saveTenants: '/system/api/system/tenant/save',
    //部门 depart
    departList: '/system/api/system/depart/list',
    departPage: '/system/api/system/depart/page',
    delDeparts: '/system/api/system/depart/remove',
    saveDeparts: '/system/api/system/depart/save',
}

export {SysApis}
