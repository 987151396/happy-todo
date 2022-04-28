package com.happy.todo.lib_common.db.table;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;

/**
 * @作者:
 * @时间: 2018/7/6
 * @描述: 用户搜索机场历史
 */
@Entity(tableName = "eb_search_airport_history", indices = {@Index(value = { "city_name","airport_name","index_id"},
        unique = true)})
public class SearchAirportHistory {

    @PrimaryKey(autoGenerate = true) //定义主键 自动增长
    private long id;
    @ColumnInfo(name = "index_id")
    private long index_id;
    @ColumnInfo(name = "city_code")
    private String city_code;
    @ColumnInfo(name = "city_name")
    private String city_name;
    @ColumnInfo(name = "airport_code")
    private String airport_code;
    @ColumnInfo(name = "airport_name")
    private String airport_name;
    @ColumnInfo(name = "country_name")
    private String country_name;
    @ColumnInfo(name = "country_code")
    private String country_code;
    @ColumnInfo(name = "update_time")
    private Date date;

//(city_code, city_name, airport_code, airport_name, country_name,new Date());

    @Ignore
    public SearchAirportHistory( long index_id,String city_code, String city_name, String country_name, String country_code,String airport_name,String airport_code, Date date) {
        this.index_id = index_id;
        this.city_code = city_code;
        this.country_name = country_name;
        this.country_code = country_code;
        this.city_name = city_name;
        this.airport_name = airport_name;
        this.airport_code = airport_code;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public long getIndex_id() {
        return index_id;
    }

    public void setIndex_id(long index_id) {
        this.index_id = index_id;
    }

    public String getCity_code() {
        return city_code;
    }

    public void setCity_code(String city_code) {
        this.city_code = city_code;
    }

    public SearchAirportHistory() {
    }



    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getAirport_code() {
        return airport_code;
    }

    public void setAirport_code(String airport_code) {
        this.airport_code = airport_code;
    }

    public String getAirport_name() {
        return airport_name;
    }

    public void setAirport_name(String airport_name) {
        this.airport_name = airport_name;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


}
