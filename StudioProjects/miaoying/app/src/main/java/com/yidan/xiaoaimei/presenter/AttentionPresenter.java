package com.yidan.xiaoaimei.presenter;

import com.yidan.xiaoaimei.contract.AttentionContract;
import com.yidan.xiaoaimei.http.base.OnHttpCallBack;
import com.yidan.xiaoaimei.model.mine.AttentionInfo;
import com.yidan.xiaoaimei.model.mine.AttentionModel;


/**
 * 关注，粉丝列表presenter
 * Created by jaydenma on 2017/7/17.
 */

public class AttentionPresenter implements AttentionContract.IAttentionPresenter {

    private AttentionContract.IAttentionView mAttentionView;
    private AttentionContract.IAttentionModel mAttentionModel;


    public AttentionPresenter(AttentionContract.IAttentionView mAttentionView) {
        this.mAttentionView = mAttentionView;
        mAttentionModel = new AttentionModel();
    }

    @Override
    public void getAttention() {
        mAttentionView.showProgress();
        mAttentionModel.getAttention(mAttentionView.getToken(), mAttentionView.getType(), mAttentionView.getPageNum() + "", new OnHttpCallBack<AttentionInfo>() {
            @Override
            public void onSuccessful(AttentionInfo attentionInfo) {
                mAttentionView.hideProgress();
                mAttentionView.showAttention(attentionInfo);
            }

            @Override
            public void onFaild(String errorMsg) {
                mAttentionView.hideProgress();
                mAttentionView.showError(errorMsg);
            }
        });
    }
}
