package com.yidan.xiaoaimei.contract;


import com.yidan.xiaoaimei.http.base.OnHttpCallBack;
import com.yidan.xiaoaimei.model.detail.AlbumShowInfo;

/**
 * Created by jaydenma on 2017/7/17.
 */

public class UserDetailAlbumContract {
    /**
     * V视图层
     */
    public interface IUserDetailAlbumView {

        void showProgress();//可以显示进度条

        void hideProgress();//可以隐藏进度条

        void showInfo(String info);//提示用户,提升友好交互

        void showErrorMsg(String msg);//发生错误就显示错误信息

        String getUserId();

        String getToken();

        String getType();  //0相册1视频

        void showAlbum(AlbumShowInfo info);
    }

    /**
     * P视图与逻辑处理的连接层
     */
    public interface IUserDetailAlbumPresenter {
        void getAlbum();
    }

    /**
     * 逻辑处理层
     */
    public interface IUserDetailAlbumModel {
        void getAlbum(String token, String userId, String type, OnHttpCallBack<AlbumShowInfo> callBack);
    }


}
