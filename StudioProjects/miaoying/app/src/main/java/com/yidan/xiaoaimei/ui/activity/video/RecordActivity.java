package com.yidan.xiaoaimei.ui.activity.video;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.miaokong.commonutils.utils.ScreenUtils;
import com.miaokong.commonutils.utils.ToastUtil;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.tencent.ugc.TXRecordCommon;
import com.tencent.ugc.TXUGCRecord;
import com.yidan.xiaoaimei.R;
import com.yidan.xiaoaimei.base.BaseActivity;
import com.yidan.xiaoaimei.ui.view.CircleButtonView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by jaydenma on 2017/8/1.
 */

public class RecordActivity extends BaseActivity implements TXRecordCommon.ITXVideoRecordListener {

    @BindView(R.id.video_view)
    TXCloudVideoView videoView;
    @BindView(R.id.cbv_record)
    CircleButtonView cbvRecord;
    @BindView(R.id.iv_close)
    ImageView ivClose;

    @OnClick({R.id.iv_close, R.id.iv_reverse_camera})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                finish();
                break;
            case R.id.iv_reverse_camera:
                mFront = !mFront;
                if (mTXCameraRecord != null) {
                    mTXCameraRecord.switchCamera(mFront);
                }
                break;
            default:
                break;
        }
    }

    public static Activity instance;

    private TXUGCRecord mTXCameraRecord;

    private int type = 0;

    private boolean mFront = true;

    private int recordTime;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_record;
    }


    @Override
    public void initData() {
        super.initData();
        ScreenUtils.translateStatusBar(this);
        instance = this;
        if (getIntent().hasExtra("type")) {
            type = getIntent().getIntExtra("type", 0);
        }

        TXRecordCommon.TXUGCSimpleConfig param = new TXRecordCommon.TXUGCSimpleConfig();
        param.videoQuality = TXRecordCommon.VIDEO_QUALITY_MEDIUM;
        param.isFront = mFront;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            param.mHomeOriention = TXLiveConstants.VIDEO_ANGLE_HOME_DOWN;
        } else {
            param.mHomeOriention = TXLiveConstants.VIDEO_ANGLE_HOME_RIGHT;
        }

//        mLivePlayer = new TXLivePlayer(RecordActivity.this);
        mTXCameraRecord = TXUGCRecord.getInstance(this.getApplicationContext());

        mTXCameraRecord.setVideoRecordListener(this);                    //设置录制回调
        videoView = (TXCloudVideoView) findViewById(R.id.video_view);    //准备一个预览摄像头画面的TXCloudVideoView
        videoView.enableHardwareDecode(true);
//        param = new TXRecordCommon.TXUGCSimpleConfig();
        //param.videoQuality = TXRecordCommon.VIDEO_QUALITY_LOW;        // 360p
//        param.videoQuality = TXRecordCommon.VIDEO_QUALITY_MEDIUM;        // 540p
        //param.videoQuality = TXRecordCommon.VIDEO_QUALITY_HIGH;        // 720p
//        param.isFront = mFront;                                            //是否前置摄像头，使用 switchCamera 可以切换
        mTXCameraRecord.startCameraSimplePreview(param, videoView);
        mTXCameraRecord.switchCamera(mFront);

        cbvRecord.setOnLongClickListener(new CircleButtonView.OnLongClickListener() {
            @Override
            public void onLongClick() {
                //开始录制
                mTXCameraRecord.startRecord();
            }

            @Override
            public void onNoMinRecord(int minTime) {
                mTXCameraRecord.stopRecord();
                ToastUtil.showToast(mActivity, "录制太短了");
            }

            @Override
            public void onRecordFinishedListener(int time) {
                mTXCameraRecord.stopRecord();
                recordTime = time;
//                mTXCameraRecord.stopCameraPreview();
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        if (mTXCameraRecord != null) {
            mTXCameraRecord.switchCamera(mFront);
        }
    }

    @Override
    public void onRecordEvent(int i, Bundle bundle) {

    }

    @Override
    public void onRecordProgress(long l) {

    }

    @Override
    public void onRecordComplete(TXRecordCommon.TXRecordResult txRecordResult) {
        Intent intent = new Intent(mActivity, VideoPlayActivity.class);
        intent.putExtra("videoPath", txRecordResult.videoPath);
        intent.putExtra("coverPath", txRecordResult.coverPath);
        intent.putExtra("time", recordTime);
        intent.putExtra("fromType", 1);
        intent.putExtra("type", type);
        startActivity(intent);

    }
}
