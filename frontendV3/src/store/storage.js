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
    getToken: () => sessionStorage.getItem('token'),
    setToken: (token) => sessionStorage.setItem('token', token),

    getUserInfo: () => JSON.parse(sessionStorage.getItem('userInfo')),
    setUserInfo: (userInfo) => sessionStorage.setItem('userInfo', JSON.stringify(userInfo)),

    getUserMenus: () => JSON.parse(sessionStorage.getItem('menus')),
    setUserMenus: (menus) => sessionStorage.setItem('menus', JSON.stringify(menus)),

    getPermissions: () => JSON.parse(sessionStorage.getItem('permissions')),
    setPermissions: (permissions) => sessionStorage.setItem('permissions', JSON.stringify(permissions)),

    getRouters: () => JSON.parse(sessionStorage.getItem('routers')),
    setRouters: (routers) => sessionStorage.setItem('routers', JSON.stringify(routers)),

}
