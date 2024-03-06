<template>
  <div>
    <a-cascader v-model="selected" :options="options" :show-search="true" :placeholder="placeholder"
                @change="onChange" @search="filter" :fieldNames="fieldNames"/>
  </div>
</template>

<script>
import system from "@/mixins/system";

export default {
  name: 'c-area-select',
  mixins: [system],
  props: {
    dataType: {
      type: String,
      default: 'string',
      validator: function (value) {
        return ["string", "array"].includes(value);
      },
    },
    value: {
      type: [Array, String, Number],
      default: () => {
        return []
      }
    },
    fieldNames: {
      type: Object,
      default: () => {
        return {
          label: 'name',
          value: 'code',
          children: 'children'
        }
      }
    },
    placeholder: {
      type: String,
      default: '请选择区域'
    },
  },
  data() {
    return {
      selected: [],
      options: [],
      isArray: false,
    }
  },
  watch: {
    value: {
      handler: function (val, oldVal) {
        console.log('handler', val)
        if (typeof val === 'string') {
          this.selected = val.split(',')
        } else if (val instanceof Array) {
          this.selected = val
        }
      },
      immediate: true
    }
  },
  model: {
    prop: "value",
    event: "change",
  },
  mounted() {
    this.loadAreaTree()
  },
  methods: {
    loadAreaTree() {
      this.$http.get('/api/area/tree').then(result => {
        if (result.status !== 200) {
          this.$message.error(result.message);
          return;
        }
        this.options = result.data
      }).catch(function (error) {
        console.error('出现错误:', error);
      }).finally(() => {
      });
    },
    onChange(value, selectedOptions) {
      console.log(value, selectedOptions);
      if (this.dataType === 'array') {
        this.$emit('change', this.selected)
      } else if (this.dataType === 'string') {
        this.$emit('change', this.selected.join(','))
      }
    },
    filter(inputValue, path) {
      return path.some(option => option.label.toLowerCase().indexOf(inputValue.toLowerCase()) > -1);
    },
  }
}
</script>

<style scoped>
</style>
