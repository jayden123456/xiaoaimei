package com.yidan.xiaoaimei.presenter;

import com.yidan.xiaoaimei.contract.MyAccountContract;
import com.yidan.xiaoaimei.http.base.OnHttpCallBack;
import com.yidan.xiaoaimei.model.CommonInfo;
import com.yidan.xiaoaimei.model.mine.BalanceDetailInfo;
import com.yidan.xiaoaimei.model.mine.BalanceInfo;
import com.yidan.xiaoaimei.model.mine.MyAccountModel;
import com.yidan.xiaoaimei.model.mine.PayOptionsInfo;

/**
 * 我的presenter
 * Created by jaydenma on 2017/7/17.
 */

public class MyAccountPresenter implements MyAccountContract.IMyAccountPresenter {

    private MyAccountContract.IMyAccountView mMyAccountView;
    private MyAccountContract.IMyAccountModel mMyAccountModel;


    public MyAccountPresenter(MyAccountContract.IMyAccountView mMyAccountView) {
        this.mMyAccountView = mMyAccountView;
        mMyAccountModel = new MyAccountModel();
    }


    /**
     * 充值
     */
    @Override
    public void recharge() {
        mMyAccountView.showProgress();
        mMyAccountModel.recharge(mMyAccountView.getToken(), mMyAccountView.getPayType(), mMyAccountView.getPayOptionId(), new OnHttpCallBack<CommonInfo>() {
            @Override
            public void onSuccessful(CommonInfo commonInfo) {
                mMyAccountView.hideProgress();
                mMyAccountView.toPay(commonInfo.getData());
            }

            @Override
            public void onFaild(String errorMsg) {
                mMyAccountView.hideProgress();
                mMyAccountView.showErrorMsg(errorMsg);
            }
        });
    }

    /**
     * 获取充值选项
     */
    @Override
    public void getPayOptions() {
        mMyAccountView.showProgress();
        mMyAccountModel.getPayOptions(new OnHttpCallBack<PayOptionsInfo>() {
            @Override
            public void onSuccessful(PayOptionsInfo payOptionsInfo) {
                mMyAccountView.hideProgress();
                mMyAccountView.showOptions(payOptionsInfo);
            }

            @Override
            public void onFaild(String errorMsg) {
                mMyAccountView.hideProgress();
                mMyAccountView.showErrorMsg(errorMsg);
            }
        });
    }

    @Override
    public void getBalance() {
//        mMyAccountView.showProgress();
        mMyAccountModel.getBalance(mMyAccountView.getToken(), new OnHttpCallBack<BalanceInfo>() {
            @Override
            public void onSuccessful(BalanceInfo info) {
//                mMyAccountView.hideProgress();
                mMyAccountView.showBalance(info);
            }

            @Override
            public void onFaild(String errorMsg) {
//                mMyAccountView.hideProgress();
                mMyAccountView.showErrorMsg(errorMsg);
            }
        });
    }

    @Override
    public void getBalanceDetail() {
        mMyAccountView.showProgress();
        mMyAccountModel.getBalanceDetail(mMyAccountView.getToken(), new OnHttpCallBack<BalanceDetailInfo>() {
            @Override
            public void onSuccessful(BalanceDetailInfo info) {
                mMyAccountView.hideProgress();
                mMyAccountView.showBalanceDetail(info);
            }

            @Override
            public void onFaild(String errorMsg) {
                mMyAccountView.hideProgress();
                mMyAccountView.showErrorMsg(errorMsg);
            }
        });
    }
}
