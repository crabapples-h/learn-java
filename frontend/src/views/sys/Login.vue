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
import {setToken, getToken, setUserInfo, getUserInfo, setRouterMap, setPermissions} from '@/utils/sessionUtils'

export default {
  name: 'Login',
  data() {
    return {
      username: '',
      password: ''
    }
  },
  activated() {
    this.checkLoginStatus()
  },
  mounted() {
  },
  methods: {
    checkLoginStatus() {
      let token = getToken()
      let userInfo = getUserInfo()
      this.$store.state.token = token
      this.$store.state.userInfo = userInfo
      if (!!(token && userInfo)) {
        this.$router.push('/index')
      }
    },

    submit() {
      const _this = this
      let data = {
        username: _this.username,
        password: _this.password
      };
      commonApi.login(data).then(result => {
        if (result.status !== 200) {
          _this.$message.error(result.message);
          return
        }
        let userInfo = _this.getUserInfo()
        let routerMap = _this.getRouterMap()
        let permissions = _this.getPermissions()
        let userInfoStatus, routerStatus, permissionsStatus
        userInfo.then(userInfoRes => {
          userInfoStatus = userInfoRes.status
          routerMap.then(routerRes => {
            routerStatus = routerRes.status
            permissions.then(permissionsRes => {
              permissionsStatus = permissionsRes.status
              console.log(userInfoStatus, routerStatus, permissionsStatus)
              if (userInfoStatus && routerStatus && permissionsStatus) {
                setToken(result.data)
                setUserInfo(userInfoRes.data)
                setRouterMap(routerRes.data)
                setPermissions(permissionsRes.data)
                _this.$router.push('/index')
              } else {
                _this.$message.error('登录信息获取失败')
              }
            })
          })
        })
      })
    },
    getUserInfo() {
      return commonApi.getUserInfo().then(result => {
        if (result.status !== 200) {
          this.$message.error(result.message);
          return;
        }
        if (result.data !== null) {
          this.userInfo = result.data;
          return Promise.resolve({status: true, data: result.data})
        }
      }).catch(function (error) {
        console.error('出现错误:', error);
      })
    },
    getRouterMap() {
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
  background: url(~@assets/login-backgroud.png) no-repeat center;
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
