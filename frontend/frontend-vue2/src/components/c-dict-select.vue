<!--
    自定义组件，字典选择组件
-->
<template>
  <div>
    <a-select :mode="multiple" :placeholder="placeholder" :fieldNames="fieldNames"
              :options="options" :value="selected"
              :filterOption="filter" @change="onChange">
      <a-select-option v-for="item in options" :key="item.id" :value="item.value">
        {{ item.label }}
      </a-select-option>
    </a-select>
  </div>
</template>

<script>
import system from '@/mixins/system'

export default {
  name: 'c-dict-select',
  mixins: [system],
  props: {
    dictCode: {
      type: String,
      default: '',
      required: true,
    },
    multiple: {
      type: String,
      default: 'default',
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
          label: 'label',
          value: 'value',
        }
      }
    },
    placeholder: {
      type: String,
      default: '请选择'
    },
  },
  data() {
    return {
      selected: [],
      options: [],
    }
  },
  watch: {
    value: {
      handler: function (val, oldVal) {
        console.log('handler', val)
        this.selected = val
      },
      immediate: true
    }
  },
  model: {
    prop: "value",
    event: "change",
  },
  mounted() {
    this.loadDictOptions()
  },
  methods: {
    loadDictOptions() {
      if (this.dictCode) {
        this.$http.get(`/api/system/dict/options/${this.dictCode}`).then(result => {
          if (result.status !== 200) {
            this.$message.error(result.message);
            return;
          }
          this.options = result.data
        }).catch(function (error) {
          console.error('出现错误:', error);
        }).finally(() => {
        });
      }
    },
    onChange(value, selectedOptions) {
      this.$emit('change', value)
    },
    filter(inputValue, path) {
      return path.some(option => option.label.toLowerCase().indexOf(inputValue.toLowerCase()) > -1);
    },
  }
}
</script>

<style scoped>
</style>
