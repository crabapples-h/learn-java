<template>
  <a-modal :visible="visible" width="100%" height="70vh" :dialog-style="{ top: '0' }" @cancel="closeForm">
    <c-bpmn-design v-model="form.xml"></c-bpmn-design>
    <div class="drawer-bottom-button">
      <a-button :style="{ marginRight: '8px' }" @click="closeForm">关闭</a-button>
      <a-button type="primary" @click="submit">保存</a-button>
    </div>
  </a-modal>
</template>

<script>
import {SysApis} from '@/api/Apis'
import system from '@/mixins/system'
import CBpmnDesign from "@comp/c-bpnm-design.vue";


export default {
  name: 'activiti-add',
  components: {CBpmnDesign},
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
      },
      url: {
        save: SysApis.saveMenus,
      },
      form: {
        xml: ''
      }
    }
  },

  activated() {
  },
  mounted() {
  },

  methods: {
    submit() {
      console.log(this.form.xml)
    },
  }
}
</script>

<style scoped lang="less">
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
