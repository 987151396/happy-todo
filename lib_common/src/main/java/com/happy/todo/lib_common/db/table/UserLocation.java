package com.happy.todo.lib_common.db.table;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;


/**
 * @作者: TwoSX
 * @时间: 2018/1/14 下午9:20
 * @描述: 用户定位表
 */
@Entity(tableName = "eb_user_location")
public class UserLocation {
    @PrimaryKey(autoGenerate = true) //定义主键 自动增长
    private long id;
    @ColumnInfo(name = "user_id")
    private long user_id;
    @ColumnInfo(name = "lon")
    private String lon;
    @ColumnInfo(name = "lat")
    private String lat;
    @ColumnInfo(name = "country")
    private String country;
    @ColumnInfo(name = "province")
    private String province;
    @ColumnInfo(name = "city")
    private String city;
    @ColumnInfo(name = "address")
    private String address;
    @ColumnInfo(name = "update_time")
    private Date date;

    public UserLocation() {
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

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
