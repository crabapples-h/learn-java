<template>
    <body class="login-bg">
    <div class="login layui-anim layui-anim-up">
        <div class="message">用户登录</div>
        <div id="darkbannerwrap"></div>
        <input autocomplete="off" placeholder="用户名" type="text" v-model="username">
        <hr class="hr15">
        <input autocomplete="off" placeholder="密码" type="password" v-model="password">
        <hr class="hr15">
        <input style="width:100%;" type="button" value="立即登录" @click="login">
        <hr class="hr15">
    </div>
    <a-button type="primary">按钮</a-button>
    </body>
</template>

<script>
    export default {
        name: 'Login',
        data() {
            return {
                username: '',
                password: ''
            }
        },
        methods: {
            login() {
                const _this = this
                // console.log(_this)
                let data = {
                    username: this.username,
                    password: this.password
                };
                // console.log(this)
                _this.$http.post('/api/loginCheck', data).then(response => {
                    const result = response.data;
                    _this.$message.success(result.message)
                    console.log('通过api获取到的数据:', result)
                    if (result.status !== 200) {
                        // layer.msg(result.message);
                        return
                    }
                    // layer.msg(result.message);
                    // if (result.data.admin) {
                    //     window.location.href = '/manage/index'
                    // } else {

                    // this.$route.phsh()
                    // window.location.href = '/index'
                    // }
                    // window.location.href = '/index'
                }).catch(exception => {
                    // layer.msg('系统错误');
                    console.error('系统错误', exception)
                })
            }
        }
    }
</script>
<style scoped>
    .login-bg {
        background: url(../assets/login-backgroud.png) no-repeat center;
        background-size: cover;
        overflow: hidden;
    }

    .login {
        margin: 120px auto 0 auto;
        min-height: 420px;
        max-width: 420px;
        padding: 40px;
        background-color: #ffffff;
        border-radius: 4px;
        /* overflow-x: hidden; */
        box-sizing: border-box;
    }

    .login a.logo {
        display: block;
        height: 58px;
        width: 167px;
        margin: 0 auto 30px auto;
        background-size: 167px 42px;
    }

    .login .message {
        margin: 10px 0 0 -58px;
        padding: 18px 10px 18px 60px;
        background: #189F92;
        position: relative;
        color: #fff;
        font-size: 16px;
    }

    .login #darkbannerwrap {
        width: 18px;
        height: 10px;
        margin: 0 0 20px -58px;
        position: relative;
    }

    .login input[type=text],
    .login input[type=file],
    .login input[type=password],
    .login input[type=email], select {
        border: 1px solid #DCDEE0;
        vertical-align: middle;
        border-radius: 3px;
        height: 50px;
        padding: 0 16px;
        font-size: 14px;
        color: #555555;
        outline: none;
        width: 100%;
        box-sizing: border-box;
    }

    .login input[type=text]:focus,
    .login input[type=file]:focus,
    .login input[type=password]:focus,
    .login input[type=email]:focus, select:focus {
        border: 1px solid #27A9E3;
    }

    .login input[type=submit],
    .login input[type=button] {
        display: inline-block;
        vertical-align: middle;
        padding: 12px 24px;
        margin: 0;
        font-size: 18px;
        line-height: 24px;
        text-align: center;
        white-space: nowrap;
        cursor: pointer;
        color: #ffffff;
        background-color: #189F92;
        border-radius: 3px;
        border: none;
        -webkit-appearance: none;
        outline: none;
        width: 100%;
    }

    .login hr {
        background: #fff url() 0 0 no-repeat;
    }

    .login hr.hr15 {
        height: 15px;
        border: none;
        margin: 0;
        padding: 0;
        width: 100%;
    }

    .login hr.hr20 {
        height: 20px;
        border: none;
        margin: 0;
        padding: 0;
        width: 100%;
    }
</style>
