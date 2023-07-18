<template>
    <div id="app">
        <!--    <keep-alive>-->
        <!--      <router-view/>-->
        <!--    </keep-alive>-->
        <vue-canvas-poster :widthPixels="0" :painting="painting" @success="success" @fail="fail"/>
        <img :src="posterImgimg" alt="" style="width: 100%">
    </div>
</template>
<script>
import storage from '@/store/storage'
import Loading from "./views/base/Loading";

export default {
    name: 'App',
    data() {
        return {
            painting: {},
            posterImg: ''
        }
    },
    created() {
        this.initMenus()
    },
    mounted() {
        window.addEventListener('beforeunload', () => {
            alert(1)
        })
        this.createShareCard()
    },
    methods: {
        beforeunload(e) {
            alert(1)
            console.log(e)
        },
        initMenus() {
            const token = storage.getToken()
            if (token) {
                this.$store.dispatch('INIT_TOKEN', token)
                this.$store.dispatch('MENUS')
                this.$store.dispatch('PERMISSIONS')
                this.$store.dispatch('USER_INFO')
            } else {
                console.log('token不存在:', token)
            }
        },
        createShareCard() {
            let url = "xxxxx"
            let painting = {
                width: "500px",
                height: "550px",
                top: "0px",
                views: [
                    {
                        type: 'qrcode',
                        content: url,
                        css: {
                            top: "310px",
                            width: '80px',
                            left: '70px',
                            color: '#000',
                            height: '130px'
                        }
                    },
                ]
            }
            this.painting = painting
        },
        success(src) {
            this.posterImg = src;
        },
        fail() {
            console.log('生成海报失败')
        },
    }
}
</script>

<style>
#app {
    margin: 0;
    padding: 0;
}
</style>
