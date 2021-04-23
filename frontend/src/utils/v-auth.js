/**
 * TODO vue自定义指令
 *
 * @author Mr.He
 * 2021/4/15 14:40
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mrhe
 */
import Vue from 'vue'
import {getPermissions} from "@/utils/sessionUtils";

// 注册一个全局自定义指令 `v-auth`
const auth = Vue.directive('auth', {
    bind: (el, binding) => {
        let permissions = getPermissions()
        console.log(permissions)
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
