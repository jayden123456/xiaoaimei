package com.yidan.xiaoaimei.presenter;

import android.util.Log;

import com.yidan.xiaoaimei.contract.LoginQuickContract;
import com.yidan.xiaoaimei.http.base.OnHttpCallBack;
import com.yidan.xiaoaimei.model.user.AdverInfo;
import com.yidan.xiaoaimei.model.user.LoginQuickInfo;
import com.yidan.xiaoaimei.model.user.LoginQuickModel;
import com.yidan.xiaoaimei.model.user.VerifyCodeInfo;


/**
 * 登录的桥梁(View和Model的桥梁)-------P通过将V拿到的数据交给M来处理  P又将处理的结果回显到V
 * 说白点就是P中new出M和V 然后通过调用它们两个的方法来完成操作
 * <p/>
 * (看例子之前看一遍下面的直白的解释,看完之后再看一遍就更明白MVP模式了)
 * --------V层   负责响应用户的交互(获取数据---->提示操作结果)
 * --------P层   传递完数据给M层处理之后,实例化回调对象,成功了就通知V层登录成功,失败了就通知V层显示错误信息
 * --------M层   对P层传递过来的userInfo进行登录(网络请求)判断,处理完成之后将处理结果回调给P层
 * Created by jaydenma on 2017/7/17.
 */

public class LoginQuickPresenter implements LoginQuickContract.ILoginQuickPresenter {

    private LoginQuickContract.ILoginQuickView mLoginQuickView;
    private LoginQuickContract.ILoginQuickModel mLoginQuickModel;


    public LoginQuickPresenter(LoginQuickContract.ILoginQuickView mLoginQuickView) {
        this.mLoginQuickView = mLoginQuickView;
        mLoginQuickModel = new LoginQuickModel();
    }


    /**
     * 登录
     */
    @Override
    public void login() {
        mLoginQuickView.showProgress();
        mLoginQuickModel.login(mLoginQuickView.getUserLoginInfo(), new OnHttpCallBack<LoginQuickInfo>() {
            @Override
            public void onSuccessful(LoginQuickInfo loginQuickInfo) {
                mLoginQuickView.hideProgress();
                mLoginQuickModel.saveUserInfo(mLoginQuickView.getCurContext(), loginQuickInfo);
                mLoginQuickView.toMain();
                mLoginQuickView.showMsg("登录成功");
            }

            @Override
            public void onFaild(String errorMsg) {
                mLoginQuickView.hideProgress();
                mLoginQuickView.showError(errorMsg);
            }
        });
    }

    @Override
    public void getAdverPage() {
        mLoginQuickModel.getAdverPage(new OnHttpCallBack<AdverInfo>() {
            @Override
            public void onSuccessful(AdverInfo info) {
                mLoginQuickView.showAdverPage(info);
            }

            @Override
            public void onFaild(String errorMsg) {
                mLoginQuickView.showError(errorMsg);
            }
        });
    }

    @Override
    public void refreshToken() {
        mLoginQuickView.showProgress();
        mLoginQuickModel.refreshToken(mLoginQuickView.getToken(), new OnHttpCallBack<LoginQuickInfo>() {
            @Override
            public void onSuccessful(LoginQuickInfo loginQuickInfo) {
                mLoginQuickView.hideProgress();
                mLoginQuickModel.saveUserInfo(mLoginQuickView.getCurContext(), loginQuickInfo);
                mLoginQuickView.toMain();
            }

            @Override
            public void onFaild(String errorMsg) {
                mLoginQuickView.hideProgress();
                mLoginQuickView.showError(errorMsg);
                mLoginQuickView.toLogin();
            }
        });
    }

    @Override
    public void getVerifyCode() {
        mLoginQuickView.showProgress();
        mLoginQuickModel.getVerifyCode(mLoginQuickView.getUserLoginInfo().getMobile(), new OnHttpCallBack<VerifyCodeInfo>() {
            @Override
            public void onSuccessful(VerifyCodeInfo verifyCodeInfo) {
                mLoginQuickView.hideProgress();
                Log.e("verifycode", "验证码:" + verifyCodeInfo.getData().getVerify_code() + "");
                mLoginQuickView.showMsg("已将验证码发送至\n" + verifyCodeInfo.getData().getMobile());
                mLoginQuickView.timeStart();
            }

            @Override
            public void onFaild(String errorMsg) {
                mLoginQuickView.hideProgress();
                mLoginQuickView.showError(errorMsg);
            }
        });
    }
}
