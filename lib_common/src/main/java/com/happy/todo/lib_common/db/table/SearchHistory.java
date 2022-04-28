package com.happy.todo.lib_common.db.table;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;

/**
 * @作者: TwoSX
 * @时间: 2018/1/14 下午2:56
 * @描述: 用户搜索历史
 */
@Entity(tableName = "eb_search_history", indices = {@Index(value = {"content", "user_id"},
        unique = true)})
public class SearchHistory {

    @PrimaryKey(autoGenerate = true) //定义主键 自动增长
    private long id;
    @ColumnInfo(name = "user_id")
    private long user_id;
    @ColumnInfo(name = "content")
    private String content;
    @ColumnInfo(name = "update_time")
    private Date date;

    @ColumnInfo(name = "code")
    private String code;//区域码或者酒店码
    @ColumnInfo(name = "type")
    private int type;//用于区分是区域还是国家
    //新添加字段
    @ColumnInfo(name = "city_code")
    private String city_code;

    @ColumnInfo(name = "lat")
    private String lat;

    @ColumnInfo(name = "lng")
    private String lng;

    @ColumnInfo(name = "country_code")
    private String country_code;

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public SearchHistory() {
    }

    @Ignore
    public SearchHistory(long user_id, String content, String code,String city_code, int type,Date date,String lat,String lng, String country_code) {
        this.user_id = user_id;
        this.content = content;
        this.code = code;
        this.date = date;
        this.city_code = city_code;
        this.type=type;
        this.lat=lat;
        this.lng=lng;
        this.country_code = country_code;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getType() {
        return type;
    }

    public void setType(int status) {
        this.type = status;
    }

    public String getCity_code() {
        return city_code;
    }

    public void setCity_code(String city_code) {
        this.city_code = city_code;
    }

}
