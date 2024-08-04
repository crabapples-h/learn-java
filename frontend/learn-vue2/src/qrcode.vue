<template>
    <div>
        <img :src="posterImg" alt="" style="width: 100px">
        <br>
        <button @click="download">下载</button>
        <vue-canvas-poster :widthPixels="0" :painting="painting" @success="success" @fail="fail"/>
    </div>
</template>
<script>

export default {
    name: 'qr-code',
    mixins: [],
    components: {},
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
            this.painting = {
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
</style>
