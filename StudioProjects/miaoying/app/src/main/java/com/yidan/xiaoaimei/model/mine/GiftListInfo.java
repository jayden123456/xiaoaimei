package com.yidan.xiaoaimei.model.mine;

import java.util.List;

/**
 * Created by jaydenma on 2017/11/24.
 */

public class GiftListInfo {

    /**
     * status_code : 0
     * message : success
     * data : [{"count":1,"imgUrl":"http://image.miaoying.tv/images/rose%403x.png","giftName":"玫瑰花","createdAt":"2017-11-22 18:01:28"},{"count":1,"imgUrl":"http://image.miaoying.tv/images/rose%403x.png","giftName":"玫瑰花","createdAt":"2017-11-22 18:01:24"},{"count":1,"imgUrl":"http://image.miaoying.tv/images/cucumber%403x.png","giftName":"小黄瓜","createdAt":"2017-11-22 17:57:33"},{"count":4,"imgUrl":"http://image.miaoying.tv/images/fireworks%403x.png","giftName":"心型烟花","createdAt":"2017-11-22 17:57:20"},{"count":5,"imgUrl":"http://image.miaoying.tv/images/cucumber%403x.png","giftName":"小黄瓜","createdAt":"2017-11-22 17:57:10"},{"count":6,"imgUrl":"http://image.miaoying.tv/images/fireworks%403x.png","giftName":"心型烟花","createdAt":"2017-11-22 16:42:25"},{"count":6,"imgUrl":"http://image.miaoying.tv/images/fireworks%403x.png","giftName":"心型烟花","createdAt":"2017-11-22 16:42:25"}]
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
         * count : 1
         * imgUrl : http://image.miaoying.tv/images/rose%403x.png
         * giftName : 玫瑰花
         * createdAt : 2017-11-22 18:01:28
         */

        private int count;
        private String imgUrl;
        private String giftName;
        private String createdAt;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getGiftName() {
            return giftName;
        }

        public void setGiftName(String giftName) {
            this.giftName = giftName;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }
    }
}
