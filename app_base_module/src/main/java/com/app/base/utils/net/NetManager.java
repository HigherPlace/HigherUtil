package com.app.base.utils.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.app.base.utils.net.bean.RequestBean;
import com.app.base.utils.net.bean.ResponseBean;
import com.app.base.utils.net.callback.DownloadCallback;
import com.app.base.utils.net.callback.RequestCallBack2;
import com.app.base.utils.net.constant.ResponseStatus;
import com.app.base.utils.net.constant.Status;
import com.bryan.common.utils.ToastMgr;
import com.bryan.common.utils.log.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback.Cancelable;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NetManager {

    public static final String TAG = "NetManager";

    /**
     * 是否正在重连
     */
    private static boolean isReLoading = false;


    /**
     * 网络请求回调接口
     */
    public interface RequestCallBack {
        /**
         * 访问成功并返回数据时调用该方法
         */
        void onSuccess(ResponseBean responseBean);

        /**
         * 取消网络请求时调用
         */
        void onCancelled(ResponseBean responseBean);

        /**
         * 发生网络错误时调用
         */
        void onError(ResponseBean responseBean);

        /**
         * 不管如何结束后都会调用该方法
         */
        void onFinished(ResponseBean responseBean);

    }

    private static Map<String, RequestBean> requestMap = new HashMap<>();

    public static Cancelable request(@NonNull Context context, @NonNull RequestBean requestBean) {
        return request(context, requestBean, null);
    }

    /**
     * 需要重连的时候返回null
     */
    public static Cancelable request(@NonNull final Context context, @NonNull final RequestBean requestBean, final Parser parser) {
        final ResponseBean responseBean = new ResponseBean();
        responseBean.setUrl(requestBean.getUrl());

        // 添加到网络访问列表里
        requestMap.put(requestBean.toString(), requestBean);

        if (!isNetWorkReachable(context)) {
            if (requestBean.getCallback() != null) {
                responseBean.setStatus(ResponseStatus.DISABLE_NETWORK)
                        .setMessage("当前网络不可用，请检查网络后再试")
                        .setThrowable(new Throwable("network disable"));
                requestBean.getCallback().onError(responseBean);
                requestBean.getCallback().onFinished(responseBean);
            }
            return null;
        }

        RequestParams params = new RequestParams(requestBean.getUrl());
        if (requestBean.getTimeOut() > 0) {
            params.setConnectTimeout(requestBean.getTimeOut());
        }

        LogUtils.i(TAG, "url ====> " + requestBean.getUrl());
        if (!TextUtils.isEmpty(requestBean.getBodyContent())) {
            // 以json数据格式提交
            // json 必须以post方式提交,强制设为Post
            LogUtils.i(TAG, "bodyparam ====> " + requestBean.getBodyContent());
            requestBean.setRequestMethod(RequestBean.METHOD_POST);
            adddHeaders(params, requestBean);
            params.setAsJsonContent(true);
            params.setBodyContent(requestBean.getBodyContent());
        } else {
            addParamsAndHeaders(params, requestBean);
        }

        CommonCallback<String> commonCallback = new CommonCallback<String>() {

            @Override
            public void onCancelled(CancelledException cancelledException) {
                if (requestBean.getCallback() != null) {
                    responseBean.setStatus(ResponseStatus.CANCELLED_EXCEPTION)
                            .setMessage("取消网络请求")
                            .setCancelledException(cancelledException);
                    requestBean.getCallback().onCancelled(responseBean);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

                if (ex instanceof HttpException) { // 网络错误
                    HttpException httpEx = (HttpException) ex;
                    int responseCode = httpEx.getCode();
                    String responseMsg = httpEx.getMessage();
                    String errorResult = httpEx.getResult();

                    responseBean.setStatus(ResponseStatus.ERROR)
                            .setMessage("网络错误，请稍后重试")
                            .setThrowable(ex)
                            .setShowToast(true);

                    // TODO
//                    MobclickAgent.reportError(context,
//                            DateUtil.getDay(System.currentTimeMillis())
//                                    + "-------->" + requestBean.getUrl()
//                                    + "--------->" + ex.toString()); // 把错误上传到友盟
                } else { // 其他错误
                    responseBean.setStatus(ResponseStatus.ERROR_OTHER)
                            .setMessage("出现异常，请稍后重试")
                            .setThrowable(ex)
                            .setShowToast(true);
                }
                LogUtils.catchError(ex.getMessage(), ex);
                // 连接超时，重连(最多重连2次)
//                if ((ex instanceof SocketTimeoutException || ex instanceof UnknownHostException)
//                        && requestBean.isNeedReconnection() && requestBean.getCount() < RequestBean.MAX_RECONNECTION_COUNT) {
//                    // 连接超时，设置重连标志
////                    requestBean.setNeedReconnection(true).setCount(requestBean.getCount() + 1);
//                    requestBean.setCount(requestBean.getCount() + 1);
//
//                    LogUtils.i(TAG, "重连 ---> ");
//                    LogUtils.i(TAG, "url ---> " + requestBean.getUrl());
//                    LogUtils.i(TAG, "count ---> " + requestBean.getCount());
//                } else {
//                    if (requestBean.getCallback() != null) {
//                        requestBean.getCallback().onError(responseBean);
//                    }
//                }
                if (ex instanceof SocketTimeoutException || ex instanceof UnknownHostException) {
                    responseBean.setStatus(ResponseStatus.ERROR_OTHER)
                            .setMessage("网络超时")
                            .setThrowable(ex);
                }

                if (requestBean.getCallback() != null) {
                    requestBean.getCallback().onError(responseBean);
                }
            }

            @Override
            public void onFinished() {
                if (requestBean.isNeedReconnection() && requestBean.getCount() <= RequestBean.MAX_RECONNECTION_COUNT) {
                    // 进行重连
//                    requestBean.setNeedReconnection(false);
                    request(context, requestBean);
                } else {
                    requestMap.remove(requestBean.toString());
                    if (requestBean.getCallback() != null) {
                        requestBean.getCallback().onFinished(responseBean);
                    }
                }
            }

            @Override
            public void onSuccess(String result) {
                LogUtils.json(result);
                //把所有数据都返回给上层
                responseBean.setAllSuccessInfo(result);
                if (!requestBean.isNeedParse()) {
                    if (requestBean.getCallback() != null) {
                        responseBean.setStatus(ResponseStatus.SUCCESS)
                                .setMessage("获取数据成功")
                                .setResult(result);
                        requestBean.getCallback().onSuccess(responseBean);
                    }
                    return;
                }

                // 自定义解析(可参照下面的解析)
                if (parser != null) {
                    parser.parse(requestBean, result, responseBean);
                    return;
                }

                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(result);
                    int status = jsonObject.optInt(Status.STATUS, 1000);
                    String message = jsonObject.optString(Status.MESSAGE);
                    int total = jsonObject.optInt(Status.TOTAL);
                    if (total != 0) {
                        responseBean.setTotal(total);
                    }
                    switch (status) {
                        case ResponseStatus.SUCCESS:// 成功获取数据
                            String datas = jsonObject.optString(Status.DATA);
                            if (requestBean.getCallback() != null) {
                                responseBean.setStatus(ResponseStatus.SUCCESS)
                                        .setMessage(message != null ? message : "获取数据成功")
                                        .setResult(datas);
                                requestBean.getCallback().onSuccess(responseBean);
                            }
                            break;
                        case ResponseStatus.SUCCESS_ONLY_DATA:// 成功获取数据，没有返回状态字直接返回数据的情况
                            if (requestBean.getCallback() != null) {
                                responseBean.setStatus(ResponseStatus.SUCCESS_ONLY_DATA)
                                        .setMessage(message != null ? message : "获取数据成功")
                                        .setResult(result);
                                requestBean.getCallback().onSuccess(responseBean);
                            }
                            break;
                        case ResponseStatus.UNLOGIN:// 当前未登录
                            if (requestBean.getCallback() != null) {

                                if (RequestBean.callbackUnlogin && requestBean.getCallback() instanceof RequestCallBack2) {
                                    ((RequestCallBack2) requestBean.getCallback()).onUnlogin(message != null ? message : "未登录");
                                } else {
                                    responseBean.setStatus(ResponseStatus.UNLOGIN)
                                            .setMessage(message != null ? message : "未登录")
                                            .setThrowable(new Throwable("UNLOGIN"))
                                            .setResult(result);
                                    requestBean.getCallback().onError(responseBean);
                                }
                            }

                            break;
                        case ResponseStatus.FAIL:// 获取数据异常(+已拉去玩全部数据的情况)
                            if (requestBean.getCallback() != null) {
                                responseBean.setStatus(ResponseStatus.FAIL)
                                        .setMessage(message != null ? message : "获取数据失败")
                                        .setThrowable(new Throwable("fail"))
                                        .setResult(result);

                                requestBean.getCallback().onError(responseBean);
                            }
                            break;

                        default:
                            if (requestBean.getCallback() != null) {
                                responseBean.setStatus(status)
                                        .setMessage(message != null ? message : "访问出错")
                                        .setThrowable(new Throwable("访问出错"))
                                        .setResult(result);

                                requestBean.getCallback().onError(responseBean);
                            }
                            break;
                    }

                } catch (JSONException e) {
                    LogUtils.catchError(TAG, e.getMessage(), e);
                    if (requestBean.getCancelable() != null) {
                        responseBean.setStatus(ResponseStatus.ERROR_OTHER)
                                .setMessage("解析出错")
                                .setThrowable(e);
                        LogUtils.catchError(e.getMessage(), e);
                        requestBean.getCallback().onError(responseBean);
                    }
                }
            }
        };

        if (requestBean.getRequestMethod() == RequestBean.METHOD_POST) {
            return x.http().post(params, commonCallback);
        } else {
            return x.http().get(params, commonCallback);
        }
    }

    /**
     * 检查网络是否连通
     */
    public static boolean isNetWorkReachable(Context context) {
        ConnectivityManager mManager = (ConnectivityManager) context.getApplicationContext().getSystemService(
                Context.CONNECTIVITY_SERVICE);

        // 如果没有可用的数据网络会返回null
        NetworkInfo current = mManager.getActiveNetworkInfo();
        if (current == null) {
            return false;
        }
        // 有可用的数据网络还需要检查他的稳定性
        return (current.getState() == NetworkInfo.State.CONNECTED);
    }

    /**
     * 上传文件
     *
     * @param url
     * @param uploadFile
     * @param uploadCallBack
     */
    public static void uploadFile(String url, File uploadFile, CommonCallback<String> uploadCallBack) {
        uploadFile(url, uploadFile, null, uploadCallBack);
    }

    /**
     * 上传文件
     *
     * @param url
     * @param uploadFile
     * @param paramMap
     * @param uploadCallBack
     */
    public static void uploadFile(String url, File uploadFile, Map<String, String> paramMap, CommonCallback<String> uploadCallBack) {
        List<File> uplodFileList = new ArrayList<>();
        uplodFileList.add(uploadFile);
        uploadFile(url, uplodFileList, paramMap, uploadCallBack);
    }

    /**
     * 上传多个文件
     *
     * @param url
     * @param uploadFileList
     * @param paramMap
     * @param uploadCallBack
     */
    public static void uploadFile(String url, List<File> uploadFileList, Map<String, String> paramMap, CommonCallback<String> uploadCallBack) {

        if (uploadFileList == null || uploadFileList.size() == 0) {
            // TODO 抛出异常
            return;
        }

        RequestParams params = new RequestParams(url);   // 上传文件的接口
        params.setMultipart(true);

        // 替上传的file设置参数名称，否则一样的参数名称无法上传多个文件
        for (int i = 0; i < uploadFileList.size(); i++) {
            File file = uploadFileList.get(i);

            if (i == 0) {
                params.addBodyParameter("file", file);
            } else {
                params.addBodyParameter("file" + i, file);
            }
        }

        addParamsOrHeaders(params, paramMap, true);
        x.http().post(params, uploadCallBack);
    }

    public static void uploadFile(String url, Map<String, File> fileMap, Map<String, String> paramMap, CommonCallback<String> uploadCallBack) {

        if (fileMap == null) {
            // TODO 抛出异常
            return;
        }

        RequestParams params = new RequestParams(url);   // 上传文件的接口
        params.setMultipart(true);

//        if (fileMap != null) {
        Set<String> keySet = paramMap.keySet();
        Iterator<String> iterator = keySet.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            File value = fileMap.get(key);
            params.addBodyParameter(key, value);
        }
//        }

        addParamsOrHeaders(params, paramMap, true);
        x.http().post(params, uploadCallBack);
    }

    public static void adddHeaders(RequestParams params, RequestBean requestBean) {
        // 添加全局head
        addParamsOrHeaders(params, RequestBean.getGlobalHeadMap(), false);
        // 添加head
        addParamsOrHeaders(params, requestBean.getHeadMap(), false);

    }

    /**
     * 往RequestParams中添加请求参数以及head
     *
     * @param params
     * @param requestBean
     */
    public static void addParamsAndHeaders(RequestParams params, RequestBean requestBean) {
        adddHeaders(params, requestBean);
        // 添加参数
        addParamsOrHeaders(params, requestBean.getParamMap(), true);
        // 添加全局参数
        addParamsOrHeaders(params, RequestBean.getGlobalParamMap(), true);
        // 添加数组参数
        Map<String, List<String>> paramArrayList = requestBean.getParamArrayMap();
        if (paramArrayList != null) {
            Set<String> keySet = paramArrayList.keySet();
            Iterator<String> iterator = keySet.iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                List<String> valueList = paramArrayList.get(key);
                // 打印参数名称和值
                StringBuilder sbParam = new StringBuilder();
                for (int i = 0; i < valueList.size(); i++) {
                    sbParam.append("param: ").append(key).append(" ---> ").append(valueList.get(i));
                    if (i != valueList.size() - 1) {
                        sbParam.append("\n");
                    }
                    params.addBodyParameter(key, valueList.get(i));
                }
                LogUtils.i(TAG, sbParam.toString());
            }
        }
    }

    /**
     * @param params
     * @param map
     * @param isParam 添加参数
     */
    public static void addParamsOrHeaders(RequestParams params, Map<String, String> map, boolean isParam) {
        if (map != null) {
            Set<String> keySet = map.keySet();
            Iterator<String> iterator = keySet.iterator();
            StringBuilder sbParam = new StringBuilder();
            while (iterator.hasNext()) {
                String key = iterator.next();
                String value = map.get(key);
                // 打印参数名称和值
//                StringBuilder sbTemp = new StringBuilder();
                if (isParam) {
                    sbParam.append("param: ");
                    params.addBodyParameter(key, value);
                } else {
                    sbParam.append("head: ");
                    params.addHeader(key, value);
                }
                sbParam.append(key).append(" ---> ").append(value).append("\n");
            }
            LogUtils.i(TAG, sbParam.toString());
        }
    }

    /**
     * 若有设为了自动添加，则添加token
     *
     * @param context
     * @param requestBean
     */
//    private static void addToken(Context context, RequestBean requestBean, RequestParams params) {
//        if (requestBean.isNeedToken()) {
//            String token = null;
//            if(TextUtils.isEmpty(TOKEN)) {
//                token = FileUtils.loadContentFromInternalFilesDir(context.getApplicationContext(), Constant.FILE_TOKEN);
//            }else {
//                token = TOKEN;
//            }
//
//            if (!TextUtils.isEmpty(token)) {
//                LogUtils.sysout("token --> " + token);
//                params.addBodyParameter(Constant.TOKEN, token);
//            }
//        }
//    }


//    /**
//     * 上传车辆照片到服务器
//     *
//     * @param path 图片路径
//     */
//    public static void uploadImage(String URL, Context context, String path,
//                                   UploadImageCallBack uploadImageCallBack, boolean isShowLoading) {
//
//        ResponseBean responseBean = null;
//        if (path == null) {
//            responseBean = new ResponseBean(ResponseStatus.PARSE_IMAGE_FILE_TO_BASE64, "本地图片路径为空!");
//            responseBean.setThrowable(new Throwable("本地图片路径为空!"));
//            if (uploadImageCallBack != null) {
//                uploadImageCallBack.onError(responseBean);
//                uploadImageCallBack.onFinished(responseBean);
//            }
//            return;
//        }
//
//        JSONObject json = new JSONObject();
//        try {
//            json.put("BaseCode", BitmapCompressUtil.BitmapToStream(path));
//        } catch (JSONException e) {
//            if (uploadImageCallBack != null) {
//                responseBean = new ResponseBean(ResponseStatus.PARSE_IMAGE_FILE_TO_BASE64, "解析错误");
//                uploadImageCallBack.onError(responseBean);
//                uploadImageCallBack.onFinished(responseBean);
//            }
//            LogUtils.e(TAG, e.getMessage());
//        }
//        RequestBean uploadImgRequestBean = new RequestBean(URL, RequestBean.METHOD_POST);
//        uploadImgRequestBean.setBodyContent(json.toString())
//                .setShowLoading(isShowLoading)
//                .setCallback(uploadImageCallBack).
//                request(context);
//    }

    /**
     * 取消tag所标记的所有网络请求
     *
     * @param tag
     */
    public static void cacelRquests(String tag) {
        Set<Map.Entry<String, RequestBean>> entrySet = requestMap.entrySet();
        Iterator<Map.Entry<String, RequestBean>> iterator = entrySet.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, RequestBean> entry = iterator.next();
            RequestBean requestBean = entry.getValue();

            if (tag != null) {
                if (tag.equals(requestBean.getTag()) && requestBean.getCancelable() != null && !requestBean.getCancelable().isCancelled()) {
                    requestBean.getCancelable().cancel();
                    iterator.remove();
                }
            } else {
                if (requestBean.getCancelable() != null && !requestBean.getCancelable().isCancelled()) {
                    requestBean.getCancelable().cancel();
                    iterator.remove();
                }
            }
        }
    }

    /**
     * 取消全部请求
     */
    public static void cacelAllRquests() {
        cacelRquests(null);
    }

    /**
     * 下载
     */
    public static void download(Context context, String url, File destFolder, DownloadCallback downloadCallback) {
        if (destFolder != null && destFolder.exists()) {
            StringBuilder sbDownPath = new StringBuilder();
            sbDownPath.append(destFolder.getAbsolutePath()).append(File.separator).append(url.substring(url.lastIndexOf("/") + 1));
            LogUtils.sysout("downPath ----> " + sbDownPath.toString());

            RequestParams params = new RequestParams(url);
            params.setAutoResume(true);
            params.setAutoRename(false);
            params.setSaveFilePath(sbDownPath.toString());
//	        params.setExecutor(executor);
            // 为请求创建新的线程, 取消时请求线程被立即中断; false: 请求建立过程可能不被立即终止
            params.setCancelFast(true);
            x.http().get(params, downloadCallback);
        } else {
            ToastMgr.show("找不到下载路径,请确认是否已安装sdcard");
        }
    }

}
