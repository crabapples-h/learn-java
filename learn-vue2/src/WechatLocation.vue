<template>

</template>

<script>
import wx from 'weixin-jsapi'
import sha1 from 'js-sha1'

export default {
    name: "WechatLocation",
    mounted() {
        this.config()
    },
    methods: {
        config() {
            const _this = this
            let url = location.href.split('#')[0];
            _this.$axios.get(`/api/wechat/get/config?url=${url}`).then(response => {
                const result = response.data
                const data = result.data
                wx.config({
                    debug: false,
                    appId: data.appId,
                    timestamp: data.timestamp,
                    nonceStr: data.nonceStr,
                    signature: data.sign,
                    jsApiList: ['getLocation']
                })
                wx.ready(function () {
                    _this.getLocation()
                });
            })
        },
        getLocation() {
            wx.getLocation({
                type: 'wgs84', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
                success: function (res) {
                    let latitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
                    let longitude = res.longitude; // 经度，浮点数，范围为180 ~ -180。
                    let speed = res.speed; // 速度，以米/每秒计
                    let accuracy = res.accuracy; // 位置精度
                    console.group()
                    console.log("经度:", latitude)
                    console.log("维度:", longitude,)
                    console.log("速度(m/s):", speed,)
                    console.log("位置精度:", accuracy)
                    console.groupEnd()
                }
            })
        },
    }
}
</script>

<style scoped>

</style>
