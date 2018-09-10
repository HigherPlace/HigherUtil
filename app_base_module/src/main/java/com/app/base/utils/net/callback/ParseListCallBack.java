package com.app.base.utils.net.callback;

import android.text.TextUtils;

import com.app.base.utils.net.bean.ResponseBean;
import com.bryan.common.utils.JsonUtil;
import com.huichexing.base.utils.net.bean.ResponseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zwj on 2016/12/9.
 */

public abstract class ParseListCallBack<T> extends SimpleCallBack {
    private Class<T> clazz;

    public ParseListCallBack(Class<T> clazz) {
        super();
        this.clazz = clazz;
    }

    @Override
    public void onSuccess(ResponseBean responseBean) {
        // TODO 还需处理一些类型转换出错等之类的错误，抛出异常
        String result = responseBean.getResult();
        if (!TextUtils.isEmpty(result) && !result.equalsIgnoreCase("null")) {
            List<T> list = JsonUtil.getObjects(result, clazz);
            onSuccess(responseBean, list);
        } else {
            onSuccess(responseBean, new ArrayList<T>());
        }
    }

    public abstract void onSuccess(ResponseBean responseBean, List<T> list);
}
