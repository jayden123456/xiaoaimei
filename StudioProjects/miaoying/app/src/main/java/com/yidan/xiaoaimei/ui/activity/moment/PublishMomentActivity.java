package com.yidan.xiaoaimei.ui.activity.moment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.miaokong.commonutils.oss.Constant;
import com.miaokong.commonutils.oss.ImageFactory;
import com.miaokong.commonutils.oss.OssService;
import com.miaokong.commonutils.utils.AutoViwHeightUtil;
import com.miaokong.commonutils.utils.LoadingDialogUtil;
import com.miaokong.commonutils.utils.StringUtil;
import com.yidan.xiaoaimei.R;
import com.yidan.xiaoaimei.base.BaseActivity;
import com.yidan.xiaoaimei.base.adapterutils.SpacesItemDecoration;
import com.yidan.xiaoaimei.contract.MomentPublishContract;
import com.yidan.xiaoaimei.model.find.MomentPublishInfo;
import com.yidan.xiaoaimei.model.find.PriceOptionInfo;
import com.yidan.xiaoaimei.model.find.TopicInfo;
import com.yidan.xiaoaimei.presenter.MomentPublishPresenter;
import com.yidan.xiaoaimei.ui.activity.video.RecordActivity;
import com.yidan.xiaoaimei.ui.activity.video.VideoPlayActivity;
import com.yidan.xiaoaimei.ui.activity.video.choose.TCVideoChooseActivity;
import com.yidan.xiaoaimei.ui.dialog.CommonDialog;
import com.yidan.xiaoaimei.ui.dialog.ImageUploadPopwindow;
import com.yidan.xiaoaimei.ui.dialog.RecordDialog;
import com.yidan.xiaoaimei.utils.AudioRecoderUtils;
import com.yidan.xiaoaimei.utils.ToastUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import cn.finalteam.toolsfinal.StringUtils;
import xxsh.neu.android.com.mylibrary.Luban;
import xxsh.neu.android.com.mylibrary.OnCompressListener;

import static cn.finalteam.toolsfinal.DeviceUtils.dip2px;

/**
 * 发布动态
 * Created by jaydenma on 2017/12/27.
 */

public class PublishMomentActivity extends BaseActivity implements MomentPublishContract.IMomentPublishView {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_button_right)
    TextView tvButtonRight;
    @BindView(R.id.et_text)
    EditText etText;
    @BindView(R.id.rv_photo)
    RecyclerView rvPhoto;
    @BindView(R.id.ll_photo)
    LinearLayout llPhoto;
    @BindView(R.id.ftl_topic)
    TagFlowLayout ftlTopic;
    @BindView(R.id.ll_topic)
    LinearLayout llTopic;
    @BindView(R.id.ftl_price)
    TagFlowLayout ftlPrice;
    @BindView(R.id.ll_price)
    LinearLayout llPrice;
    @BindView(R.id.tv_voice)
    TextView tvVoice;
    @BindView(R.id.iv_img)
    ImageView ivImg;
    @BindView(R.id.iv_delete)
    ImageView ivDelete;
    @BindView(R.id.iv_play)
    ImageView ivPlay;
    @BindView(R.id.rl_video)
    RelativeLayout rlVideo;
    @BindView(R.id.ll_media)
    LinearLayout llMedia;
    @BindView(R.id.tv_record)
    TextView tvRecord;
    @BindView(R.id.ll_record)
    LinearLayout llRecord;
    @BindView(R.id.tv_text_num)
    TextView tvTextNum;
    @BindView(R.id.iv_voice_animation)
    ImageView ivVoiceAnimation;
    @BindView(R.id.rl_voice)
    RelativeLayout rlVoice;


    @OnClick({R.id.iv_back, R.id.tv_button_right, R.id.iv_delete, R.id.rl_video, R.id.rl_voice, R.id.tv_record})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                if (!StringUtil.isEmpty(etText.getText().toString().trim())
                        || topicIds.size() > 0
                        || imageUrls.size() > 0
                        || priceId > 0
                        || !StringUtil.isEmpty(mVideoUrl)
                        || !StringUtil.isEmpty(mVoicePath)) {
                    commonDialog.show();
                } else {
                    finish();
                }
                break;
            case R.id.tv_button_right:
                if (type == 3) {
                    mVideoUrl = "video/" + System.currentTimeMillis() + ".mp4";
                    mCoverUrl = "image/" + System.currentTimeMillis() + ".jpg";
                    ossService.asyncPutImage(mVideoUrl, mVideoPath);
                    ossService.asyncPutImage(mCoverUrl, mCoverPath);
                } else {
                    momentPublishPresenter.publishMoment();
                }
                break;
            case R.id.iv_delete:
                ivDelete.setVisibility(View.GONE);
                ivPlay.setVisibility(View.GONE);
                ivImg.setImageResource(R.mipmap.bg_moment_image_add);
                mVideoPath = "";
                mCoverPath = "";
                mTime = "";
                checkCanSubmit();
                break;
            case R.id.rl_video:
                if (StringUtil.isEmpty(mVideoPath)) {
                    imageUploadPopwindow.show();
                } else {
                    //播放
                    Intent intent = new Intent(mActivity, VideoPlayActivity.class);
                    intent.putExtra("videoPath", mVideoPath);
                    intent.putExtra("coverPath", mCoverPath);
                    intent.putExtra("type", 1);
                    intent.putExtra("fromType", 2);
                    startActivity(intent);
                }
                break;
            case R.id.rl_voice:
                //播放语音
                if (mediaPlayer.isPlaying()) {
                    ToastUtils.ShowError(mActivity, "正在播放语音");
                } else {
                    try {
                        mediaPlayer.reset();
                        mediaPlayer.setDataSource(mfilePath);
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                        animationDrawable = (AnimationDrawable) ivVoiceAnimation.getDrawable();
                        animationDrawable.start();
                    } catch (IOException e) {
                        Log.e("PublishMomentActivity.class", "prepare() failed");
                    }
                }
                break;
            default:
                break;
        }
    }

    public static boolean isAddVideo = false;

    public static String mVideoPath, mCoverPath, mTime;

    private String mVideoUrl, mCoverUrl;

    private static final int GALLERY_REQUEST_REPLACE_CODE = 1;
    private static final int GALLERY_REQUEST_TAKE_CODE = 2;

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

    private MediaPlayer mediaPlayer;

    private RecordDialog recordDialog;

    private AudioRecoderUtils audioRecoderUtils;

    private String mVoicePath;

    private String mfilePath;

    private ImageUploadPopwindow imageUploadPopwindow;

    private Dialog loadingDialog;

    private MomentPublishPresenter momentPublishPresenter;

    private List<TopicInfo.DataBean> topics = new ArrayList<TopicInfo.DataBean>();

    private List<PriceOptionInfo.DataBean> prices = new ArrayList<PriceOptionInfo.DataBean>();

    private TagAdapter<TopicInfo.DataBean> topicAdapter;

    private TagAdapter<PriceOptionInfo.DataBean> priceAdapter;

    private List<Integer> topicIds = new ArrayList<Integer>();

    private List<Integer> priceIds = new ArrayList<Integer>();

    private int priceId = 0;

    private Gson gson = new Gson();

    private int type;

    private List<String> imagePaths = new ArrayList<String>();

    private List<String> imageUrls = new ArrayList<String>();

    private CommonAdapter<String> imageAdapter;

    private RecyclerView.LayoutManager mLayoutManager;

    private String mImageUrl;

    private int uploadNo = 0;

    private int imageCount;

    private int videoUploadCount = 0;

    private CommonDialog commonDialog;

    private AnimationDrawable animationDrawable;

    //阿里oss
    public OssService ossService;

    // 处理oss上传后的回调
    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //上传成功
            if (msg.what == Constant.UPLOADOK) {
                if (type == 3) {
                    if (videoUploadCount == 0) {
                        videoUploadCount++;
                        momentPublishPresenter.publishMoment();
                    }
                } else {
                    if (uploadNo == imageCount) {
                        LoadingDialogUtil.closeDialog(loadingDialog);
                        uploadNo = 0;
                        if (imagePaths.size() < 9) {
                            imagePaths.add("add@pic");
                        }
                        imageAdapter.notifyDataSetChanged();
                    }
                }
                checkCanSubmit();
            }
        }

    };

    @Override
    public int getLayoutResId() {
        return R.layout.activity_moment_publish;
    }

    @Override
    public void initData() {
        super.initData();
        type = getIntent().getIntExtra("type", 1);
        if (type == 1) {
            tvTitle.setText("发文字");
            llPhoto.setVisibility(View.GONE);
            llPrice.setVisibility(View.GONE);
            llMedia.setVisibility(View.GONE);
            llRecord.setVisibility(View.GONE);
        } else if (type == 2) {
            tvTitle.setText("发图片");
            llPhoto.setVisibility(View.VISIBLE);
            llPrice.setVisibility(View.VISIBLE);
            llMedia.setVisibility(View.GONE);
            llRecord.setVisibility(View.GONE);
            imageUploadPopwindow = new ImageUploadPopwindow(mActivity, "上传图片", "点击拍摄", new listener());
        } else if (type == 3) {
            tvTitle.setText("发视频");
            llPhoto.setVisibility(View.GONE);
            llPrice.setVisibility(View.VISIBLE);
            llMedia.setVisibility(View.VISIBLE);
            rlVideo.setVisibility(View.VISIBLE);
            rlVoice.setVisibility(View.GONE);
            ivDelete.setVisibility(View.GONE);
            ivPlay.setVisibility(View.GONE);
            ivImg.setImageResource(R.mipmap.bg_moment_image_add);
            llRecord.setVisibility(View.GONE);
            imageUploadPopwindow = new ImageUploadPopwindow(mActivity, "上传视频", "点击拍摄", new listener());
        } else {
            tvTitle.setText("发语音");
            llPhoto.setVisibility(View.GONE);
            llPrice.setVisibility(View.VISIBLE);
            llMedia.setVisibility(View.GONE);
            rlVideo.setVisibility(View.GONE);
            rlVoice.setVisibility(View.VISIBLE);
            llRecord.setVisibility(View.VISIBLE);
        }

        commonDialog = new CommonDialog(mActivity, "提示", "退出此次编辑？", "退出", "取消", true, new CommonDialog.OkListener() {
            @Override
            public void ok() {
                finish();
            }
        });

        etText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tvTextNum.setText(s.toString().length() + "/140字");
                checkCanSubmit();
            }
        });

        //初始化oss
        ossService = ImageFactory.initOSS(this, Constant.ENDPOINT, Constant.BUCKET, null);
        ossService.setHandler(mHandler);


        tvButtonRight.setText("发布");
        tvButtonRight.setVisibility(View.VISIBLE);
        tvButtonRight.setTextColor(getResources().getColor(R.color.commonWhite));
        tvButtonRight.setBackgroundResource(R.drawable.common_unclick_button);
        tvButtonRight.setClickable(false);
        tvButtonRight.setPadding(dip2px(mActivity, 5), 0, dip2px(mActivity, 5), 0);

        imagePaths.add("add@pic");

        mLayoutManager = new GridLayoutManager(mActivity, 4);
        rvPhoto.setLayoutManager(mLayoutManager);
        rvPhoto.setHasFixedSize(true);
        rvPhoto.addItemDecoration(new SpacesItemDecoration(false, dip2px(mActivity, 5), dip2px(mActivity, 5), dip2px(mActivity, 10), 0));
        imageAdapter = new CommonAdapter<String>(mActivity, R.layout.item_moment_image, imagePaths) {
            @Override
            protected void convert(ViewHolder holder, String s, final int position) {
                RelativeLayout rl = holder.getView(R.id.rl_album);
                AutoViwHeightUtil.setGridItemHeight(mActivity, rl, 1, 1, dip2px(mActivity, 60), 4);
                ImageView ivPhoto = holder.getView(R.id.iv_item_img);
                ImageView ivDelete = holder.getView(R.id.iv_icon_delete);
                if (s.equals("add@pic")) {
                    Glide.with(mActivity).load(R.mipmap.bg_moment_image_add).into(ivPhoto);
                    ivDelete.setVisibility(View.GONE);
                } else {
                    Glide.with(mActivity).load(s).into(ivPhoto);
                    ivDelete.setVisibility(View.VISIBLE);
                }
                ivDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imagePaths.remove(position);
                        imageUrls.remove(position);
                        imageAdapter.notifyDataSetChanged();
                    }
                });
            }
        };
        rvPhoto.setAdapter(imageAdapter);
        imageAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if (position == imagePaths.size() - 1) {
                    imageUploadPopwindow.show();
                    imagePaths.remove(position);
                } else {
                    //大图

                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

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
                checkCanSubmit();
            }
        });

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                animationDrawable.stop();
                ivVoiceAnimation.setImageResource(R.drawable.voice_animation);
            }
        });

        recordDialog = new RecordDialog(mActivity, new finishListener());


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
                                    llMedia.setVisibility(View.VISIBLE);
                                    tvVoice.setText(recordTime + "s");
//                                    tvStatus.setText("可上传");
//                                    if (llVoice.getVisibility() == View.GONE) {
//                                        llVoice.setVisibility(View.VISIBLE);
//                                        viewVoice.setVisibility(View.VISIBLE);
//                                        tvEmpty.setVisibility(View.GONE);
//                                    }
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


        momentPublishPresenter = new MomentPublishPresenter(this);
        momentPublishPresenter.getTopic();
        momentPublishPresenter.getPriceOptions();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isAddVideo) {
            Glide.with(mActivity).load("file://" + mCoverPath).into(ivImg);
            ivDelete.setVisibility(View.VISIBLE);
            ivPlay.setVisibility(View.VISIBLE);
            checkCanSubmit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isAddVideo = false;
        mCoverPath = "";
        mVideoPath = "";
        mTime = "";
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
        finish();
    }

    @Override
    public void showError(String msg) {
        ToastUtils.ShowError(mActivity, msg);
    }

    @Override
    public void showTopics(TopicInfo info) {
        topics = info.getData();
        final LayoutInflater mInflater = LayoutInflater.from(this);
        topicAdapter = new TagAdapter<TopicInfo.DataBean>(topics) {

            @Override
            public View getView(FlowLayout parent, final int position, final TopicInfo.DataBean dataBean) {
                CheckBox cbTag = (CheckBox) mInflater.inflate(R.layout.item_topic, ftlTopic, false);
                cbTag.setText("#" + dataBean.getTagName() + "#");
                if (dataBean.getStatus() == 1) {
                    cbTag.setChecked(true);
                } else {
                    cbTag.setChecked(false);
                }
                cbTag.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dataBean.getStatus() != 1) {
                            for (TopicInfo.DataBean data : topics) {
                                data.setStatus(0);
                            }
                            topics.get(position).setStatus(1);
                            topicAdapter.notifyDataChanged();
                            topicIds.clear();
                            topicIds.add(dataBean.getTagId());
                        } else {
                            topicAdapter.notifyDataChanged();
                        }
                        checkCanSubmit();
                    }
                });
                return cbTag;
            }
        };
        ftlTopic.setAdapter(topicAdapter);
    }

    @Override
    public void showPrices(PriceOptionInfo info) {
        prices = info.getData();
        final LayoutInflater mInflater = LayoutInflater.from(this);
        priceAdapter = new TagAdapter<PriceOptionInfo.DataBean>(prices) {

            @Override
            public View getView(FlowLayout parent, final int position, final PriceOptionInfo.DataBean dataBean) {
                CheckBox cbTag = (CheckBox) mInflater.inflate(R.layout.item_moment_price, ftlTopic, false);
                cbTag.setText(dataBean.getMomentPrice() + "金币");
                if (dataBean.getStatus() == 1) {
                    cbTag.setChecked(true);
                } else {
                    cbTag.setChecked(false);
                }
                cbTag.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dataBean.getStatus() != 1) {
                            for (PriceOptionInfo.DataBean data : prices) {
                                data.setStatus(0);
                            }
                            prices.get(position).setStatus(1);
                            priceAdapter.notifyDataChanged();
                            priceIds.clear();
                            priceId = dataBean.getOptionId();
                            priceIds.add(dataBean.getOptionId());
                        } else {
                            priceAdapter.notifyDataChanged();
                        }
                        checkCanSubmit();
                    }
                });
                return cbTag;
            }
        };
        ftlPrice.setAdapter(priceAdapter);
    }

    @Override
    public String getToken() {
        return spUtil.getStringValue("token", "");
    }

    @Override
    public String getType() {
        return type + "";
    }

    @Override
    public MomentPublishInfo getData() {
        return new MomentPublishInfo(/** content*/etText.getText().toString().trim(),
                /** city*/"",
                /** voice*/mVoicePath,
                /** voiceTime*/recordTime + "",
                /** video*/mVideoUrl,
                /** firstImg*/mCoverUrl,
                /** videoTime*/mTime,
                /** imgs*/gson.toJson(imageUrls));
    }

    @Override
    public String getTagId() {
        return gson.toJson(topicIds);
    }

    @Override
    public String getOptionId() {
        return priceId + "";
    }


    private void checkCanSubmit() {
        if (type == 1) {
            if (topicIds.size() > 0 && !StringUtils.isEmpty(etText.getText().toString().trim())) {
                tvButtonRight.setClickable(true);
                tvButtonRight.setBackgroundResource(R.drawable.common_red_button);
            } else {
                tvButtonRight.setClickable(false);
                tvButtonRight.setBackgroundResource(R.drawable.common_unclick_button);
            }
        } else if (type == 2) {
            if (topicIds.size() > 0 && imageUrls.size() > 0 && priceId > 0) {
                tvButtonRight.setClickable(true);
                tvButtonRight.setBackgroundResource(R.drawable.common_red_button);
            } else {
                tvButtonRight.setClickable(false);
                tvButtonRight.setBackgroundResource(R.drawable.common_unclick_button);
            }
        } else if (type == 3) {
            if (topicIds.size() > 0 && !StringUtil.isEmpty(mVideoUrl) && priceId > 0) {
                tvButtonRight.setClickable(true);
                tvButtonRight.setBackgroundResource(R.drawable.common_red_button);
            } else {
                tvButtonRight.setClickable(false);
                tvButtonRight.setBackgroundResource(R.drawable.common_unclick_button);
            }
        } else {
            if (topicIds.size() > 0 && !StringUtil.isEmpty(mVoicePath) && priceId > 0) {
                tvButtonRight.setClickable(true);
                tvButtonRight.setBackgroundResource(R.drawable.common_red_button);
            } else {
                tvButtonRight.setClickable(false);
                tvButtonRight.setBackgroundResource(R.drawable.common_unclick_button);
            }
        }


    }


    class listener implements ImageUploadPopwindow.onSelectSexListener {

        @Override
        public void onSelect(int location) {
            if (location == 0) {
                if (type == 2) {
                    //打开相册
                    FunctionConfig functionConfig = new FunctionConfig.Builder()
                            .setMutiSelectMaxSize(9 - imageUrls.size())
                            .setEnableCamera(true)//开启相机功能
                            .setEnableEdit(false)//开启编辑功能
                            .setEnableCrop(false)//开启裁剪功能
                            .setEnableRotate(false)//开启旋转功能
                            .setEnablePreview(false)//是否开启预览功能
                            .build();
                    GalleryFinal.openGalleryMuti(GALLERY_REQUEST_REPLACE_CODE, functionConfig, mOnHanlderResultCallback);
                } else {
                    //上传本地视频
                    Intent localIntent = new Intent(mActivity, TCVideoChooseActivity.class);
                    localIntent.putExtra("type", 2);
                    startActivity(localIntent);
                }
            } else {
                if (type == 2) {
                    //打开相机
                    FunctionConfig functionConfig = new FunctionConfig.Builder()
                            .setMutiSelectMaxSize(9 - imageUrls.size())
                            .setEnableCamera(true)//开启相机功能
                            .setEnableEdit(false)//开启编辑功能
                            .setEnableCrop(false)//开启裁剪功能
                            .setEnableRotate(false)//开启旋转功能
                            .setEnablePreview(false)//是否开启预览功能
                            .build();
                    GalleryFinal.openCamera(GALLERY_REQUEST_TAKE_CODE, functionConfig, mOnHanlderResultCallback);
                } else {
                    //录制视频
                    Intent intent = new Intent(mActivity, RecordActivity.class);
                    intent.putExtra("type", 2);
                    startActivity(intent);
                }
            }
        }
    }


    //相册监听
    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            imageCount = resultList.size();
            switch (reqeustCode) {
                case GALLERY_REQUEST_REPLACE_CODE://相册
                    upPhotoOss(resultList);
                    break;
                case GALLERY_REQUEST_TAKE_CODE://拍照
                    upPhotoOss(resultList);
                    break;
            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
        }
    };


    /**
     * 对图片进行压缩后上传到oss
     *
     * @param resultList
     */
    private void upPhotoOss(List<PhotoInfo> resultList) {

        if (resultList != null && resultList.size() > 0) {
            loadingDialog = LoadingDialogUtil.createLoadingDialog(mActivity, "");
            for (PhotoInfo photo : resultList) {
                String photoPath = photo.getPhotoPath();
                Compress(photoPath, photo.getWidth(), photo.getHeight());
            }
        } else {
            ToastUtils.ShowError(mActivity, "你没有选择图片");
        }
    }


    /**
     * 图片压缩上传
     *
     * @param path
     */
    private void Compress(String path, final int width, final int height) {
        Luban.get(this).load(new File(path)).putGear(Luban.THIRD_GEAR)
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onSuccess(File file) {
                        //图片压缩成功后，发布图片
                        //获取Options对象
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        //仅做解码处理，不加载到内存
                        options.inJustDecodeBounds = true;
                        //解析文件
                        BitmapFactory.decodeFile(file.getPath(), options);
                        //获取宽高
                        int imgWidth = options.outWidth;
                        int imgHeight = options.outHeight;
                        mImageUrl = "image/" + System.currentTimeMillis() + "_" + imgWidth + "_" + imgHeight + "_.jpg";
                        uploadNo++;
                        imageUrls.add(mImageUrl);
                        imagePaths.add(file.getPath());
                        //开始上传
                        ossService.asyncPutImage(mImageUrl, file.getPath());
                    }

                    @Override
                    public void onError(Throwable e) {
//                        LoadingDialogUtil.closeDialog(loadingDialog);
                        //发布失败
                        ToastUtils.ShowError(mActivity, "上传失败");
                    }
                }).launch();

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
