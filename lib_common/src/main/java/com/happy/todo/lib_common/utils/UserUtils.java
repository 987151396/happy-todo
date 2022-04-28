package com.happy.todo.lib_common.utils;

import com.happy.todo.lib_common.bean.UserInfoResult;
import com.happy.todo.lib_common.manage.LoginManage;



/**
 *
 * Created by Jaminchanks on 2018/1/25.
 */

public class UserUtils {

    private static final String USER_INFO_RESULT = "user_login_result";

    /**
     * 登录时获取的对象，保存到本地
     * @param result
     */
    public static void saveFullUserInfo(UserInfoResult result) {
        LoginManage.saveLastUser(result.getAccount());
        LoginManage.saveLogonInfo(result.getUser_id(), result.getToken());

        SPUtil.newInstance().saveObject(USER_INFO_RESULT, result);
    }

    /**
     * 获取登录时保存的对象
     * @return
     */
    public static UserInfoResult getFullUserInfo() {
        return SPUtil.newInstance().getObject(USER_INFO_RESULT, UserInfoResult.class);
    }

    public static void clearFullUserInfo() {
        LoginManage.logout();
        SPUtil.newInstance().remove(USER_INFO_RESULT);
    }

    public static void saveAppVersionChangedName(){
        SPUtil.newInstance().putAndApply("app_changed_version",AppUtil.getAppVersionName());
    }
    public static String getAppVersionChangedName(){
        return (String) SPUtil.newInstance().get("app_changed_version","");
    }

    public static void saveLoginClose(){
        SPUtil.newInstance().putAndApply("home_login_close",true);
    }
    public static void saveInviteClose(){
        SPUtil.newInstance().putAndApply("home_invite_close",true);
    }

    public static boolean getLoginClose(){
        return (boolean) SPUtil.newInstance().get("home_login_close",false);
    }
    public static boolean getInviteClose(){
        return (boolean) SPUtil.newInstance().get("home_invite_close",false);
    }
}
