package com.ixinrun.lifestyle.common.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.ixinrun.lifestyle.common.db.dao.StepDao;
import com.ixinrun.lifestyle.common.db.table.DbStepInfo;

/**
 * 描述: App全局数据库
 * </p>
 *
 * @author ixinrun
 * @date 2021/4/13
 */
@Database(entities = {DbStepInfo.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "lifestyle_db";

    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room
                    .databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                    .build();
        }
        return instance;
    }


    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {
    }

    public abstract StepDao stepDao();

}
