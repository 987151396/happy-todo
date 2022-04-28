package com.happy.todo.lib_common.router;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.happy.todo.lib_common.utils.AppUtil;

/**
 * @作者: TwoSX
 * @时间: 2017/11/30 下午6:08
 * @描述: 路由帮助类
 */
public class RouterHelper {

    private static RouterHelper sRouterHelper;

    private RouterHelper() {
    }

    public static RouterHelper newInstance() {
        if (sRouterHelper == null) {
            synchronized (RouterHelper.class) {
                if (sRouterHelper == null) {
                    sRouterHelper = new RouterHelper();
                }
            }
        }
        return sRouterHelper;
    }

    public void init(Application application) {

        if (AppUtil.isDebug()) {   // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(application); // 尽可能早，推荐在Application中初始化

    }


}
