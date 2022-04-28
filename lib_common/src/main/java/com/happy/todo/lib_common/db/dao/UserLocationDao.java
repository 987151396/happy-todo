package com.happy.todo.lib_common.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.happy.todo.lib_common.db.table.UserLocation;

import java.util.List;

import io.reactivex.Flowable;

/**
 * @作者: TwoSX
 * @时间: 2018/1/14 下午9:27
 * @描述: dao类
 */
@Dao
public interface UserLocationDao {

    /**
     * 根据用户 ID 获取定位，使用 list 是为了防止出现不回调的 bug
     *
     * @param user_id
     * @return
     */
    @Query("select * from eb_user_location where user_id = :user_id")
    Flowable<List<UserLocation>> getLocationByUserId(long user_id);

    @Query("select * from eb_user_location where user_id = :user_id limit :num")
    Flowable<List<UserLocation>> getLocationByUserId(long user_id, int num);

    @Query("select * from eb_user_location limit :num")
    Flowable<List<UserLocation>> getLocation(int num);


    @Insert
    void insert(UserLocation... entities);

    @Delete
    void delete(UserLocation entity);

    @Query("delete from eb_user_location")
    void deleteAll();

    @Update
    void update(UserLocation entity);

}
