import { defineStore } from 'pinia'
import commonApi from '@/api/CommonApi'
import storage from '@/store/storage'

export const useSysStore = defineStore('sys', {
  state: () => ({
    loadFinish: false,
    serverAddress: storage.getServerAddress() || '',
    filePreviewAddress: storage.getFilePreviewAddress() || '',
  }),
  getters: {
    LOAD_FINISH: state => state.loadFinish,
    SERVER_ADDRESS: state => state.serverAddress,
    FILE_PREVIEW_ADDRESS: state => state.filePreviewAddress,
  },
  actions: {
    setLoadFinish(value) {
      this.loadFinish = value
    },
    setServerAddress(value) {
      this.serverAddress = value
      storage.setServerAddress(value)
    },
    setFilePreviewAddress(value) {
      this.filePreviewAddress = value
      storage.setFilePreviewAddress(value)
    },
    async loadServerAddress() {
      const result = await commonApi.serverAddress()
      if (result.status === 200) {
        this.setServerAddress(result.data)
      }
      return result
    },
    async loadFilePreviewAddress() {
      const result = await commonApi.filePreviewAddress()
      if (result.status === 200) {
        this.setFilePreviewAddress(result.data)
      }
      return result
    },
  },
})
