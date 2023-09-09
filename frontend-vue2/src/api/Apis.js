const SysApis = {
  //系统接口 system
  login: '/api/system/login',
  logout: '/api/system/logout',
  userInfo: '/api/system/userInfo',
  permissions: '/api/system/permissions',
  checkUsername: '/api/system/checkUsername',
  //用户 user
  resetPassword: '/api/system/user/password/reset',
  updatePassword: '/api/system/user/updatePassword',

  userPage: '/api/system/user/page',
  userList: '/api/system/user/list',
  delUser: '/api/system/user/del',
  lockUser: '/api/system/user/lock',
  unlockUser: '/api/system/user/unlock',
  saveUser: '/api/system/user/save',

  //菜单 menu
  menuList: '/api/system/menu/list',
  delMenus: '/api/system/menu/remove',
  saveMenus: '/api/system/menu/save',
  roleMenus: '/api/system/menu/role',
  menusUser: '/api/system/menu/user',
  //角色 roles
  roleList: '/api/system/role/list',
  rolePage: '/api/system/role/page',
  delRoles: '/api/system/role/remove',
  saveRoles: '/api/system/role/save',
  userRoles: '/api/system/role/user',

}
export { SysApis }
