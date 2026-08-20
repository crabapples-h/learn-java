<template>
  <a-modal :open="open" title="菜单列表" width="30%" :footer="null" :destroy-on-close="true"
           @cancel="closeDetail">
    <div style="height: 60vh;overflow-y: auto">
      <a-tree
        :default-expand-all="true"
        :check-strictly="false"
        :tree-data="dataSource"
        :field-names="fieldNames"
        :show-line="true"
        :show-icon="false"
      />
    </div>
  </a-modal>
</template>

<script setup>
import { ref, watch } from 'vue'
import request from '@/utils/request'
import { SysApis } from '@/api/Apis'

const props = defineProps({
  open: {
    type: Boolean,
    default: false,
  },
  roleId: {
    type: String,
    default: '',
  },
})

const emit = defineEmits(['cancel'])

const dataSource = ref([])
const fieldNames = {
  children: 'children',
  title: 'name',
  key: 'id',
}

watch(
  () => props.open,
  val => {
    if (val) {
      loadDetail()
    }
  },
)

const loadDetail = async () => {
  try {
    const result = await request.get(`${SysApis.roleMenusTree}/${props.roleId}`)
    dataSource.value = (result.data || []).sort((a, b) => a.sort - b.sort)
  } catch (error) {
    console.error('出现错误:', error)
  }
}

const closeDetail = () => {
  emit('cancel')
}
</script>

<style scoped>
</style>
