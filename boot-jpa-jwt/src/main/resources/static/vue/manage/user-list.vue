<template>
	<el-row>
		<el-table :data="tableData" stripe style="width: 100%">
			<el-table-column prop="username" label="用户名"></el-table-column>
			<el-table-column prop="name" label="姓名"></el-table-column>
			<el-table-column prop="mail" label="邮箱"></el-table-column>
			<el-table-column prop="phone" label="手机号"></el-table-column>
			<el-table-column prop="role" label="角色">
				<template slot-scope="scope">
					<el-tag size="mini" type="success" effect="plain" v-if="scope.row.role === '0'">管理员</el-tag>
					<el-tag size="mini" type="primary" effect="plain" v-if="scope.row.role === '1'">学生</el-tag>
					<el-tag size="mini" type="warning" effect="plain" v-if="scope.row.role === '2'">教师</el-tag>
				</template>
			</el-table-column>
			<el-table-column label="状态">
				<template slot-scope="scope">
					<el-tag size="mini" :key="scope.row.id" type="success" effect="plain" v-if="scope.row.status === 0">正常</el-tag>
					<el-tag size="mini" :key="scope.row.id" type="danger" effect="plain" v-else>禁用</el-tag>
				</template>
			</el-table-column>
			<el-table-column label="操作" width="350">
				<template slot-scope="scope">
					<template v-if="!scope.row.admin">
						<el-button type="danger" @click="changeStatus(scope)" size="mini" v-if="scope.row.status === 0">禁用</el-button>
						<el-button type="success" @click="changeStatus(scope)" size="mini" v-else>启用</el-button>
					</template>
					<el-button type="primary" @click="remove(scope)" size="mini" v-if="!scope.row.admin">删除</el-button>
					<el-button type="primary" @click="edit(scope)" size="mini" v-if="!scope.row.admin">编辑</el-button>
					<el-button type="primary" @click="changePassword(scope)" size="mini">修改密码</el-button>
				</template>
			</el-table-column>
		</el-table>
		<el-drawer :before-close="formClose" :visible.sync="drawer.show" :wrapperClosable="false" ref="drawer" size="70%">
			<div class="demo-drawer__content">
				<el-form v-model="form">
					<el-form-item label="用户名" :label-width="formLabelWidth">
						<el-input v-model="form.username" autocomplete="off" :disabled="isEdit"></el-input>
					</el-form-item>
					<el-form-item label="姓名" :label-width="formLabelWidth">
						<el-input v-model="form.name" autocomplete="off"></el-input>
					</el-form-item>
					<el-form-item label="邮箱" :label-width="formLabelWidth">
						<el-input v-model="form.mail" autocomplete="off"></el-input>
					</el-form-item>
					<el-form-item label="手机号" :label-width="formLabelWidth">
						<el-input v-model="form.phone" autocomplete="off" :maxlength="11"></el-input>
					</el-form-item>
					<el-form-item label="用户权限" :label-width="formLabelWidth">
						<el-radio-group v-model="form.role">
							<el-radio label="1">学生</el-radio>
							<el-radio label="2">教师</el-radio>
						</el-radio-group>
					</el-form-item>
				</el-form>
				<div class="drawer-footer">
					<el-button type="primary" @click="saveForm">确定</el-button>
					<el-button @click="formClose">取 消</el-button>
				</div>
			</div>
		</el-drawer>
		<el-button type="primary" @click="add()" size="mini">添加</el-button>
	</el-row>
</template>

<script>
    module.exports = {
        data() {
            return {
                isEdit: true,
                formLabelWidth: '80px',
                tableData: [],
                form: {
                    id: '',
                    username: '',
                    name: '',
                    mail: '',
                    phone: '',
                },
                drawer: {
                    show: false,
                },
                passwordForm: {
                    id: '',
                    password: ''
                }
            };
        },
        mounted() {
            this.getTableDataList()
        },
        methods: {
            changePassword(scope) {
                const _this = this
                _this.$prompt('请输入新密码', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                }).then(({value}) => {
                    _this.passwordForm.id = scope.row.id
                    _this.passwordForm.password = value
                    axios.post(`/manage/resetPassword`, _this.passwordForm).then(response => {
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
            },
            remove(scope) {
                const _this = this;
                const id = scope.row.id;
                _this.$confirm('确认删除？').then(e => {
                    axios.delete(`/api/removePaperById/${id}`).then(response => {
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
            changeStatus(scope) {
                const _this = this;
                const id = scope.row.id;
                _this.$confirm('确认操作？').then(e => {
                    axios.put(`/manage/changeStatus/${id}`).then(response => {
                        _this.getTableDataList();
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
                axios.get('/manage/getUserList').then(response => {
                    const result = response.data;
                    console.log('通过api获取到的数据:', result);
                    if (result.status !== 200) {
                        this.$message.error(result.message);
                        return;
                    }
                    _this.tableData = result.data;
                }).catch(function (error) {
                    console.log('请求出现错误:', error);
                });
            },
            edit(scope) {
                this.$nextTick(() => {
                    this.findDataById(scope ? scope.row.id : ' ');
                    this.drawer.show = true;
                    this.isEdit = true
                })
            },
            add() {
                this.drawer.show = true;
                this.isEdit = false;
                this.form = {}
            },
            findDataById(id) {
                const _this = this;
                axios.get(`/manage/getUsersById?id=${id}`).then(response => {
                    const result = response.data;
                    console.log('通过api获取到的数据:', result);
                    if (result.status !== 200) {
                        this.$message.error(result.message);
                        return
                    }
                    _this.form = result.data;
                }).catch(function (error) {
                    console.log('请求出现错误:', error);
                });
            },
            formClose() {
                this.drawer.show = false;
                this.getTableDataList();
            },
            saveForm() {
                const _this = this;
                let url = this.isEdit ? '/manage/saveUserInfo' : 'addUser'
                axios.post(url, _this.form).then(response => {
                    const result = response.data;
                    console.log('通过api获取到的数据:', result);
                    if (result.status !== 200) {
                        this.$message.error(result.message);
                        return
                    }
                    _this.$message.success('操作成功');
                    _this.drawer.show = false;
                    _this.getTableDataList()
                }).catch(function (error) {
                    window.location.reload();
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
