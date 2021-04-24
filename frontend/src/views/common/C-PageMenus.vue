<template>
  <div>
    <a-layout-sider>
      <a-menu style="width: 200px;height: 100%" mode="inline">
        <a-sub-menu :key="item.key" v-for="item in menus" v-if="item.children && item.children.length">
          <span slot="title"><a-icon :type="item.icon"/><span>{{ item.name }}</span></span>
          <a-sub-menu :key="item.key" v-if="item.children && item.children.length" v-for="item in item.children">
            <span slot="title"><a-icon :type="item.icon"/><span>{{ item.name }}</span></span>
            <a-menu-item :key="item.key" v-for="item in item.children" @click="clickMenu(item.url)">
              <a-icon :type="item.icon"/>
              <span>{{ item.name }}</span>
            </a-menu-item>
          </a-sub-menu>
          <a-menu-item :key="item.key" v-else @click="clickMenu(item.url)">
            <a-icon :type="item.icon"/>
            <span>{{ item.name }}</span>
          </a-menu-item>
        </a-sub-menu>
        <a-menu-item :key="item.key" v-else @click="clickMenu(item.url)">
          <a-icon :type="item.icon"/>
          <span>{{ item.name }}</span>
        </a-menu-item>
      </a-menu>
    </a-layout-sider>
  </div>
</template>

<script>
import {manageRouter, manageRouter1} from "@/router/manage/manage";
import RolesList from "@/views/manage/RolesList";
import MenusList from "@/views/manage/MenusList";
import {SysApis} from "@/api/Apis";

export default {
  name: "C-PageMenus",
  data() {
    return {
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
    this.getUserMenus()
  },
  mounted() {
    // this.$router.push({name: 'welcome'})
  },
  methods: {
    getUserMenus() {
      this.$http.get(SysApis.menus).then(result => {
        if (result.status !== 200) {
          this.$message.error(result.message);
          return;
        }
        console.log(12000)
        let children = result.data.map(e => {
          return {
            path: e.path,
            // components: {innerView: () => require('@/views/manage/UserList')},
            // components: {innerView: resolve => require(['@/views/manage/UserList'], resolve),},
            // components: {innerView: () => import('@/views/manage/UserList')},
            name: 'user-list',
          }
        })
        let routerMap = {
          path: '/',
          // component: import('@/views/manage'),
          name: 'layout',
          meta: {title: '首页', icon: 'clipboard'},
          children: children
        }
        let array1 = [manageRouter]
        let array = [routerMap]
        console.log(array)
        // console.log(manageRouter1)
        // this.$router.addRoutes(array1)
        this.$router.addRoutes(manageRouter1)
        if (result.data !== null) {
          // this.userInfo = result.data;
          // sessionStorage.setItem("userInfo", result.data)
        }
      }).catch(function (error) {
        console.error('出现错误:', error);
      });
    },
    clickMenu(e) {
      this.$router.push(e)
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