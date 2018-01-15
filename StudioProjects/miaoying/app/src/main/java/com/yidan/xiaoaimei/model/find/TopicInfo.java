package com.yidan.xiaoaimei.model.find;

import java.util.List;

/**
 * Created by jaydenma on 2017/12/27.
 */

public class TopicInfo {

    /**
     * status_code : 0
     * message : success
     * data : [{"tagId":194,"tagName":"吃吃喝喝"},{"tagId":195,"tagName":"发个自拍"},{"tagId":196,"tagName":"神马都可以"}]
     */

    private int status_code;
    private String message;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * tagId : 194
         * tagName : 吃吃喝喝
         */

        private int tagId;
        private String tagName;
        private int status;

        public int getTagId() {
            return tagId;
        }

        public void setTagId(int tagId) {
            this.tagId = tagId;
        }

        public String getTagName() {
            return tagName;
        }

        public void setTagName(String tagName) {
            this.tagName = tagName;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
