package com.yidan.xiaoaimei.presenter;

import com.yidan.xiaoaimei.contract.IncomeContract;
import com.yidan.xiaoaimei.http.base.OnHttpCallBack;
import com.yidan.xiaoaimei.model.CommonEmptyInfo;
import com.yidan.xiaoaimei.model.mine.IncomeDetailInfo;
import com.yidan.xiaoaimei.model.mine.IncomeModel;
import com.yidan.xiaoaimei.model.mine.WithdrawInfo;
import com.yidan.xiaoaimei.model.user.VerifyCodeInfo;

/**
 * 我的收益income
 * Created by jaydenma on 2017/7/21.
 */

public class IncomePresenter implements IncomeContract.IIncomePresenter {

    private IncomeContract.IIncomeView mIncomeView;
    private IncomeContract.IIncomeModel mIncomelModel;

    public IncomePresenter(IncomeContract.IIncomeView mIncomeView) {
        this.mIncomeView = mIncomeView;
        mIncomelModel = new IncomeModel();
    }


    @Override
    public void getWithdrawInfo() {
        mIncomeView.showProgress();
        mIncomelModel.getWithdrawInfo(mIncomeView.getToken(), new OnHttpCallBack<WithdrawInfo>() {
            @Override
            public void onSuccessful(WithdrawInfo info) {
                mIncomeView.hideProgress();
                mIncomeView.showData(info);
            }

            @Override
            public void onFaild(String errorMsg) {
                mIncomeView.hideProgress();
                mIncomeView.showErrorMsg(errorMsg);
            }
        });
    }

    @Override
    public void authWithdraw() {
        mIncomeView.showProgress();
        mIncomelModel.authWithdraw(mIncomeView.getToken(), mIncomeView.getWithdrawInfo(), new OnHttpCallBack<CommonEmptyInfo>() {
            @Override
            public void onSuccessful(CommonEmptyInfo commonEmptyInfo) {
                mIncomeView.hideProgress();
                mIncomeView.showInfo("提交成功");
            }

            @Override
            public void onFaild(String errorMsg) {
                mIncomeView.hideProgress();
                mIncomeView.showErrorMsg(errorMsg);
            }
        });
    }

    @Override
    public void withdraw() {
        mIncomeView.showProgress();
        mIncomelModel.withdraw(mIncomeView.getToken(), new OnHttpCallBack<CommonEmptyInfo>() {
            @Override
            public void onSuccessful(CommonEmptyInfo commonInfo) {
                mIncomeView.hideProgress();
                mIncomeView.withdrawSuc();
            }

            @Override
            public void onFaild(String errorMsg) {
                mIncomeView.hideProgress();
                mIncomeView.showErrorMsg(errorMsg);
            }
        });
    }

    @Override
    public void getIncomeDetail() {
        mIncomeView.showProgress();
        mIncomelModel.getIncomeDetail(mIncomeView.getToken(), new OnHttpCallBack<IncomeDetailInfo>() {
            @Override
            public void onSuccessful(IncomeDetailInfo incomeDetailInfo) {
                mIncomeView.hideProgress();
                mIncomeView.showIncomeDetail(incomeDetailInfo);
            }

            @Override
            public void onFaild(String errorMsg) {
                mIncomeView.hideProgress();
                mIncomeView.showErrorMsg(errorMsg);
            }
        });
    }


    @Override
    public void getVerifyCode() {
        mIncomeView.showProgress();
        mIncomelModel.getVerifyCode(mIncomeView.getPhone(), new OnHttpCallBack<VerifyCodeInfo>() {
            @Override
            public void onSuccessful(VerifyCodeInfo verifyCodeInfo) {
                mIncomeView.hideProgress();
                mIncomeView.timeStart("获取验证码成功");
            }

            @Override
            public void onFaild(String errorMsg) {
                mIncomeView.hideProgress();
                mIncomeView.showErrorMsg(errorMsg);
            }
        });
    }

}
