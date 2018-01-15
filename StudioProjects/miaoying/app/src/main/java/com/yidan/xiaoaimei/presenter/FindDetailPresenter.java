package com.yidan.xiaoaimei.presenter;

import com.yidan.xiaoaimei.contract.FindDetailContract;
import com.yidan.xiaoaimei.http.base.OnHttpCallBack;
import com.yidan.xiaoaimei.model.CommonEmptyInfo;
import com.yidan.xiaoaimei.model.find.FindDetailModel;
import com.yidan.xiaoaimei.model.find.MomentDetailInfo;


/**
 * 发现详情presenter
 * Created by jaydenma on 2017/7/17.
 */

public class FindDetailPresenter implements FindDetailContract.IFindDetailPresenter {

    private FindDetailContract.IFindDetailView mFindDetailView;
    private FindDetailContract.IFindDetailModel mFindDetailModel;


    public FindDetailPresenter(FindDetailContract.IFindDetailView mFindDetailView) {
        this.mFindDetailView = mFindDetailView;
        mFindDetailModel = new FindDetailModel();
    }

    @Override
    public void getFindDetail() {
        mFindDetailView.showProgress();
        mFindDetailModel.getFindDetail(mFindDetailView.getToken(), mFindDetailView.getMomentId(), new OnHttpCallBack<MomentDetailInfo>() {
            @Override
            public void onSuccessful(MomentDetailInfo momentDetailInfo) {
                mFindDetailView.hideProgress();
                mFindDetailView.showMoments(momentDetailInfo);
            }

            @Override
            public void onFaild(String errorMsg) {
                mFindDetailView.hideProgress();
                mFindDetailView.showError(errorMsg);
            }
        });
    }

    @Override
    public void comment() {
        mFindDetailView.showProgress();
        mFindDetailModel.comment(mFindDetailView.getToken(), mFindDetailView.getMomentId(), mFindDetailView.getContent(), new OnHttpCallBack<CommonEmptyInfo>() {
            @Override
            public void onSuccessful(CommonEmptyInfo commonEmptyInfo) {
                mFindDetailView.hideProgress();
                mFindDetailView.showInfo("评论成功");
            }

            @Override
            public void onFaild(String errorMsg) {
                mFindDetailView.hideProgress();
                mFindDetailView.showError(errorMsg);
            }
        });
    }

    @Override
    public void praise() {
        mFindDetailView.showProgress();
        mFindDetailModel.praise(mFindDetailView.getToken(), mFindDetailView.getMomentId(), new OnHttpCallBack<CommonEmptyInfo>() {
            @Override
            public void onSuccessful(CommonEmptyInfo commonEmptyInfo) {
                mFindDetailView.hideProgress();
                mFindDetailView.showInfo("点赞成功");
            }

            @Override
            public void onFaild(String errorMsg) {
                mFindDetailView.hideProgress();
                mFindDetailView.showError(errorMsg);
            }
        });
    }

    @Override
    public void buy() {
        mFindDetailView.showProgress();
        mFindDetailModel.buy(mFindDetailView.getToken(), mFindDetailView.getMomentId(), new OnHttpCallBack<CommonEmptyInfo>() {
            @Override
            public void onSuccessful(CommonEmptyInfo commonEmptyInfo) {
                mFindDetailView.hideProgress();
                mFindDetailView.showInfo("购买成功");
            }

            @Override
            public void onFaild(String errorMsg) {
                mFindDetailView.hideProgress();
                mFindDetailView.showError(errorMsg);
            }
        });
    }
}
