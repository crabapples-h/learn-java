<template>
  <div>
    <vue-canvas-poster :widthPixels="0" :painting="painting" @success="success" @fail="fail"/>
    <button @click="download">下载</button>
    <div style="border: 1px solid red;height: 100vh;width: 100%" id="printable">
      <img :src="posterImg" alt="" style="width: 100%">

    </div>
  </div>
</template>
<script>

export default {
  name: 'qr-code',
  mixins: [],
  components: {},
  data() {
    return {
      painting: {},
      posterImg: "",
    }
  },
  mounted() {
    this.createShareCard()
  },
  methods: {
    /*生成二维码*/
    createShareCard() {
      let list = new Array(20).fill({id: '这个是id', name: '这个是名称'})
      console.clear()
      const itemHeight = 383.86
      const width = 2480
      const marginTop = 65
      const marginLeft = 100
      const height = (itemHeight + marginTop) * Math.floor(list.length / 2)

      console.log(width, height)
      let views = []
      for (let i = 0; i < list.length; i++) {
        // left: i % lineCount === 0 ? '80px' : undefined,
        //     right: i % lineCount === 1 ? '80px' : undefined,
        let lineCount = 2
        let line = Math.floor(i / lineCount)
        let itemWidth = 805.51
        let isLeft = i % lineCount === 0
        let isRight = i % lineCount === 1
        console.log(isLeft, isRight)
        // let itemWidth = 265
        // 265* 134
        let rect = {
          id: `parent${i}`,
          type: 'rect',
          css:
              {
                width: `${itemWidth}px`,
                color: '#f2f2f2',
                height: `${itemHeight}px`,
                // borderColor: '#f00',
                // borderWidth: '1px',
                // borderRadius: '1px',
                left: `${isLeft ? marginLeft : itemWidth + marginLeft * 2}px`,
                // right: isRight ? '80px' : undefined,
                // right: i % lineCount === 1 ? 0 : width / 2 + 'px',
                top: `${line * itemHeight + line * marginTop + marginTop}px`,
              },

        }
        let content = `第${i}条数据第${i}条数据第${i}条数据第${i}条数据第${i}条数据第${i}条数据第${i}条数据第${i}条数据第${i}条数据`
        let text = {
          type: 'text',
          text: content.length > 8 ? content.substring(0, 8) : content,
          css: [
            {
              // textDecoration: 'overline',
              width: `${itemWidth / 2}px`,
              fontSize: '40px',
              textIndent: '20px',
              top: [line * itemHeight + line * marginTop + marginTop + 'px', `parent${i}`, 0.45],
              left: isLeft ? [`0px`, `parent${i}`, 0.2] : [`${itemWidth + marginLeft * 2 / 2}px`, `parent${i}`, 0.2]

            },
          ]
        }
        // console.log(rect.css[0].left)
        // console.table(rect.css[0])

        let qrcode = {
          type: 'qrcode',
          content: 'https://github.com/sunniejs/vue-canvas-poster',
          css: {
            color: '#000',
            width: `${itemHeight * 0.7}px`,
            height: `${itemHeight * 0.7}px`,
            top: [line * itemHeight + line * marginTop + marginTop + 'px', `parent${i}`, 0.15],
            left: isLeft ? [`0px`, `parent${i}`, 0.7] : [`${itemWidth + marginLeft * 2 / 2}px`, `parent${i}`, 0.7]
          }
        }
        views.push(rect)
        views.push(text)
        views.push(qrcode)
      }
      this.painting = {
        width: `${width}px`,
        height: `${height}px`,
        // top: "0px",
        views: views
      }
      console.log(this.painting.width, this.painting.height)

    },
    printPage(printContent) {
      const printWindow = window.open('', '_blank');
      printWindow.document.write(printContent);
      printWindow.document.close();
      printWindow.focus();
      printWindow.print();
      printWindow.close();
    },
    /*生成成功回调*/
    success(src) {
      this.posterImg = src;
      // console.log(src)
      // setTimeout(() => {
      //   let aLink = document.createElement("a");
      //   aLink.style.display = "none";
      //   aLink.href = this.posterImg;
      //   aLink.download = "qrcode.jpg";
      //   document.body.appendChild(aLink);
      //   aLink.click();
      //   document.body.removeChild(aLink);
      //   this.$nextTick(() => {
      //     const printContent = document.getElementById('printable').innerHTML;
      //     this.printPage(printContent);
      //   }, 1000)
      // }, 2000)
    },
    /*生成失败回调*/
    fail(error) {
      console.log('生成海报失败')
      console.log(error)
    },
    /*下载*/
    download() {
      let aLink = document.createElement("a");
      aLink.style.display = "none";
      aLink.href = this.posterImg;
      aLink.download = "qrcode.jpg";
      document.body.appendChild(aLink);
      aLink.click();
      document.body.removeChild(aLink);


      this.$nextTick(() => {
        // 使用方法：
        // 假设你要打印的内容在一个id为"printable"的元素中
        const printContent = document.getElementById('printable').innerHTML;
        this.printPage(printContent);
      }, 1000)
    }
  }
}
</script>


<style>
</style>
