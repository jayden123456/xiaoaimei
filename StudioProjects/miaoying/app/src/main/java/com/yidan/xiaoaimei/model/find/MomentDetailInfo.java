package com.yidan.xiaoaimei.model.find;

import java.util.List;

/**
 * Created by jaydenma on 2017/12/25.
 */

public class MomentDetailInfo {

    /**
     * status_code : 0
     * message : success
     * data : {"momentId":60,"money":0,"content":"","location":"","createdAt":"2017-12-19 10:40:04","user_id":8,"momentType":2,"commentNum":0,"praiseNum":0,"paidNum":0,"userInfo":{"headImg":"http://image.miaoying.tv/images/201712191513668866617305.jpg","nickName":"羊村一枝花","age":28,"uid":11114058,"sex":-1,"userLevel":7,"actorLevel":26,"voiceStatus":0,"videoStatus":0,"officialCer":0,"income":419100,"expenses":644100},"topicTags":[{"tagId":196,"tagName":"神马都可以"}],"comment":[],"isPraise":false,"praiseList":[],"momentMedia":{"imgInfo":{"ImageHeight":"6510","ImageWidth":"562"},"imgs":["http://image.miaoying.tv/images/201712191513651203476521.jpg"],"video":{},"voice":{}},"isPaid":true}
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
         * momentId : 60
         * money : 0
         * content :
         * location :
         * createdAt : 2017-12-19 10:40:04
         * user_id : 8
         * momentType : 2
         * commentNum : 0
         * praiseNum : 0
         * paidNum : 0
         * userInfo : {"headImg":"http://image.miaoying.tv/images/201712191513668866617305.jpg","nickName":"羊村一枝花","age":28,"uid":11114058,"sex":-1,"userLevel":7,"actorLevel":26,"voiceStatus":0,"videoStatus":0,"officialCer":0,"income":419100,"expenses":644100}
         * topicTags : [{"tagId":196,"tagName":"神马都可以"}]
         * comment : []
         * isPraise : false
         * praiseList : []
         * momentMedia : {"imgInfo":{"ImageHeight":"6510","ImageWidth":"562"},"imgs":["http://image.miaoying.tv/images/201712191513651203476521.jpg"],"video":{},"voice":{}}
         * isPaid : true
         */

        private int momentId;
        private int money;
        private String content;
        private String location;
        private String createdAt;
        private int user_id;
        private int momentType;
        private int commentNum;
        private int praiseNum;
        private int paidNum;
        private UserInfoBean userInfo;
        private boolean isPraise;
        private MomentMediaBean momentMedia;
        private boolean isPaid;
        private List<TopicTagsBean> topicTags;
        private List<CommentBean> comment;
        private List<PraiseListBean> praiseList;

        public int getMomentId() {
            return momentId;
        }

        public void setMomentId(int momentId) {
            this.momentId = momentId;
        }

        public int getMoney() {
            return money;
        }

        public void setMoney(int money) {
            this.money = money;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getMomentType() {
            return momentType;
        }

        public void setMomentType(int momentType) {
            this.momentType = momentType;
        }

        public int getCommentNum() {
            return commentNum;
        }

        public void setCommentNum(int commentNum) {
            this.commentNum = commentNum;
        }

        public int getPraiseNum() {
            return praiseNum;
        }

        public void setPraiseNum(int praiseNum) {
            this.praiseNum = praiseNum;
        }

        public int getPaidNum() {
            return paidNum;
        }

        public void setPaidNum(int paidNum) {
            this.paidNum = paidNum;
        }

        public UserInfoBean getUserInfo() {
            return userInfo;
        }

        public void setUserInfo(UserInfoBean userInfo) {
            this.userInfo = userInfo;
        }

        public boolean isIsPraise() {
            return isPraise;
        }

        public void setIsPraise(boolean isPraise) {
            this.isPraise = isPraise;
        }

        public MomentMediaBean getMomentMedia() {
            return momentMedia;
        }

        public void setMomentMedia(MomentMediaBean momentMedia) {
            this.momentMedia = momentMedia;
        }

        public boolean isIsPaid() {
            return isPaid;
        }

        public void setIsPaid(boolean isPaid) {
            this.isPaid = isPaid;
        }

        public List<TopicTagsBean> getTopicTags() {
            return topicTags;
        }

        public void setTopicTags(List<TopicTagsBean> topicTags) {
            this.topicTags = topicTags;
        }

        public List<CommentBean> getComment() {
            return comment;
        }

        public void setComment(List<CommentBean> comment) {
            this.comment = comment;
        }

        public List<PraiseListBean> getPraiseList() {
            return praiseList;
        }

        public void setPraiseList(List<PraiseListBean> praiseList) {
            this.praiseList = praiseList;
        }

        public static class UserInfoBean {
            /**
             * headImg : http://image.miaoying.tv/images/201712191513668866617305.jpg
             * nickName : 羊村一枝花
             * age : 28
             * uid : 11114058
             * sex : -1
             * userLevel : 7
             * actorLevel : 26
             * voiceStatus : 0
             * videoStatus : 0
             * officialCer : 0
             * income : 419100
             * expenses : 644100
             */

            private String headImg;
            private String nickName;
            private int age;
            private int uid;
            private int sex;
            private int userLevel;
            private int actorLevel;
            private int voiceStatus;
            private int videoStatus;
            private int officialCer;
            private int income;
            private int expenses;

            public String getHeadImg() {
                return headImg;
            }

            public void setHeadImg(String headImg) {
                this.headImg = headImg;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public int getAge() {
                return age;
            }

            public void setAge(int age) {
                this.age = age;
            }

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public int getSex() {
                return sex;
            }

            public void setSex(int sex) {
                this.sex = sex;
            }

            public int getUserLevel() {
                return userLevel;
            }

            public void setUserLevel(int userLevel) {
                this.userLevel = userLevel;
            }

            public int getActorLevel() {
                return actorLevel;
            }

            public void setActorLevel(int actorLevel) {
                this.actorLevel = actorLevel;
            }

            public int getVoiceStatus() {
                return voiceStatus;
            }

            public void setVoiceStatus(int voiceStatus) {
                this.voiceStatus = voiceStatus;
            }

            public int getVideoStatus() {
                return videoStatus;
            }

            public void setVideoStatus(int videoStatus) {
                this.videoStatus = videoStatus;
            }

            public int getOfficialCer() {
                return officialCer;
            }

            public void setOfficialCer(int officialCer) {
                this.officialCer = officialCer;
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

        public static class CommentBean {
            /**
             * headImg : http://image.miaoying.tv/images/201712181513592386751809.jpg
             * commentId : 105
             * content : 啦啦啦
             * replyId : 0
             * nickName : 萌我爱
             * uid : 10003279
             * pname :
             * pid : 0
             * createdAt : 20小时前
             */

            private String headImg;
            private int commentId;
            private String content;
            private int replyId;
            private String nickName;
            private int uid;
            private String pname;
            private int pid;
            private String createdAt;

            public String getHeadImg() {
                return headImg;
            }

            public void setHeadImg(String headImg) {
                this.headImg = headImg;
            }

            public int getCommentId() {
                return commentId;
            }

            public void setCommentId(int commentId) {
                this.commentId = commentId;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getReplyId() {
                return replyId;
            }

            public void setReplyId(int replyId) {
                this.replyId = replyId;
            }

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

            public String getPname() {
                return pname;
            }

            public void setPname(String pname) {
                this.pname = pname;
            }

            public int getPid() {
                return pid;
            }

            public void setPid(int pid) {
                this.pid = pid;
            }

            public String getCreatedAt() {
                return createdAt;
            }

            public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
            }
        }

        public static class PraiseListBean {
            /**
             * uid : 10003279
             * headImg : http://image.miaoying.tv/images/201712181513592386751809.jpg
             */


            private int uid;
            private String headImg;

            public PraiseListBean(int uid, String headImg) {
                this.uid = uid;
                this.headImg = headImg;
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
        }
    }

    public static class MomentMediaBean {
        /**
         * imgInfo : {"ImageHeight":"6510","ImageWidth":"562"}
         * imgs : ["http://image.miaoying.tv/images/201712191513651203476521.jpg"]
         * video : {}
         * voice : {}
         */

        private ImgInfoBean imgInfo;
        private VideoBean video;
        private VoiceBean voice;
        private List<String> imgs;

        public ImgInfoBean getImgInfo() {
            return imgInfo;
        }

        public void setImgInfo(ImgInfoBean imgInfo) {
            this.imgInfo = imgInfo;
        }

        public VideoBean getVideo() {
            return video;
        }

        public void setVideo(VideoBean video) {
            this.video = video;
        }

        public VoiceBean getVoice() {
            return voice;
        }

        public void setVoice(VoiceBean voice) {
            this.voice = voice;
        }

        public List<String> getImgs() {
            return imgs;
        }

        public void setImgs(List<String> imgs) {
            this.imgs = imgs;
        }

        public static class ImgInfoBean {
            /**
             * ImageHeight : 6510
             * ImageWidth : 562
             */

            private String ImageHeight;
            private String ImageWidth;

            public String getImageHeight() {
                return ImageHeight;
            }

            public void setImageHeight(String ImageHeight) {
                this.ImageHeight = ImageHeight;
            }

            public String getImageWidth() {
                return ImageWidth;
            }

            public void setImageWidth(String ImageWidth) {
                this.ImageWidth = ImageWidth;
            }
        }

        public static class VideoBean {
            /**
             * videoUrl : http://image.miaoying.tv/video/201712181513594511922320.mp4
             * firstImg : http://image.miaoying.tv/images/201712181513594511922320.jpg
             */

            private String videoUrl;
            private String firstImg;

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

        public static class VoiceBean {
            /**
             * voiceUrl : http://image.miaoying.tv/voice/201712181513606657400097.aac
             * voiceTime : 9
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
    }

    public static class TopicTagsBean {
        /**
         * tagId : 196
         * tagName : 神马都可以
         */

        private int tagId;
        private String tagName;

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
    }
}

