<template>
	<el-row>
		<el-table :data="tableData" stripe style="width: 100%" :height="400">
			<el-table-column prop="name" label="设备名"></el-table-column>
			<el-table-column prop="time" label="单次运行时长(分钟)"></el-table-column>
			<el-table-column prop="timeCount" label="总运行时长(分钟)"></el-table-column>
			<el-table-column prop="lineCount" label="预约次数"></el-table-column>
			<el-table-column prop="useCount" label="使用次数"></el-table-column>
			<el-table-column label="操作" width="160">
				<template slot-scope="scope">
					<el-button type="danger" @click="remove(scope)" size="mini">删除</el-button>
					<el-button type="primary" @click="drawerOpen(scope)" size="mini">编辑</el-button>
				</template>
			</el-table-column>
		</el-table>
		<el-drawer :visible.sync="drawer.show" :wrapperClosable="false" size="50%">
			<div class="demo-drawer__content">
				<el-form v-model="form">
					<el-form-item label="设备名称" :label-width="formLabelWidth">
						<el-input size="mini" v-model="form.name" autocomplete="off"></el-input>
					</el-form-item>
					<el-form-item label="运行时长" :label-width="formLabelWidth">
						<el-input size="mini" v-model="form.time" autocomplete="off" placeholder="单位(分钟)" type="number"></el-input>
					</el-form-item>
					<el-form-item label="注意事项" :label-width="formLabelWidth">
						<el-input type="textarea" :rows="4" v-model="form.content"></el-input>
					</el-form-item>
					<el-form-item :label-width="formLabelWidth">
						<el-button size="mini" @click="saveForm" type="primary">确定</el-button>
						<el-button size="mini" @click="drawerClose">取 消</el-button>
					</el-form-item>
				</el-form>
			</div>
		</el-drawer>

		<div style="margin-top: 10px">
			<el-button size="mini" type="primary" @click="drawerOpen(undefined)">添加</el-button>
		</div>
	</el-row>

</template>

<script>
    module.exports = {
        data() {
            return {
                tableData: [],
                form: {
                    id: '',
                    name: '',
                    content: '',
                    lineCount: 0,
                    useCount: 0,
                },
                drawer: {
                    show: false,
                },
                formLabelWidth: '80px',
            };
        },
        activated() {
            this.getTableDataList()
        },
        mounted() {
        },
        methods: {
            remove(scope) {
                const _this = this;
                const id = scope.row.id;
                _this.$confirm('确认删除？').then(e => {
                    axios.delete(`/api/delMachinesById?id=${id}`).then(response => {
                        _this.getTableDataList(this.keyWords);
                        const result = response.data;
                        console.log('通过api获取到的数据:', result);
                        if (result.status !== 200) {
                            _this.$message.error(result.message);
                            return
                        }
                        _this.$message.success(result.message)
                    }).catch(function (error) {
                        _this.getTableDataList();
                        console.log('请求出现错误:', error);
                    });
                });
            },
            getTableDataList() {
                const _this = this;
                axios.get(`/api/getMachinesList/`).then(response => {
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
                    this.getDataById(scope ? scope.row.id : ' ');
                    this.drawer.show = true;
                })
            },
            getDataById(id) {
                const _this = this;
                axios.get(`/api/getMachinesById?id=${id}`).then(response => {
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
            saveForm() {
                const _this = this;
                axios.post(`/api/saveMachinesInfo`, _this.form).then(response => {
                    const result = response.data;
                    console.log('通过api获取到的数据:', result);
                    if (result.status !== 200) {
                        this.$message.error(result.message);
                        return
                    }
                    _this.$message.success(result.message);
                    _this.drawer.show = false;
                    _this.getTableDataList()
                }).catch(function (error) {
                    window.location.reload();
                    console.log('请求出现错误:', error);
                });
            },
            drawerClose() {
                this.drawer.show = false;
                this.getTableDataList();
            },
        }
    }
</script>

<style scoped>
</style>
