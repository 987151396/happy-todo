package com.happy.todo.lib_common.db.table;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import android.text.TextUtils;

import java.util.Date;
import java.util.Locale;

@Entity(tableName = "eb_car_search_history", indices = {@Index(value = {"content", "user_id"},
        unique = true)})
public class CarSearchHistoryBean {

    /**
     * code : 630247
     * city_name : Guangzhou
     * city_code : CN1
     * country_code : CN
     * country_name : China
     * name : Guangzhou Homies Hotel
     * type : ATLAS
     */

    @PrimaryKey(autoGenerate = true) //定义主键 自动增长
    private long id;
    @ColumnInfo(name = "user_id")
    private long user_id;
    @ColumnInfo(name = "content")
    private String content;
    @ColumnInfo(name = "update_time")
    private Date date;

    @ColumnInfo(name = "code")
    private String code;
    @ColumnInfo(name = "city_name")
    private String city_name;
    @ColumnInfo(name = "city_code")
    private String city_code;
    @ColumnInfo(name = "country_code")
    private String country_code;
    @ColumnInfo(name = "country_name")
    private String country_name;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "type")
    private String type;

    public CarSearchHistoryBean(){}

    @Ignore
    public CarSearchHistoryBean(int user_id, Date date1,String content, String code, String city_name, String city_code, String country_code, String country_name, String name, String type) {
        this.user_id = user_id;
        this.date = date1;
        this.content = content;
        this.code = code;
        this.city_name = city_name;
        this.city_code = city_code;
        this.country_code = country_code;
        this.country_name = country_name;
        this.name = name;
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getCity_code() {
        return city_code;
    }

    public void setCity_code(String city_code) {
        this.city_code = city_code;
    }

    public String getCountry_code() {
        if(!TextUtils.isEmpty(country_code)) country_code.toUpperCase(Locale.US);
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
