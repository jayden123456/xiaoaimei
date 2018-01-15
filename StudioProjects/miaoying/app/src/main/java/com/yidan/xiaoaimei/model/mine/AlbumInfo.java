package com.yidan.xiaoaimei.model.mine;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jaydenma on 2017/12/4.
 */

public class AlbumInfo implements Serializable {

    /**
     * status_code : 0
     * message : success
     * data : [{"imgId":5,"imgUrl":"http://image.miaoying.tv/images/201711211511244284626269.jpg","createdAt":"2017-11-21 14:04:48"},{"imgId":3,"imgUrl":"http://image.miaoying.tv/images/201711211511242279453388.jpg","createdAt":"2017-11-21 13:31:22"},{"imgId":4,"imgUrl":"http://image.miaoying.tv/images/201711211511242280452353.jpg","createdAt":"2017-11-21 13:31:22"}]
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
         * imgId : 5
         * imgUrl : http://image.miaoying.tv/images/201711211511244284626269.jpg
         * createdAt : 2017-11-21 14:04:48
         */

        private int imgId;
        private String imgUrl;
        private String createdAt;
        private String videoUrl;
        private String firstImg;

        public int getImgId() {
            return imgId;
        }

        public void setImgId(int imgId) {
            this.imgId = imgId;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getVideoUrl() {
            return videoUrl;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }

        public String getFirstImg() {
            return firstImg;
        }

        public void setFirstImg(String firstImg) {
            this.firstImg = firstImg;
        }
    }
}
