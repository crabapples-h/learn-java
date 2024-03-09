<template>
  <div>
    <froala id="edit" :config="config" v-model="content"></froala>
  </div>
</template>

<script>
//Import Froala Editor
import 'froala-editor/js/plugins.pkgd.min.js';
//Import third party plugins
import 'froala-editor/js/languages/zh_cn';
import 'froala-editor/js/third_party/embedly.min';
import 'froala-editor/js/third_party/font_awesome.min';
import 'froala-editor/js/third_party/spell_checker.min';
import 'froala-editor/js/third_party/image_tui.min';
// Import Froala Editor css files.
import 'froala-editor/css/froala_editor.pkgd.min.css';


export default {
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
      editor: null,
      content: '',
      config: {
        // toolbarButtons:
        // ['undo', 'redo', 'clearFormatting', '|', 'bold', 'italic', 'underline', 'strikeThrough', '|', 'fontFamily', 'fontSize', 'color', '|', 'paragraphFormat', 'align', 'formatOL', 'formatUL', 'outdent', 'indent', 'quote', '-', 'insertLink', 'insertImage', 'insertVideo', 'embedly', 'insertFile', 'insertTable', '|', 'emoticons', 'specialCharacters', 'insertHR', 'selectAll', '|', 'print', 'spellChecker', 'help', '|', 'fullscreen'],
        // ['fullscreen', 'bold', 'italic', 'underline', 'strikeThrough', 'subscript', 'superscript', '|', 'fontFamily', 'fontSize', 'color', 'inlineStyle', 'paragraphStyle', '|', 'paragraphFormat', 'align', 'formatOL', 'formatUL', 'outdent', 'indent', 'quote', '-', 'insertLink', 'insertImage', 'insertVideo', 'embedly', 'insertFile', 'insertTable', '|', 'emoticons', 'specialCharacters', 'insertHR', 'selectAll', 'clearFormatting', '|', 'print', 'spellChecker', 'help', 'html', '|', 'undo', 'redo'],//显示可操作项
        theme: "dark",//主题
        placeholder: "请填写内容",
        language: "zh_cn",//国际化
        imageUploadURL: "http://i.froala.com/upload",//上传url
        fileUploadURL: "http://i.froala.com/upload",//上传url 更多上传介绍 请访问https://www.froala.com/wysiwyg-editor/docs/options
        quickInsertButtons: ['image', 'table', 'ul', 'ol', 'hr'],//快速插入项
        // toolbarVisibleWithoutSelection: true,//是否开启 不选中模式
        // disableRightClick: true,//是否屏蔽右击
        colorsHEXInput: false,//关闭16进制色值
        toolbarSticky: true,//操作栏是否自动吸顶
        zIndex: 99999,
        events: {
          'initialized': function () {
            console.log('initialized')
          },
          'input': function (inputEvent) {
            // 在这里做一些事情。
            // 这是编辑器实例。

            console.log(this.html.get(true));
            console.log(this);
          }
        },

      },
    }
  },
  computed: {},
  mounted() {
  },
  methods: {},

  beforeDestroy() {
  }
}
</script>
<style>
</style>
