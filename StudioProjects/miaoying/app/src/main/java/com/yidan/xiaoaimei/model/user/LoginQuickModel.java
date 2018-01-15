package com.yidan.xiaoaimei.model.user;

import android.content.Context;
import android.util.Log;

import com.miaokong.commonutils.utils.SharedPreferencesUtil;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.socks.library.KLog;
import com.yidan.xiaoaimei.Const;
import com.yidan.xiaoaimei.MyCache;
import com.yidan.xiaoaimei.contract.LoginQuickContract;
import com.yidan.xiaoaimei.http.base.APIService;
import com.yidan.xiaoaimei.http.base.OnHttpCallBack;
import com.yidan.xiaoaimei.http.utils.RetrofitUtils;
import com.yidan.xiaoaimei.preference.Preferences;
import com.yidan.xiaoaimei.utils.ToastUtils;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 快捷登录model
 * Created by jaydenma on 2017/7/17.
 */

public class LoginQuickModel implements LoginQuickContract.ILoginQuickModel {

    @Override
    public void login(UserInfo userInfo, final OnHttpCallBack<LoginQuickInfo> callBack) {
        //网络请求
        RetrofitUtils.newInstence(Const.baseUrl)
                .create(APIService.class)
                .loginQuick(userInfo.getMobile(), userInfo.getVerifyCode(), userInfo.getLocation())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LoginQuickInfo>() {
                    @Override
                    public void onCompleted() {
                        //完成的时候调用
                    }

                    @Override
                    public void onError(Throwable e) {
                        KLog.e(e.getMessage() + "--");
                        e.printStackTrace();
                        if (e instanceof HttpException) {
                            HttpException httpException = (HttpException) e;
                            //httpException.response().errorBody().string()
                            int code = httpException.code();
                            if (code == 500 || code == 404) {
                                callBack.onFaild("服务器出错");
                            } else {
                                callBack.onFaild("服务器异常");
                            }
                        } else if (e instanceof ConnectException) {
                            callBack.onFaild("网络断开,请打开网络!");
                        } else if (e instanceof SocketTimeoutException) {
                            callBack.onFaild("网络连接超时!!");
                        } else {
                            callBack.onFaild("发生未知错误" + e.getMessage());
                            KLog.e("Myloy", e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(LoginQuickInfo loginQuickInfo) {
                        if (loginQuickInfo.getStatus_code() == 0) {
                            callBack.onSuccessful(loginQuickInfo);
                        } else {
                            callBack.onFaild(loginQuickInfo.getMessage());
                        }
                    }
                });
    }

    @Override
    public void getAdverPage(final OnHttpCallBack<AdverInfo> callBack) {
        //网络请求
        RetrofitUtils.newInstence(Const.baseUrl)
                .create(APIService.class)
                .getAdverPage()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AdverInfo>() {
                    @Override
                    public void onCompleted() {
                        //完成的时候调用
                    }

                    @Override
                    public void onError(Throwable e) {
                        KLog.e(e.getMessage() + "--");
                        e.printStackTrace();
                        if (e instanceof HttpException) {
                            HttpException httpException = (HttpException) e;
                            //httpException.response().errorBody().string()
                            int code = httpException.code();
                            if (code == 500 || code == 404) {
                                callBack.onFaild("服务器出错");
                            } else {
                                callBack.onFaild("服务器异常");
                            }
                        } else if (e instanceof ConnectException) {
                            callBack.onFaild("网络断开,请打开网络!");
                        } else if (e instanceof SocketTimeoutException) {
                            callBack.onFaild("网络连接超时!!");
                        } else {
                            callBack.onFaild("发生未知错误" + e.getMessage());
                            KLog.e("Myloy", e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(AdverInfo adverInfo) {
                        if (adverInfo.getStatus_code() == 0) {
                            callBack.onSuccessful(adverInfo);
                        } else {
                            callBack.onFaild(adverInfo.getMessage());
                        }
                    }
                });
    }

    @Override
    public void refreshToken(String token, final OnHttpCallBack<LoginQuickInfo> callBack) {
        //网络请求
        RetrofitUtils.newInstence(Const.baseUrl)
                .create(APIService.class)
                .refreshToken(token)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LoginQuickInfo>() {
                    @Override
                    public void onCompleted() {
                        //完成的时候调用
                    }

                    @Override
                    public void onError(Throwable e) {
                        KLog.e(e.getMessage() + "--");
                        e.printStackTrace();
                        if (e instanceof HttpException) {
                            HttpException httpException = (HttpException) e;
                            //httpException.response().errorBody().string()
                            int code = httpException.code();
                            if (code == 500 || code == 404) {
                                callBack.onFaild("服务器出错");
                            } else {
                                callBack.onFaild("服务器异常");
                            }
                        } else if (e instanceof ConnectException) {
                            callBack.onFaild("网络断开,请打开网络!");
                        } else if (e instanceof SocketTimeoutException) {
                            callBack.onFaild("网络连接超时!!");
                        } else {
                            callBack.onFaild("发生未知错误" + e.getMessage());
                            KLog.e("Myloy", e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(LoginQuickInfo loginQuickInfo) {
                        if (loginQuickInfo.getStatus_code() == 0) {
                            callBack.onSuccessful(loginQuickInfo);
                        } else {
                            callBack.onFaild(loginQuickInfo.getMessage());
                        }
                    }
                });
    }

    @Override
    public void getVerifyCode(String phone, final OnHttpCallBack<VerifyCodeInfo> callBack) {
        //网络请求
        RetrofitUtils.newInstence(Const.baseUrl)
                .create(APIService.class)
                .getCode(phone, "0")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<VerifyCodeInfo>() {
                    @Override
                    public void onCompleted() {
                        //完成的时候调用
                    }

                    @Override
                    public void onError(Throwable e) {
                        KLog.e(e.getMessage() + "--");
                        e.printStackTrace();
                        if (e instanceof HttpException) {
                            HttpException httpException = (HttpException) e;
                            //httpException.response().errorBody().string()
                            int code = httpException.code();
                            if (code == 500 || code == 404) {
                                callBack.onFaild("服务器出错");
                            }
                        } else if (e instanceof ConnectException) {
                            callBack.onFaild("网络断开,请打开网络!");
                        } else if (e instanceof SocketTimeoutException) {
                            callBack.onFaild("网络连接超时!!");
                        } else {
                            callBack.onFaild("发生未知错误" + e.getMessage());
                            KLog.e("Myloy", e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(VerifyCodeInfo verifyCodeInfo) {
                        if (verifyCodeInfo.getStatus_code() == 0) {
                            callBack.onSuccessful(verifyCodeInfo);
                        } else {
                            callBack.onFaild(verifyCodeInfo.getMessage());
                        }
                    }
                });
    }

    private void saveLoginInfo(final String account, final String token) {
        Preferences.saveUserAccount(account);
        Preferences.saveUserToken(token);
    }

    @Override
    public void saveUserInfo(final Context context, final LoginQuickInfo loginQuickInfo) {
//        Set<String> tags = new HashSet<>();
//        tags.add(loginQuickInfo.getData().getUserUniqueId() + "=mk");
//        JPushInterface.setTags(context, 0, tags);
        SharedPreferencesUtil spUtil = new SharedPreferencesUtil(context, Const.SP_NAME);
        spUtil.putStringValue("token", "Bearer" + loginQuickInfo.getTokenInfo().getToken());
        spUtil.putStringValue("nickname", loginQuickInfo.getData().getNickName());
        spUtil.putStringValue("headImg", loginQuickInfo.getData().getHeadImg());
        spUtil.putIntValue("sex", loginQuickInfo.getData().getSex());
        spUtil.putIntValue("isTag", loginQuickInfo.getData().getIsTag());
        spUtil.putIntValue("firstLogin", loginQuickInfo.getData().getFirstLogin());
        spUtil.putIntValue("isMember", loginQuickInfo.getData().getIsMember());
        spUtil.putStringValue("userId", loginQuickInfo.getData().getUid() + "");
        spUtil.putStringValue("nimToken", loginQuickInfo.getData().getYunxinToken());
        spUtil.putStringValue("nimAccount", loginQuickInfo.getData().getYunxinId() + "");
        LoginInfo info = new LoginInfo(loginQuickInfo.getData().getYunxinId() + "", loginQuickInfo.getData().getYunxinToken());
        RequestCallback<LoginInfo> callback =
                new RequestCallback<LoginInfo>() {
                    @Override
                    public void onSuccess(LoginInfo param) {
                        ToastUtils.ShowSucess(context, "云信登录成功");
                        MyCache.setAccount(loginQuickInfo.getData().getYunxinId()+"");
                        saveLoginInfo(loginQuickInfo.getData().getYunxinId()+"", loginQuickInfo.getData().getYunxinToken());
                    }

                    @Override
                    public void onFailed(int code) {
                        Log.d("nimsdk", "登录失败" + code);
                    }

                    @Override
                    public void onException(Throwable exception) {

                    }
                };
        NIMClient.getService(AuthService.class).login(info)
                .setCallback(callback);
    }


}
