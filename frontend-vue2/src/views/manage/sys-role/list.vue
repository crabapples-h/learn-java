<template>
	<div>
		<a-button @click="showAdd" v-auth:sys:roles:add>添加角色</a-button>
		<a-divider/>
		<add-role :visible="show.add" @cancel="closeForm" ref="addMenu"/>
		<role-detail :visible="show.detail" @cancel="closeShowDetail" ref="detail"/>
		<a-table :data-source="dataSource" rowKey="id" :columns="columns" :pagination="pagination">
        <span slot="action" slot-scope="text, record">
        <c-pop-button title="确定要删除吗" text="删除" type="danger" size="small" @click="remove(record)"
                      v-auth:sys:roles:del/>
        <a-divider type="vertical" v-auth:sys:roles:del/>
        <a-button type="primary" size="small" @click="edit(record)" v-auth:sys:roles:edit>编辑</a-button>
        <a-divider type="vertical" v-auth:sys:roles:edit/>
        <a-button type="primary" size="small" @click="showDetail(record)">查看菜单</a-button>
      </span>
		</a-table>
	</div>
</template>

<script>
import commonApi from '@/api/CommonApi'
import {buildTree} from '@/utils/ListUtils'
import {SysApis} from '@/api/Apis'
import SystemMinix from '@/minixs/SystemMinix'
import AddRole from '@/views/manage/sys-role/add.vue'
import RoleDetail from '@/views/manage/sys-role/detail.vue'

export default {
	name: 'roles-list',
	mixins: [SystemMinix],
	components: {AddRole, RoleDetail},
	data() {
		return {
			columns: [
				{
					dataIndex: 'name',
					title: '角色',
					key: 'name',
					width: '50%'
				},
				{
					title: '操作',
					key: 'action',
					scopedSlots: {customRender: 'action'},
					width: '50%'
				},
			],
			show: {
				add: false,
				detail: false,
			},
			url: {
				list: SysApis.rolePage,
				delete: SysApis.delRoles,
				roleMenus: SysApis.roleMenus,
			},
			allMenuList: []
		}
	},
	activated() {
	},
	mounted() {
	},
	methods: {
		showAdd() {
			this.show.add = true
		},
		closeForm() {
			this.show.add = false
			this.refreshData()
			commonApi.refreshSysData()
		},
		edit(e) {
			// this.getMenusList()
			this.$refs.addMenu.form.id = e.id
			this.$refs.addMenu.form.name = e.name
			this.$http.get(`${this.url.roleMenus}/${e.id}`).then(result => {
				let hasMenuList = result.data.map(e => {
					return {id: e.id, name: e.name, pid: e.pid, sort: e.sort}
				})
				/*
				* 新增子菜单后，需要将其父级菜单设置为未选择状态
* 1.首先从两个数组中筛选出不一样的元素，这些元素就是没有权限的菜单
* 2.记录下没有权限的菜单的pid
*/
				let differentMenuList = this.allMenuList.filter(e => {
					return hasMenuList.every(r => {
						return e.id !== r.id
					})
				}).map(e => e.pid)
				/*
 * 3.如果角色拥有的菜单中包含了没有权限的菜单则将其过滤掉
 */
				this.$refs.addMenu.form.menusList = result.data.filter(e => {
					return !differentMenuList.includes(e.id)
				}).map(e => e.id)
			})
			this.show.add = true
		},
		
		showDetail(e) {
			this.$http.get(`${this.url.roleMenus}/${e.id}`).then(result => {
				console.log(result.data)
				this.$refs.detail.dataSource = buildTree(result.data, '')
			}).finally(() => {
				this.show.detail = true
			})
		},
		closeShowDetail() {
			this.show.detail = false
		},
		
	}
}
</script>

<style scoped>
.drawer-bottom-button {
	position: absolute;
	right: 0;
	bottom: 0;
	width: 100%;
	border-top: 1px solid #e9e9e9;
	padding: 10px 16px;
	background: #fff;
	text-align: right;
	z-index: 1;
}
</style>
