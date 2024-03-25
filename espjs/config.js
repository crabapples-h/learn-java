const wifiConfig1 = {
  'ssid': 'CMCC-kEPN',
  'password': {
    'password': 'deeb7744'
  }
}
const wifiConfig = {
  ssid: 'crabapples-ios',
  password: {
    'password': '12345678'
  }
}
const mqttServer = 'crabapples.site'
const mqttOptions = {
  'client_id': 'random',
  'keep_alive': 60,
  'port': 1883,
  'clean_session': true,
  'username': 'esp8266-01',
  'password': 'esp8266-01',
  'protocol_name': 'MQTT',
  'protocol_level': 4
}

export { wifiConfig1, wifiConfig, mqttServer, mqttOptions }
