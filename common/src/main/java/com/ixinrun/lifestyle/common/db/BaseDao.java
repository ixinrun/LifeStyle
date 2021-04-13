package com.ixinrun.lifestyle.common.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

/**
 * 描述: BaseDao提供CRUD功能
 * </p>
 *
 * @author ixinrun
 * @date 2021/4/13
 */
@Dao
public interface BaseDao<T> {
    /**
     * 插入单个item
     *
     * @param item
     */
    @Insert(onConflict = REPLACE)
    void insertItem(T item);

    /**
     * 插入多个item
     *
     * @param items
     */
    @Insert(onConflict = REPLACE)
    void insertItems(List<T> items);

    /**
     * 删除一个item
     *
     * @param item
     */
    @Delete
    void deleteItem(T item);

    /**
     * 删除多个item
     *
     * @param item
     */
    @Delete
    void deleteItems(List<T> item);

    /**
     * 更新item
     *
     * @param item
     */
    @Update
    void updateItem(T item);
}
