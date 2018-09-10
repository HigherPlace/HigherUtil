package com.app.base.utils.net;


import com.bryan.common.utils.log.LogUtils;
import com.huichexing.base.utils.net.bean.RequestBean;
import com.huichexing.base.utils.net.bean.ResponseBean;
import com.huichexing.base.utils.net.callback.RequestCallBack2;
import com.huichexing.base.utils.net.constant.ResponseStatus;
import com.huichexing.base.utils.net.constant.Status;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Desc:  <br/>
 * Author: YJG <br/>
 * Email: ye.jg@outlook.com <br/>
 * Date: 2017/5/11 <br/>
 */
public class MyParser implements Parser {

    private String TAG = "";

    @Override
    public void parse(RequestBean requestBean, String result, ResponseBean responseBean) {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(result);
            int status = jsonObject.optInt("code", 1000);
            String message = jsonObject.optString(Status.MESSAGE);
            switch (status) {
                case 0:// 成功获取数据
                    String datas = jsonObject.optString(Status.DATA);
                    if (requestBean.getCallback() != null) {
                        responseBean.setStatus(ResponseStatus.SUCCESS).setMessage(message != null ? message : "获取数据成功").setResult(datas);
                        requestBean.getCallback().onSuccess(responseBean);
                    }
                    break;
                case ResponseStatus.SUCCESS_ONLY_DATA:// 成功获取数据，没有返回状态字直接返回数据的情况
                    if (requestBean.getCallback() != null) {
                        responseBean.setStatus(ResponseStatus.SUCCESS_ONLY_DATA).setMessage(message != null ? message : "获取数据成功").setResult(result);
                        requestBean.getCallback().onSuccess(responseBean);
                    }
                    break;
                case ResponseStatus.UNLOGIN:// 当前未登录
                    if (requestBean.getCallback() != null) {

                        if (RequestBean.callbackUnlogin && requestBean.getCallback() instanceof RequestCallBack2) {
                            ((RequestCallBack2) requestBean.getCallback()).onUnlogin(message != null ? message : "未登录");
                        } else {
                            responseBean.setStatus(ResponseStatus.UNLOGIN).setMessage(message != null ? message : "未登录").setThrowable(new Throwable("UNLOGIN")).setResult(result);
                            requestBean.getCallback().onError(responseBean);
                        }
                    }

                    break;
                case 1:// 获取数据异常(+已拉去玩全部数据的情况)
                    if (requestBean.getCallback() != null) {
                        responseBean.setStatus(ResponseStatus.FAIL).setMessage(message != null ? message : "获取数据失败").setThrowable(new Throwable("fail")).setResult(result);

                        requestBean.getCallback().onError(responseBean);
                    }
                    break;

                default:
                    if (requestBean.getCallback() != null) {
                        responseBean.setStatus(status).setMessage(message != null ? message : "访问出错").setThrowable(new Throwable("访问出错")).setResult(result);

                        requestBean.getCallback().onError(responseBean);
                    }
                    break;
            }

        } catch (JSONException e) {
            LogUtils.e(TAG, e.getMessage());
            e.printStackTrace();

            if (requestBean.getCancelable() != null) {
                responseBean.setStatus(ResponseStatus.ERROR_OTHER).setMessage("解析出错").setThrowable(e);

                requestBean.getCallback().onError(responseBean);
            }
        }
    }
}
