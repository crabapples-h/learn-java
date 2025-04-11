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
      // 检查是否存在人脸
      const detectionsWithLandmarks = await this.checkFace()
      let faceInfo = this.drawFaceData(detectionsWithLandmarks)
      faceInfo.faces.forEach(e => {
        localStorage.setItem("face-info", JSON.stringify(e.point))
        console.log(e.point)
      })
      this.showPhoto()

    },
    // 检查是否存在人脸
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
      } : () => {
        console.log("未检测到人脸或图片可辨识度低")
      }
      data()
      console.log("面部68位特征点:", detectionsWithLandmarks)
      return detectionsWithLandmarks
    },
    // 绘制面部轨迹
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
      faceapi.draw.drawDetections(canvas, resizedResults)
      resizedResults.forEach((e, index) => {
        const drawOptions = {
          label: `目标:${++index}`,
          lineWidth: 3,
          boxColor: '#f00'
        }
        // 绘制人脸方框
        new faceapi.draw.DrawBox(e.detection._box, drawOptions).draw(canvas)
      })

      // 绘制面部特征
      faceapi.draw.drawFaceLandmarks(canvas, resizedResults)
      detectionsWithLandmarks.forEach((result, index) => {
        const {age, gender, genderProbability, expressions} = result
        let genderText = gender === "female" ? "女" : (gender === "male" ? "男" : gender);
        console.group("序号:" + ++index)
        console.table({
          '预测年龄': ~~age,
          '预测性别': genderText,
          '性别概率': genderProbability.toFixed(1),
          '愤怒': expressions.angry,
          '反感': expressions.disgusted,
          '害怕': expressions.fearful,
          '开心': expressions.happy,
          '中立': expressions.neutral,
          '难过': expressions.sad,
          '惊讶': expressions.surprised
        })
        console.groupEnd()
        // 绘制文字
        new faceapi.draw.DrawTextField([
          `序号: ${index} `,
          `年龄: ${~~age} `,
          `性别:${genderText} {${genderProbability.toFixed(1)}}`
        ], result.detection.box.bottomLeft).draw(canvas)
      })


      let faceInfo = detectionsWithLandmarks.map((faceItem, index) => {
        const {age, gender, genderProbability, expressions, landmarks, detection} = faceItem
        let genderText = gender === "female" ? "女" : (gender === "male" ? "男" : gender);
        console.group(["序号:", ++index])
        console.table({
          '预测年龄': ~~age,
          '预测性别': genderText,
          '性别概率': genderProbability.toFixed(1),
          '愤怒': expressions.angry,
          '反感': expressions.disgusted,
          '害怕': expressions.fearful,
          '开心': expressions.happy,
          '中立': expressions.neutral,
          '难过': expressions.sad,
          '惊讶': expressions.surprised
        })
        console.groupEnd()
        let text = {
          data: [`年龄: ${~~age}`, `${genderText}: ${genderProbability.toFixed(1)}`],
          pos: detection.box.bottomLeft
        }
        // 绘制性别
        new faceapi.draw.DrawTextField(text.data, {x: text.pos.x, y: text.pos.y + 5}).draw(canvas);
        return {text, age, gender, genderProbability, expressions, point: landmarks._positions};
      })

      return {
        count: resizedResults.length,
        base64: this.canvas.toDataURL(),
        faces: faceInfo
      };
    },
    // 显示图片
    showPhoto() {
      document.getElementById('photo').src = this.canvas.toDataURL()
    },

  }
}
</script>

<style>
</style>







