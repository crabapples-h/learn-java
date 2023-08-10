<template>
    <div id="app">
        <!--        <qr-code/>-->
        <!--        <face-check-image/>-->
<!--                <face-check-video/>-->
<!--                <table-demo/>-->
                <wechat-location/>
<!--        <face-check-video-file/>-->
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
import FaceCheckImage from "@/FaceCheckImage.vue";
import FaceCheckVideo from "@/FaceCheckVideo.vue";
import FaceCheckVideoFile from "@/FaceCheckVideoFile.vue";
import TableDemo from "@/TableDemo.vue";
import WechatLocation from "@/WechatLocation.vue";

export default {
    name: 'app',
    mixins: [],
    components: {
        WechatLocation,
        FaceCheckImage,
        FaceCheckVideo,
        FaceCheckVideoFile,
        QrCode,
        TableDemo,
    },
    data() {
        return {
            painting: {},
            posterImg: "",
        }
    },
    mounted() {
    },
    methods: {}
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
