const SysApis = {
    //system
    login: '/api/system/login',
    logout: '/api/system/logout',
    userInfo: '/api/system/userInfo',
    menus: '/api/system/menus/list/user',
    permissions: '/api/system/permissions',
    checkUsername: '/api/system/checkUsername',
    //user
    addUser: '/api/system/user/add',
    editUser: '/api/system/user/edit',
    resetPassword: '/api/system/user/password/reset',
    updatePassword: '/api/system/user/updatePassword',
    userListPage: '/api/system/user/page',
    delUser: '/api/system/user/del',
    lockUser: '/api/system/user/lock',
    unlockUser: '/api/system/user/unlock/',
    //menus
    menusList: '/api/system/menus/list',
    menusListPage: '/api/system/menus/page',
    delMenus: '/api/system/menus/remove/',
    saveMenus: '/api/system/menus/save',
    //roles
    rolesList: '/api/system/roles/list',
    rolesListPage: '/api/system/roles/page',
    delRoles: '/api/system/roles/remove/',
    saveRoles: '/api/system/roles/save',
}
export {SysApis}
