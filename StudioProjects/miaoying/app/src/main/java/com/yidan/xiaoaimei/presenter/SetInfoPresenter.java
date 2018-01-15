package com.yidan.xiaoaimei.presenter;

import com.yidan.xiaoaimei.contract.SetInfoContract;
import com.yidan.xiaoaimei.http.base.OnHttpCallBack;
import com.yidan.xiaoaimei.model.user.CityInfo;
import com.yidan.xiaoaimei.model.user.SetInfoModel;
import com.yidan.xiaoaimei.model.user.SetPersonInfo;


/**
 * 设置资料presenter
 * Created by jaydenma on 2017/7/17.
 */

public class SetInfoPresenter implements SetInfoContract.ISetInfoPresenter {

    private SetInfoContract.ISetInfoView mSetInfoView;
    private SetInfoContract.ISetInfoModel mSetInfoModel;


    public SetInfoPresenter(SetInfoContract.ISetInfoView mSetInfoView) {
        this.mSetInfoView = mSetInfoView;
        mSetInfoModel = new SetInfoModel();
    }


    @Override
    public void getCity() {
        mSetInfoView.showProgress();
        mSetInfoModel.getCity(new OnHttpCallBack<CityInfo>() {
            @Override
            public void onSuccessful(CityInfo cityInfo) {
                mSetInfoView.hideProgress();
                mSetInfoView.showCity(cityInfo);
            }

            @Override
            public void onFaild(String errorMsg) {
                mSetInfoView.hideProgress();
                mSetInfoView.showError(errorMsg);
            }
        });
    }

    @Override
    public void setPersonal() {
        mSetInfoView.showProgress();
        mSetInfoModel.setPersonal(mSetInfoView.getToken(), mSetInfoView.getUserInfo(), new OnHttpCallBack<SetPersonInfo>() {
            @Override
            public void onSuccessful(SetPersonInfo setPersonInfo) {
                mSetInfoView.hideProgress();
//                mSetInfoView.showInfo("上传成功");
                mSetInfoView.toNewPage();
            }

            @Override
            public void onFaild(String errorMsg) {
                mSetInfoView.hideProgress();
                mSetInfoView.showError(errorMsg);
            }
        });
    }
}
