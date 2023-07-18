<template>
    <div id="app">
<!--        <qr-code/>-->
        <face-check/>
        <!--        <div id="nav">-->
        <!--            <router-link to="/">Home</router-link>-->
        <!--            |-->
        <!--            <router-link to="/about">About</router-link>-->
        <!--        </div>-->
        <!--        <router-view/>-->
        <!--        <vue-canvas-poster :widthPixels="0" :painting="painting" @success="success" @fail="fail"/>-->
    </div>
</template>
<script>
import QrCode from "@/QrCode.vue";
import FaceCheck from "@/FaceCheck.vue";

export default {
    name: 'app',
    mixins: [],
    components: {FaceCheck, QrCode},
    data() {
        return {
            painting: {},
            posterImg: "",
        }
    },
    mounted() {
        this.createShareCard()
    },
    methods: {
        /*生成二维码*/
        createShareCard() {
            let url = '测试数据'
            let painting = {
                width: "100px",
                height: "100px",
                top: "0px",
                views: [
                    {
                        type: 'qrcode',
                        content: url,
                        css: {
                            width: '100px',
                            color: '#000',
                            height: '100px'
                        }
                    },
                ]
            }
            this.painting = painting
        },
        /*生成成功回调*/
        success(src) {
            this.posterImg = src;
            console.log(src)
        },
        /*生成失败回调*/
        fail() {
            console.log('生成海报失败')
        },
        /*下载*/
        download() {
            let aLink = document.createElement("a");
            aLink.style.display = "none";
            aLink.href = this.posterImg;
            aLink.download = "test.jpg";
            document.body.appendChild(aLink);
            aLink.click();
            document.body.removeChild(aLink);
        }
    }
}
</script>


<style>
#app {
    font-family: Avenir, Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
}

#nav {
    padding: 30px;
}

#nav a {
    font-weight: bold;
    color: #2c3e50;
}

#nav a.router-link-exact-active {
    color: #42b983;
}
</style>
