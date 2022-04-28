package com.happy.todo.lib_common.db;

import androidx.room.Database;

import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.happy.todo.lib_common.db.dao.CarSearchHistoryDao;
import com.happy.todo.lib_common.db.dao.SearchAirportHistoryDao;
import com.happy.todo.lib_common.db.dao.SearchHistoryDao;
import com.happy.todo.lib_common.db.dao.UserLocationDao;
import com.happy.todo.lib_common.db.dao.UserPhoneAreaCodeDao;
import com.happy.todo.lib_common.db.table.CarSearchHistoryBean;
import com.happy.todo.lib_common.db.table.SearchAirportHistory;
import com.happy.todo.lib_common.db.table.SearchHistory;
import com.happy.todo.lib_common.db.table.UserLocation;
import com.happy.todo.lib_common.db.table.UserPhoneAreaCodeTable;

/**
 * @作者: TwoSX
 * @时间: 2018/1/14 下午3:19
 * @描述: 定义数据库
 */
@Database(entities = {SearchHistory.class, UserLocation.class, SearchAirportHistory.class, UserPhoneAreaCodeTable.class, CarSearchHistoryBean.class}, version = 11, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class EbblyDatabase extends RoomDatabase {
    public abstract CarSearchHistoryDao getCarSearchHistoryDao();
    
    public abstract SearchHistoryDao getSearchHistoryDao();

    public abstract UserLocationDao getUserLocationDao();

    public abstract SearchAirportHistoryDao getSearchAirportHistoryDao();

    public abstract UserPhoneAreaCodeDao getUserPhoneAreaCodeDao();
}
