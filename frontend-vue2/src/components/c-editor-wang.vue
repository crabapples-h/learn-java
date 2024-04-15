<!--
  富文本编辑器 wang-editor
-->
<template>
  <div style="border: 1px solid #ccc;">
    <Toolbar
        style="border-bottom: 1px solid #ccc"
        :editor="editor"
        :defaultConfig="toolbarConfig"
        :mode="mode"
    />
    <Editor :style="{'height': height, 'overflow-y': 'hidden'}"
            v-model="content"
            :defaultConfig="editorConfig"
            :mode="mode"
            @onCreated="onCreated"
            @onChange="onChange"
            @onDestroyed="onDestroyed"
            @onMaxLength="onMaxLength"
            @onFocus="onFocus"
            @onBlur="onBlur"
            @customAlert="customAlert"
            @customPaste="customPaste"
    />
  </div>
</template>

<script>
const full = [
  {
    key: 'group-more-style', // 必填，要以 group 开头
    title: '更多样式', // 必填
    iconSvg: '<svg>....</svg>', // 可选
    menuKeys: ["through", "code", "clearStyle"] // 下级菜单 key ，必填
  }, //  菜单组，包含多个菜单
  '|',// 分割线
  "bold",//加粗
  "underline",// 下划线
  "italic",// 斜体
  "through",//删除线
  "code",// 行内代码
  "sub",// 下标
  "sup",// 上标
  "clearStyle",// 清除样式
  "color",// 文字颜色
  "bgColor",// 背景颜色
  "fontSize",// 字号
  "fontFamily",// 字体
  "indent",//增加缩进
  "delIndent",//减少缩进
  "justifyLeft",//左对齐
  "justifyRight",//右对齐
  "justifyCenter",// 居中对齐
  "justifyJustify",// 两端对齐
  "lineHeight",//行高
  "insertImage",//插入网络图片
  "deleteImage",// 删除图片
  "editImage",//编辑图片
  "viewImageLink",//查看链接
  "imageWidth30",
  "imageWidth50",
  "imageWidth100",
  "divider",//分割线
  "emotion",//表情
  "insertLink",//插入超链接
  "editLink",// 修改超链接
  "unLink",//取消超链接
  "viewLink",// 查看超链接
  "codeBlock",// 代码块
  "blockquote",// 引用
  "headerSelect",// 标题
  "header1",
  "header2",
  "header3",
  "header4",
  "header5",
  "todo",// 待办
  "redo",// 重做
  "undo",// 撤销
  "fullScreen",// 全屏
  "enter",// 插入回车
  "bulletedList",// 无序列表
  "numberedList",//  有序列表
  "insertTable",// 插入表格
  "deleteTable",// 删除表格
  "insertTableRow",// 插入表格行
  "deleteTableRow",// 删除表格行
  "insertTableCol",// 插入表格列
  "deleteTableCol",// 删除表格列
  "tableHeader",//表头
  "tableFullWidth",// 表格宽度自适应
  "insertVideo",// 插入网络视频
  "uploadVideo",//上传视频
  "editVideoSize",// 修改视频尺寸
  "uploadImage",//上传图片
  "codeSelectLang"//选择代码语言

]
const simple = [
  // {
  //   key: 'group-more-style', // 必填，要以 group 开头
  //   title: '更多样式', // 必填
  //   iconSvg: '<svg>....</svg>', // 可选
  //   menuKeys: ["through", "code", "clearStyle"] // 下级菜单 key ，必填
  // }, //  菜单组，包含多个菜单
  '|',// 分割线
  "bold",//加粗
  "underline",// 下划线
  "italic",// 斜体
  "through",//删除线
  "code",// 行内代码
  "sub",// 下标
  "sup",// 上标
  "clearStyle",// 清除样式
  "color",// 文字颜色
  "bgColor",// 背景颜色
  "fontSize",// 字号
  "fontFamily",// 字体
  "indent",//增加缩进
  "delIndent",//减少缩进
  "justifyLeft",//左对齐
  "justifyRight",//右对齐
  "justifyCenter",// 居中对齐
  "justifyJustify",// 两端对齐
  "lineHeight",//行高
  "insertImage",//插入网络图片
  "deleteImage",// 删除图片
  "editImage",//编辑图片
  "viewImageLink",//查看链接
  "imageWidth30",
  "imageWidth50",
  "imageWidth100",
  "divider",//分割线
  "emotion",//表情
  "insertLink",//插入超链接
  "editLink",// 修改超链接
  "unLink",//取消超链接
  "viewLink",// 查看超链接
  "codeBlock",// 代码块
  "blockquote",// 引用
  "headerSelect",// 标题
  "header1",
  "header2",
  "header3",
  "header4",
  "header5",
  "todo",// 待办
  "redo",// 重做
  "undo",// 撤销
  "fullScreen",// 全屏
  "enter",// 插入回车
  "bulletedList",// 无序列表
  "numberedList",//  有序列表
  "insertTable",// 插入表格
  "deleteTable",// 删除表格
  "insertTableRow",// 插入表格行
  "deleteTableRow",// 删除表格行
  "insertTableCol",// 插入表格列
  "deleteTableCol",// 删除表格列
  "tableHeader",//表头
  "tableFullWidth",// 表格宽度自适应
  "insertVideo",// 插入网络视频
  "uploadVideo",//上传视频
  "editVideoSize",// 修改视频尺寸
  "uploadImage",//上传图片
  "codeSelectLang"//选择代码语言

]
const custom = [
  {
    key: 'group-font-style', // 必填，要以 group 开头
    title: '更多样式', // 必填
    iconSvg: '<b>B</b>', // 可选
    menuKeys: ["bold", "underline", "italic", "through"]
  }, //  菜单组，包含多个菜单
  "fontSize",// 字号
  "fontFamily",// 字体
  "color",// 文字颜色
  "bgColor",// 背景颜色
  '|',
  "clearStyle",// 清除样式
  "headerSelect",// 标题
  '|',
  "indent",//增加缩进
  "delIndent",//减少缩进
  "justifyLeft",//左对齐
  "justifyRight",//右对齐
  "justifyCenter",// 居中对齐
  "justifyJustify",// 两端对齐
  '|',
  "lineHeight",//行高
  "divider",//分割线
  "emotion",//表情
  "insertLink",//插入超链接
  "bulletedList",// 无序列表
  "numberedList",//  有序列表
  "insertTable",// 插入表格
  "uploadVideo",//上传视频
  "uploadImage",//上传图片
]
import {Editor, Toolbar} from '@wangeditor/editor-for-vue'

export default {
  components: {Editor, Toolbar},
  props: {
    maxLength: {
      type: Number,
    },
    readOnly: {
      type: Boolean,
      default: false
    },
    autoFocus: {
      type: Boolean,
      default: false
    },
    height: {
      type: String,
      default: '300px'
    },
    mode: {
      type: String,
      default: 'simple'// 'default' or 'simple'
    },
    value: {
      type: String,
      default: ''
    },
    placeholder: {
      type: String,
      default: '请输入内容...'
    },
  },
  watch: {
    value: {
      handler: function (val, oldVal) {
       this.content = val
      },
      immediate: true
    }
  },
  model: {
    prop: "value",
    event: "change",
  },
  data() {
    return {
      content: '',
      editor: null,
      toolbarConfig: {
        toolbarKeys: custom
      },
      editorConfig: {
        placeholder: this.placeholder,
        readOnly: this.readOnly,
        autoFocus: this.autoFocus,
        maxLength: this.maxLength,
        MENU_CONF: {
          'uploadImage': {
            server: '/api/uploadFile',
            // form-data fieldName ，默认值 'wangeditor-uploaded-image'
            fieldName: 'file',
            // 单个文件的最大体积限制，默认为 2M
            maxFileSize: 10 * 1024 * 1024, // 1M
            // 最多可上传几个文件，默认为 100
            maxNumberOfFiles: 10,
            // 选择文件时的类型限制，默认为 ['image/*'] 。如不想限制，则设置为 []
            allowedFileTypes: ['image/*'],
            // 自定义上传参数，例如传递验证的 token 等。参数会被添加到 formData 中，一起上传到服务端。
            meta: {
              token: 'xxx',
              otherKey: 'yyy'
            },
            // 将 meta 拼接到 url 参数中，默认 false
            metaWithUrl: false,
            // 自定义增加 http  header
            headers: {
              Accept: 'text/x-json',
              otherKey: 'xxx'
            },
            // 跨域是否传递 cookie ，默认为 false
            withCredentials: true,
            // 超时时间，默认为 10 秒
            timeout: 5 * 1000, // 5 秒
            // 上传之前触发
            onBeforeUpload(file) {
              // file 选中的文件，格式如 { key: file }
              return file

              // 可以 return
              // 1. return file 或者 new 一个 file ，接下来将上传
              // 2. return false ，不上传这个 file
            },

            // 上传进度的回调函数
            onProgress(progress) {
              // progress 是 0-100 的数字
              console.log('progress', progress)
            },

            // 单个文件上传成功之后
            onSuccess(file, res) {
              console.log(`${file.name} 上传成功`, res)
            },

            // 单个文件上传失败
            onFailed(file, res) {
              console.log(`${file.name} 上传失败`, res)
            },

            // 上传错误，或者触发 timeout 超时
            onError(file, err, res) {
              console.log(`${file.name} 上传出错`, err, res)
            },
            // 自定义插入图片
            customInsert(res, insertFn) {
              console.log(res.data)
              let url = res.data.virtualPath
              let alt = res.data.oldName
              let href = res.data.virtualPath
              console.log(url, alt, href)
              // res 即服务端的返回结果

              // 从 res 中找到 url alt href ，然后插入图片
              insertFn(url, alt, href)
            },
          },
          'uploadVideo': {
            server: '/api/uploadFile',
            // form-data fieldName ，默认值 'wangeditor-uploaded-image'
            fieldName: 'file',
            // 单个文件的最大体积限制，默认为 2M
            maxFileSize: 1000 * 1024 * 1024, // 1M
            headers: {
              Accept: 'text/x-json',
              otherKey: 'xxx'
            },
            timeout: 5 * 1000, // 5 秒
            // 上传之前触发
            onBeforeUpload(file) {
              // file 选中的文件，格式如 { key: file }
              // 可以 return
              // 1. return file 或者 new 一个 file ，接下来将上传
              // 2. return false ，不上传这个 file
              return file
            },

            // 上传进度的回调函数
            onProgress(progress) {
              // progress 是 0-100 的数字
              console.log('progress', progress)
            },

            // 单个文件上传成功之后
            onSuccess(file, res) {
              console.log(`${file.name} 上传成功`, res)
            },

            // 单个文件上传失败
            onFailed(file, res) {
              console.log(`${file.name} 上传失败`, res)
            },

            // 上传错误，或者触发 timeout 超时
            onError(file, err, res) {
              console.log(`${file.name} 上传出错`, err, res)
            },
            // 自定义插入图片
            customInsert(res, insertFn) {
              console.log(res.data)
              let url = res.data.virtualPath
              let alt = res.data.oldName
              let href = res.data.virtualPath
              console.log(url, alt, href)
              // res 即服务端的返回结果

              // 从 res 中找到 url alt href ，然后插入图片
              insertFn(url, alt, href)
            },
          },
        }
      }
      ,
    }
  }
  ,
  methods: {
    // 编辑器初始化
    onCreated(editor) {
      // 一定要用 Object.seal() ，否则会报错
      this.editor = Object.seal(editor)
    },
    // 编辑器内容改变
    onChange(editor) {
      this.$emit("change", this.content)
    },
    // 编辑器销毁
    onDestroyed(editor) {
    },
    // 超过最大字数限制
    onMaxLength(editor) {
    },
    // 获得焦点
    onFocus(editor) {
    },
    // 失去焦点
    onBlur(editor) {
    },
    customAlert(info, type) {
      window.alert(`customAlert in Vue demo\n${type}:\n${info}`)
    },
    customPaste(editor, event, callback) {
      console.log('ClipboardEvent 粘贴事件对象', event)
      // const html = event.clipboardData.getData('text/html') // 获取粘贴的 html
      // const text = event.clipboardData.getData('text/plain') // 获取粘贴的纯文本
      // const rtf = event.clipboardData.getData('text/rtf') // 获取 rtf 数据（如从 word wsp 复制粘贴）

      // 自定义插入内容
      editor.insertText('xxx')

      // 返回 false ，阻止默认粘贴行为
      event.preventDefault()
      callback(false) // 返回值（注意，vue 事件的返回值，不能用 return）

      // 返回 true ，继续默认的粘贴行为
      // callback(true)
    },
  },
  mounted() {

  },

  beforeDestroy() {
    const editor = this.editor
    if (editor == null) return
    // 组件销毁时，及时销毁编辑器
    editor.destroy()
  }
}
</script>
<style src="@wangeditor/editor/dist/css/style.css"></style>
<style scoped></style>
