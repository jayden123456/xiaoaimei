package com.yidan.xiaoaimei.ui.activity.mine;

import android.app.Dialog;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.miaokong.commonutils.oss.Constant;
import com.miaokong.commonutils.oss.ImageFactory;
import com.miaokong.commonutils.oss.OssService;
import com.miaokong.commonutils.utils.LoadingDialogUtil;
import com.miaokong.commonutils.utils.StringUtil;
import com.yidan.xiaoaimei.R;
import com.yidan.xiaoaimei.base.BaseActivity;
import com.yidan.xiaoaimei.contract.VerifyContract;
import com.yidan.xiaoaimei.model.mine.VerifyInfo;
import com.yidan.xiaoaimei.presenter.VerifyPresenter;
import com.yidan.xiaoaimei.ui.dialog.RecordDialog;
import com.yidan.xiaoaimei.utils.AudioRecoderUtils;
import com.yidan.xiaoaimei.utils.ToastUtils;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 语音认证
 * Created by jaydenma on 2017/12/5.
 */

public class VoiceVerifyActivity extends BaseActivity implements VerifyContract.IVerifyView {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_button_right)
    TextView tvButtonRight;
    @BindView(R.id.tv_empty)
    TextView tvEmpty;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.tv_duration)
    TextView tvDuration;
    @BindView(R.id.ll_voice)
    LinearLayout llVoice;
    @BindView(R.id.view_voice)
    View viewVoice;
    @BindView(R.id.tv_record)
    TextView tvRecord;

    @OnClick({R.id.iv_back, R.id.tv_button_right, R.id.iv_play_voice})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_button_right:
                loadingDialog = LoadingDialogUtil.createLoadingDialog(mActivity, "");
                ossService.asyncPutImage(mVoicePath, mfilePath);
                break;
            case R.id.iv_play_voice:
                if (mediaPlayer.isPlaying()) {
                    ToastUtils.ShowError(mActivity, "正在播放语音");
                } else {
                    if (!StringUtil.isEmpty(verifyInfo.getData().getVoiceUrl())) {
                        try {
                            mediaPlayer.setDataSource(verifyInfo.getData().getVoiceUrl());
                            mediaPlayer.prepare();
                            mediaPlayer.start();
                        } catch (IOException e) {
                            Log.e("VoiceVerifyActivity.class", "prepare() failed");
                        }
                    } else {
                        try {
                            mediaPlayer.setDataSource(mfilePath);
                            mediaPlayer.prepare();
                            mediaPlayer.start();
                        } catch (IOException e) {
                            Log.e("VoiceVerifyActivity.class", "prepare() failed");
                        }
                    }
                }
                break;
            default:
                break;
        }
    }

    private Dialog loadingDialog;

    private VerifyPresenter verifyPresenter;

    private static final int MIN_RECORD_TIME = 10; // 最短录音时间，单位秒
    private static final int MAX_RECORD_TIME = 60;//最长录音时间，单位秒
    private static final int RECORD_OFF = 0; // 不在录音
    private static final int RECORD_ON = 1; // 正在录音

    private int recordState = 0; // 录音状态
    private int recordTime = 0; // 录音时长，如果录音时间太短则录音失败
    private double voiceValue = 0.0; // 录音的音量值
    private boolean isCanceled = false; // 是否取消录音
    private float downY;

    private boolean onRecord = false;

    private RecordDialog recordDialog;

    private AudioRecoderUtils audioRecoderUtils;

    private String mVoicePath;

    private String mfilePath;

    private VerifyInfo verifyInfo = new VerifyInfo();

    private MediaPlayer mediaPlayer;

    //阿里oss
    public OssService ossService;

    // 处理oss上传后的回调
    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //上传成功
            if (msg.what == Constant.UPLOADOK) {
                verifyPresenter.submitVoice();
            }
        }

    };

    @Override
    public int getLayoutResId() {
        return R.layout.activity_voice_verify;
    }

    @Override
    public void initData() {
        super.initData();
        //初始化oss
        ossService = ImageFactory.initOSS(this, Constant.ENDPOINT, Constant.BUCKET, null);
        ossService.setHandler(mHandler);

        mediaPlayer = new MediaPlayer();

        recordDialog = new RecordDialog(mActivity, new finishListener());

        tvTitle.setText("语音认证");
        tvButtonRight.setText("上传");
        tvButtonRight.setTextColor(getResources().getColor(R.color.commonWhite));
        tvButtonRight.setBackgroundResource(R.drawable.common_unclick_button);
        tvButtonRight.setClickable(false);
        tvButtonRight.setPadding(10, 0, 10, 0);
        tvButtonRight.setVisibility(View.VISIBLE);
        verifyPresenter = new VerifyPresenter(this);
        verifyPresenter.getVerify();

        audioRecoderUtils = new AudioRecoderUtils();

        audioRecoderUtils.setOnAudioStatusUpdateListener(new AudioRecoderUtils.OnAudioStatusUpdateListener() {
            @Override
            public void onUpdate(double db, long time) {

            }

            @Override
            public void onStop(String filePath) {
                //录制完成
                mVoicePath = "voice/" + System.currentTimeMillis() + ".amr";
                mfilePath = filePath;

                tvButtonRight.setBackgroundResource(R.drawable.common_red_button);
                tvButtonRight.setClickable(true);
                tvButtonRight.setPadding(10, 0, 10, 0);

                verifyInfo.getData().setVoiceUrl("");

            }
        });


        tvRecord.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: // 按下按钮
                        if (recordState != RECORD_ON) {
                            tvRecord.setText("松开结束");
                            tvRecord.setBackgroundResource(R.drawable.common_red_button_click);
                            recordDialog.resetTime();
                            recordDialog.show();
                            downY = event.getY();
                            if (audioRecoderUtils != null) {
                                audioRecoderUtils.startRecord();
                                recordState = RECORD_ON;
                                onRecord = true;
                            }
                        }
                        break;
                    case MotionEvent.ACTION_MOVE: // 滑动手指
                        float moveY = event.getY();
                        if (downY - moveY > 50) {
                            isCanceled = true;
                            recordDialog.setBottom(0);
                        }
                        if (downY - moveY < 20) {
                            isCanceled = false;
                            recordDialog.setBottom(1);
                        }

                        //MAX_RECORD_TIME 最长录音时间剩余时间的提醒
//                        if (recodeTime < MAX_RECORD_TIME && recodeTime >= MAX_RECORD_TIME - 10) {
//                            if (mRecordDialog.isShowing()) {
//                                showVoiceDialog(2);
//                            }
//                        }

                        //录音时间超过最大时间直接取消
                        if (recordTime >= MAX_RECORD_TIME) {
                            if (recordDialog.isShowing()) {
                                recordDialog.dismiss();
                            }
                            audioRecoderUtils.stopRecord();
                        }
                        break;
                    case MotionEvent.ACTION_UP: // 松开手指
                        if (recordState == RECORD_ON) {
                            onRecord = false;
                            recordState = RECORD_OFF;
                            if (recordDialog.isShowing()) {
                                recordDialog.dismiss();
                            }
                            voiceValue = 0.0;
                            if (isCanceled) {
                                audioRecoderUtils.cancelRecord();
                            } else {
                                if (recordTime < MIN_RECORD_TIME) {
                                    ToastUtils.ShowError(mActivity, "录制时间太短");
                                    audioRecoderUtils.cancelRecord();
                                } else {
                                    audioRecoderUtils.stopRecord();
                                    tvDuration.setText(recordTime + "s");
                                    tvStatus.setText("可上传");
                                    if (llVoice.getVisibility() == View.GONE) {
                                        llVoice.setVisibility(View.VISIBLE);
                                        viewVoice.setVisibility(View.VISIBLE);
                                        tvEmpty.setVisibility(View.GONE);
                                    }
                                }
                            }
                            isCanceled = false;
                            tvRecord.setText("按住录音(10-60s)");
                            tvRecord.setBackgroundResource(R.drawable.common_red_button_default);
                        }
                        break;
                }
                return true;
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
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
        tvButtonRight.setBackgroundResource(R.drawable.common_unclick_button);
        tvButtonRight.setPadding(10, 0, 10, 0);
        tvButtonRight.setClickable(false);
        tvStatus.setText("已上传");
    }

    @Override
    public void showError(String msg) {
        ToastUtils.ShowError(mActivity, msg);
    }

    @Override
    public void showVerify(VerifyInfo info) {
        verifyInfo = info;
        if (info.getData() != null) {
            tvEmpty.setVisibility(View.GONE);
            viewVoice.setVisibility(View.VISIBLE);
            llVoice.setVisibility(View.VISIBLE);
            tvStatus.setText("已上传");
            tvDuration.setText(info.getData().getVoiceTime() + "s");
        } else {
            tvEmpty.setVisibility(View.VISIBLE);
            viewVoice.setVisibility(View.GONE);
            llVoice.setVisibility(View.GONE);
        }
    }

    @Override
    public String getToken() {
        return spUtil.getStringValue("token", "");
    }

    @Override
    public String getType() {
        return "6";
    }

    @Override
    public String getTime() {
        return recordTime + "";
    }

    @Override
    public String getUrl() {
        return mVoicePath;
    }

    @Override
    public String getFirstImg() {
        return null;
    }


    class finishListener implements RecordDialog.FinishListener {

        @Override
        public void finishRecord() {
            audioRecoderUtils.stopRecord();
            tvRecord.setText("按住录音(10-60s)");
            tvRecord.setBackgroundResource(R.drawable.common_red_button_default);
        }

        @Override
        public void onTime(String time) {
            if (onRecord) {
                recordTime = Integer.parseInt(time);
            }
        }
    }
}
