# Video KYC: An Android App with EnableX Android Toolkit

Video KYC: A Sample Android App with EnableX Android Toolkit

This is a Sample Android Application that demonstrates the use of EnableX platform Video APIs (https://developer.enablex.io/docs/references/apis/video-api/index/) and Android Toolkit (https://developer.enablex.io/docs/references/sdks/video-sdk/android-sdk/index/) to build 1-to-1 RTC (Real Time Communication) Application. It allows developers to ramp up on app development by hosting on their own devices.

EnableX Developer Center: https://developer.enablex.io/

This basic Video KYC Application is developed using Android (The EnableX Android Toolkit). Using Paid Subscription, the Application records Video Session.

The details of the supported set of web browsers can be found here: https://developer.enablex.io/docs/quickstart/video/browser-compatibility/index

>The details of the supported set of web browsers can be found here:
https://developer.enablex.io/docs/quickstart/video/browser-compatibility/index

## 1. Trial

Sign up for a free trial https://www.enablex.io/free-trial/ or try our multiparty video chat https://try.enablex.io/.

## 2. How to get started

### 2.1 Prerequisites

#### 2.1.1 App Id and App Key 

* Register with EnableX [https://www.enablex.io/free-trial/] 
* Create your Application
* Get your App ID and App Key delivered to your email


#### 2.1.2 Sample Android Client 

* Clone or download this Repository [https://github.com/EnableX/Video-KYC-Application-Sample-for-Android.git] 


#### 2.1.3 Test Application Server

You need to setup an Application Server to provision Web Service API for your Android Application to enable the Video Session. 

To help you to try our Android Application quickly, without having to set up Applciation Server, this Application is shipped pre-configured to work in a "try" mode with EnableX hosted Application Server i.e. https://demo.enablex.io. 

Our Application Server restricts a single Session durations to 10 minutes and allows 1 moderator and not more than 3 participants in a Session.

Once you tried EnableX Android Sample Application, you may need to setup your own  Application Server and verify your Application to work with your Application Server.  Refer to point 3 for more details on this.


#### 2.1.4 Configure Android Client 

* Open the App
* Go to WebConstants and change the following:
``` 
 /* To try the App with Enablex Hosted Service you need to set the kTry = true When you setup your own Application Service, set kTry = false */
     
     public  static  final  boolean kTry = true;
     
 /* Your Web Service Host URL. Keet the defined host when kTry = true */
 
     String kBaseURL = "https://demo.enablex.io/"
     
 /* Your Application Credential required to try with EnableX Hosted Service
     When you setup your own Application Service, remove these */
     
     String kAppId = ""  
     String kAppkey = ""  
 ```
  
  ### 2.2 Test

  #### 2.2.1 Open the App

  * Open the App in your Device. You get a form to enter Credentials i.e. Name & Room Id.
  * You need to create a Room by clicking the "Create Room" button.
  * Once the Room Id is created, you can use it and share with others to connect to the Virtual Room to carry out an RTC Session either as a Moderator or a Participant (Choose applicable Role in the Form).

  Note: Only one user with Moderator Role allowed to connect to a Virtual Room while trying with EnableX Hosted Service. Your Own Application Server may allow upto 5 Moderators.
  
  Note:- In case of emulator/simulator your local stream will not create. It will create only on real device.
    
## 3. Setup Your Own Application Server

You may need to setup your own Application Server after you tried the Sample Application with EnableX hosted Server. We have different variants of Application Server Sample Code. Pick one in your preferred language and follow instructions given in respective README.md file.

* NodeJS: [https://github.com/EnableX/Video-Conferencing-Open-Source-Web-Application-Sample.git]
* PHP: [https://github.com/EnableX/Group-Video-Call-Conferencing-Sample-Application-in-PHP]

Note the following:
* You need to use App ID and App Key to run this Service.
* Your Android Client End Point needs to connect to this Service to create Virtual Room and Create Token to join the session.
* Application Server is created using EnableX Server API, a Rest API Service helps in provisioning, session access and post-session reporting.  

To know more about Server API, go to:
https://developer.enablex.io/docs/guides/video-guide/sample-codes/video-calling-app/#demo-application-server


## 4. Android Toolkit

This Sample Applcation uses EnableX Android Toolkit to communicate with EnableX Servers to initiate and manage Real Time Communications. Please update your Application with latest version of EnableX Android Toolkit as and when a new release is available.

* Documentation: https://developer.enablex.io/docs/references/sdks/video-sdk/android-sdk/index/
* Download Toolkit: https://developer.enablex.io/docs/references/sdks/video-sdk/android-sdk/index/

## 5. Demo

EnableX provides hosted Vemo Application of different use-case for you to try out.

1. Try a quick Video Call: https://demo.enablex.io/
2. Sign up for a free trial https://www.enablex.io/free-trial/
