<!--
  多图片预览组件
-->
<template>
  <ul>
    <li style="list-style: none" ref="images" class="images">
      <img :alt="alt" :style="{width: size}" :src="item" v-for="item in imageList" @click="handlePreview" />
    </li>
  </ul>
</template>

<script>
import "viewerjs/dist/viewer.css";
import Viewer from "viewerjs";

export default {
  name: "c-preview",
  mixins: [],
  props: {
    alt: {
      type: String,
      default: ""
    },
    src: {
      // 按理说应该是字符串，为了兼容不报错暂时多几种数据类型，后期修改
      type: [String,Object,Array],
      default: ""
    },
    size: {
      type: [Number, String],
      default: "60px"
    }
  },
  watch: {
    src: {
      handler: function(val, oldVal) {
        if(typeof(val)=== 'string'){
          this.imageList = val.split(",");
          this.image = this.imageList.length ? this.imageList[0] : "";
        }
      },
      immediate: true
    }
  },
  data() {
    return {
      image: "",
      imageList: []
    };
  },
  model: {},
  methods: {
    async handlePreview() {
      this.$nextTick(() => {
        let element = this.$refs.images;
        const viewer = new Viewer(element, {
          url: "src",
          title: function(image) {
            return image.alt + " (" + (this.index + 1) + "/" + this.length + ")";
          },
          navbar: true,
          fullscreen: false,
          viewed() {
            // viewer.zoomTo(1);
          }
        });
      });
    }
  }

};
</script>

<style scoped>
.images > img {
  display: none;
}

.images > img:first-child {
  display: inline-block;
}
</style>
