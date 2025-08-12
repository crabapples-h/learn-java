<!--
    自定义组件，字典选择组件
-->
<template>
  <div>
    <a-radio-group name="radioGroup" v-model="selected" @change="onChange">
      <a-radio v-for="item in options" :key="item.id" :value="item.value"> {{ item.label }}</a-radio>
    </a-radio-group>
  </div>
</template>

<script>
import system from '@/mixins/system'

export default {
  name: 'c-dict-radio',
  mixins: [system],
  props: {
    dictCode: {
      type: String,
      default: '',
      required: true,
    },
    name: {
      type: String,
      default: 'default',
    },
    value: {
      type: String,
      default: ''
    },
  },
  data() {
    return {
      selected: '',
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
      this.$emit('change', this.selected)
    },
  }
}
</script>

<style scoped>
</style>
