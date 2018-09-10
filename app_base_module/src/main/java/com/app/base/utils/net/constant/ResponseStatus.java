package com.app.base.utils.net.constant;

/**
 * Created by Administrator on 2016/5/27.
 */
public interface ResponseStatus {
    /** 成功获取数据 */
    int SUCCESS = 1;

    /** 成功获取数据，没有返回状态字直接返回数据的情况 */
    int SUCCESS_ONLY_DATA = 1000;

    /** 未登录 */
    int UNLOGIN = 1001;

    /** 请求数据失败,数据为空 */
    int FAIL = 0;

    /** 超时 */
    int TIME_OUT = 1111;

    /** 没有网络 */
    int DISABLE_NETWORK = 1112;

    /** 图片转换成base64的数据出错时也将调用该方法 */
    int PARSE_IMAGE_FILE_TO_BASE64 = 1113;

    /** 取消网络请求失败异常 */
    int CANCELLED_EXCEPTION = 1114;

    /** 后台出错 */
    int ERROR = 1115;

    /** 其他错误 */
    int ERROR_OTHER = 1116;
}
