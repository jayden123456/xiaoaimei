package com.yidan.xiaoaimei.contract;

import com.yidan.xiaoaimei.http.base.OnHttpCallBack;
import com.yidan.xiaoaimei.model.CommonEmptyInfo;
import com.yidan.xiaoaimei.model.mine.AlbumInfo;

import java.util.List;

/**
 * Created by jaydenma on 2017/7/17.
 */

public class AlbumContract {
    /**
     * V视图层
     */
    public interface IAlbumView {

        void showProgress();//可以显示进度条

        void hideProgress();//可以隐藏进度条

        void showInfo(String info);//提示用户,提升友好交互

        void showErrorMsg(String msg);//发生错误就显示错误信息

        void showData(List<AlbumInfo.DataBean> data);  //展示相册或视频

        void uploadSuc(); //上传成功

        void deleteSuc(); //删除成功

        String getToken();

        String getAlbumType();  //0相册1视频

        String getDataJsonStr();  //获取图片或视频的json

        String getImageId();   //获取操作的图片或视频id

    }

    /**
     * P视图与逻辑处理的连接层
     */
    public interface IAlbumPresenter {
        void getAlbums();

        void addImage();

        void addVedio();

        void deleteImg();

    }

    /**
     * 逻辑处理层
     */
    public interface IAlbumModel {
        void getAlbums(String token, String albumType, OnHttpCallBack<AlbumInfo> callBack);

        void addImage(String token, String img, OnHttpCallBack<CommonEmptyInfo> callBack);

        void addVedio(String token, String videoInfo, OnHttpCallBack<CommonEmptyInfo> callBack);

        void deleteImg(String token, String imgId, OnHttpCallBack<CommonEmptyInfo> callBack);
    }


}
