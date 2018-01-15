package com.yidan.xiaoaimei.contract;

import android.content.Context;

import com.yidan.xiaoaimei.http.base.OnHttpCallBack;
import com.yidan.xiaoaimei.model.user.AdverInfo;
import com.yidan.xiaoaimei.model.user.LoginQuickInfo;
import com.yidan.xiaoaimei.model.user.UserInfo;
import com.yidan.xiaoaimei.model.user.VerifyCodeInfo;


/**
 * 快捷登录
 * Created by jaydenma on 2017/7/17.
 */

public class LoginQuickContract {
    /**
     * V视图层
     */
    public interface ILoginQuickView {
        Context getCurContext();//获取上下文对象,用于保存数据等

        void showProgress();//可以显示进度条

        void hideProgress();//可以隐藏进度条

        void showMsg(String info);//提示用户,提升友好交互

        void showError(String msg);//发生错误就显示错误信息

        void toMain();//跳转到主页面

        void toLogin();

        void toNext();//下一步

        UserInfo getUserLoginInfo();//获取用户登录信息

        String getToken();

        void timeStart(); //按钮倒计时开始

        void showAdverPage(AdverInfo info);
    }

    /**
     * P视图与逻辑处理的连接层
     */
    public interface ILoginQuickPresenter {
        void login();//唯一的桥梁就是登录了

        void getAdverPage();  //获取广告页

        void refreshToken();  //刷新token

        void getVerifyCode();//获取验证码
    }

    /**
     * 逻辑处理层
     */
    public interface ILoginQuickModel {
        void login(UserInfo userInfo, OnHttpCallBack<LoginQuickInfo> callBack);//登录

        void getAdverPage(OnHttpCallBack<AdverInfo> callBack); //获取广告页

        void refreshToken(String token, OnHttpCallBack<LoginQuickInfo> callBack); //刷新token

        void getVerifyCode(String phone, OnHttpCallBack<VerifyCodeInfo> callBack);//获取验证码

        void saveUserInfo(Context context, LoginQuickInfo loginQuickInfo);//登录成功就保存用户信息
    }


}
