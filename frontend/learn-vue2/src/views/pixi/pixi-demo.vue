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
// https://www.gsap.com/archived-docs/
import * as PIXI from 'pixi.js'
import {gsap} from 'gsap'
import {PixiPlugin} from 'gsap/PixiPlugin'

gsap.registerPlugin(PixiPlugin)

export default {
	data() {
		return {
			img: require('@/assets/logo.png'),//21 x 787
			app: null,
			deviceWeight: window.innerWidth,
			deviceHeight: window.innerHeight,
		}
	},
	created() {
		console.log('设备宽度:', window.innerWidth, '设备高度:', window.innerHeight)
	},
	async mounted() {
		await this.createStage()
		this.loadResource()
	},
	methods: {
		// 加载资源
		loadResource() {
			this.app.loader.onProgress.add(this.loadProgress)
			this.app.loader
					.add(this.img)
					.load(this.loadFinishCallBack)
		},
		// 创建舞台
		createStage() {
			this.app = new PIXI.Application({
				width: this.deviceWeight,
				height: this.deviceHeight,
			})
			this.$refs.pixi.appendChild(this.app.view)
		},
		// 资源加载完成回调
		loadFinishCallBack() {
			let a = (this.deviceWeight / 21)
			let b = this.deviceHeight / a
			console.log(a, b)
			let texture = PIXI.utils.TextureCache[this.img]
			// texture.frame = new PIXI.Rectangle(0, 0, 21, b)
			let sprite = new PIXI.Sprite(texture)
			// sprite.scale.x = a
			// sprite.scale.y =17.8
			sprite.position.set((this.app.screen.width) / 2, (this.app.screen.height) / 2)
			// 弧度值
			sprite.rotation = Math.PI * 2
			// console.log(sprite.rotation)
			// 锚点
			// anchor.x和anchor.y值表示纹理尺寸的百分比，从0到1(0%到100%)。
			// 将它设置为0.5，使纹理在点上居中。
			// 点本身的位置不会改变，只会改变纹理的位置。
			sprite.anchor.set(0.5)
			// 轴心点
			// sprite.pivot.x = 50
			// sprite.pivot.y = 50
			// sprite.anchor.y = 1
			console.log(sprite.anchor)
			console.log(sprite.scale)
			// sprite.scale.set(2)
			this.app.ticker.add((delta) => {
				// console.log(delta)
				sprite.rotation += 0.01 * delta
			})
			// sprite.rotation = 0.5
			// sprite.anchor.x = 0.5
			// sprite.anchor.y = 0.5
			// sprite.width = 51
			this.app.stage.addChild(sprite)
			sprite.interactive = true
			sprite.addListener("tap", () => {
				console.log('click')
			})
		},
		// 资源加载进度
		loadProgress(loader, resource) {
			console.log('加载进度:', loader.progress)
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
