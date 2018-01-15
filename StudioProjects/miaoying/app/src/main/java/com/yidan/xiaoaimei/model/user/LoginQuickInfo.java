package com.yidan.xiaoaimei.model.user;

/**
 * Created by jaydenma on 2017/10/17.
 */

public class LoginQuickInfo {


    /**
     * status_code : 0
     * message : success
     * data : {"uid":55559880,"headImg":"http://image.miaoying.tv/image/1511500855702.jpg","nickName":"ak","sex":-1,"isTag":1,"isMember":0,"firstLogin":0,"yunxinToken":"d9fb548132b0fdf992e9c534c9e7423f","yunxinId":55559880}
     * tokenInfo : {"token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOi8vYXBpdGVzdC5taWFveWluZy50di9hcGkvYXV0aC9uZXdMb2dpbiIsImlhdCI6MTUxMTUxMDg0NCwiZXhwIjoxNTExNzcwMDQ0LCJuYmYiOjE1MTE1MTA4NDQsImp0aSI6Ik9mVVlRV05sS29Galo3anciLCJzdWIiOjN9.NvEF-vXuyMJaAe2IzoThW0l7sAdsERMMteKEJYpTNdc","expiredAt":"2017-11-27 16:07:24","refreshExpiredAt":"2017-12-08 16:07:24"}
     */

    private int status_code;
    private String message;
    private DataBean data;
    private TokenInfoBean tokenInfo;

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

    public TokenInfoBean getTokenInfo() {
        return tokenInfo;
    }

    public void setTokenInfo(TokenInfoBean tokenInfo) {
        this.tokenInfo = tokenInfo;
    }

    public static class DataBean {
        /**
         * uid : 55559880
         * headImg : http://image.miaoying.tv/image/1511500855702.jpg
         * nickName : ak
         * sex : -1
         * isTag : 1
         * isMember : 0
         * firstLogin : 0
         * yunxinToken : d9fb548132b0fdf992e9c534c9e7423f
         * yunxinId : 55559880
         */

        private int uid;
        private String headImg;
        private String nickName;
        private int sex;
        private int isTag;
        private int isMember;
        private int firstLogin;
        private String yunxinToken;
        private int yunxinId;

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

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public int getIsTag() {
            return isTag;
        }

        public void setIsTag(int isTag) {
            this.isTag = isTag;
        }

        public int getIsMember() {
            return isMember;
        }

        public void setIsMember(int isMember) {
            this.isMember = isMember;
        }

        public int getFirstLogin() {
            return firstLogin;
        }

        public void setFirstLogin(int firstLogin) {
            this.firstLogin = firstLogin;
        }

        public String getYunxinToken() {
            return yunxinToken;
        }

        public void setYunxinToken(String yunxinToken) {
            this.yunxinToken = yunxinToken;
        }

        public int getYunxinId() {
            return yunxinId;
        }

        public void setYunxinId(int yunxinId) {
            this.yunxinId = yunxinId;
        }
    }

    public static class TokenInfoBean {
        /**
         * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOi8vYXBpdGVzdC5taWFveWluZy50di9hcGkvYXV0aC9uZXdMb2dpbiIsImlhdCI6MTUxMTUxMDg0NCwiZXhwIjoxNTExNzcwMDQ0LCJuYmYiOjE1MTE1MTA4NDQsImp0aSI6Ik9mVVlRV05sS29Galo3anciLCJzdWIiOjN9.NvEF-vXuyMJaAe2IzoThW0l7sAdsERMMteKEJYpTNdc
         * expiredAt : 2017-11-27 16:07:24
         * refreshExpiredAt : 2017-12-08 16:07:24
         */

        private String token;
        private String expiredAt;
        private String refreshExpiredAt;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getExpiredAt() {
            return expiredAt;
        }

        public void setExpiredAt(String expiredAt) {
            this.expiredAt = expiredAt;
        }

        public String getRefreshExpiredAt() {
            return refreshExpiredAt;
        }

        public void setRefreshExpiredAt(String refreshExpiredAt) {
            this.refreshExpiredAt = refreshExpiredAt;
        }
    }
}
