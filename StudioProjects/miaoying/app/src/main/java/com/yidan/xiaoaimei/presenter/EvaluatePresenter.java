package com.yidan.xiaoaimei.presenter;

import com.yidan.xiaoaimei.contract.EvaluateContract;
import com.yidan.xiaoaimei.http.base.OnHttpCallBack;
import com.yidan.xiaoaimei.model.CommonEmptyInfo;
import com.yidan.xiaoaimei.model.detail.EvaluateInfo;
import com.yidan.xiaoaimei.model.detail.EvaluateModel;


/**
 * 评价presenter
 * Created by jaydenma on 2017/7/17.
 */

public class EvaluatePresenter implements EvaluateContract.IEvaluatePresenter {

    private EvaluateContract.IEvaluateView mEvaluateView;
    private EvaluateContract.IEvaluateModel mEvaluateModel;


    public EvaluatePresenter(EvaluateContract.IEvaluateView mEvaluateView) {
        this.mEvaluateView = mEvaluateView;
        mEvaluateModel = new EvaluateModel();
    }


    @Override
    public void getEvaluate() {
        mEvaluateView.showProgress();
        mEvaluateModel.getEvaluate(new OnHttpCallBack<EvaluateInfo>() {

            @Override
            public void onSuccessful(EvaluateInfo evaluateInfo) {
                mEvaluateView.hideProgress();
                mEvaluateView.showEvaluate(evaluateInfo);
            }

            @Override
            public void onFaild(String errorMsg) {
                mEvaluateView.hideProgress();
                mEvaluateView.showError(errorMsg);
            }
        });
    }

    @Override
    public void evaluate() {
        mEvaluateView.showProgress();
        mEvaluateModel.evaluate(mEvaluateView.getToken(), mEvaluateView.getEvaId(), mEvaluateView.getUid(), new OnHttpCallBack<CommonEmptyInfo>() {
            @Override
            public void onSuccessful(CommonEmptyInfo commonEmptyInfo) {
                mEvaluateView.hideProgress();
                mEvaluateView.showInfo("提交成功");
            }

            @Override
            public void onFaild(String errorMsg) {
                mEvaluateView.hideProgress();
                mEvaluateView.showError(errorMsg);
            }
        });
    }

}
