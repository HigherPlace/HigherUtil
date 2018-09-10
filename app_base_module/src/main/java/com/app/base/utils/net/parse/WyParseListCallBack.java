package com.app.base.utils.net.parse;

import com.app.base.BaseApplication;
import com.app.base.constant.Constant;
import com.app.base.utils.net.callback.ParseListCallBack;
import com.bryan.common.utils.BroadcastManager;
import com.bryan.common.utils.ToastMgr;
import com.huichexing.base.BaseApplication;
import com.huichexing.base.constant.Constant;
import com.huichexing.base.utils.net.callback.ParseListCallBack;

/**
 * 统一处理Token失效的问题
 * Created by Administrator on 2017/3/21 0021.
 */
public abstract class WyParseListCallBack<T> extends ParseListCallBack<T> {

    protected boolean isNeedToast = false;

    public WyParseListCallBack(Class<T> clazz) {
        super(clazz);
    }

    @Override
    public void onUnlogin(String msg) {
        super.onUnlogin(msg);
        ToastMgr.show("登陆失效，请重新登陆");
        BroadcastManager.getInstance(BaseApplication.getGlobalContext()).sendBroadcast(Constant.Action.Quit);
    }
}
