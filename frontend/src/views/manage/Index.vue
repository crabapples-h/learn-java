<template>
  <div>
    <a-layout>
      <c-page-header/>
      <a-layout>
        <a-layout-sider>
          <a-menu style="width: 200px;height: 100%" mode="inline">
            <a-sub-menu :key="item.key" v-for="item in menus" v-if="item.children && item.children.length">
              <span slot="title"><a-icon :type="item.icon"/><span>{{ item.name }}</span></span>
              <a-sub-menu :key="item.key" v-if="item.children && item.children.length" v-for="item in item.children">
                <span slot="title"><a-icon :type="item.icon"/><span>{{ item.name }}</span></span>
                <a-menu-item :key="item.key" v-for="item in item.children" @click="clickMenu(item.url)">
                  <a-icon
                      :type='item.icon.substring(item.icon.indexOf("\"") + 1,item.icon.lastIndexOf("\"")) || "appstore"'/>
                  <span>{{ item.name }}</span>
                </a-menu-item>
              </a-sub-menu>
              <a-menu-item :key="item.key" v-else @click="clickMenu(item.url)">
                <a-icon
                    :type='item.icon.substring(item.icon.indexOf("\"") + 1,item.icon.lastIndexOf("\"")) || "appstore"'/>
                <span>{{ item.name }}</span>
              </a-menu-item>
            </a-sub-menu>
            <a-menu-item :key="item.key" v-else @click="clickMenu(item.url)">
              <a-icon
                  :type='item.icon.substring(item.icon.indexOf("\"") + 1,item.icon.lastIndexOf("\"")) || "appstore"'/>
              <span>{{ item.name }}</span>
            </a-menu-item>
          </a-menu>
        </a-layout-sider>
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
import {getToken, getUserInfo, getRouterMap} from "@/utils/sessionUtils";
import {$addRouters} from "@/router";

export default {
  name: "Index",
  components: {
    CPageHeader,
    CPageMenus,
    CPageFooter,
  },
  data() {
    return {
      userInfo: {
        name: ''
      },
      menus: [
        {
          key: '1',
          name: '用户管理',
          icon: 'appstore',
          url: '/sys/user-list',
        },
        {
          key: '12',
          name: '角色管理',
          icon: 'appstore',
          url: '/sys/roles-list',
        },
        {
          key: '13',
          name: '菜单管理',
          icon: 'appstore',
          url: '/sys/menus-list',
        },
        {
          key: '99',
          name: '系统管理',
          icon: 'appstore',
          url: '/',
          children: [
            {
              key: '12331',
              name: '菜单管理',
              icon: 'appstore',
              url: '/settings-menu',
            }
          ]
        },
      ],
    };
  },
  activated() {
    this.checkLoginStatus()
    this.initMenus()
    this.initRouterMap()
  },
  mounted() {
  },
  methods: {
    clickMenu(e) {
      this.$router.push(e)
    },
    checkLoginStatus() {
      let token = getToken()
      let userInfo = getUserInfo()
      this.$store.state.token = token
      this.$store.state.userInfo = userInfo
      if (!!(token && userInfo)) {
        this.$router.push('/index')
      } else {
        this.$router.push('/login')
      }
    },
    initRouterMap() {
      let routerMap = getRouterMap()
      if (routerMap && routerMap.length) {
        $addRouters(routerMap)
      }
    },
    initMenus() {
      let menusSource = getRouterMap()
      let format = function (data) {
        let menus = data.map(e => {
          console.log('e-->', e)
          return {
            key: e.id,
            name: e.name,
            icon: e.icon,
            url: e.path,
            sort: e.sort,
            children: format(e.children)
          }
        })
        menus.sort((a, b) => {
          return a.sort - b.sort
        })
        return menus;
      }
      let menus = format(menusSource)
      this.menus = menus
      console.log('menus-->', menus)
    }

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