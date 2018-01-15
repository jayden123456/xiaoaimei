package com.yidan.xiaoaimei.presenter;


import com.yidan.xiaoaimei.contract.AlbumContract;
import com.yidan.xiaoaimei.http.base.OnHttpCallBack;
import com.yidan.xiaoaimei.model.CommonEmptyInfo;
import com.yidan.xiaoaimei.model.mine.AlbumInfo;
import com.yidan.xiaoaimei.model.mine.AlbumModel;

/**
 * 相册视频集合model
 * Created by jaydenma on 2017/7/21.
 */

public class AlbumPresenter implements AlbumContract.IAlbumPresenter {

    private AlbumContract.IAlbumView mAlbumView;
    private AlbumContract.IAlbumModel mAlbumlModel;

    public AlbumPresenter(AlbumContract.IAlbumView mAlbumView) {
        this.mAlbumView = mAlbumView;
        mAlbumlModel = new AlbumModel();
    }


    @Override
    public void getAlbums() {
        mAlbumView.showProgress();
        mAlbumlModel.getAlbums(mAlbumView.getToken(), mAlbumView.getAlbumType(), new OnHttpCallBack<AlbumInfo>() {
            @Override
            public void onSuccessful(AlbumInfo albumInfo) {
                mAlbumView.hideProgress();
                mAlbumView.showData(albumInfo.getData());
            }

            @Override
            public void onFaild(String errorMsg) {
                mAlbumView.hideProgress();
                mAlbumView.showErrorMsg(errorMsg);
            }
        });
    }

    @Override
    public void addImage() {
//        mAlbumView.showProgress();
        mAlbumlModel.addImage(mAlbumView.getToken(), mAlbumView.getDataJsonStr(), new OnHttpCallBack<CommonEmptyInfo>() {
            @Override
            public void onSuccessful(CommonEmptyInfo commonEmptyInfo) {
                mAlbumView.hideProgress();
                mAlbumView.uploadSuc();
                mAlbumView.showInfo("上传成功");
            }

            @Override
            public void onFaild(String errorMsg) {
                mAlbumView.hideProgress();
                mAlbumView.showErrorMsg(errorMsg);
            }
        });
    }

    @Override
    public void addVedio() {
        mAlbumlModel.addVedio(mAlbumView.getToken(), mAlbumView.getDataJsonStr(), new OnHttpCallBack<CommonEmptyInfo>() {
            @Override
            public void onSuccessful(CommonEmptyInfo commonEmptyInfo) {
                mAlbumView.hideProgress();
                mAlbumView.uploadSuc();
                mAlbumView.showInfo("上传成功");
            }

            @Override
            public void onFaild(String errorMsg) {
                mAlbumView.hideProgress();
                mAlbumView.showErrorMsg(errorMsg);
            }
        });
    }

    @Override
    public void deleteImg() {
        mAlbumView.showProgress();
        mAlbumlModel.deleteImg(mAlbumView.getToken(), mAlbumView.getImageId(), new OnHttpCallBack<CommonEmptyInfo>() {
            @Override
            public void onSuccessful(CommonEmptyInfo commonEmptyInfo) {
                mAlbumView.hideProgress();
                mAlbumView.showInfo("删除成功");
                mAlbumView.deleteSuc();
            }

            @Override
            public void onFaild(String errorMsg) {
                mAlbumView.hideProgress();
                mAlbumView.showErrorMsg(errorMsg);
            }
        });
    }
}
