package com.happy.todo.module_user.bean.response;

/**
 * Created by CXK on 2018/5/5.
 */

public class ResetPasswordResult {
    /**
     * user_id : 122
     * token : 66fa86db9674f636b651dd9df5424d03
     */

    private String user_id;
    private String token;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
