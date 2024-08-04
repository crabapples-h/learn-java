<!--
  自定义组件，带确认提升的按钮
  list-type：显示类型(text, picture, picture-card)
  action：上传图片地址
  multiple：是否支持多选文件
  text：按钮文字
  accept：接受上传的文件类型
-->
<template>
  <div>
    <a-upload ref="fileUpload" name="file"
              :action="uploadUrl"
              :multiple="multiple"
              :accept="accept"
              :headers="headers"
              @change="handleChange"
              @preview="handlePreview"
              @beforeUpload="beforeUpload"
              :file-list="fileList"
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
    <a-modal :visible="previewVisible" :footer="null" @cancel="handleCancel">
      <img alt="预览" style="width: 100%" :src="previewImage"/>
    </a-modal>
  </div>
</template>

<script>
import system, { getBase64 } from '@/mixins/system'

export default {
  name: 'c-file-upload',
  mixins: [system],
  props: {
    value: {
      type: [Array, String],
    },
    max: {
      type: Number,
      default: 2,
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
        if (typeof val === 'string') {
          console.log('is  String--->')
          this.fileList = val.split(',').map(e => {
            return {
              id: Math.random(),
              key: Math.random(),
              uid: Math.random(),
              name: e,
              status: 'done',
              url: e
            }
          })
        } else if (val instanceof Array) {
          this.fileList = val.map(e => {
            e.uid = e.id ? e.id : Math.random()
            e.name = e.oldName || e.name
            e.status = e.status ? e.status : 'done'
            e.url = e.virtualPath || e.url
            return e
          })
        }
        console.log('valueHandler--->', this.fileList)
      },
      immediate: true
    }
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
      // debugger
      console.log('change', info)
      if (info.file.status === "uploading") {
        let exist = false
        for (let i = 0; i < this.fileList.length; i++) {
          if (this.fileList[i].uid === info.file.uid) {
            exist = true
            break
          }
        }
        if (!exist) {
          this.fileList.push({
            id: info.file.uid,
            oldName: info.file.name,
            status: info.file.status,
            virtualPath: 'temp',
          })
        }
      }
      if (info.file.status === "done") {
        for (let i = 0; i < this.fileList.length; i++) {
          if (this.fileList[i].id === info.file.uid) {
            this.fileList[i] = info.file.response.data
            break
          }
        }
      }
      if (info.file.status === "removed") {
        for (let i = 0; i < this.fileList.length; i++) {
          if (this.fileList[i].id === info.file.id) {
            this.fileList.splice(i, 1)
            break
          }
        }
      }
      this.$emit("change", this.fileList)
    },
    handleCancel() {
      this.previewVisible = false;
    },
    async handlePreview(file) {
      if (!file.url && !file.preview) {
        file.preview = await getBase64(file.originFileObj);
      }
      this.previewImage = file.url || file.preview;
      this.previewVisible = true;
    },
  }

}
</script>

<style scoped>
</style>
