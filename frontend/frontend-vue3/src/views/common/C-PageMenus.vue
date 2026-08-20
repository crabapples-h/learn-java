<template>
  <a-layout-sider class="page-sider" width="220" theme="light">
    <div class="menu-search">
      <a-input v-model:value="searchKey" placeholder="搜索菜单" allow-clear />
    </div>
    <a-menu
      mode="inline"
      :selected-keys="selectedKeys"
      :open-keys="currentOpenKeys"
      @openChange="onOpenChange"
      @click="handleClick"
    >
      <template v-for="menu in filteredMenus" :key="String(menu.id || menu.path)">
        <a-sub-menu v-if="menu.children?.length" :key="String(menu.id || menu.path)">
          <template #title>
            <span>
              <svg class="iconfont" aria-hidden="true"><use :href="`#icon-${menu.icon}`" /></svg>
              {{ menu.name }}
            </span>
          </template>
          <a-menu-item v-for="child in menu.children" :key="child.path">
            <span>
              <svg class="iconfont" aria-hidden="true"><use :href="`#icon-${child.icon}`" /></svg>
              {{ child.name }}
            </span>
          </a-menu-item>
        </a-sub-menu>
        <a-menu-item v-else :key="menu.path">
          <span>
            <svg class="iconfont" aria-hidden="true"><use :href="`#icon-${menu.icon}`" /></svg>
            {{ menu.name }}
          </span>
        </a-menu-item>
      </template>
    </a-menu>
  </a-layout-sider>
</template>

<script setup>
import { computed, ref, watch } from 'vue'
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

const searchKey = ref('')

// 展开的父菜单：优先取 localStorage（刷新后保持），否则取当前路由的父菜单
const savedOpen = localStorage.getItem('OPEN_MENU_IDS')
const currentOpenKeys = ref(
  [String(savedOpen && savedOpen !== 'null' ? savedOpen : route.meta.pid || '')].filter(Boolean),
)

const selectedKeys = computed(() => [route.path])

const flatten = items =>
  items.flatMap(item => (item.children?.length ? [item, ...flatten(item.children)] : [item]))

// 菜单搜索：递归过滤，保留匹配的父菜单链
const filteredMenus = computed(() => {
  if (!searchKey.value) return props.menus
  const keyword = searchKey.value.toLowerCase()
  const filterTree = items =>
    items.reduce((acc, item) => {
      if (!item || !item.name) return acc
      const nameMatch = item.name.toLowerCase().includes(keyword)
      if (item.children?.length) {
        const children = filterTree(item.children)
        if (children.length > 0 || nameMatch) {
          acc.push({ ...item, children })
        }
      } else if (nameMatch) {
        acc.push(item)
      }
      return acc
    }, [])
  return filterTree(props.menus)
})

// 搜索激活时自动展开匹配的父菜单
watch(searchKey, val => {
  if (val) {
    currentOpenKeys.value = filteredMenus.value
      .filter(item => item.children?.length)
      .map(item => String(item.id))
  } else {
    currentOpenKeys.value = []
  }
})

const onOpenChange = openKeys => {
  currentOpenKeys.value = openKeys
}

const handleClick = ({ key }) => {
  const menu = flatten(props.menus).find(
    item => item.path === key || String(item.id) === String(key),
  )
  if (menu) {
    emit('clickMenu', menu)
  }
}
</script>

<style scoped lang="less">
.page-sider {
  height: 100%;
  overflow: auto;
  border-right: 1px solid #e5e7eb;
  background: #fff;
}

.menu-search {
  padding: 8px;
  border-bottom: 1px solid #e5e7eb;
}
</style>
