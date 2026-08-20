<template>
  <a-modal :open="open" width="50%" ok-text="确认" cancel-text="取消" :destroy-on-close="true" @ok="onSubmit"
           @cancel="closeForm">
    <c-icon-select :open="extend" title="选择图标" @change="val => (form.icon = val)"
                   @update:open="v => (extend = v)" />
    <a-form ref="formRef" :model="form" :rules="rules" :label-col="labelCol" :wrapper-col="wrapperCol">
      <a-form-item label="ID" style="display: none">
        <a-input v-model:value="form.id" disabled placeholder="新建时自动生成" />
      </a-form-item>
      <a-form-item label="名称" name="name">
        <a-input v-model:value="form.name" />
      </a-form-item>
      <a-form-item label="图标" name="icon" v-if="form.menusType === 1 || form.menusType === 3">
        <a-space align="center">
          <a-input v-model:value="form.icon" style="display: none" />
          <svg v-if="form.icon" class="iconfont" aria-hidden="true" style="width: 50px;height: 50px">
            <use :href="`#icon-${form.icon}`" />
          </svg>
          <a-button ghost type="primary" size="small" @click="showExtend">选择图标</a-button>
        </a-space>
      </a-form-item>
      <a-form-item label="排序" name="sort" v-if="form.menusType === 1">
        <a-input-number :min="0" :max="9999" v-model:value="form.sort" />
      </a-form-item>
      <a-form-item label="类型" name="menusType">
        <a-radio-group v-model:value="form.menusType">
          <a-radio :value="1">
            <a-tag size="small" color="green">菜单</a-tag>
          </a-radio>
          <a-radio :value="2">
            <a-tag size="small" color="blue">按钮</a-tag>
          </a-radio>
          <a-radio :value="3">
            <a-tag size="small" color="purple">超链接</a-tag>
          </a-radio>
        </a-radio-group>
      </a-form-item>
      <a-form-item label="是否隐藏" name="showFlag" v-if="form.menusType !== 2">
        <a-radio-group v-model:value="form.showFlag">
          <a-radio :value="0">显示</a-radio>
          <a-radio :value="1">隐藏</a-radio>
        </a-radio-group>
      </a-form-item>
      <a-form-item label="浏览器访问路径" name="path" v-if="form.menusType === 1">
        <a-input v-model:value="form.path" />
      </a-form-item>
      <a-form-item label="文件路径" name="filePath" v-if="form.menusType === 1">
        <a-input addon-before="@/views/" addon-after=".vue" v-model:value="form.filePath" />
      </a-form-item>
      <a-form-item label="授权标识" name="permission" v-if="form.menusType === 2">
        <a-input v-model:value="form.permission" />
      </a-form-item>
      <a-form-item label="超链接地址" name="link" v-if="form.menusType === 3">
        <a-input v-model:value="form.link" />
      </a-form-item>
    </a-form>
    <div class="drawer-bottom-button">
      <a-button :style="{ marginRight: '8px' }" @click="closeForm">关闭</a-button>
      <a-button type="primary" @click="onSubmit">保存</a-button>
    </div>
  </a-modal>
</template>

<script setup>
import { ref, watch } from 'vue'
import { SysApis } from '@/api/Apis'
import { useSystem } from '@/utils/useSystem'
import CIconSelect from '@/components/c-icon-select.vue'

const props = defineProps({
  open: {
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
  save: SysApis.saveMenus,
})

const extend = ref(false)

const rules = {
  menusType: [{ required: true, message: '类型不能为空', trigger: 'change' }],
  showFlag: [{ required: true, message: '请选择是否隐藏', trigger: 'change' }],
  filePath: [
    { required: true, message: '请输入文件路径', trigger: 'change' },
    { whitespace: true, message: '请输入文件路径', trigger: 'change' },
  ],
  path: [
    { required: true, message: '请输入浏览器访问路径', trigger: 'change' },
    { whitespace: true, message: '请输入浏览器访问路径', trigger: 'change' },
    { validator: checkStartChar, message: '必须以/开头', trigger: 'change' },
  ],
  link: [
    { required: true, message: '请输入超链接地址', trigger: 'change' },
    { whitespace: true, message: '请输入超链接地址', trigger: 'change' },
  ],
}

watch(
  () => props.open,
  val => {
    if (val) {
      form.value = props.editData ? { ...props.editData } : {}
    }
  },
)

const closeForm = () => {
  form.value = {}
  emit('close')
}

const onSubmit = () => submit(formRef.value, closeForm)

const showExtend = () => {
  extend.value = true
}

function checkStartChar(rule, value, callback) {
  if (!value || value.startsWith('/')) {
    callback()
  } else {
    callback(new Error('必须以/开头'))
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
