import { defineStore } from 'pinia'
import commonApi from '@/api/CommonApi'
import storage from '@/store/storage'

export const useRoleStore = defineStore('roles', {
  state: () => ({
    roles: storage.getUserRoles() || [],
  }),
  getters: {
    ROLES: state => state.roles,
  },
  actions: {
    setRoles(roles) {
      this.roles = roles
      storage.setUserRoles(roles)
    },
    async loadRoles() {
      const result = await commonApi.getUserRoles()
      if (result.status === 200 && result.data !== null) {
        this.setRoles(result.data)
      }
      return result
    },
  },
})
