package com.happy.todo.lib_common.bean;

import java.io.Serializable;

/**
 * @功能描述：
 * @创建日期： 2018/07/20
 * @作者： dengkewu
 */

public class VersionBean implements Serializable{

    /**
     * system_version : 1.0.0
     * costom_version : 1
     * appurl : url
     * tip : en_tip
     */

    private String system_version;
    private String minimum_version;
    private String costom_version;
    private String appurl;
    private String tip;
    private String compel_tip;
    private String en_compel_tip;

    public String getCompel_tip() {
        return compel_tip;
    }

    public void setCompel_tip(String compel_tip) {
        this.compel_tip = compel_tip;
    }

    public String getEn_compel_tip() {
        return en_compel_tip;
    }

    public void setEn_compel_tip(String en_compel_tip) {
        this.en_compel_tip = en_compel_tip;
    }

    public String getMinimum_version() {
        return minimum_version;
    }

    public void setMinimum_version(String minimum_version) {
        this.minimum_version = minimum_version;
    }

    public String getSystem_version() {
        return system_version;
    }

    public void setSystem_version(String system_version) {
        this.system_version = system_version;
    }

    public String getCostom_version() {
        return costom_version;
    }

    public void setCostom_version(String costom_version) {
        this.costom_version = costom_version;
    }

    public String getAppurl() {
        return appurl;
    }

    public void setAppurl(String appurl) {
        this.appurl = appurl;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }
}
