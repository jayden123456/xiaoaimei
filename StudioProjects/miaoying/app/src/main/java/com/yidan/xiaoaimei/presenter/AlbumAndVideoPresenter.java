package com.yidan.xiaoaimei.presenter;

import com.yidan.xiaoaimei.contract.AlbumAndVideoContract;
import com.yidan.xiaoaimei.http.base.OnHttpCallBack;
import com.yidan.xiaoaimei.model.mine.AlbumAndVideoInfo;
import com.yidan.xiaoaimei.model.mine.AlbumAndVideoModel;


/**
 * 相册和视频presenter
 * Created by jaydenma on 2017/7/17.
 */

public class AlbumAndVideoPresenter implements AlbumAndVideoContract.IAlbumAndVideoPresenter {

    private AlbumAndVideoContract.IAlbumAndVideoView mAlbumAndVideoView;
    private AlbumAndVideoContract.IAlbumAndVideoModel mAlbumAndVideoModel;


    public AlbumAndVideoPresenter(AlbumAndVideoContract.IAlbumAndVideoView mAlbumAndVideoView) {
        this.mAlbumAndVideoView = mAlbumAndVideoView;
        mAlbumAndVideoModel = new AlbumAndVideoModel();
    }

    @Override
    public void getAlbumAndVideo() {
        mAlbumAndVideoView.showProgress();
        mAlbumAndVideoModel.getAlbumAndVideo(mAlbumAndVideoView.getToken(), new OnHttpCallBack<AlbumAndVideoInfo>() {
            @Override
            public void onSuccessful(AlbumAndVideoInfo albumAndVideoInfo) {
                mAlbumAndVideoView.hideProgress();
                mAlbumAndVideoView.showData(albumAndVideoInfo);
            }

            @Override
            public void onFaild(String errorMsg) {
                mAlbumAndVideoView.hideProgress();
                mAlbumAndVideoView.showError(errorMsg);
            }
        });
    }
}
