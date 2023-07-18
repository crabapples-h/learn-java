<template>
    <div>
        <video src="" id="video" autoplay muted></video>
    </div>
</template>
<script>

export default {
    name: 'face-api',
    mixins: [],
    components: {},
    data() {
        return {
            videoSrc: '',
            modelPath: './face_landmark_68_tiny_model.json'
        }
    },
    mounted() {
        this.openVideo()
        this.loadModel()
    },
    methods: {
        openVideo() {
            try {
                navigator.mediaDevices.getUserMedia({video: true}).then(res => {
                    const video = document.getElementById('video')
                    video.srcObject = res
                })
            } catch (e) {
                console.log(e)
            }
        },
        async loadModel() {
            console.log(faceapi.nets)
             faceapi.loadTinyFaceDetectorModel(this.modelPath)
        }
    }
}
</script>

<style></style>







