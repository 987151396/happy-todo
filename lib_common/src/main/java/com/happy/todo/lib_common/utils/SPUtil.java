package com.happy.todo.lib_common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class SPUtil {


    private static SPUtil sSPUtil = null;

    private Context mContext;

    public static SPUtil newInstance() {
        if (sSPUtil == null) {
            synchronized (SPUtil.class) {
                if (sSPUtil == null) {
                    sSPUtil = new SPUtil();
                }
            }
        }
        return sSPUtil;
    }

    public void init(Context context) {
        mContext = context;
    }

    /**
     * SharedPreferences存储在sd卡中的文件名字
     */
    private String getSpName() {
        return mContext.getPackageName() + "_preferences";
    }

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     */
    public void putAndApply(String key, Object o) {

        SharedPreferences sp = mContext.getSharedPreferences(getSpName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if (o == null) {
            editor.putString(key, "");
        } else {
            if (o instanceof String) {
                editor.putString(key, (String) o);
            } else if (o instanceof Integer) {
                editor.putInt(key, (Integer) o);
            } else if (o instanceof Float) {
                editor.putFloat(key, (Float) o);
            } else if (o instanceof Long) {
                editor.putLong(key, (Long) o);
            } else if (o instanceof Boolean) {
                editor.putBoolean(key, (Boolean) o);
            } else {
                editor.putString(key, o.toString());
            }
        }
        //提交
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 得到保存数据的方法，我们根据!!!默认值的类型!!!得到保存的数据的具体类型，然后调用相对于的方法获取值
     */
    public Object get(String key, Object defaultObject) {
        SharedPreferences sp = mContext.getSharedPreferences(getSpName(), Context.MODE_PRIVATE);

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
    public void remove(String key) {
        SharedPreferences sp = mContext.getSharedPreferences(getSpName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 移除某些key值已经对应的值
     */
    public void remove(String... keys) {
        if (keys == null || keys.length == 0)
            return;
        SharedPreferences sp = mContext.getSharedPreferences(getSpName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        for (String s : keys) {
            editor.remove(s);
        }
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 清除所有数据
     */
    public void clear() {
        SharedPreferences sp = mContext.getSharedPreferences(getSpName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 查询某个key是否已经存在
     */
    public boolean contains(String key) {
        SharedPreferences sp = mContext.getSharedPreferences(getSpName(), Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 查询某些key是否已经存在
     */
    public boolean contains(String... keys) {
        SharedPreferences sp = mContext.getSharedPreferences(getSpName(), Context.MODE_PRIVATE);
        boolean isOk = true;
        for (String key : keys) {
            if (!sp.contains(key)) {
                isOk = false;
                break;
            }
        }
        return isOk;
    }

    /**
     * 返回所有的键值对
     */
    public Map<String, ?> getAll() {
        SharedPreferences sp = mContext.getSharedPreferences(getSpName(), Context.MODE_PRIVATE);
        return sp.getAll();
    }

    //*************************************内部类*******************************************//

    /**
     * 优先使用SharedPreferences的apply方法，如果找不到则使用commit方法
     */
    private static class SharedPreferencesCompat {

        //查看SharedPreferences是否有apply方法
        private static final Method sApplyMethod = findApply();

        private static Method findApply() {

            try {
                Class cls = SharedPreferences.class;
                return cls.getMethod("apply");
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         */
        public static void apply(SharedPreferences.Editor editor) {

            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }

            editor.commit();
        }
    }


    /**
     * 存放对象
     * @param keyName
     * @param object
     * @param <T>
     */
    public <T> void saveObject(String keyName, T object) {
        Gson gson = new Gson();
        String objectJson = gson.toJson(object);
        putAndApply(keyName, objectJson);
    }

    /**
     * 获取对象
     * @param keyName
     * @param type
     * @param <T>
     * @return
     */
    public  <T> T getObject(String keyName, Class<T> type) {
        String objectJson = (String) get(keyName, "");
        if (TextUtils.isEmpty(objectJson)) {
            return null;
        }
        Gson gson = new Gson();

        return gson.fromJson(objectJson, type);
    }

}
