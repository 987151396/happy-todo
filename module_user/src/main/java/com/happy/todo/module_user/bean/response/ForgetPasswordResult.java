package com.happy.todo.module_user.bean.response;

/**
 * 发送手机验证码/邮箱验证码返回结果
 * Created by Jaminchanks on 2018/6/11.
 */
public class ForgetPasswordResult {

    /**
     * encryption_code : WldKaVlqZzBNV0ZoWXpnd09XTm1NV1E1WkdFek9USmtZMlUwT0dJeFlqUT0=
     * token : c8ac71c3d80bef5ec5c5a846b37bbdca
     */

    private String encryption_code;
    private String token;

    public String getEncryption_code() {
        return encryption_code;
    }

    public void setEncryption_code(String encryption_code) {
        this.encryption_code = encryption_code;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
