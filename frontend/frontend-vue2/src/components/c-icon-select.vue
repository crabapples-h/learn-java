<template>
  <a-modal :title="title" :visible="visible" @ok="" @cancel="closeExtend" width="80%" :footer="null">
    <a-tabs type="card" @change="">
      <a-tab-pane :key="icon.id" :tab="icon.name" v-for="icon in icons">
        <a-space style="flex-wrap: wrap">
          <div class="icon-item" v-for="item in icon.glyphs">
            <svg class="iconfont" aria-hidden="true" @click="selectedIcon(item.font_class,item)">
              <use :xlink:href="'#icon-'+item.font_class"></use>
            </svg>
            <span>{{ item.name }}</span>
          </div>
        </a-space>

      </a-tab-pane>

    </a-tabs>
  </a-modal>
</template>

<script>
import system from '@/mixins/system'
// antd图标库
import IconAntd from '@public/iconfont/icon-antd.json'
// lolita图标库
import IconLolita from '@public/iconfont/icon-lolita.json'
// 可爱图标库
import IconCute from '@public/iconfont/icon-cute.json'

export default {
  name: 'c-icon-select',
  mixins: [system],
  props: {
    visible: {
      type: Boolean,
      default: false
    },
    title: {
      type: String
    },
    close: {
      type: Function,
    }
  },
  data() {
    return {
      icons: []
    }
  },
  watch: {
    value: {
      handler: function (val, oldVal) {
        console.log('handler', val)
      },
      immediate: true
    }
  },
  model: {
    prop: "value",
    event: "change",
  },
  mounted() {
    this.icons.push(IconAntd)
    this.icons.push(IconLolita)
    this.icons.push(IconCute)
  },
  methods: {
    selectedIcon(key, item) {
      console.log("选择图标->", key, item.name)
      this.$emit('change', key)
      this.closeExtend()
    }
  }
}
</script>

<style scoped>
.iconfont {
  width: 3em;
  height: 3em;
  border: 1px solid #999;
  border-radius: 5px;
}

.icon-item {
  display: flex;
  flex-direction: column;
  width: 100px;
  align-items: center;
  margin-top: 12px
}
</style>
