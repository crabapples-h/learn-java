<template>
  <a-drawer title="字典详情" width="50%" :open="open" @close="closeForm">
    <a-button type="primary" size="small" @click="showAddItem()">新增</a-button>
    <a-divider />
    <a-table :data-source="dataSource" row-key="id" :columns="columns" :pagination="false" bordered>
      <template #bodyCell="{ column, record }">
        <template v-if="column.key === 'action'">
          <a-button type="primary" size="small" @click="showEditItem(record)">编辑</a-button>
          <a-divider type="vertical" />
          <c-pop-button title="确定要删除吗" text="删除" type="danger" @click="remove(record)" />
        </template>
      </template>
    </a-table>
    <add-dict-item :open="show.addItem" :dict-code="dictCode" :edit-data="addItemData"
                   @cancel="closeItemForm" />
  </a-drawer>
</template>

<script setup>
import { reactive, ref, watch } from 'vue'
import { message } from 'ant-design-vue'
import request from '@/utils/request'
import { SysApis } from '@/api/Apis'
import CPopButton from '@/components/c-pop-button.vue'
import AddDictItem from './add-item.vue'

const props = defineProps({
  open: {
    type: Boolean,
    default: false,
  },
  dictCode: {
    type: String,
    default: '',
  },
})

const emit = defineEmits(['cancel'])

const columns = [
  { key: 'text', title: '字典项文字', dataIndex: 'text' },
  { key: 'value', title: '字典项值', dataIndex: 'value' },
  { key: 'action', title: '操作', dataIndex: 'action' },
]

const dataSource = ref([])
const addItemData = ref(null)
const show = reactive({
  addItem: false,
})

watch(
  () => props.open,
  val => {
    if (val) {
      loadDetail()
    }
  },
)

const closeForm = () => {
  emit('cancel')
}

const loadDetail = async () => {
  try {
    const result = await request.get(`${SysApis.dictItemListByCode}/${props.dictCode}`)
    dataSource.value = result.data || []
  } catch (error) {
    console.error('出现错误:', error)
  }
}

const showAddItem = () => {
  addItemData.value = null
  show.addItem = true
}

const showEditItem = e => {
  addItemData.value = e
  show.addItem = true
}

const closeItemForm = () => {
  show.addItem = false
  loadDetail()
}

const refreshData = () => {
  loadDetail()
}

const remove = async e => {
  try {
    const result = await request.delete(`${SysApis.delDictItems}/${e.id}`)
    if (result.status !== 200) {
      message.error(result.message)
      return
    }
    message.success(result.message)
  } catch (error) {
    console.error('出现错误:', error)
  } finally {
    loadDetail()
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
