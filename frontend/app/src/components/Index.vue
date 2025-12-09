<template>
  <div class="main">
    <router-view></router-view>
    <a-affix style="position: fixed;bottom: 0;z-index: 999;width: 100%">
      <div class="bottom-menu-container" style="">
        <div v-for="(item, index) in menus" style="width: 25%">
          <a-avatar @click="clickMenu(item)" :size="32" :src="item.imgSrc"></a-avatar>
          <p>{{ item.text }}</p>
        </div>
      </div>
    </a-affix>
  </div>
</template>
<script setup lang="ts">
import {useRouter} from "vue-router";

defineProps({})
import {useTokenStoreApi, useTokenStoreFunc} from '@/store/storage'
import {onMounted, reactive, ref} from "vue";


onMounted(() => {
  console.log(useTokenStoreApi().description)
  console.log(useTokenStoreApi().descriptionVal)
  useTokenStoreFunc().doSomething()
  router.push('/home')
  // router.push('/mine')
})
const router = useRouter()
const clickMenu = (e: any) => {
  console.log('点击了菜单-->', e)
  router.push(e.path)
}
const menus = reactive([
  {
    imgSrc: "https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png",
    text: "首页",
    path: "/home",
  },
  {
    imgSrc: "https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png",
    text: "视频",
    path: "/video",

  },
  {
    imgSrc: "https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png",
    text: "写真",
    path: "/photo",

  },
  {
    imgSrc: "https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png",
    text: "我的",
    path: "/mine",
  }
])
</script>

<style scoped>
p {
  padding: 0;
  margin: 0;
}

.main {
  text-align: center;
  border: 1px solid red;
}

.bottom-menu-container {
  margin-top: 12px;
  display: flex;
  flex-wrap: wrap;
  border: 1px solid pink;
  padding: 6px;
  justify-content: space-evenly;
  background-color: #fff;
}

@media (min-width: 1024px) {
  .greetings h1,
  .greetings h3 {
    text-align: left;
  }
}
</style>
