<!--
  多图片预览组件
  author:Ms.He
  date:2024-01-27
  email:crabapples.cn@gmail.com
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
      type: String,
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
        this.imageList = val.split(",");
        this.image = this.imageList.length ? this.imageList[0] : "";
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
  display: block;
}
</style>
