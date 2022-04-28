package com.happy.todo.lib_common.db;

import androidx.room.TypeConverter;

import java.util.Date;

/**
 * @作者: TwoSX
 * @时间: 2018/1/14 下午3:24
 * @描述: 类型转换
 */
public class DateConverter {
    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}