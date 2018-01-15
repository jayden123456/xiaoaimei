package com.yidan.xiaoaimei.model.mine;

import java.util.List;

/**
 * Created by jaydenma on 2017/11/22.
 */

public class VisitorInfo {

    /**
     * status_code : 0
     * message : success
     * data : [{"headImg":"http://image.miaoying.tv/images/201711011509517786036657.jpg","officialCer":0,"nickName":"刘松","updatedAt":"2017-11-20 17:13:13","uid":99993187,"signature":"这种天气，只适合在家吃着西瓜，吹着空调","income":8888,"expenses":6666,"time":"4分钟前"},{"headImg":"http://image.miaoying.tv/images/201711011509519949141319.jpg","officialCer":0,"nickName":"土方不二","updatedAt":"2017-11-20 16:49:51","uid":22226648,"signature":"这种天气，只适合在家吃着西瓜，吹着空调","income":8888,"expenses":6666,"time":"27分钟前"},{"headImg":"http://image.miaoying.tv/image/201705041510623664702.jpg","officialCer":0,"nickName":"aaa","updatedAt":"2017-11-20 16:49:38","uid":55559880,"signature":"这种天气，只适合在家吃着西瓜，吹着空调","income":8888,"expenses":6666,"time":"28分钟前"},{"headImg":"http://image.miaoying.tv/images/201710311509444521483379.jpg","officialCer":0,"nickName":"刘松","updatedAt":"2017-11-20 16:49:36","uid":99994779,"signature":"这种天气，只适合在家吃着西瓜，吹着空调","income":8888,"expenses":6666,"time":"28分钟前"}]
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
         * headImg : http://image.miaoying.tv/images/201711011509517786036657.jpg
         * officialCer : 0
         * nickName : 刘松
         * updatedAt : 2017-11-20 17:13:13
         * uid : 99993187
         * signature : 这种天气，只适合在家吃着西瓜，吹着空调
         * income : 8888
         * expenses : 6666
         * time : 4分钟前
         */

        private String headImg;
        private int officialCer;
        private String nickName;
        private String updatedAt;
        private int uid;
        private String signature;
        private int income;
        private int expenses;
        private String time;

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public int getOfficialCer() {
            return officialCer;
        }

        public void setOfficialCer(int officialCer) {
            this.officialCer = officialCer;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public int getIncome() {
            return income;
        }

        public void setIncome(int income) {
            this.income = income;
        }

        public int getExpenses() {
            return expenses;
        }

        public void setExpenses(int expenses) {
            this.expenses = expenses;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
