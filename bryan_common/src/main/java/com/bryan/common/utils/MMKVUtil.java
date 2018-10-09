
package com.bryan.common.utils;

import android.content.Context;
import android.util.Log;

import com.bryan.common.utils.encrypt.TextUtils;
import com.tencent.mmkv.MMKV;

import java.util.Map;

/**
 * 使用方法，再Application中初始化即可正常使用
 * 效率是SharePreference的100倍
 */
public class MMKVUtil {

    private MMKVUtil() {
    }

    /**
     * 在程序初始化的时候调用, 只需调用一次
     */
    public static void init(Context _context) {
        String rootDir = MMKV.initialize(_context);
        Log.i("mmkv", "path:" + rootDir);
    }


    public static void putString(String key, String value) {
        if (value == null) {
            value = "";
        }
        MMKV.defaultMMKV().encode(key, value);

    }

    /**
     * @param fileName 注意文件名不能有\等特殊符号
     * @param key
     * @param value
     */
    public static void putString(String fileName, String key, String value) {
        if (value == null) {
            value = "";
        }
        MMKV.mmkvWithID(fileName).encode(key, value);
    }

    /**
     * 取出key对应的String值
     *
     * @param key
     * @return 返回key对应的String值，若没有则返回""
     */
    public static String getString(String key) {
        String result = MMKV.defaultMMKV().decodeString(key);
        if (TextUtils.isEmpty(result))
            result = "";
        return result;
    }

    /**
     * 取出指定文件中对应的key所对应的值
     */
    public static String getString(String fileName, String key) {
        String result = MMKV.mmkvWithID(fileName).decodeString(key);
        if (TextUtils.isEmpty(result))
            result = "";
        return result;
    }

    /**
     * 取出key对应的String值
     *
     * @param key
     * @return 返回key对应的String值，若没有则返回""
     */
    public static String getStringWithDefault(String key, String defaultValue) {
        String result = MMKV.defaultMMKV().decodeString(key, defaultValue);
        if (TextUtils.isEmpty(result))
            result = "";
        return result;
    }

    /**
     * 取出指定文件中对应的key所对应的值
     */
    public static String getStringWithDefault(String fileName, String key, String defaultValue) {
        String result = MMKV.mmkvWithID(fileName).decodeString(key, defaultValue);
        if (TextUtils.isEmpty(result))
            result = "";
        return result;
    }

    public static void putBoolean(String key, boolean value) {
        MMKV.defaultMMKV().encode(key, value);
    }

    public static void putBoolean(String fileName, String key, boolean value) {
        MMKV.mmkvWithID(fileName).encode(key, value);
    }

    /**
     * 取出key对应的boolen值
     *
     * @param key
     * @return 返回key对应的boolen值，若没有则返回false
     */
    public static Boolean getBoolean(String key) {
        return MMKV.defaultMMKV().decodeBool(key);
    }

    public static Boolean getBoolean(String fileName, String key) {
        return MMKV.mmkvWithID(fileName).decodeBool(key);
    }


    public static void putInt(String key, int value) {
        MMKV.defaultMMKV().encode(key, value);
    }

    public static void putInt(String fileName, String key, int value) {
        MMKV.mmkvWithID(fileName).encode(key, value);
    }

    /**
     * 取出key对应的int值
     *
     * @param key
     * @return 返回key对应的int值，若没有则返回0
     */
    public static int getInt(String key) {
        return MMKV.defaultMMKV().decodeInt(key);
    }

    /**
     * 取出key对应的int值
     *
     * @param key
     * @return 返回key对应的int值，若没有则返回0
     */
    public static int getInt(String fileName, String key) {
        return MMKV.mmkvWithID(fileName).decodeInt(key);
    }

    /**
     * 移除某个key值已经对应的值
     */
    public static void remove(String key) {
        MMKV.defaultMMKV().remove(key);
    }

    /**
     * 移除某个key值已经对应的值
     */
    public static void remove(String fileName, String key) {
        MMKV.mmkvWithID(fileName).remove(key);
    }

    /**
     * 清除所有数据
     */
    public static void clear() {
        MMKV.defaultMMKV().clearAll();
    }

    /**
     * 清除所有数据
     */
    public static void clear(String fileName) {
        MMKV.mmkvWithID(fileName).clearAll();
    }

    /**
     * 查询某个key是否已经存在
     */
    public static boolean contains(String key) {
        return MMKV.defaultMMKV().contains(key);
    }

    /**
     * 查询某个key是否已经存在
     */
    public static boolean contains(String fileName, String key) {
        return MMKV.mmkvWithID(fileName).contains(key);
    }

    /**
     * 返回所有的键值对
     */
    public static Map<String, ?> getAll() {
        return MMKV.defaultMMKV().getAll();
    }

}
