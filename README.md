# FallNot - MakeUofT2019

## Inspiration
We all realized that in situations where it is necessary to track the safety of individuals in a group trek by knowing if they are not hurt, have a healthy body temperature, and are not very far from the group (desired location). So, we decided to make a wristband for the purpose of a large, group trek that can help the group locate themselves and make sure everyone is safe.

## What it does
Our simple system allows you to create a group of individuals whose safety you want to track over time. The system allows everyone to monitor the group's status through a mobile application and the users can interact with the system through a wristband that has an accelerometer and a touch sensor. It consists of two parts - a mobile application to keep track of everything, and a microcontroller application which would be uploaded to all microcontrollers (i.e., wristbands) that a user has and paired with the mobile application. 

A demonstration video can be seen [here](https://www.youtube.com/watch?v=5iqh7y6gNkA)

## How we built it
For processing the wristband sensor data and connecting to the database, we used an ESP8266, which can also be connected to each other in a mesh topology for scaling purposes in a remote setting with a scarce network. Behind the scenes, we used Google Firebase to serve as a cloud platform to support the mobile application and for the sensors to send data to. If there is an anomaly (fall or temperature rise to dangerous levels) detected for any of the group members, an alert will be automatically be sent to everyone else. If there our accelerometer detects that a person has fallen an alert will be sent right away to the phone app, and the user has the option to press the touch sensor to call emergency services.

## Challenges we ran into
We first started with developing our project on the Telus IoT Starter Kit, but we, unfortunately, received a mismatched package with a burnt board. After hours of debugging, we decided to switch from that dev board to ESP8266 and from Microsoft Azure to Google Firebase to accelerate development. While working with the ESP8266, all our sensors were not working together because of power constraints, and we were having difficulties reading latitude and longitude from the database because we had to account for the non-string data type and this made making markers appear across the map difficult.

## Accomplishments that we're proud of
It is the first hardware hackathon for all of us and it was a fresh experience to work on a project in 24 hours and especially catching up with our original idea after a long delay debugging at the start. We had little experience with any of the cloud-platforms to start with, but now we learned how to setup Microsoft Azure IoT devices, and have a fully functioning Google Firebase that works with the ESP8266 and with our Android app.

## What we learned
One valuable lesson that we learned was that we should pull the plug on an idea, or fork to trying out a different method of achieving the goal if it does not work after a significant amount of time. Being too stuck on a single idea can result in losing a lot of precious time.


## What's next for FallNot
We hope to take FallNot to the next level by filling in all the places in which we did not have enough time to fully explore due to the nature of a hackathon. In the future, we could integrate and miniaturize the system to fit nicely in a wristband, add multi-platform support application, and reduce cost per unit by utilizing mesh networks instead of GPRS, to enable connectivity in remote areas. We can also develop a communication protocol, much like a walkie-talkie that can prove to be very useful in a trek setting.

### Built with
Google Cloud Platform(GCP), Firebase, Android Studio (Java), Arduino (C++) and love
