package com.happy.todo.lib_common.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.happy.todo.lib_common.db.table.SearchHistory;
import java.util.List;

import io.reactivex.Flowable;

/**
 * @作者: TwoSX
 * @时间: 2018/1/14 下午3:14
 * @描述: dao 类
 */
@Dao
public interface SearchHistoryDao {

    @Query("select * from eb_search_history order by update_time desc")
    List<SearchHistory> getAllHistory();

    @Query("select * from eb_search_history order by update_time desc limit :num ")
    Flowable<List<SearchHistory>> getAllHistory(int num);

    @Query("select * from eb_search_history where user_id=:user_id")
    Flowable<List<SearchHistory>> getHistoryByUserId(long user_id);

    @Query("select * from eb_search_history where user_id=:user_id limit :num")
    Flowable<List<SearchHistory>> getHistoryByUserId(long user_id, int num);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(SearchHistory... entities);

    @Delete
    void delete(SearchHistory entity);

    @Query("delete from eb_search_history")
    void deleteAll();

    @Update
    void update(SearchHistory entity);
}
