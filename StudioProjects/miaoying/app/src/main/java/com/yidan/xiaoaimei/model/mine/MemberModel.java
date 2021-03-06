package com.yidan.xiaoaimei.model.mine;

import com.socks.library.KLog;
import com.yidan.xiaoaimei.Const;
import com.yidan.xiaoaimei.contract.MemberContract;
import com.yidan.xiaoaimei.http.base.APIService;
import com.yidan.xiaoaimei.http.base.OnHttpCallBack;
import com.yidan.xiaoaimei.http.utils.RetrofitUtils;
import com.yidan.xiaoaimei.model.CommonInfo;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 我的model
 * Created by jaydenma on 2017/7/19.
 */

public class MemberModel implements MemberContract.IMemberModel {

    @Override
    public void getMemberOptions(String token,final OnHttpCallBack<MemberOptionsInfo> callBack) {
        //网络请求
        RetrofitUtils.newInstence(Const.baseUrl)
                .create(APIService.class)
                .getMemberPayOptions(token)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MemberOptionsInfo>() {
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
                    public void onNext(MemberOptionsInfo memberOptionsInfo) {
                        if (memberOptionsInfo.getStatus_code() == 0) {
                            callBack.onSuccessful(memberOptionsInfo);
                        } else {
                            callBack.onFaild(memberOptionsInfo.getMessage());
                        }
                    }
                });
    }

    @Override
    public void recharge(String token, String payType, String payOptionId, final OnHttpCallBack<CommonInfo> callBack) {
        //网络请求
        RetrofitUtils.newInstence(Const.baseUrl)
                .create(APIService.class)
                .memberRecharge(token,payType,payOptionId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CommonInfo>() {
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
                    public void onNext(CommonInfo commonInfo) {
                        if (commonInfo.getStatus_code() == 0) {
                            callBack.onSuccessful(commonInfo);
                        } else {
                            callBack.onFaild(commonInfo.getMessage());
                        }
                    }
                });
    }
}
