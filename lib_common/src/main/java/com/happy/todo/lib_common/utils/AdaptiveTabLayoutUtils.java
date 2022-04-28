package com.happy.todo.lib_common.utils;

import android.content.Context;
import android.util.Log;

import com.flyco.tablayout.SlidingTabLayout;

/**
 * @author Vegen
 * @creation_time 2018/11/19
 * @description 可以自适配宽度的 TabLayoutUtils
 */

public class AdaptiveTabLayoutUtils {

    /**
     * 适配宽度，小于屏幕全屏，大于屏幕不作处理
     * @param tabLayout
     */
    public static void fitWidth(final SlidingTabLayout tabLayout){
        if (tabLayout == null) return;

        final int tabCount = tabLayout.getTabCount();
        if (tabCount == 0) return;
        final float startTabPadding = tabLayout.getTabPadding();
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                float allTabWidth = 0;
                // 计算所有 Tab 的宽度总和
                for(int i = 0; i < tabCount; i ++){
                    allTabWidth += tabLayout.getTitleView(i).getMeasuredWidth();
                }
                // 与 tabLayout 的宽度对比
                int tabLayoutWidth = tabLayout.getMeasuredWidth();
                if (tabLayoutWidth > allTabWidth){
                    // 所有 tab 未占满 tabLayout
                    // 计算缺失部分宽度
                    float extraWidth = tabLayoutWidth - allTabWidth;
                    // 计算每个 tab 可以分得的宽度
                    float addPaddingWidth = px2dp(tabLayout.getContext(), (extraWidth / tabCount) / 2.0f);
                    // 重设 tabPadding
                    tabLayout.setTabPadding(px2dp(tabLayout.getContext(), startTabPadding) + addPaddingWidth);

                    Log.e("AdaptiveTabLayoutUtils", "allTabWidth:" + allTabWidth + "  tabLayoutWidth:" + tabLayoutWidth
                            + "   extraWidth:" + extraWidth + "  addPaddingWidth:" + addPaddingWidth +
                    "  tabLayout.getTabPadding:" + tabLayout.getTabPadding());
                }
            }
        });
    }


    /**
     * px 转 dp，高精度 return float
     * @param pxValue px 值
     * @return dp 值
     */
    public static float px2dp(Context context, final float pxValue) {
        final float scale = context.getApplicationContext().getResources().getDisplayMetrics().density;
        return pxValue / scale;
    }

}
