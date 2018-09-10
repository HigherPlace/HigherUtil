package com.app.base.utils.net.callback;


import com.app.base.utils.net.NetManager;
import com.huichexing.base.utils.net.NetManager;

/**
 * Created by zwj on 2017/3/22.
 */

public interface RequestCallBack2 extends NetManager.RequestCallBack {

    /**
     * 未登录
     */
    void onUnlogin(String msg);
}
