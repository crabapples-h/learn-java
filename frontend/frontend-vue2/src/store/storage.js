/**
 * TODO storage工具
 *
 * @author Mr.He
 * 2021/4/22 18:47
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mrhe
 */

export default {
  getToken: () => localStorage.getItem('TOKEN'),
  setToken: (token) => localStorage.setItem('TOKEN', token),

  getUserBaseInfo: () => JSON.parse(localStorage.getItem('USER_BASE_INFO')),
  setUserBaseInfo: (data) => localStorage.setItem('USER_BASE_INFO', JSON.stringify(data)),

  getUserRoles: () => JSON.parse(localStorage.getItem('USER_ROLE_LIST')),
  setUserRoles: (data) => localStorage.setItem('USER_ROLE_LIST', JSON.stringify(data)),

  getUserMenusList: () => JSON.parse(localStorage.getItem('USER_MENU_LIST')),
  setUserMenusList: (data) => localStorage.setItem('USER_MENU_LIST', JSON.stringify(data)),

  getUserMenusTree: () => JSON.parse(localStorage.getItem('USER_MENU_TREE')),
  setUserMenusTree: (data) => localStorage.setItem('USER_MENU_TREE', JSON.stringify(data)),

  getPermissions: () => JSON.parse(localStorage.getItem('USER_PERMISSION_LIST')),
  setPermissions: (data) => localStorage.setItem('USER_PERMISSION_LIST', JSON.stringify(data)),

  logout: () => localStorage.removeItem('TOKEN'),
}
