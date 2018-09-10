package com.app.base.utils.net.callback;

import android.text.TextUtils;

import com.app.base.utils.net.bean.ResponseBean;
import com.bryan.common.utils.JsonUtil;
import com.huichexing.base.utils.net.bean.ResponseBean;


/**
 * Created by zwj on 2016/12/9.
 */

public abstract class ParseBeanCallBack<T> extends SimpleCallBack {

    private Class<T> clazz;

    public ParseBeanCallBack(Class<T> clazz) {
        super();
        this.clazz = clazz;
    }

    @Override
    public void onSuccess(ResponseBean responseBean) {
        // TODO 还需处理一些类型转换出错等之类的错误，抛出异常
        String result = responseBean.getResult();
        if (!TextUtils.isEmpty(result) && !result.equalsIgnoreCase("null")) {
            T obj = JsonUtil.getObject(result, clazz);
            onSuccess(responseBean, obj);
        } else {
            onSuccess(responseBean, null);
        }
    }

    public abstract void onSuccess(ResponseBean responseBean, T obj);
}
