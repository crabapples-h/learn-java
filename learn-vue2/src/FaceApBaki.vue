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
                    const canvas2 = faceapi.createCanvasFromMedia(video)
                    document.body.appendChild(canvas2)

                })
            } catch (e) {
                console.log(e)
            }
        },
        async loadModel() {
            console.log(faceapi)
            // await faceapi.nets.ssdMobilenetv1.loadFromUri('/models')
            await faceapi.loadFaceDetectionModel('/models')
            await faceapi.loadFaceLandmarkModel('/models')
            await faceapi.loadFaceExpressionModel('/models')

            // loadAgeGenderModel
            // loadFaceDetectionModel
            // loadFaceExpressionModel
            // loadFaceLandmarkModel
            // loadFaceLandmarkTinyModel
            // loadFaceRecognitionModel
            // loadSsdMobilenetv1Model
            // loadTinyFaceDetectorModel
            // loadTinyYolov2Model


            // faceapi.tinyFaceDetector(this.modelPath)
            const input = document.getElementById('myImg')
            // const options = new faceapi.SsdMobilenetv1Options({ minConfidence: 0.8 })
            // console.log(options)
            // console.log(faceapi.IFaceDetection())


            const detectionsWithLandmarks = await faceapi.detectAllFaces(input).withFaceLandmarks()
            console.log(detectionsWithLandmarks)
            // const displaySize = {width: input.width, height: input.height}
            // console.log(displaySize)
            //
            // // resize the overlay canvas to the input dimensions
            // const canvas = document.getElementById('overlay')
            // // faceapi.matchDimensions(canvas, displaySize)
            //
            // /* 显示面部边界 */
            // const detections = await faceapi.detectAllFaces(input)
            // // resize the detected boxes in case your displayed image has a different size than the original
            // const resizedDetections = faceapi.resizeResults(detections, displaySize)
            // // draw detections into the canvas
            // faceapi.draw.drawDetections(canvas, resizedDetections)

            /* 显示面部特征 */
            // const detectionsWithLandmarks = await faceapi
            //     .detectAllFaces(input)
            //     .withFaceLandmarks()
            // // resize the detected boxes and landmarks in case your displayed image has a different size than the original
            // const resizedResults = faceapi.resizeResults(detectionsWithLandmarks, displaySize)
            // 矩形标注面部
            // faceapi.draw.drawDetections(canvas, resizedResults)
            // 标注面部特征点
            // faceapi.draw.drawFaceLandmarks(canvas, resizedResults)


            /* 显示面部表情 */
            // const detectionsWithExpressions = await faceapi
            //     .detectAllFaces(input)
            //     .withFaceLandmarks()
            //     .withFaceExpressions()
            // // resize the detected boxes and landmarks in case your displayed image has a different size than the original
            // const resizedResults = faceapi.resizeResults(detectionsWithExpressions, displaySize)
            // // draw detections into the canvas
            // faceapi.draw.drawDetections(canvas, resizedResults)
            // // draw a textbox displaying the face expressions with minimum probability into the canvas
            // const minProbability = 0.05
            // faceapi.draw.drawFaceExpressions(canvas, resizedResults, minProbability)
        }
    }
}
</script>

<style></style>







