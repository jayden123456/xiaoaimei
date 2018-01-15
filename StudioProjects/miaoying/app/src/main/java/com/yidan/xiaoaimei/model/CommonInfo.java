package com.yidan.xiaoaimei.model;

/**
 * Created by jaydenma on 2017/7/17.
 */

public class CommonInfo {

    /**
     * status_code : 0
     * message : success
     * data: "http://img.miaokong.tv/fasdfdggdsgsd"
     */

    private int status_code;
    private String message;
    private String data;

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
