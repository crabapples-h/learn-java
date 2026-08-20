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
    <a-upload
      ref="fileUpload"
      name="file"
      :action="uploadMinio"
      :multiple="multiple"
      :accept="accept"
      :headers="headers"
      :file-list="fileList"
      :list-type="listType"
      @change="handleChange"
      @preview="handlePreview"
      @remove="removeImage"
    >
      <div v-if="fileList.length < max">
        <template v-if="listType === 'picture-card'">
          <svg class="iconfont" aria-hidden="true" style="width: 1em;height: 1em">
            <use href="#icon-plus" />
          </svg>
          <div class="ant-upload-text">{{ text }}</div>
        </template>
        <template v-else>
          <a-button>
            <svg class="iconfont" aria-hidden="true" style="width: 1em;height: 1em">
              <use href="#icon-upload" />
            </svg>
            <span>{{ text }}</span>
          </a-button>
        </template>
      </div>
    </a-upload>
    <a-modal title="图片预览" :open="previewVisible" :footer="null" @cancel="handleCancel">
      <img alt="预览" style="width: 100%" :src="previewImage" />
    </a-modal>
  </div>
</template>

<script setup>
import { ref, watch } from 'vue'
import { getBase64 } from '@/utils/useSystem'
import storage from '@/store/storage'

const props = defineProps({
  value: {
    type: String,
    default: '',
  },
  max: {
    type: Number,
    default: 1,
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
})

const emit = defineEmits(['change'])

const fileUpload = ref(null)
const fileList = ref([])
const previewVisible = ref(false)
const previewImage = ref('')
const uploadMinio = '/api/file/upload/MINIO'
const headers = {
  'crabapples-token': localStorage.getItem('TOKEN') || '',
}

watch(
  () => props.value,
  val => {
    if (val) {
      fileList.value = val.split(',').map(e => ({
        id: Math.random(),
        key: Math.random(),
        uid: Math.random(),
        name: e,
        status: 'done',
        url: e,
      }))
    }
  },
  { immediate: true },
)

const handleChange = info => {
  fileList.value = info.fileList
  const status = info.file.status
  if (status === 'done' || status === 'removed') {
    const imageList = fileList.value.map(e => e.response?.data || e.url)
    emit('change', imageList.join(','))
  }
}

const removeImage = file => {
  fileList.value = fileList.value.filter(e => e.uid !== file.uid)
}

const handleCancel = () => {
  previewVisible.value = false
}

const handlePreview = async file => {
  if (!file.url && !file.preview) {
    file.preview = await getBase64(file.originFileObj)
  }
  previewImage.value = file.url || file.preview
  const serverAddress = storage.getServerAddress()
  const previewAddress = storage.getFilePreviewAddress()
  const url = `${serverAddress}${file.url}`
  const previewUrl = `${previewAddress}/onlinePreview?url=${encodeURIComponent(btoa(url))}`
  window.open(previewUrl)
}
</script>

<style scoped>
</style>
