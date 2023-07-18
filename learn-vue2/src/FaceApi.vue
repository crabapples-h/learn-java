<template>
    <div>
        <!--        <video src="" id="video" autoplay muted></video>-->
        <img id="myImg" src=""/>
        <input id="myFileUpload" type="file" @change="uploadImage" accept=".jpg, .jpeg, .png">
        <hr>
        <canvas id="overlay" width="100px" height="100px">测试</canvas>
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
        // this.openVideo()
        // this.loadModel()
    },
    methods: {
        async uploadImage() {
            const imgFile = document.getElementById('myFileUpload').files[0]
            const img = await faceapi.bufferToImage(imgFile)
            document.getElementById('myImg').src = img.src
            await this.loadModel()
        },
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
            console.log(faceapi)
            await faceapi.loadFaceDetectionModel('/models')
            await faceapi.loadFaceLandmarkModel('/models')
            const input = document.getElementById('myImg')

            const detectionsWithLandmarks = await faceapi.detectAllFaces(input).withFaceLandmarks()
            let length = detectionsWithLandmarks.length
            const data = length ? () => {
                console.log("%c%s%d", "color:red", "检测到人脸数:", length)
                alert("检测到人脸数:"+ length)
            } : () => {
                alert("未检测到人脸或图片可辨识度低")
            }
            data()
            console.log(detectionsWithLandmarks)
        }
    }
}
</script>

<style></style>







