<template>
	<el-container style=" border: 1px solid #eee">
		<el-header width="100%" class="container-header">
			<el-row>
				<el-col :span="4">
					<span style="font-size: 20px">实训室管理系统</span>
				</el-col>
				<el-col :span="3" :offset="17">
					<el-dropdown>
						<i class="el-icon-setting" style="margin-right: 15px"></i>
						<el-dropdown-menu slot="dropdown">
							<el-dropdown-item><span @click="changePassword">修改密码</span></el-dropdown-item>
							<el-dropdown-item><span @click="logout">退出登录</span></el-dropdown-item>
						</el-dropdown-menu>
					</el-dropdown>
					<span>{{userInfo.name}}</span>
				</el-col>
			</el-row>
		</el-header>

		<el-container>
			<el-aside width="200px">
				<el-menu :default-openeds="['0']">
					<template v-for="(item,index) in menus.menu">
						<template v-if="item.children">
							<el-submenu :index="`${index}`">
								<template slot="title">
									<i :class="item.icon"></i>{{item.name}}
								</template>
								<el-menu-item :index="item.id" v-for="item in item.children" @click="clickMenu(item.url)" :key="item.id">
									<i :class="item.icon"></i>{{item.name}}
								</el-menu-item>
							</el-submenu>
						</template>
						<template v-else>
							<el-menu-item :index="item.id" @click="clickMenu(item.url)">
								<i :class="item.icon"></i>
								<span slot="title">{{item.name}}</span>
							</el-menu-item>
						</template>
					</template>
				</el-menu>
			</el-aside>
			<el-main>
				<keep-alive>
					<router-view></router-view>
				</keep-alive>

			</el-main>
		</el-container>
		<el-dialog title="修改密码" :visible.sync="show.changePassword" width="40%">
			<el-form :rules="rules" ref="changePassword" :model="form" size="mini" label-position="right" label-width="80px">

				<el-form-item label="原密码" prop="password">
					<el-input type="password" style=" width:70%" v-model="form.password"></el-input>
				</el-form-item>
				<el-form-item label="新密码" prop="newPassword">
					<el-input type="password" style=" width:70%" v-model="form.newPassword"></el-input>
				</el-form-item>
				<el-form-item label="重复密码" prop="rePassword">
					<el-input type="password" style=" width:70%" v-model="form.rePassword"></el-input>
				</el-form-item>
			</el-form>
			<el-button @click="submitForm('changePassword')">提交</el-button>
		</el-dialog>
	</el-container>
</template>

<script>
    module.exports = {
        data() {
            return {
                rules: {
                    password: [
                        {required: true, message: '请输入原密码', trigger: 'blur'},
                    ],
                    newPassword: [
                        {required: true, message: '请输入新密码', trigger: 'blur'},
                    ],
                    rePassword: [
                        {
                            validator: (rule, value, callback) => {
                                if (value !== this.form.newPassword) {
                                    callback(new Error('两次密码不一致'))
                                } else {
                                    callback()
                                }
                            }, trigger: 'blur'
                        }
                    ],
                },
                form: {
                    password: ''
                },
                show: {
                    changePassword: false
                },
                userInfo: {
                    name: ''
                },
                menus: {
                    menu: [
                        {
                            id: '1',
                            name: '预约列表',
                            icon: 'el-icon-menu',
                            url: '/lineups-list'
                        },
                        {
                            id: '2',
                            name: '发起预约',
                            icon: 'el-icon-menu',
                            url: '/lineups-add'
                        },
                        {
                            id: '3',
                            name: '实训室列表',
                            icon: 'el-icon-menu',
                            url: '/rooms-list'
                        },
                        {
                            id: '4',
                            name: '设备列表',
                            icon: 'el-icon-menu',
                            url: '/machines-list'
                        },
                        {
                            id: '3-1',
                            name: '用户列表',
                            icon: 'el-icon-menu',
                            url: '/user-list'
                        }
                    ],
                    roleMenus: [
                        [
                            {
                                id: '6',
                                name: '用户管理',
                                icon: 'el-icon-menu',
                                url: '/user-list'
                            }
                        ],
                        [
                            {
                                id: '1',
                                name: '我的预约',
                                icon: 'el-icon-menu',
                                url: '/lineups-list'
                            },
                            {
                                id: '2',
                                name: '发起预约',
                                icon: 'el-icon-menu',
                                url: '/lineups-add'
                            },
                        ],
                        [
                            {
                                id: '3',
                                name: '实训室列表',
                                icon: 'el-icon-menu',
                                url: '/rooms-list'
                            },
                            {
                                id: '4',
                                name: '设备列表',
                                icon: 'el-icon-menu',
                                url: '/machines-list'
                            },
                            {
                                id: '5',
                                name: '预约列表',
                                icon: 'el-icon-menu',
                                url: '/lineups-list-teacher'
                            },
                        ],
                    ],
                    demoMenus: [
                        {
                            id: 'demo',
                            name: '测试菜单',
                            icon: 'el-icon-menu',
                            children: [
                                {
                                    id: 'demo-1',
                                    name: '表格列表(富文本)',
                                    icon: 'el-icon-menu',
                                    url: '/demo/demo1'
                                },
                                {
                                    id: 'demo-2',
                                    name: '表格列表(文本域)',
                                    icon: 'el-icon-menu',
                                    url: '/demo/demo2'
                                },
                                {
                                    id: 'demo-3',
                                    name: '照片墙',
                                    icon: 'el-icon-menu',
                                    url: '/demo/demo3'
                                },
                                {
                                    id: 'demo-4',
                                    name: '富文本页面',
                                    icon: 'el-icon-menu',
                                    url: '/demo/demo4'
                                },
                                {
                                    id: 'demo-5',
                                    name: '表格列表(带图片)',
                                    icon: 'el-icon-menu',
                                    url: '/demo/demo5'
                                }
                            ]
                        },
                    ],
                },

            };
        },
        mounted() {
            this.getUserInfo()
            this.$router.push({name: 'welcome'})
        },
        methods: {
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        axios.post('/manage/changePassword', this.form).then(response => {
                            let result = response.data
                            if (result.status !== 200) {
                                this.$message.error(result.message);
                                return;
                            }
                            this.$message.success(result.message);
                            this.form = {}
                            this.show.changePassword = false
                        }).catch(function (error) {
                            console.error('出现错误:', error);
                        });
                    } else {
                        console.log('验证失败');
                        return false;
                    }
                });
            },
            changePassword() {
                this.show.changePassword = true
            },
            logout() {
                window.location.href = '/logout'
            },
            clickMenu(url) {
                router.push(url)
                console.log(url)
            },
            getUserInfo() {
                axios.get('/getUserInfo').then(response => {
                    let result = response.data
                    if (result.status !== 200) {
                        if (result.status === 401) {
                            window.location.href = '/login'
                        }
                        this.$message.error(result.message);
                        return;
                    }
                    if (result.data !== null) {
                        this.userInfo = result.data;
                        this.menus.menu = this.menus.roleMenus[this.userInfo.role]
                    }
                }).catch(function (error) {
                    console.error('出现错误:', error);
                });
            }
        }
    }
</script>

<style>
	.container-header {
		font-size: 12px;
		background-color: #97baea;
		color: #333;
		line-height: 60px;
	}

	.el-aside {
		background-color: rgb(238, 241, 246);
		color: #333;
	}

	.parent-body {
		padding: 16px;
	}
</style>
