<template>
  <div>
    <a-space>
      <a-button type="primary" @click="scanDevice" :disabled="disable">开始扫描设备</a-button>
    </a-space>
    <p v-for="item in messages">{{ item }}</p>
  </div>

</template>

<script>
import system from '@/mixins/system'

export default {
  name: "SseExample",
  mixins: [system],
  data() {
    return {
      url: {
        connect: '/api/sse/connect',
        test: '/api/sse/send',
      },
      clientId: '',
      disable: false,
      messages: [],
      timer: null,
      connected: null,
      server: null,
      service: null,
      device: null,
      // uuid:'0000180c-0000-1000-8000-00805f9b34fb',
      wave: {
        'a': [
          '0A0A0A0A00000000',
          '0A0A0A0A14141414',
          '0A0A0A0A28282828',
          '0A0A0A0A3C3C3C3C',
          '0A0A0A0A50505050',
          '0A0A0A0A64646464',
          '0A0A0A0A64646464',
          '0A0A0A0A64646464',
          '0A0A0A0A00000000',
          '0A0A0A0A00000000',
          '0A0A0A0A00000000',
          '0A0A0A0A00000000'
        ],
        'b': [
          '4A4A4A4A64646464',
          '4545454564646464',
          '4040404064646464',
          '3B3B3B3B64646464',
          '3636363664646464',
          '3232323264646464',
          '2D2D2D2D64646464',
          '2828282864646464',
          '2323232364646464',
          '1E1E1E1E64646464',
          '1A1A1A1A64646464',
          '0A0A0A0A64646464',
          '0A0A0A0A64646464',
          '0A0A0A0A64646464',
          '0A0A0A0A64646464',
          '0A0A0A0A64646464',
          '0A0A0A0A64646464'
        ],
        'c': [
          '0A0A0A0A00000000',
          '0A0A0A0A32323232',
          '0A0A0A0A64646464',
          '0A0A0A0A46464646',
          '1515151500000000',
          '1515151532323232',
          '1515151564646464',
          '1515151546464646',
          '2020202000000000',
          '2020202032323232',
          '2020202064646464',
          '2020202064646464',
          '2B2B2B2B00000000',
          '2B2B2B2B32323232',
          '2B2B2B2B64646464',
          '2B2B2B2B64646464',
          '3636363600000000',
          '3636363632323232',
          '3636363664646464',
          '3636363646464646'
        ]
      },
      uuid: '68d2b017-7cee-db86-9f40-5282759347c5',
      serviceId: '0000180c-0000-1000-8000-00805f9b34fb',
      channelId: '0000150a-0000-1000-8000-00805f9b34fb'
    }
  },
  mounted() {
  },
  watch: {
    connected(val, oldVal) {
      if (val) {
        console.log("设备已连接")
        this.$message.success('设备已连接')
        this.sendData(this.serviceId)
      } else {
        console.log("设备断开连接")
        this.$message.warn('设备断开连接')
        this.device.gatt.connect().then(server => {

          if (server.connected) {
            this.server = server
            this.connected = true
          }
        })
      }
    }
  },
  methods: {
    hexStringToUint8Array(hexString) {
      if (hexString.length % 2 !== 0) {
        console.error('Hex字符串长度必须是偶数')
      }
      const array = new Uint8Array(hexString.length / 2);
      for (let i = 0; i < hexString.length; i += 2) {
        array[i / 2] = parseInt(hexString.substr(i, 2), 16);
      }
      return array;
    },
    sendData() {
      // let currentIndex = count % coyote3wave[selectedOption].length
      let valueA = 'B0000000' + this.wave['a'][currentIndex] + wave['a'][currentIndex]
      // let characteristicIdA = '0000150a-0000-1000-8000-00805f9b34fb' // 3.0设备蓝牙特性的波形写入 UUID (AB通道相同)
      console.log('开始发送数据');
      const channel = this.service.getCharacteristic(this.channelId)
      channel.then(channel => {
        setInterval(() => {
          channel.writeValue(this.hexStringToUint8Array(valueA));
        }, 500)
      })
    },
    scanDevice() {
      console.clear()
      console.log("开始扫描设备")
      // console.log(navigator)
      if (typeof navigator.bluetooth === 'undefined') {
        console.log('您的浏览器不支持蓝牙API，请更换为Chrome浏览器')
        return
      }
      navigator.bluetooth.requestDevice({
        // acceptAllDevices: true,
        filters: [{namePrefix: '47L'}],
        optionalServices: [this.uuid],
      }).then(device => {
        console.log("选择的设备:", device)
        this.device = device
        this.connected = device.gatt.connected
        console.log("设备连接状态:", this.connected)
      })
    },
  }
}

</script>

<style scoped>


</style>