<template>
	<div data="信访信息">
		<template >
			<template v-if="showButton">
				<button class="saveData mui-btn-primary" @click="saveData">确定</button>
			</template>
			<p>信访信息</p>
			<template data="解决备注">
				<div class="row-item">
					<span>姓名</span>
					<div>
						<input v-model="datainfo.jjRemark" class="mui-btn" style="width: 100%;" />
					</div>
				</div>
			</template>
			<template data="是否越级上访">
				<div class="row-item">
					<span>是否越级上访</span>
					<select class="mui-btn" v-model="datainfo.isYjsf" style="text-align-last: center;">
						<option value="">--选择是否越级上访--</option>
						<option value="是">是</option>
						<option value="否">否</option>
					</select>
				</div>
			</template>
			<template data="信访记录">
				<div class="row-item">
					<span>信访记录</span>
					<div>
						<input v-model="datainfo.Xfjls" class="mui-btn" style="width: 100%;" />
					</div>
				</div>
			</template>
			<template data="越级上访单位等级">
				<div class="row-item">
					<span>越级上访单位等级</span>
					<div>
						<input v-model="datainfo.yjsfdwdj" class="mui-btn" style="width: 100%;" />
					</div>
				</div>
			</template>
			<template data="信访时间">
				<div class="row-item">
					<span>信访时间</span>
					<div>
						<input v-model="datainfo.times" class="mui-btn" style="width: 100%;" />
					</div>
				</div>
			</template>
			<template data="越级上访单位">
				<div class="row-item">
					<span>越级上访单位</span>
					<div>
						<input v-model="datainfo.yjsfdw" class="mui-btn" style="width: 100%;" />
					</div>
				</div>
			</template>
			<template data="信访诉求">
				<div class="row-item">
					<span>信访诉求</span>
					<div>
						<input v-model="datainfo.xfsq" class="mui-btn" style="width: 100%;" />
					</div>
				</div>
			</template>
			<template data="信访备注">
				<div class="row-item">
					<span>信访备注</span>
					<div>
						<input v-model="datainfo.xfRemark" class="mui-btn" style="width: 100%;" />
					</div>
				</div>
			</template>
			<template data="解决人员">
				<div class="row-item">
					<span>解决人员</span>
					<div>
						<input v-model="datainfo.jjry" class="mui-btn" style="width: 100%;" />
					</div>
				</div>
			</template>
			<template data="信访材料照片">
				<div class="row-item">
					<span>信访材料照片</span>
					<!-- <div>
						<input v-model="datainfo.xfclzp" class="mui-btn" style="width: 100%;" />
					</div> -->
				</div>
				<div>
					<span style="font-size: 1rem; color: #666666;">照片：</span>
					<li class="mui-table-view-cell " v-if="datainfo.xfclzp">
						<img class="mui-media-object" :src="url(datainfo.xfclzp)" data-preview-src=""
							data-preview-group="1" style="width: 100%;" />
						<span class="mui-icon mui-icon-trash deleteBtn" @click="deleteImg()"
							style="background-color:#DDDDDD; top: 0.75rem; right: 0; position: absolute;z-index:1;"></span>
					</li>
					<div @click="openFiles" v-if="!datainfo.xfclzp"
						style="text-align: center; color: #0062CC; border: #0062CC 0.0625rem solid; background-color: #FFFFFF;">
						<span class="mui-icon mui-icon-image" style="font-size: 1.5rem;"></span>
						<span>上传</span>
					</div>
					<br>
				</div>
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
				datainfo: {
					Kid:this.$route.params.id-0,
					xfclzp:''
				},
				show: {
					time: false
				},
				showButton:true,
			}
		},
		props: {
		},
		mounted() {
			this.options = options
		},
		methods: {
			url(e){
				return UTIL.url(e)
			},
			formatTime() {
				var date = this.datainfo.bySj
				this.datainfo.bySj = new moment(date).format('YYYY-MM-DD')
			},
			saveData() {
				let url = UTIL.url(`/member/saveXfjl.do`)
				axios.post(url, this.datainfo).then(response => {
					alert('保存成功')
					window.history.back(-1)
				})
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
							console.log('fileUrl-->', fileUrl)
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
