
package com.bryan.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;

public class SPUtil {

    /**
     * SharedPreferences存储在sd卡中的文件名字
     */
    private static String getSpName(Context context) {
        return context.getPackageName() + "_preferences";
    }

    /**
     * SharedPreferences存储在sd卡中的文件名字
     */
    private static String getSpName(Context context, String fileName) {
        return context.getPackageName() + "_" + fileName;
    }

    public static void putString(Context context, String key, String value) {
        if (value == null) {
            value = "";
        }
        SharedPreferences sp = context.getApplicationContext().getSharedPreferences(getSpName(context), Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.putString(key, value);
        SharedPreferencesCompat.apply(editor);
    }

    public static void putString(Context context, String key, String value, String fileName) {
        if (value == null) {
            value = "";
        }
        SharedPreferences sp = context.getApplicationContext().getSharedPreferences(getSpName(context, fileName), Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.putString(key, value);
        SharedPreferencesCompat.apply(editor);
    }

    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences sp = context.getApplicationContext().getSharedPreferences(getSpName(context), Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.putBoolean(key, value);
        SharedPreferencesCompat.apply(editor);
    }

    public static void putBoolean(Context context, String key, boolean value, String fileName) {
        SharedPreferences sp = context.getApplicationContext().getSharedPreferences(getSpName(context,fileName), Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.putBoolean(key, value);
        SharedPreferencesCompat.apply(editor);
    }

    public static void putInt(Context context, String key, int value) {
        SharedPreferences sp = context.getApplicationContext().getSharedPreferences(getSpName(context), Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.putInt(key, value);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 取出key对应的String值
     *
     * @param context
     * @param key
     * @return 返回key对应的String值，若没有则返回""
     */
    public static String getString(Context context, String key) {
        SharedPreferences sp = context.getApplicationContext().getSharedPreferences(getSpName(context), Context.MODE_PRIVATE);
        return sp.getString(key, "");
    }

    /**
     * 取出指定文件中对应的key所对应的值
     */
    public static String getString(Context context, String key, String fileName) {
        SharedPreferences sp = context.getApplicationContext().getSharedPreferences(getSpName(context, fileName), Context.MODE_PRIVATE);
        return sp.getString(key, "");
    }

    /**
     * 取出key对应的boolen值
     *
     * @param context
     * @param key
     * @return 返回key对应的boolen值，若没有则返回false
     */
    public static Boolean getBoolean(Context context, String key) {
        SharedPreferences sp = context.getApplicationContext().getSharedPreferences(getSpName(context), Context.MODE_PRIVATE);
        return sp.getBoolean(key, false);
    }

    public static Boolean getBoolean(Context context, String key, String fileName) {
        SharedPreferences sp = context.getApplicationContext().getSharedPreferences(getSpName(context, fileName), Context.MODE_PRIVATE);
        return sp.getBoolean(key, false);
    }


    /**
     * 取出key对应的int值
     *
     * @param context
     * @param key
     * @return 返回key对应的int值，若没有则返回0
     */
    public static int getInt(Context context, String key) {
        SharedPreferences sp = context.getApplicationContext().getSharedPreferences(getSpName(context), Context.MODE_PRIVATE);
        return sp.getInt(key, 0);
    }

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     */
    public static void putAndApply(Context context, String key, Object object) {
        SharedPreferences sp = context.getApplicationContext().getSharedPreferences(getSpName(context), Context.MODE_PRIVATE);
        Editor editor = sp.edit();

        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }

        SharedPreferencesCompat.apply(editor);
    }

    public static boolean putSharedPreferences(Context context, String fileName, Map<String, Object> map) {
        boolean flag = false;

        SharedPreferences preferences = context.getApplicationContext().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();

        for (Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object object = entry.getValue();
            try {
                if (object instanceof String) {
                    String value = (String) object;
                    editor.putString(key, value);
                } else if (object instanceof Integer) {
                    Integer value = (Integer) object;
                    editor.putInt(key, value);
                } else if (object instanceof Boolean) {
                    Boolean value = (Boolean) object;
                    editor.putBoolean(key, value);
                } else if (object instanceof Float) {
                    Float value = (Float) object;
                    editor.putFloat(key, value);
                } else if (object instanceof Long) {
                    Long value = (Long) object;
                    editor.putLong(key, value);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        flag = editor.commit();
        return flag;
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     */
    public static Object get(Context context, String key, Object defaultObject) {
        SharedPreferences sp = context.getApplicationContext().getSharedPreferences(getSpName(context), Context.MODE_PRIVATE);

        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        } else {
            return null;
        }
    }

    /**
     * 移除某个key值已经对应的值
     */
    public static void remove(Context context, String key) {
        SharedPreferences sp = context.getApplicationContext().getSharedPreferences(getSpName(context), Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 清除所有数据
     */
    public static void clear(Context context) {
        SharedPreferences sp = context.getApplicationContext().getSharedPreferences(getSpName(context), Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 查询某个key是否已经存在
     */
    public static boolean contains(Context context, String key) {
        SharedPreferences sp = context.getApplicationContext().getSharedPreferences(getSpName(context), Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     */
    public static Map<String, ?> getAll(Context context) {
        SharedPreferences sp = context.getApplicationContext().getSharedPreferences(getSpName(context), Context.MODE_PRIVATE);
        return sp.getAll();
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     *
     * @author zhy
     */
    private static class SharedPreferencesCompat {

        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }

            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         */
        public static void apply(Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException expected) {
            } catch (IllegalAccessException expected) {
            } catch (InvocationTargetException expected) {
            }
            editor.commit();
        }
    }


}
