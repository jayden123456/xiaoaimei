package com.yidan.xiaoaimei.model.mine;

import java.util.List;

/**
 * Created by jaydenma on 2017/12/1.
 */

public class AlbumAndVideoInfo {

    /**
     * status_code : 0
     * message : success
     * data : {"photo":[{"imgId":5,"imgUrl":"http://image.miaoying.tv/images/201711211511244284626269.jpg","createdAt":"2017-11-21 14:04:48"},{"imgId":6,"imgUrl":"http://image.miaoying.tv/images/201711211511244287502353.jpg","createdAt":"2017-11-21 14:04:48"},{"imgId":3,"imgUrl":"http://image.miaoying.tv/images/201711211511242279453388.jpg","createdAt":"2017-11-21 13:31:22"},{"imgId":4,"imgUrl":"http://image.miaoying.tv/images/201711211511242280452353.jpg","createdAt":"2017-11-21 13:31:22"}],"video":[{"imgId":10,"videoUrl":"http://image.miaoying.tv/video/201711251511573957397845.mp4","firstImg":"http://image.miaoying.tv/images/201711251511573957397845.jpg","createdAt":"2017-11-25 09:39:20"},{"imgId":9,"videoUrl":"http://image.miaoying.tv/video/201711241511517788208720.mp4","firstImg":"http://image.miaoying.tv/images/201711241511517788208720.jpg","createdAt":"2017-11-24 18:03:13"},{"imgId":8,"videoUrl":"http://image.miaoying.tv/video/201711241511516686600131.mp4","firstImg":"http://image.miaoying.tv/images/201711241511516686600131.jpg","createdAt":"2017-11-24 17:44:52"},{"imgId":7,"videoUrl":"http://image.miaoying.tv/video/201711231511428006165992.mp4","firstImg":"http://image.miaoying.tv/images/201711231511428006165992.jpg","createdAt":"2017-11-23 17:06:55"}]}
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
        private List<PhotoBean> photo;
        private List<VideoBean> video;

        public List<PhotoBean> getPhoto() {
            return photo;
        }

        public void setPhoto(List<PhotoBean> photo) {
            this.photo = photo;
        }

        public List<VideoBean> getVideo() {
            return video;
        }

        public void setVideo(List<VideoBean> video) {
            this.video = video;
        }

        public static class PhotoBean {
            /**
             * imgId : 5
             * imgUrl : http://image.miaoying.tv/images/201711211511244284626269.jpg
             * createdAt : 2017-11-21 14:04:48
             */

            private int imgId;
            private String imgUrl;
            private String createdAt;

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
        }

        public static class VideoBean {
            /**
             * imgId : 10
             * videoUrl : http://image.miaoying.tv/video/201711251511573957397845.mp4
             * firstImg : http://image.miaoying.tv/images/201711251511573957397845.jpg
             * createdAt : 2017-11-25 09:39:20
             */

            private int imgId;
            private String videoUrl;
            private String firstImg;
            private String createdAt;

            public int getImgId() {
                return imgId;
            }

            public void setImgId(int imgId) {
                this.imgId = imgId;
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

            public String getCreatedAt() {
                return createdAt;
            }

            public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
            }
        }
    }
}
