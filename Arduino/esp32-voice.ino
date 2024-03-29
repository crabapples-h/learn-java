#define RED 15

void setup() {
  Serial.begin(115200);
  pinMode(RED, OUTPUT);
  Serial.println("hello,world");
}

void loop() {
  for (int dutyCycle = 0; dutyCycle < 255; dutyCycle++) {
    analogWrite(RED, dutyCycle);
    delay(20);
  }
  for (int dutyCycle = 0; dutyCycle < 255; dutyCycle++) {
    analogWrite(RED, 255 - dutyCycle);
    delay(20);
  }
  analogWrite(RED, 0);
  delay(1000);


  // for(int dutyCycle = 1023; dutyCycle > 0; dutyCycle--){
  //   analogWrite(RED, dutyCycle);
  //   delay(1);
  // }
}
