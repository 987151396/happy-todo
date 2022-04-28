package com.happy.todo.lib_common.event;

/**
 * @Describe：
 * @Date： 2019/04/10
 * @Author： dengkewu
 * @Contact：
 */
public class MainTabChangeEvent {
    private  int tab;

    private String city;

    private boolean isUpdate;

    private boolean isMainHotCity;

    public boolean isMainHotCity() {
        return isMainHotCity;
    }

    public void setMainHotCity(boolean mainhotCity) {
        isMainHotCity = mainhotCity;
    }

    public boolean isUpdate() {
        return isUpdate;
    }

    public void setUpdate(boolean update) {
        isUpdate = update;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getTab() {
        return tab;
    }

    public void setTab(int tab) {
        this.tab = tab;
    }

}
