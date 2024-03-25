var on = false

setInterval(function () {
  on = !on
  LED1.write(on)
}, 500)
var wifiLed = D13
var button = D22
var dht11 = D19
pinMode(wifiLed, 'output')
pinMode(button, 'input_pullup')
pinMode(dht11, 'input_pulldown')

// const { wifiConfig,mqttServer,mqttOptions } = require('config.json')
// const { wifiConfig, mqttServer, mqttOptions } = require('config.js')
// const wifiConfig = require('config.js').wifiConfig
const wifiConfig1 = {
  ssid: 'crabapples-ios',
  password: {
    'password': '12345678'
  }
}
const wifiConfig = {
  'ssid': 'CMCC',
  'password': {
    'password': '10086'
  }
}
const mqttServer = '127.0.0.1'
const mqttOptions = {
  'client_id': 'ramdon',
  'keep_alive': 60,
  'port': 1883,
  'clean_session': true,
  'username': 'username',
  'password': 'password',
  'protocol_name': 'MQTT',
  'protocol_level': 4
}
// const mqttServer = require('config.js').mqttServer
// const mqttOptions = require('config.js').mqttOptions
var wifi = require('Wifi')
const fs = require('fs')
// var mqtt = require('MQTT.min.js').create(mqttServer, mqttOptions)
var mqtt = require('MQTT.js').create(mqttServer, mqttOptions)
var reConnectedTimer = null
var binkLedTimer = null
var maxReConnectTime = 30
E.setTimeZone(8)
// wifi.setSNTP('ntp.aliyun.com', 'CCT');

// var Sntp = require('sntp.js');
//
// // All options are optional
//
// var options = {
//   host: 'ntp.aliyun.com',  // Defaults to pool.ntp.org
//   port: 123,                      // Defaults to 123 (NTP)
//   timeout: 1000                   // Defaults to zero (no timeout)
// };
// // Request server time
// Sntp.time(options, function (err, time) {
//   if (err) {
//     console.log('Failed: ' + err.message);
//     return;
//   }
//   console.log('Local clock is off by: ' + time.t + ' milliseconds');
// });

// 初始化函数
function onInit() {
  console.log('------------------------------------>')
  // listenTapButton()
  listenTapButton1()
  connectWifi()
  startWebServer()
}

function startWebServer() {
  require('http').createServer((req, res) => {
    // console.log(E.openFile('index.html', 'r'));
    try {
      fs.readdirSync()
    } catch (e) { //'Uncaught Error: Unable to mount media : NO_FILESYSTEM'
      console.log('Formatting FS - only need to do once')
      E.flashFatFS({ format: true })
      console.log(fs.readdir())
    }
    res.writeHead(200)
    res.end('Hello World')
  }).listen(8080) // port 8080
}

function startDht11() {
  // pinMode(dht11, 'output')
  // pinMode(dht11, 'input_pulldown')
}

function listenTapButton() {
  var working = false
  setInterval(() => {
    if (!working) {
      if (digitalRead(button)) {
        let delay = true
        let success = false
        // 第一次延时用于消抖
        setTimeout(() => {
          if (digitalRead(button)) {
            // 第二次延时用于检测长按
            let longClickTime = 2000
            setTimeout(() => {
              delay = false
            }, longClickTime)
            let longClickCheck = setInterval(() => {
              if (delay) {
                if (!digitalRead(button)) {
                  clearInterval(longClickCheck)
                  working = false43
                }
              } else {
                success = true
                clearInterval(longClickCheck)
              }
              if (success) {
                console.log('long click button ~~~~~~')
              }
            }, 10)
          }
        }, 20)
      }
    }
  }, 20)
}

function listenTapButton1() {
  setWatch((e) => {
      console.log('--------------', e)
    }, D34,
    { repeat: true, edge: 'falling', debounce: 100 }
  )
}

// MQTT连接成功回调
mqtt.on('connected', function () {
  console.log('MQTT connect callback...success')
  console.log('MQTT 连接回调...连接成功')
  mqtt.subscribe('/controller-center')
})
// MQTT断开连接回调
mqtt.on('disconnected', function () {
  console.log('MQTT disconnected')
  console.log('MQTT 连接断开回调')
  wifi.disconnect()
})
// 收到消息回调
mqtt.on('message', function (pub) {
  console.log('MQTT message callback()')
  console.log('MQTT 收到消息')
  console.log('topic: ' + pub.topic)
  console.log('message: ' + pub.message)
})
// MQTT订阅回调
mqtt.on('subscribed', function (pub) {
  console.log('MQTT subscribed callback...success')
  console.log('MQTT 订阅主题成功')
})
// MQTT错误回调
mqtt.on('error', function (pub) {
  console.log('MQTT error callback()')
  console.log('MQTT 发生错误')
  console.log('error: ' + pub)
})

// wifi断开回调
wifi.on('disconnected', function () {
  console.log('network disconnect try reconnect...')
  console.log('尝试重新连接网络中....')
  mqtt.disconnect()
  if (!reConnectedTimer) {
    reConnectedTimer = setInterval(() => {

      console.log(`wifi reTryConnecting...[${maxReConnectTime}]`)
      console.log(`网络重新连接中,重试次数:[${maxReConnectTime}]`)
      connectWifi()
    }, 3000)
  }
})
// wifi连接回调
wifi.on('connected', function () {
  console.log('network connect success')
  console.log('网络连接成功')
  // console.log('启动时间同步',wifi.setSNTP('ntp.aliyun.com', '8'))
  stopReConnectedTimer()
  stopBlinkWifiLed()
  openWifiLed()
  getTestPage()
  maxReConnectTime = 30
  setTimeout(() => {
    console.log('MQTT connecting...')
    console.log('MQTT 连接中...')
    mqtt.connect()
  }, 2000)
})



// 连接wifi
function connectWifi() {
  console.log('wifi connecting...')
  if (maxReConnectTime <= 0) {
    console.log('wifi connect fail after 5 second ago restart device!')
    console.log('网络连接无法恢复,5秒钟后设备将自动重启!')
    setTimeout(() => {
      ESP32.reboot()
    }, 5000)
  }
  maxReConnectTime--
  if (!binkLedTimer) {
    blinkWifiLed()
  }
  setTimeout(() => {
    console.log('connecting...')
    console.log('网络连接中...')
    wifi.connect(wifiConfig.ssid, wifiConfig.password, function (err) {
      console.log('connect event()')
      if (err) {
        console.error('network connect fail:' + err)
        console.error('网络连接失败:' + err)
        setTimeout(() => {
          connectWifi()
        }, 3000)
      }
    })
  }, 3000)
}

// 打开wifi连接指示灯
function openWifiLed() {
  digitalWrite(wifiLed, true)
}

// wifi指示灯闪烁
function blinkWifiLed() {
  var successLed = true
  binkLedTimer = setInterval(() => {
    successLed = !successLed
    digitalWrite(wifiLed, successLed)
  }, 500)
}

// 停止wifi指示灯闪烁
function stopBlinkWifiLed() {
  clearInterval(binkLedTimer)
  binkLedTimer = null
}

// 停止wifi重连
function stopReConnectedTimer() {
  clearInterval(reConnectedTimer)
  reConnectedTimer = null
}

setInterval(() => {
  console.log(wifi.getStatus().station, wifi.getIP().ip)
  console.log(new Date())
  mqtt.publish('/device-live-topic', '心跳' + Math.random(),
    { qos: 2, retain: true, dup: false })
  getTestPage()
}, 3000)

function getTestPage() {
  // require('http').get('http://f.m.suning.com/api/ct.do', function (res) {
  //   res.on('data', (d) => {
  //     d.currentTime
  //     console.log('--->' + d)
  //   })
  // })
}
