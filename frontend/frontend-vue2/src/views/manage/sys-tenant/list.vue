<template>
  <div>
    <a-form layout="inline" @keyup.enter.native="getList">
      <a-space align="center" style="flex-wrap: wrap">
        <a-form-item label="名称">
          <a-input placeholder="请输入名称" v-model="queryParam.name" :allow-clear="true"/>
        </a-form-item>
        <a-button type="default" @click="getList" icon="search">查询</a-button>
        <a-button type="default" @click="resetSearch" icon="reload">重置</a-button>
        <a-button type="primary" @click="showAdd" icon="plus" v-auth:sys:tenant:add ghost>添加</a-button>
      </a-space>
    </a-form>
    <a-divider/>
    <add-tenant :visible="show.add" @close="closeAdd"/>
    <add-tenant :visible="show.edit" @close="closeEdit" :is-edit="true" ref="editForm"/>
    <a-table :data-source="dataSource" rowKey="id" :columns="columns" :pagination="pagination"
             bordered>
      <span slot="action" slot-scope="text, record">
      <a-space align="center" style="flex-wrap: wrap">
        <c-pop-button title="确定要删除吗" text="删除" type="danger" @click="remove(record)" v-auth:sys:tenant:del/>
        <a-button type="primary" size="small" @click="showEdit(record)" v-auth:sys:tenant:edit>编辑</a-button>
          </a-space>
      </span>
    </a-table>
  </div>
</template>

<script>

import { SysApis } from '@/api/Apis'
import system from '@/mixins/system'
import AddTenant from '@/views/manage/sys-tenant/add.vue'

export default {
  name: 'tenant-list',
  mixins: [system],
  components: {
    AddTenant,
  },
  data() {
    return {
      columns: [
        {
          dataIndex: 'id',
          title: 'id',
        },
        {
          dataIndex: 'name',
          title: '名称',
        },
        {
          dataIndex: 'action',
          title: '操作',
          scopedSlots: {customRender: 'action'},
        },
      ],
      url: {
        list: SysApis.tenantPage,
        remove: SysApis.delTenants,
      },
      dictCode: '',
    }
  },
  activated() {
  },
  mounted() {
  },
  methods: {
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
