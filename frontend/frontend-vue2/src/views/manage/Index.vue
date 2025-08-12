<template>
  <a-layout>
    <c-page-header :title="title" :userInfo="userInfo" style="height: 7vh" :theme="theme"/>
    <a-layout :style="showFooter? {height: '87vh'} : {height: '93vh'}">
      <c-page-menus :menus="$store.getters.MENUS_TREE" @clickMenu="clickMenu" :theme="theme"/>
      <a-layout-content class="content">
        <a-card style="overflow-y: scroll;height: 80vh" class="content-card">
          <keep-alive>
            <router-view></router-view>
          </keep-alive>
        </a-card>
      </a-layout-content>
    </a-layout>
    <c-page-footer v-if="showFooter" :theme="theme"/>
  </a-layout>

</template>

<script>
import CPageHeader from '@/views/common/C-PageHeader'
import CPageMenus from '@/views/common/C-PageMenus'
import CPageFooter from '@/views/common/C-PageFooter'

export default {
  name: 'Index',
  components: {
    CPageHeader,
    CPageMenus,
    CPageFooter,
  },
  data() {
    return {
      title: '后台管理系统',
      userInfo: {},
      theme: 'light',
      showFooter: false
    }
  },
  activated() {
  },
  mounted() {
    this.$message.info('首页')
    let userInfo = this.$store.getters.USER_BASE_INFO
    if (typeof userInfo === "object") {
      this.userInfo = userInfo
    }
  },
  methods: {
    clickMenu(e) {
      // 点击菜单时记录当前点击的菜单ID，用于重新登录时恢复之前的页面
      localStorage.setItem('OPEN_MENU_IDS', e.pid)
      localStorage.setItem('SELECT_MENU_IDS', e.id)
      console.log('点击菜单', e.path, e.name, e.id)
      if (e.menusType === 3) {
        window.open(e.link)
      } else {
        this.$router.push(e.path)
      }
    },
  }
}
</script>

<style scoped lang="less">
@import "~@public/color.less";

.title {
  font-size: 20px;
  color: #fff;
  font-weight: 700;
}


.content {
  padding: 16px;
  background-color: #fff;
}

//
//.ant-layout-header {
//  background: @primary-color;
//  color: #fff;
//  height: 7vh;
//  line-height: 7vh;
//}
//
//.ant-layout-footer {
//  background: @primary-color;
//  color: #fff;
//  height: 10vh;
//  line-height: 7vh;
//}
//
//.ant-layout-sider {
//  width: 100%;
//  height: 83vh;
//  background: #fff;
//}
//
//.ant-layout-content {
//  box-shadow: inset 0 0 5px fade(@primary-color, 20%);
//  padding: 12px;
//  background: #fff;
//  min-height: 120px;
//  height: 83vh;
//  overflow: auto;
//}
//
</style>
