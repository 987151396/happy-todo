package com.happy.todo.lib_common.utils;

/**
 * @Describe：判断重复点击工具类
 * @Date： 2019/02/25
 * @Author： dengkewu
 * @Contact：
 */
public class ViewOnClickUtils {
    private static final int MIN_DELAY_TIME= 500;  // 两次点击间隔不能少于500ms
    private static long lastClickTime;
    private static int lastButtonId = -1;

    /**
     * 判断是否快速点击
     * @return
     */
    public static boolean isFastClick() {
        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();
        if ((currentClickTime - lastClickTime) >= MIN_DELAY_TIME) {
            flag = false;
        }
        lastClickTime = currentClickTime;
        return flag;
    }

    /**
     * 判断是否快速点击
     * @param time 设置时间
     * @return
     */
    public static boolean isFastClick(int time) {
        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();
        if ((currentClickTime - lastClickTime) >= time) {
            flag = false;
        }
        lastClickTime = currentClickTime;
        return flag;
    }

    /**
     * 根据id判断是否是快速点击
     */
    public static boolean isFastDoubleClick(int buttonId) {
        return isFastDoubleClick(buttonId, MIN_DELAY_TIME);
    }

    /**
     * 根据id判断是否是快速点击
     */
    public static boolean isFastDoubleClick(int buttonId, long diff) {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (lastButtonId == buttonId && lastClickTime > 0 && timeD < diff) {
            // Log.v("isFastDoubleClick", "短时间内按钮多次触发");
            return true;
        }
        lastClickTime = time;
        lastButtonId = buttonId;
        return false;
    }
}
