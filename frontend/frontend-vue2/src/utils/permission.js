import Vue from 'vue'
import storage from "@/store/storage";

/**
 * TODO vue自定义指令
 *
 * @author Mr.He
 * 2021/4/15 14:40
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mrhe
 */

// bind：只调用一次，指令第一次绑定到元素时调用。在这里可以进行一次性的初始化设置。
// inserted：被绑定元素插入父节点时调用 (仅保证父节点存在，但不一定已被插入文档中)。
// update：所在组件的 VNode 更新时调用，但是可能发生在其子 VNode 更新之前。指令的值可能发生了改变，也可能没有。但是你可以通过比较更新前后的值来忽略不必要的模板更新 (详细的钩子函数参数见下)
// 注册一个全局自定义指令 `v-auth`
const auth = Vue.directive('auth', {
    bind: (el, binding) => {
        let permissions = storage.getPermissions()
        if (!permissions) {
            return
        }
        let exist = permissions.includes(binding.arg)
        if (!exist) {
            setTimeout(() => {
                if (el.parentNode == null) {
                    el.style.display = 'none'
                } else {
                    el.parentNode.removeChild(el)
                }
            }, 10)
        }
    },
})
export {auth}
