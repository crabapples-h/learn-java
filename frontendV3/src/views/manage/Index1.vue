<template>
  <a-layout>
    <c-page-header title="aaa"/>
  </a-layout>
</template>

<script>
import CPageHeader from "@/views/common/C-PageHeader";
import CPageMenus from "@/views/common/C-PageMenus";
import CPageFooter from "@/views/common/C-PageFooter";

export default {
  components: {CPageHeader, CPageMenus, CPageFooter},

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
    console.log('activated(')
    // this.checkLoginStatus()
    // this.initMenus()
    // this.initRouterMap()
  },
  mounted() {
    console.log('index1')
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
      // console.log('menus&ndash;&gt;', this.menus)
    },
  }
}
</script>

<style scoped lang="less">
//@import "~@public/color.less";
</style>
