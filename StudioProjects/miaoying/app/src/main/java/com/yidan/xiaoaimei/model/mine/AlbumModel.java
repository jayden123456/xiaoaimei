package com.yidan.xiaoaimei.model.mine;

import com.socks.library.KLog;
import com.yidan.xiaoaimei.Const;
import com.yidan.xiaoaimei.contract.AlbumContract;
import com.yidan.xiaoaimei.http.base.APIService;
import com.yidan.xiaoaimei.http.base.OnHttpCallBack;
import com.yidan.xiaoaimei.http.utils.RetrofitUtils;
import com.yidan.xiaoaimei.model.CommonEmptyInfo;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 相册视频model
 * Created by jaydenma on 2017/8/1.
 */

public class AlbumModel implements AlbumContract.IAlbumModel {

    /**
     * 获取相册或视频
     *
     * @param token
     * @param albumType
     * @param callBack
     */
    @Override
    public void getAlbums(String token, String albumType, final OnHttpCallBack<AlbumInfo> callBack) {
        //网络请求
        RetrofitUtils.newInstence(Const.baseUrl)
                .create(APIService.class)
                .getAlbum(token, albumType)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AlbumInfo>() {
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
                    public void onNext(AlbumInfo albumInfo) {
                        if (albumInfo.getStatus_code() == 0) {
                            callBack.onSuccessful(albumInfo);
                        } else {
                            callBack.onFaild(albumInfo.getMessage());
                        }
                    }
                });
    }

    /**
     * 添加图片
     *
     * @param token
     * @param img
     * @param callBack
     */
    @Override
    public void addImage(String token, String img, final OnHttpCallBack<CommonEmptyInfo> callBack) {
        //网络请求
        RetrofitUtils.newInstence(Const.baseUrl)
                .create(APIService.class)
                .addImage(token, img)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CommonEmptyInfo>() {
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
                    public void onNext(CommonEmptyInfo commonEmptyInfo) {
                        if (commonEmptyInfo.getStatus_code() == 0) {
                            callBack.onSuccessful(commonEmptyInfo);
                        } else {
                            callBack.onFaild(commonEmptyInfo.getMessage());
                        }
                    }
                });
    }

    /**
     * 添加视频
     *
     * @param token
     * @param videoInfo
     * @param callBack
     */
    @Override
    public void addVedio(String token, String videoInfo, final OnHttpCallBack<CommonEmptyInfo> callBack) {

        //网络请求
        RetrofitUtils.newInstence(Const.baseUrl)
                .create(APIService.class)
                .addVideo(token, videoInfo)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CommonEmptyInfo>() {
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
                    public void onNext(CommonEmptyInfo commonEmptyInfo) {
                        if (commonEmptyInfo.getStatus_code() == 0) {
                            callBack.onSuccessful(commonEmptyInfo);
                        } else {
                            callBack.onFaild(commonEmptyInfo.getMessage());
                        }
                    }
                });
    }


    /**
     * 删除图片或视频
     *
     * @param token
     * @param imgId
     * @param callBack
     */
    @Override
    public void deleteImg(String token, String imgId, final OnHttpCallBack<CommonEmptyInfo> callBack) {
        //网络请求
        RetrofitUtils.newInstence(Const.baseUrl)
                .create(APIService.class)
                .deleteImage(token, imgId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CommonEmptyInfo>() {
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
                    public void onNext(CommonEmptyInfo commonEmptyInfo) {
                        if (commonEmptyInfo.getStatus_code() == 0) {
                            callBack.onSuccessful(commonEmptyInfo);
                        } else {
                            callBack.onFaild(commonEmptyInfo.getMessage());
                        }
                    }
                });
    }
}
