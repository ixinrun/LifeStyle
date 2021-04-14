package com.ixinrun.lifestyle.common.db.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.ixinrun.lifestyle.common.db.BaseDao;
import com.ixinrun.lifestyle.common.db.table.StepTable;

import java.util.List;

/**
 * 描述: 运动表Dao
 * </p>
 *
 * @author ixinrun
 * @date 2021/4/13
 */
@Dao
public interface StepDao extends BaseDao<StepTable> {

    /**
     * 查询所有
     *
     * @return 返回多条数据
     */
    @Query("SELECT * FROM StepTable")
    List<StepTable> getAllData();

    /**
     * 单个查询
     *
     * @param id
     * @return 返回单条数据
     */
    @Query("SELECT * FROM StepTable WHERE id = :id")
    StepTable getStepTableById(int id);

    /**
     * 清空表
     */
    @Query("DELETE FROM StepTable")
    void cleanTable();
}
