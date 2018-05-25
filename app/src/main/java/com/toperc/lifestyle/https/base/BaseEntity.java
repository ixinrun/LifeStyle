package com.toperc.lifestyle.https.base;

/**
 * @author HelloXinrun
 * @date 2018/5/25
 * @description
 */
public class BaseEntity<T> {
    private static final int SUCCESS_CODE = 0;
    private int code;
    private String msg;
    private T data;


    public boolean isSuccess() {
        return getCode() == SUCCESS_CODE;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

