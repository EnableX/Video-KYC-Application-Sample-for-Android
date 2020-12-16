package com.enablex.remitonline.webcommunication;

public class WebConstants {

    /* To try the app with Enablex hosted service you need to set the kTry = true */
    public  static  final  boolean kTry = true;

    /*Your webservice host URL, Keet the defined host when kTry = true */

    public static final String baseURL = "https://demo.enablex.io/";

    /*The following information required, Only when kTry = true, When you hosted your own webservice remove these fileds*/

    /*Use enablec portal to create your app and get these following credentials*/
    public static final String kAppId = "5ef5b31690ef80b4300b0bd2";
    public static final String kAppkey = "uJehyWaAu4uvyTupeJyJuHu6ygyYaGu2yzuq";


    public static final String createRoomURL = "createRoom/";
    public static final int createRoomCode = 1;
    public static final String createTokenURL = "createToken/";
    public static final int createTokenCode = 2;
    public static final String uploadImageURL = "createSnapShot/";
    public static final int uploadImageCode = 3;
}
