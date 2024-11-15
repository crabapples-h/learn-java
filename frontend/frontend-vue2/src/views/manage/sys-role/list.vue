<template>
  <div>
    <a-form layout="inline" @keyup.enter.native="getList">
      <a-space align="center" style="flex-wrap: wrap">
        <a-form-item label="角色">
          <a-input placeholder="请输入角色" v-model="queryParam.name" :allow-clear="true"/>
        </a-form-item>
        <a-button type="default" @click="getList" icon="search">查询</a-button>
        <a-button type="default" @click="resetSearch" icon="reload">重置</a-button>
        <a-button type="primary" @click="showAdd" icon="plus" v-auth:sys:roles:add ghost>添加</a-button>
      </a-space>
    </a-form>
    <a-divider/>
    <add-role :visible="show.add" @cancel="closeForm" title="添加"/>
    <add-role :visible="show.edit" @cancel="closeForm" :is-edit="show.edit" ref="editForm" title="编辑"/>
    <role-detail :visible="show.detail" @cancel="closeDetail" :role-id="detailId" ref="detail"/>
    <a-table :data-source="dataSource" rowKey="id" :columns="columns" :pagination="pagination" bordered>
        <span slot="action" slot-scope="text, record">
      <a-space align="center" style="flex-wrap: wrap">
        <c-pop-button title="确定要删除吗" text="删除" type="danger" size="small" @click="remove(record)"
                      v-auth:sys:roles:del/>
        <a-button type="primary" size="small" @click="showEdit(record)" v-auth:sys:roles:edit>编辑</a-button>
        <a-button type="primary" size="small" @click="showDetail(record)">查看菜单</a-button>
        </a-space>
      </span>
    </a-table>
  </div>
</template>

<script>
import commonApi from '@/api/CommonApi'
import { SysApis } from '@/api/Apis'
import system from '@/mixins/system'
import AddRole from '@/views/manage/sys-role/add.vue'
import RoleDetail from '@/views/manage/sys-role/detail.vue'

export default {
  name: 'role-list',
  mixins: [system],
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
        edit: false,
      },
      url: {
        list: SysApis.rolePage,
        remove: SysApis.delRoles,
      },
      detailId: '',
      title: '标题',
    }
  },
  activated() {
  },
  mounted() {
  },
  methods: {

    closeForm() {
      this.show.add = false
      this.show.edit = false
      this.refreshData()
      commonApi.refreshSysData()
    },
    showDetail(e) {
      this.detailId = e.id
      this.show.detail = true
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
