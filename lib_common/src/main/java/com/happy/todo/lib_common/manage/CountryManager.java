package com.happy.todo.lib_common.manage;

import android.text.TextUtils;

import com.happy.todo.lib_common.utils.SPUtil;

import java.util.Locale;

/**
 * @功能描述：选中国籍工具类
 * @创建日期： 2018/07/19
 * @作者： dengkewu
 */

public class CountryManager {

    public static  final String COUNTRY_NAME="country_name";
    public static  final String COUNTRY_EN_NAME="country_en_name";
    public static  final String COUNTRY_SHORT_NAME="country_short_name";
    public static  final String COUNTRY_TW_NAME="country_tw_name";

    //国籍
    private String countryName;
    private String countryEnName;
    private String countryShortName;
    private String countryTwName;


    private void init(){
        countryName = (String) SPUtil.newInstance().get(COUNTRY_NAME, "");
        countryEnName = (String) SPUtil.newInstance().get(COUNTRY_EN_NAME, "");
        countryShortName = (String) SPUtil.newInstance().get(COUNTRY_SHORT_NAME, "");
        countryTwName = (String) SPUtil.newInstance().get(COUNTRY_TW_NAME, "");

    }
    private CountryManager() {
        init();
    }

    private static CountryManager mCountryManager;

    public static CountryManager getInstance() {
        if (mCountryManager == null) {
            synchronized (CountryManager.class) {
                if (mCountryManager == null) {
                    mCountryManager = new CountryManager();
                }
            }
        }
        return mCountryManager;
    }


    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        SPUtil.newInstance().putAndApply(COUNTRY_NAME, countryName);
        this.countryName = countryName;
    }

    public String getCountryEnName() {
        return countryEnName;
    }

    public void setCountryEnName(String countryEnName) {
        SPUtil.newInstance().putAndApply(COUNTRY_EN_NAME, countryEnName);
        this.countryEnName = countryEnName;
    }

    public String getCountryShortName() {
        if(!TextUtils.isEmpty(countryShortName)) countryShortName.toUpperCase(Locale.US);
        return countryShortName;
    }

    public void setCountryShortName(String countryShortName) {
        SPUtil.newInstance().putAndApply(COUNTRY_SHORT_NAME, countryShortName);
        this.countryShortName = countryShortName;
    }
    public String getCountryTwName() {
        return countryTwName;
    }

    public void setCountryTwName(String countryTwName) {
        SPUtil.newInstance().putAndApply(COUNTRY_TW_NAME, countryShortName);
        this.countryTwName = countryTwName;
    }
}
