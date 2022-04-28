package com.happy.todo.lib_common.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: TwoSX
 * @description: 键盘操作工具类
 * @projectName: iapp2
 * @date: 2017/11/24
 * @time: 下午5:28
 */
public class KeyboardUtil {
    /**
     * 显示软键盘的延迟时间
     */
    public static final int SHOW_KEYBOARD_DELAY_TIME = 200;
    private static final String TAG = "QMUIKeyboardHelper";
    private final static int KEYBOARD_VISIBLE_THRESHOLD_DP = 100;

    /**
     * EditText获取焦点并显示软键盘
     */
    public static void showSoftInputFromWindow(Activity activity, EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        //显示软键盘
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        //如果上面的代码没有弹出软键盘 可以使用下面另一种方式
        //InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        // imm.showSoftInput(editText, 0);
    }

    /**
     * 针对给定的editText显示软键盘（editText会先获得焦点）. 可以和{@link #hideKeyboard(View)}
     * 搭配使用，进行键盘的显示隐藏控制。
     */
    public static void showKeyboard(final EditText editText, boolean delay) {
        if (null == editText)
            return;

        if (!editText.requestFocus()) {
            Log.w(TAG, "showSoftInput() can not get focus");
            return;
        }
        if (delay) {
            editText.postDelayed(new Runnable() {
                @Override
                public void run() {
                    InputMethodManager imm = (InputMethodManager) editText.getContext()
                            .getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
                }
            }, SHOW_KEYBOARD_DELAY_TIME);
        } else {
            InputMethodManager imm = (InputMethodManager) editText.getContext()
                    .getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    /**
     * 隐藏软键盘 可以和{@link #showKeyboard(EditText, boolean)}搭配使用，进行键盘的显示隐藏控制。
     *
     * @param view 当前页面上任意一个可用的view
     */
    public static boolean hideKeyboard(final View view) {
        if (null == view)
            return false;

        InputMethodManager inputManager = (InputMethodManager) view.getContext()
                .getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        // 即使当前焦点不在editText，也是可以隐藏的。
        return inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager
                .HIDE_NOT_ALWAYS);
    }

    /**
     * 监听键盘弹出隐藏
     *
     * @param activity Activity
     * @param listener KeyboardVisibilityEventListener
     */
    @SuppressWarnings("deprecation")
    public static void setVisibilityEventListener(final Activity activity, final
    KeyboardVisibilityEventListener listener) {

        if (activity == null) {
            throw new NullPointerException("Parameter:activity must not be null");
        }

        if (listener == null) {
            throw new NullPointerException("Parameter:listener must not be null");
        }

        final View activityRoot = ViewUtil.getActivityRoot(activity);

        final ViewTreeObserver.OnGlobalLayoutListener layoutListener = new ViewTreeObserver
                .OnGlobalLayoutListener() {

            private final Rect r = new Rect();

            private final int visibleThreshold = Math.round(SizeUtil.dp2px(
                    KEYBOARD_VISIBLE_THRESHOLD_DP));

            private boolean wasOpened = false;

            @Override
            public void onGlobalLayout() {
                activityRoot.getWindowVisibleDisplayFrame(r);

                int usableHeightNow = r.height();

                // 高度差 ：视图高度减去可视高度得出  高度差相当于键盘高度
                int heightDiff = activityRoot.getRootView().getHeight() - usableHeightNow;

                boolean isOpen = heightDiff > visibleThreshold;

                if (isOpen == wasOpened) {
                    // keyboard state has not changed
                    return;
                }

                wasOpened = isOpen;

                listener.onVisibilityChanged(isOpen, heightDiff);
            }

        };
        activityRoot.getViewTreeObserver().addOnGlobalLayoutListener(layoutListener);
        activity.getApplication().registerActivityLifecycleCallbacks(new AutoActivityLifecycleCallback(activity) {
            @Override
            protected void onTargetActivityDestroyed() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    activityRoot.getViewTreeObserver().removeOnGlobalLayoutListener(layoutListener);
                } else {
                    activityRoot.getViewTreeObserver().removeGlobalOnLayoutListener(layoutListener);
                }
            }
        });
    }


    /**
     * 确定键盘是否可见
     *
     * @param activity Activity
     * @return Whether keyboard is visible or not
     */
    public static boolean isKeyboardVisible(Activity activity) {
        Rect r = new Rect();

        View activityRoot = ViewUtil.getActivityRoot(activity);
        int visibleThreshold = Math.round(SizeUtil.dp2px(
                KEYBOARD_VISIBLE_THRESHOLD_DP));

        activityRoot.getWindowVisibleDisplayFrame(r);

        int heightDiff = activityRoot.getRootView().getHeight() - r.height();

        return heightDiff > visibleThreshold;
    }


    public interface KeyboardVisibilityEventListener {

        void onVisibilityChanged(boolean isOpen, int height);
    }


    // 根据 EditText 所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘
    public static boolean isShouldHideKeyboard(Activity activity, MotionEvent event) {
        List<View> viewList = getAllEditViews(activity.getWindow().getDecorView());

        boolean isOk = false;

        for (View v : viewList) {
            if (v != null) {
                int[] l = {0, 0};
                v.getLocationInWindow(l);
                int left = l[0],
                        top = l[1],
                        bottom = top + v.getHeight(),
                        right = left + v.getWidth();
                isOk = event.getX() > left && event.getX() < right
                        && event.getY() > top && event.getY() < bottom;
                if (isOk)
                    break;
            }
        }


        return !isOk;
    }

    private static List<View> getAllEditViews(View parent) {

        List<View> allchildren = new ArrayList<View>();

        if (parent instanceof ViewGroup) {

            ViewGroup vp = (ViewGroup) parent;

            for (int i = 0; i < vp.getChildCount(); i++) {

                View viewchild = vp.getChildAt(i);

                if (viewchild instanceof EditText) {
                    allchildren.add(viewchild);
                }

                allchildren.addAll(getAllEditViews(viewchild));

            }

        }

        return allchildren;
    }


}
