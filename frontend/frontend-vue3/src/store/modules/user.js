import { defineStore } from 'pinia'
import { notification } from 'ant-design-vue'
import commonApi from '@/api/CommonApi'
import storage from '@/store/storage'

export const useUserStore = defineStore('user', {
  state: () => ({
    token: storage.getToken() || '',
    info: storage.getUserBaseInfo() || {},
  }),
  getters: {
    TOKEN: state => state.token,
    USER_BASE_INFO: state => state.info,
  },
  actions: {
    setToken(token) {
      this.token = token
      storage.setToken(token)
    },
    setUserBaseInfo(info) {
      this.info = info
      storage.setUserBaseInfo(info)
    },
    async login(data) {
      const result = await commonApi.login(data)
      if (result.status !== 200) {
        notification.error({ message: result.message })
        throw new Error(result.message)
      }
      this.setToken(result.data)
      return result
    },
    initToken(token) {
      this.setToken(token)
    },
    async loadUserBaseInfo() {
      const result = await commonApi.getUserInfo()
      if (result.status === 200 && result.data !== null) {
        this.setUserBaseInfo(result.data)
      }
      return result
    },
  },
})
