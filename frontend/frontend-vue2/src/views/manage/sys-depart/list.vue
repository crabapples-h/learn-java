<template>
  <div>
    <a-form layout="inline" @keyup.enter.native="getList">
      <a-space align="center" style="flex-wrap: wrap">
        <a-form-item label="名称">
          <a-input placeholder="请输入名称" v-model="queryParam.name" :allow-clear="true"/>
        </a-form-item>
        <a-button type="default" @click="getList" icon="search">查询</a-button>
        <a-button type="default" @click="resetSearch" icon="reload">重置</a-button>
        <a-button type="primary" @click="showAdd" icon="plus" v-auth:sys:menus:add ghost>添加</a-button>
      </a-space>
    </a-form>
    <a-divider/>
    <depart-detail :visible="show.detail" :dict-code="dictCode" @cancel="closeDetail"/>
    <add-depart :visible="show.add" @close="closeAdd"/>
    <add-depart :visible="show.edit" @close="closeEdit" :is-edit="true" ref="editForm"/>
<!--    <add-dict-item :visible="show.addItem" :dict-code="dictCode" @cancel="closeItemForm"/>-->
    <a-table :data-source="dataSource" rowKey="id" :columns="columns" :pagination="pagination"
             bordered>
      <span slot="action" slot-scope="text, record">
      <a-space align="center" style="flex-wrap: wrap">
        <c-pop-button title="确定要删除吗" text="删除" type="danger" @click="remove(record)"/>
        <a-button type="primary" size="small" @click="showEdit(record)">编辑</a-button>
        <a-button type="primary" size="small" @click="showAddItem(record)">添加</a-button>
        <a-button type="primary" size="small" @click="showDetail(record)">查看</a-button>
          </a-space>
      </span>
    </a-table>
  </div>
</template>

<script>

import { SysApis } from '@/api/Apis'
import system from '@/mixins/system'
import AddDepart from '@/views/manage/sys-depart/add.vue'
import DepartDetail from '@/views/manage/sys-depart/detail.vue'

export default {
  name: 'depart-list',
  mixins: [system],
  components: {
    AddDepart,
    DepartDetail
  },
  data() {
    return {
      columns: [
        {
          dataIndex: 'name',
          title: '名称',
        },
        {
          dataIndex: 'code',
          title: '代码',
        },
        {
          dataIndex: 'action',
          title: '操作',
          scopedSlots: {customRender: 'action'},
        },
      ],
      url: {
        list: SysApis.departPage,
        remove: SysApis.delDeparts ,
      },
      dictCode: '',
    }
  },
  activated() {
  },
  mounted() {
  },
  methods: {
    showAddItem(e) {
      this.dictCode = e.id
      this.show.addItem = true
    },
    closeItemForm() {
      this.show.addItem = false
      this.refreshData()
    },
    showDetail(e) {
      this.dictCode = e.code
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
