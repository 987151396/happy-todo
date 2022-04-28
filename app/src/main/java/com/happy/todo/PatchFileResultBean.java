package com.happy.todo;

/**
 * 下载补丁接口回调数据
 * Created by Jaminchanks on 2018/8/30.
 */
public class PatchFileResultBean {
    /**
     * app_version : 1.0.0
     * salt : e10adc3949ba59abbe56e057f20f883e
     * patch_url : xxxx
     * app_url : xxxx
     * updatetime : 2018-08-30 14:57:52
     */

    private String app_version;
    private String salt;
    private String patch_url;
    private String app_url;
    private String updatetime;

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPatch_url() {
        return patch_url;
    }

    public void setPatch_url(String patch_url) {
        this.patch_url = patch_url;
    }

    public String getApp_url() {
        return app_url;
    }

    public void setApp_url(String app_url) {
        this.app_url = app_url;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }
}
