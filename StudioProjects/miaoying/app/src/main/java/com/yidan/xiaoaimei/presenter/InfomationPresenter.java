package com.yidan.xiaoaimei.presenter;

import com.yidan.xiaoaimei.contract.InfomationContract;
import com.yidan.xiaoaimei.http.base.OnHttpCallBack;
import com.yidan.xiaoaimei.model.mine.InfomationModel;
import com.yidan.xiaoaimei.model.mine.PersonInfo;
import com.yidan.xiaoaimei.model.mine.UpdatePersonInfo;
import com.yidan.xiaoaimei.model.user.CityInfo;


/**
 * 基本信息presenter
 * Created by jaydenma on 2017/7/17.
 */

public class InfomationPresenter implements InfomationContract.IInfomationPresenter {

    private InfomationContract.IInfomationView mInfomationView;
    private InfomationContract.IInfomationModel mInfomationModel;


    public InfomationPresenter(InfomationContract.IInfomationView mInfomationView) {
        this.mInfomationView = mInfomationView;
        mInfomationModel = new InfomationModel();
    }

    @Override
    public void getInfo() {
        mInfomationView.showProgress();
        mInfomationModel.getInfo(mInfomationView.getToken(), new OnHttpCallBack<PersonInfo>() {
            @Override
            public void onSuccessful(PersonInfo personInfo) {
                mInfomationView.hideProgress();
                mInfomationView.showData(personInfo);
            }

            @Override
            public void onFaild(String errorMsg) {
                mInfomationView.hideProgress();
                mInfomationView.showError(errorMsg);
            }
        });
    }

    @Override
    public void setPersonal() {
        mInfomationView.showProgress();
        mInfomationModel.setPersonal(mInfomationView.getToken(), mInfomationView.getData(), new OnHttpCallBack<UpdatePersonInfo>() {
            @Override
            public void onSuccessful(UpdatePersonInfo updatePersonInfo) {
                mInfomationView.hideProgress();
                mInfomationView.back();
            }

            @Override
            public void onFaild(String errorMsg) {
                mInfomationView.hideProgress();
                mInfomationView.showError(errorMsg);
            }
        });
    }

    @Override
    public void getCity() {
        mInfomationModel.getCity(new OnHttpCallBack<CityInfo>() {
            @Override
            public void onSuccessful(CityInfo cityInfo) {
                mInfomationView.showCity(cityInfo);
            }

            @Override
            public void onFaild(String errorMsg) {
                mInfomationView.showError(errorMsg);
            }
        });
    }
}
