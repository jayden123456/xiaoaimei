package com.yidan.xiaoaimei.ui.activity.video;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.miaokong.commonutils.oss.Constant;
import com.miaokong.commonutils.oss.ImageFactory;
import com.miaokong.commonutils.oss.OssService;
import com.miaokong.commonutils.utils.LoadingDialogUtil;
import com.miaokong.commonutils.utils.NetworkUtil;
import com.miaokong.commonutils.utils.ScreenUtils;
import com.tencent.rtmp.ITXLivePlayListener;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.yidan.xiaoaimei.MyApplication;
import com.yidan.xiaoaimei.R;
import com.yidan.xiaoaimei.base.BaseActivity;
import com.yidan.xiaoaimei.contract.AlbumContract;
import com.yidan.xiaoaimei.model.mine.AlbumInfo;
import com.yidan.xiaoaimei.presenter.AlbumPresenter;
import com.yidan.xiaoaimei.ui.activity.mine.AlbumActivity;
import com.yidan.xiaoaimei.ui.activity.mine.HomepageActivity;
import com.yidan.xiaoaimei.ui.activity.mine.VideoVerifyActivity;
import com.yidan.xiaoaimei.ui.activity.moment.PublishMomentActivity;
import com.yidan.xiaoaimei.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.tencent.rtmp.TXLiveConstants.PLAY_EVT_PLAY_END;
import static com.tencent.ugc.TXVideoEditConstants.PREVIEW_RENDER_MODE_FILL_EDGE;

/**
 * Created by jaydenma on 2017/8/1.
 */

public class VideoPlayActivity extends BaseActivity implements AlbumContract.IAlbumView {

    @BindView(R.id.video_view)
    TXCloudVideoView videoView;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_ok)
    ImageView ivOk;
    @BindView(R.id.iv_pause)
    ImageView ivPause;

    @OnClick({R.id.iv_back, R.id.iv_ok, R.id.iv_back_finish, R.id.video_view})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_ok:
                //上传视频
                if (type == 1) {
                    VideoVerifyActivity.mVideoPath = videoPath;
                    VideoVerifyActivity.mCoverPath = coverPath;
                    VideoVerifyActivity.mTime = time + "";
                    VideoVerifyActivity.isAddVideo = true;
                    RecordActivity.instance.finish();
                    finish();
                } else if (type == 2) {
                    PublishMomentActivity.mVideoPath = videoPath;
                    PublishMomentActivity.mCoverPath = coverPath;
                    PublishMomentActivity.mTime = time + "";
                    PublishMomentActivity.isAddVideo = true;
                    RecordActivity.instance.finish();
                    finish();
                } else {
                    mVideoUrl = "video/" + System.currentTimeMillis() + ".mp4";
                    mCoverUrl = "image/" + System.currentTimeMillis() + ".jpg";
                    loadingDialog = LoadingDialogUtil.createLoadingDialog(mActivity, "");
                    ossService.asyncPutImage(mVideoUrl, videoPath);
                    ossService.asyncPutImage(mCoverUrl, coverPath);
                }
                break;
            case R.id.iv_back_finish:
                finish();
                break;
            case R.id.video_view:
                //暂停
                if (isPlayed) {
                    mLivePlayer.pause();
                    isPlayed = false;
                    ivPause.setVisibility(View.VISIBLE);
                } else {
                    mLivePlayer.resume();
                    isPlayed = true;
                    ivPause.setVisibility(View.GONE);
                }
                break;
            default:
                break;
        }
    }

    private Dialog loadingDialog;

    private AlbumPresenter albumPresenter;

    private TXLivePlayer mLivePlayer; //腾讯云视频播放对象

    private boolean isPlayed = true;

    private int count = 0;

    private String videoPath;
    private String coverPath;
    private int time;

    private String mVideoUrl;
    private String mCoverUrl;

    private List<Video> videos = new ArrayList<Video>();

    private int fromType, type;

//    private VideoTimesDialog videoTimesDialog;

    //阿里oss
    public OssService ossService;

    // 处理oss上传后的回调
    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //上传成功
            if (msg.what == Constant.UPLOADOK) {
                if (count == 0) {
                    albumPresenter.addVedio();
                    count++;
                }
            }
        }

    };

    @Override
    public int getLayoutResId() {
        return R.layout.activity_video_play;
    }

    @Override
    public void initData() {
        super.initData();
        ScreenUtils.translateStatusBar(this);

        //初始化oss
        ossService = ImageFactory.initOSS(this, Constant.ENDPOINT, Constant.BUCKET, null);
        ossService.setHandler(mHandler);

        albumPresenter = new AlbumPresenter(this);

        mLivePlayer = new TXLivePlayer(VideoPlayActivity.this);

        fromType = getIntent().getIntExtra("fromType", 0);
        videoPath = getIntent().getStringExtra("videoPath");
        coverPath = getIntent().getStringExtra("coverPath");

        if (getIntent().hasExtra("time")) {
            time = getIntent().getIntExtra("time", 0);
        }

        if (getIntent().hasExtra("type")) {
            type = getIntent().getIntExtra("type", 0);
        }
//        Glide.with(mActivity).load("file://" + coverPath).into(ivCover);
        mLivePlayer.setPlayerView(videoView);
        mLivePlayer.setRenderMode(PREVIEW_RENDER_MODE_FILL_EDGE);
        if (fromType == 1) {
            mLivePlayer.startPlay(videoPath, TXLivePlayer.PLAY_TYPE_LOCAL_VIDEO);
            ivBack.setVisibility(View.VISIBLE);
            ivOk.setVisibility(View.VISIBLE);
        } else {
            if (type == 1 || type == 2) {
                mLivePlayer.startPlay(videoPath, TXLivePlayer.PLAY_TYPE_LOCAL_VIDEO);
            } else {
                if (!NetworkUtil.isWifi(mActivity) && MyApplication.getNoWifiAvailable().equals("0")) {
//                videoTimesDialog = new VideoTimesDialog(mActivity, "当前为非Wi-Fi环境，是否使用流量观看视频？", "继续观看", "取消", new noWifiListener());
//                videoTimesDialog.show();
                } else {
                    mLivePlayer.startPlay(videoPath, TXLivePlayer.PLAY_TYPE_VOD_MP4);
                }
            }
            ivBack.setVisibility(View.GONE);
            ivOk.setVisibility(View.GONE);
        }
        mLivePlayer.setPlayListener(new ITXLivePlayListener() {
            @Override
            public void onPlayEvent(int i, Bundle bundle) {
                if (i == PLAY_EVT_PLAY_END) {
                    mLivePlayer.seek(0);
                    mLivePlayer.pause();
                    isPlayed = false;
                    ivPause.setVisibility(View.VISIBLE);
//                    mLivePlayer.startPlay(videoPath, TXLivePlayer.PLAY_TYPE_LOCAL_VIDEO);
                }
            }

            @Override
            public void onNetStatus(Bundle bundle) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLivePlayer.stopPlay(true); // true代表清除最后一帧画面
        videoView.onDestroy();
    }

    @Override
    public void showProgress() {
        loadingDialog = LoadingDialogUtil.createLoadingDialog(mActivity, "");
    }

    @Override
    public void hideProgress() {
        LoadingDialogUtil.closeDialog(loadingDialog);
    }

    @Override
    public void showInfo(String info) {
        ToastUtils.ShowSucess(mActivity, info);
    }

    @Override
    public void showErrorMsg(String msg) {
        ToastUtils.ShowError(mActivity, msg);
    }

    @Override
    public void showData(List<AlbumInfo.DataBean> data) {

    }


    @Override
    public void uploadSuc() {
        RecordActivity.instance.finish();
        AlbumActivity.changed = 1;
        HomepageActivity.isChange = 1;
        LoadingDialogUtil.closeDialog(loadingDialog);
        finish();
    }

    @Override
    public void deleteSuc() {

    }

    @Override
    public String getToken() {
        return spUtil.getStringValue("token", "");
    }


    @Override
    public String getAlbumType() {
        return "";
    }

    @Override
    public String getDataJsonStr() {
        Video video = new Video();
        video.setVideoUrl(mVideoUrl);
        video.setFirstImg(mCoverUrl);
        video.setVideoTime(time + "");
        videos.add(video);
        Gson gson = new Gson();
        return gson.toJson(videos);
    }

    @Override
    public String getImageId() {
        return null;
    }


    public class Video {
        private String firstImg;
        private String videoUrl;
        private String videoTime;

        public String getFirstImg() {
            return firstImg;
        }

        public void setFirstImg(String firstImg) {
            this.firstImg = firstImg;
        }

        public String getVideoUrl() {
            return videoUrl;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }

        public String getVideoTime() {
            return videoTime;
        }

        public void setVideoTime(String videoTime) {
            this.videoTime = videoTime;
        }
    }

//    class noWifiListener implements VideoTimesDialog.OkListener {
//
//        @Override
//        public void ok() {
//            videoTimesDialog.dismiss();
//            mLivePlayer.startPlay(videoPath, TXLivePlayer.PLAY_TYPE_VOD_MP4);
//            MKApplication.setNoWifiAvailable("1");
//        }
//
//        @Override
//        public void cancel() {
//            videoTimesDialog.dismiss();
//        }
//    }
}
