package com.yidan.xiaoaimei.presenter;

import com.yidan.xiaoaimei.contract.MineContract;
import com.yidan.xiaoaimei.http.base.OnHttpCallBack;
import com.yidan.xiaoaimei.model.mine.MineModel;
import com.yidan.xiaoaimei.model.mine.PersonInfo;


/**
 * 我的presenter
 * Created by jaydenma on 2017/7/17.
 */

public class MinePresenter implements MineContract.IMinePresenter {

    private MineContract.IMineView mMineView;
    private MineContract.IMineModel mMineModel;


    public MinePresenter(MineContract.IMineView mMineView) {
        this.mMineView = mMineView;
        mMineModel = new MineModel();
    }

    @Override
    public void userShow() {
        mMineView.showProgress();
        mMineModel.userShow(mMineView.getToken(), new OnHttpCallBack<PersonInfo>() {
            @Override
            public void onSuccessful(PersonInfo personInfo) {
                mMineView.hideProgress();
                mMineView.userShow(personInfo);
            }

            @Override
            public void onFaild(String errorMsg) {
                mMineView.hideProgress();
                mMineView.showError(errorMsg);
            }
        });
    }

}
