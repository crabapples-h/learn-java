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
<!--      <div ref="bpmn-container" ></div>-->
      <div class="bpmn-container" style="height: 500px; width: 100%" id="bpmn-container">
        <div ref="canvas" class="canvas"></div>
        <div ref="properties" class="properties-panel"></div>
      </div>
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


import BpmnModeler from 'bpmn-js/lib/Modeler'
import 'bpmn-js/dist/assets/diagram-js.css'
import 'bpmn-js/dist/assets/bpmn-font/css/bpmn.css'

// // 如果使用属性面板
// import 'bpmn-js-properties-panel/dist/assets/properties-panel.css';

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
  watch:{
    xml(newVal) {
      this.loadDiagram(newVal);
    }
  },
  activated() {
  },
  mounted() {
    this.initModeler();
    this.loadDiagram(this.xml || this.getEmptyDiagram());
  },
  methods: {
    initModeler() {
      this.bpmnModeler = new BpmnModeler({
        container: this.$refs.canvas,
        propertiesPanel: {
          parent: this.$refs.properties
        },
        // 如果需要属性面板
        additionalModules: [
          // 引入属性面板模块
        ]
      });

      // 监听变化事件
      this.bpmnModeler.on('commandStack.changed', () => {
        this.saveDiagram();
      });
    },
    getEmptyDiagram() {
      return `<?xml version="1.0" encoding="UTF-8"?>
        <bpmn2:definitions xmlns:bpmn2="http://www.omg.org/spec/BPMN/20100524/MODEL"
                          id="sample-diagram"
                          targetNamespace="http://bpmn.io/schema/bpmn">
          <bpmn2:process id="Process_1" isExecutable="false">
            <bpmn2:startEvent id="StartEvent_1"/>
          </bpmn2:process>
        </bpmn2:definitions>`;
    },
    async loadDiagram(xml) {
      try {
        await this.bpmnModeler.importXML(xml);
      } catch (err) {
        console.error('加载流程图失败:', err);
      }
    },
    async saveDiagram() {
      try {
        const { xml } = await this.bpmnModeler.saveXML({ format: true });
        this.$emit('update:xml', xml);
      } catch (err) {
        console.error('保存失败:', err);
      }
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
