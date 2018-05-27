package com.toperc.lifestyle.https.subscribe;

/**
 * @author HelloXinrun
 * @date 2018/5/25
 * @description
 */
public class BaseResponse<T> {
    private static final int SUCCESS_CODE = 0;
    private int code;
    private String msg;
    private T data;
    private int total;
    private boolean more;

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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public boolean isMore() {
        return more;
    }

    public void setMore(boolean more) {
        this.more = more;
    }
}

