<template>
    <div>
        <img id="photo" src="" style="display: none"/>
        <input id="upload" type="file" @change="uploadImage" accept=".jpg, .jpeg, .png">
    </div>
</template>
<script>

import * as faceapi from "face-api.js";

export default {
    name: 'face-check',
    mixins: [],
    components: {},
    data() {
        return {
            canvas: null
        }
    },
    mounted() {
    },
    methods: {
        changeImageSize(src) {
            let canvas = document.createElement('canvas');
            canvas.width = 300;
            canvas.height = 400;
            let ctx = canvas.getContext('2d');
            let img = new Image();
            img.src = src
            img.onload = function () {
                ctx.drawImage(img, 0, 0, 300, 400);
                document.getElementById('photo').src = canvas.toDataURL();
            }
        },
        async uploadImage() {
            if (this.canvas) {
                this.canvas.remove()
            }
            const imgFile = document.getElementById('upload').files[0]
            let img = await faceapi.bufferToImage(imgFile)
            this.changeImageSize(img.src)
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
            await faceapi.loadFaceExpressionModel('/models')
            await faceapi.loadAgeGenderModel('/models')
            const photo = document.getElementById('photo')

            const canvas = faceapi.createCanvasFromMedia(photo)
            canvas.id = "check-result"
            document.body.append(canvas)
            this.canvas = canvas
            const displaySize = {width: photo.width, height: photo.height}

            /* 显示面部特征 */
            const detectionsWithLandmarks = await faceapi
                .detectAllFaces(photo)
                .withFaceLandmarks()
                .withFaceExpressions()
                .withAgeAndGender()
            let length = detectionsWithLandmarks.length
            const data = length ? () => {
                console.log("%c%s%d", "color:red", "检测到人脸数:", length)
                // alert("检测到人脸数:" + length)
            } : () => {
                // alert("未检测到人脸或图片可辨识度低")
                console.log("未检测到人脸或图片可辨识度低")
            }
            data()
            console.log(detectionsWithLandmarks)
            // 重置画布大小
            const resizedResults = faceapi.resizeResults(detectionsWithLandmarks, displaySize)
            // 绘制人脸方框
            faceapi.draw.drawDetections(canvas, resizedResults)
            // 绘制面部特征点
            faceapi.draw.drawFaceLandmarks(canvas, resizedResults)
            detectionsWithLandmarks.forEach(result=>{
                const {age,gender,genderProbability} = result
                console.log(result)
                let genderText = gender
                if(gender==='female'){
                    genderText='女'
                }else if(gender==='male'){
                    genderText='男'
                }
                new faceapi.draw.DrawTextField([
                   `年龄: ${~~age} `,
                   `${genderText} {${genderProbability.toFixed(1)}}`
                ],result.detection.box.bottomLeft).draw(canvas)
            })
        }
    }
}
</script>

<style>
</style>







