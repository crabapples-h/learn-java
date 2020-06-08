<template>
	<el-row :gutter="20">
		<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" style="text-align: center">
			<h2>{{title}}</h2>
			<br>
		</el-col>
		<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" style="text-align: center">
			<div id="editor"></div>
			<br>
			<el-button type="primary" @click="saveContent">立即发表</el-button>
		</el-col>
	</el-row>
</template>

<script>
    module.exports = {
        data() {
            return {
                wangEditorOptions: [
                    'head',  // 标题
                    'bold',  // 粗体
                    'fontSize',  // 字号
                    'fontName',  // 字体
                    'italic',  // 斜体
                    'underline',  // 下划线
                    'strikeThrough',  // 删除线
                    'justify',  // 对齐方式
                    'image',  // 插入图片
                ],
                editor: null,
                paper: {
                    content: ''
                },
                keyWords: 'honors',
                title: '富文本页面'
            }
        },
        mounted() {
            this.editor = new window.wangEditor('#editor');
            this.editor.customConfig.uploadImgShowBase64 = true;
            this.editor.customConfig.showLinkImg = false;
            this.editor.customConfig.pasteIgnoreImg = true;
            this.editor.customConfig.menus = this.wangEditorOptions;
            this.editor.create();
            this.getPaper(this.keyWords)
        },
        methods: {
            getPaper(keyWord) {
                const _this = this;
                axios.get(`/api/getPapersByKeyWords/${keyWord}`).then(response => {
                    const result = response.data;
                    console.log('通过api获取到的数据:', result);
                    if (result.status !== 200) {
                        this.$message.error('数据加载失败');
                        return
                    }
                    _this.paper = result.data[0] ? result.data[0] : _this.paper;
                    _this.editor.txt.html(_this.paper.content)
                }).catch(function (error) {
                    console.log('请求出现错误:', error);
                });
            },
            uploadSuccess(result) {
                console.log('通过api获取到的数据:', result);
                if (result.status !== 200) {
                    this.$message.error('上传失败');
                    return
                }
                this.$message.success('上传成功');
                this.getPictures()
            },
            saveContent() {
                const _this = this;
                _this.paper.content = _this.editor.txt.html();
                _this.paper.keyWord = this.keyWord;
                axios.post(`/api/savePaper`, _this.paper).then(response => {
                    const result = response.data;
                    console.log('通过api获取到的数据:', result);
                    if (result.status !== 200) {
                        this.$message.error('数据加载失败');
                        return
                    }
                    _this.content = result.data;
                    this.$message.success('编辑成功');
                }).catch(function (error) {
                    // 请求失败处理
                    console.log('请求出现错误:', error);
                });
            },

        }
    }
</script>

<style scoped>
</style>
