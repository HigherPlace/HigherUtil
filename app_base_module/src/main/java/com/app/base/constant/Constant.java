package com.app.base.constant;


/**
 * Created by bryan on 2017/12/5 0005.
 */

public interface Constant {
    //设备类型，1-表示Android,2-表示IOS,3-表示Web,4-微信服务号
    String DeviceTag = "1";
    //    String AppId = "2e8e82158a0f4a6fa69c2718a75cbe03";
//    String Base_Url = BuildConfig.BASE_URL + "/api";

    String WeatherSuccess = "ok";


    String WebSocketUrl = "ws://192.168.10.118:8080/websocket/android";

    interface Url {

//        String P


    }

    /**
     * api参数
     */
    interface Param {

    }


    /**
     * 界面跳转参数携带
     */
    interface Key {

    }

    /**
     * 广播的Action
     */
    interface Action {
        String Quit = "app_quit";
        String Update_Param = "UpdateParam";
    }

    interface Extra {
        String Data = "data";
        String Room = "room";
    }

    interface RequestCode {

    }


    interface File {
        String Param = "param";

    }

    interface Permission {

    }


}

