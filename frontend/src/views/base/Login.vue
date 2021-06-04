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
      password: '',
      msg: '拖动滑块验证',
      checked: false,
      show: {
        checkCode: false,
      },
    }
  },
  activated() {
    this.checkLoginStatus()
  },
  mounted() {
  },
  methods: {
    onSuccess(times) {
      this.checked = true
      this.closeCheckCode()
      this.submit()
    },
    onFail() {
    },
    onAgain() {
      console.log("检测到非人为操作的哦！");
      this.$refs.slideblock.reset();
    },
    showCheckCode() {
      this.show.checkCode = true
      this.$refs.slideblock.reset();
    },
    checkLoginStatus() {
      let token = getToken()
      let userInfo = getUserInfo()
      this.$store.state.token = token
      this.$store.state.userInfo = userInfo
      if (!!(token && userInfo)) {
        this.$router.push('/manage/index')
      }
    },

    async submit() {
      const _this = this
      // if (!_this.checked) {
      //   if (_this.username.trim() && _this.password.trim()) {
      //     _this.showCheckCode()
      //     return
      //   }
      //   _this.$message.error("用户名或密码不能为空")
      //   return
      // }
      _this.checked = false
      let data = {
        username: _this.username,
        password: _this.password
      };
      let token = await _this.getToken(data)
      if (token.status) {
        setToken(token.data)
        let userInfo = await _this.getUserInfo()
        let routerMap = await _this.getRouterMap()
        let permissions = await _this.getPermissions()
        if (userInfo.status && routerMap.status && permissions.status) {
          setUserInfo(userInfo.data)
          setRouterMap(routerMap.data)
          setPermissions(permissions.data)
          _this.$router.push('/manage/index')
        } else {
          _this.$message.error('登录失败')
        }
      }
    },
    getToken(data) {
      return commonApi.login(data).then(result => {
        if (result.status !== 200) {
          this.$message.error(result.message);
          return
        }
        return Promise.resolve({status: true, data: result.data})
      }).catch(function (error) {
        console.error('出现错误:', error);
      })
    },
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
