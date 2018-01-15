package com.yidan.xiaoaimei.presenter;

import com.yidan.xiaoaimei.contract.VisitorContract;
import com.yidan.xiaoaimei.http.base.OnHttpCallBack;
import com.yidan.xiaoaimei.model.mine.VisitorInfo;
import com.yidan.xiaoaimei.model.mine.VisitorModel;


/**
 * 访客presenter
 * Created by jaydenma on 2017/7/17.
 */

public class VisitorPresenter implements VisitorContract.IVisitorPresenter {

    private VisitorContract.IVisitorView mVisitorView;
    private VisitorContract.IVisitorModel mVisitorModel;


    public VisitorPresenter(VisitorContract.IVisitorView mVisitorView) {
        this.mVisitorView = mVisitorView;
        mVisitorModel = new VisitorModel();
    }


    @Override
    public void getVisitors() {
        mVisitorView.showProgress();
        mVisitorModel.getVisitors(mVisitorView.getToken(), mVisitorView.getPageNum() + "", new OnHttpCallBack<VisitorInfo>() {
            @Override
            public void onSuccessful(VisitorInfo visitorInfo) {
                mVisitorView.showVisitor(visitorInfo);
                mVisitorView.hideProgress();
            }

            @Override
            public void onFaild(String errorMsg) {
                mVisitorView.hideProgress();
                mVisitorView.showError(errorMsg);
            }
        });
    }
}
