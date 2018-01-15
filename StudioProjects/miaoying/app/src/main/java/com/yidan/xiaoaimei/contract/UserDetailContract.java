package com.yidan.xiaoaimei.contract;

import com.netease.nim.uikit.api.model.gift.GiftListInfo;
import com.yidan.xiaoaimei.http.base.OnHttpCallBack;
import com.yidan.xiaoaimei.model.CommonEmptyInfo;
import com.yidan.xiaoaimei.model.detail.UserDetailInfo;


/**
 * 详情
 * Created by jaydenma on 2017/7/17.
 */

public class UserDetailContract {
    /**
     * V视图层
     */
    public interface IUserDetailView {

        void showProgress();//可以显示进度条

        void hideProgress();//可以隐藏进度条

        void showInfo(String info);//提示用户,提升友好交互

        void showError(String msg);//发生错误就显示错误信息

        void showUserDetail(UserDetailInfo info);

        String getToken();

        String getFocusType();  //1关注，0取消关注

        String getUid();

        String getFocusUid();

        void showGifts(GiftListInfo info);
    }

    /**
     * P视图与逻辑处理的连接层
     */
    public interface IUserDetailPresenter {
        void getUserDetail();

        void focus();

        void getGifts();
    }

    /**
     * 逻辑处理层
     */
    public interface IUserDetailModel {
        void getUserDetail(String token, String uid, OnHttpCallBack<UserDetailInfo> callBack);

        void focus(String token, String type, String uid, OnHttpCallBack<CommonEmptyInfo> callBack);

        void getGifts(OnHttpCallBack<GiftListInfo> callBack);
    }


}
