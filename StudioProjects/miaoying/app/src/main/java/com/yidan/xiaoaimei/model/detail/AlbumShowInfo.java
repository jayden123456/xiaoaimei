package com.yidan.xiaoaimei.model.detail;

import java.util.List;

/**
 * Created by jaydenma on 2017/8/14.
 */

public class AlbumShowInfo {

    /**
     * status_code : 0
     * message : success
     * data : {"readAll":0,"lists":[{"imgId":661,"imgUrl":"http://img.miaokong.tv/image/201708111502442715083878.jpg","createdAt":"2017-08-11 17:11:57"},{"imgId":665,"imgUrl":"http://img.miaokong.tv/image/201708111502442716142526.jpg","createdAt":"2017-08-11 17:11:57"},{"imgId":666,"imgUrl":"http://img.miaokong.tv/image/201708111502442717432020.jpg","createdAt":"2017-08-11 17:11:57"},{"imgId":660,"imgUrl":"http://img.miaokong.tv/image/201708111502442715013953.jpg","createdAt":"2017-08-11 17:11:57"},{"imgId":667,"imgUrl":"http://img.miaokong.tv/image/201708111502442717420609.jpg","createdAt":"2017-08-11 17:11:57"},{"imgId":663,"imgUrl":"http://img.miaokong.tv/image/201708111502442715530770.jpg","createdAt":"2017-08-11 17:11:57"},{"imgId":664,"imgUrl":"http://img.miaokong.tv/image/201708111502442716972362.jpg","createdAt":"2017-08-11 17:11:57"},{"imgId":659,"imgUrl":"http://img.miaokong.tv/image/201708111502442714077506.jpg","createdAt":"2017-08-11 17:11:57"},{"imgId":662,"imgUrl":"http://img.miaokong.tv/image/201708111502442715918262.jpg","createdAt":"2017-08-11 17:11:57"},{"imgId":558,"imgUrl":"http://img.miaokong.tv/image/201708101502352401595591.jpg","createdAt":"2017-08-10 16:06:42"}]}
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
         * readAll : 0
         * lists : [{"imgId":661,"imgUrl":"http://img.miaokong.tv/image/201708111502442715083878.jpg","createdAt":"2017-08-11 17:11:57"},{"imgId":665,"imgUrl":"http://img.miaokong.tv/image/201708111502442716142526.jpg","createdAt":"2017-08-11 17:11:57"},{"imgId":666,"imgUrl":"http://img.miaokong.tv/image/201708111502442717432020.jpg","createdAt":"2017-08-11 17:11:57"},{"imgId":660,"imgUrl":"http://img.miaokong.tv/image/201708111502442715013953.jpg","createdAt":"2017-08-11 17:11:57"},{"imgId":667,"imgUrl":"http://img.miaokong.tv/image/201708111502442717420609.jpg","createdAt":"2017-08-11 17:11:57"},{"imgId":663,"imgUrl":"http://img.miaokong.tv/image/201708111502442715530770.jpg","createdAt":"2017-08-11 17:11:57"},{"imgId":664,"imgUrl":"http://img.miaokong.tv/image/201708111502442716972362.jpg","createdAt":"2017-08-11 17:11:57"},{"imgId":659,"imgUrl":"http://img.miaokong.tv/image/201708111502442714077506.jpg","createdAt":"2017-08-11 17:11:57"},{"imgId":662,"imgUrl":"http://img.miaokong.tv/image/201708111502442715918262.jpg","createdAt":"2017-08-11 17:11:57"},{"imgId":558,"imgUrl":"http://img.miaokong.tv/image/201708101502352401595591.jpg","createdAt":"2017-08-10 16:06:42"}]
         */

        private int readAll;
        private List<ListsBean> lists;

        public int getReadAll() {
            return readAll;
        }

        public void setReadAll(int readAll) {
            this.readAll = readAll;
        }

        public List<ListsBean> getLists() {
            return lists;
        }

        public void setLists(List<ListsBean> lists) {
            this.lists = lists;
        }

        public static class ListsBean {
            /**
             * imgId : 661
             * imgUrl : http://img.miaokong.tv/image/201708111502442715083878.jpg
             * createdAt : 2017-08-11 17:11:57
             */

            private int imgId;
            private String imgUrl;
            private String videoUrl;
            private String firstImg;
            private String videoTime;
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

            public String getVideoTime() {
                return videoTime;
            }

            public void setVideoTime(String videoTime) {
                this.videoTime = videoTime;
            }
        }
    }
}
