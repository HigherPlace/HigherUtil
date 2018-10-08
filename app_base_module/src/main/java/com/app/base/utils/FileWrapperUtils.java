package com.app.base.utils;


import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import com.app.base.BaseApplication;
import com.app.base.R;
import com.bryan.common.utils.FileUtils;

import java.io.File;

/**
 * 获取存储的文件路径
 * <p>
 * by bryan
 * <p>
 * Context.getExternalCacheDir().getPath();//Android/data/package/cache,APP被卸载数据会跟着被清除，对应着清除缓存
 * Context..getCacheDir().getPath();// /data/data//cache
 * <p>
 */
public class FileWrapperUtils {

    private static String File_Common_Cache_Folder = "cache";//通用的缓存文件
    private static String File_App_Folder = "/apk"; // apk文件在sdcard上的文件目录

    /**
     * 生成文件
     */
    public static File getTargetFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    /**
     * 获取缓存企业信息的文件存储路径
     */
    public static String getEnterpriseCacheFilePath() {
        String fileCachePath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            fileCachePath = BaseApplication.getGlobalContext().getExternalCacheDir().getPath() + File.separator + File_Common_Cache_Folder;
        } else {
            fileCachePath = BaseApplication.getGlobalContext().getCacheDir().getPath();
        }
        return fileCachePath;
    }

    /**
     * 获取通用缓存图片的路径
     */
    public static String getCommonBmpCachePath() {
        String cachePath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = BaseApplication.getGlobalContext().getExternalCacheDir().getPath() + File.separator + File_Common_Cache_Folder;
        } else {
            cachePath = BaseApplication.getGlobalContext().getCacheDir().getPath();
        }
        return cachePath;
    }

    /**
     * 获取通用文件缓存路径
     */
    public static String getCommonFileCachePath() {
        String cachePath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = BaseApplication.getGlobalContext().getExternalFilesDir(null).getPath();
        } else {
            cachePath = BaseApplication.getGlobalContext().getCacheDir().getPath();
        }
        return cachePath;
    }

    /**
     * 获取apk文件所在的目录
     *
     * @return
     */
    public static File getAPKFolder() {
        return FileUtils.getFolderFromExternalFilesDir(BaseApplication.getGlobalContext(),
                File_App_Folder);
    }

    /**
     * 获取保存图片的目录，用于给用户查看
     */
    public static String getSaveBmpPath() {
        String cachePath = null;
        boolean isSuccess = false;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + BaseApplication.getGlobalContext().getString(R.string.app_name) + File.separator + "图片");
            if (!file.exists()) {
                isSuccess = file.mkdirs();
            } else {
                isSuccess = true;
            }
            if (isSuccess) {
                return cachePath = file.getAbsolutePath();
            } else {
                return cachePath = BaseApplication.getGlobalContext().getCacheDir().getPath();
            }
        } else {
            return cachePath = BaseApplication.getGlobalContext().getCacheDir().getPath();
        }
    }


    public static Uri getResourceUri(Context context, int res) {
        try {
            Context packageContext = context.createPackageContext(context.getPackageName(),
                    Context.CONTEXT_RESTRICTED);
            Resources resources = packageContext.getResources();
            String appPkg = packageContext.getPackageName();
            String resPkg = resources.getResourcePackageName(res);
            String type = resources.getResourceTypeName(res);
            String name = resources.getResourceEntryName(res);

            Uri.Builder uriBuilder = new Uri.Builder();
            uriBuilder.scheme(ContentResolver.SCHEME_ANDROID_RESOURCE);
            uriBuilder.encodedAuthority(appPkg);
            uriBuilder.appendEncodedPath(type);
            if (!appPkg.equals(resPkg)) {
                uriBuilder.appendEncodedPath(resPkg + ":" + name);
            } else {
                uriBuilder.appendEncodedPath(name);
            }
            return uriBuilder.build();

        } catch (Exception e) {
            return null;
        }
    }


    /********************************************Uri转Path Start*****************************************/
    /**
     * Get a file path from a Uri. This will get the the path for Storage Access
     * Framework Documents, as well as the _data field for the MediaStore and
     * other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri     The Uri to query.
     * @author paulburke
     */
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /********************************************Uri转Path End*****************************************/
}
