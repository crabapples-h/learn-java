<template>
	<el-row>
		<el-table :data="tableData" stripe style="width: 100%">
			<el-table-column prop="name" label="商品名"></el-table-column>
			<el-table-column prop="price" label="价格"></el-table-column>
			<el-table-column prop="remaining" label="库存"></el-table-column>
			<el-table-column prop="sale" label="销量"></el-table-column>
			<el-table-column label="操作" width="160">
				<template slot-scope="scope">
					<el-button type="danger" @click="remove(scope)" size="mini">删除</el-button>
					<el-button type="primary" @click="drawerOpen(scope)" size="mini">编辑</el-button>
				</template>
			</el-table-column>
		</el-table>
		<el-button type="primary" @click="drawerOpen(undefined)" size="mini">添加</el-button>
		<el-drawer :before-close="drawerClose" :visible.sync="drawer.show"
		           :wrapperClosable="false" ref="drawer" size="70%" v-loading="drawer.loading">
			<div class="demo-drawer__content">
				<el-form v-model="form">
					<el-input v-model="form.id" v-if="form.id" style="display: none"></el-input>
					<el-form-item label="商品名" :label-width="formLabelWidth">
						<el-input v-model="form.name" autocomplete="off"></el-input>
					</el-form-item>
					<el-form-item label="价格" :label-width="formLabelWidth">
						<el-input v-model="form.price" autocomplete="off" type="number"></el-input>
					</el-form-item>
					<el-form-item label="库存" :label-width="formLabelWidth">
						<el-input v-model="form.remaining" autocomplete="off"></el-input>
					</el-form-item>
					<el-form-item label="商品介绍" :label-width="formLabelWidth">
						<el-input type="textarea" :rows="4" placeholder="请输入商品详情" v-model="form.content"></el-input>
					</el-form-item>
					<el-form-item label="商品图片" :label-width="formLabelWidth">
						<!--            <el-upload action="/api/uploadFile/newFile" list-type="picture-card" :file-list="fileList"-->
						<el-upload action="/api/uploadFile/newFile" list-type="picture-card" :file-list="form.audioFiles"
						           :on-preview="handlePictureCardPreview" :on-remove="handleRemove"
						           :on-success="uploadSuccess">
							<i class="el-icon-plus"></i>
						</el-upload>
						<el-dialog :visible.sync="dialogVisible">
							<img width="100%" :src="dialogImageUrl" alt="">
						</el-dialog>
					</el-form-item>
				</el-form>
				<div class="drawer-footer">
					<el-button type="primary" @click="saveAnimalInfo" :loading="drawer.loading">确定</el-button>
					<el-button @click="drawerClose">取 消</el-button>
				</div>
			</div>
		</el-drawer>
	</el-row>

</template>

<script>
    module.exports = {
        data() {
            return {
                // fileList: [],
                dialogImageUrl: '',
                dialogVisible: false,
                file: {},
                tableData: [],
                form: {
                    id: '',
                    keyWord: '',
                    remaining: '',
                    content: '',
                    name: '',
                    price: '',
                    audioFiles: []
                },
                drawer: {
                    show: false,
                    loading: false,
                },
                formLabelWidth: '80px',
                keyWord: 'products',
            };
        },
        mounted() {
            this.getAnimalList(this.keyWord)
        },
        methods: {
            remove(scope) {
                const _this = this;
                const id = scope.row.id;
                _this.$confirm('确认删除？').then(e => {
                    _this.drawer.loading = true;
                    axios.delete(`/api/removeAnimalById/${id}`).then(response => {
                        _this.getPaperList(_this.keyWord);
                        const result = response.data;
                        console.log('通过api获取到的数据:', result);
                        if (result.status !== 200) {
                            _this.$message.error('数据加载失败');
                            return
                        }
                        _this.$message.success('操作成功')
                    }).catch(function (error) {
                        _this.getAnimalList();
                        console.log('请求出现错误:', error);
                    });
                });
            },
            getAnimalList() {
                const _this = this;
                axios.get(`/api/getAnimalList`).then(response => {
                    const result = response.data;
                    console.log('通过api获取到的数据:', result);
                    if (result.status !== 200) {
                        this.$message.error('数据加载失败');
                        return;
                    }
                    _this.tableData = result.data;
                }).catch(function (error) {
                    console.log('请求出现错误:', error);
                });
            },

            drawerOpen(scope) {
                this.drawer.show = true;
                this.drawer.loading = true;
                this.$nextTick(() => {
                    this.getAnimalById(scope ? scope.row.id : '');
                })
            },
            getAnimalById(id) {
                const _this = this;
                axios.get(`/api/getAnimalById?id=${id}`).then(response => {
                    const result = response.data;
                    console.log('通过api获取到的数据:', result);
                    if (result.status !== 200) {
                        this.$message.error('数据加载失败');
                        return
                    }
                    _this.form = result.data;
                    this.drawer.loading = false;
                    // _this.fileList = result.data.audioFiles;
                }).catch(function (error) {
                    console.log('请求出现错误:', error);
                });
            },
            saveAnimalInfo() {
                const _this = this;
                _this.loading = true;
                _this.form.keyWord = this.keyWord;
                axios.post(`/api/saveAnimalInfo`, _this.form).then(response => {
                    const result = response.data;
                    console.log('通过api获取到的数据:', result);
                    if (result.status !== 200) {
                        this.$message.error('数据加载失败');
                        return
                    }
                    _this.content = result.data;
                    _this.$message.success('操作成功');
                    _this.drawer.loading = false;
                    _this.drawer.show = false;
                    _this.getAnimalList();
                }).catch(function (error) {
                    this.$message.error(error);
                    setTimeout(() => {
                        window.location.reload();
                    }, 3000);

                });
            },
            drawerClose() {
                this.drawer.loading = false;
                this.drawer.show = false;
                this.getAnimalList();
            },

            handleRemove(file, fileList) {
                const _this = this;
                const id = file.id;
                axios.delete(`/api/removeFileById/${id}`).then(response => {
                    // _this.getFileList(_this.keyWord);
                    const result = response.data;
                    console.log('通过api获取到的数据:', result);
                    if (result.status !== 200) {
                        _this.$message.error('数据加载失败');
                        return
                    }
                    _this.$message.success('操作成功')
                }).catch(function (error) {
                    // _this.getFileList(_this.keyWord);
                    console.log('请求出现错误:', error);
                });
            },
            handlePictureCardPreview(file) {
                this.dialogImageUrl = file.url;
                this.dialogVisible = true;
            },
            uploadSuccess(result) {
                this.drawer.loading = true;
                console.log('通过api获取到的数据:', result);
                if (result.status !== 200) {
                    this.$message.error('上传失败');
                    return
                }
                this.file = result.data;
                this.file.keyWord = this.keyWord;
                this.updateFile();
                // this.getFileList(this.form.id)
            },
            updateFile() {
                axios.post(`/api/updateFile`, this.file).then(response => {
                        const result = response.data;
                        console.log('通过api获取到的数据:', result);
                        if (result.status !== 200) {
                            this.$message.error('上传失败');
                            return
                        }
                        result.data.url = `${result.data.url}`;
                        this.$message.success('上传成功');
                        this.form.audioFiles.push(result.data);
                        console.log('文件列表:', this.form.audioFiles);
                        this.drawer.loading = false;
                    }
                ).catch(function (error) {
                    console.log('请求出现错误:', error);
                });
            },
        }
    }
</script>

<style scoped>
	.drawer-footer {
		margin-left: 10px;
	}
</style>
