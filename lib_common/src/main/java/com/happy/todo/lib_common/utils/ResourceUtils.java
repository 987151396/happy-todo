package com.happy.todo.lib_common.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import androidx.core.content.ContextCompat;

import com.happy.todo.lib_common.utils.language.LanguageUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 读取资源文件的工具类
 * Created by chenmingzhen on 16-6-7.
 */
public class ResourceUtils {
    private static final String TAG = ResourceUtils.class.getSimpleName();

    /**
     * 读取Assert目录下的文件
     *
     * @param context
     * @param fileName
     * @return
     */
    public static String getFromAssets(Context context, String fileName) {
        String Result = "";
        InputStreamReader inputReader = null;
        BufferedReader bufReader = null;
        try {
            inputReader = new InputStreamReader(context.getResources().getAssets().open(fileName));
            bufReader = new BufferedReader(inputReader);
            String line;

            while ((line = bufReader.readLine()) != null)
                Result += line;
        } catch (Exception e) {
            AppLogUtil.e("error!", e);
        } finally {
            try {
                if (bufReader != null) {
                    bufReader.close();
                }
                if (inputReader != null) {
                    inputReader.close();
                }
            } catch (IOException e) {
                AppLogUtil.d(TAG, e.getMessage());
            }
        }
        return Result;
    }


    public static Resources getResources() {
        return Utils.getApp().getResources();
    }

    /**
     * 获取string.xml下的字符串资源
     *
     * @param resId
     * @param formatArgs
     * @return
     */
    public static String getString(int resId, Object... formatArgs) {
        try {
            return LanguageUtil.getStringByLocale(resId, formatArgs);
        } catch (Exception e) {
            AppLogUtil.d(TAG, e.getMessage());
        }
        return StringUtils.EMPTY;
    }

    /**
     * 获取字符串数组
     *
     * @param resId
     * @return
     */
    public static String[] getStringArray(int resId) {
        try {
            return getResources().getStringArray(resId);
        } catch (Exception ex) {
            AppLogUtil.e(TAG, ex);
        }
        return null;
    }

    /**
     * 获取颜色
     *
     * @param resId
     * @return
     */
    public static int getColor(int resId) {
        return ContextCompat.getColor(Utils.getApp(), resId);
    }


    public static Drawable getDrawable(int resId) {
        return ContextCompat.getDrawable(Utils.getApp(), resId);
    }


    public static float getDimens(int resId) {
        return getResources().getDimension(resId);
    }

    public static int getMipmap(Context context, String imageName) {
//        Context ctx=getBaseContext();
        int resId = getResources().getIdentifier(imageName, "mipmap", context.getPackageName());
        //如果没有在"mipmap"下找到imageName,将会返回0
        return resId;
    }

}
