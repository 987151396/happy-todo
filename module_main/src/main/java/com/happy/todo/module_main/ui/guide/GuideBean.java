package com.happy.todo.module_main.ui.guide;

/**
 * @功能描述：
 * @创建日期： 2018/07/21
 * @作者： dengkewu
 */

public class GuideBean {
    private int images;
    private String title;
    private String content;

    private String btnContent;

    public String getBtnContent() {
        return btnContent;
    }

    public void setBtnContent(String btnContent) {
        this.btnContent = btnContent;
    }

    public int getImages() {
        return images;
    }

    public void setImages(int images) {
        this.images = images;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
