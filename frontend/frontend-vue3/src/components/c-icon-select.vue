<template>
  <a-modal :title="title" :open="open" :footer="null" width="80%" @cancel="closeExtend">
    <a-tabs type="card">
      <a-tab-pane v-for="icon in icons" :key="icon.id" :tab="icon.name">
        <a-space style="flex-wrap: wrap">
          <div v-for="item in icon.glyphs" :key="item.font_class" class="icon-item" @click="selectedIcon(item.font_class, item)">
            <svg class="iconfont" aria-hidden="true">
              <use :href="`#icon-${item.font_class}`" />
            </svg>
            <span>{{ item.name }}</span>
          </div>
        </a-space>
      </a-tab-pane>
    </a-tabs>
  </a-modal>
</template>

<script setup>
import { ref } from 'vue'
// antd图标库
import IconAntd from '@public/iconfont/icon-antd.json'
// lolita图标库
import IconLolita from '@public/iconfont/icon-lolita.json'
// 可爱图标库
import IconCute from '@public/iconfont/icon-cute.json'

defineProps({
  open: {
    type: Boolean,
    default: false,
  },
  title: {
    type: String,
    default: '',
  },
})

const emit = defineEmits(['change', 'update:open'])

const icons = ref([IconAntd, IconLolita, IconCute])

const closeExtend = () => {
  emit('update:open', false)
}

const selectedIcon = (key, item) => {
  console.log('选择图标->', key, item.name)
  emit('change', key)
  closeExtend()
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
  margin-top: 12px;
  cursor: pointer;
}
</style>
