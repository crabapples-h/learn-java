const SysApis = {
  //系统接口 system
  login: '/api/system/login',
  logout: '/api/system/logout',
  userInfo: '/api/system/userInfo',
  permissions: '/api/system/permissions',
  checkUsername: '/api/system/checkUsername',
  resetPassword: '/api/system/user/password/reset',
  updatePassword: '/api/system/user/updatePassword',

  //用户 user
  userPage: '/api/system/user/page',
  userList: '/api/system/user/list',
  delUser: '/api/system/user/del',
  lockUser: '/api/system/user/lock',
  unlockUser: '/api/system/user/unlock',
  saveUser: '/api/system/user/save',

  //菜单 menu
  menuList: '/api/system/menu/list',
  menuPage: '/api/system/menu/page',
  delMenus: '/api/system/menu/remove',
  saveMenus: '/api/system/menu/save',
  roleMenus: '/api/system/menu/role',
  menusUser: '/api/system/menu/user',
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
}
export { SysApis }
