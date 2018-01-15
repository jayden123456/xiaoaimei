package com.yidan.xiaoaimei.model.mine;

import java.util.List;

/**
 * Created by jaydenma on 2017/11/17.
 */

public class PersonInfo {

    /**
     * status_code : 0
     * message : success
     * data : {"uid":55559880,"nickName":"ak","headImg":"http://image.miaoying.tv/image/1511251938032.jpg","tags":[{"tagName":"礼仪","tagId":4},{"tagName":"潮男","tagId":8}],"isTag":1,"serviceTags":[{"tagName":"活动主持","tagId":86},{"tagName":"演员","tagId":112}],"fansCount":2,"followCount":0,"city":null,"signature":"这种天气，只适合在家吃着西瓜，吹着空调还不够","age":26,"height":169,"measurements":[69,68,72],"sex":-1,"isMember":0,"momentNum":0,"income":10000,"expenses":5000,"wechatPay":0,"balance":0}
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
         * uid : 55559880
         * nickName : ak
         * headImg : http://image.miaoying.tv/image/1511251938032.jpg
         * tags : [{"tagName":"礼仪","tagId":4},{"tagName":"潮男","tagId":8}]
         * isTag : 1
         * serviceTags : [{"tagName":"活动主持","tagId":86},{"tagName":"演员","tagId":112}]
         * fansCount : 2
         * followCount : 0
         * city : null
         * signature : 这种天气，只适合在家吃着西瓜，吹着空调还不够
         * age : 26
         * height : 169
         * measurements : [69,68,72]
         * sex : -1
         * isMember : 0
         * momentNum : 0
         * income : 10000
         * expenses : 5000
         * wechatPay : 0
         * balance : 0
         */

        private int uid;
        private String nickName;
        private String headImg;
        private int isTag;
        private int fansCount;
        private int followCount;
        private String city;
        private String signature;
        private int age;
        private int height;
        private int sex;
        private int isMember;
        private int momentNum;
        private int income;
        private int expenses;
        private int wechatPay;
        private int balance;
        private List<TagsBean> tags;
        private List<ServiceTagsBean> serviceTags;
        private List<Integer> measurements;
        private CityInfoBean cityInfo;

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

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public int getIsMember() {
            return isMember;
        }

        public void setIsMember(int isMember) {
            this.isMember = isMember;
        }

        public int getMomentNum() {
            return momentNum;
        }

        public void setMomentNum(int momentNum) {
            this.momentNum = momentNum;
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

        public int getWechatPay() {
            return wechatPay;
        }

        public void setWechatPay(int wechatPay) {
            this.wechatPay = wechatPay;
        }

        public int getBalance() {
            return balance;
        }

        public void setBalance(int balance) {
            this.balance = balance;
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

        public List<Integer> getMeasurements() {
            return measurements;
        }

        public void setMeasurements(List<Integer> measurements) {
            this.measurements = measurements;
        }

        public CityInfoBean getCityInfo() {
            return cityInfo;
        }

        public void setCityInfo(CityInfoBean cityInfo) {
            this.cityInfo = cityInfo;
        }

        public static class TagsBean {
            /**
             * tagName : 礼仪
             * tagId : 4
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
             * tagName : 活动主持
             * tagId : 86
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

    public static class CityInfoBean {
        /**
         * pid : 21
         * cityId : 278
         * cityName : 海东
         */

        private int pid;
        private int cityId;
        private String cityName;

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public int getCityId() {
            return cityId;
        }

        public void setCityId(int cityId) {
            this.cityId = cityId;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }
    }


}
