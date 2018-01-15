package com.yidan.xiaoaimei.model.detail;

import java.util.List;

/**
 * Created by jaydenma on 2017/12/12.
 */

public class UserDetailInfo {

    /**
     * status_code : 0
     * message : success
     * data : {"uid":77772002,"nickName":"刘松5549","headImg":"http://image.miaoying.tv/images/201712061512542041259198.jpg","tags":[{"tagName":"主播","tagId":2},{"tagName":"学生","tagId":6}],"evaTags":[{"evaTagName":"声音甜美","num":24,"evaId":1},{"evaTagName":"颜值高","num":33,"evaId":2},{"evaTagName":"舞姿热辣","num":15,"evaId":4},{"evaTagName":"小鲜肉","num":2,"evaId":17},{"evaTagName":"皮肤白","num":12,"evaId":10},{"evaTagName":"神似明星","num":2,"evaId":24},{"evaTagName":"嗓音迷人","num":2,"evaId":27},{"evaTagName":"气质好","num":2,"evaId":3},{"evaTagName":"随意撩","num":1,"evaId":9},{"evaTagName":"性感","num":1,"evaId":5}],"isTag":1,"serviceTags":[{"tagName":"粉丝推广","tagId":85}],"fansCount":2,"followCount":4,"city":"","giftList":[],"visitRecord":[{"headImg":"http://image.miaoying.tv/image/1512542250674.jpg","uid":11112464},{"headImg":"http://image.miaoying.tv/images/201712101512850747616656.jpg","uid":11118365},{"headImg":"http://image.miaoying.tv/images/201712081512699868039620.jpg","uid":11114058},{"headImg":"http://image.miaoying.tv/images/201712061512542229528183.jpg","uid":66663718}],"visitNum":4,"momentNum":0,"imgsNum":4,"videoNum":0,"signature":"哈哈，有点无奈吧","age":20,"income":0,"expenses":0,"videoCer":{"videoUrl":"http://image.miaoying.tv/video/201712071512646023491915.mp4","firstImg":"http://image.miaoying.tv/images/201712071512646023491915.jpg","videoTime":10},"voiceCer":{"voiceUrl":"http://image.miaoying.tv/voice/201712111512975649668933.aac","voiceTime":12},"height":155,"measurements":[18,10,10],"isFollowed":true}
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
         * uid : 77772002
         * nickName : 刘松5549
         * headImg : http://image.miaoying.tv/images/201712061512542041259198.jpg
         * tags : [{"tagName":"主播","tagId":2},{"tagName":"学生","tagId":6}]
         * evaTags : [{"evaTagName":"声音甜美","num":24,"evaId":1},{"evaTagName":"颜值高","num":33,"evaId":2},{"evaTagName":"舞姿热辣","num":15,"evaId":4},{"evaTagName":"小鲜肉","num":2,"evaId":17},{"evaTagName":"皮肤白","num":12,"evaId":10},{"evaTagName":"神似明星","num":2,"evaId":24},{"evaTagName":"嗓音迷人","num":2,"evaId":27},{"evaTagName":"气质好","num":2,"evaId":3},{"evaTagName":"随意撩","num":1,"evaId":9},{"evaTagName":"性感","num":1,"evaId":5}]
         * isTag : 1
         * serviceTags : [{"tagName":"粉丝推广","tagId":85}]
         * fansCount : 2
         * followCount : 4
         * city :
         * giftList : []
         * visitRecord : [{"headImg":"http://image.miaoying.tv/image/1512542250674.jpg","uid":11112464},{"headImg":"http://image.miaoying.tv/images/201712101512850747616656.jpg","uid":11118365},{"headImg":"http://image.miaoying.tv/images/201712081512699868039620.jpg","uid":11114058},{"headImg":"http://image.miaoying.tv/images/201712061512542229528183.jpg","uid":66663718}]
         * visitNum : 4
         * momentNum : 0
         * imgsNum : 4
         * videoNum : 0
         * signature : 哈哈，有点无奈吧
         * age : 20
         * income : 0
         * expenses : 0
         * videoCer : {"videoUrl":"http://image.miaoying.tv/video/201712071512646023491915.mp4","firstImg":"http://image.miaoying.tv/images/201712071512646023491915.jpg","videoTime":10}
         * voiceCer : {"voiceUrl":"http://image.miaoying.tv/voice/201712111512975649668933.aac","voiceTime":12}
         * height : 155
         * measurements : [18,10,10]
         * isFollowed : true
         */

        private int uid;
        private String nickName;
        private String headImg;
        private int isTag;
        private int fansCount;
        private int followCount;
        private String city;
        private int visitNum;
        private int momentNum;
        private int imgsNum;
        private int videoNum;
        private String signature;
        private int age;
        private int income;
        private int expenses;
        private VideoCerBean videoCer;
        private VoiceCerBean voiceCer;
        private int height;
        private boolean isFollowed;
        private List<TagsBean> tags;
        private List<EvaTagsBean> evaTags;
        private List<ServiceTagsBean> serviceTags;
        private List<GiftListBean> giftList;
        private List<VisitRecordBean> visitRecord;
        private List<Integer> measurements;

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public int getIsTag() {
            return isTag;
        }

        public void setIsTag(int isTag) {
            this.isTag = isTag;
        }

        public int getFansCount() {
            return fansCount;
        }

        public void setFansCount(int fansCount) {
            this.fansCount = fansCount;
        }

        public int getFollowCount() {
            return followCount;
        }

        public void setFollowCount(int followCount) {
            this.followCount = followCount;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public int getVisitNum() {
            return visitNum;
        }

        public void setVisitNum(int visitNum) {
            this.visitNum = visitNum;
        }

        public int getMomentNum() {
            return momentNum;
        }

        public void setMomentNum(int momentNum) {
            this.momentNum = momentNum;
        }

        public int getImgsNum() {
            return imgsNum;
        }

        public void setImgsNum(int imgsNum) {
            this.imgsNum = imgsNum;
        }

        public int getVideoNum() {
            return videoNum;
        }

        public void setVideoNum(int videoNum) {
            this.videoNum = videoNum;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
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

        public VideoCerBean getVideoCer() {
            return videoCer;
        }

        public void setVideoCer(VideoCerBean videoCer) {
            this.videoCer = videoCer;
        }

        public VoiceCerBean getVoiceCer() {
            return voiceCer;
        }

        public void setVoiceCer(VoiceCerBean voiceCer) {
            this.voiceCer = voiceCer;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public boolean isIsFollowed() {
            return isFollowed;
        }

        public void setIsFollowed(boolean isFollowed) {
            this.isFollowed = isFollowed;
        }

        public List<TagsBean> getTags() {
            return tags;
        }

        public void setTags(List<TagsBean> tags) {
            this.tags = tags;
        }

        public List<EvaTagsBean> getEvaTags() {
            return evaTags;
        }

        public void setEvaTags(List<EvaTagsBean> evaTags) {
            this.evaTags = evaTags;
        }

        public List<ServiceTagsBean> getServiceTags() {
            return serviceTags;
        }

        public void setServiceTags(List<ServiceTagsBean> serviceTags) {
            this.serviceTags = serviceTags;
        }

        public List<GiftListBean> getGiftList() {
            return giftList;
        }

        public void setGiftList(List<GiftListBean> giftList) {
            this.giftList = giftList;
        }

        public List<VisitRecordBean> getVisitRecord() {
            return visitRecord;
        }

        public void setVisitRecord(List<VisitRecordBean> visitRecord) {
            this.visitRecord = visitRecord;
        }

        public List<Integer> getMeasurements() {
            return measurements;
        }

        public void setMeasurements(List<Integer> measurements) {
            this.measurements = measurements;
        }

        public static class VideoCerBean {
            /**
             * videoUrl : http://image.miaoying.tv/video/201712071512646023491915.mp4
             * firstImg : http://image.miaoying.tv/images/201712071512646023491915.jpg
             * videoTime : 10
             */

            private String videoUrl;
            private String firstImg;
            private int videoTime;

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

            public int getVideoTime() {
                return videoTime;
            }

            public void setVideoTime(int videoTime) {
                this.videoTime = videoTime;
            }
        }

        public static class VoiceCerBean {
            /**
             * voiceUrl : http://image.miaoying.tv/voice/201712111512975649668933.aac
             * voiceTime : 12
             */

            private String voiceUrl;
            private int voiceTime;

            public String getVoiceUrl() {
                return voiceUrl;
            }

            public void setVoiceUrl(String voiceUrl) {
                this.voiceUrl = voiceUrl;
            }

            public int getVoiceTime() {
                return voiceTime;
            }

            public void setVoiceTime(int voiceTime) {
                this.voiceTime = voiceTime;
            }
        }

        public static class TagsBean {
            /**
             * tagName : 主播
             * tagId : 2
             */

            private String tagName;
            private int tagId;

            public String getTagName() {
                return tagName;
            }

            public void setTagName(String tagName) {
                this.tagName = tagName;
            }

            public int getTagId() {
                return tagId;
            }

            public void setTagId(int tagId) {
                this.tagId = tagId;
            }
        }

        public static class EvaTagsBean {
            /**
             * evaTagName : 声音甜美
             * num : 24
             * evaId : 1
             */

            private String evaTagName;
            private int num;
            private int evaId;

            public String getEvaTagName() {
                return evaTagName;
            }

            public void setEvaTagName(String evaTagName) {
                this.evaTagName = evaTagName;
            }

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }

            public int getEvaId() {
                return evaId;
            }

            public void setEvaId(int evaId) {
                this.evaId = evaId;
            }
        }

        public static class ServiceTagsBean {
            /**
             * tagName : 粉丝推广
             * tagId : 85
             */

            private String tagName;
            private int tagId;

            public String getTagName() {
                return tagName;
            }

            public void setTagName(String tagName) {
                this.tagName = tagName;
            }

            public int getTagId() {
                return tagId;
            }

            public void setTagId(int tagId) {
                this.tagId = tagId;
            }
        }

        public static class VisitRecordBean {
            /**
             * headImg : http://image.miaoying.tv/image/1512542250674.jpg
             * uid : 11112464
             */

            private String headImg;
            private int uid;

            public String getHeadImg() {
                return headImg;
            }

            public void setHeadImg(String headImg) {
                this.headImg = headImg;
            }

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }
        }

        public static class GiftListBean {
            /**
             * headImg : http://image.miaoying.tv/image/1512542250674.jpg
             * uid : 11112464
             */

            private String imgUrl;

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }
        }
    }
}
