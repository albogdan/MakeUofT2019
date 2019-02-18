
#include <ESP8266WiFi.h>
#include <FirebaseArduino.h>

#include <SparkFun_ADXL345.h>         // SparkFun ADXL345 Library

#include <DHT.h>

#include <SPI.h>

#include <MFRC522.h>

#ifndef STASSID
//#define STASSID "Kooresh's iPhone"
//#define STAPSK  "kooresh112233"
#define STASSID "Pranshu"
#define STAPSK  "test1234"
#endif


#define SS_PIN 2
#define RST_PIN 3
MFRC522 mfrc522(SS_PIN, RST_PIN);   // Create MFRC522 instance


#define FIREBASE_AUTH ""
#define FIREBASE_HOST "fallnot.firebaseio.com"

#define DHTTYPE DHT11

const String DEVICE_ID = "1004186640";
const char* ssid     = STASSID;
const char* password = STAPSK;

const int TOUCH_BUTTON_PIN = 15; //Input pin for touch state
const int DHT_PIN = 10;         //Input pin for the DHT temp sensor



DHT dht(DHT_PIN, DHTTYPE); // 11 works fine for ESP8266
float temp_f;  // Values read from sensor
unsigned long previousMillis = 0;        // will store last temp was read
const long interval = 2000;              // interval at which to read sensor
 


//Init the Accelerometer on I2C, SDA on D2, SCL on D1
ADXL345 adxl = ADXL345();             // USE FOR I2C COMMUNICATION
  
void setup() {
  ESP.wdtDisable();
  Serial.begin(115200);
  WiFi.mode(WIFI_STA);
  pinMode(LED_BUILTIN, OUTPUT);
  digitalWrite(LED_BUILTIN, HIGH);
  WiFi.begin(ssid, password);
  delay(200);
  adxl.powerOn();            //Power on the ADXL345 Accelerometer
  adxl.setRangeSetting(8);  // Give the range settings
                                      // Accepted values are 2g, 4g, 8g or 16g
                                      // Higher Values = Wider Measurement Range
                                      // Lower Values = Greater Sensitivity


  while(WiFi.status() != WL_CONNECTED){
    Serial.print(".");
    delay(500);
  }
  Serial.println();
  Serial.print("connected: ");

  Serial.println(WiFi.localIP());

  pinMode(TOUCH_BUTTON_PIN, INPUT);  //Set touch button as input

  dht.begin();
  
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
  delay(1000);
  Serial.println("Firebase connected");
  
  SPI.begin();      // Initiate  SPI bus
  mfrc522.PCD_Init();   // Initiate MFRC522
  Serial.println("SPI and RFID initialized");

  Serial.println("Tap card to activate");

  activate();

}

int xAccel,yAccel,zAccel, xAccelPrev,yAccelPrev,zAccelPrev,touchInput, touchInputPrev;
float temp_fPrev;
String location;


void activate(){
  // Look for new cards
  while ( ! mfrc522.PICC_IsNewCardPresent()) 
  {
    ESP.wdtFeed();
  }
  // Select one of the cards
  while ( ! mfrc522.PICC_ReadCardSerial()) 
  { ESP.wdtFeed();
  }
  //Show UID on serial monitor
  Serial.print("UID tag :");
  String content= "";
  byte letter;
  for (byte i = 0; i < mfrc522.uid.size; i++) 
  {
     Serial.print(mfrc522.uid.uidByte[i] < 0x10 ? " 0" : " ");
     Serial.print(mfrc522.uid.uidByte[i], HEX);
     content.concat(String(mfrc522.uid.uidByte[i] < 0x10 ? " 0" : " "));
     content.concat(String(mfrc522.uid.uidByte[i], HEX));
  }
  Serial.println();
  Serial.print("Message : ");
  content.toUpperCase();
  
  if (content.substring(1) == "40 7D DD A4") //change here the UID of the card/cards that you want to give access
  {
    Serial.println("Authorized access");
    Serial.println();
  }

  
}

void loop() {




  xAccelPrev = xAccel;
  yAccelPrev = yAccel;
  zAccelPrev = zAccel;
  temp_fPrev = temp_f;
  touchInputPrev = touchInput;
  touchInput = digitalRead(TOUCH_BUTTON_PIN);
  Serial.print("TouchIN: ");
  Serial.println(touchInput);
  temp_f = dht.readTemperature();

  adxl.readAccel(&xAccel, &yAccel, &zAccel);
//  String location = DEVICE_ID;
//  String readStr = Serial.readString();
  if(zAccel != zAccelPrev){
    location = DEVICE_ID +  "/AccelZ";
    Firebase.setInt(location, zAccel); 
    Serial.print("Updated zAccel:");
    Serial.println(zAccel);
  }
  Serial.print("NOW: ");
  Serial.print(touchInput);
  Serial.print(" PREV: ");
  Serial.println(touchInputPrev);
  if(touchInput != touchInputPrev){
    location = DEVICE_ID +  "/Touch";
    Firebase.setInt(location, touchInput);     
    Serial.print("Updated touch input: ");
    Serial.println(touchInput);
  }
  if(temp_f != temp_fPrev){
    location = DEVICE_ID +  "/Temp";
    Firebase.setFloat(location, temp_f);     
    Serial.print("Updated temp: ");
    Serial.println(temp_f);
  }


  delay(500);
  ESP.wdtFeed();
}
