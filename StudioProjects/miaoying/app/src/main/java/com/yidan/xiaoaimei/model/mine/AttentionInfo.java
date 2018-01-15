package com.yidan.xiaoaimei.model.mine;

import java.util.List;

/**
 * Created by jaydenma on 2017/11/22.
 */

public class AttentionInfo {

    /**
     * status_code : 0
     * message : success
     * data : [{"nickName":"刘松","uid":99994779,"headImg":"http://image.miaoying.tv/images/201710311509444521483379.jpg","city":"","officialCer":0,"signature":"这种天气，只适合在家吃着西瓜，吹着空调","isFollowed":0,"income":1000,"expenses":2000},{"nickName":"土方不二","uid":22226648,"headImg":"http://image.miaoying.tv/images/201711011509519949141319.jpg","city":"","officialCer":0,"signature":"这种天气，只适合在家吃着西瓜，吹着空调","isFollowed":0,"income":1000,"expenses":2000}]
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
         * nickName : 刘松
         * uid : 99994779
         * headImg : http://image.miaoying.tv/images/201710311509444521483379.jpg
         * city :
         * officialCer : 0
         * signature : 这种天气，只适合在家吃着西瓜，吹着空调
         * isFollowed : 0
         * income : 1000
         * expenses : 2000
         */

        private String nickName;
        private int uid;
        private String headImg;
        private String city;
        private int officialCer;
        private String signature;
        private int isFollowed;
        private int income;
        private int expenses;

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public int getOfficialCer() {
            return officialCer;
        }

        public void setOfficialCer(int officialCer) {
            this.officialCer = officialCer;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public int getIsFollowed() {
            return isFollowed;
        }

        public void setIsFollowed(int isFollowed) {
            this.isFollowed = isFollowed;
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
    }
}
