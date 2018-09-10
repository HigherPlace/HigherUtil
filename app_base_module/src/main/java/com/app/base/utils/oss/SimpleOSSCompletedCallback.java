//package com.ctx.base.utils.oss;
//
//import com.alibaba.sdk.android.oss.ClientException;
//import com.alibaba.sdk.android.oss.ServiceException;
//import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
//import com.alibaba.sdk.android.oss.model.OSSRequest;
//import com.alibaba.sdk.android.oss.model.OSSResult;
//import com.chetongxiang.huichexingmanager.bean.car.CarPicBean;
//import com.zwj.zwjutils.LogUtils;
//
///**
// * Created by zwj on 2017/1/17.
// */
//
//public class SimpleOSSCompletedCallback implements OSSCompletedCallback {
//    private CarPicBean carPicBean;
//    private int carPicType; // 0,大图； 1，中图； 2，小图
//    public interface UploadCarPicCallback {
//        void onRefreshUI();
//        void onFinish();
//    }
//    private UploadCarPicCallback uploadCarPicCallback;
//
//    public SimpleOSSCompletedCallback(CarPicBean carPicBean, int carPicType, UploadCarPicCallback uploadCarPicCallback) {
//        this.carPicBean = carPicBean;
//        this.carPicType = carPicType;
//        this.uploadCarPicCallback = uploadCarPicCallback;
//    }
//
//    @Override
//    public void onSuccess(OSSRequest ossRequest, OSSResult ossResult) {
//        LogUtils.sysout("car pic upload success");
//        switch (carPicType) {
//            case 0:
//                carPicBean.setBigPicUploadSuccess(true);
//                carPicBean.setUrl("http://img-hch.chetongxiang.com/"+carPicBean.getName());
//                break;
//
//            case 1:
//                carPicBean.setMediumPicUploadSuccess(true);
//                break;
//
//            case 2:
//                carPicBean.setSamllPicUploadSuccess(true);
//                break;
//        }
//
//        // 三种尺寸图片都上传成功
//        if(carPicBean.isBigPicUploadSuccess() && carPicBean.isMediumPicUploadSuccess() && carPicBean.isSamllPicUploadSuccess()) {
//            carPicBean.setUploadStatus(CarPicBean.UploadStatus.UPLOAD_SUCCESSED);
//            carPicBean.setTask(null);
//
//            LogUtils.sysout("small url --> "+carPicBean.getUrlSmall());
//            LogUtils.sysout("medium url --> "+carPicBean.getUrlMedium());
//            LogUtils.sysout("big url --> "+carPicBean.getUrlBig());
//            if(uploadCarPicCallback != null) {
//                uploadCarPicCallback.onRefreshUI();
//            }
//        }
//
//        if(uploadCarPicCallback != null) {
//            uploadCarPicCallback.onFinish();
//        }
//    }
//
//    @Override
//    public void onFailure(OSSRequest ossRequest, ClientException e, ServiceException e1) {
//        LogUtils.sysout("car pic upload fail");
//        switch (carPicType) {
//            case 0:
//                carPicBean.setBigPicUploadSuccess(false);
//                break;
//
//            case 1:
//                carPicBean.setMediumPicUploadSuccess(false);
//                break;
//
//            case 2:
//                carPicBean.setSamllPicUploadSuccess(false);
//                break;
//        }
//
//        carPicBean.setUploadStatus(CarPicBean.UploadStatus.UPLOAD_FAILED);
//        carPicBean.setUrl(null);
//        carPicBean.setTask(null);
//        if(uploadCarPicCallback != null) {
//            uploadCarPicCallback.onRefreshUI();
//            uploadCarPicCallback.onFinish();
//        }
//    }
//}
