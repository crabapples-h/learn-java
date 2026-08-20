<template>
  <a-modal :open="open" width="50%" ok-text="确认" cancel-text="取消" @ok="onSubmit" @cancel="closeForm">
    <a-form ref="formRef" :model="form" :rules="rules" :label-col="labelCol" :wrapper-col="wrapperCol">
      <a-form-item label="ID" style="display: none">
        <a-input v-model:value="form.id" disabled placeholder="新建时自动生成" />
      </a-form-item>
      <a-form-item label="租户名称" name="name">
        <a-input v-model:value="form.name" />
      </a-form-item>
    </a-form>
    <div class="drawer-bottom-button">
      <a-button :style="{ marginRight: '8px' }" @click="closeForm">关闭</a-button>
      <a-button type="primary" @click="onSubmit">保存</a-button>
    </div>
  </a-modal>
</template>

<script setup>
import { watch } from 'vue'
import { SysApis } from '@/api/Apis'
import { useSystem } from '@/utils/useSystem'

const props = defineProps({
  open: {
    type: Boolean,
    default: false,
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

const emit = defineEmits(['close'])

const {
  form,
  formRef,
  labelCol,
  wrapperCol,
  submit,
} = useSystem({
  save: SysApis.saveTenants,
})

const rules = {
  name: [
    { required: true, message: '请输入租户名称', trigger: 'change' },
    { whitespace: true, message: '请输入租户名称', trigger: 'change' },
  ],
}

watch(
  () => props.open,
  val => {
    if (val) {
      form.value = props.isEdit && props.editData ? { ...props.editData } : {}
    }
  },
)

const closeForm = () => {
  form.value = {}
  emit('close')
}

const onSubmit = () => submit(formRef.value, closeForm)
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
