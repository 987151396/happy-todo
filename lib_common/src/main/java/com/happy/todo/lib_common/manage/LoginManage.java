package com.happy.todo.lib_common.manage;

import android.text.TextUtils;

import com.happy.todo.lib_common.utils.SPUtil;
import com.happy.todo.lib_common.utils.StringUtils;


/**
 * Description:
 * Author: 贰师兄
 * Date: 2017/10/13 下午3:16
 */

public class LoginManage {

    private static final String TAG = "LoginManage";

    private static LoginManage sLoginManage;
    public static final String KEY_USER_ID = "user_id";
    public static final String KEY_USER_TOKEN = "user_token";

    public static final String KEY_USER_THIRD_PARTY = "user_third_party";
    public static final String KEY_LAST_USER = "last_user";

    private static final String PAY_PWD_PATTERN = "^\\d{6}$";

    private static Boolean mHasLogin = null;

    private LoginManage() {
    }

    public static void saveLogonInfo(long userId, String token) {

        SPUtil.newInstance().putAndApply(KEY_USER_ID, userId);
        SPUtil.newInstance().putAndApply(KEY_USER_TOKEN, token);
    }

    // 保存临时的用户信息，下次获取后就清除
    public static void saveTempLogonInfo(long userId, String token) {
        SPUtil.newInstance().putAndApply(KEY_USER_ID + "_temp", userId);
        SPUtil.newInstance().putAndApply(KEY_USER_TOKEN + "_temp", token);
    }

    public static String getTempUserToken() {
        String temp_token = (String) SPUtil.newInstance().get(KEY_USER_TOKEN + "_temp", "");
        SPUtil.newInstance().remove(KEY_USER_TOKEN + "_temp");
        return temp_token;
    }

    public static String getTempUserId() {
        String temp_user_id = (String) SPUtil.newInstance().get(KEY_USER_ID + "_temp", "");
        SPUtil.newInstance().remove(KEY_USER_ID + "_temp");
        return temp_user_id;
    }

    public static void saveThirdParty(int index) {
        SPUtil.newInstance().putAndApply(KEY_USER_THIRD_PARTY, index);
    }

    public static int getThirdParty() {
        return (int) SPUtil.newInstance().get(KEY_USER_THIRD_PARTY, 0);
    }


    public static String getUserToken() {
        return (String) SPUtil.newInstance().get(KEY_USER_TOKEN, "");
    }

    public static long getUserId() {
        return (long) SPUtil.newInstance().get(KEY_USER_ID, 0L);
    }

    public static boolean isLogin() {
        if (mHasLogin == null) {
            mHasLogin = !TextUtils.isEmpty(getUserToken()) && !TextUtils.isEmpty(getUserId() + "");
        }
        return mHasLogin;
    }

    public static String getLastUser() {
        return (String) SPUtil.newInstance().get(KEY_LAST_USER, "");
    }

    public static void saveLastUser(String user) {
        SPUtil.newInstance().putAndApply(KEY_LAST_USER, user);
        login();
    }

    /**
     * 通知已登录状态
     */
    public static void login() {
        mHasLogin = true;
    }

    /**
     * 通知已退出登录状态
     */
    public static void logout() {
        // 清空数据
        SPUtil.newInstance().remove(KEY_USER_ID, KEY_USER_TOKEN);
        mHasLogin = false;
    }


    /**
     * 有效的邮箱
     *
     * @param email
     * @return
     */
    public static boolean validateEmail(String email) {
        return StringUtils.isEmail(email);
    }

    /**
     * 有效的密码
     *
     * @param password
     * @return
     */
    public static boolean validatePassword(String password) {
        return StringUtils.validatePassword(password);
    }

    /**
     * 有效的手机号码 带 + 或不带
     *
     * @param phone
     * @return
     */
    public static boolean validatePhone(String phone) {
        return StringUtils.isPhone(phone);
    }

}
