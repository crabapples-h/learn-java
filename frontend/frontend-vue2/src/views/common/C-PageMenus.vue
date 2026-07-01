<template>
  <a-layout-sider theme="light">
    <div style="padding: 8px">
      <a-input v-model="searchKey" placeholder="搜索菜单" allow-clear>
        <a-icon slot="prefix" type="search" />
      </a-input>
    </div>
    <a-menu mode="inline" :theme="theme" style="height: calc(100% - 44px)"
            :default-open-keys="currentOpenKeys"
            :default-selected-keys="selectMenuIds"
            @openChange="onOpenChange">
      <template v-for="item in filteredMenus">
        <a-menu-item v-if="!item.children" :key="item.key" @click="click(item)">
          <svg class="iconfont" aria-hidden="true">
            <use :xlink:href="'#icon-'+item.icon"></use>
          </svg>
          <span>{{ item.name }}</span>
        </a-menu-item>
        <sub-menu v-else :key="item.id" :menu-item="item" @clickMenu="click"/>
      </template>
    </a-menu>
  </a-layout-sider>
</template>

<script>
import {Menu} from 'ant-design-vue'

const SubMenu = {
  template: `
    <a-sub-menu :key="menuItem.id" v-bind="$props" v-on="$listeners">
        <span slot="title">
            <svg class="iconfont" aria-hidden="true">
            <use :xlink:href="'#icon-'+menuItem.icon"></use>
          </svg>
          <span>{{ menuItem.name }}</span>
        </span>
      <template v-for="item in menuItem.children">
        <a-menu-item v-if="!item.children" :key="item.id" @click="click(item)">
          <svg class="iconfont" aria-hidden="true">
            <use :xlink:href="'#icon-'+item.icon"></use>
          </svg>
          <span>{{ item.name }}</span>
        </a-menu-item>
        <sub-menu v-else :key="item.id" :menu-item="item"/>
      </template>
    </a-sub-menu>
  `,
  name: 'SubMenu',
  // must add isSubMenu: true
  isSubMenu: true,
  props: {
    ...Menu.SubMenu.props,
    clickMenu: {
      type: Function
    },
    // Cannot overlap with properties within Menu.SubMenu.props
    menuItem: {
      type: Object,
      default: () => ({}),
    },
  },
  mounted() {
  },
  methods: {
    click(e) {
      localStorage.setItem('OPEN_MENU_IDS', e.pid)
      localStorage.setItem('SELECT_MENU_IDS', e.id)
      this.$emit('clickMenu', e)
    },
  }
};

export default {
  components: {
    'sub-menu': SubMenu,
  },
  isRootMenu: false,
  props: {
    theme: {
      type: String,
    },
    menus: {
      type: Array,
      required: true,
      default: () => {
        return []
      }
    },
    clickMenu: {
      type: Function,
    },
  },
  computed: {
    // 当前选中的菜单
    selectMenuIds() {
      let SELECT_MENU_IDS = [localStorage.getItem('SELECT_MENU_IDS')]
      SELECT_MENU_IDS = SELECT_MENU_IDS.filter(e => e != null && e !== 'null')
      return SELECT_MENU_IDS
    },
    // 当前展开的菜单
    currentOpenKeys() {
      if (this.currentInternalKeys.length) {
        return this.currentInternalKeys
      }
      let OPEN_MENU_IDS = [localStorage.getItem('OPEN_MENU_IDS')]
      OPEN_MENU_IDS = OPEN_MENU_IDS.filter(e => e != null && e !== 'null')
      if (!OPEN_MENU_IDS.length) {
        OPEN_MENU_IDS = [...this.selectMenuIds]
      }
      return OPEN_MENU_IDS
    },
    filteredMenus() {
      if (!this.searchKey) return this.menus
      const keyword = this.searchKey.toLowerCase()
      const filterTree = (items) => {
        return items.reduce((acc, item) => {
          if (!item || !item.name) return acc
          const nameMatch = item.name.toLowerCase().includes(keyword)
          if (item.children) {
            const filteredChildren = filterTree(item.children)
            if (filteredChildren.length > 0 || nameMatch) {
              acc.push({...item, children: filteredChildren})
            }
          } else if (nameMatch) {
            acc.push(item)
          }
          return acc
        }, [])
      }
      return filterTree(this.menus)
    }
  },
  data() {
    return {
      searchKey: '',
      currentInternalKeys: [],
    };
  },
  watch: {
    searchKey(val) {
      if (val) {
        this.currentInternalKeys = this.filteredMenus
          .filter(item => item.children && item.children.length > 0)
          .map(item => String(item.id))
      } else {
        this.currentInternalKeys = []
      }
    }
  },
  methods: {
    onOpenChange(openKeys) {
      this.currentInternalKeys = openKeys
    },
    click(e) {
      this.$emit('clickMenu', e)
    },
  },
  mounted() {
  }
};
</script>
