<!--
  自定义组件，文件上传组件(返回逗号分割的字符串)
  list-type：显示类型(text, picture, picture-card)
  action：上传图片地址
  multiple：是否支持多选文件
  text：按钮文字
  accept：接受上传的文件类型
-->
<template>
  <div>
    <a-upload ref="fileUpload" name="file"
              :action="uploadMinio"
              :multiple="multiple"
              :accept="accept"
              :headers="headers"
              @change="handleChange"
              @preview="handlePreview"
              :file-list="fileList"
              :remove="removeImage"
              :list-type="listType">
      <div v-if="fileList.length < max">
        <template v-if="listType==='picture-card'">
          <a-icon type="plus"/>
          <div class="ant-upload-text">
            {{ text }}
          </div>
        </template>
        <template v-else>
          <a-button>
            <a-icon type="upload"/>
            <span>{{ text }}</span>
          </a-button>
        </template>
      </div>

    </a-upload>
    <a-modal title="图片预览" :visible="previewVisible" :footer="null" @cancel="handleCancel">
      <img alt="预览" style="width: 100%" :src="previewImage"/>
    </a-modal>
  </div>
</template>

<script>
import system, {getBase64} from '@/mixins/system'
import storage from "@/store/storage";

export default {
  name: 'c-file-upload',
  mixins: [system],
  props: {
    value: {
      type: String,
    },
    max: {
      type: Number,
      default: 1,
    },
    beforeUpload: {
      type: Function,
      default: function () {
      },
    },
    multiple: {
      type: Boolean,
      default: true,
    },
    accept: {
      type: String,
      default: '',
    },
    text: {
      type: String,
      default: '上传',
    },
    listType: {
      type: String,
      default: 'text',
    },
    name: {
      type: String,
      default: 'file',
    },
  },
  watch: {
    value: {
      handler: function (val, oldVal) {
        if (val) {
          this.fileList = val.split(',').map(e => {
            let imageItem = {
              id: Math.random(),
              key: Math.random(),
              uid: Math.random(),
              name: e,
              status: 'done',
              url: e
            }
            console.log(imageItem)
            return imageItem
          })
        }
      },
      immediate: true
    },
  },
  data() {
    return {
      // innerText: this.$slots.default[0].text
      fileList: [],
      previewVisible: false,
      previewImage: '',
    }
  },
  model: {
    prop: "value",
    event: "change",
  },
  methods: {
    handleChange(info) {
      this.fileList = info.fileList;
      let status = info.file.status
      if (status === "done" || status === "removed") {
        let imageList = this.fileList.map(e => e.response?.data || e.url);
        this.$emit("change", imageList.join(','))
        console.log('this.fileList', imageList)
      }
    },
    removeImage(file) {
      this.fileList = this.fileList.filter(e => e.uid !== file.uid)
    },
    handleCancel() {
      this.previewVisible = false;
    },
    async handlePreview(file) {
      console.log('file', file)
      if (!file.url && !file.preview) {
        file.preview = await getBase64(file.originFileObj);
      }
      this.previewImage = file.url || file.preview;
      let serverAddress = storage.getServerAddress()
      let previewAddress = storage.getFilePreviewAddress()
      let url = `${serverAddress}${file.url}`
      let previewUrl = `${previewAddress}/onlinePreview?url=${encodeURIComponent(btoa(url))}`
      window.open(previewUrl)
      // this.previewVisible = true;
    },
  }

}
</script>

<style scoped>
</style>
