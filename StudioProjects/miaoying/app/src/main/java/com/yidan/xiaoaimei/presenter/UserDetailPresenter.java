package com.yidan.xiaoaimei.presenter;

import com.netease.nim.uikit.api.model.gift.GiftListInfo;
import com.yidan.xiaoaimei.contract.UserDetailContract;
import com.yidan.xiaoaimei.http.base.OnHttpCallBack;
import com.yidan.xiaoaimei.model.CommonEmptyInfo;
import com.yidan.xiaoaimei.model.detail.UserDetailInfo;
import com.yidan.xiaoaimei.model.detail.UserDetailModel;


/**
 * 详情presenter
 * Created by jaydenma on 2017/7/17.
 */

public class UserDetailPresenter implements UserDetailContract.IUserDetailPresenter {

    private UserDetailContract.IUserDetailView mUserDetailView;
    private UserDetailContract.IUserDetailModel mUserDetailModel;


    public UserDetailPresenter(UserDetailContract.IUserDetailView mUserDetailView) {
        this.mUserDetailView = mUserDetailView;
        mUserDetailModel = new UserDetailModel();
    }


    @Override
    public void getUserDetail() {
        mUserDetailView.showProgress();
        mUserDetailModel.getUserDetail(mUserDetailView.getToken(), mUserDetailView.getUid(), new OnHttpCallBack<UserDetailInfo>() {

            @Override
            public void onSuccessful(UserDetailInfo userDetailInfo) {
                mUserDetailView.hideProgress();
                mUserDetailView.showUserDetail(userDetailInfo);
            }

            @Override
            public void onFaild(String errorMsg) {
                mUserDetailView.hideProgress();
                mUserDetailView.showError(errorMsg);
            }
        });
    }

    @Override
    public void focus() {
        mUserDetailView.showProgress();
        mUserDetailModel.focus(mUserDetailView.getToken(), mUserDetailView.getFocusType(), mUserDetailView.getFocusUid(), new OnHttpCallBack<CommonEmptyInfo>() {
            @Override
            public void onSuccessful(CommonEmptyInfo commonEmptyInfo) {
                mUserDetailView.hideProgress();
                if (mUserDetailView.getFocusType().equals("1")) {
                    mUserDetailView.showInfo("关注成功");
                } else {
                    mUserDetailView.showInfo("取消关注成功");
                }
            }

            @Override
            public void onFaild(String errorMsg) {
                mUserDetailView.hideProgress();
                mUserDetailView.showError(errorMsg);
            }
        });
    }

    @Override
    public void getGifts() {
        mUserDetailModel.getGifts(new OnHttpCallBack<GiftListInfo>() {
            @Override
            public void onSuccessful(GiftListInfo giftListInfo) {
                mUserDetailView.showGifts(giftListInfo);
            }

            @Override
            public void onFaild(String errorMsg) {
                mUserDetailView.showError(errorMsg);
            }
        });
    }
}
