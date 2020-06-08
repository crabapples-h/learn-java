<template>
	<el-row>
		<p>欢迎登录{{userInfo.name}}</p>
	</el-row>
</template>

<script>
    module.exports = {
        props: {},
        data() {
            return {
                userInfo: {
                    name: ''
                }
            };
        },
        activated() {
            console.log(this.$route.params)
        },
        mounted() {
        },
        methods: {}
    }
</script>

<style scoped>
</style>
