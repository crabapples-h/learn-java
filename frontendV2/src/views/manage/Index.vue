<template>
  <div>
    <a-layout>
      <c-page-header :title="title"/>
      <a-layout>
        <c-page-menus :menus="menus" @clickMenu="clickMenu"/>
        <a-layout-content class="content">
          <keep-alive>
            <router-view name="innerView"></router-view>
          </keep-alive>
        </a-layout-content>
      </a-layout>
      <c-page-footer/>
    </a-layout>
  </div>
</template>

<script>
import CPageHeader from "@/views/common/C-PageHeader";
import CPageMenus from "@/views/common/C-PageMenus";
import CPageFooter from "@/views/common/C-PageFooter";
import permissions from "@/store/modules/permissions";

export default {
  name: "Index",
  components: {
    CPageHeader,
    CPageMenus,
    CPageFooter,
  },
  data() {
    return {
      title: '管理',
      userInfo: {
        name: ''
      },
      menus: [],
    };
  },
  activated() {
    // this.checkLoginStatus()
    this.initMenus()
    // this.initRouterMap()
  },
  mounted() {
    // this.$router.push({path: '/manage/welcome'})
    this.$message.info('首页')
  },
  methods: {
    clickMenu(e) {
      console.log(e)
      this.$router.push(e.path)
    },
    checkLoginStatus() {
      let token = this.$store.getters.token
      let userInfo = this.$store.getters.userInfo
      if (!!(token && userInfo)) {
        this.$router.push('/manage/welcome')
      } else {
        this.$router.replace('/login')
      }
    },
    initMenus() {
      this.menus = this.$store.getters.MENUS
      console.log('menus-->', this.$store.getters.MENUS)
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

.ant-layout-header {
  background: @primary-color;
  color: #fff;
  height: 7vh;
  line-height: 7vh;
}

.ant-layout-footer {
  background: @primary-color;
  color: #fff;
  height: 10vh;
  line-height: 7vh;
}

.ant-layout-sider {
  width: 100%;
  height: 83vh;
  background: #fff;
}

.ant-layout-content {
  box-shadow: inset 0 0 5px fade(@primary-color, 20%);
  padding: 12px;
  background: #fff;
  min-height: 120px;
  height: 83vh;
  overflow: auto;
}

/*滚动条整体样式*/
.ant-layout-content::-webkit-scrollbar {
  width: 10px; /*高宽分别对应横竖滚动条的尺寸*/
  height: 1px;
  margin-right: 10px;
  opacity: 0.2;
}

/*滚动条里面小方块(滑块 )*/
.ant-layout-content::-webkit-scrollbar-thumb {
  border-radius: 10px;
  -webkit-box-shadow: inset 0 0 5px @primary-color;
  background: fade(@primary-color, 20%);
  opacity: 0.2;
}

/*滚动条里面轨道(背景)*/
.ant-layout-content::-webkit-scrollbar-track {
  -webkit-box-shadow: inset 0 0 5px @primary-color;
  border-radius: 10px;
  background: fade(@primary-color, 20%);
  opacity: 0.2;
}
</style>
