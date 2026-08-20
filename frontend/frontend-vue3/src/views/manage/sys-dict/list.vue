<template>
  <div>
    <a-form layout="inline" @keyup.enter="getList">
      <a-space align="center" style="flex-wrap: wrap">
        <a-form-item label="名称">
          <a-input placeholder="请输入字典名称" v-model:value="queryParam.name" allow-clear />
        </a-form-item>
        <a-button type="default" @click="getList">查询</a-button>
        <a-button type="default" @click="resetSearch">重置</a-button>
        <a-button type="primary" ghost v-auth:sys:menus:add @click="showAdd">添加</a-button>
      </a-space>
    </a-form>
    <a-divider />
    <dict-detail :open="show.detail" :dict-code="dictCode" @cancel="closeDetail" />
    <add-dict :open="show.add" @close="closeAdd" />
    <add-dict :open="show.edit" @close="closeEdit" :is-edit="true" :edit-data="editData" />
    <add-dict-item :open="show.addItem" :dict-code="dictCode" @cancel="closeItemForm" />
    <a-table :data-source="dataSource" row-key="id" :columns="columns" :pagination="pagination" bordered>
      <template #bodyCell="{ column, record }">
        <template v-if="column.key === 'action'">
          <a-space align="center" style="flex-wrap: wrap">
            <c-pop-button title="确定要删除吗" text="删除" type="danger" @click="remove(record)" />
            <a-button type="primary" size="small" @click="showEdit(record)">编辑</a-button>
            <a-button type="primary" size="small" @click="showAddItem(record)">添加字典项</a-button>
            <a-button type="primary" size="small" @click="showDetail(record)">查看字典项</a-button>
          </a-space>
        </template>
      </template>
    </a-table>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { SysApis } from '@/api/Apis'
import { useSystem } from '@/utils/useSystem'
import CPopButton from '@/components/c-pop-button.vue'
import AddDict from './add.vue'
import AddDictItem from './add-item.vue'
import DictDetail from './detail.vue'

const columns = [
  { dataIndex: 'name', title: '名称' },
  { dataIndex: 'code', title: '代码' },
  { dataIndex: 'action', title: '操作' },
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
  closeAdd,
  showEdit,
  closeEdit,
  remove,
  refreshData,
} = useSystem({
  list: SysApis.dictPage,
  remove: SysApis.delDicts,
})

const dictCode = ref('')

const showAddItem = e => {
  dictCode.value = e.code
  show.addItem = true
}

const closeItemForm = () => {
  dictCode.value = null
  show.addItem = false
  refreshData()
}

const showDetail = e => {
  dictCode.value = e.code
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
