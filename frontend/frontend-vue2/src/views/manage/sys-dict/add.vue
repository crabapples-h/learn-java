<template>
  <a-modal :visible="visible" width="50%" ok-text="确认" cancel-text="取消" @ok="submit"
           @cancel="closeForm">
    <a-form-model :model="form" :rules="rules" :label-col="labelCol" :wrapper-col="wrapperCol" ref="ruleForm">
      <a-form-model-item label="ID" style="display: none">
        <a-input v-model="form.id" disabled placeholder="新建字典时自动生成"/>
      </a-form-model-item>
      <a-form-model-item label="字典名称" prop="name">
        <a-input v-model="form.name"/>
      </a-form-model-item>
      <a-form-model-item label="字典代码" prop="code">
        <a-input v-model="form.code"/>
      </a-form-model-item>
      <a-form-model-item label="排序" prop="sort">
        <a-input v-model="form.sort"/>
      </a-form-model-item>
    </a-form-model>
    <div class="drawer-bottom-button">
      <a-button :style="{ marginRight: '8px' }" @click="closeForm">关闭</a-button>
      <a-button type="primary" @click="submit">保存</a-button>
    </div>
  </a-modal>
</template>

<script>

import { SysApis } from '@/api/Apis'
import system from '@/mixins/system'

export default {
  name: 'dict-add',
  mixins: [system],
  props: {
    visible: {
      type: Boolean,
      default: false
    },
    close: {
      type: Function,
    },
  },
  data() {
    return {
      rules: {
        name: [
          {required: true, message: '请输入字典项值', trigger: 'change'},
          {whitespace: true, message: '请输入字典项值', trigger: 'change'}
        ],
        value: [
          {required: true, message: '请输入字典项值', trigger: 'change'},
          {whitespace: true, message: '请输入字典项值', trigger: 'change'}
        ],
        code: [
          {required: true, message: '请输入代码', trigger: 'change'},
          {whitespace: true, message: '请输入代码', trigger: 'change'}
        ],
      },
      form: {},
      url: {
        save: SysApis.saveDicts,
      }
    }
  },
  activated() {
  },
  mounted() {
  },
  methods: {
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
