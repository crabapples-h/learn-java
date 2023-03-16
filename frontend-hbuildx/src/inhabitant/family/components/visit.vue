<template>
	<div data="信访信息" id="parent">
		<template v-if="datainfo != null">
			<!-- <van-cell center title="姓名" value-class="red" :value="datainfo.pName+'(已故)'" v-if="isdie"></van-cell>
			<van-cell center title="姓名" :value="datainfo.pName" v-else></van-cell>
			<van-cell center title="性别" :value="datainfo.Sex"></van-cell> -->
			<van-cell title="姓名" :value="datainfo.jjRemark"></van-cell>
			<van-cell center title="是否越级上访" :value="datainfo.isYjsf"></van-cell>
			<van-cell center title="信访记录" :value="datainfo.Xfjls"></van-cell>
			<van-cell center title="越级上访单位等级" :value="datainfo.yjsfdwdj"></van-cell>
			<van-cell center title="信访时间" :value="datainfo.times"></van-cell>
			<van-cell center title="越级上访单位" :value="datainfo.yjsfdw"></van-cell>
			<van-cell center title="信访诉求" :value="datainfo.xfsq"></van-cell>
			<van-cell center title="信访备注" :value="datainfo.xfRemark"></van-cell>
			<van-cell center title="解决人员" :value="datainfo.jjry"></van-cell>
			<template data="信访材料照片">
				<van-cell>
					<span>信访材料照片</span>
				</van-cell>
				<van-cell :border="false">
					<img class="mui-media-object" :src="url(datainfo.xfclzp)" style="width: 100%;"
						@touchstart="downloadImg(datainfo.xfclzp)" @touchend="touchEnd" />
				</van-cell>
			</template>
		</template>
	</div>
</template>

<script>
	module.exports = {
		components: {},
		data() {
			return {
				options: {},
				show: {
					time: false
				}
			}
		},
		props: {
			datainfo: {
				type: Object,
				default: () => null
			},
			showButton: {
				type: Boolean,
				default: true
			},
			submit: {
				type: Function,
			},
			delete: {
				type: Function,
			},
		},
		mounted() {
			this.options = options
		},
		updated() {
			const viewer = window.Viewer
			var view = new viewer(document.getElementById('parent'), {
				movable: false,
				navbar: false,
				title: false,
				toolbar: false,
			})
		},
		methods: {
			touchEnd() {
				clearTimeout(this.loop); //清空定时器，防止重复注册定时器
			},
			downloadImg(img) {
				let Url = this.url(img)
				clearTimeout(this.loop);
				this.loop = setTimeout(() => {
					var blob = new Blob([''], {
						type: 'application/octet-stream'
					});
					var url = URL.createObjectURL(blob);
					var a = document.createElement('a');
					a.href = Url;
					a.download = Url.replace(/(.*\/)*([^.]+.*)/ig, "$2").split("?")[0];
					var e = document.createEvent('MouseEvents');
					e.initMouseEvent('click', true, false, window, 0, 0, 0, 0, 0, false, false, false,
						false,
						0, null);
					a.dispatchEvent(e);
					URL.revokeObjectURL(url);
				}, 1000);
			},
			url(e) {
				return UTIL.url(e)
			},
			formatTime() {
				var date = this.datainfo.bySj
				this.datainfo.bySj = new moment(date).format('YYYY-MM-DD')
			},
			saveData() {
				this.$emit('submit', this.datainfo)
			},
			openFiles() {
				const _this = this
				if (mui.os.plus) {
					var buttonTit = [{
						title: "拍照"
					}, {
						title: "从手机相册选择"
					}];
					plus.nativeUI.actionSheet({
						title: "上传图片",
						cancel: "取消",
						buttons: buttonTit
					}, function(b) {
						/*actionSheet 按钮点击事件*/
						switch (b.index) {
							case 0:
								break;
							case 1:
								_this.getImage(); /*拍照*/
								break;
							case 2:
								_this.galleryImg(); /*打开相册*/
								break;
							default:
								break;
						}
					})
				}
			},
			getImage() {
				const _this = this
				var c = plus.camera.getCamera();
				c.captureImage(function(e) {
					plus.io.resolveLocalFileSystemURL(e, function(entry) {
						var imgSrc = entry.toLocalURL() + "?version=" + new Date()
							.getTime();
						_this.uploadImg(imgSrc)
					}, function(e) {
						console.log("读取拍照文件错误：" + e.message);
					});
				}, function(s) {
					console.log("error" + s.message);
				}, {
					filename: "_doc/camera/"
				})
			},
			galleryImg() {
				const _this = this
				plus.gallery.pick(function(e) {
					_this.uploadImg(e)
				}, function(e) {
					console.log("取消选择图片");
				}, {
					filter: "image",
					multiple: false,
					system: false,
				});
			},
			uploadImg(file) {
				const _this = this
				var image = new Image();
				image.src = file
				var wt = plus.nativeUI.showWaiting();
				wt.close();
				var task = plus.uploader.createUpload(UTIL.url('global_file_upload.do'), {
						method: "POST"
					},
					function(t, status) { //上传完成
						if (status == 200) {
							let fileUrl = (JSON.parse(t.responseText)).content
							console.log('fileUrl', fileUrl)
							_this.datainfo.xfclzp = fileUrl
							wt.close(); //关闭等待提示按钮
						} else {
							alert('上传失败')
							wt.close(); //关闭等待提示按钮
						}
					}
				)

				task.addFile(image.src, {
					key: `file`
				});
				task.start();
			},
			deleteImg() {
				this.datainfo.xfclzp = ''
			},
		}
	}
</script>

<style scoped>
	.saveData {
		position: fixed;
		top: 5.20rem;
		z-index: 999;
		right: 10px;
		background-color: none;
	}

	.deleteData {
		position: fixed;
		top: 5.20rem;
		z-index: 999;
		left: 10px;
		background-color: none;
	}
</style>
