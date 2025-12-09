<template>
  <div class="main">
    <a-affix :offset-top="0">
      <div class="top-search-container">
        <a-tag @click="clickFavorite">收藏</a-tag>
        <a-input-search v-model:value="searchText"
                        placeholder="请输入搜索关键词"
                        style="width: 70%"
                        size="small"
                        @search="onSearch"/>
      </div>
    </a-affix>

    <div class="body-type-container" style="">
      <div v-for="(item, index) in typeList.data" style="width: 25%">
        <a-avatar @click="clickType(item)" :size="50" style="margin-top: 6px" :src="item.img"/>
        <p>{{ item.name }}</p>
      </div>
    </div>

    <div class="body-card-container" style="">
      <div v-for="(item, index) in cardList.data" style="width: 48%">
        <div @click="clickCard(item)" style="border: 1px solid red;margin-top: 12px;height: 180px">
          <img :src="item.img" style="width: 100%;height:80%;border-radius: 15px;" alt=""/>
          <div style="text-align: left;">
            <p style="">{{ item.name }}</p>
            <p style="color: red;font-size: 10px">{{ item.visitCount }}人观看&nbsp;{{ item.bestCount }}%人好评</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script setup lang="ts">
defineProps({})
import {useTokenStoreApi, useTokenStoreFunc} from '@/store/storage'
import axios from '@/utils/axios'
import {onMounted, reactive, triggerRef, customRef, ref} from "vue";

onMounted(() => {
  console.log(useTokenStoreApi().description)
  console.log(useTokenStoreApi().descriptionVal)
  useTokenStoreFunc().doSomething()
  loadType()
  loadCard()
})
let typeList = reactive({data: []})
const cardList = reactive({data: []})
const loadType = () => {
  axios.get('/api/custom/type/list').then(res => {
    typeList.data = res.data
  })
}
const loadCard = () => {
  axios.get('/api/custom/card/list').then(res => {
    cardList.data = res.data
  })
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

@media (min-width: 1024px) {
  .greetings h1,
  .greetings h3 {
    text-align: left;
  }
}
</style>
