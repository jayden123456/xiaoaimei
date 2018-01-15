package com.yidan.xiaoaimei.presenter;

import com.yidan.xiaoaimei.contract.VerifyContract;
import com.yidan.xiaoaimei.http.base.OnHttpCallBack;
import com.yidan.xiaoaimei.model.CommonEmptyInfo;
import com.yidan.xiaoaimei.model.mine.VerifyInfo;
import com.yidan.xiaoaimei.model.mine.VerifyModel;


/**
 * 认证presenter
 * Created by jaydenma on 2017/7/17.
 */

public class VerifyPresenter implements VerifyContract.IVerifyPresenter {

    private VerifyContract.IVerifyView mVerifyView;
    private VerifyContract.IVerifyModel mVerifyModel;


    public VerifyPresenter(VerifyContract.IVerifyView mVerifyView) {
        this.mVerifyView = mVerifyView;
        mVerifyModel = new VerifyModel();
    }


    @Override
    public void getVerify() {
        mVerifyView.showProgress();
        mVerifyModel.getVerify(mVerifyView.getToken(), mVerifyView.getType(), new OnHttpCallBack<VerifyInfo>() {

            @Override
            public void onSuccessful(VerifyInfo verifyInfo) {
                mVerifyView.hideProgress();
                mVerifyView.showVerify(verifyInfo);
            }

            @Override
            public void onFaild(String errorMsg) {
                mVerifyView.hideProgress();
                mVerifyView.showError(errorMsg);
            }
        });
    }

    @Override
    public void submitVoice() {
//        mVerifyView.showProgress();
        mVerifyModel.submitVoice(mVerifyView.getToken(), mVerifyView.getUrl(), mVerifyView.getTime(), new OnHttpCallBack<CommonEmptyInfo>() {
            @Override
            public void onSuccessful(CommonEmptyInfo commonEmptyInfo) {
                mVerifyView.hideProgress();
                mVerifyView.showInfo("上传成功");
            }

            @Override
            public void onFaild(String errorMsg) {
                mVerifyView.hideProgress();
                mVerifyView.showError(errorMsg);
            }
        });
    }

    @Override
    public void submitVideo() {
//        mVerifyView.showProgress();
        mVerifyModel.submitVideo(mVerifyView.getToken(), mVerifyView.getUrl(), mVerifyView.getTime(), mVerifyView.getFirstImg(), new OnHttpCallBack<CommonEmptyInfo>() {
            @Override
            public void onSuccessful(CommonEmptyInfo commonEmptyInfo) {
                mVerifyView.hideProgress();
                mVerifyView.showInfo("上传成功");
            }

            @Override
            public void onFaild(String errorMsg) {
                mVerifyView.hideProgress();
                mVerifyView.showError(errorMsg);
            }
        });
    }
}
