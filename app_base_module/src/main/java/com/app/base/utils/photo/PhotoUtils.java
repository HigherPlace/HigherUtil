package com.app.base.utils.photo;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.app.base.BuildConfig;
import com.app.base.constant.Constant;
import com.app.base.utils.fileprovider.FileProvider7;
import com.bryan.common.utils.AppDateMgr;
import com.bryan.common.utils.log.LogUtils;
import com.zxy.tiny.Tiny;
import com.zxy.tiny.callback.FileCallback;

import java.io.File;
import java.util.List;


/**
 * [从本地选择图片以及拍照工具类，完美适配2.0-5.0版本]
 *
 * @author huxinwu
 * @version 1.0
 * @date 2015-1-7
 **/
public class PhotoUtils {

    private final String tag = PhotoUtils.class.getSimpleName();

    /**
     * 裁剪图片成功后返回
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

    public static final String CROP_FILE_NAME = "crop_file.jpg";

    private static final String TAG = "PhotoUtils2";

    private String mImageName = CROP_FILE_NAME;


    /**
     * PhotoUtils对象
     **/
    private OnPhotoResultListener onPhotoResultListener;


    public PhotoUtils(OnPhotoResultListener onPhotoResultListener) {
        this.onPhotoResultListener = onPhotoResultListener;
    }

    /**
     * 拍照
     *
     * @param
     * @return
     */
    public boolean takePicture(Activity activity) {
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            File originalFile = getOriginalFile(activity);
            if (originalFile == null) {
                return false;
            }
            Uri uri;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                uri = FileProvider.getUriForFile(activity, BuildConfig._ID + Constant.Provider_File_Name, originalFile);
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                FileProvider7.grantPermissions(activity, intent, uri, true);
            } else {
                uri = Uri.fromFile(originalFile);
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            if (!isIntentAvailable(activity, intent)) {
                return false;
            }
            activity.startActivityForResult(intent, INTENT_TAKE);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /***
     * 选择一张图片
     * 图片类型，这里是image/*，当然也可以设置限制
     * 如：image/jpeg等
     *
     * @param activity Activity
     */
    public boolean selectPicture(Activity activity) {
        try {
            Intent intent;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                intent = new Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            } else {
                intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            }
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            if (!isIntentAvailable(activity, intent)) {
                return false;
            }
            activity.startActivityForResult(intent, INTENT_SELECT);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取原图的保存路径
     *
     * @param activity
     * @return
     */
    private File getOriginalFile(Activity activity) {

        try {
            mImageName = "crop" + AppDateMgr.todayYyyyMmDdHhMmSs(AppDateMgr.DFYYYYMMDDHHMMSS) + ".jpg";
            File file = new File(activity.getExternalFilesDir("crop"), mImageName);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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
            case INTENT_TAKE://拍照
                if (resultCode == Activity.RESULT_OK) {
                    File _file = new File(activity.getExternalFilesDir("crop"), mImageName);
                    if (_file.exists()) {
                        compressFile(_file, activity);
                    } else {
                        onPhotoResultListener.onPhotoCancel();
                    }
                } else {
                    onPhotoResultListener.onPhotoCancel();
                }
                break;
            case INTENT_SELECT:  //选择图片//content://com.android.providers.media.documents/document/image%3A811598
                if (resultCode == Activity.RESULT_OK && data.getData() != null) {
                    String imagePath;
                    if (Build.VERSION.SDK_INT >= 19) {
                        //4.4 及以上系统使用这个方法处理图片
                        imagePath = handleImageOnKitKat(activity, data);
                    } else {
                        //4.4 及以下系统使用这个方法处理图片
                        imagePath = handleImageBeforeKitKat(activity, data);
                    }
                    File file = new File(imagePath);
                    if (file.exists()) {
                        compressFile(file, activity);
                    } else {
                        onPhotoResultListener.onPhotoCancel();
                    }
                } else {
                    onPhotoResultListener.onPhotoCancel();
                }
                break;
        }
    }

    private String handleImageOnKitKat(Activity activity, Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        //data是从相册返回的数据
        //android 7.1.1
        //uri == content://com.android.providers.media.documents/document/image%3A75
        //uri.getAuthority() == com.android.providers.media.documents
        //uri.getPath() == /document/image:75
        //DocumentsContract.getDocumentId(uri) == image:75
        //MediaStore.Images.Media.EXTERNAL_CONTENT_URI == content://media/external/images/media
        //真实路径 path == /storage/emulated/0/Download/picture.jpg

        //android4.4
        //uri == content://com.android.providers.media.documents/document/image%3A28
        //uri.getAuthority() == com.android.providers.media.documents
        //uri.getPath() == /document/image:28
        //DocumentsContract.getDocumentId(uri) == image:28
        //MediaStore.Images.Media.EXTERNAL_CONTENT_URI == content://media/external/images/media
        //真实路径 path == /storage/sdcard/images/picture.jpg

        //相册存了图片的id，并没有存实际路径。
        //Authority就是相册数据库的标识符，这里有两个数据库，他们的标识符分别为
        //com.android.providers.media.documents
        //com.android.providers.downloads.documents
        //当点击一张照片它会返回document封装了的uri，然后进行解析出资源id，
        //然后根据id在MediaStore数据库中获取真实URL路径

        //判断该Uri是否是document封装过的
        if (DocumentsContract.isDocumentUri(activity, uri)) {
            //如果是document类型的Uri,则通过document id 处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(activity, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                //这个方法负责把id和contentUri连接成一个新的Uri
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(docId));
                imagePath = getImagePath(activity, contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            //如果是content类型的Uri,则使用普通方式处理
            imagePath = getImagePath(activity, uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            //如果是file类型的Uri,直接获取图片路径即可
            imagePath = uri.getPath();
        }
        return imagePath;
    }

    private String handleImageBeforeKitKat(Activity activity, Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(activity, uri, null);
        return imagePath;
    }

    private String getImagePath(Activity activity, Uri uri, String selection) {
        String path = null;
        //通过Uri和selection来获取真实路径
        //Android系统提供了MediaScanner，MediaProvider，MediaStore等接口，并且提供了一套数据库
        //表格，通过Content Provider的方式提供给用户。当手机开机或者有SD卡插拔等事件发生时，系统
        //将会自动扫描SD卡和手机内存上的媒体文件，如audio，video，图片等，将相应的信息放到定义好
        //的数据库表格中。在这个程序中，我们不需要关心如何去扫描手机中的文件，只要了解如何查询和使
        //用这些信息就可以了。MediaStore中定义了一系列的数据表格，通过ContentResolver提供的查询
        //接口，我们可以得到各种需要的信息。
        //EXTERNAL_CONTENT_URI 为查询外置内存卡的，INTERNAL_CONTENT_URI为内置内存卡。
        //MediaStore.Audio获取音频信息的类
        //MediaStore.Images获取图片信息
        //MediaStore.Video获取视频信息
        Cursor cursor = activity.getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToNext()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }


    /**
     * [回调监听类]
     *
     * @author huxinwu
     * @version 1.0
     * @date 2015-1-7
     **/
    public interface OnPhotoResultListener {
        void onPhotoResult(Uri uri);

        void onPhotoCancel();
    }

    public OnPhotoResultListener getOnPhotoResultListener() {
        return onPhotoResultListener;
    }

    public void setOnPhotoResultListener(OnPhotoResultListener onPhotoResultListener) {
        this.onPhotoResultListener = onPhotoResultListener;
    }

    private void compressFile(File file, Context context) {
        try {
            Tiny.FileCompressOptions compressOptions = new Tiny.FileCompressOptions();
            compressOptions.config = Bitmap.Config.RGB_565;
            Tiny.getInstance().source(file).asFile().withOptions(compressOptions).compress(new FileCallback() {
                @Override
                public void callback(boolean isSuccess, String outfile, Throwable t) {
                    if (isSuccess) {
                        File file = new File(outfile);
                        LogUtils.i(TAG, "outfile path  ----> " + outfile);
                        LogUtils.i(TAG, "compress info len ----> " + file.length());
                        Uri uri = Uri.fromFile(file);
                        onPhotoResultListener.onPhotoResult(uri);
                    } else {
                        LogUtils.e(TAG, "compress file failed!");
                        Uri uri = Uri.fromFile(file);
                        onPhotoResultListener.onPhotoResult(uri);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            onPhotoResultListener.onPhotoCancel();
        }
    }

}
