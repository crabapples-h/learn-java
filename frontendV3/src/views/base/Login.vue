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
import commonApi from '@/api/CommonApi'
import storage from '@/store/storage'

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
    async submit() {
      const _this = this
      let data = {
        username: _this.username,
        password: _this.password
      };
      let token = await _this.login(data)
      if (token.status === 200) {
        this.$store.commit('setToken', token.data)
        let userInfo = await _this.getUserInfo()
        let userMenus = await _this.getUserMenus()
        let permissions = await _this.getPermissions()
        if (userInfo.status && userMenus.status && permissions.status) {
          this.$store.commit('setUserInfo', userInfo.data)
          this.$store.commit('setUserMenus', userMenus.data)
          this.$store.commit('setUserPermissions', permissions.data)
          // _this.$route.push('/manage/index')
          window.location.reload();
        } else {
          _this.$message.error('登录失败')
        }
      }
    },
    login(data) {
      return commonApi.login(data).then(result => {
        if (result.status !== 200) {
          this.$message.error(result.message);
          return
        }
        return Promise.resolve({status: 200, data: result.data})
      }).catch(function (error) {
        console.error('出现错误:', error);
      })
    },
    //获取用户信息
    getUserInfo() {
      return commonApi.getUserInfo().then(result => {
        if (result.status !== 200) {
          this.$message.error(result.message);
          return;
        }
        if (result.data !== null) {
          return Promise.resolve({status: true, data: result.data})
        }
      }).catch(function (error) {
        console.error('出现错误:', error);
      })
    },
    //获取用户拥有的菜单，并根据菜单生成路由表
    getUserMenus() {
      return commonApi.getUserMenus().then(result => {
        if (result.status !== 200) {
          return;
        }
        if (result.data !== null) {
          return Promise.resolve({status: true, data: result.data})
        }
      }).catch(function (error) {
        console.error('出现错误:', error);
      });
    },
    //获取用户拥有的权限(按钮)
    getPermissions() {
      return commonApi.getUserPermissions().then(result => {
        if (result.status !== 200) {
          return;
        }
        if (result.data !== null) {
          return Promise.resolve({status: true, data: result.data})
        }
      }).catch(function (error) {
        console.error('出现错误:', error);
      });
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
