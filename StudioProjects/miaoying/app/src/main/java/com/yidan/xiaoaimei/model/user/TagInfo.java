package com.yidan.xiaoaimei.model.user;

import java.util.List;

/**
 * Created by jaydenma on 2017/11/14.
 */

public class TagInfo {

    /**
     * status_code : 0
     * message : success
     * data : {"tags":[{"tagId":1,"tagName":"网红","status":0},{"tagId":2,"tagName":"主播","status":0},{"tagId":3,"tagName":"模特","status":0},{"tagId":4,"tagName":"礼仪","status":0},{"tagId":5,"tagName":"演员","status":0},{"tagId":6,"tagName":"学生","status":0},{"tagId":7,"tagName":"鲜肉","status":0},{"tagId":8,"tagName":"潮男","status":0},{"tagId":9,"tagName":"御姐","status":0},{"tagId":10,"tagName":"自由职业","status":0}],"serviceTags":[{"tagId":84,"tagName":"活动通告","status":0},{"tagId":85,"tagName":"粉丝推广","status":0},{"tagId":86,"tagName":"活动主持","status":0},{"tagId":87,"tagName":"电商达人","status":0},{"tagId":88,"tagName":"车展活动","status":0},{"tagId":89,"tagName":"公益活动","status":0},{"tagId":90,"tagName":"旅游达人","status":0},{"tagId":91,"tagName":"T台走秀","status":0},{"tagId":92,"tagName":"直播推广","status":0},{"tagId":93,"tagName":"才艺教学","status":0},{"tagId":94,"tagName":"视频拍摄","status":0},{"tagId":95,"tagName":"平面拍摄","status":0},{"tagId":96,"tagName":"电影首映","status":0},{"tagId":106,"tagName":"现场驻唱","status":0},{"tagId":107,"tagName":"广告代言","status":0},{"tagId":108,"tagName":"模特礼仪","status":0},{"tagId":110,"tagName":"产品体验师","status":0},{"tagId":111,"tagName":"COSPLAY","status":0},{"tagId":112,"tagName":"演员","status":0},{"tagId":113,"tagName":"游戏开黑","status":0}]}
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
        private List<TagsBean> tags;
        private List<ServiceTagsBean> serviceTags;

        public List<TagsBean> getTags() {
            return tags;
        }

        public void setTags(List<TagsBean> tags) {
            this.tags = tags;
        }

        public List<ServiceTagsBean> getServiceTags() {
            return serviceTags;
        }

        public void setServiceTags(List<ServiceTagsBean> serviceTags) {
            this.serviceTags = serviceTags;
        }

        public static class TagsBean {
            /**
             * tagId : 1
             * tagName : 网红
             * status : 0
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

        public static class ServiceTagsBean {
            /**
             * tagId : 84
             * tagName : 活动通告
             * status : 0
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
}
