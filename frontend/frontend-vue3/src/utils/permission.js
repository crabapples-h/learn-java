import storage from '@/store/storage'

export function hasPermission(permissions = [], code) {
  if (!code) {
    return true
  }
  return permissions.includes(code)
}

/**
 * 注册全局 v-auth 指令：无权限时移除元素
 * 用法：<button v-auth:sys:user:add>新增</button>
 */
export default {
  install(app) {
    app.directive('auth', {
      mounted(el, binding) {
        const permissions = storage.getPermissions() || []
        if (!hasPermission(permissions, binding.arg)) {
          el.parentNode?.removeChild(el)
        }
      },
    })
  },
}
