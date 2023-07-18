<template>
    <div>
        <video src="" id="video" autoplay muted ></video>
    </div>
</template>
<script>

export default {
    name: 'face-api',
    mixins: [],
    components: {},
    data() {
        return {
            painting: {},
            posterImg: "",
            videoSrc: ''
        }
    },
    mounted() {
        this.openVideo()
    },
    methods: {
        async openVideo() {
            try {
                this.videoSrc = await navigator.mediaDevices.getUserMedia({video: true})
                const a = document.getElementById('video')
                a.srcObject = this.videoSrc
                console.log(this.videoSrc)
            } catch (e) {
                console.log(e)
            }
        },

    }
}
</script>

<style></style>







