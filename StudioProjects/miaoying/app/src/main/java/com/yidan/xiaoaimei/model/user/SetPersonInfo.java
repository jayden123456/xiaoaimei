package com.yidan.xiaoaimei.model.user;

/**
 * Created by jaydenma on 2017/7/17.
 */

public class SetPersonInfo {

    /**
     * status_code : 0
     * message : success
     * data : {"headImg":"http://image.wangxiaohong.tv/image/201707031499061088796184.jpg"}
     */

    private int status_code;
    private String message;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * headImg : http://image.wangxiaohong.tv/image/201707031499061088796184.jpg
         */

        private String headImg;

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }
    }
}
