package com.happy.todo.lib_common.db.table;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

/**
 * 用户手机号与地区号对应表
 * Created by Jaminchanks on 2018/8/20.
 */
@Entity(tableName = "phone_area_table")
public class UserPhoneAreaCodeTable {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "phone_code")
    private String phoneCode = "";

    @ColumnInfo(name = "area_code")
    private String areaCode;

    public String getPhoneCode() {
        return phoneCode;
    }

    public void setPhoneCode(String phoneCode) {
        this.phoneCode = phoneCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
}
