package com.yidan.xiaoaimei.contract;

import com.yidan.xiaoaimei.http.base.OnHttpCallBack;
import com.yidan.xiaoaimei.model.mine.AlbumAndVideoInfo;


/**
 * 相册和视频
 * Created by jaydenma on 2017/7/17.
 */

public class AlbumAndVideoContract {
    /**
     * V视图层
     */
    public interface IAlbumAndVideoView {

        void showProgress();//可以显示进度条

        void hideProgress();//可以隐藏进度条

        void showInfo(String info);//提示用户,提升友好交互

        void showError(String msg);//发生错误就显示错误信息

        String getToken(); //获取登录token

        void showData(AlbumAndVideoInfo info);

    }

    /**
     * P视图与逻辑处理的连接层
     */
    public interface IAlbumAndVideoPresenter {
        void getAlbumAndVideo();
    }

    /**
     * 逻辑处理层
     */
    public interface IAlbumAndVideoModel {
        void getAlbumAndVideo(String token, OnHttpCallBack<AlbumAndVideoInfo> callBack);
    }


}
