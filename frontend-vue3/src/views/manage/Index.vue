<template>
  <div>
    <!--    <p>666</p>-->
    <a-layout>
      <c-page-header :title="title"/>
      <a-layout>
        <c-page-menus :menus="menus"/>
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
      demo: {
        routerMap: [
          {
            path: '/',
            redirect: '/index',
          },
          {
            path: '/manage/users-list',
            name: 'users-list',
            filePath: 'manage/UsersList',
          },
          {
            path: '/manage/roles-list',
            name: 'roles-list',
            filePath: 'manage/RolesList',
          },
          {
            path: '/manage/menus-list',
            name: 'menus-list',
            filePath: 'manage/MenusList',
          },
        ],
        menus: [
          {
            key: '99',
            name: '系统管理',
            icon: 'appstore',
            path: '/',
            children: [
              {
                key: '13',
                name: '菜单管理',
                icon: 'appstore',
                path: '/manage/menus-list',
                sort: 999,
              },
              {
                key: '12',
                name: '角色管理',
                icon: 'appstore',
                path: '/manage/roles-list',
                sort: 998,
              },
              {
                key: '1',
                name: '用户管理',
                icon: 'appstore',
                path: '/manage/users-list',
                sort: 997,
              },
            ]
          },
        ]
      }
    };
  },
  activated() {
    this.checkLoginStatus()
    this.initMenus()
    this.initRouterMap()
  },
  mounted() {
    this.$message.info('首页')
  },
  methods: {
    clickMenu(e) {
      this.$router.push(e.path)
    },
    checkLoginStatus() {
      let token = this.$store.getters.token
      let userInfo = this.$store.getters.userInfo
      if (!!(token && userInfo)) {
        this.$router.push('/manage/welcome')
      } else {
        this.$router.push('/login')
      }
    },
    initRouterMap() {
      let routerMap = this.$store.getters.routers
      // let routerMap = this.demo.routerMap
      if (routerMap && routerMap.length) {
        // $addRouters(routerMap)
        this.checkLoginStatus()
      }
    },
    initMenus() {
      let menusSource = this.$store.getters.userMenus
      console.log('menusSource', menusSource)
      let format = function (data) {
        return data.map(e => {
          if (e.menusType !== 1) {
            return null
          }
          let icon = e.icon.substring(e.icon.indexOf("\"") + 1, e.icon.lastIndexOf("\""))
          let menus = {
            id: e.id,
            key: e.id,
            name: e.name,
            icon: icon,
            path: e.path,
            sort: e.sort,
          }
          if (e.children && e.children.length > 0) {
            let children = format(e.children).filter(e => {
              return !!e
            })
            if (children && children.length)
              menus.children = children
          }
          return menus
        }).sort((a, b) => {
          if (a && b && a.sort && b.sort)
            return a.sort - b.sort
        });
      }
      this.menus = format(menusSource)
      // this.menus = this.demo.menus
      // console.log('menus-->', this.menus)
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
