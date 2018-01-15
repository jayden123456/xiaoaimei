package com.yidan.xiaoaimei.model.mine;

/**
 * Created by jaydenma on 2017/8/3.
 */

public class AuthWithdrawBean {

    private String identityName;
    private String identityNum;
    private String alipayAccount;
    private String mobile;
    private String verifyCode;

    public AuthWithdrawBean(String identityName, String identityNum, String alipayAccount, String mobile, String verifyCode) {
        this.identityName = identityName;
        this.identityNum = identityNum;
        this.alipayAccount = alipayAccount;
        this.mobile = mobile;
        this.verifyCode = verifyCode;
    }

    public String getIdentityName() {
        return identityName;
    }

    public void setIdentityName(String identityName) {
        this.identityName = identityName;
    }

    public String getIdentityNum() {
        return identityNum;
    }

    public void setIdentityNum(String identityNum) {
        this.identityNum = identityNum;
    }

    public String getAlipayAccount() {
        return alipayAccount;
    }

    public void setAlipayAccount(String alipayAccount) {
        this.alipayAccount = alipayAccount;
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
}
