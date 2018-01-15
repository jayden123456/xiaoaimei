package com.yidan.xiaoaimei.presenter;

import com.yidan.xiaoaimei.contract.GiftsContract;
import com.yidan.xiaoaimei.http.base.OnHttpCallBack;
import com.yidan.xiaoaimei.model.mine.GiftListInfo;
import com.yidan.xiaoaimei.model.mine.GiftsModel;


/**
 * 礼物列表presenter
 * Created by jaydenma on 2017/7/17.
 */

public class GiftsPresenter implements GiftsContract.IGiftsPresenter {

    private GiftsContract.IGiftsView mGiftsView;
    private GiftsContract.IGiftsModel mGiftsModel;


    public GiftsPresenter(GiftsContract.IGiftsView mGiftsView) {
        this.mGiftsView = mGiftsView;
        mGiftsModel = new GiftsModel();
    }


    @Override
    public void getGifts() {
        mGiftsView.showProgress();
        mGiftsModel.getGifts(mGiftsView.getUid(), new OnHttpCallBack<GiftListInfo>() {
            @Override
            public void onSuccessful(GiftListInfo giftInfo) {
                mGiftsView.showGifts(giftInfo);
                mGiftsView.hideProgress();
            }

            @Override
            public void onFaild(String errorMsg) {
                mGiftsView.hideProgress();
                mGiftsView.showError(errorMsg);
            }
        });
    }
}
