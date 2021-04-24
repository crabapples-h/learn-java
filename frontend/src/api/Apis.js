const SysApis = {
    //user
    addUser: '/api/sys/user/add',
    editUser: '/api/sys/user/edit',
    resetPassword: '/api/user/password/reset',
    userListPage: '/api/sys/user/page',
    delUser: '/api/sys/user/del',
    lockUser: '/api/sys/user/unlock',
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