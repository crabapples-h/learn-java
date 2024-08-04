<template>
	<div id="content">
		<video id="video" autoplay controls width="640" height="480" class="media"></video>
	</div>
</template>
<script>

import * as faceapi from 'face-api.js'

export default {
	name: 'face-check-video',
	mixins: [],
	components: {},
	data() {
		return {
			canvas: null
		}
	},
	async mounted() {
		await this.loadModel()
		this.openVideo()
	},
	methods: {
		openVideo() {
			const _this = this
			try {
				navigator.mediaDevices.getUserMedia({video: true}).then(res => {
					let event = null
					const video = document.getElementById('video')
					video.srcObject = res
					video.addEventListener("play", () => {
						if (_this.canvas != null) {
							_this.canvas.remove()
						}
						let {width, height} = video
						const canvas = faceapi.createCanvasFromMedia(video)
						document.getElementById('content').append(canvas)
						_this.canvas = canvas
						let ctx = canvas.getContext('2d')
						event = setInterval(async () => {
							let faceData = await _this.checkFace(video)
							ctx.drawImage(video, 0, 0, width, height);
							await _this.drawFaceData(faceData, canvas, width, height)
							// ctx.clearRect(0, 0, width, height)
						}, 100)
					})
					video.addEventListener("pause", () => {
						clearInterval(event)
					})
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
			await faceapi.loadTinyFaceDetectorModel('/models')
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

		async checkFace(source) {
			/* 检测面部特征 */
			return faceapi
					.detectAllFaces(source, new faceapi.TinyFaceDetectorOptions())
					.withFaceLandmarks()
					.withFaceExpressions()
					.withAgeAndGender();
		},
		async drawFaceData(detections, canvas, width, height) {
			// this.canvas = canvas
			// 重置画布大小
			console.log(width, height)
			console.log(canvas.width, canvas.height)
			const resizedResults = await faceapi.resizeResults(detections, {width, height})
			// 绘制人脸方框
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
			// // 绘制面部特征点
			faceapi.draw.drawFaceLandmarks(canvas, resizedResults)
			console.log(detections)
			detections.forEach((result, index) => {
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
			// this.changeImageSize()
		}
		,
		changeImageSize() {
			document.getElementById('photo').src = this.canvas.toDataURL()
		},

	}
}
</script>

<style scoped>
#content {
	margin: 100px;
}

.media {
//display: none; //opacity: 0;
}

</style>







