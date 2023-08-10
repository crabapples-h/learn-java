<template>

</template>

<script>
import wx from 'weixin-jsapi'
import sha1 from 'js-sha1'

export default {
    name: "WechatLocation",
    mounted() {
        let appId = "appid"
        let jsapi_ticket = "O3SMpm8bG7kJnF36aXbe85Doj6P-XJdoIULK0l3KrIr0tKcx8qQ3qo7pzhSs5CKOhzPRsXjdZb1hMPoqhtK_KA"
        let timestamp = new Date().getTime()
        let nonceStr = "abcdef"
        let source = `jsapi_ticket=${jsapi_ticket}&noncestr=${nonceStr}&timestamp=${timestamp}&url=https://1072d12p84.oicp.vip`
        let signature = sha1(source)

        console.log(signature)
        wx.config({
            debug: true,
            appId: appId,
            timestamp: timestamp,
            nonceStr: nonceStr,
            signature: signature,
            jsApiList: []
        })
        wx.getLocation({
            type: 'wgs84', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
            success: function (res) {
                var latitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
                var longitude = res.longitude; // 经度，浮点数，范围为180 ~ -180。
                var speed = res.speed; // 速度，以米/每秒计
                var accuracy = res.accuracy; // 位置精度
            }
        })
    },
    methods: {}
}
</script>

<style scoped>

</style>
