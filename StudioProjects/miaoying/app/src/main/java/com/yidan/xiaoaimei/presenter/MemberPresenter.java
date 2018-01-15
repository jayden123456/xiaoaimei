package com.yidan.xiaoaimei.presenter;


import com.yidan.xiaoaimei.contract.MemberContract;
import com.yidan.xiaoaimei.http.base.OnHttpCallBack;
import com.yidan.xiaoaimei.model.CommonInfo;
import com.yidan.xiaoaimei.model.mine.MemberModel;
import com.yidan.xiaoaimei.model.mine.MemberOptionsInfo;

/**
 * 我的presenter
 * Created by jaydenma on 2017/7/17.
 */

public class MemberPresenter implements MemberContract.IMemberPresenter {

    private MemberContract.IMemberView mMemberView;
    private MemberContract.IMemberModel mMemberModel;


    public MemberPresenter(MemberContract.IMemberView mMemberView) {
        this.mMemberView = mMemberView;
        mMemberModel = new MemberModel();
    }

    @Override
    public void getMemberOptions() {
        mMemberView.showProgress();
        mMemberModel.getMemberOptions(mMemberView.getToken(), new OnHttpCallBack<MemberOptionsInfo>() {
            @Override
            public void onSuccessful(MemberOptionsInfo info) {
                mMemberView.hideProgress();
                mMemberView.showOptions(info);
            }

            @Override
            public void onFaild(String errorMsg) {
                mMemberView.hideProgress();
                mMemberView.showErrorMsg(errorMsg);
            }
        });
    }

    @Override
    public void recharge() {
        mMemberView.showProgress();
        mMemberModel.recharge(mMemberView.getToken(), mMemberView.getPayType(), mMemberView.getPayOptionId(), new OnHttpCallBack<CommonInfo>() {
            @Override
            public void onSuccessful(CommonInfo commonInfo) {
                mMemberView.hideProgress();
                mMemberView.toPay(commonInfo.getData());
            }

            @Override
            public void onFaild(String errorMsg) {
                mMemberView.hideProgress();
                mMemberView.showErrorMsg(errorMsg);
            }
        });
    }
}
