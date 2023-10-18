<template>
  <a-modal :visible="visible" width="50%" ok-text="确认" cancel-text="取消" @ok="submit"
           @cancel="closeForm">
    <a-form-model :model="form" :rules="rules" :label-col="labelCol" :wrapper-col="wrapperCol" ref="menuForm">
      <a-form-model-item label="ID" style="display: none">
        <a-input v-model="form.id" disabled placeholder="新建时自动生成"/>
      </a-form-model-item>
      <a-form-model-item label="名称" prop="name">
        <a-input v-model="form.name"/>
      </a-form-model-item>
      <a-form-model-item label="图标" prop="icon" v-if="form.menusType === 1||form.menusType === 3">
        <a-input v-model="form.icon"/>
      </a-form-model-item>
      <a-form-model-item label="排序" prop="sort" v-if="form.menusType === 1">
        <a-input-number :min="0" :max="9999" v-model="form.sort"/>
      </a-form-model-item>
      <a-form-model-item label="类型" prop="menusType">
        <a-radio-group name="radioGroup" v-model="form.menusType">
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
      </a-form-model-item>
      <a-form-model-item label="是否隐藏" prop="showFlag" v-if="form.menusType !== 2">
        <a-radio-group name="radioGroup" v-model="form.showFlag">
          <a-radio :value="0">显示</a-radio>
          <a-radio :value="1">隐藏</a-radio>
        </a-radio-group>
      </a-form-model-item>
      <a-form-model-item label="浏览器访问路径" prop="path" v-if="form.menusType === 1">
        <a-input v-model="form.path"/>
      </a-form-model-item>
      <a-form-model-item label="文件路径" prop="filePath" v-if="form.menusType === 1">
        <a-input addon-before="@/views/" addon-after=".vue" v-model="form.filePath"/>
      </a-form-model-item>
      <a-form-model-item label="授权标识" prop="permission" v-if="form.menusType === 2">
        <a-input v-model="form.permission"/>
      </a-form-model-item>
      <a-form-model-item label="超链接地址" prop="link" v-if="form.menusType === 3">
        <a-input v-model="form.link"/>
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
import SystemMinix from '@/minixs/SystemMinix'

export default {
  name: 'menus-add',
  mixins: [SystemMinix],
  props: {
    visible: {
      type: Boolean,
      default: false
    },
    cancel: {
      type: Function,
    }
  },

  data() {
    return {
      rules: {
        menusType: [
          { required: true, message: '类型不能为空', trigger: 'change' },
        ],
        showFlag: [
          { required: true, message: '请选择是否隐藏', trigger: 'change' },
        ],
        filePath: [
          { required: true, message: '请输入文件路径', trigger: 'change' },
          { whitespace: true, message: '请输入文件路径', trigger: 'change' }
        ],
        path: [
          { required: true, message: '请输入浏览器访问路径', trigger: 'change' },
          { whitespace: true, message: '请输入浏览器访问路径', trigger: 'change' }
        ],
        link: [
          { required: true, message: '请输入超链接地址', trigger: 'change' },
          { whitespace: true, message: '请输入超链接地址', trigger: 'change' }
        ],
      },
      url: {
        save: SysApis.saveMenus,
      }
    }
  },
  activated() {
  },
  mounted() {
  },
  methods: {
    closeForm() {
      this.$emit('cancel')
    },
    submit() {
      this.$refs.menuForm.validate(valid => {
        if (valid) {
          this.$http.post(this.url.save, this.form).then(result => {
            if (result.status !== 200) {
              this.$message.error(result.message)
            }
          }).catch(function (error) {
            console.error('出现错误:', error)
          }).finally(() => {
            this.closeForm()
          })
        }
      })
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
