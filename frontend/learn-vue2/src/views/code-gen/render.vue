<template>
  <div>
    <k-form-build :value="jsonData" ref="KFB" @submit="handleSubmit"/>
  </div>
</template>
<script>
export default {
  name: 'Demo',
  data() {
    return {
      jsonData: {
        "list": [{
          "type": "cascader",
          "label": "级联选择器",
          "options": {
            "disabled": false,
            "showLabel": true,
            "hidden": false,
            "showSearch": false,
            "placeholder": "请选择",
            "clearable": false,
            "dynamicKey": "",
            "dynamic": false,
            "options": [{
              "value": "1",
              "label": "选项1",
              "children": [{"value": "11", "label": "选项11"}]
            }, {"value": "2", "label": "选项2", "children": [{"value": "22", "label": "选项22"}]}]
          },
          "model": "cascader_1737017308648",
          "key": "cascader_1737017308648",
          "help": "",
          "rules": [{"required": false, "message": "必填项"}]
        }, {
          "type": "number",
          "label": "数字输入框",
          "options": {
            "width": "100%",
            "defaultValue": 0,
            "min": null,
            "max": null,
            "precision": null,
            "step": 1,
            "showLabel": true,
            "hidden": false,
            "disabled": false,
            "placeholder": "请输入"
          },
          "model": "number_1737017316740",
          "key": "number_1737017316740",
          "help": "",
          "rules": [{"required": false, "message": "必填项"}]
        }, {
          "type": "number",
          "label": "数字输入框",
          "options": {
            "width": "100%",
            "defaultValue": 0,
            "min": null,
            "max": null,
            "precision": null,
            "step": 1,
            "showLabel": true,
            "hidden": false,
            "disabled": false,
            "placeholder": "请输入"
          },
          "model": "number_1737017321960",
          "key": "number_1737017321960",
          "help": "",
          "rules": [{"required": false, "message": "必填项"}]
        }, {
          "type": "number",
          "label": "数字输入框",
          "options": {
            "width": "100%",
            "defaultValue": 0,
            "min": null,
            "max": null,
            "precision": null,
            "step": 1,
            "showLabel": true,
            "hidden": false,
            "disabled": false,
            "placeholder": "请输入"
          },
          "model": "number_1737017323328",
          "key": "number_1737017323328",
          "help": "",
          "rules": [{"required": false, "message": "必填项"}]
        }, {
          "type": "uploadImg",
          "label": "上传图片",
          "options": {
            "defaultValue": "[]",
            "multiple": false,
            "showLabel": true,
            "hidden": false,
            "disabled": false,
            "width": "100%",
            "data": "{}",
            "limit": 3,
            "placeholder": "上传",
            "fileName": "image",
            "headers": {},
            "action": "http://cdn.kcz66.com/upload-img.txt",
            "listType": "picture-card"
          },
          "model": "uploadImg_1737017443903",
          "key": "uploadImg_1737017443903",
          "help": "",
          "rules": [{"required": false, "message": "必填项"}]
        }],
        "config": {
          "layout": "horizontal",
          "labelCol": {"xs": 4, "sm": 4, "md": 4, "lg": 4, "xl": 4, "xxl": 4},
          "labelWidth": 100,
          "labelLayout": "flex",
          "wrapperCol": {"xs": 18, "sm": 18, "md": 18, "lg": 18, "xl": 18, "xxl": 18},
          "hideRequiredMark": false,
          "customStyle": ""
        }
      }
    }
  },
  methods: {
    handleSubmit(p) {
      // 通过表单提交按钮触发，获取promise对象
      p().then(res => {
        // 获取数据成功
        alert(JSON.stringify(res))
      }).catch(err => {
        console.log(err, '校验失败')
      })
    },
    getData() {
      // 通过函数获取数据
      this.$refs.KFB.getData().then(res => {
        // 获取数据成功
        alert(JSON.stringify(res))
      })
          .catch(err => {
            console.log(err, '校验失败')
          })
    }
  }
}
</script>