<template>
	<a-drawer title="字典详情" width="50%" :visible="visible" @close="closeForm">
		<a-button type="primary" size="small" @click="showAddItem()">新增</a-button>
		<a-divider/>
		
		<a-table :data-source="dataSource" key="id" bordered>
			<a-table-column key="code" title="字典项代码" data-index="code"/>
			<a-table-column key="value" title="字典项值" data-index="value"/>
			<a-table-column key="id" title="操作" data-index="id">
				<template slot-scope="text, record">
					<a-button type="primary" size="small" @click="showEditItem(record)">编辑</a-button>
					<a-divider type="vertical"/>
					<c-pop-button title="确定要删除吗" text="删除" type="danger" @click="remove(record)"/>
				</template>
			</a-table-column>
		</a-table>
		
		<add-dict-item :visible="show.addItem" :dict-code="dictCode" @cancel="closeItemForm" ref="AddDictItem"/>
	</a-drawer>
</template>

<script>

import {SysApis} from '@/api/Apis'
import SystemMinix from '@/minixs/SystemMinix'
import {buildTree} from "@/utils/ListUtils";
import AddDictItem from "@/views/manage/sys-dict/add-item.vue";

export default {
	name: 'dict-add',
	components: {AddDictItem},
	mixins: [SystemMinix],
	props: {
		visible: {
			type: Boolean,
			default: false
		},
		cancel: {
			type: Function,
		},
		dictCode: {
			type: String,
		},
	},
	watch: {
		dictCode(nowValue, oldValue) {
			this.loadDetail()
		}
	},
	data() {
		return {
			rules: {
				value: [
					{required: true, message: '请输入字典项值', trigger: 'change'},
					{whitespace: true, message: '请输入字典项值', trigger: 'change'}
				],
				code: [
					{required: true, message: '请输入代码', trigger: 'change'},
					{whitespace: true, message: '请输入代码', trigger: 'change'}
				],
			},
			form: {},
			url: {
				detail: SysApis.dictItemListByCode,
				delete: SysApis.delDictItems,
			},
			show: {
				addItem: false,
				editItem: false,
			}
		}
	},
	activated() {
	},
	mounted() {
	},
	methods: {
		closeForm() {
			this.form = {}
			this.$emit('cancel')
		},
		loadDetail() {
			this.$http.get(`${this.url.detail}/${this.dictCode}`).then(result => {
				this.dataSource = result.data
			})
		},
		showAddItem() {
			this.show.addItem = true
		},
		showEditItem(e) {
			this.$refs.AddDictItem.form = e
			this.show.addItem = true
		},
		closeItemForm() {
			this.show.addItem = false
			this.loadDetail()
		},
		refreshData() {
			this.loadDetail()
		}
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
