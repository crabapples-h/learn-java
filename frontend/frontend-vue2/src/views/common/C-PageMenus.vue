<template>
  <a-layout-sider theme="light">
    <a-menu mode="inline" :theme="theme" style="height: 100%"
            :default-open-keys="openMenuIds"
            :defaultSelectedKeys="selectMenuIds">
      <template v-for="item in menus">
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
import { Menu } from 'ant-design-vue'

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
    selectMenuIds() {
      let SELECT_MENU_IDS = [localStorage.getItem('SELECT_MENU_IDS')]
      SELECT_MENU_IDS = SELECT_MENU_IDS.filter(e => e != null)
      console.debug('selectMenuIds', SELECT_MENU_IDS)
      return SELECT_MENU_IDS
    },
    openMenuIds() {
      let OPEN_MENU_IDS = [localStorage.getItem('OPEN_MENU_IDS')]
      OPEN_MENU_IDS = OPEN_MENU_IDS.filter(e => e != null)
      console.debug('openMenuIds', OPEN_MENU_IDS)
      return OPEN_MENU_IDS
    }
  },
  data() {
    return {
    };
  },
  methods: {
    click(e) {
      console.log('内部点击菜单', e)
      localStorage.setItem('OPEN_MENU_IDS', e.pid)
      localStorage.setItem('SELECT_MENU_IDS', e.id)
      this.$emit('clickMenu', e)
    },
  },
  mounted() {
  }
};
</script>
