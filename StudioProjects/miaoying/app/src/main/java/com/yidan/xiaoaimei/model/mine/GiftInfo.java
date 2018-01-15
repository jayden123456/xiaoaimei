package com.yidan.xiaoaimei.model.mine;

import java.util.List;

/**
 * Created by jaydenma on 2017/11/24.
 */

public class GiftInfo {

    /**
     * status_code : 0
     * message : success
     * data : [{"imgUrl":"http://image.miaoying.tv/images/cucumber%403x.png","name":"小黄瓜","price":100,"giftId":1},{"imgUrl":"http://image.miaoying.tv/images/fireworks%403x.png","name":"心型烟花","price":52000,"giftId":2},{"imgUrl":"http://image.miaoying.tv/images/Ferrari%403x.png","name":"法拉利","price":99000,"giftId":3},{"imgUrl":"http://image.miaoying.tv/images/luxury_yacht%403x.png","name":"豪华游艇","price":131400,"giftId":4},{"imgUrl":"http://image.miaoying.tv/images/kiss%403x.png","name":"么么哒","price":300,"giftId":5},{"imgUrl":"http://image.miaoying.tv/images/rose%403x.png","name":"玫瑰花","price":500,"giftId":6},{"imgUrl":"http://image.miaoying.tv/images/chanel%403x.png","name":"香奶奶","price":9900,"giftId":7},{"imgUrl":"http://image.miaoying.tv/images/bear%403x.png","name":"玩具熊","price":1000,"giftId":8}]
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
         * imgUrl : http://image.miaoying.tv/images/cucumber%403x.png
         * name : 小黄瓜
         * price : 100
         * giftId : 1
         */

        private String imgUrl;
        private String name;
        private int price;
        private int giftId;

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getGiftId() {
            return giftId;
        }

        public void setGiftId(int giftId) {
            this.giftId = giftId;
        }
    }
}
