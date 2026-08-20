<template>
  <div>
    <a-form layout="inline" @keyup.enter="getList">
      <a-space align="center" style="flex-wrap: wrap">
        <a-form-item label="菜单">
          <a-input placeholder="请输入菜单" v-model:value="queryParam.name" allow-clear />
        </a-form-item>
        <a-button type="default" @click="getList">查询</a-button>
        <a-button type="default" @click="resetSearch">重置</a-button>
        <a-button type="primary" ghost v-auth:sys:menus:add @click="showAdd">添加</a-button>
      </a-space>
    </a-form>
    <a-divider />
    <a-table :data-source="dataSource" row-key="id" :columns="columns" :pagination="pagination"
             :scroll="{ x: true }" bordered @expand="onExpand">
      <template #bodyCell="{ column, record, text }">
        <template v-if="column.key === 'icon'">
          <svg class="iconfont" aria-hidden="true" style="width: 10px;height: 10px">
            <use :href="`#icon-${text}`" />
          </svg>
        </template>
        <template v-else-if="column.key === 'type'">
          <a-tag size="small" color="green" v-if="record.menusType === 1">菜单</a-tag>
          <a-tag size="small" color="blue" v-if="record.menusType === 2">按钮</a-tag>
          <a-tag size="small" color="purple" v-if="record.menusType === 3">超链接</a-tag>
        </template>
        <template v-else-if="column.key === 'action'">
          <a-space align="center" style="flex-wrap: wrap">
            <c-pop-button title="确定要删除吗" text="删除" type="danger" v-auth:sys:menus:del @click="remove(record)" />
            <a-button type="primary" size="small" v-auth:sys:menus:edit @click="showEdit(record)">编辑</a-button>
            <span v-if="record.menusType === 1">
              <a-button type="primary" size="small" v-auth:sys:menus:add-children @click="showAddChild(record)">
                添加子菜单
              </a-button>
            </span>
          </a-space>
        </template>
      </template>
    </a-table>
    <add-menu :open="show.add" @close="closeAdd" />
    <add-menu :open="show.edit" @close="closeEdit" :edit-data="editData" />
  </div>
</template>

<script setup>
import { message } from 'ant-design-vue'
import request from '@/utils/request'
import { SysApis } from '@/api/Apis'
import { useSystem } from '@/utils/useSystem'
import CPopButton from '@/components/c-pop-button.vue'
import AddMenu from './add.vue'

const columns = [
  { dataIndex: 'name', title: '名称', align: 'left', width: 180 },
  { dataIndex: 'icon', title: '图标', align: 'center', width: 80 },
  { dataIndex: 'sort', title: '排序', align: 'center', width: 80 },
  { dataIndex: 'type', title: '类型', align: 'center', width: 80 },
  { dataIndex: 'permission', title: '授权标识', align: 'center' },
  { title: '操作', key: 'action', width: 250 },
]

const {
  dataSource,
  queryParam,
  pagination,
  show,
  editData,
  getQueryPage,
  getList,
  setListHandler,
  resetSearch,
  showAdd,
  closeAdd,
  showEdit,
  closeEdit,
  remove,
} = useSystem({
  list: SysApis.menuListPage,
  remove: SysApis.delMenus,
  childList: SysApis.childMenuList,
})

setListHandler(async () => {
  const page = getQueryPage()
  try {
    const result = await request.get(SysApis.menuListPage, { params: page })
    if (result.status !== 200) {
      message.error(result.message)
      return
    }
    if (result.data !== null) {
      dataSource.value = result.data.records
      pagination.total = result.data.total || result.data.totalRow
      pagination.current = result.data.current || result.data.pageNumber
      pagination.pageSize = result.data.size || result.data.pageSize
      dataSource.value.forEach(e => {
        if (!e.hasChildren) {
          delete e.children
        }
      })
    }
  } catch (error) {
    console.error('出现错误:', error)
  }
})

const onExpand = async (expanded, row) => {
  if (!expanded) {
    row.children = []
    return
  }
  try {
    const result = await request.get(SysApis.childMenuList, { params: { pid: row.id } })
    if (result.status !== 200) {
      message.error(result.message)
      return
    }
    if (result.data !== null) {
      row.children = result.data.records || result.data
      row.children.forEach(e => {
        if (!e.hasChildren) {
          delete e.children
        }
      })
      if (!row.children.length) {
        delete row.children
      }
    }
  } catch (error) {
    console.error('出现错误:', error)
  }
}

const showAddChild = e => {
  editData.value = { pid: e.id }
  show.edit = true
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
