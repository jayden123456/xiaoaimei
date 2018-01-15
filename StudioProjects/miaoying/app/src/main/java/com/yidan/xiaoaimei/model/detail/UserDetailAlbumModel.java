package com.yidan.xiaoaimei.model.detail;

import com.socks.library.KLog;
import com.yidan.xiaoaimei.Const;
import com.yidan.xiaoaimei.contract.UserDetailAlbumContract;
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
 * 详情相册model
 * Created by jaydenma on 2017/7/27.
 */

public class UserDetailAlbumModel implements UserDetailAlbumContract.IUserDetailAlbumModel {

    @Override
    public void getAlbum(String token, String userId, String type, final OnHttpCallBack<AlbumShowInfo> callBack) {
        //网络请求
        RetrofitUtils.newInstence(Const.baseUrl)
                .create(APIService.class)
                .getDetailAlbums(token, userId, type)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AlbumShowInfo>() {
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
                    public void onNext(AlbumShowInfo albumShowInfo) {
                        if (albumShowInfo.getStatus_code() == 0) {
                            callBack.onSuccessful(albumShowInfo);
                        } else {
                            callBack.onFaild(albumShowInfo.getMessage());
                        }
                    }
                });
    }
}
