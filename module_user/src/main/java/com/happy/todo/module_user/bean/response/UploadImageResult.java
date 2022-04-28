package com.happy.todo.module_user.bean.response;

/**
 * 上传图片返回结果
 * Created by Jaminchanks on 2018/1/26.
 */

public class UploadImageResult {

    /**
     * avatar : E:/phpStudy/WWW/final_ebbly/public/upload/sign/1525339004.png
     * avatar_thumb : E:/phpStudy/WWW/final_ebbly/public/upload/sign/thumb_1525339004.png
     */

    private String avatar;
    private String avatar_thumb;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatar_thumb() {
        return avatar_thumb;
    }

    public void setAvatar_thumb(String avatar_thumb) {
        this.avatar_thumb = avatar_thumb;
    }
}
