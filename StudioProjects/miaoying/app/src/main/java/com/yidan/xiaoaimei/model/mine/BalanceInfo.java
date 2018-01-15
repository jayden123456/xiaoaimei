package com.yidan.xiaoaimei.model.mine;

/**
 * Created by jaydenma on 2017/7/21.
 */

public class BalanceInfo {

    /**
     * status_code : 0
     * message : success
     * data : 9550
     */

    private int status_code;
    private String message;
    private int data;

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}
