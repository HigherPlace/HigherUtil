package com.app.base.constant;


/**
 * Created by bryan on 2017/12/5 0005.
 */

public interface Constant {
    //设备类型，1-表示Android,2-表示IOS,3-表示Web,4-微信服务号
    String DeviceTag = "1";
    //    String AppId = "2e8e82158a0f4a6fa69c2718a75cbe03";
//    String Base_Url = BuildConfig.BASE_URL + "/api";

    String Provider_File_Name = ".fileprovider";


    interface Url {

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
        String Quit = "higher_util_app_quit";
        String Update_Param = "UpdateParam";
    }

    interface Extra {
        String Data = "data";
        String Bean = "bean";
    }

    interface RequestCode {

    }

    interface File {
        String Param = "param";

    }

    interface Permission {

    }


}

