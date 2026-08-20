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

  // ========== 用户 user ==========
  getUserPage(params) {
    return instance({ url: SysApis.userPage, method: 'get', params })
  },
  getUserList() {
    return instance({ url: SysApis.userList, method: 'get' })
  },
  delUser(id) {
    return instance({ url: `${SysApis.delUser}/${id}`, method: 'delete' })
  },
  lockUser(id) {
    return instance({ url: `${SysApis.lockUser}/${id}`, method: 'post' })
  },
  unlockUser(id) {
    return instance({ url: `${SysApis.unlockUser}/${id}`, method: 'post' })
  },
  saveUser(data) {
    return instance({ url: SysApis.saveUser, method: 'post', data })
  },
  checkUsername(username) {
    return instance({ url: `${SysApis.checkUsername}/${username}`, method: 'get' })
  },
  resetPassword(data) {
    return instance({ url: SysApis.resetPassword, method: 'post', data })
  },
  updatePassword(data) {
    return instance({ url: SysApis.updatePassword, method: 'post', data })
  },

  // ========== 菜单 menu ==========
  getMenuTreeList() {
    return instance({ url: SysApis.menuTreeList, method: 'get' })
  },
  getMenuList() {
    return instance({ url: SysApis.menuList, method: 'get' })
  },
  getMenuListPage(params) {
    return instance({ url: SysApis.menuListPage, method: 'get', params })
  },
  getChildMenuList(params) {
    return instance({ url: SysApis.childMenuList, method: 'get', params })
  },
  delMenus(id) {
    return instance({ url: `${SysApis.delMenus}/${id}`, method: 'delete' })
  },
  saveMenus(data) {
    return instance({ url: SysApis.saveMenus, method: 'post', data })
  },
  getRoleMenusTree(roleId) {
    return instance({ url: `${SysApis.roleMenusTree}/${roleId}`, method: 'get' })
  },

  // ========== 角色 role ==========
  getRoleList() {
    return instance({ url: SysApis.roleList, method: 'get' })
  },
  getRolePage(params) {
    return instance({ url: SysApis.rolePage, method: 'get', params })
  },
  delRoles(id) {
    return instance({ url: `${SysApis.delRoles}/${id}`, method: 'delete' })
  },
  saveRoles(data) {
    return instance({ url: SysApis.saveRoles, method: 'post', data })
  },

  // ========== 字典 dict ==========
  getDictList() {
    return instance({ url: SysApis.dictList, method: 'get' })
  },
  getDictPage(params) {
    return instance({ url: SysApis.dictPage, method: 'get', params })
  },
  delDicts(id) {
    return instance({ url: `${SysApis.delDicts}/${id}`, method: 'delete' })
  },
  saveDicts(data) {
    return instance({ url: SysApis.saveDicts, method: 'post', data })
  },
  saveDictItems(data) {
    return instance({ url: SysApis.saveDictItems, method: 'post', data })
  },
  getDictItemListByCode(code) {
    return instance({ url: `${SysApis.dictItemListByCode}/${code}`, method: 'get' })
  },
  getDictItemListById(id) {
    return instance({ url: `${SysApis.dictItemListById}/${id}`, method: 'get' })
  },
  delDictItems(id) {
    return instance({ url: `${SysApis.delDictItems}/${id}`, method: 'delete' })
  },

  // ========== 租户 tenant ==========
  getTenantList() {
    return instance({ url: SysApis.tenantList, method: 'get' })
  },
  getTenantPage(params) {
    return instance({ url: SysApis.tenantPage, method: 'get', params })
  },
  delTenants(id) {
    return instance({ url: `${SysApis.delTenants}/${id}`, method: 'delete' })
  },
  saveTenants(data) {
    return instance({ url: SysApis.saveTenants, method: 'post', data })
  },

  // ========== 部门 depart ==========
  getDepartList() {
    return instance({ url: SysApis.departList, method: 'get' })
  },
  getDepartPage(params) {
    return instance({ url: SysApis.departPage, method: 'get', params })
  },
  delDeparts(id) {
    return instance({ url: `${SysApis.delDeparts}/${id}`, method: 'delete' })
  },
  saveDeparts(data) {
    return instance({ url: SysApis.saveDeparts, method: 'post', data })
  },
}

export default commonApi
