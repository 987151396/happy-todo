package com.happy.todo.lib_common.event;

/**
 * Author by Ouyangle, Date on 2020-07-12.
 * PS: Not easy to write code, please indicate.
 */
public class WebLanChangeEvnet {
    private int languageType;

    public WebLanChangeEvnet(int languageType) {
        this.languageType = languageType;
    }

    public int getLanguageType() {
        return languageType;
    }

    public void setLanguageType(int languageType) {
        this.languageType = languageType;
    }
}
