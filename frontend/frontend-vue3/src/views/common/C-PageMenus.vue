<template>
  <a-layout-sider class="page-sider" width="220" theme="light">
    <a-menu mode="inline" :selected-keys="selectedKeys" :open-keys="openKeys" @click="handleClick">
      <template v-for="menu in menus" :key="menu.id || menu.path">
        <a-sub-menu v-if="menu.children?.length" :key="String(menu.id || menu.path)">
          <template #title>{{ menu.name }}</template>
          <a-menu-item v-for="child in menu.children" :key="child.path">
            {{ child.name }}
          </a-menu-item>
        </a-sub-menu>
        <a-menu-item v-else :key="menu.path">
          {{ menu.name }}
        </a-menu-item>
      </template>
    </a-menu>
  </a-layout-sider>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute } from 'vue-router'

const props = defineProps({
  menus: {
    type: Array,
    default: () => [],
  },
  theme: {
    type: String,
    default: 'light',
  },
})

const emit = defineEmits(['clickMenu'])
const route = useRoute()
const selectedKeys = computed(() => [route.path])
const openKeys = computed(() => [String(route.meta.pid || '')].filter(Boolean))

const flatten = items => items.flatMap(item => item.children?.length ? [item, ...flatten(item.children)] : [item])

const handleClick = ({ key }) => {
  const menu = flatten(props.menus).find(item => item.path === key || String(item.id) === String(key))
  if (menu) {
    emit('clickMenu', menu)
  }
}
</script>

<style scoped>
.page-sider {
  height: 100%;
  overflow: auto;
  border-right: 1px solid #e5e7eb;
  background: #fff;
}
</style>
