package com.app.base.utils.oss;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.DeleteObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.app.base.BaseApplication;

import java.io.File;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by oss on 2015/12/7 0007.
 * 支持普通上传，普通下载和断点上传
 */
public class OssService {
    private OSS oss;
    private static final String endpoint = "http://oss-cn-shanghai.aliyuncs.com";
    //    private static final String stsserver = "http://hch.chetongxiang.com/ChetongxiangHCH/api/sys/getOssToken";
    private static final String stsserver = "";
//    private static final String stsserver = "http://xny.chetongxiang.com/api/sys/getOssToken";
//    private static final String stsserver = "http://192.168.31.181:8888/api/sys/getOssToken";

    private String bucket = "ctx-hch";

//    private static final String stsserver ="https://api-debug.iqcloud.cc:8083" + "/api" + "/sys/getOssToken";
//    private static final String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
//    private String bucket = "witcloud";

    private String callbackAddress;
    //根据实际需求改变分片大小
    private final static int partSize = 256 * 1024;

    public static String IMAGE_DOMAIN = "http://img-hch.chetongxiang.com/";


    public OssService(OSS oss) {
        this.oss = oss;
    }

    public void SetBucketName(String bucket) {
        this.bucket = bucket;
    }

    public void InitOss(OSS _oss) {
        this.oss = _oss;
    }

    public void setCallbackAddress(String callbackAddress) {
        this.callbackAddress = callbackAddress;
    }

    //初始化一个OssService用来上传下载
    public static OssService initOSS() {
        //如果希望直接使用accessKey来访问的时候，可以直接使用OSSPlainTextAKSKCredentialProvider来鉴权。
        //OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(accessKeyId, accessKeySecret);

        OSSCredentialProvider credentialProvider;
        //使用自己的获取STSToken的类
        if (TextUtils.isEmpty(stsserver)) {
            credentialProvider = new STSGetter();
        } else {
            credentialProvider = new STSGetter(stsserver);
        }

        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        OSS oss = new OSSClient(BaseApplication.getGlobalContext(), endpoint, credentialProvider, conf);
        return new OssService(oss);

    }

    public void asyncPutImage(String object, String localFile) {
        if (object.equals("")) {
            Log.w("AsyncPutImage", "ObjectNull");
            return;
        }

        File file = new File(localFile);
        if (!file.exists()) {
            Log.w("AsyncPutImage", "FileNotExist");
            Log.w("LocalFile", localFile);
            return;
        }


        // 构造上传请求
        PutObjectRequest put = new PutObjectRequest(bucket, object, localFile);

        if (callbackAddress != null) {
            // 传入对应的上传回调参数，这里默认使用OSS提供的公共测试回调服务器地址
            put.setCallbackParam(new HashMap<String, String>() {
                {
                    put("callbackUrl", callbackAddress);
                    //callbackBody可以自定义传入的信息
                    put("callbackBody", "filename=${object}");
                }
            });
        }

        // 异步上传时可以设置进度回调
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                //Log.d("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
                int progress = (int) (100 * currentSize / totalSize);
                // TODO 设置进度条
//                UIDisplayer.updateProgress(progress);
//                UIDisplayer.displayInfo("上传进度: " + String.valueOf(progress) + "%");
            }
        });

        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                Log.d("PutObject", "UploadSuccess");

                Log.d("ETag", result.getETag());
                Log.d("RequestId", result.getRequestId());

//                UIDisplayer.uploadComplete();
//                UIDisplayer.displayInfo("Bucket: " + bucket
//                        + "\nObject: " + request.getObjectKey()
//                        + "\nETag: " + result.getETag()
//                        + "\nRequestId: " + result.getRequestId()
//                        + "\nCallback: " + result.getServerCallbackReturnBody());
                // TODO 上传成功
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                String info = "";
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                    info = clientExcepion.toString();
                }
                if (serviceException != null) {
                    // 服务异常
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                    info = serviceException.toString();
                }
//                UIDisplayer.uploadFail(info);
//                UIDisplayer.displayInfo(info);
                // TODO 上传失败
            }
        });

    }


    public OSSAsyncTask asyncPutImage(String fileName, String localFile, OSSCompletedCallback ossCompletedCallback) {
        if (TextUtils.isEmpty(fileName)) {
            Log.w("AsyncPutImage", "ObjectNull");
            return null;
        }

        File file = new File(localFile);
        if (!file.exists()) {
            Log.w("AsyncPutImage", "FileNotExist");
            Log.w("LocalFile", localFile);
            return null;
        }

        // 构造上传请求
        PutObjectRequest put = new PutObjectRequest(bucket, fileName, localFile);
        if (callbackAddress != null) {
            // 传入对应的上传回调参数，这里默认使用OSS提供的公共测试回调服务器地址
            put.setCallbackParam(new HashMap<String, String>() {
                {
                    put("callbackUrl", callbackAddress);
                    //callbackBody可以自定义传入的信息
                    put("callbackBody", "filename=${object}");
                }
            });
        }

        // 异步上传时可以设置进度回调
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                //Log.d("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
                int progress = (int) (100 * currentSize / totalSize);
                Log.i("图片上传", "当前进度----->" + currentSize + "/" + totalSize);

                // TODO 设置进度条
//                UIDisplayer.updateProgress(progress);
//                UIDisplayer.displayInfo("上传进度: " + String.valueOf(progress) + "%");
            }
        });

        OSSAsyncTask task = oss.asyncPutObject(put, ossCompletedCallback);
        return task;
    }

    public void asyncDeleteObject(String objectKey, OSSCompletedCallback ossCompletedCallback) {
        // 创建删除请求
        DeleteObjectRequest delete = new DeleteObjectRequest(bucket, objectKey);
        // 异步删除
        OSSAsyncTask deleteTask = oss.asyncDeleteObject(delete, ossCompletedCallback);
    }


    /**
     * 生成oss存储的照片名称
     * 组装名称格式 文件夹名称/车辆code_生成的日期的long值.png(jpg)
     *
     * @return
     */
    public static String generateImageName(String uploadFolderPath, String picName, String path) {
        Date date = new Date();
        StringBuilder sbObjectName = new StringBuilder();
        String end = path.contains(".") ? path.substring(path.lastIndexOf(".")) : path + ".jpg";
        if (!(end.equalsIgnoreCase(".jpg"))) {// || end.equalsIgnoreCase(".png")暂时不支持png,微信分享好像不行，都以JPEG结尾
            end += ".jpg";
        }
        sbObjectName.append(uploadFolderPath).append("/").append(picName).append("_").append(date.getTime()).append(end);
        return sbObjectName.toString();
    }
}
