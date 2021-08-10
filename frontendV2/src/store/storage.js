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

    getUserInfo: () => JSON.parse(localStorage.getItem('USER_INFO')),
    setUserInfo: (userInfo) => localStorage.setItem('USER_INFO', JSON.stringify(userInfo)),

    getUserMenus: () => JSON.parse(localStorage.getItem('MENUS')),
    setUserMenus: (menus) => localStorage.setItem('MENUS', JSON.stringify(menus)),

    getPermissions: () => JSON.parse(localStorage.getItem('PERMISSIONS')),
    setPermissions: (permissions) => localStorage.setItem('PERMISSIONS', JSON.stringify(permissions)),

    getRouters: () => JSON.parse(localStorage.getItem('ROUTERS')),
    setRouters: (routers) => localStorage.setItem('ROUTERS', JSON.stringify(routers)),

}
