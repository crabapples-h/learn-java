<template>
  <div>
    <a-form layout="inline" @keyup.enter.native="getList">
      <a-space align="center" style="flex-wrap: wrap">
        <a-form-item label="名称">
          <a-input placeholder="请输入流程名称" v-model="queryParam.name" :allow-clear="true"/>
        </a-form-item>
        <a-button type="default" @click="getList" icon="search">查询</a-button>
        <a-button type="default" @click="resetSearch" icon="reload">重置</a-button>
        <a-button type="primary" @click="showAdd" icon="plus" v-auth:sys:process:add ghost>添加</a-button>
      </a-space>
    </a-form>
    <a-divider/>
    <add :visible="show.add" @close="closeAdd"/>
    <add :visible="show.edit" @close="closeEdit" :is-edit="true" ref="editForm"/>
    <a-table :data-source="dataSource" rowKey="id" :columns="columns" :pagination="pagination"
             bordered>
      <span slot="action" slot-scope="text, record">
      <a-space align="center" style="flex-wrap: wrap">
        <c-pop-button title="确定要删除吗" text="删除" type="danger" @click="remove(record)" v-auth:sys:process:del/>
        <a-button type="primary" size="small" @click="showEdit(record)" v-auth:sys:process:edit>编辑</a-button>
        <a-button type="primary" size="small" @click="showDetail(record)">查看流程图</a-button>
      </a-space>
      </span>
    </a-table>
  </div>
</template>

<script>

import {SysApis} from '@/api/Apis'
import system from '@/mixins/system'
import Add from './add.vue'

export default {
  name: 'process-list',
  mixins: [system],
  components: {
    Add,
  },
  data() {
    return {
      columns: [
        {
          dataIndex: 'key',
          title: '流程Key',
        },
        {
          dataIndex: 'name',
          title: '流程名称',
        },
        {
          dataIndex: 'code',
          title: '版本',
        },
        {
          dataIndex: 'status',
          title: '状态',
        },
        {
          dataIndex: 'createTime',
          title: '创建时间',
        },
        {
          dataIndex: 'action',
          title: '操作',
          scopedSlots: {customRender: 'action'},
        },
      ],
      url: {
        list: SysApis.dictPage,
        remove: SysApis.delDicts,
      },
      dictCode: '',
      show: {
        add: true
      }
    }
  },
  activated() {
  },
  mounted() {
  },
  methods: {}
}
</script>

<style scoped>
</style>
