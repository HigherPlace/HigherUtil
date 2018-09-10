package com.app.base.utils.user;

import android.content.Context;
import android.os.Environment;

import com.app.base.BaseApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static com.bryan.common.utils.FileUtils.sdcardIsEnable;


/**
 * Created by Administrator on 2017/3/3 0003.
 */

public class Utils {

    /**
     * 获取文件(夹)对象，若该文件(夹)不存在，则创建
     *
     * @param path 文件夹的绝对路径
     * @return
     */
    public static File getInternalFolder(String path) {
        File folder = null;
        if (sdcardIsEnable()) {
            folder = new File(BaseApplication.getGlobalContext().getFilesDir(),
                    path);
            if (!folder.exists()) {
                folder.mkdirs();
            }
        }
        return folder;
    }

    /**
     * 获取文件(夹)对象，若该文件(夹)不存在，则创建
     *
     * @param path 文件夹的绝对路径
     * @return
     */
    public static File getExternalFolder(String path) {
        File folder = null;
        if (sdcardIsEnable()) {
            folder = new File(BaseApplication.getGlobalContext().getExternalFilesDir(null),
                    path);
            if (!folder.exists()) {
                folder.mkdirs();
            }
        }
        return folder;
    }

    /**
     * 把全局变量序列化到本地
     */
    public static final void saveObject(String path, String fileName,
                                        Object saveObject) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            File folder = new File(path);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            File f = new File(folder, fileName);
            try {
                fos = new FileOutputStream(f);
                oos = new ObjectOutputStream(fos);
                oos.writeObject(saveObject);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (oos != null) {
                        oos.close();
                    }
                    if (fos != null) {
                        fos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 反序列化
     */
    public static final Object restoreObject(String path) {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        Object object = null;
        File f = new File(path);
        if (!f.exists()) {
            return null;
        }
        try {
            fis = new FileInputStream(f);
            ois = new ObjectInputStream(fis);
            object = ois.readObject();
            return object;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return object;
    }

    /**
     * 保存信息到文件中
     *
     * @param ctx
     * @param str     信息
     * @param filName 文件名
     */
    public static void saveInfo(Context ctx, String str, String filName) {
        FileOutputStream fos = null;
        try {
            String path = Utils.getIntechStorageDir(ctx) + "/" + filName + "/";
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(path + filName);
            if (!file.exists())
                file.createNewFile();
            if (file.length() > 1024 * 10) {// 大于10kb则自动删除
                file.delete();
                file.createNewFile();
            }
            fos = new FileOutputStream(file, true);
            fos.write((str + "\n").getBytes());
            fos.flush();
        } catch (Exception e) {
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                    fos = null;
                } catch (IOException e) {
                }
            }
        }
    }

    public static String getIntechStorageDir(Context context) {
        String state = Environment.getExternalStorageState();
        boolean imcc_dir_exist = false;
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "智慧云");
            if (!file.exists()) {
                if (file.mkdir())
                    imcc_dir_exist = true;
                else
                    imcc_dir_exist = false;
            } else {
                imcc_dir_exist = true;
            }
        }

        if (imcc_dir_exist) {
            return Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "智慧云";
        } else {
            return Environment.getDataDirectory().getAbsolutePath() + "/data" + "/" + context.getPackageName();
        }
    }

    public static String getBitmapCacheDir(Context context) {
        String state = Environment.getExternalStorageState();
        boolean imcc_dir_exist = false;
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "智慧云办公");
            if (!file.exists()) {
                if (file.mkdir())
                    imcc_dir_exist = true;
                else
                    imcc_dir_exist = false;
            } else {
                imcc_dir_exist = true;
            }
        }

        if (imcc_dir_exist) {
            return Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "智慧云办公";
        } else {
            return Environment.getDataDirectory().getAbsolutePath() + "/data" + "/" + context.getPackageName();
        }
    }
}
