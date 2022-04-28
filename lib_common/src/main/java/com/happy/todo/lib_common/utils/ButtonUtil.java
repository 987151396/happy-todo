package com.happy.todo.lib_common.utils;

/**
 * @作者: TwoSX
 * @时间: 2017/12/21 下午6:48
 * @描述:
 */
public class ButtonUtil {
    private static long lastClickTime = 0;
    private static long DIFF = 500;
    private static int lastButtonId = -1;

    /**
     * 判断两次点击的间隔，如果小于500，则认为是多次无效点击
     *
     * @return
     */
    public static boolean isFastDoubleClick() {
        return isFastDoubleClick(-1, DIFF);
    }

    /**
     * 判断两次点击的间隔，如果小于500，则认为是多次无效点击
     */
    public static boolean isFastDoubleClick(int buttonId) {
        return isFastDoubleClick(buttonId, DIFF);
    }

    /**
     * 判断两次点击的间隔，如果小于diff，则认为是多次无效点击
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

    public static boolean isFastClick() {
        long ClickingTime = System.currentTimeMillis();
        if (ClickingTime - lastClickTime < 1000) {
            return true;
        }
        lastClickTime = ClickingTime;
        return false;
    }
}
