<template>
  <div>
    <a-form layout="inline" @keyup.enter="getList">
      <a-space align="center" style="flex-wrap: wrap">
        <a-form-item label="角色">
          <a-input placeholder="请输入角色" v-model:value="queryParam.name" allow-clear />
        </a-form-item>
        <a-button type="default" @click="getList">查询</a-button>
        <a-button type="default" @click="resetSearch">重置</a-button>
        <a-button type="primary" ghost v-auth:sys:roles:add @click="showAdd">添加</a-button>
      </a-space>
    </a-form>
    <a-divider />
    <add-role :open="show.add" @cancel="closeForm" title="添加" />
    <add-role :open="show.edit" @cancel="closeForm" :is-edit="true" title="编辑" :edit-data="editData" />
    <role-detail :open="show.detail" @cancel="closeDetail" :role-id="detailId" />
    <a-table :data-source="dataSource" row-key="id" :columns="columns" :pagination="pagination" bordered>
      <template #bodyCell="{ column, record }">
        <template v-if="column.key === 'action'">
          <a-space align="center" style="flex-wrap: wrap">
            <c-pop-button title="确定要删除吗" text="删除" type="danger" size="small" v-auth:sys:roles:del
                          @click="remove(record)" />
            <a-button type="primary" size="small" v-auth:sys:roles:edit @click="showEdit(record)">编辑</a-button>
            <a-button type="primary" size="small" @click="showDetail(record)">查看菜单</a-button>
          </a-space>
        </template>
      </template>
    </a-table>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import commonApi from '@/api/CommonApi'
import { SysApis } from '@/api/Apis'
import { useSystem } from '@/utils/useSystem'
import CPopButton from '@/components/c-pop-button.vue'
import AddRole from './add.vue'
import RoleDetail from './detail.vue'

const columns = [
  { dataIndex: 'name', title: '角色', key: 'name', width: '30%' },
  { dataIndex: 'code', title: '角色代码', key: 'code', width: '30%' },
  { title: '操作', key: 'action', width: '40%' },
]

const {
  dataSource,
  queryParam,
  pagination,
  show,
  editData,
  getList,
  resetSearch,
  showAdd,
  showEdit,
  remove,
  refreshData,
} = useSystem({
  list: SysApis.rolePage,
  remove: SysApis.delRoles,
})

const detailId = ref('')

const closeForm = () => {
  show.add = false
  show.edit = false
  refreshData()
  commonApi.refreshSysData()
}

const showDetail = e => {
  detailId.value = e.id
  show.detail = true
}

const closeDetail = () => {
  show.detail = false
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
