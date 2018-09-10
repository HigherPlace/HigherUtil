package com.app.base.utils.photo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.bryan.common.utils.FileUtils;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.List;


/**
 * [从本地选择图片以及拍照工具类，完美适配2.0-5.0版本]
 */
public class PhotoUtils {

    private final String tag = PhotoUtils.class.getSimpleName();

    public static final int MSG_SELECT_BMP_FINISH = 0X504;

    /**
     * 裁剪图片成功后返回他
     **/
    public static final int INTENT_CROP = 2;
    /**
     * 拍照成功后返回
     **/
    public static final int INTENT_TAKE = 3;
    /**
     * 拍照成功后返回
     **/
    public static final int INTENT_SELECT = 4;
    /**
     * 选择图片，报事报修会使用
     */
    public static final int INTENT_SELECT_TWO = 5;

    public static final String CROP_FILE_NAME = "crop_file.jpg";

    private String mImagePath = CROP_FILE_NAME;

    /**
     * PhotoUtils对象
     **/
    private OnPhotoResultListener onPhotoResultListener;


    public PhotoUtils(OnPhotoResultListener onPhotoResultListener) {
        this.onPhotoResultListener = onPhotoResultListener;
    }

    private boolean isNeedCrop = false;

    public void setNeedCrop(boolean isNeedCrop) {
        this.isNeedCrop = isNeedCrop;
    }

    /**
     * 拍照
     *
     * @param
     * @return
     */
    public void takePicture(Activity activity) {
        try {
            //每次选择图片吧之前的图片删除
//            clearCropFile(buildUri(activity));
            clearCacheFile(activity);
            generateImagePath();
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, buildUri(activity));
            if (!isIntentAvailable(activity, intent)) {
                return;
            }
            activity.startActivityForResult(intent, INTENT_TAKE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /***
     * 选择一张图片
     * 图片类型，这里是image/*，当然也可以设置限制
     * 如：image/jpeg等
     *
     * @param activity Activity
     */
    @SuppressLint("InlinedApi")
    public void selectPicture(Activity activity) {
        selectPicture(activity, INTENT_SELECT);
    }

    /***
     * 选择一张图片
     * 图片类型，这里是image/*，当然也可以设置限制
     * 如：image/jpeg等
     *
     * @param activity Activity
     */
    @SuppressLint("InlinedApi")
    public void selectPicture(Activity activity, int requestCode) {
        try {
            //每次选择图片吧之前的图片删除
//            clearCropFile(buildUri(activity));
            clearCacheFile(activity);
            Intent intent = new Intent(Intent.ACTION_PICK, null);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");

            if (!isIntentAvailable(activity, intent)) {
                return;
            }
            activity.startActivityForResult(intent, requestCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 构建uri
     *
     * @param activity
     * @return
     */
    private Uri buildUri(Activity activity) {
        Logger.e(System.currentTimeMillis() + CROP_FILE_NAME);
        if (checkSDCard()) {
            File folder = new File(Environment.getExternalStorageDirectory(), "crop");
            if (!folder.exists()) {
                folder.mkdirs();
            }
            return Uri.fromFile(folder).buildUpon().appendPath(mImagePath).build();
        } else {
            File folder = new File(activity.getFilesDir(), "crop");
            if (!folder.exists()) {
                folder.mkdirs();
            }
            return Uri.fromFile(folder).buildUpon().appendPath(mImagePath).build();
        }
    }

    private void generateImagePath() {
        mImagePath = System.currentTimeMillis() + CROP_FILE_NAME;
    }

    /**
     * @param intent
     * @return
     */
    protected boolean isIntentAvailable(Activity activity, Intent intent) {
        PackageManager packageManager = activity.getPackageManager();
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    private boolean corp(Activity activity, Uri uri) {
        Intent cropIntent = new Intent("com.android.camera.action.CROP");
        cropIntent.setDataAndType(uri, "image/*");
        cropIntent.putExtra("crop", "true");
        cropIntent.putExtra("aspectX", 1);
        cropIntent.putExtra("aspectY", 1);
        cropIntent.putExtra("outputX", 200);
        cropIntent.putExtra("outputY", 200);
        cropIntent.putExtra("return-data", false);
        cropIntent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        Uri cropuri = buildUri(activity);
        cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, cropuri);
        if (!isIntentAvailable(activity, cropIntent)) {
            return false;
        } else {
            try {
                activity.startActivityForResult(cropIntent, INTENT_CROP);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    /**
     * 返回结果处理
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        if (onPhotoResultListener == null) {
            Log.e(tag, "onPhotoResultListener is not null");
            return;
        }

        switch (requestCode) {
            //拍照
            case INTENT_TAKE:
//                if (new File(buildUri(activity).getPath()).exists()) {
//                    if (corp(activity, buildUri(activity))) {
//                        return;
//                    }
//                    onPhotoResultListener.onPhotoCancel();
//                }
                if (resultCode == Activity.RESULT_OK && new File(buildUri(activity).getPath()).exists()) {
                    onPhotoResultListener.onPhotoResult(buildUri(activity), requestCode);
                } else {
                    onPhotoResultListener.onPhotoCancel();
                }
                break;
            case INTENT_SELECT:  //选择图片
                if (isNeedCrop) {
                    generateImagePath();
                    if (data != null && data.getData() != null) {
                        Uri imageUri = data.getData();
                        if (corp(activity, imageUri)) {
                            return;
                        }
                    }
                    onPhotoResultListener.onPhotoCancel();
                } else {
                    onPhotoResultListener.onPhotoResult(data.getData(), requestCode);
                }
                break;
            //截图
            case INTENT_CROP:
                if (resultCode == Activity.RESULT_OK && new File(buildUri(activity).getPath()).exists()) {
                    onPhotoResultListener.onPhotoResult(buildUri(activity), requestCode);
                }
                break;
        }
    }

    /**
     * 删除文件
     *
     * @param uri
     * @return
     */
    public boolean clearCropFile(Uri uri) {
        if (uri == null) {
            return false;
        }

        File file = new File(uri.getPath());
        if (file.exists()) {
            boolean result = file.delete();
            if (result) {
                Log.i(tag, "Cached crop file cleared.");
            } else {
                Log.e(tag, "Failed to clear cached crop file.");
            }
            return result;
        } else {
            Log.w(tag, "Trying to clear cached crop file but it does not exist.");
        }

        return false;
    }

    public void clearCacheFile(Activity activity) {
        if (checkSDCard()) {
            FileUtils.deleteFileFromSdcard(activity, "crop", null);
        } else {
            File folder = new File(activity.getFilesDir(), "crop");
            FileUtils.deleteFileFromInternalFilesDir(activity, folder.getAbsolutePath(), null);
        }
    }

    /**
     * [回调监听类]
     *
     * @author huxinwu
     * @version 1.0
     * @date 2015-1-7
     **/
    public interface OnPhotoResultListener {
        void onPhotoResult(Uri uri, int requestCode);

        void onPhotoCancel();
    }

    public OnPhotoResultListener getOnPhotoResultListener() {
        return onPhotoResultListener;
    }

    public void setOnPhotoResultListener(OnPhotoResultListener onPhotoResultListener) {
        this.onPhotoResultListener = onPhotoResultListener;
    }


    /**
     * 判断SDCard是否存在,并可写
     *
     * @return
     */
    public static boolean checkSDCard() {
        String flag = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(flag);
    }
}
