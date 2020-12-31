<template>
    <div id="app">
        <router-view></router-view>
    </div>
</template>

<script>
    export default {
        name: 'App',
        mounted() {
            this.getUserInfo()
            // this.$router.push({path: "/loginApi"})
        },
        methods: {
            getUserInfo() {
                this.$http.get('/api/123').then(response=>{
                    const result = response.data
                })
                //TODO 获取用户信息判断而登录状态
            },
            click() {
                this.$message.success("调换")
            }
        }
    }
</script>

<style>
    #app {
        margin: 0;
        padding: 0;
    }
</style>
