<template>
	<el-row>
		<el-table :data="tableData" stripe style="width: 100%">
			<el-table-column prop="createTime" label="日期" width="220"></el-table-column>
			<el-table-column prop="title" label="标题" width="180"></el-table-column>
			<el-table-column prop="content" label="正文" :show-overflow-tooltip="true"></el-table-column>
			<el-table-column label="操作" width="160">
				<template slot-scope="scope">
					<el-button type="danger" @click="remove(scope)" size="mini">删除</el-button>
					<el-button type="primary" @click="drawerOpen(scope)" size="mini">编辑</el-button>
				</template>
			</el-table-column>
		</el-table>
		<el-button type="primary" @click="drawerOpen(undefined)" size="mini">添加</el-button>
		<el-drawer :visible.sync="drawer.show" :wrapperClosable="false" size="50%">
			<div class="demo-drawer__content">
				<el-form v-model="form">
					<el-form-item label="通知标题" :label-width="formLabelWidth">
						<el-input v-model="form.title" autocomplete="off"></el-input>
					</el-form-item>
					<el-form-item label="通知内容" :label-width="formLabelWidth">
						<el-input type="textarea" :rows="4" placeholder="请输入通知内容" v-model="form.content"></el-input>
					</el-form-item>
				</el-form>
				<div class="drawer-footer">
					<el-button type="primary" @click="savePaper" :loading="drawer.loading">
						{{ drawer.loading ? '提交中 ...' : '确定'}}
					</el-button>
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
                tableData: [],
                form: {
                    id: '',
                    keyWords: '',
                    title: '',
                    content: '',
                },
                drawer: {
                    show: false,
                    loading: false,
                },
                formLabelWidth: '80px',
                keyWords: 'demo2',
            };
        },
        mounted() {
            this.getTableDataList(this.keyWord)
        },
        methods: {
            remove(scope) {
                const _this = this;
                const id = scope.row.id;
                _this.$confirm('确认删除？').then(e => {
                    _this.drawer.loading = true;
                    axios.delete(`/api/removePapersById/${id}`).then(response => {
                        _this.getTableDataList(this.keyWords);
                        const result = response.data;
                        console.log('通过api获取到的数据:', result);
                        if (result.status !== 200) {
                            _this.$message.error('数据加载失败');
                            return
                        }
                        _this.$message.success('操作成功')
                    }).catch(function (error) {
                        _this.getTableDataList(_this.keyWords);
                        console.log('请求出现错误:', error);
                    });
                });
            },
            getTableDataList(keyWord) {
                const _this = this;
                axios.get(`/api/getPapersByKeyWords/${keyWord}`).then(response => {
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
                this.$nextTick(() => {
                    this.getPaperById(scope ? scope.row.id : ' ');
                    this.drawer.show = true;
                })
            },
            getPaperById(id) {
                const _this = this;
                axios.get(`/api/getPapersById?id=${id}`).then(response => {
                    const result = response.data;
                    console.log('通过api获取到的数据:', result);
                    if (result.status !== 200) {
                        this.$message.error('数据加载失败');
                        return
                    }
                    _this.form = result.data;
                }).catch(function (error) {
                    console.log('请求出现错误:', error);
                });
            },
            savePaper() {
                const _this = this;
                _this.drawer.loading = true;
                _this.form.keyWords = this.keyWords;
                axios.post(`/api/savePapers`, _this.form).then(response => {
                    const result = response.data;
                    console.log('通过api获取到的数据:', result);
                    if (result.status !== 200) {
                        this.$message.error('数据加载失败');
                        return
                    }
                    _this.$message.success('操作成功');
                    _this.drawer.loading = false;
                    _this.drawer.show = false;
                    _this.getTableDataList(this.keyWords)
                }).catch(function (error) {
                    window.location.reload();
                    console.log('请求出现错误:', error);
                });
            },
            drawerClose() {
                this.drawer.loading = false;
                this.drawer.show = false;
                this.getTableDataList(this.keyWords);
            },
        }
    }
</script>

<style scoped>
	.drawer-footer {
		margin-left: 10px;
	}
</style>
