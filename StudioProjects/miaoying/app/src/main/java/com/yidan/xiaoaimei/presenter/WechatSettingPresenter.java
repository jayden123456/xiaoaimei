package com.yidan.xiaoaimei.presenter;

import com.yidan.xiaoaimei.contract.WechatSettingContract;
import com.yidan.xiaoaimei.http.base.OnHttpCallBack;
import com.yidan.xiaoaimei.model.CommonEmptyInfo;
import com.yidan.xiaoaimei.model.mine.WechatPayInfo;
import com.yidan.xiaoaimei.model.mine.WechatSettingModel;

/**
 * Created by jaydenma on 2017/7/21.
 */

public class WechatSettingPresenter implements WechatSettingContract.IWechatSettingPresenter {

    private WechatSettingContract.IWechatSettingView mWechatSettingView;
    private WechatSettingContract.IWechatSettingModel mWechatSettinglModel;

    public WechatSettingPresenter(WechatSettingContract.IWechatSettingView mWechatSettingView) {
        this.mWechatSettingView = mWechatSettingView;
        mWechatSettinglModel = new WechatSettingModel();
    }


    @Override
    public void getWechatPay() {
        mWechatSettingView.showProgress();
        mWechatSettinglModel.getWechatPay(mWechatSettingView.getToken(), new OnHttpCallBack<WechatPayInfo>() {
            @Override
            public void onSuccessful(WechatPayInfo info) {
                mWechatSettingView.hideProgress();
                mWechatSettingView.showWechatPay(info);
            }

            @Override
            public void onFaild(String errorMsg) {
                mWechatSettingView.hideProgress();
                mWechatSettingView.showErrorMsg(errorMsg);
            }
        });
    }

    @Override
    public void setWechat() {
        mWechatSettingView.showProgress();
        mWechatSettinglModel.setWechat(mWechatSettingView.getToken(), mWechatSettingView.getOptionId(), mWechatSettingView.getWechatNum(), new OnHttpCallBack<CommonEmptyInfo>() {
            @Override
            public void onSuccessful(CommonEmptyInfo commonEmptyInfo) {
                mWechatSettingView.hideProgress();
                mWechatSettingView.sellSuc(commonEmptyInfo.getStatus_code());
            }

            @Override
            public void onFaild(String errorMsg) {
                mWechatSettingView.hideProgress();
                mWechatSettingView.showErrorMsg(errorMsg);
            }
        });
    }
}
