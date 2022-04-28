package com.happy.todo.lib_common.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.happy.todo.lib_common.db.table.UserPhoneAreaCodeTable;

import io.reactivex.Flowable;

/**
 * Created by Jaminchanks on 2018/8/20.
 */
@Dao
public interface UserPhoneAreaCodeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPhoneAreaCode(UserPhoneAreaCodeTable bean);

    @Query("SELECT * FROM phone_area_table WHERE phone_code = :phoneCode")
    Flowable<UserPhoneAreaCodeTable> getPhoneAreaCode(String phoneCode);

}
