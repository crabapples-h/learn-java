<template>
  <a-modal :visible="visible" width="50%" ok-text="确认" cancel-text="取消" @ok="submit" @cancel="closeForm"
           :destroy-on-close="true">
    <c-icon-select :visible="show.extend" @closeExtend="closeExtend" title="选择图标" v-model="form.icon"/>
    <a-form-model :model="form" :rules="rules" :label-col="labelCol" :wrapper-col="wrapperCol" ref="ruleForm">
      <a-form-model-item label="ID" style="display: none">
        <a-input v-model="form.id" disabled placeholder="新建时自动生成"/>
      </a-form-model-item>
      <a-form-model-item label="名称" prop="name">
        <a-input v-model="form.name"/>
      </a-form-model-item>
      <a-form-model-item label="图标" prop="icon" v-if="form.menusType === 1||form.menusType === 3">
        <a-space align="center">
          <a-input v-model="form.icon" style="display: none"/>
          <svg class="iconfont" aria-hidden="true" style="width: 50px;height: 50px" v-if="form.icon">
            <use :xlink:href="'#icon-'+form.icon"></use>
          </svg>
          <a-button @click="showExtend" ghost type="primary" size="small">选择图标</a-button>
        </a-space>
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
import system from '@/mixins/system'
import CIconSelect from '@comp/c-icon-select.vue'

export default {
  name: 'menus-add',
  components: {CIconSelect},
  mixins: [system],
  props: {
    visible: {
      type: Boolean,
      default: false
    },
    close: {
      type: Function,
    }
  },

  data() {
    return {
      rules: {
        menusType: [
          {required: true, message: '类型不能为空', trigger: 'change'},
        ],
        showFlag: [
          {required: true, message: '请选择是否隐藏', trigger: 'change'},
        ],
        filePath: [
          {required: true, message: '请输入文件路径', trigger: 'change'},
          {whitespace: true, message: '请输入文件路径', trigger: 'change'}
        ],
        path: [
          {required: true, message: '请输入浏览器访问路径', trigger: 'change'},
          {whitespace: true, message: '请输入浏览器访问路径', trigger: 'change'},
          {validator: this.checkStartChar, message: '必须以/开头', trigger: 'change'},
        ],
        link: [
          {required: true, message: '请输入超链接地址', trigger: 'change'},
          {whitespace: true, message: '请输入超链接地址', trigger: 'change'}
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
      this.form = {}
      this.show.add = false
      this.show.edit = false
      this.$emit('close')
    },
    checkStartChar(rule, value, callback) {
      if (!value.startsWith("/")) {
        callback(new Error('必须以/开头'))
        return
      }
      callback()
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
