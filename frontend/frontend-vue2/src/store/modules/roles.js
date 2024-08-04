/**
 *
 * @author Ms.He
 * 2023/8/31 0:43
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mshe
 */
import commonApi from '@/api/CommonApi'
import message from 'ant-design-vue/es/message'
import notification from 'ant-design-vue/es/notification'
import storage from '@/store/storage'

const roles = {
  state: {
  },
  //commit调用mutations方法，实时更新
  mutations: {
    ROLES: (state, payload) => {
      storage.setUserRoles(payload)
    },
  },
  // dispatch调用actions方法时，是非实时更新，可以包含异步操作
  // commit调用的是上面的mutations中的方法
  actions: {
    ROLES(object, data) {
      return commonApi.getUserRoles(data).then(({ status, data, message }) => {
        if (status !== 200) {
          notification.error({ message: message })
          return Promise.reject(message)
        }
        object.commit('ROLES', data)
        return Promise.resolve(message)
      }).catch(function (error) {
        console.error('出现错误:', error)
        return Promise.reject(message)
      })
    },
  }
}
export default roles
