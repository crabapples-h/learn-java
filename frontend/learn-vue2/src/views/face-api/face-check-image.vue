<template>
    <div style="margin-top: 200px;text-align: center">
        <div>
            <img id="hidden-photo" src="" style="display: none"/>
            <img id="photo" src="" style="width: 500px;height: 600px"/>
            <input id="upload" type="file" @change="uploadImage" accept=".jpg, .jpeg, .png">
        </div>
    </div>
</template>
<script>

import * as faceapi from 'face-api.js'

export default {
    name: 'face-check-image',
    mixins: [],
    components: {},
    data() {
        return {
            canvas: null
        }
    },
    async mounted() {
        await this.loadModel()
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
            console.log(faceapi)
            await faceapi.loadFaceDetectionModel('/models')
            await faceapi.loadFaceLandmarkModel('/models')
            await faceapi.loadFaceExpressionModel('/models')
            await faceapi.loadAgeGenderModel('/models')
        },
        async uploadImage() {
            if (this.canvas) {
                this.canvas.remove()
            }
            let hiddenPhoto = document.getElementById('hidden-photo')
            const file = document.getElementById('upload').files[0]
            let img = await faceapi.bufferToImage(file)
            hiddenPhoto.src = img.src
            // this.changeImageSize(img.src)
            const detectionsWithLandmarks = await this.checkFace()
            this.drawFaceData(detectionsWithLandmarks)
        },

        async checkFace() {
            const photo = document.getElementById('hidden-photo')
            /* 检测面部特征 */
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
            return detectionsWithLandmarks
        },
        drawFaceData(detectionsWithLandmarks) {
            const photo = document.getElementById('hidden-photo')
            const canvas = faceapi.createCanvasFromMedia(photo)
            canvas.id = "check-result"
            canvas.style.display = "none"
            document.body.append(canvas)
            this.canvas = canvas
            const displaySize = {width: photo.width, height: photo.height}
            // 重置画布大小
            const resizedResults = faceapi.resizeResults(detectionsWithLandmarks, displaySize)
            // 绘制人脸方框
            const minProbability = 2
            console.log(resizedResults)
            // faceapi.draw.drawDetections(canvas, resizedResults)
            resizedResults.forEach(e => {
                let _box = e.detection._box
                const box = {
                    x: _box._x,
                    y: _box._y,
                    width: _box._width,
                    height: _box._height,
                }
                const drawOptions = {
                    // label: 'Hello I am a box!',
                    lineWidth: 3,
                    boxColor: '#f00'
                }
                const drawBox = new faceapi.draw.DrawBox(box, drawOptions)
                drawBox.draw(canvas)
            })
//             const box = {x: 50, y: 50, width: 100, height: 100}
// // see DrawBoxOptions below
//             const drawOptions = {
//                 label: 'Hello I am a box!',
//                 lineWidth: 2
//             }
//             const drawBox = new faceapi.draw.DrawBox(box, drawOptions)
//             drawBox.draw(canvas)

            // 绘制面部特征点
            faceapi.draw.drawFaceLandmarks(canvas, resizedResults)
            detectionsWithLandmarks.forEach((result, index) => {
                index += 1
                const {age, gender, genderProbability, expressions} = result
                // console.log(result)
                let genderText = gender
                if (gender === 'female') {
                    genderText = '女'
                } else if (gender === 'male') {
                    genderText = '男'
                }
                console.group("序号:", index)
                console.log(
                    `预测年龄:${~~age}`,
                    `预测性别:${genderText}`,
                    `性别概率:${genderProbability.toFixed(1)}`)
                console.log('面部表情:\n',
                    `愤怒:${expressions.angry}\n`,
                    `反感:${expressions.disgusted}\n`,
                    `害怕:${expressions.fearful}\n`,
                    `开心:${expressions.happy}\n`,
                    `中立:${expressions.neutral}\n`,
                    `难过:${expressions.sad}\n`,
                    `惊讶:${expressions.surprised}\n`)
                console.groupEnd()

                new faceapi.draw.DrawTextField([
                    `序号: ${index} `,
                    `年龄: ${~~age} `,
                    `性别:${genderText} {${genderProbability.toFixed(1)}}`
                ], result.detection.box.bottomLeft).draw(canvas)
            })
            this.changeImageSize()
        },
        changeImageSize() {
            document.getElementById('photo').src = this.canvas.toDataURL()
        },

    }
}
</script>

<style>
</style>







