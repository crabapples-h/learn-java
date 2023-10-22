<template>
  <a-modal title="菜单列表" :visible.sync="visible" width="30%" :footer="null" @cancel="closeDetail">
    <div style="height: 60vh;overflow-y: auto">
      <a-table :data-source="dataSource" :columns="columns" :pagination="false" v-if="false">
        <span slot="icon" slot-scope="text, record">
          <a-icon :type='text.substring(text.indexOf("\"") + 1,text.lastIndexOf("\"")) || "appstore"'/>
        </span>
        <span slot="type" slot-scope="text, record">
	        <a-tag size="small" color="green" v-if="record.menusType === 1">菜单</a-tag>
	        <a-tag size="small" color="blue" v-if="record.menusType === 2">按钮</a-tag>
	        <a-tag size="small" color="purple" v-if="record.menusType === 3">超链接</a-tag>
        </span>
      </a-table>
      <a-tree :default-expand-all="true" :check-strictly="false" :tree-data="dataSource"
              :replace-fields="replaceFields"/>
    </div>
  </a-modal>
</template>

<script>
import SystemMinix from '@/minixs/SystemMinix'
import { buildTree } from '@/utils/ListUtils'
import { SysApis } from '@/api/Apis'

export default {
  name: 'role-detail',
  mixins: [SystemMinix],
  props: {
    visible: {
      type: Boolean,
      default: false
    },
    cancel: {
      type: Function,
    },
    roleId: {
      type: String,
      default: false
    },
  },
  watch: {
    roleId(nowValue, oldValue) {
      this.loadDetail()
    }
  },
  data() {
    return {
      columns: [
        {
          dataIndex: 'name',
          title: '名称',
          key: 'id'
        },
        {
          dataIndex: 'icon',
          title: '图标',
          scopedSlots: { customRender: 'icon' },
          key: 'id'
        },
        {
          dataIndex: 'sort',
          title: '排序',
          key: 'id'

        },
        {
          dataIndex: 'type',
          title: '类型',
          scopedSlots: { customRender: 'type' },
        },
      ],
      replaceFields: {
        children: 'children', title: 'name', key: 'id'
      },
      url: {
        roleMenus: SysApis.roleMenus,
      },
    }
  },
  activated() {
  },
  mounted() {
  },
  methods: {
    loadDetail() {
      this.$http.get(`${this.url.roleMenus}/${this.roleId}`).then(result => {
        this.dataSource = buildTree(result.data, '')
      })
    },
    closeDetail() {
      this.$emit('cancel')
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
