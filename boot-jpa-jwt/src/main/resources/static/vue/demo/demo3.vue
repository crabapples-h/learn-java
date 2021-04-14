<template>
	<el-row>
		<el-col>
			<el-upload action="/api/uploadFile/newFile" list-type="picture-card" :file-list="fileList"
			           :on-preview="handlePictureCardPreview" :on-remove="handleRemove"
			           :on-success="uploadSuccess">
				<i class="el-icon-plus"></i>
			</el-upload>
			<el-dialog :visible.sync="dialogVisible">
				<img width="100%" :src="dialogImageUrl">
			</el-dialog>
		</el-col>
	</el-row>

</template>
<script>
    module.exports = {
        data() {
            return {
                fileList: [],
                dialogImageUrl: '',
                dialogVisible: false,
                file: {},
                keyWord: 'demo3'
            };
        },
        mounted() {
            this.getFileList(this.keyWord)
        },
        methods: {
            handleRemove(file, fileList) {
                const _this = this;
                const id = file.id;
                axios.delete(`/api/removeFileById/${id}`).then(response => {
                    _this.getFileList(_this.keyWord);
                    const result = response.data;
                    console.log('通过api获取到的数据:', result);
                    if (result.status !== 200) {
                        _this.$message.error('数据加载失败');
                        return
                    }
                    _this.$message.success('操作成功')
                }).catch(function (error) {
                    _this.getFileList(_this.keyWord);
                    console.log('请求出现错误:', error);
                });
            },
            handlePictureCardPreview(file) {
                this.dialogImageUrl = file.url;
                this.dialogVisible = true;
            },
            uploadSuccess(result) {
                console.log('通过api获取到的数据:', result);
                if (result.status !== 200) {
                    this.$message.error('上传失败');
                    return
                }
                this.file = result.data;
                this.file.keyWord = this.keyWord;
                this.updateFile();
                window.location.reload();
            },
            updateFile() {
                axios.post(`/api/updateFile`, this.file).then(response => {
                    const result = response.data;
                    console.log('通过api获取到的数据:', result);
                    if (result.status !== 200) {
                        this.$message.error('上传失败');
                        return
                    }
                    this.$message.success('上传成功');
                }).catch(function (error) {
                    console.log('请求出现错误:', error);
                });
            },
            getFileList(keyWord) {
                const _this = this;
                axios.get(`/api/getFileListByKeyWords/${keyWord}`).then(response => {
                    const result = response.data;
                    console.log('通过api获取到的数据:', result);
                    if (result.status !== 200) {
                        this.$message.error('数据加载失败');
                        return;
                    }
                    _this.fileList = result.data;
                }).catch(function (error) {
                    console.log('请求出现错误:', error);
                });
            },
        }
    }
</script>
<style>


</style>
