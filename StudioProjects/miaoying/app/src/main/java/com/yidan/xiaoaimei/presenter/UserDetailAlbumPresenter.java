package com.yidan.xiaoaimei.presenter;


import com.yidan.xiaoaimei.contract.UserDetailAlbumContract;
import com.yidan.xiaoaimei.http.base.OnHttpCallBack;
import com.yidan.xiaoaimei.model.detail.AlbumShowInfo;
import com.yidan.xiaoaimei.model.detail.UserDetailAlbumModel;

/**
 * Created by jaydenma on 2017/7/21.
 */

public class UserDetailAlbumPresenter implements UserDetailAlbumContract.IUserDetailAlbumPresenter {

    private UserDetailAlbumContract.IUserDetailAlbumView mUserDetailAlbumView;
    private UserDetailAlbumContract.IUserDetailAlbumModel mUserDetaillAlbumModel;

    public UserDetailAlbumPresenter(UserDetailAlbumContract.IUserDetailAlbumView mUserDetailAlbumView) {
        this.mUserDetailAlbumView = mUserDetailAlbumView;
        mUserDetaillAlbumModel = new UserDetailAlbumModel();
    }

    @Override
    public void getAlbum() {
        mUserDetailAlbumView.showProgress();
        mUserDetaillAlbumModel.getAlbum(mUserDetailAlbumView.getToken(), mUserDetailAlbumView.getUserId(), mUserDetailAlbumView.getType(), new OnHttpCallBack<AlbumShowInfo>() {
            @Override
            public void onSuccessful(AlbumShowInfo albumShowInfo) {
                mUserDetailAlbumView.hideProgress();
                mUserDetailAlbumView.showAlbum(albumShowInfo);
            }

            @Override
            public void onFaild(String errorMsg) {
                mUserDetailAlbumView.hideProgress();
                mUserDetailAlbumView.showErrorMsg(errorMsg);
            }
        });
    }
}
