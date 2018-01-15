package com.miaokong.commonutils.utils;

import android.content.Context;
import android.text.TextUtils;

import com.miaokong.commonutils.R;

/**
 * Created by Administrator on 2017/5/5.
 * 校验手机号和密码是否正确
 */

public class CheckUtils {
    /**
     * 校验手机号码
     *
     * @param mContext  上下文
     * @param mobileNum  手机号码
     * @return
     */
    //手机号码正则表达式
    public static final String MOBILE_REGEX = "[1][3587]\\d{9}";
    //密码检测的正则表达式
    public static final String CHAECK_PWD = "^\\w+$";//只能输入由数字和26个英文字母或者下划线组成的字符串

    public static boolean checkMobileNum(Context mContext, String mobileNum) {
        //非空判断
        if (TextUtils.isEmpty(mobileNum)) {
            ToastUtil.showToast(mContext, mContext.getResources().getString(R.string.toast_login_phone_empty));
            return false;
        }

        //是否是正确的手机号码
        boolean matches = mobileNum.matches(MOBILE_REGEX);
        if (!matches) {
            ToastUtil.showToast(mContext, mContext.getResources().getString(R.string.toast_login_phone_error));
            return false;
        }
        return true;
    }

    /**
     * 校验密码
     *
     * @param mContext 上下文
     * @param firstPwd 首次密码
     * @param againPwd 确认密码
     * @return
     */
    public static boolean checkPwd(Context mContext, String firstPwd, String againPwd) {
        if (firstPwd == null || againPwd == null) {
            ToastUtil.showToast(mContext, mContext.getResources().getString(R.string.toast_login_pwd_empty));
            return false;
        }

        int length = firstPwd.length();
        if (length < 6 || length > 16) {
            ToastUtil.showToast(mContext, mContext.getResources().getString(R.string.toast_login_pwd_error));
            return false;
        }
        boolean matches = firstPwd.matches(CHAECK_PWD);
        if (!matches) {
            ToastUtil.showToast(mContext, mContext.getResources().getString(R.string.toast_login_pwd_error));
            return false;
        }

        if (!firstPwd.equals(againPwd)) {
            ToastUtil.showToast(mContext, mContext.getResources().getString(R.string.toast_login_same_pwd));
            return false;
        }
        return true;
    }

    /**
     * 检测密码和验证码
     *
     * @param mContext   上下文
     * @param firstPwd   首次密码
     * @param againPwd   确认密码
     * @param verifycode 验证码
     * @return
     */
    public static boolean checkNumAndCode(Context mContext, String firstPwd, String againPwd, String verifycode, String mobileNum) {
        //非空判断
        if (TextUtils.isEmpty(mobileNum)) {
            ToastUtil.showToast(mContext, mContext.getResources().getString(R.string.toast_login_phone_empty));
            return false;
        }

        //是否是正确的手机号码
        boolean matches = mobileNum.matches(MOBILE_REGEX);
        if (!matches) {
            ToastUtil.showToast(mContext, mContext.getResources().getString(R.string.toast_login_phone_error));
            return false;
        }

        if (firstPwd == null || againPwd == null) {
            ToastUtil.showToast(mContext, mContext.getResources().getString(R.string.toast_login_pwd_empty));
            return false;
        }

        if (TextUtils.isEmpty(verifycode)) {
            ToastUtil.showToast(mContext, mContext.getResources().getString(R.string.toast_login_code_empty));
            return false;
        }

        int length = firstPwd.length();
        if (length < 6 || length > 16) {
            ToastUtil.showToast(mContext, mContext.getResources().getString(R.string.toast_login_pwd_error));
            return false;
        }

        if (!firstPwd.equals(againPwd)) {
            ToastUtil.showToast(mContext, mContext.getResources().getString(R.string.toast_login_same_pwd));
            return false;
        }

        return true;
    }

    /**
     * 检查手机号码和密码
     *
     * @param mContext  上下文
     * @param mobileNum 手机号码
     * @param password  密码
     * @return
     */
    public static boolean checkMobileAndPwd(Context mContext, String mobileNum, String password) {
        if (TextUtils.isEmpty(mobileNum)) {
            ToastUtil.showToast(mContext, mContext.getResources().getString(R.string.toast_login_phone_empty));
            return false;
        }

        boolean matches = mobileNum.matches(MOBILE_REGEX);
        if (!matches) {
            ToastUtil.showToast(mContext, mContext.getResources().getString(R.string.toast_login_phone_error));
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            ToastUtil.showToast(mContext, mContext.getResources().getString(R.string.toast_login_pwd_empty));
            return false;
        }

        return true;
    }


    /**
     * 校验验证码
     *
     * @param mContext
     * @param verfiyCode
     * @return
     */
    public static boolean checkVerifyCodeAndNum(Context mContext, String mobileNum, String verfiyCode) {
        if (TextUtils.isEmpty(mobileNum)) {
            ToastUtil.showToast(mContext, mContext.getResources().getString(R.string.toast_login_phone_empty));
            return false;
        }

        boolean matches = mobileNum.matches(MOBILE_REGEX);
        if (!matches) {
            ToastUtil.showToast(mContext, mContext.getResources().getString(R.string.toast_login_phone_error));
            return false;
        }

        if (TextUtils.isEmpty(verfiyCode)) {
            ToastUtil.showToast(mContext, mContext.getResources().getString(R.string.toast_login_code_empty));
            return false;
        }
        return true;
    }


}
