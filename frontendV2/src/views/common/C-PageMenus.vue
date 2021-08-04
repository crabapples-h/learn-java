<template>
  <div>
    <a-layout-sider>
      <a-menu style="width: 200px;height: 100%" mode="inline">
        <a-sub-menu :key="item.key" v-for="item in menus" v-if="item.children && item.children.length">
          <span slot="title"><a-icon :type="item.icon"/><span>{{ item.name }}</span></span>
          <a-sub-menu :key="item.key" v-if="item.children && item.children.length" v-for="item in item.children">
            <span slot="title"><a-icon :type="item.icon"/><span>{{ item.name }}</span></span>
            <a-menu-item :key="item.key" v-for="item in item.children" @click="click(item)">
              <a-icon :type="item.icon"/>
              <span>{{ item.name }}</span>
            </a-menu-item>
          </a-sub-menu>
          <a-menu-item :key="item.key" v-else @click="click(item)">
            <a-icon :type="item.icon"/>
            <span>{{ item.name }}</span>
          </a-menu-item>
        </a-sub-menu>
        <a-menu-item :key="item.key" v-else @click="click(item)">
          <a-icon :type="item.icon"/>
          <span>{{ item.name }}</span>
        </a-menu-item>
      </a-menu>
    </a-layout-sider>
  </div>
</template>

<script>

export default {
  name: "C-PageMenus",
  props: {
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
  data() {
    return {};
  },
  activated() {
  },
  mounted() {
  },
  methods: {
    click(e) {
      this.$emit("clickMenu", e)
    }
  }
}
</script>

<style scoped lang="less">
@import "~@public/color.less";

.title {
  font-size: 20px;
  color: #fff;
  font-weight: 700;
}

.ant-layout-header {
  background: @primary-color;
  color: #fff;
  height: 7vh;
  line-height: 7vh;
}

.ant-layout-footer {
  background: @primary-color;
  color: #fff;
  height: 10vh;
  line-height: 7vh;
}

.ant-layout-sider {
  width: 100%;
  height: 83vh;
  background: #fff;
}

.ant-layout-content {
  box-shadow: inset 0 0 5px fade(@primary-color, 20%);
  padding: 12px;
  background: #fff;
  min-height: 120px;
  height: 83vh;
  overflow: auto;
}

/*滚动条整体样式*/
.ant-layout-content::-webkit-scrollbar {
  width: 10px; /*高宽分别对应横竖滚动条的尺寸*/
  height: 1px;
  margin-right: 10px;
  opacity: 0.2;
}

/*滚动条里面小方块(滑块 )*/
.ant-layout-content::-webkit-scrollbar-thumb {
  border-radius: 10px;
  -webkit-box-shadow: inset 0 0 5px @primary-color;
  background: fade(@primary-color, 20%);
  opacity: 0.2;
}

/*滚动条里面轨道(背景)*/
.ant-layout-content::-webkit-scrollbar-track {
  -webkit-box-shadow: inset 0 0 5px @primary-color;
  border-radius: 10px;
  background: fade(@primary-color, 20%);
  opacity: 0.2;
}
</style>
