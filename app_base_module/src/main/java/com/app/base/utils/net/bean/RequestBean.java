package com.app.base.utils.net.bean;

import android.content.Context;
import android.text.TextUtils;

import com.app.base.utils.net.NetManager;
import com.app.base.utils.net.Parser;

import org.xutils.common.Callback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 网络请求的实体类
 */
public class RequestBean {
    /**
     * 最大重连次数
     */
    public static final int MAX_RECONNECTION_COUNT = 2;
    public static final int METHOD_GET = 2000;
    public static final int METHOD_POST = 2001;

    /**
     * 全局的参数，需要每次请求都传递的参数直接在里面设置
     */
    public static final Map<String, String> globalParamMap = new HashMap<>();

    /**
     * 全局的head参数，需要每次请求都传递的head直接在里面设置
     */
    public static final Map<String, String> globalHeadMap = new HashMap<>();

    /**
     * true - 未登录时会回调onUnlogin
     */
    public static boolean callbackUnlogin = false;

    //    private String key;        // 用于标记是否为同一个请求实体
    private String url;
    /**
     * 请求的方式
     */
    private int requestMethod;
    /**
     * 请求参数
     */
    private Map<String, String> paramMap;
    private Map<String, List<String>> paramArrayMap;    // 存数组的参数
    private String bodyContent; // 以json形式传递的参数
    private int timeOut = 10 * 10000;   // 连接超时时间，单位毫秒

    /**
     * 请求头部的参数
     */
    private Map<String, String> headMap;

    /**
     * 是否需要对结果进行解析处理,默认不进行解析(全局)
     */
    public static boolean isNeedParse = true;

    // 回调接口
    private NetManager.RequestCallBack callback;

    private int count;    // 超时重连次数
    private boolean isNeedReconnection; // 是否需要重连，true需要；


    private Callback.Cancelable cancelable;
    private String tag;

    /**
     * 设置解析模式
     */
    public enum ParseMode {
        /** 跟随全局设定 */
        GLOBAL,
        /** 解析*/
        TRUE,
        /** 不解析 */
        FALSE
    }

    /**
     * 默认跟随全局
     */
    private ParseMode parseMode = ParseMode.GLOBAL;



    public RequestBean() {
        super();
    }

    public RequestBean(String url, int requestMethod) {
        this();
        this.url = url;
        this.requestMethod = requestMethod;
    }

    public RequestBean(String url, int requestMethod, NetManager.RequestCallBack callback) {
        this(url, requestMethod);
        this.callback = callback;
    }

    public int getRequestMethod() {
        return requestMethod;
    }

    public RequestBean setRequestMethod(int requestMethod) {
        this.requestMethod = requestMethod;
        return this;
    }

    public Map<String, String> getParamMap() {
        return paramMap;
    }

    public RequestBean setParamMap(Map<String, String> paramMap) {
        this.paramMap = paramMap;
        return this;
    }

    public boolean isNeedParse() {
        switch (parseMode) {
            case GLOBAL:
                return RequestBean.isNeedParse;

            case TRUE:
                return true;

            case FALSE:
                return false;
        }

        return RequestBean.isNeedParse;
    }

    /**
     * 设置全局解析，默认true
     * @param isNeedParse
     * @return
     */
    public static void setGlobalParse(boolean isNeedParse) {
        RequestBean.isNeedParse = isNeedParse;
    }

    public NetManager.RequestCallBack getCallback() {
        return callback;
    }

    public RequestBean setCallback(NetManager.RequestCallBack callback) {
        this.callback = callback;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public RequestBean setUrl(String url) {
        this.url = url;
        return this;
    }

    public int getCount() {
        return count;
    }

    public RequestBean setCount(int count) {
        this.count = count;
        return this;
    }

    public boolean isNeedReconnection() {
        return isNeedReconnection;
    }

    public RequestBean setNeedReconnection(boolean needReconnection) {
        isNeedReconnection = needReconnection;
        return this;
    }

    public RequestBean addParam(String name, String value) {
        if (!TextUtils.isEmpty(value)) {
            if (paramMap == null) {
                paramMap = new HashMap<>();
            }
            paramMap.put(name, value);
        }
        return this;
    }

    /**
     * 添加全局参数
     * @param name
     * @param value
     * @return
     */
    public static void addGlobalParam(String name, String value) {
        if (!TextUtils.isEmpty(value)) {
            globalParamMap.put(name, value);
        }
    }

    public static Map<String, String> getGlobalParamMap() {
        return globalParamMap;
    }

    public static Map<String, String> getGlobalHeadMap() {
        return globalHeadMap;
    }

    public static void clearGlobalParamMap() {
        globalParamMap.clear();
    }

    public static void clearGlobalHeadMap() {
        globalHeadMap.clear();
    }

    public static void clearGlobalMap() {
        globalParamMap.clear();
        globalHeadMap.clear();
    }


    public RequestBean addHead(String name, String value) {
        if (!TextUtils.isEmpty(value)) {
            if (headMap == null) {
                headMap = new HashMap<>();
            }
            headMap.put(name, value);
        }
        return this;
    }

    public void setHeadMap(Map<String, String> headMap) {
        this.headMap = headMap;
    }

    public Map<String, String> getHeadMap() {
        return headMap;
    }

    public static void addGlobalHead(String name, String value) {
        if (!TextUtils.isEmpty(value)) {
            globalHeadMap.put(name, value);
        }
    }

    public String getBodyContent() {
        return bodyContent;
    }

    public RequestBean setBodyContent(String bodyContent) {
        this.bodyContent = bodyContent;
        return this;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public RequestBean setTimeOut(int timeOut) {
        this.timeOut = timeOut;
        return this;
    }

    /**
     * 发起网络请求
     *
     * @param context
     * @return
     */
    public Callback.Cancelable request(Context context) {
        this.cancelable = NetManager.request(context, this);
        return cancelable;
    }

    public Callback.Cancelable request(Context context, Parser parser) {
        this.cancelable = NetManager.request(context, this, parser);
        return cancelable;
    }


    public Map<String, List<String>> getParamArrayMap() {
        return paramArrayMap;
    }

    public RequestBean setParamArrayMap(Map<String, List<String>> paramArrayMap) {
        this.paramArrayMap = paramArrayMap;
        return this;
    }

    /**
     * 添加数组参数
     *
     * @param name
     * @param valueList
     * @return
     */
    public RequestBean addParamArray(String name, List<String> valueList) {
        if (valueList != null && valueList.size() > 0) {
            if (paramArrayMap == null) {
                paramArrayMap = new HashMap<>();
            }
            paramArrayMap.put(name, valueList);
        }
        return this;
    }

    public Callback.Cancelable getCancelable() {
        return cancelable;
    }

    public RequestBean setCancelable(Callback.Cancelable cancelable) {
        this.cancelable = cancelable;
        return this;
    }

    public String getTag() {
        return tag;
    }

    public RequestBean setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public ParseMode getParseMode() {
        return parseMode;
    }

    public RequestBean setParseMode(ParseMode parseMode) {
        this.parseMode = parseMode;
        return this;
    }

    public void download() {

    }

}
