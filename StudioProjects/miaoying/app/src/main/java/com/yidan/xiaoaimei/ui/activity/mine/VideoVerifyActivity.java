package com.yidan.xiaoaimei.ui.activity.mine;

import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
import com.yidan.xiaoaimei.ui.activity.video.RecordActivity;
import com.yidan.xiaoaimei.ui.activity.video.VideoPlayActivity;
import com.yidan.xiaoaimei.ui.activity.video.choose.TCVideoChooseActivity;
import com.yidan.xiaoaimei.ui.dialog.ImageUploadPopwindow;
import com.yidan.xiaoaimei.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 视频认证
 * Created by jaydenma on 2017/12/5.
 */

public class VideoVerifyActivity extends BaseActivity implements VerifyContract.IVerifyView {

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
    @BindView(R.id.ll_video)
    LinearLayout llVideo;
    @BindView(R.id.iv_first_image)
    ImageView ivFirstImage;
    @BindView(R.id.rl_video)
    RelativeLayout rlVideo;
    @BindView(R.id.view_voice)
    View viewVoice;
    @BindView(R.id.tv_record)
    TextView tvRecord;

    @OnClick({R.id.iv_back, R.id.tv_button_right, R.id.tv_record, R.id.iv_first_image})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_button_right:
                loadingDialog = LoadingDialogUtil.createLoadingDialog(mActivity, "");
                ossService.asyncPutImage(mVideoUrl, mVideoPath);
                ossService.asyncPutImage(mCoverUrl, mCoverPath);
                break;
            case R.id.tv_record:
                imageUploadPopwindow.show();
                break;
            case R.id.iv_first_image:
                //播放
                if (StringUtil.isEmpty(verifyInfo.getData().getVideoUrl())) {
                    Intent intent = new Intent(mActivity, VideoPlayActivity.class);
                    intent.putExtra("videoPath", mVideoPath);
                    intent.putExtra("coverPath", mCoverPath);
                    intent.putExtra("fromType", 2);
                    intent.putExtra("type", 1);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(mActivity, VideoPlayActivity.class);
                    intent.putExtra("videoPath", verifyInfo.getData().getVideoUrl());
                    intent.putExtra("coverPath", verifyInfo.getData().getFirstImg());
                    intent.putExtra("fromType", 2);
                    startActivity(intent);
                }
                break;
            default:
                break;
        }
    }

    public static boolean isAddVideo = false;

    public static String mVideoPath, mCoverPath, mTime;

    private String mVideoUrl, mCoverUrl;

    private VerifyInfo verifyInfo = new VerifyInfo();

    private VerifyPresenter verifyPresenter;

    private Dialog loadingDialog;

    private ImageUploadPopwindow imageUploadPopwindow;

    private int count = 0;

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
                    verifyPresenter.submitVideo();
                    count++;
                }
            }
        }

    };

    @Override
    public int getLayoutResId() {
        return R.layout.activity_video_verify;
    }


    @Override
    public void initData() {
        super.initData();
        //初始化oss
        ossService = ImageFactory.initOSS(this, Constant.ENDPOINT, Constant.BUCKET, null);
        ossService.setHandler(mHandler);

        tvTitle.setText("视频认证");
        tvButtonRight.setText("上传");
        tvButtonRight.setTextColor(getResources().getColor(R.color.commonWhite));
        tvButtonRight.setBackgroundResource(R.drawable.common_unclick_button);
        tvButtonRight.setClickable(false);
        tvButtonRight.setPadding(10, 0, 10, 0);
        tvButtonRight.setVisibility(View.VISIBLE);

        imageUploadPopwindow = new ImageUploadPopwindow(mActivity, "拍摄", "本地上传", new listener());

        verifyPresenter = new VerifyPresenter(this);
        verifyPresenter.getVerify();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isAddVideo) {
            tvStatus.setText("可上传");
            tvDuration.setText(mTime + "s");
            Glide.with(mActivity).load("file://" + mCoverPath).into(ivFirstImage);
            tvEmpty.setVisibility(View.GONE);
            llVideo.setVisibility(View.VISIBLE);
            rlVideo.setVisibility(View.VISIBLE);
            viewVoice.setVisibility(View.VISIBLE);

            mVideoUrl = "video/" + System.currentTimeMillis() + ".mp4";
            mCoverUrl = "image/" + System.currentTimeMillis() + ".jpg";

            tvButtonRight.setBackgroundResource(R.drawable.common_red_button);
            tvButtonRight.setClickable(true);
            tvButtonRight.setPadding(10, 0, 10, 0);

            verifyInfo.getData().setVideoUrl("");
            verifyInfo.getData().setFirstImg("");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isAddVideo = false;
        mVideoPath = "";
        mCoverPath = "";
        mTime = "";
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
        if (info.getData() != null && !StringUtil.isEmpty(info.getData().getVideoUrl())) {
            tvEmpty.setVisibility(View.GONE);
            llVideo.setVisibility(View.VISIBLE);
            rlVideo.setVisibility(View.VISIBLE);
            viewVoice.setVisibility(View.VISIBLE);
            tvStatus.setText("已上传");
            tvDuration.setText(info.getData().getVideoTime() + "s");
            Glide.with(mActivity).load(info.getData().getFirstImg()).into(ivFirstImage);
        } else {
            tvEmpty.setVisibility(View.VISIBLE);
            llVideo.setVisibility(View.GONE);
            rlVideo.setVisibility(View.GONE);
            viewVoice.setVisibility(View.GONE);
        }
    }

    @Override
    public String getToken() {
        return spUtil.getStringValue("token", "");
    }

    @Override
    public String getType() {
        return "5";
    }

    @Override
    public String getTime() {
        return mTime;
    }

    @Override
    public String getUrl() {
        return mVideoUrl;
    }

    @Override
    public String getFirstImg() {
        return mCoverUrl;
    }

    class listener implements ImageUploadPopwindow.onSelectSexListener {

        @Override
        public void onSelect(int location) {
            if (location == 0) {
                Intent intent = new Intent(mActivity, RecordActivity.class);
                intent.putExtra("type", 1);
                startActivity(intent);
            } else {
                Intent localIntent = new Intent(mActivity, TCVideoChooseActivity.class);
                localIntent.putExtra("type", 1);
                startActivity(localIntent);
            }
        }
    }

}
