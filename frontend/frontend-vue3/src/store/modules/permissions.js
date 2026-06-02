import { defineStore } from 'pinia'
import commonApi from '@/api/CommonApi'
import storage from '@/store/storage'
import { initRouter } from '@/router'

const formatMenusTree = data => data
  .map(item => {
    if (item.showFlag !== 0 || item.menusType === 2) {
      return null
    }

    const menu = {
      id: item.id,
      pid: item.pid,
      name: item.name,
      icon: item.icon,
      path: item.path,
      sort: item.sort,
      link: item.link,
      menusType: item.menusType,
      filePath: item.filePath,
      permission: item.permission,
      hidden: false,
    }

    if (item.children?.length) {
      const children = formatMenusTree(item.children).filter(Boolean)
      if (children.length) {
        menu.children = children
      }
    }

    return menu
  })
  .filter(Boolean)
  .sort((a, b) => (a.sort || 0) - (b.sort || 0))

export const usePermissionStore = defineStore('permissions', {
  state: () => ({
    menusTree: storage.getUserMenusTree() || [],
    menusList: storage.getUserMenusList() || [],
    permissions: storage.getPermissions() || [],
  }),
  getters: {
    MENUS_TREE: state => state.menusTree,
    MENUS_LIST: state => state.menusList,
    PERMISSIONS: state => state.permissions,
  },
  actions: {
    setMenusList(menus) {
      this.menusList = menus
      storage.setUserMenusList(menus)
      initRouter(menus)
    },
    setMenusTree(menus) {
      this.menusTree = menus
      storage.setUserMenusTree(menus)
    },
    setPermissions(permissions) {
      this.permissions = permissions
      storage.setPermissions(permissions)
    },
    async loadMenusList() {
      const result = await commonApi.getUserMenusList()
      if (result.status === 200 && result.data !== null) {
        const menus = result.data.filter(item => item.showFlag === 0)
        this.setMenusList(menus)
      }
      return result
    },
    async loadMenusTree() {
      const result = await commonApi.getUserMenusTree()
      if (result.status === 200 && result.data !== null) {
        this.setMenusTree(formatMenusTree(result.data))
      }
      return result
    },
    async loadPermissions() {
      const result = await commonApi.getUserPermissions()
      if (result.status === 200 && result.data !== null) {
        this.setPermissions(result.data)
      }
      return result
    },
  },
})
