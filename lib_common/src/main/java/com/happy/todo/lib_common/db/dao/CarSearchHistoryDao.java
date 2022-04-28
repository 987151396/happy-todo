package com.happy.todo.lib_common.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.happy.todo.lib_common.db.table.CarSearchHistoryBean;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface CarSearchHistoryDao {
    @Query("select * from eb_car_search_history order by update_time desc")
    List<CarSearchHistoryBean> getAllHistory();

    @Query("select * from eb_car_search_history order by update_time desc limit :num ")
    Flowable<List<CarSearchHistoryBean>> getAllHistory(int num);

    @Query("select * from eb_car_search_history where user_id=:user_id")
    Flowable<List<CarSearchHistoryBean>> getCarHistoryByUserId(long user_id);

    @Query("select * from eb_car_search_history where user_id=:user_id limit :num")
    Flowable<List<CarSearchHistoryBean>> getCarHistoryByUserId(long user_id, int num);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CarSearchHistoryBean... entities);

    @Delete
    void delete(CarSearchHistoryBean entity);

    @Query("delete from eb_car_search_history")
    void deleteAll();

    @Update
    void update(CarSearchHistoryBean entity);
}
