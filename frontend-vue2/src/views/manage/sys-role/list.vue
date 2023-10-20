<template>
  <div>
    <a-button @click="showAdd" v-auth:sys:roles:add>添加角色</a-button>
    <a-divider/>
    <add-role :visible="show.add" @cancel="closeForm" :is-edit="show.edit" ref="addMenu"/>
    <role-detail :visible="show.detail" @cancel="closeDetail"  :role-id="detailId"  ref="detail"/>
    <a-table :data-source="dataSource" rowKey="id" :columns="columns" :pagination="pagination">
        <span slot="action" slot-scope="text, record">
        <c-pop-button title="确定要删除吗" text="删除" type="danger" size="small" @click="remove(record)"
                      v-auth:sys:roles:del/>
        <a-divider type="vertical" v-auth:sys:roles:del/>
        <a-button type="primary" size="small" @click="showEdit(record)" v-auth:sys:roles:edit>编辑</a-button>
        <a-divider type="vertical" v-auth:sys:roles:edit/>
        <a-button type="primary" size="small" @click="showDetail(record)">查看菜单</a-button>
      </span>
    </a-table>
  </div>
</template>

<script>
import commonApi from '@/api/CommonApi'
import { buildTree } from '@/utils/ListUtils'
import { SysApis } from '@/api/Apis'
import SystemMinix from '@/minixs/SystemMinix'
import AddRole from '@/views/manage/sys-role/add.vue'
import RoleDetail from '@/views/manage/sys-role/detail.vue'

export default {
  name: 'role-list',
  mixins: [SystemMinix],
  components: { AddRole, RoleDetail },
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
          scopedSlots: { customRender: 'action' },
          width: '50%'
        },
      ],
      show: {
        add: false,
        detail: false,
        edit: false,
      },
      url: {
        list: SysApis.rolePage,
        delete: SysApis.delRoles,
      },
      detailId: ''
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
      this.show.edit = false
      this.refreshData()
      commonApi.refreshSysData()
    },
    showEdit(e) {
      this.$refs.addMenu.form = e
      this.show.add = true
      this.show.edit = true
    },
    showDetail(e) {
      this.detailId = e.id
      this.show.detail = true
    },
    closeDetail() {
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
