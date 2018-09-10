package com.app.base.utils.net.parse;


import com.app.base.BaseApplication;
import com.app.base.constant.Constant;
import com.app.base.utils.net.callback.SimpleCallBack;
import com.bryan.common.utils.BroadcastManager;
import com.bryan.common.utils.ToastMgr;

/**
 * Created by Administrator on 2017/3/21 0021.
 */

public abstract class WySimpleCallBack extends SimpleCallBack {

    @Override
    public void onUnlogin(String msg) {
        super.onUnlogin(msg);
        ToastMgr.show("登陆失效，请重新登陆");
        BroadcastManager.getInstance(BaseApplication.getGlobalContext()).sendBroadcast(Constant.Action.Quit);
    }

}
