package com.yidan.xiaoaimei.presenter;

import com.yidan.xiaoaimei.contract.MomentPublishContract;
import com.yidan.xiaoaimei.http.base.OnHttpCallBack;
import com.yidan.xiaoaimei.model.CommonEmptyInfo;
import com.yidan.xiaoaimei.model.find.MomentPublishModel;
import com.yidan.xiaoaimei.model.find.MomentDetailInfo;
import com.yidan.xiaoaimei.model.find.PriceOptionInfo;
import com.yidan.xiaoaimei.model.find.TopicInfo;


/**
 * 发布动态presenter
 * Created by jaydenma on 2017/7/17.
 */

public class MomentPublishPresenter implements MomentPublishContract.IMomentPublishPresenter {

    private MomentPublishContract.IMomentPublishView mMomentPublishView;
    private MomentPublishContract.IMomentPublishModel mMomentPublishModel;


    public MomentPublishPresenter(MomentPublishContract.IMomentPublishView mMomentPublishView) {
        this.mMomentPublishView = mMomentPublishView;
        mMomentPublishModel = new MomentPublishModel();
    }

    @Override
    public void publishMoment() {
        mMomentPublishView.showProgress();
        mMomentPublishModel.publishMoment(mMomentPublishView.getToken(), mMomentPublishView.getType(), mMomentPublishView.getTagId(), mMomentPublishView.getOptionId(), mMomentPublishView.getData(), new OnHttpCallBack<CommonEmptyInfo>() {
            @Override
            public void onSuccessful(CommonEmptyInfo commonEmptyInfo) {
                mMomentPublishView.hideProgress();
                mMomentPublishView.showInfo("发布成功");
            }

            @Override
            public void onFaild(String errorMsg) {
                mMomentPublishView.hideProgress();
                mMomentPublishView.showError(errorMsg);
            }
        });
    }

    @Override
    public void getTopic() {
        mMomentPublishView.showProgress();
        mMomentPublishModel.getTopic(new OnHttpCallBack<TopicInfo>() {
            @Override
            public void onSuccessful(TopicInfo topicInfo) {
                mMomentPublishView.hideProgress();
                mMomentPublishView.showTopics(topicInfo);
            }

            @Override
            public void onFaild(String errorMsg) {
                mMomentPublishView.hideProgress();
                mMomentPublishView.showError(errorMsg);
            }
        });
    }

    @Override
    public void getPriceOptions() {
        mMomentPublishModel.getPriceOptions(new OnHttpCallBack<PriceOptionInfo>() {
            @Override
            public void onSuccessful(PriceOptionInfo info) {
                mMomentPublishView.showPrices(info);
            }

            @Override
            public void onFaild(String errorMsg) {
                mMomentPublishView.showError(errorMsg);
            }
        });
    }
}
