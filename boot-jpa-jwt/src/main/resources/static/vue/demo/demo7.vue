<template>
	<el-row>
		<el-table :data="tableData" stripe style="width: 100%">
			<el-table-column prop="name" label="站点名"></el-table-column>
			<el-table-column label="操作" width="160">
				<template slot-scope="scope">
					<el-button type="danger" @click="remove(scope)" size="mini">删除</el-button>
					<el-button type="primary" @click="edit(scope)" size="mini">编辑</el-button>
				</template>
			</el-table-column>
		</el-table>
		<el-button type="primary" @click="edit(undefined)" size="mini">添加</el-button>
	</el-row>

</template>

<script>
    module.exports = {
        data() {
            return {
                stands: [],
                standsData: [],
                tableData: [],
                form: {
                    id: '',
                    name: '',
                },
                formLabelWidth: '80px',
            };
        },
        mounted() {
            this.getTableDataList()
        },
        methods: {
            remove(scope) {
                const _this = this;
                const id = scope.row.id;
                _this.$confirm('确认删除？').then(e => {
                    axios.delete(`/api/removeStandsById/${id}`).then(response => {
                        _this.getTableDataList();
                        const result = response.data;
                        console.log('通过api获取到的数据:', result);
                        if (result.status !== 200) {
                            _this.$message.error('数据加载失败');
                            return
                        }
                        _this.$message.success('操作成功')
                    }).catch(function (error) {
                        _this.getTableDataList();
                        console.log('请求出现错误:', error);
                    });
                });
            },
            getTableDataList() {
                const _this = this;
                axios.get(`/api/getStandsList`).then(response => {
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
            edit(scope) {
                const _this = this
                _this.$nextTick(() => {
                    _this.getDataById(scope ? scope.row.id : ' ');
                    _this.$prompt('请输入站点名', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                    }).then(({value}) => {
                        _this.form.name = value
                        axios.post(`/api/saveStandsInfo`, _this.form).then(response => {
                            const result = response.data;
                            console.log('通过api获取到的数据:', result);
                            if (result.status !== 200) {
                                this.$message.error('数据加载失败');
                                return
                            }
                            _this.$message.success('操作成功');
                            _this.getTableDataList()
                        }).catch(function (error) {
                            window.location.reload();
                            console.log('请求出现错误:', error);
                        });
                    })
                })
            },
            getDataById(id) {
                const _this = this;
                axios.get(`/api/getStandsById?id=${id}`).then(response => {
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
        }
    }
</script>

<style scoped>
	.table-row-hidden {
		white-space: nowrap;
		overflow: hidden;
		text-overflow: ellipsis;
	}

	.drawer-footer {
		margin-left: 10px;
	}
</style>
