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
    </div>
    <!-- 隐藏的文件输入框 -->
    <input type="file" ref="fileInput" style="display: none" @change="importBPMN" accept=".bpmn, .xml"/>
  </div>
</template>
<script>
import BpmnJS from 'bpmn-js/lib/Modeler';
import 'bpmn-js/dist/assets/diagram-js.css'
import 'bpmn-js/dist/assets/bpmn-font/css/bpmn.css'
import CamundaModdleDescriptor from 'camunda-bpmn-moddle/resources/camunda.json';
import customTranslateModule, {initialDiagramXML} from './c-bpmn-plugins';
import system from "@/mixins/system";


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
    value: {
      handler(newVal) {
        this.xml = newVal
      },
      immediate: true
    },
    form: {
      handler(newVal) {
        if (this.show.isElement) {
          console.log(newVal)
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
    // 组件销毁时，销毁 BpmnJS 实例
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
    updateElementId(element, key, value) {
      console.log('updateElementId-->', element, key, value)
      // 检查是否有选中的元素和输入的新 ID
      if (!value || !value.trim()) {
        this.$message.error(`请输入一个有效的新 ${key}！`);
        return;
      }
      //  不能包含空格
      if (/\s/.test(value)) {
        this.$message.error(`${key} 不能包含空格！`);
        return;
      }

      // 获取 modeling 模块
      const modeling = this.bpmnModeler.get('modeling');

      // 使用 updateProperties 方法来修改 ID
      // 第一个参数是目标元素
      // 第二个参数是一个对象，包含了要更新的属性
      modeling.updateProperties(element, {
        [key]: value
      });
      // console.log(`元素 ${key} 已成功修改为: ${value}`);
      //
      // // 更新后，最好也更新一下 activeElement 的 id，虽然模型已经变了
      // // 但我们的本地引用可能还是旧的
      // element[key] = value;
    },
    readElement({businessObject}) {
      this.$nextTick(() => {
        this.$set(this.form, 'elementId', businessObject?.id);
        this.$set(this.form, 'elementName', businessObject?.name);
      })
    },
    bindEvents() {
      const eventBus = this.bpmnModeler.get('eventBus');
      // 1. 监听图表导入完成事件
      eventBus.on('import.done', (event) => {
        const {error, warnings} = event;
        if (error) {
          console.error('图表导入失败!', error);
        } else {
          // 可以在这里执行自适应视口等操作
          this.bpmnModeler.get('canvas').zoom('fit-viewport');
          const canvas = this.bpmnModeler.get('canvas');
          const rootElement = canvas.getRootElement();
          const businessObject = rootElement.businessObject;
          this.$set(this.form, 'processId', businessObject.id);
          this.$set(this.form, 'processName', businessObject.name);
          console.log('图表导入成功!', businessObject);

        }
      });
      // 2. 监听元素点击事件
      eventBus.on('element.click', (event) => {
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
        if (element.businessObject.assignee) {
          console.log('负责人:', element.businessObject.assignee);
        }
      });
      // 3. 监听元素属性变化事件
      eventBus.on('element.changed', async (event) => {
        const {element} = event;
        this.selectedElement = element;
        this.readElement(element)
        console.log(`3.元素 ${element.id} 的属性或位置发生了变化`, JSON.stringify(element.businessObject));
        this.bpmnModeler.saveXML({format: true}).then(res => {
          this.$emit('change', res.xml)
        });
      });
      // 4. 监听新形状添加事件
      eventBus.on('shape.added', (event) => {
        this.show.isElement = true
        const {element} = event;
        this.selectedElement = element;
        this.readElement(element)
        console.log(`4.一个新的形状被添加: ${element.id} (类型: ${element.type})`);
      });
      // 5. 监听撤销/重做事件
      eventBus.on('commandStack.changed', (event) => {
        console.log('5命令栈发生变化，可能执行了撤销或重做。');
        console.log(event)
        // 在这里可以触发保存操作，更新后端数据
      });
    },
    initBpmn() {
      // 获取 DOM 元素
      const canvas = this.$refs.canvas;
      const propertiesPanel = this.$refs.propertiesPanel;

      // 初始化 BpmnJS
      this.bpmnModeler = new BpmnJS({
        container: canvas,
        // 挂载属性面板
        propertiesPanel: {
          parent: propertiesPanel,
        },
        // 添加 Camunda 扩展模块
        additionalModules: [
          // BpmnPropertiesPanelModule,
          // BpmnPropertiesProviderModule,
          customTranslateModule
          // CamundaExtensionModule,
        ],
        // 添加 Camunda 扩展描述符
        moddleExtensions: {
          camunda: CamundaModdleDescriptor,
        },
      });
      this.bindEvents();

      // 加载默认的 BPMN 图
      this.loadDiagram(this.initialDiagramXML);
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

/* 隐藏 bpmn.io Logo */
/deep/ .bjs-powered-by {
  display: none;
}
</style>
