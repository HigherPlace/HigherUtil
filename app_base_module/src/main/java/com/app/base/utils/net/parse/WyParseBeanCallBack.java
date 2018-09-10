package com.app.base.utils.net.parse;

import com.app.base.BaseApplication;
import com.app.base.constant.Constant;
import com.app.base.utils.net.callback.ParseBeanCallBack;
import com.bryan.common.utils.BroadcastManager;
import com.bryan.common.utils.ToastMgr;

public abstract class WyParseBeanCallBack<T> extends ParseBeanCallBack<T> {

    protected WyParseBeanCallBack(Class<T> clazz) {
        super(clazz);
    }

    @Override
    public void onUnlogin(String msg) {
        super.onUnlogin(msg);
        ToastMgr.show("登陆失效，请重新登陆");
        BroadcastManager.getInstance(BaseApplication.getGlobalContext()).sendBroadcast(Constant.Action.Quit);
    }
}
