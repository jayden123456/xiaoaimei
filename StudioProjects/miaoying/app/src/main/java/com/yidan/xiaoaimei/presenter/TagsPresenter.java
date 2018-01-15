package com.yidan.xiaoaimei.presenter;

import com.yidan.xiaoaimei.contract.TagsContract;
import com.yidan.xiaoaimei.http.base.OnHttpCallBack;
import com.yidan.xiaoaimei.model.CommonEmptyInfo;
import com.yidan.xiaoaimei.model.user.TagInfo;
import com.yidan.xiaoaimei.model.user.TagsModel;


/**
 * 设置标签presenter
 * Created by jaydenma on 2017/7/17.
 */

public class TagsPresenter implements TagsContract.ITagsPresenter {

    private TagsContract.ITagsView mTagsView;
    private TagsContract.ITagsModel mTagsModel;


    public TagsPresenter(TagsContract.ITagsView mTagsView) {
        this.mTagsView = mTagsView;
        mTagsModel = new TagsModel();
    }

    @Override
    public void getAllTags() {
        mTagsView.showProgress();
        mTagsModel.getAllTags(mTagsView.getToken(), new OnHttpCallBack<TagInfo>() {
            @Override
            public void onSuccessful(TagInfo tagInfo) {
                mTagsView.hideProgress();
                mTagsView.showTags(tagInfo);
            }

            @Override
            public void onFaild(String errorMsg) {
                mTagsView.hideProgress();
                mTagsView.showError(errorMsg);
            }
        });
    }

    @Override
    public void selectTags() {
        mTagsView.showProgress();
        mTagsModel.selectTags(mTagsView.getToken(), mTagsView.getTagStr(), mTagsView.getServiceStr(), new OnHttpCallBack<CommonEmptyInfo>() {
            @Override
            public void onSuccessful(CommonEmptyInfo commonEmptyInfo) {
                mTagsView.hideProgress();
                mTagsView.showInfo(commonEmptyInfo.getMessage());
                mTagsView.toNewPage();
            }

            @Override
            public void onFaild(String errorMsg) {
                mTagsView.hideProgress();
                mTagsView.showError(errorMsg);
            }
        });
    }
}
