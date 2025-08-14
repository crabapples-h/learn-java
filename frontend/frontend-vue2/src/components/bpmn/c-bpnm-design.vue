<template>
  <div>
    <!-- BPMN 建模器容器 -->
    <div class="bpmn-canvas-container">
      <div class="bpmn-properties-panel">
        <a-form-model :model="form" :label-col="{span: 6}" :wrapper-col="{span: 17}">
          <template>
            <a-form-model-item label="流程名称">
              <a-input v-model="form.processName"/>
            </a-form-model-item>
            <a-form-model-item label="流程ID">
              <a-input v-model="form.processId"/>
            </a-form-model-item>
          </template>
          <template v-if="show.isElement">
            <a-divider/>
            <a-form-model-item label="元素名称">
              <a-input v-model="form.elementName"/>
            </a-form-model-item>
            <a-form-model-item label="元素ID">
              <a-input v-model="form.elementId"/>
            </a-form-model-item>
            <a-form-model-item label="执行人">
            <a-input v-model="form.assignee"/>
          </a-form-model-item>
          </template>
          <a-divider/>
          <a-space>
            <a-button @click="handleOpenFile">打开文件</a-button>
            <a-button @click="handleDownloadXML">下载 BPMN</a-button>
            <a-button @click="handleDownloadSVG">下载 SVG</a-button>
          </a-space>
        </a-form-model>
      </div>
      <div ref="canvas" class="bpmn-canvas"></div>
      <div ref="propertiesPanel" class="bpmn-properties-panel"></div>

    </div>
    <!-- 隐藏的文件输入框 -->
    <input type="file" ref="fileInput" style="display: none" @change="importBPMN" accept=".bpmn, .xml"/>
  </div>
</template>
<script>
import customTranslateModule, {initialDiagramXML} from './c-bpmn-plugins';
import system from "@/mixins/system";
import 'bpmn-js/dist/assets/diagram-js.css'
import 'bpmn-js/dist/assets/bpmn-font/css/bpmn.css'
import 'bpmn-js/dist/assets/bpmn-font/css/bpmn-codes.css'
import 'bpmn-js/dist/assets/bpmn-font/css/bpmn-embedded.css'
import 'bpmn-js-properties-panel/dist/assets/properties-panel.css'
import 'bpmn-js-properties-panel/dist/assets/element-templates.css'

import BpmnJS from 'bpmn-js/lib/Modeler';
import {BpmnPropertiesPanelModule,BpmnPropertiesProviderModule} from 'bpmn-js-properties-panel';
import activitiModdle from "./activiti";
import activitiDescriptor from "./activitiDescriptor.json";


export default {
  name: 'c-bpmn-design',
  props: {
    value: {
      type: String,
      default: initialDiagramXML
    },
  },
  mixins: [system],
  watch: {
    form: {
      handler(newVal) {
        if (this.show.isElement) {
          if (this.selectedElement) {
            this.updateElementId(this.selectedElement, 'id', newVal.elementId)
            if (newVal.elementName) {
              this.updateElementId(this.selectedElement, 'name', newVal.elementName)
            }
          }
        }
      },
      deep: true
    }
  },
  beforeDestroy() {
    if (this.bpmnModeler) {
      this.bpmnModeler.destroy();
    }
  },
  mounted() {
    this.initBpmn()
  },
  model: {
    prop: 'value',
    event: "change",
  },
  data() {
    return {
      bpmnModeler: null, // BpmnJS 实例
      initialDiagramXML,
      show: {
        isElement: false,
      },
      selectedElement: null,
    }
  },
  methods: {
    async initBpmn() {
      // 初始化 BpmnJS
      this.bpmnModeler = new BpmnJS({
        container: this.$refs.canvas,
        // 挂载属性面板
        propertiesPanel: {
          parent: this.$refs.propertiesPanel,
        },
        additionalModules: [
          BpmnPropertiesPanelModule,
          BpmnPropertiesProviderModule,
          customTranslateModule,
          activitiModdle
        ],
        moddleExtensions: {
          activiti: activitiDescriptor
        },
      });
      this.bindEvents();
      this.loadDiagram(this.initialDiagramXML);
    },
    updateElementId(element, key, value) {
      console.log('更新数据-->', element, key, value)
      if (!value || !value.trim()) {
        this.$message.error(`请输入一个有效的新 ${key}！`);
        return;
      }
      if (/\s/.test(value)) {
        this.$message.error(`${key} 不能包含空格！`);
        return;
      }
      const modeling = this.bpmnModeler.get('modeling');
      console.log(modeling)
      modeling.updateProperties(element, {
        [key]: value
      });
    },
    readElement({businessObject}) {
      this.$nextTick(() => {
        console.log('读取数据start-->',businessObject)
        this.$set(this.form, 'elementId', businessObject?.id);
        this.$set(this.form, 'elementName', businessObject?.name);
        this.$set(this.form, 'assignee', businessObject?.assignee);
        console.log('读取数据end-->',this.form)

      })
    },
    bindEvents() {
      // 1. 图表导入完成事件
      const importDone = (event) => {
        const {error, warnings} = event;
        if (error) {
          console.error('BPMN导入失败!', error);
        } else {
          // 可以在这里执行自适应视口等操作
          this.bpmnModeler.get('canvas').zoom('fit-viewport');
          const canvas = this.bpmnModeler.get('canvas');
          const rootElement = canvas.getRootElement();
          const businessObject = rootElement.businessObject;
          this.$set(this.form, 'processId', businessObject.id);
          this.$set(this.form, 'processName', businessObject.name);
          console.log('BPMN导入成功!', businessObject);

        }
      };
      // 2. 元素点击事件
      const elementClick = (event) => {
        const {element} = event;
        this.selectedElement = element;
        this.form.elementName = element.businessObject.name
        this.form.elementId = element.businessObject.id
        if (!element.parent) {
          const {businessObject} = element
          this.$set(this.form, 'processId', businessObject.id);
          this.$set(this.form, 'processName', businessObject.name);
          this.show.isElement = false
          return;
        }
        this.show.isElement = true
        // businessObject 包含了元素的业务属性，如 name, assignee 等
        console.debug('点击的元素-->',element.businessObject)
      };
      // 3. 元素属性变化事件
      const elementChange = async (event) => {
        const {element} = event;
        this.selectedElement = element;
        this.readElement(element)
        console.log(`3.修改了元素 ${element.id}-->`, JSON.stringify(element.businessObject));
        this.bpmnModeler.saveXML({format: true}).then(res => {
          // console.log('change', res.xml)
          this.$emit('change', res.xml)
        });
      };
      // 4. 新节点添加事件
      const shapeAdd = (event) => {
        this.show.isElement = true
        const {element} = event;
        this.selectedElement = element;
        this.readElement(element)
        console.log(`4.添加元素--->: ${element.id} (类型: ${element.type})`);
      };
      // 5. 撤销/重做事件
      const commandStackChange = (event) => {
        console.log('5命令栈发生变化，可能执行了撤销或重做。');
        console.log(event)
        // 在这里可以触发保存操作，更新后端数据
      };

      const eventBus = this.bpmnModeler.get('eventBus');
      eventBus.on('import.done', importDone);
      eventBus.on('element.click', elementClick);
      eventBus.on('element.changed', elementChange);
      eventBus.on('shape.added', shapeAdd);
      eventBus.on('commandStack.changed', commandStackChange);
    },
    async loadDiagram(xml) {
      try {
        await this.bpmnModeler.importXML(xml);
        this.fitViewport();
        console.log('BPMN 图加载成功!');
      } catch (err) {
        console.error('加载 BPMN 图失败:', err);
      }
    },
    // 使图表适应视口
    fitViewport() {
      this.bpmnModeler.get('canvas').zoom('fit-viewport');
    },
    // 触发文件选择
    handleOpenFile() {
      this.$refs.fileInput.click();
    },
    // 导入本地 BPMN 文件
    importBPMN(event) {
      const file = event.target.files[0];
      if (file) {
        const reader = new FileReader();
        reader.onload = (e) => {
          this.loadDiagram(e.target.result);
        };
        reader.readAsText(file);
      }
    },
    // 导出 BPMN (XML 格式)
    async handleDownloadXML() {
      try {
        const {xml} = await this.bpmnModeler.saveXML({format: true});
        this.downloadFile('diagram.bpmn', xml, 'application/xml');
      } catch (err) {
        console.error('导出 BPMN 失败:', err);
      }
    },
    // 导出 SVG 图像
    async handleDownloadSVG() {
      try {
        const {svg} = await this.bpmnModeler.saveSVG();
        this.downloadFile('diagram.svg', svg, 'image/svg+xml');
      } catch (err) {
        console.error('导出 SVG 失败:', err);
      }
    },
  },

}
</script>
<style scoped lang="less">
/* 容器样式 */
.bpmn-container {
  display: flex;
  flex-direction: column;
  border: 1px solid #ccc;
}


/* 建模器和属性面板容器 */
.bpmn-canvas-container {
  display: flex;
  flex: 1;
  overflow: hidden;
  height: calc(100vh - 101px);
}

/* 画布样式 */
.bpmn-canvas {
  flex: 1;
  height: 100%;
}

/* 属性面板样式 */
.bpmn-properties-panel {
  overflow-y: auto;
  background-color: #f8f8f8;
  border: 1px solid #f1f1f1;
  padding: 6px;
}

/* 属性面板样式 */
.bpmn-properties-panel {
  width: 300px;
  height: 100%;
  overflow-y: auto;
  background-color: #f8f8f8;
  border-left: 1px solid #ccc;
}


/* 隐藏 bpmn.io Logo */
/deep/ .bjs-powered-by {
  display: none;
}
</style>
