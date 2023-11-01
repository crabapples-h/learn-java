<template>
	<div>
		<div ref="pixi"></div>
	</div>
</template>
<script>
// 文档 https://pixijs.huashengweilai.com/guide/start/7.rotation.html#%E6%97%8B%E8%BD%AC
// https://github.com/AlloyTeam/PhyTouch
// https://gsap.com/docs/v3/GSAP/Timeline/
// http://www.greensock.com/gsap-js/
import * as PIXI from 'pixi.js'
import PhyTouch from "phy-touch";

export default {
	data() {
		return {
			img: require('@/assets/bg.jpg'),
			pixi: null
		}
	},
	mounted() {
		this.touchDemo()
		// this.loadImg()
		
		// this.loadBackgroundImg()
		// this.loadProgress()
		// this.createPixi()
		// this.sayHello()
	},
	methods: {
		touchDemo() {
			let phyTouch = new PhyTouch({
				touch: "body",//反馈触摸的dom
				vertical: true,//不必需，默认是true代表监听竖直方向touch
				target: {y: 0}, //运动的对象
				property: "y",  //被运动的属性
				min: 1, //不必需,运动属性的最小值
				max: 2000, //不必需,滚动属性的最大值
				sensitivity: 1,//不必需,触摸区域的灵敏度，默认值为1，可以为负数
				factor: 1,//不必需,表示触摸位移运动位移与被运动属性映射关系，默认值是1
				moveFactor: 1,//不必需,表示touchmove位移与被运动属性映射关系，默认值是1
				step: 45,//用于校正到step的整数倍
				bindSelf: false,
				maxSpeed: 2, //不必需，触摸反馈的最大速度限制
				value: 0,
				change: function (value) {
					console.log(value)
					// target.style.transform = "translate(0," + value + "px)"
					// target.style.webkitTransform = "translate(0," + value + "px)"
				},
				touchStart: function (evt, value) {
				},
				touchMove: function (evt, value) {
				},
				touchEnd: function (evt, value) {
				},
				tap: function (evt, value) {
				},
				pressMove: function (evt, value) {
				},
				animationEnd: function (value) {
				} //运动结束
			})},
		loadImg() {
			// 创建一个舞台
			let app = new PIXI.Application({
				width: 750,           // default: 800 宽度
				height: 1448,        // default: 600 高度
				antialias: true,     // default: false 反锯齿
				transparent: false,  // default: false 透明度
				resolution: 1,      // default: 1 分辨率
			})
			app.stage.alpha = 1 // 透明度
			let bgi = [require('@/assets/img.jpg')]
			let images = []
			for (let i = 0; i < 70; i++) {
				images.push(require(`@/assets/images/${i + 1}.jpeg`))
			}
			this.$refs.pixi.appendChild(app.view)
			// console.log(app)
			// console.log(app.loader)
			// 监听加载函数，每加载一个文件就调用一次
			app.loader.onProgress.add((loader, resource) => {
				// console.log(loader, resource)
				// console.log(loader.progress)
			})
			let sprites = []
			app.loader
					// .add(bgi)
					// .add(images)
					// .add(images)
					.load(() => {
						// 创建一个精灵
						// let sprite = new PIXI.Sprite(app.loader.resources[bgi].texture)
						//
						// // sprite.width = app.view.width
						// // sprite.height = app.view.height
						// sprite.scale.set(30, 30) //缩放
						// sprite.position.set(0, 0) //位置
						// sprite.anchor.set(0, 0) //锚点
						// app.stage.addChild(sprite)
						images.forEach(e => {
							let image = PIXI.Texture.from(e) //纹理精灵
							// console.log('11', image)
							// 截取图片的坐标 x,y,weight,height
							let rectangle = new PIXI.Rectangle(0, 0, 360, 480)
							let item = new PIXI.Texture(image, rectangle)
							sprites.push(item)
						})
						let animatedSprite = new PIXI.AnimatedSprite(sprites)
						// new PIXI.
					})
		},
		
		
		loadBackgroundImg() {
			// 创建一个舞台
			let app = new PIXI.Application({
				width: 750,           // default: 800 宽度
				height: 1448,        // default: 600 高度
				antialias: true,     // default: false 反锯齿
				transparent: false,  // default: false 透明度
				resolution: 1,      // default: 1 分辨率
			})
			app.stage.alpha = 1 // 透明度
			let bgi = [require('@/assets/img.jpg')]
			this.$refs.pixi.appendChild(app.view)
			app.loader
					.add(bgi)
					.load(() => {
						// 创建一个精灵
						let sprite = new PIXI.Sprite(app.loader.resources[bgi].texture)
						
						// sprite.width = app.view.width
						// sprite.height = app.view.height
						sprite.scale.set(30, 30) //缩放
						sprite.position.set(0, 0) //位置
						sprite.anchor.set(0, 0) //锚点
						app.stage.addChild(sprite)
					})
		},
		loadProgress() {
			// 创建一个舞台
			let app = new PIXI.Application({
				width: 750,           // default: 800 宽度
				height: 1448,        // default: 600 高度
			})
			let images = []
			for (let i = 0; i < 70; i++) {
				images.push(require(`@/assets/images/${i + 1}.jpeg`))
			}
			this.$refs.pixi.appendChild(app.view)
			// 监听加载函数，每加载一个文件就调用一次
			app.loader.onProgress.add((loader, resource) => {
				console.log('加载进度:', parseFloat(loader.progress.toFixed(2)))
			})
			app.loader
					.add(images)
					.load(() => {
					})
		},
		createPixi() {
			console.log(this.img)
			this.pixi = new PIXI.Application({
				width: 750,
				height: 1448,
			})
			this.$refs.pixi.appendChild(this.pixi.view)
		},
		sayHello() {
			let type = 'WebGL'
			if (!PIXI.utils.isWebGLSupported()) {
				type = 'canvas'
			}
			PIXI.utils.sayHello(type)
		},
		
		
	}
}
</script>
<style scoped>
* {
	padding: 0;
	margin: 0
}
</style>
