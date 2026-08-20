<template>
  <a-drawer :title="title" width="50%" :open="open" @close="closeForm" :destroy-on-close="true">
    <a-form ref="formRef" :model="form" :rules="rules" :label-col="labelCol" :wrapper-col="wrapperCol">
      <a-form-item label="ID" style="display: none">
        <a-input v-model:value="form.id" disabled placeholder="新建时自动生成" />
      </a-form-item>
      <a-form-item label="名称" name="name">
        <a-input v-model:value="form.name" />
      </a-form-item>
      <a-form-item label="代码" name="code">
        <a-input v-model:value="form.code" />
      </a-form-item>
      <a-form-item label="菜单">
        <a-tree
          v-model:checked-keys="form.menuList"
          :checkable="true"
          :default-expand-all="true"
          :check-strictly="false"
          :tree-data="menusOptions"
          :field-names="fieldNames"
        />
      </a-form-item>
    </a-form>
    <div class="drawer-bottom-button">
      <a-button :style="{ marginRight: '8px' }" @click="closeForm">关闭</a-button>
      <a-button type="primary" @click="submit">保存</a-button>
    </div>
  </a-drawer>
</template>

<script setup>
import { onMounted, ref, watch } from 'vue'
import { message } from 'ant-design-vue'
import request from '@/utils/request'
import { SysApis } from '@/api/Apis'
import { useSystem } from '@/utils/useSystem'

const props = defineProps({
  open: {
    type: Boolean,
    default: false,
  },
  title: {
    type: String,
    default: '',
  },
  isEdit: {
    type: Boolean,
    default: false,
  },
  editData: {
    type: Object,
    default: () => null,
  },
})

const emit = defineEmits(['cancel'])

const {
  form,
  formRef,
  labelCol,
  wrapperCol,
} = useSystem({})

const fieldNames = {
  children: 'children',
  title: 'name',
  key: 'id',
}
const rules = {
  name: [
    { required: true, message: '请输入名称', trigger: 'change' },
    { min: 2, max: 16, message: '长度为2-16个字符', trigger: 'change' },
    { whitespace: true, message: '请输入名称', trigger: 'change' },
  ],
  code: [
    { required: true, message: '请输入角色代码', trigger: 'change' },
    { min: 2, max: 16, message: '长度为2-16个字符', trigger: 'change' },
    { whitespace: true, message: '请输入代码', trigger: 'change' },
  ],
}
const menusOptions = ref([])

watch(
  () => props.open,
  val => {
    if (val) {
      form.value = props.isEdit && props.editData ? { ...props.editData } : { menuList: [] }
      if (!form.value.menuList) {
        form.value.menuList = []
      }
      if (props.isEdit) {
        loadRoleMenus()
      }
    }
  },
)

const closeForm = () => {
  form.value = { menuList: [] }
  emit('cancel')
}

const submit = async () => {
  let valid = false
  try {
    await formRef.value.validate()
    valid = true
  } catch (e) {
    console.log('校验失败:', e)
  }
  if (!valid) return
  try {
    const result = await request.post(SysApis.saveRoles, form.value)
    if (result.status !== 200) {
      message.error(result.message)
    } else {
      message.success(result.message)
    }
  } catch (error) {
    console.error('出现错误:', error)
  } finally {
    closeForm()
  }
}

const getMenusList = async () => {
  try {
    const result = await request.get(SysApis.menuList)
    if (result.status !== 200) {
      message.error(result.message)
      return
    }
    if (result.data !== null) {
      menusOptions.value = result.data
    }
  } catch (error) {
    console.error('出现错误:', error)
  }
}

const tree2list = (list, data = []) => {
  list.forEach(r => {
    data.push({ id: r.id, name: r.name, pid: r.pid, sort: r.sort, children: r.children || [] })
    tree2list(r.children || [], data)
  })
  return data
}

const loadRoleMenus = async () => {
  try {
    const result = await request.get(`${SysApis.roleMenus}/${form.value.id}`)
    const menusOption = tree2list(menusOptions.value)
    const hasMenuList = tree2list(result.data || [])
    const diffIds = []
    for (let i = hasMenuList.length - 1; i >= 0; i--) {
      for (const option of menusOption) {
        if (option.id === hasMenuList[i].id) {
          if ((option.children || []).length !== (hasMenuList[i].children || []).length) {
            diffIds.push(option.id)
          }
        }
      }
    }
    form.value.menuList = tree2list(result.data || [])
      .map(e => e.id)
      .filter(e => !diffIds.includes(e))
  } catch (error) {
    console.error('出现错误:', error)
  }
}

onMounted(() => {
  getMenusList()
})
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
