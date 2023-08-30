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

  getUserMenus: () => JSON.parse(localStorage.getItem('MENUS')),
  setUserMenus: (data) => localStorage.setItem('MENUS', JSON.stringify(data)),

  getPermissions: () => JSON.parse(localStorage.getItem('PERMISSIONS')),
  setPermissions: (data) => localStorage.setItem('PERMISSIONS', JSON.stringify(data)),

  getRouters: () => JSON.parse(localStorage.getItem('ROUTERS')),
  setRouters: (data) => localStorage.setItem('ROUTERS', JSON.stringify(data)),

  logout: () => localStorage.removeItem('TOKEN'),
}
