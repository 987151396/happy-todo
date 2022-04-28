package com.happy.todo.lib_common.router;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.launcher.ARouter;
import com.happy.todo.lib_common.utils.ActivityUtil;

import java.util.List;

/**
 * 登录后需要重新返回原界面
 * 或者需要登录后才能继续跳转到下个界面
 * 第一种：示例代码
 *   if (!LoginManage.isLogin()) {
 *         LoginBackTagUtil.getInstance().setLastNeedLoginPage(RouterPath.HOTEL_DETAIL);
 *         return;
 *  }
 *
 * 第二种：在访问权限受限的页面添加如下注解
 * @Route(path = {页面的路由路径}, extras = RouterPath.NEED_LOGIN)
 *
 * 登录后自动返回原先界面的操作，在登录页已做处理，无需关心
 * Created by Jaminchanks on 2018/8/21.
 */
public class LoginBackTagUtil {

    public static LoginBackTagUtil getInstance() {
        return InstanceHolder.mInstance;
    }

    private LoginBackTagUtil() {
    }

    private static class InstanceHolder {
        private static LoginBackTagUtil mInstance = new LoginBackTagUtil();
    }


    private String originalPath;

    private Postcard nextPagePostcard;


    /**
     * 设置源未登录页
     * @param originalPath 源跳转页,未携带参数的版本
     */
    private void setTag(String originalPath) {
        this.originalPath = originalPath;
    }


    /**
     * 设置源未登录页
     * @param nextPagePostcard 即将跳转过去的权限受限页面
     */
    public void setTag(Postcard nextPagePostcard) {
        this.nextPagePostcard = nextPagePostcard;
    }

    private String getOriginalPath() {
        return originalPath;
    }

    public Postcard getNextPagePostcard() {
        return nextPagePostcard;
    }


    /**
     * 清除掉未登录标记
     */
    public void clearTag() {
        this.originalPath = null;
        this.nextPagePostcard = null;
    }


    /**
     * 是否需要跳转到源未登录页
     * @return
     */
    public boolean isNeedLoginBack() {
        return originalPath != null || nextPagePostcard != null;
    }

    /**
     * 记录最后一个未登录页面
     * @param routerPath 该界面对应的路由
     */
    public void setLastNeedLoginPage(String routerPath) {
        LoginBackTagUtil.getInstance().setTag(routerPath); //由谁跳转过去的
        ARouter.getInstance().build(RouterPath.USER_LOGIN).navigation();
    }

    public void setLastNeedLoginPage(String routerPath, Activity activity, int requestCode) {
        LoginBackTagUtil.getInstance().setTag(routerPath); //由谁跳转过去的
        ARouter.getInstance().build(RouterPath.USER_LOGIN)
                .navigation(activity, requestCode);
    }

    /**
     * 回退到先前未登录时的界面
     * 调用此方法前最好先做判断 #isNeedLoginBack()
     */
    private void backToPreviousNeedLoginPage() {
        try { //将登录首页之后，登录操作之前的界面全部finish掉
            List<Activity> activityList = ActivityUtil.getActivityList();
            String activityName = "com.mexico.oppo.module_user.ui.login.LoginMainActivity";
            Activity destActivity = null;
            for (int i = activityList.size() - 1; i >= 0; --i) {
                Activity activity = activityList.get(i);
                if (activityName.equals(activity.getClass().getCanonicalName())) {
                    destActivity = activity;
                    break;
                }
            }

            if (destActivity != null) {
                //AppLogUtil.OY_D("1 : " + destActivity.toString());
                destActivity.setResult(Activity.RESULT_OK);
                ActivityUtil.finishToActivity(destActivity, true);
            }

            if (nextPagePostcard != null) {
                //AppLogUtil.OY_D("2 : " + nextPagePostcard.getPath());
                ARouter.getInstance().build(nextPagePostcard.getPath()).with(nextPagePostcard.getExtras()).navigation();
            }
        } catch (Exception ignored) {
            if (originalPath != null) {
                //AppLogUtil.OY_D("3 :" + originalPath);
                ARouter.getInstance().build(originalPath).navigation();
            }
        }

        nextPagePostcard = null;
        originalPath = null;
    }


    /**
     * 返回首页或者原先的未登录页面
     */
    @SuppressLint("WrongConstant")
    public void goToMainPageOrLastPage() {
        //如果以绑定直接跳到首页/之前未登录的界面，否则跳转到登录界面
        if (LoginBackTagUtil.getInstance().isNeedLoginBack()) {
            LoginBackTagUtil.getInstance().backToPreviousNeedLoginPage();
            return;
        }
        //如果以绑定直接跳到首页，否则跳转到登录界面
        ARouter.getInstance().build(RouterPath.MAIN)
                .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK)
                .navigation();
    }


}
