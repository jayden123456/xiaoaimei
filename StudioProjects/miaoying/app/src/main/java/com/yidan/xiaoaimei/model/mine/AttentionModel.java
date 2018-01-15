package com.yidan.xiaoaimei.model.mine;

import com.socks.library.KLog;
import com.yidan.xiaoaimei.Const;
import com.yidan.xiaoaimei.contract.AttentionContract;
import com.yidan.xiaoaimei.http.base.APIService;
import com.yidan.xiaoaimei.http.base.OnHttpCallBack;
import com.yidan.xiaoaimei.http.utils.RetrofitUtils;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 关注，粉丝model
 * Created by jaydenma on 2017/7/17.
 */

public class AttentionModel implements AttentionContract.IAttentionModel {

    @Override
    public void getAttention(String token, String type, String pageNum, final OnHttpCallBack<AttentionInfo> callBack) {
        //网络请求
        RetrofitUtils.newInstence(Const.baseUrl)
                .create(APIService.class)
                .getAttentionList(token, type, pageNum)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AttentionInfo>() {
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
                    public void onNext(AttentionInfo attentionInfo) {
                        if (attentionInfo.getStatus_code() == 0) {
                            callBack.onSuccessful(attentionInfo);
                        } else {
                            callBack.onFaild(attentionInfo.getMessage());
                        }
                    }
                });
    }
}
