package com.yidan.xiaoaimei.presenter;

import com.yidan.xiaoaimei.contract.HomeContract;
import com.yidan.xiaoaimei.http.base.OnHttpCallBack;
import com.yidan.xiaoaimei.model.CommonEmptyInfo;
import com.yidan.xiaoaimei.model.home.HomeCommonInfo;
import com.yidan.xiaoaimei.model.home.HomeModel;


/**
 * 首页presenter
 * Created by jaydenma on 2017/7/17.
 */

public class HomePresenter implements HomeContract.IHomePresenter {

    private HomeContract.IHomeView mHomeView;
    private HomeContract.IHomeModel mHomeModel;


    public HomePresenter(HomeContract.IHomeView mHomeView) {
        this.mHomeView = mHomeView;
        mHomeModel = new HomeModel();
    }


    @Override
    public void getHome() {
        mHomeView.showProgress();
        mHomeModel.getHome(mHomeView.getToken(), mHomeView.getType(), mHomeView.getPageNum() + "", new OnHttpCallBack<HomeCommonInfo>() {

            @Override
            public void onSuccessful(HomeCommonInfo homeCommonInfo) {
                mHomeView.hideProgress();
                mHomeView.showHomeData(homeCommonInfo);
            }

            @Override
            public void onFaild(String errorMsg) {
                mHomeView.hideProgress();
                mHomeView.showError(errorMsg);
            }
        });
    }

    @Override
    public void focus() {
        mHomeView.showProgress();
        mHomeModel.focus(mHomeView.getToken(), mHomeView.getFocusType(), mHomeView.getUid(), new OnHttpCallBack<CommonEmptyInfo>() {
            @Override
            public void onSuccessful(CommonEmptyInfo commonEmptyInfo) {
                mHomeView.hideProgress();
                if (mHomeView.getFocusType().equals("1")) {
                    mHomeView.showInfo("关注成功");
                } else {
                    mHomeView.showInfo("取消关注成功");
                }
            }

            @Override
            public void onFaild(String errorMsg) {
                mHomeView.hideProgress();
                mHomeView.showError(errorMsg);
            }
        });
    }
}
