package com.netease.nim.uikit.api.model.gift;

import java.io.Serializable;
import java.util.List;

/**
 * 礼物列表
 * Created by jaydenma on 2018/1/15.
 */

public class GiftListInfo {

    /**
     * status_code : 0
     * message : success
     * data : [{"imgUrl":"http://image.miaoying.tv/gifts/AMB@3x.png","name":"暧昧币","price":10,"giftId":1},{"imgUrl":"http://image.miaoying.tv/gifts/XHG@3x.png","name":"小黄瓜","price":100,"giftId":2},{"imgUrl":"http://image.miaoying.tv/gifts/XJ@3x.png","name":"香蕉","price":200,"giftId":3},{"imgUrl":"http://image.miaoying.tv/gifts/BBT@3x.png","name":"棒棒糖","price":288,"giftId":4},{"imgUrl":"http://image.miaoying.tv/gifts/MMD@3x.png","name":"么么哒","price":300,"giftId":5},{"imgUrl":"http://image.miaoying.tv/gifts/FK@3x.png","name":"房卡","price":400,"giftId":6},{"imgUrl":"http://image.miaoying.tv/gifts/MGH@3x.png","name":"玫瑰花","price":500,"giftId":7},{"imgUrl":"http://image.miaoying.tv/gifts/BJN@3x.png","name":"比基尼","price":600,"giftId":8},{"imgUrl":"http://image.miaoying.tv/gifts/XS@3x.png","name":"香水","price":700,"giftId":9},{"imgUrl":"http://image.miaoying.tv/gifts/QBB@3x.png","name":"求抱抱","price":800,"giftId":60},{"imgUrl":"http://image.miaoying.tv/gifts/XB@3x.png","name":"香槟","price":888,"giftId":59},{"imgUrl":"http://image.miaoying.tv/gifts/QQQ@3x.png","name":"求亲亲","price":900,"giftId":61},{"imgUrl":"http://image.miaoying.tv/gifts/AN@3x.png","name":"爱你","price":1000,"giftId":62},{"imgUrl":"http://image.miaoying.tv/gifts/WJX@3x.png","name":"玩具熊","price":1000,"giftId":64},{"imgUrl":"http://image.miaoying.tv/gifts/YM@3x.png","name":"约么","price":1888,"giftId":63},{"imgUrl":"http://image.miaoying.tv/gifts/520@3x.png","name":"520爱心","price":5200,"giftId":65},{"imgUrl":"http://image.miaoying.tv/gifts/CHANEL@3x.png","name":"香奶奶","price":9900,"giftId":67},{"imgUrl":"http://image.miaoying.tv/gifts/JZ@3x.png","name":"戒指","price":10000,"giftId":68},{"imgUrl":"http://image.miaoying.tv/gifts/WG@3x.png","name":"皇冠","price":20000,"giftId":69},{"imgUrl":"http://image.miaoying.tv/gifts/FLL@3x.png","name":"法拉利","price":30000,"giftId":70},{"imgUrl":"http://image.miaoying.tv/gifts/YH@3x.png","name":"新型烟花","price":52000,"giftId":71},{"imgUrl":"http://image.miaoying.tv/gifts/DBJ@3x.png","name":"大宝剑","price":66600,"giftId":72},{"imgUrl":"http://image.miaoying.tv/gifts/YL@3x.png","name":"豪华游艇","price":131400,"giftId":73}]
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

    public static class DataBean implements Serializable {
        /**
         * imgUrl : http://image.miaoying.tv/gifts/AMB@3x.png
         * name : 暧昧币
         * price : 10
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
