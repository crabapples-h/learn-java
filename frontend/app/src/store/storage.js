import {defineStore} from 'pinia'
import {computed, ref} from "vue";
//  `defineStore()` 的返回值的命名是自由的
// 但最好含有 store 的名字，且以 `use` 开头，以 `Store` 结尾。
// (比如 `useUserStore`，`useCartStore`，`useProductStore`)
// 第一个参数是你的应用中 Store 的唯一 ID。

export const useTokenStoreApi = defineStore('systemApi', {
    state: () => ({
        description: '这是API式的定义方式',
        token: ''
    }),
    getters: {
        tokenVal: (state) => state.token,
        descriptionVal: (state) => state.description,
    },
    actions: {
        doSomething() {
            console.log('这里可以做些什么....')
        },
    },
})
export const useTokenStoreFunc = defineStore('systemFunc', () => {
    const description = ref('这是函数式的定义方式')
    const token = ref('')
    const tokenVal = computed(() => token)
    const descriptionVal = computed(() => description)

    function doSomething() {
        console.log('这里可以做些什么....')
    }

    return {token, tokenVal, descriptionVal, doSomething}
})

