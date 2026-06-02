import instance from '@/utils/request'
import storage from '@/store/storage'
import { SysApis } from '@/api/Apis'

const commonApi = {
  filePreviewAddress() {
    return instance({
      url: SysApis.filePreviewAddress,
      method: 'get',
    })
  },
  serverAddress() {
    return instance({
      url: SysApis.serverAddress,
      method: 'get',
    })
  },
  login(data) {
    return instance({
      url: SysApis.login,
      method: 'post',
      data,
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
  getUserInfo() {
    return instance({
      url: SysApis.userInfo,
      method: 'get',
    })
  },
  getUserRoles() {
    return instance({
      url: SysApis.userRoles,
      method: 'get',
    })
  },
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
  getUserPermissions() {
    return instance({
      url: SysApis.permissions,
      method: 'get',
    })
  },
  async refreshSysData() {
    const permissions = await this.getUserPermissions()
    if (permissions.status === 200) {
      storage.setPermissions(permissions.data)
    }

    const menusTree = await this.getUserMenusTree()
    if (menusTree.status === 200) {
      storage.setUserMenusTree(menusTree.data)
    }
  },
}

export default commonApi
