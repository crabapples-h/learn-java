<template>
  <div class="main">
    <p>我的</p>

  </div>
</template>
<script setup lang="ts">
defineProps({})
import {useTokenStoreApi, useTokenStoreFunc} from '@/store/storage'

import {onMounted, reactive, ref} from "vue";

onMounted(() => {
  console.log(useTokenStoreApi().description)
  console.log(useTokenStoreApi().descriptionVal)
  useTokenStoreFunc().doSomething()
})
const loadType = () => {

}
const loadCard = () => {

}
const loadMenu = () => {

}
const searchText = ref('')
const onSearch = () => {
  console.log(searchText.value)
}
const clickFavorite = (e: any) => {
  console.log('点击了收藏-->', e)
}
const clickType = (e: any) => {
  console.log('点击了按钮-->', e)
}
const clickCard = (e: any) => {
  console.log('点击了卡片-->', e)
}
const clickMenu = (e: any) => {
  console.log('点击了菜单-->', e)
}
const typeCount = ref(80)
const typeList = reactive(new Array(8).fill({
  imgSrc: "https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png",
  text: "1"
}, 0, 8))
const cardList = reactive(new Array(typeCount.value).fill({
  imgSrc: "https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png",
  text: "1"
}, 0, typeCount.value))
const menus = reactive([
  {
    imgSrc: "https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png",
    text: "首页"
  },
  {
    imgSrc: "https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png",
    text: "视频"
  },
  {
    imgSrc: "https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png",
    text: "写真"
  },
  {
    imgSrc: "https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png",
    text: "我的"
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

.top-search-container {
  background-color: #f1f1f1;
  padding: 12px;
  display: flex;
  justify-content: space-evenly;
  align-items: center
}

.body-type-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between
}

.body-card-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-evenly;
  margin-top: 12px;
  margin-bottom: 70px;
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
