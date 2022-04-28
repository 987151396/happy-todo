package com.happy.todo.lib_common.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.happy.todo.lib_common.db.table.SearchAirportHistory;

import java.util.List;

import io.reactivex.Flowable;

/**
 * @描述: dao 类
 */
@Dao
public interface SearchAirportHistoryDao {

    @Query("select * from eb_search_airport_history order by update_time desc")
    List<SearchAirportHistory> getAllHistory();

    @Query("select * from eb_search_airport_history order by update_time desc limit :num")
    Flowable<List<SearchAirportHistory>> getAllHistory(int num);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(SearchAirportHistory... entities);

    @Delete
    void delete(SearchAirportHistory entity);

    @Query("delete from eb_search_airport_history")
    void deleteAll();

    @Update
    void update(SearchAirportHistory entity);
}
