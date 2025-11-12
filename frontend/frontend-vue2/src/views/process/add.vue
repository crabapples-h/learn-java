<template>
  <a-modal :visible="visible" width="100%" height="70vh" :dialog-style="{ top: '0' }" @cancel="closeForm"
           title="流程设计">
    <c-bpmn-design v-model="form.xml" ref="cBpmnDesign"/>
    <div class="drawer-bottom-button">
      <a-space>
        <a-button @click="submit" type="primary">保存</a-button>
        <a-button @click="closeForm">关闭</a-button>
      </a-space>
    </div>
  </a-modal>
</template>

<script>
import {SysApis} from '@/api/Apis'
import system from '@/mixins/system'
import CBpmnDesign from "@comp/bpmn/c-bpnm-design.vue";


export default {
  name: 'process-add',
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
      this.$refs.cBpmnDesign.saveXml().then(res => {
        console.log(res.xml)
      })
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

/deep/ .ant-modal-body {
  padding: 0;
}
</style>
