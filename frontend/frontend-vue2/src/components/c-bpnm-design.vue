<template>
  <div>
    <div style="width: 100%;display: flex;height: calc(100vh - 107px)">
      <div style="width: 100%;display: flex;flex-direction: column;">
        <div style="text-align: center;padding: 6px">
          <a-space size="large">
            <a-button @click="handleOpenFile" type="primary">打开文件</a-button>
            <a-button @click="handleDownloadXML">下载 BPMN</a-button>
            <a-button @click="handleDownloadSVG">下载 SVG</a-button>
            <a-button @click="showKeys">显示未翻译的键</a-button>
          </a-space>
        </div>
        <!-- BPMN 建模器容器 -->
        <div class="bpmn-canvas-container">
          <div ref="canvas" class="bpmn-canvas"></div>
        </div>
      </div>
      <!-- BPMN 属性面板 -->
      <div ref="propertiesPanel" class="bpmn-properties-panel"></div>
    </div>

    <!-- 隐藏的文件输入框 -->
    <input type="file" ref="fileInput" style="display: none" @change="importBPMN" accept=".bpmn, .xml"/>
  </div>
</template>
<script>
import './bpmn/properties-panel.css';
import initialDiagramXML from './bpmn/initialDiagramXML';
import system from "@/mixins/system";
import 'bpmn-js/dist/assets/diagram-js.css'
import 'bpmn-js/dist/assets/bpmn-font/css/bpmn.css'
import 'bpmn-js/dist/assets/bpmn-font/css/bpmn-codes.css'
import 'bpmn-js/dist/assets/bpmn-font/css/bpmn-embedded.css'
import zh_cn from 'bpmn-js-i18n/translations/zn.js';
import zh_cn_custom from './bpmn/zh_cn';
import CamundaBpmnModdle from 'camunda-bpmn-moddle/resources/camunda.json'
import BpmnJS from 'bpmn-js/lib/Modeler'; // 或者使用 Viewer
import {BpmnPropertiesPanelModule} from 'bpmn-js-properties-panel';
import {BpmnPropertiesProviderModule} from 'bpmn-js-properties-panel';
import {CamundaPlatformPropertiesProviderModule} from 'bpmn-js-properties-panel';

export default {
  name: 'c-bpmn-design',
  props: {
    value: {
      type: String,
      default: initialDiagramXML
    },
  },
  mixins: [system],
  watch: {},
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
      selectedElement: null,
      untranslatedKeys: new Set()
    }
  },
  methods: {
    showKeys() {
      console.log(this.untranslatedKeys);
    },
    async initBpmn() {
      const _this = this
      const customTranslateModule = {
        translate: ['value', function (key) {
          const translated = {...zh_cn_custom, ...zh_cn}[key];
          if (!translated && key !== key.toLowerCase()) {
            _this.untranslatedKeys.add(key);
          }
          return `${translated}(${key})`;
        }]
      };
      // 初始化 BpmnJS
      this.bpmnModeler = new BpmnJS({
        bpmnRenderer: {
          defaultFillColor: '#fff',
          defaultStrokeColor: '#f490ac'
        },
        container: this.$refs.canvas,
        propertiesPanel: {
          parent: this.$refs.propertiesPanel,
        },
        additionalModules: [
          customTranslateModule,
          BpmnPropertiesPanelModule,
          BpmnPropertiesProviderModule,
          CamundaPlatformPropertiesProviderModule,
        ],
        moddleExtensions: {
          camunda: CamundaBpmnModdle
        }

      });
      this.bindEvents();
      this.loadDiagram(initialDiagramXML);
    },
    async loadDiagram(xml) {
      try {
        await this.bpmnModeler.importXML(xml);
        this.fitViewport();
        console.log('BPMN 加载成功!');
      } catch (err) {
        console.error('加载 BPMN 失败:', err);
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
          console.log('BPMN导入成功!', businessObject);

        }
      };
      // 2. 元素点击事件
      const elementClick = (event) => {
        const {element} = event;
        this.selectedElement = element;
        console.debug('点击的元素-->', element.businessObject)
      };
      // 3. 元素属性变化事件
      const elementChange = async (event) => {
        const {element} = event;
        this.selectedElement = element;
        console.log(`3.修改了元素 ${element.id}-->`, JSON.stringify(element.businessObject));
      };
      // 4. 新节点添加事件
      const shapeAdd = (event) => {
        const {element} = event;
        this.selectedElement = element;
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
    saveXml() {
      return this.bpmnModeler.saveXML({format: true, preamble: false})
    }
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
  //flex: 1;
  overflow: hidden;
  height: 100%;
}

/* 画布样式 */
.bpmn-canvas {
  //flex: 1;
  width: 100%;
  height: 100%;
}

/* 属性面板样式 */
.bpmn-properties-panel {
  //overflow-y: auto;
  background-color: #f8f8f8;
  border: 1px solid #f1f1f1;
  padding: 6px;
  height: 100vh;
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
