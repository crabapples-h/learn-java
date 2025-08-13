<template>
  <div>
    <a-button @click="showDialog"> 点击显示</a-button>
    <a-modal v-model="visible" width="100%" height="70vh" :dialog-style="{ top: '0' }" @cancel="close">
      <c-bpmn-design v-model="xml" ></c-bpmn-design>
    </a-modal>
  </div>

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
      xml: '',
    }
  },

  activated() {
  },
  mounted() {
  },

  methods: {

    showDialog() {
      this.aa = true
      this.$nextTick(() => {
        // this.initBpmn();
      })

    },

    closeForm() {
      this.form = {}
      this.show.add = false
      this.show.edit = false
      this.$emit('close')
    },
  }
}
</script>

<style scoped lang="less">

</style>
