package com.yidan.xiaoaimei.presenter;

import com.yidan.xiaoaimei.contract.FindContract;
import com.yidan.xiaoaimei.http.base.OnHttpCallBack;
import com.yidan.xiaoaimei.model.CommonEmptyInfo;
import com.yidan.xiaoaimei.model.find.FindModel;
import com.yidan.xiaoaimei.model.find.MomentListInfo;


/**
 * 发现presenter
 * Created by jaydenma on 2017/7/17.
 */

public class FindPresenter implements FindContract.IFindPresenter {

    private FindContract.IFindView mFindView;
    private FindContract.IFindModel mFindModel;


    public FindPresenter(FindContract.IFindView mFindView) {
        this.mFindView = mFindView;
        mFindModel = new FindModel();
    }


    @Override
    public void getFind() {
        mFindView.showProgress();
        mFindModel.getFind(mFindView.getToken(), mFindView.getType(), mFindView.getPageNum() + "", new OnHttpCallBack<MomentListInfo>() {
            @Override
            public void onSuccessful(MomentListInfo momentListInfo) {
                mFindView.hideProgress();
                mFindView.showMoments(momentListInfo);
            }

            @Override
            public void onFaild(String errorMsg) {
                mFindView.hideProgress();
                mFindView.showError(errorMsg);
            }
        });
    }

    @Override
    public void praise() {
        mFindView.showProgress();
        mFindModel.praise(mFindView.getToken(), mFindView.getMomentId(), new OnHttpCallBack<CommonEmptyInfo>() {
            @Override
            public void onSuccessful(CommonEmptyInfo commonEmptyInfo) {
                mFindView.hideProgress();
                mFindView.showInfo("点赞成功！");
            }

            @Override
            public void onFaild(String errorMsg) {
                mFindView.hideProgress();
                mFindView.showError(errorMsg);
            }
        });
    }

    @Override
    public void buy() {
        mFindView.showProgress();
        mFindModel.buy(mFindView.getToken(), mFindView.getMomentId(), new OnHttpCallBack<CommonEmptyInfo>() {
            @Override
            public void onSuccessful(CommonEmptyInfo commonEmptyInfo) {
                mFindView.hideProgress();
                mFindView.showInfo("购买成功！");
            }

            @Override
            public void onFaild(String errorMsg) {
                mFindView.hideProgress();
                mFindView.showError(errorMsg);
            }
        });
    }


}
