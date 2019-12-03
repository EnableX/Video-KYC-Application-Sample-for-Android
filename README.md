# Video KYC: A Sample Android App with EnableX Android Toolkit

This is a Sample Android App demonstrates the use of EnableX (https://www.enablex.io) platform Server APIs and Android Toolkit to build 1-to-1 RTC (Real Time Communication) Application.  It allows developers to ramp up on app development by hosting on their own devices. 

> EnableX Developer Center: https://developer.enablex.io/

This basic Video KYC Application is developed using Android (The EnableX Android Toolkit). Using Paid Subscription, the Application records Video Session.

>The details of the supported set of web browsers can be found here:
https://developer.enablex.io/release-notes/#cross-compatibility

## 1. Demo

Visit Demo Zone (https://portal.enablex.io/demo-zone/) to request a Guided Demo or Demo Access to different type of application available there.

## 2. Installation

### 2.1 Pre-Requisites

#### 2.1.1 App Id and App Key 

* Register with EnableX [https://www.enablex.io] 
* Create your Application
* Get your App ID and App Key delivered to your Email


#### 2.1.2 Sample Android Client 

* Clone or download this Repository [https://github.com/EnableX/Video-KYC-Application-Sample-for-Android.git] 


#### 2.1.3 Sample App Server 

* Clone or download this Repository [https://github.com/EnableX/One-to-One-Video-Chat-Sample-Web-Application.git ] & follow the steps further 
* You need to use App ID and App Key to run this Service. 
* Your Android Client End Point needs to connect to this Service to create Virtual Room.
* Follow README file of this Repository to setup the Service.


#### 2.1.4 Configure Android Client 

* Open the App
* Go to WebConstants and change the following:
``` 
 String kBaseURL = "FQDN"      /* FQDN of of App Server */
 ```
  
## 3 Server API

EnableX Server API is a Rest API service meant to be called from Partners' Application Server to provision video enabled 
meeting rooms. API Access is given to each Application through the assigned App ID and App Key. So, the App ID and App Key 
are to be used as Username and Password respectively to pass as HTTP Basic Authentication header to access Server API.
 
For this application, the following Server API calls are used: 
* https://developer.enablex.io/latest/server-api/rooms-route/#get-rooms - To get list of Rooms
* https://developer.enablex.io/latest/server-api/rooms-route/#get-room-info - To get information of the given Room
* https://developer.enablex.io/latest/server-api/rooms-route/#create-token - To create Token for the given Room

To know more about Server API, go to:
https://developer.enablex.io/latest/server-api/


## 4 Android Toolkit

Android App to use Android Toolkit to communicate with EnableX Servers to initiate and manage Real Time Communications.  

* Documentation: https://developer.enablex.io/latest/client-api/android-toolkit/
* Download: https://developer.enablex.io/wp-content/uploads/Android_SDK_1.5.1/EnxRtcAndroid-release_1.5.1.zip
