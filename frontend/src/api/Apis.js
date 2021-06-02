const SysApis = {
    //sys
    login: '/api/sys/login',
    logout: '/api/sys/logout',
    userInfo: '/api/sys/userInfo',
    menus: '/api/sys/menus/user',
    permissions: '/api/sys/permissions',
    //user
    addUser: '/api/sys/user/add',
    editUser: '/api/sys/user/edit',
    resetPassword: '/api/sys/user/password/reset',
    updatePassword: '/api/sys/updatePassword',
    userListPage: '/api/sys/user/page',
    delUser: '/api/sys/user/del',
    lockUser: '/api/sys/user/lock',
    unlockUser: '/api/sys/user/unlock/',
    checkUsername: '/api/sys/checkUsername',
    //menus
    menusList: '/api/sys/menus/list',
    menusListPage: '/api/sys/menus/page',
    delMenus: '/api/sys/menus/remove/',
    saveMenus: '/api/sys/menus/save',
    //roles
    rolesList: '/api/sys/roles/list',
    rolesListPage: '/api/sys/roles/page',
    delRoles: '/api/sys/roles/remove/',
    saveRoles: '/api/sys/roles/save',
}
export {SysApis}