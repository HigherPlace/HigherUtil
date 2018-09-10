package com.app.base.utils.net.parse;

import com.bryan.common.utils.BroadcastManager;
import com.bryan.common.utils.ToastMgr;
import com.huichexing.base.BaseApplication;
import com.huichexing.base.utils.net.callback.ParseBeanCallBack;

public abstract class WyParseBeanCallBack<T> extends ParseBeanCallBack<T> {

    protected WyParseBeanCallBack(Class<T> clazz) {
        super(clazz);
    }

    @Override
    public void onUnlogin(String msg) {
        super.onUnlogin(msg);
        ToastMgr.show("登陆失效，请重新登陆");
        BroadcastManager.getInstance(BaseApplication.getGlobalContext()).sendBroadcast(com.huichexing.base.constant.Constant.Action.Quit);
    }
}
