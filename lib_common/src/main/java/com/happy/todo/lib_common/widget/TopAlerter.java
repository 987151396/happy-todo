package com.happy.todo.lib_common.widget;

import android.app.Activity;
import androidx.annotation.StringRes;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.happy.todo.lib_common.R;
import com.happy.todo.lib_common.utils.ResourceUtils;
import com.happy.todo.lib_common.utils.SizeUtil;
import com.happy.todo.lib_common.utils.Utils;
import com.happy.todo.lib_common.utils.language.LanguageUtil;
import com.tapadoo.alerter.Alert;
import com.tapadoo.alerter.Alerter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @作者: TwoSX
 * @时间: 2017/12/14 下午5:50
 * @描述:
 */
public class TopAlerter {

    public static void show(Activity activity, String title, String content, int bgColor, int
            icon) {
        Alerter alerter = Alerter.create(activity)
                .setTitle(title) //当前UI省略title
                .setText(content)
                .setTextAppearance(R.style.alerter_text_style)
                .setBackgroundColorRes(bgColor)
                .setIcon(icon);

        //需要获取到alter并修改其中的icon大小
        setIconSize(alerter, SizeUtil.dp2px(30), SizeUtil.dp2px(10));

        alerter.show();
    }


    public static void showSuccess(Activity activity, String content) {
        showSuccess(activity, null, content);
    }

    public static void showSuccess(Activity activity, @StringRes int content) {
        showSuccess(activity,null, ResourceUtils.getString(content));
    }

    public static void showSuccess(Activity activity, String title, String content) {
        show(activity, title, content, R.color.theme_color2, R.mipmap.ic_alerter_success);
    }

    public static void showSuccess(Activity activity, @StringRes int title, @StringRes int
            content) {
        show(activity, LanguageUtil.getStringByLocale(title), LanguageUtil.getStringByLocale(content), R
                .color.theme_color2, R.mipmap.ic_alerter_success);
    }

    public static void showError(Activity activity, String content) {
        showError(activity, null, content);
    }

    public static void showError(Activity activity, @StringRes int content) {
        showError(activity, null, Utils.getApp()
                .getString(content));
    }

    public static void showError(Activity activity, String title, String content) {
        show(activity, title, content, R.color.red_E05857, R.mipmap.ic_alerter_fail);
    }

    public static void showError(Activity activity, @StringRes int title, @StringRes int content) {
        show(activity, LanguageUtil.getStringByLocale(title), LanguageUtil.getStringByLocale(content), R
                .color.red_E05857, R.mipmap.ic_alerter_fail);
    }


    /**
     * 图标的大小,因为库是从gradle导入而不是本地导入的，所以只能通过反射设置
     * @param size 单位 px
     * @param marginLeft 单位px
     */
    private static void setIconSize(Alerter alerter, int size, int marginLeft) {
        try {
            Method method = alerter.getClass().getDeclaredMethod("getAlert");
            method.setAccessible(true);
            Alert alert = (Alert) method.invoke(alerter);

            //修改图标大小
            ImageView icon = alert.findViewById(R.id.ivIcon);
            icon.getLayoutParams().height = size;
            icon.getLayoutParams().width = size;
            icon.requestLayout();

            //修改文字间隔
            LinearLayout llyTitleAndText = alert.findViewById(R.id.llAlertTextContainer);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) llyTitleAndText.getLayoutParams();
            layoutParams.leftMargin = marginLeft;
            layoutParams.setMarginStart(marginLeft);
            llyTitleAndText.requestLayout();

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
