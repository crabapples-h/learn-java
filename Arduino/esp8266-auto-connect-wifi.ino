#include <ESP8266WiFi.h>

// airkiss协议配网
void smartConfig() {
  WiFi.mode(WIFI_STA);
  Serial.println("等待网络连接");
  delay(2000);
  // 等待配网
  WiFi.beginSmartConfig();
  while (1) {
    Serial.print(".");
    delay(1000);
    if (WiFi.smartConfigDone()) {
      Serial.println();
      Serial.printf("SSID:%s\r\n", WiFi.SSID().c_str());
      Serial.printf("PSW:%s\r\n", WiFi.psk().c_str());
      WiFi.setAutoConnect(true);  // 设置自动连接
      break;
    }
  }
  delay(2000);
  Serial.println();
  Serial.println("网络连接成功");
  Serial.print("LocalIP:");
  Serial.print(WiFi.localIP());
  Serial.print(" ,GateIP:");
  Serial.println(WiFi.gatewayIP());
}

// 自动联网，成功配对以后设备会记忆密码，通电自动连接
bool AutoConfig() {
  WiFi.begin();
  for (int i = 0; i < 10; i++) {
    int wstatus = WiFi.status();
    if (wstatus == WL_CONNECTED) {
      Serial.println("WIFI SmartConfig Success");
      Serial.printf("SSID:%s", WiFi.SSID().c_str());
      Serial.printf(", PSW:%s\r\n", WiFi.psk().c_str());
      Serial.print("LocalIP:");
      Serial.print(WiFi.localIP());
      Serial.print(" ,GateIP:");
      Serial.println(WiFi.gatewayIP());
      return true;
    } else {
      Serial.print("WIFI AutoConfig Waiting......");
      Serial.println(wstatus);
      delay(1000);
    }
  }
  Serial.println("WIFI AutoConfig Faild!");
  return false;
}



void setup() {
  pinMode(BUILTIN_LED, OUTPUT);
  Serial.begin(115200);
  Serial.println();
  if (!AutoConfig()) {
    smartConfig();
  }
}

void loop() {
}
