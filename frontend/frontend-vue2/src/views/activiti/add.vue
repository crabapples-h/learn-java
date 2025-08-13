<template>
  <a-modal :visible="visible" width="50%" ok-text="确认" cancel-text="取消" @ok="submit" @cancel="closeForm"
           :destroy-on-close="true">
    <a-form-model :model="form" :rules="rules" :label-col="labelCol" :wrapper-col="wrapperCol" ref="ruleForm">
      <a-form-model-item label="ID" style="display: none">
        <a-input v-model="form.id" disabled placeholder="新建时自动生成"/>
      </a-form-model-item>
      <a-form-model-item label="名称" prop="name">
        <a-input v-model="form.name"/>
      </a-form-model-item>
      <div ref="bpmn-container" style="height: 500px; width: 100%" id="bpmn-container"></div>

    </a-form-model>
    <div class="drawer-bottom-button">
      <a-button :style="{ marginRight: '8px' }" @click="closeForm">关闭</a-button>
      <a-button type="primary" @click="submit">保存</a-button>
    </div>
  </a-modal>
</template>

<script>

import {SysApis} from '@/api/Apis'
import system from '@/mixins/system'
import CIconSelect from '@comp/c-icon-select.vue'
import BpmnViewer from 'bpmn-js';

// import BpmnModeler from 'bpmn-js/lib/Modeler';

// import resizeAllModule from 'bpmn-js-nyan/lib/resize-all-rules';
// import colorPickerModule from 'bpmn-js-nyan/lib/color-picker';
// import nyanDrawModule from 'bpmn-js-nyan/lib/nyan/draw';
// import nyanPaletteModule from 'bpmn-js-nyan/lib/nyan/palette';


// var bpmnJS = new BpmnModeler({
//   additionalModules: [
//     // resizeAllModule,
//     // colorPickerModule,
//     // nyanDrawModule,
//     // nyanPaletteModule
//   ]
// });
export default {
  name: 'activiti-add',
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
      },
      url: {
        save: SysApis.saveMenus,
      }
    }
  },
  activated() {
  },
  mounted() {
    this.initBpmnJS()
  },
  methods: {
    async initBpmnJS() {
      // let container = this.$refs['bpmn-container']
      // var viewer = new BpmnViewer({
      //   container: '#bpmn-container'
      // });

      // viewer.importXML(pizzaDiagram).then(function (result) {
      //   const {warnings} = result;
      //   console.log('success !', warnings);
      //   viewer.get('canvas').zoom('fit-viewport');
      // }).catch(function (err) {
      //   const {warnings, message} = err;
      //   console.log('something went wrong:', warnings, message);
      // });

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
