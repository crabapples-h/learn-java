<template>
  <div class="loginApi-bg">
    <div class="loginApi-div">
      <div class="title">用户登录</div>
      <a-input autocomplete="off" placeholder="用户名" type="text" v-model="username" class="input-text"></a-input>
      <a-input autocomplete="off" placeholder="密码" type="password" v-model="password" class="input-text"></a-input>
      <a-button style="width:100%;" type="button" @click="submit" class="loginApi-button">立即登录</a-button>
    </div>
  </div>
</template>

<script>
import commonApi from '@/api/CommonApi'

export default {
  name: 'Login',
  data() {
    return {
      username: '',
      password: ''
    }
  },
  activated() {
    this.checkUserInfo()
  },
  methods: {
    checkUserInfo() {
      let token = sessionStorage.getItem('crabapples-token')
      let userInfo = JSON.parse(sessionStorage.getItem('userInfo'))
      this.$store.state.token = token
      this.$store.state.userInfo = userInfo
      if (!!(token && userInfo)) {
        this.$router.push('/manage-index')
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
        sessionStorage.setItem('crabapples-token', result.data);
        _this.getUserInfo().then(res => {
          if (res.status) {
            _this.$store.state.token = result.data
            _this.$store.state.userInfo = res.userInfo
            console.log(this.$store.state)
            _this.$message.success(result.message);
            _this.$router.push('/manage-index')
          }
          _this.$message.error('登录信息获取失败')
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
          sessionStorage.setItem("userInfo", JSON.stringify(result.data))
          return Promise.resolve({status: true, userInfo: result.data})
        }
      }).catch(function (error) {
        console.error('出现错误:', error);
      })
    },
  }
}
</script>
<style scoped>

.loginApi-bg {
  background: url(/src/assets/login-backgroud.png) no-repeat center;
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
  background: #189F92;
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
  color: #ffffff;
  background-color: #189F92;
  height: 50px;
  border: none;
}
</style>
