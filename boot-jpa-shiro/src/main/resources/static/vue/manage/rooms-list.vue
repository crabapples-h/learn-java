<template>
	<el-row>
		<el-table :data="tableData" stripe style="width: 100%" :height="400">
			<el-table-column prop="name" label="名称"></el-table-column>
			<el-table-column prop="filePath" label="设备信息">
				<template slot-scope="scope">
					<el-button type="primary" @click="showMachines(scope)" size="mini">查看详情</el-button>
				</template>
			</el-table-column>
			<el-table-column label="操作" width="200">
				<template slot-scope="scope">
					<el-button type="primary" @click="edit(scope)" size="mini">管理</el-button>
					<el-button type="danger" @click="remove(scope)" size="mini">删除</el-button>
				</template>
			</el-table-column>
		</el-table>

		<el-drawer :visible.sync="show.drawer" :wrapperClosable="false" size="80%">
			<el-form v-model="form">
				<el-form-item label="名称" :label-width="formLabelWidth">
					<el-input v-model="form.name" autocomplete="off" style="width: 30%" size="mini"></el-input>
				</el-form-item>
				<el-form-item label="站点" :label-width="formLabelWidth">
					<el-transfer v-model="form.machines" :data="machines" target-order="push"
					             :titles="['所有设备', '已有设备']" :button-texts="['移除', '添加']" style="width: 100%">
					</el-transfer>
				</el-form-item>
				<el-form-item :label-width="formLabelWidth">
					<el-button type="primary" @click="saveForm" size="mini">确定</el-button>
					<el-button @click="formClose" size="mini">取 消</el-button>
				</el-form-item>
			</el-form>
		</el-drawer>

		<el-dialog title="设备信息" :visible.sync="show.dialog" width="80%">
			<el-table :data="machinesData">
				<el-table-column property="name" label="设备名"></el-table-column>
				<el-table-column property="time" label="单次运行时长"></el-table-column>
				<el-table-column property="timeCount" label="总运行时长"></el-table-column>
				<el-table-column property="lineCount" label="总预约次数"></el-table-column>
				<el-table-column property="useCount" label="总使用次数"></el-table-column>
				<el-table-column property="updateTime" label="最后使用时间"></el-table-column>
			</el-table>
		</el-dialog>
		<div style="margin-top: 10px">
			<el-button type="primary" @click="addRooms()" size="mini">添加实训室</el-button>
		</div>
	</el-row>
</template>

<script>
    module.exports = {
        data() {
            return {
                formLabelWidth: '80px',
                tableData: [],
                machinesData: [],
                machines: [],
                machinesList: [],
                form: {
                    id: '',
                    name: '',
                    machines: [],
                    createTime: '',
                },
                show: {
                    drawer: false,
                    dialog: false
                }
            };
        },
        mounted() {
            this.getTableDataList()
        },
        methods: {
            showMachines(scope) {
                this.machinesData = scope.row.machines
                this.show.dialog = true
            },
            edit(scope) {
                this.show.drawer = true
                this.getDataById(scope.row.id)
                this.getMachinesList()
            },
            remove(scope) {
                const _this = this;
                const id = scope.row.id;
                _this.$confirm('确认删除？').then(e => {
                    axios.delete(`/api/delRoomsById?id=${id}`).then(response => {
                        _this.getTableDataList();
                        const result = response.data;
                        console.log('通过api获取到的数据:', result);
                        if (result.status !== 200) {
                            _this.$message.error(result.message);
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
                axios.get('/api/getRoomsList').then(response => {
                    const result = response.data;
                    console.log('通过api获取到的数据:', result);
                    if (result.status !== 200) {
                        this.$message.error('数据加载失败');
                        return;
                    }
                    _this.tableData = result.data;
                }).catch(function (error) {
                    console.error('请求出现错误:', error);
                });
            },
            getDataById(id) {
                const _this = this;
                axios.get(`/api/getRoomsById?id=${id}`).then(response => {
                    const result = response.data;
                    console.log('通过api获取到的数据:', result);
                    if (result.status !== 200) {
                        this.$message.error('数据加载失败');
                        return
                    }
                    _this.form = result.data;
                    _this.form.machines = result.data.machines.map(e => {
                        return e.id
                    })
                }).catch(function (error) {
                    console.log('请求出现错误:', error);
                });
            },
            addRooms() {
                const _this = this
                _this.$nextTick(() => {
                    _this.$prompt('请输入名称', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                    }).then(({value}) => {
                        _this.form.name = value
                        axios.post(`/api/saveRoomsInfo`, _this.form).then(response => {
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
            getMachinesList() {
                const _this = this;
                axios.get('/api/getMachinesList').then(response => {
                    const result = response.data;
                    console.log('通过api获取到的数据:', result);
                    if (result.status !== 200) {
                        this.$message.error('数据加载失败');
                        return;
                    }
                    _this.machinesList = result.data
                    _this.machines = result.data.map(e => {
                        return {
                            key: e.id,
                            label: e.name,
                        }
                    });
                }).catch(function (error) {
                    console.error('请求出现错误:', error);
                });
            },
            saveForm() {
                const _this = this;
                let array = []
                _this.form.machines.forEach(e => {
                    _this.machinesList.forEach(t => {
                        if (e === t.id) {
                            array.push(t)
                        }
                    })
                })
                _this.form.machines = array
                console.log(_this.form)
                axios.post('/api/saveRoomsInfo', _this.form).then(response => {
                    const result = response.data;
                    console.log('通过api获取到的数据:', result);
                    if (result.status !== 200) {
                        this.$message.error(result.message);
                        return
                    }
                    _this.$message.success(result.message);
                    _this.show.drawer = false;
                    _this.getTableDataList()
                }).catch(function (error) {
                    window.location.reload();
                    console.log('请求出现错误:', error);
                });
            },
            formClose() {
                this.show.drawer = false;
                this.getTableDataList();
            },
        }
    }
</script>

<style scoped>
</style>
