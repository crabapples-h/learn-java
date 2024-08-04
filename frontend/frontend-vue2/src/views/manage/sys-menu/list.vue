<template>
	<div>
		<a-button @click="showAdd()" v-auth:sys:menus:add>添加菜单</a-button>
		<a-divider/>
		<a-table :data-source="dataSource" key="id" :columns="columns" :pagination="pagination">
      <span slot="action" slot-scope="text, record">
        <c-pop-button title="确定要删除吗" text="删除" type="danger" @click="remove(record)" v-auth:sys:menus:del/>
        <a-divider type="vertical"/>
        <a-button type="primary" size="small" @click="showEdit(record)" v-auth:sys:menus:edit>编辑</a-button>
        <span v-if="record.menusType === 1">
          <a-divider type="vertical"/>
          <a-button type="primary" size="small" @click="showAddChild(record)"
                    v-auth:sys:menus:add-children>添加子菜单</a-button>
        </span>
      </span>
			<span slot="icon" slot-scope="text, record">
        <a-icon :type='text.substring(text.indexOf("\"") + 1,text.lastIndexOf("\"")) || "appstore"'/>
      </span>
			<span slot="type" slot-scope="text, record">
        <a-tag size="small" color="green" v-if="record.menusType === 1">菜单</a-tag>
        <a-tag size="small" color="blue" v-if="record.menusType === 2">按钮</a-tag>
        <a-tag size="small" color="purple" v-if="record.menusType === 3">超链接</a-tag>
      </span>
		</a-table>
		<add-menu :visible="show.add" @cancel="closeForm" ref="addMenu"/>
	</div>
</template>

<script>

import commonApi from '@/api/CommonApi'
import { SysApis } from '@/api/Apis'
import SystemMinix from '@/minixs/SystemMinix'
import AddMenu from '@/views/manage/sys-menu/add.vue'

export default {
	name: 'menu-list',
	mixins: [SystemMinix],
	components: {
		AddMenu
	},
	data() {
		return {
			columns: [
				{
					dataIndex: 'name',
					title: '名称',
				},
				{
					dataIndex: 'icon',
					title: '图标',
					scopedSlots: {customRender: 'icon'},
				},
				{
					dataIndex: 'sort',
					title: '排序',
				},
				{
					dataIndex: 'type',
					title: '类型',
					scopedSlots: {customRender: 'type'},
				},
				{
					dataIndex: 'permission',
					title: '授权标识',
				},
				{
					dataIndex: 'action',
					title: '操作',
					scopedSlots: {customRender: 'action'},
				},
			],
			dataSource: [],
			show: {
				add: false,
			},
			url: {
				list: SysApis.menuPage,
				delete: SysApis.delMenus,
			}
		}
	},
	activated() {
	},
	mounted() {
	},
	methods: {
		getList() {
			let page = this.getQueryPage()
			this.$http.get(this.url.list, {params: page}).then(result => {
				if (result.status !== 200) {
					this.$message.error(result.message)
					return
				}
				if (result.data !== null) {
					let format = function (data) {
						return data.map(e => {
							let menus = {
								id: e.id,
								key: e.id,
								name: e.name,
								icon: e.icon,
								url: e.path,
								sort: e.sort,
								menusType: e.menusType,
								path: e.path,
								link: e.link,
								filePath: e.filePath,
								permission: e.permission,
								showFlag: e.showFlag,
							}
							if (e.children && e.children.length > 0) {
								menus.children = format(e.children)
							}
							return menus
						}).sort((a, b) => {
							return a.sort - b.sort
						})
					}
					this.dataSource = format(result.data.records)
					this.pagination.total = result.data.total
					this.pagination.current = result.data.current
					this.pagination.pageSize = result.data.size
				}
			}).catch(function (error) {
				console.error('出现错误:', error)
			})
		},
		showAdd() {
			this.show.add = true
		},
		showAddChild(e) {
			this.$refs.addMenu.form.pid = e.id
			this.show.add = true
		},
		showEdit(e) {
			this.$refs.addMenu.form = e
			this.show.add = true
		},
		closeForm() {
			this.show.add = false
			this.refreshData()
			commonApi.refreshSysData()
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
