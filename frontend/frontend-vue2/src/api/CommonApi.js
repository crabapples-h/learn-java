import instance from '@/utils/request'
import storage from '@/store/storage'
import { SysApis } from '@/api/Apis'

const commonApi = {
  login(data) {
    return instance({
      url: SysApis.login,
      method: 'post',
      data: data
    })
  },
  logout() {
    sessionStorage.clear()
    localStorage.clear()
    return instance({
      url: SysApis.logout,
      method: 'post',
    })
  },
  //获取用户信息
  getUserInfo() {
    return instance({
      url: SysApis.userInfo,
      method: 'get',
    })
  },
  //获取用户拥有的角色
  getUserRoles() {
    return instance({
      url: SysApis.userRoles,
      method: 'get',
    })
  },
  //获取用户拥有的菜单，并根据菜单生成路由表
  getUserMenusTree() {
    return instance({
      url: SysApis.menusTreeUser,
      method: 'get',
    })
  },
  getUserMenusList() {
    return instance({
      url: SysApis.menusListUser,
      method: 'get',
    })
  },
  //获取用户拥有的权限(按钮)
  getUserPermissions() {
    return instance({
      url: SysApis.permissions,
      method: 'get',
    })
  },
  refreshSysData() {
    this.getUserPermissions().then(res => {
      if (res.status === 200) {
        storage.setPermissions(res.data)
      }
    })
    this.getUserMenusTree().then(res => {
      if (res.status === 200) {
        storage.setUserMenusTree(res.data)
      }
    })
  }
}
export default commonApi
