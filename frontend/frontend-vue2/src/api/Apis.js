const SysApis = {
    /**
     * /system开头的是系统接口,经网关后转发到/api/system调用api-system服务
     */

    //系统接口 system
    login: '/api/system/login',
    logout: '/api/system/logout',
    userInfo: '/api/system/userInfo',
    permissions: '/api/system/permissions',
    checkUsername: '/api/system/checkUsername',
    resetPassword: '/api/system/user/password/reset',
    updatePassword: '/api/system/user/updatePassword',
    serverAddress: '/api/system/server/address',
    filePreviewAddress: '/api/system/preview/address',

    //用户 user
    userPage: '/api/system/user/page',
    userList: '/api/system/user/list',
    delUser: '/api/system/user/remove',
    lockUser: '/api/system/user/lock',
    unlockUser: '/api/system/user/unlock',
    saveUser: '/api/system/user/save',

    //菜单 menu
    menuTreeList: '/api/system/menu/tree/list',
    menuTreePage: '/api/system/menu/tree/page',
    menuList: '/api/system/menu/tree/list',
    menuListPage: '/api/system/menu/list/page',
    childMenuList: '/api/system/menu/child/list',

    roleMenusTree: '/api/system/menu/tree/role',
    menusTreeUser: '/api/system/menu/tree/user',
    menusListUser: '/api/system/menu/list/user',
    delMenus: '/api/system/menu/remove',
    saveMenus: '/api/system/menu/save',
    roleMenus: '/api/system/menu/tree/role',

    //角色 role
    roleList: '/api/system/role/list',
    rolePage: '/api/system/role/page',
    delRoles: '/api/system/role/remove',
    saveRoles: '/api/system/role/save',
    userRoles: '/api/system/role/user',
    //字典 dict
    dictList: '/api/system/dict/list',
    dictPage: '/api/system/dict/page',
    delDicts: '/api/system/dict/remove',
    saveDicts: '/api/system/dict/save',
    saveDictItems: '/api/system/dict/item/save',
    dictItemListByCode: '/api/system/dict/item/list/code',
    dictItemListById: '/api/system/dict/item/list/id',
    delDictItems: '/api/system/dict/item/remove',
    //租户 tenant
    tenantList: '/api/system/tenant/list',
    tenantPage: '/api/system/tenant/page',
    delTenants: '/api/system/tenant/remove',
    saveTenants: '/api/system/tenant/save',
    //部门 depart
    departList: '/api/system/depart/list',
    departPage: '/api/system/depart/page',
    delDeparts: '/api/system/depart/remove',
    saveDeparts: '/api/system/depart/save',
}

export {SysApis}
