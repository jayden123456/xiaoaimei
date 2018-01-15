package com.yidan.xiaoaimei.model.user;

/**
 * Created by jaydenma on 2017/7/17.
 */

public class UserInfo {
    private String user;
    private String pwd;
    private String headImg;
    private String nickName;
    private int sex;
    private String agentId;
    private String mobile;
    private String verifyCode;
    private String password_confirmation;
    private String location;
    private String cityId;


    public UserInfo(String user, String pwd) {
        this.user = user;
        this.pwd = pwd;
    }

    public UserInfo(String headImg, String nickName, int sex, String agentId, String location, String cityId) {
        this.headImg = headImg;
        this.nickName = nickName;
        this.sex = sex;
        this.agentId = agentId;
        this.location = location;
        this.cityId = cityId;
    }

    public UserInfo(String mobile, String verifyCode, String pwd, String password_confirmation) {
        this.mobile = mobile;
        this.verifyCode = verifyCode;
        this.pwd = pwd;
        this.password_confirmation = password_confirmation;
    }


    public UserInfo(String mobile, String verifyCode, String location) {
        this.mobile = mobile;
        this.verifyCode = verifyCode;
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
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

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getPassword_confirmation() {
        return password_confirmation;
    }

    public void setPassword_confirmation(String password_confirmation) {
        this.password_confirmation = password_confirmation;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }
}
