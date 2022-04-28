package com.happy.todo.lib_common.db;

import android.app.Application;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.annotation.NonNull;

/**
 * @作者: TwoSX
 * @时间: 2018/1/14 下午3:30
 * @描述: 数据库帮助类
 */
public class DBHelper {

    private static DBHelper sDBHelper;
    private EbblyDatabase mEbblyDatabase;

    private DBHelper() {
    }

    // 初始化操作
    public static void init(Application application) {
        if (sDBHelper == null) {
            sDBHelper = new DBHelper();
            getInstance()._init(application);
        }
    }

    // 初始化操作
    private void _init(Application application) {
        mEbblyDatabase = Room.databaseBuilder(application,
                EbblyDatabase.class, "db_ebbly")
                .addMigrations(migration_1_2(),migration_2_3(),migration_3_4())
                .fallbackToDestructiveMigration()
                .build();
    }

    public static DBHelper getInstance() {
        if (sDBHelper == null) {
            throw new ExceptionInInitializerError("请先进行初始化");
        }
        return sDBHelper;
    }

    public EbblyDatabase getEbblyDatabase() {
        return mEbblyDatabase;
    }


    /**
     * 数据库版本1升级到2
     */
    private Migration migration_1_2() {
        return new Migration(1, 2) {
            @Override
            public void migrate(@NonNull SupportSQLiteDatabase database) {
                database.execSQL("CREATE TABLE 'phone_area_table'('phone_code' TEXT PRIMARY KEY NOT NULL, 'area_code' TEXT)");
            }
        };
    }

    /**
     * 数据库版本1升级到2
     */
    private Migration migration_2_3() {
        return new Migration(2, 3) {
            @Override
            public void migrate(@NonNull SupportSQLiteDatabase database) {
//                为旧表添加新的字段
                database.execSQL("ALTER TABLE eb_search_history "
                        + " ADD COLUMN lat TEXT");
                database.execSQL("ALTER TABLE eb_search_history "
                        + " ADD COLUMN lng TEXT");
//                创建新的数据表
//                database.execSQL("CREATE TABLE IF NOT EXISTS `book` (`book_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT)");
            }
        };
    }

    /**
     * 数据库版本3升级到4
     */
    private Migration migration_3_4() {
        return new Migration(3, 4) {
            @Override
            public void migrate(@NonNull SupportSQLiteDatabase database) {

                database.execSQL("CREATE TABLE 'eb_car_search_history'" +
                        "('id' INTEGER NOT NULL,'content' TEXT,'update_time'INTEGER NOT NULL," +
                        "'code'TEXT, 'city_name'TEXT, 'city_code'TEXT, 'country_code'TEXT, 'country_name'TEXT," +
                        " 'name'TEXT, 'type'TEXT, 'user_id' INTEGER NOT NULL,PRIMARY KEY('id'))");

               /* database.execSQL("ALTER TABLE eb_search_history "
                        + " ADD COLUMN last_updata INTEGER");*/
            }
        };
    }

}
