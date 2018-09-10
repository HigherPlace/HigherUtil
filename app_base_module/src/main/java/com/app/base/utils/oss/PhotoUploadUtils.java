package com.app.base.utils.oss;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.model.OSSRequest;
import com.alibaba.sdk.android.oss.model.OSSResult;
import com.bryan.common.utils.log.LogUtils;
import com.zxy.tiny.Tiny;
import com.zxy.tiny.callback.FileCallback;

import java.io.File;

/**
 * 用于图片压缩上传工具类
 * Created by Administrator on 2017/5/8 0008.
 */
public class PhotoUploadUtils {

    public static final int MSG_COMPRESS_BMP_FINISH = 0x500;
    public static final int MSG_COMPRESS_BMP_FAIL = 0X501;
    public static final int MSG_UPLOAD_BMP_FAIL = 0X502;
    public static final int MSG_UPLOAD_BMP_SUCCESS = 0X503;

    private static final String TAG = "PhotoUploadUtils";

    private Handler mHandler;
    private Context mContext;

    public PhotoUploadUtils(Handler mHandler, Context mContext) {
        this.mHandler = mHandler;
        this.mContext = mContext;
    }

    private OssService ossService;              // OSS的上传下载
    private String imageName;
    private String imageUrl;


    /**
     * 图片压缩
     */
    public void compressBitmap(String imagePath) {
        compressBitmap(imagePath, MSG_COMPRESS_BMP_FINISH);
    }

    /**
     * 图片压缩
     */
    public void compressBitmap(String imagePath, int what) {
        Bitmap.Config mConfig = Bitmap.Config.RGB_565;
        File file = new File(imagePath);
        Tiny.FileCompressOptions compressOptions = new Tiny.FileCompressOptions();
        compressOptions.config = mConfig;
        Tiny.getInstance().source(file).asFile().withOptions(compressOptions).compress(new FileCallback() {
            @Override
            public void callback(boolean isSuccess, String outfile, Throwable t) {
                if (!isSuccess) {
                    mHandler.sendEmptyMessage(MSG_COMPRESS_BMP_FAIL);
                } else {
                    Message msg = Message.obtain();
                    msg.what = what;
                    msg.obj = outfile;
                    mHandler.sendMessage(msg);
                }
            }
        });
    }

    public void uploadImage(String picPath) {
        if (ossService == null) {
            ossService = OssService.initOSS();
        }
        imageName = OssService.generateImageName("user", "user_" + System.currentTimeMillis(), picPath);
        ossService.asyncPutImage(imageName, picPath,
                new OSSCompletedCallback() {
                    @Override
                    public void onSuccess(OSSRequest ossRequest, OSSResult ossResult) {
                        imageUrl = OssService.IMAGE_DOMAIN + imageName;
                        imageName = null;
                        LogUtils.e(TAG, "上传成功 ====> " + imageUrl);
                        if (TextUtils.isEmpty(imageUrl)) {
                            mHandler.sendEmptyMessage(MSG_UPLOAD_BMP_FAIL);
                            return;
                        }
                        Message msg = Message.obtain();
                        msg.what = MSG_UPLOAD_BMP_SUCCESS;
                        msg.obj = imageUrl;
                        mHandler.sendMessage(msg);
                    }

                    @Override
                    public void onFailure(OSSRequest ossRequest, ClientException e, ServiceException e1) {
                        imageName = null;
                        mHandler.sendEmptyMessage(MSG_UPLOAD_BMP_FAIL);
                    }
                });
    }

}


