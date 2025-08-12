<template>
  <div class="loginApi-bg">
    <div class="loginApi-div">
      <div class="title">用户登录</div>
      <a-input autocomplete="off" placeholder="用户名" type="text" v-model="username" class="input-text"></a-input>
      <a-input autocomplete="off" placeholder="密码" type="password" v-model="password" class="input-text"></a-input>
      <a-button style="width:100%;" type="primary" @click="submit" class="loginApi-button">立即登录</a-button>
    </div>
  </div>
</template>

<script>

export default {
  name: 'Login',
  data() {
    return {
      username: '',
      password: '',
    }
  },
  activated() {
  },
  mounted() {
  },
  methods: {
    submit() {
      const _this = this
      let data = {
        username: _this.username,
        password: _this.password
      }
      // dispatch调用的是/store/modules里几个js文件中actions里的方法
      this.$store.dispatch('LOGIN', data).then(result => {
        console.log('dispatch LOGIN', result)
        // 加载用户信息
        console.log('dispatch LOGIN', '加载用户信息')
        this.$store.dispatch('USER_BASE_INFO')
        // 加载用户角色
        console.log('dispatch LOGIN', '加载用户角色')
        this.$store.dispatch('ROLES')
        // 加载左侧菜单树
        console.log('dispatch LOGIN', '加载左侧菜单树')
        this.$store.dispatch('MENUS_TREE')
        // 加载路由菜单列表
        console.log('dispatch LOGIN', '加载路由菜单列表')
        this.$store.dispatch('MENUS_LIST')
        // 加载用户权限
        console.log('dispatch LOGIN', '加载用户权限')
        this.$store.dispatch('PERMISSIONS')
        this.$router.replace('/loading')
      })
    },
  }
}
</script>
<style lang="less" scoped>
@import "~@public/color.less";

.loginApi-bg {
  background: url(~@assets/login-background.png) no-repeat center;
  background-size: 100%;
  overflow: hidden;
  height: 100vh;
}

.loginApi-div {
  margin: 120px auto 0 auto;
  min-height: 420px;
  max-width: 420px;
  padding: 40px;
  background-color: #ffffff;
  border-radius: 4px;
  box-sizing: border-box;
}

.title {
  margin: 10px 0 0 -58px;
  padding: 18px 10px 18px 60px;
  background: @primary-color;
  position: relative;
  color: #fff;
  font-size: 16px;
}

.input-text {
  height: 50px;
  box-sizing: border-box;
  margin: 16px 0;
}

.loginApi-button {
  height: 50px;
  border: none;
}
</style>
