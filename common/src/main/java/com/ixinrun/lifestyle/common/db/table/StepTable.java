package com.ixinrun.lifestyle.common.db.table;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * 描述: 运动表
 * </p>
 *
 * @author ixinrun
 * @date 2021/4/13
 */
@Entity
public class StepTable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int stepNum;
    private int stepNumTarget;
    private float kc;
    private float km;
    private String date;
    private boolean isPass;
    private String remark;

    public StepTable(int stepNum, int stepNumTarget, String date) {
        this.stepNum = stepNum;
        this.stepNumTarget = stepNumTarget;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStepNum() {
        return stepNum;
    }

    public void setStepNum(int stepNum) {
        this.stepNum = stepNum;
    }

    public int getStepNumTarget() {
        return stepNumTarget;
    }

    public void setStepNumTarget(int stepNumTarget) {
        this.stepNumTarget = stepNumTarget;
    }

    public float getKc() {
        return kc;
    }

    public void setKc(float kc) {
        this.kc = kc;
    }

    public float getKm() {
        return km;
    }

    public void setKm(float km) {
        this.km = km;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isPass() {
        return isPass;
    }

    public void setPass(boolean pass) {
        isPass = pass;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
