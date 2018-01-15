package com.yidan.xiaoaimei.model.home;

import java.util.List;

/**
 * Created by jaydenma on 2017/12/8.
 */

public class HomeCommonInfo {


    /**
     * status_code : 0
     * message : success
     * data : {"lists":[{"uid":77772002,"nickName":"刘松5549","headImg":"http://image.miaoying.tv/images/201712061512542041259198.jpg","city":"","measurements":[],"signature":"","tags":[{"tagName":"主播","tagId":2},{"tagName":"学生","tagId":6}],"serviceTags":[{"tagName":"粉丝推广","tagId":85}],"isFollowed":0}],"banner":[{"id":1,"type":1,"jumpUrl":"http://api.miaokong.tv/miaokong/miaoGirlRecruit/pages/rank.html","bannerUrl":"http://image.miaoying.tv/images/634642487476309570.png","iconUrl":"http://image.miaoying.tv/images/miaokong.png","title":"喵空-助威「喵女郎」","content":"「喵女郎」全国海选活动，投票火热进行中！快来给喜欢的她投上珍贵的一票。","shareUrl":"http://api.miaokong.tv/api/vote","created_at":"2017-11-15 17:33:33","disable":0,"bannerType":0,"typeId":0},{"id":2,"type":1,"jumpUrl":"http://api.miaokong.tv/miaokong/miaoGirlRecruit/pages/enroll.html","bannerUrl":"http://image.miaoying.tv/images/599524fc62fa6.png","iconUrl":"http://image.miaoying.tv/images/miaokong.png","title":"「喵女郎」全国海选就等你来！","content":"「喵女郎」全国海选，各大城市进行中！挑选你所在的参赛城市报名，就有机会赴韩国SM公司参观学习啦！","shareUrl":"http://api.miaokong.tv/miaokong/miaoGirlRecruit/pages/enroll.html","created_at":"2017-11-15 17:33:38","disable":0,"bannerType":0,"typeId":0}]}
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
        private List<ListsBean> lists;
        private List<BannerBean> banner;

        public List<ListsBean> getLists() {
            return lists;
        }

        public void setLists(List<ListsBean> lists) {
            this.lists = lists;
        }

        public List<BannerBean> getBanner() {
            return banner;
        }

        public void setBanner(List<BannerBean> banner) {
            this.banner = banner;
        }

        public static class ListsBean {
            /**
             * uid : 77772002
             * nickName : 刘松5549
             * headImg : http://image.miaoying.tv/images/201712061512542041259198.jpg
             * city :
             * measurements : []
             * signature :
             * tags : [{"tagName":"主播","tagId":2},{"tagName":"学生","tagId":6}]
             * serviceTags : [{"tagName":"粉丝推广","tagId":85}]
             * isFollowed : 0
             */

            private int uid;
            private String nickName;
            private String headImg;
            private String city;
            private String signature;
            private int isFollowed;
            private List<Integer> measurements;
            private List<TagsBean> tags;
            private List<ServiceTagsBean> serviceTags;

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

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
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

            public List<Integer> getMeasurements() {
                return measurements;
            }

            public void setMeasurements(List<Integer> measurements) {
                this.measurements = measurements;
            }

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
        }

        public static class BannerBean {
            /**
             * id : 1
             * type : 1
             * jumpUrl : http://api.miaokong.tv/miaokong/miaoGirlRecruit/pages/rank.html
             * bannerUrl : http://image.miaoying.tv/images/634642487476309570.png
             * iconUrl : http://image.miaoying.tv/images/miaokong.png
             * title : 喵空-助威「喵女郎」
             * content : 「喵女郎」全国海选活动，投票火热进行中！快来给喜欢的她投上珍贵的一票。
             * shareUrl : http://api.miaokong.tv/api/vote
             * created_at : 2017-11-15 17:33:33
             * disable : 0
             * bannerType : 0
             * typeId : 0
             */

            private int id;
            private int type;
            private String jumpUrl;
            private String bannerUrl;
            private String iconUrl;
            private String title;
            private String content;
            private String shareUrl;
            private String created_at;
            private int disable;
            private int bannerType;
            private int typeId;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getJumpUrl() {
                return jumpUrl;
            }

            public void setJumpUrl(String jumpUrl) {
                this.jumpUrl = jumpUrl;
            }

            public String getBannerUrl() {
                return bannerUrl;
            }

            public void setBannerUrl(String bannerUrl) {
                this.bannerUrl = bannerUrl;
            }

            public String getIconUrl() {
                return iconUrl;
            }

            public void setIconUrl(String iconUrl) {
                this.iconUrl = iconUrl;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getShareUrl() {
                return shareUrl;
            }

            public void setShareUrl(String shareUrl) {
                this.shareUrl = shareUrl;
            }

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

            public int getDisable() {
                return disable;
            }

            public void setDisable(int disable) {
                this.disable = disable;
            }

            public int getBannerType() {
                return bannerType;
            }

            public void setBannerType(int bannerType) {
                this.bannerType = bannerType;
            }

            public int getTypeId() {
                return typeId;
            }

            public void setTypeId(int typeId) {
                this.typeId = typeId;
            }
        }
    }
}
