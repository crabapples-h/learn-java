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
// 注册一个全局自定义指令 `v-focus`
const auth = Vue.directive('auth', {
    bind: (el, binding) => {
        console.log('binding-->', binding.arg)
        // console.log('binding-->111'，, binding)
        // console.log('binding-->', binding)
        // console.log('binding-->', binding.value)
        setTimeout(() => {
            if (el.parentNode == null) {
                el.style.display = 'none'
            } else {
                // el.parentNode.removeChild(el)
            }
        }, 10)
    },
})
export {auth}
