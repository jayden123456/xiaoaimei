package com.yidan.xiaoaimei.ui.activity.moment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.miaokong.commonutils.utils.AutoViwHeightUtil;
import com.miaokong.commonutils.utils.LoadingDialogUtil;
import com.miaokong.commonutils.utils.StringUtil;
import com.yidan.xiaoaimei.Const;
import com.yidan.xiaoaimei.R;
import com.yidan.xiaoaimei.SystemConfig;
import com.yidan.xiaoaimei.base.BaseActivity;
import com.yidan.xiaoaimei.contract.FindDetailContract;
import com.yidan.xiaoaimei.model.find.MomentDetailInfo;
import com.yidan.xiaoaimei.presenter.FindDetailPresenter;
import com.yidan.xiaoaimei.ui.activity.showPicture.ShowPictureActivity;
import com.yidan.xiaoaimei.ui.activity.video.VideoPlayActivity;
import com.yidan.xiaoaimei.ui.dialog.CommonDialog;
import com.yidan.xiaoaimei.ui.view.RoundAngleImageView;
import com.yidan.xiaoaimei.utils.ImageStringUtil;
import com.yidan.xiaoaimei.utils.NormalUtil;
import com.yidan.xiaoaimei.utils.ToastUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.R.attr.type;
import static cn.finalteam.toolsfinal.DeviceUtils.dip2px;
import static cn.finalteam.toolsfinal.DeviceUtils.px2dip;

/**
 * 动态详情
 * Created by jaydenma on 2017/12/23.
 */

public class MomentDetailActivity extends BaseActivity implements FindDetailContract.IFindDetailView {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_comment)
    RecyclerView rvComment;
    @BindView(R.id.et_comment)
    EditText etComment;

    @OnClick(R.id.iv_back)
    public void OnClick() {
        finish();
    }


    private RoundAngleImageView ivHead;

    private TextView tvName;

    private ImageView ivVerify;

    private TextView tvAge;

    private ImageView ivVoice, ivVideo;

    private TextView tvGet, tvPay;

    private TextView tvTime;

    private TextView tvContent;

    private TextView tvLocation;

    private TextView tvTopic;

    private TextView tvZan;

    private TextView tvZanNum;

    private TextView tvCommentNum;

    private LinearLayout llZan;

    private TextView tvComment;

    private View viewBottom;

    private TextView tvTosay;

    private RelativeLayout rlVideo;

    private TextView tvVoice;

    private RelativeLayout rlVoice;

    private ImageView ivVoiceAnimation;

    private RecyclerView rvImage;

    private ImageView ivFirstImage;

    private Drawable drawableZan, drawableZaned;

    private int showZanNum = 0;

    private int twoWidth, threeWidth;

    private int screenWidth, screenHeigh;

    private Dialog loadingDialog;

    private LinearLayoutManager layoutManager;

    private FindDetailPresenter findDetailPresenter;

    private CommonAdapter<MomentDetailInfo.DataBean.CommentBean> adapter;

    private HeaderAndFooterWrapper headerAndFooterWrapper;

    private List<MomentDetailInfo.DataBean.CommentBean> comments = new ArrayList<MomentDetailInfo.DataBean.CommentBean>();

    private CommonAdapter<String> imageAdapter;

    private MomentDetailInfo momentDetailInfo = new MomentDetailInfo();

    private int momentId;

    private int doType = 0;//1:点赞，2:购买图片动态，3:购买视频动态，4:购买语音动态

    private CommonDialog commonDialog;

    private int curPosition;

    private int curWidth, curHeight;

    private View curView;

    private AnimationDrawable animationDrawable;

    private MediaPlayer mediaPlayer;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_moment_detail;
    }

    @Override
    public void initData() {
        super.initData();
        tvTitle.setText("动态详情");

        momentId = getIntent().getIntExtra("momentId", 0);

        //获取屏幕宽高
        DisplayMetrics dm = mActivity.getResources().getDisplayMetrics();
        float windowWidth = dm.widthPixels;
        int curDip = px2dip(mActivity, windowWidth - dip2px(mActivity, 150));
        showZanNum = curDip / 70;
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeigh = dm.heightPixels;
        int screenTop = SystemConfig.getStatusHeight(mActivity);

        SystemConfig.setHight(screenHeigh);
        SystemConfig.setWidth(screenWidth);
        SystemConfig.setTop(screenTop);

        drawableZan = getResources().getDrawable(R.mipmap.icon_zan);
        drawableZan.setBounds(0, 0, drawableZan.getMinimumWidth(), drawableZan.getMinimumHeight());
        drawableZaned = getResources().getDrawable(R.mipmap.icon_zaned);
        drawableZaned.setBounds(0, 0, drawableZaned.getMinimumWidth(), drawableZaned.getMinimumHeight());


        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                animationDrawable.stop();
                ivVoiceAnimation.setImageResource(R.drawable.voice_animation);
            }
        });

        etComment.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    findDetailPresenter.comment();
                    return true;
                }
                return false;
            }
        });


        findDetailPresenter = new FindDetailPresenter(this);
        findDetailPresenter.getFindDetail();
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
        if (doType == 1) {
            RoundAngleImageView roundAngleImageView = new RoundAngleImageView(mActivity);
            roundAngleImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(dip2px(mActivity, 25), dip2px(mActivity, 25));
            layout.setMargins(dip2px(mActivity, 10), 0, 0, 0);
            roundAngleImageView.setLayoutParams(layout);
            Glide.with(mActivity).load(spUtil.getStringValue("headImg", "")).into(roundAngleImageView);
            llZan.addView(roundAngleImageView);

            tvZanNum.setText("" + (Integer.parseInt(tvZanNum.getText().toString().trim()) + 1));

            tvZan.setCompoundDrawables(drawableZaned, null, null, null);
            tvZan.setBackgroundResource(R.drawable.bg_zaned);
            tvZan.setTextColor(getResources().getColor(R.color.commonWhite));
            tvZan.setPadding(dip2px(mActivity, 10), 0, 0, 0);
        } else if (doType == 5) {
            findDetailPresenter.getFindDetail();
            etComment.setText("");
        } else if (doType == 2) {
            momentDetailInfo.getData().setIsPaid(true);
            imageAdapter.notifyDataSetChanged();
            Intent intent = new Intent(mActivity, ShowPictureActivity.class);
            Bundle bundle = new Bundle();
            int[] location = new int[2];
            curView.getLocationInWindow(location);
            int x = location[0];
            int y = location[1];
            bundle.putInt("x", x);
            bundle.putInt("y", y);

            if (momentDetailInfo.getData().getMomentMedia().getImgs().size() > 4) {
                bundle.putInt("width", threeWidth);
                bundle.putInt("hight", threeWidth);
                bundle.putInt("column_num", 3);
            } else {
                if (momentDetailInfo.getData().getMomentMedia().getImgs().size() == 1) {
                    bundle.putInt("width", curWidth);
                    bundle.putInt("hight", curHeight);
                } else {
                    bundle.putInt("width", twoWidth);
                    bundle.putInt("hight", twoWidth);
                }
                bundle.putInt("column_num", 2);
            }
            Gson gson = new Gson();
            bundle.putString("imgdatas", gson.toJson(momentDetailInfo.getData().getMomentMedia().getImgs()));
            bundle.putInt("position", curPosition);

            bundle.putInt("horizontal_space", NormalUtil.dip2px(mActivity, 8));
            bundle.putInt("vertical_space", NormalUtil.dip2px(mActivity, 8));

            intent.putExtras(bundle);
            startActivity(intent);
        } else if (doType == 3) {
            momentDetailInfo.getData().setIsPaid(true);
            Glide.with(mActivity).load(momentDetailInfo.getData().getMomentMedia().getVideo().getFirstImg()).into(ivFirstImage);
            Intent intent = new Intent(mActivity, VideoPlayActivity.class);
            intent.putExtra("videoPath", momentDetailInfo.getData().getMomentMedia().getVideo().getVideoUrl());
            intent.putExtra("coverPath", momentDetailInfo.getData().getMomentMedia().getVideo().getFirstImg());
            intent.putExtra("fromType", 2);
            startActivity(intent);
        } else if (doType == 4) {
            momentDetailInfo.getData().setIsPaid(true);
            try {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(momentDetailInfo.getData().getMomentMedia().getVoice().getVoiceUrl());
                mediaPlayer.prepare();
                mediaPlayer.start();
                animationDrawable = (AnimationDrawable) ivVoiceAnimation.getDrawable();
                animationDrawable.start();
            } catch (IOException e) {
                Log.e("PublishMomentActivity.class", "prepare() failed");
            }
        }
    }

    @Override
    public void showError(String msg) {
        ToastUtils.ShowError(mActivity, msg);
    }

    @Override
    public void showMoments(final MomentDetailInfo info) {
        momentDetailInfo = info;
        comments.clear();
        comments.addAll(info.getData().getComment());
        if (adapter == null) {
            layoutManager = new LinearLayoutManager(mActivity);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            rvComment.setLayoutManager(layoutManager);
            rvComment.setHasFixedSize(true);
            adapter = new CommonAdapter<MomentDetailInfo.DataBean.CommentBean>(mActivity, R.layout.item_comment, comments) {
                @Override
                protected void convert(ViewHolder holder, MomentDetailInfo.DataBean.CommentBean commentBean, int position) {
                    Glide.with(mActivity).load(commentBean.getHeadImg()).into((RoundAngleImageView) holder.getView(R.id.iv_item_head));

                    holder.setText(R.id.tv_item_name, commentBean.getNickName());

                    holder.setText(R.id.tv_item_time, commentBean.getCreatedAt());

                    holder.setText(R.id.tv_item_content, commentBean.getContent());
                }
            };
            headerAndFooterWrapper = new HeaderAndFooterWrapper(adapter);

            //headview
            View headerView = getLayoutInflater().inflate(R.layout.item_findhot, null);
            ivHead = (RoundAngleImageView) headerView.findViewById(R.id.iv_item_head);
            tvName = (TextView) headerView.findViewById(R.id.tv_item_name);
            tvAge = (TextView) headerView.findViewById(R.id.tv_item_age);
            ivVerify = (ImageView) headerView.findViewById(R.id.iv_item_verify);
            ivVoice = (ImageView) headerView.findViewById(R.id.iv_item_voice);
            ivVideo = (ImageView) headerView.findViewById(R.id.iv_item_video);
            tvGet = (TextView) headerView.findViewById(R.id.tv_get);
            tvPay = (TextView) headerView.findViewById(R.id.tv_pay);
            tvTime = (TextView) headerView.findViewById(R.id.tv_item_time);
            tvContent = (TextView) headerView.findViewById(R.id.tv_item_content);
            tvLocation = (TextView) headerView.findViewById(R.id.tv_item_location);
            tvTopic = (TextView) headerView.findViewById(R.id.tv_item_topic);
            tvZanNum = (TextView) headerView.findViewById(R.id.tv_zan_num);
            tvZan = (TextView) headerView.findViewById(R.id.tv_item_zan);
            llZan = (LinearLayout) headerView.findViewById(R.id.ll_zans);

            tvCommentNum = (TextView) headerView.findViewById(R.id.tv_item_comment_num);
            tvCommentNum.setVisibility(View.GONE);
            tvComment = (TextView) headerView.findViewById(R.id.tv_item_comment);
            tvComment.setVisibility(View.GONE);
            viewBottom = headerView.findViewById(R.id.view_bottom);
            viewBottom.setVisibility(View.GONE);
            tvTosay = (TextView) headerView.findViewById(R.id.tv_to_say);
            tvTosay.setVisibility(View.GONE);

            rlVideo = (RelativeLayout) headerView.findViewById(R.id.rl_item_video);
            tvVoice = (TextView) headerView.findViewById(R.id.tv_item_content_voice);
            rlVoice = (RelativeLayout) headerView.findViewById(R.id.rl_item_voice);
            ivVoiceAnimation = (ImageView) headerView.findViewById(R.id.iv_voice_animation);
            rvImage = (RecyclerView) headerView.findViewById(R.id.rv_images);
            ivFirstImage = (ImageView) headerView.findViewById(R.id.iv_item_firstimage);

            LinearLayout llPay = (LinearLayout) headerView.findViewById(R.id.ll_pay);
            TextView tvPayNum = (TextView) headerView.findViewById(R.id.tv_item_pay_num);
            if (momentDetailInfo.getData().getPaidNum() == 0) {
                llPay.setVisibility(View.GONE);
            } else {
                llPay.setVisibility(View.VISIBLE);
                tvPayNum.setText("已付费观看人数：" + momentDetailInfo.getData().getPaidNum() + "人");
            }

            Glide.with(mActivity).load(momentDetailInfo.getData().getUserInfo().getHeadImg()).into(ivHead);
            tvName.setText(momentDetailInfo.getData().getUserInfo().getNickName());
            if (momentDetailInfo.getData().getUserInfo().getOfficialCer() == 1) {
                ivVerify.setVisibility(View.VISIBLE);
            } else {
                ivVerify.setVisibility(View.GONE);
            }

            if (momentDetailInfo.getData().getUserInfo().getSex() == 1) {
                tvAge.setText("♂ " + momentDetailInfo.getData().getUserInfo().getAge());
                tvAge.setBackgroundResource(R.drawable.bg_sex_man);
            } else {
                tvAge.setText("♀ " + momentDetailInfo.getData().getUserInfo().getAge());
                tvAge.setBackgroundResource(R.drawable.bg_tag_selected);
            }
            if (momentDetailInfo.getData().getUserInfo().getVoiceStatus() == 2) {
                ivVoice.setImageResource(R.mipmap.icon_find_voice);
            } else {
                ivVoice.setImageResource(R.mipmap.icon_find_voice_none);
            }

            if (momentDetailInfo.getData().getUserInfo().getVideoStatus() == 2) {
                ivVideo.setImageResource(R.mipmap.icon_find_video);
            } else {
                ivVideo.setImageResource(R.mipmap.icon_find_video_none);
            }

            tvPay.setText(momentDetailInfo.getData().getUserInfo().getExpenses() + "");
            tvGet.setText(momentDetailInfo.getData().getUserInfo().getIncome() + "");

            tvTime.setText(momentDetailInfo.getData().getCreatedAt());

            if (StringUtil.isEmpty(momentDetailInfo.getData().getContent())) {
                tvContent.setVisibility(View.GONE);
            } else {
                tvContent.setVisibility(View.VISIBLE);
                tvContent.setText(momentDetailInfo.getData().getContent());
            }

            if (StringUtil.isEmpty(momentDetailInfo.getData().getLocation())) {
                tvLocation.setVisibility(View.GONE);
            } else {
                tvLocation.setVisibility(View.VISIBLE);
                tvLocation.setText(momentDetailInfo.getData().getLocation());
            }
            tvTopic.setText("#" + momentDetailInfo.getData().getTopicTags().get(0).getTagName() + "#");

            tvZanNum.setText(momentDetailInfo.getData().getPraiseNum() + "");

            if (momentDetailInfo.getData().isIsPraise()) {
                tvZan.setCompoundDrawables(drawableZaned, null, null, null);
                tvZan.setBackgroundResource(R.drawable.bg_zaned);
                tvZan.setTextColor(getResources().getColor(R.color.commonWhite));
                tvZan.setPadding(dip2px(mActivity, 10), 0, 0, 0);
            } else {
                tvZan.setCompoundDrawables(drawableZan, null, null, null);
                tvZan.setBackgroundResource(R.drawable.bg_zan);
                tvZan.setTextColor(getResources().getColor(R.color.colorBF));
                tvZan.setPadding(dip2px(mActivity, 10), 0, 0, 0);
            }

            tvZan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!momentDetailInfo.getData().isIsPraise()) {
                        findDetailPresenter.praise();
                    }
                }
            });

            int zanNum = 0;
            llZan.removeAllViews();
            for (MomentDetailInfo.DataBean.PraiseListBean data : momentDetailInfo.getData().getPraiseList()) {
                if (zanNum < showZanNum) {
                    RoundAngleImageView roundAngleImageView = new RoundAngleImageView(mActivity);
                    roundAngleImageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(dip2px(mActivity, 25), dip2px(mActivity, 25));
                    layout.setMargins(dip2px(mActivity, 10), 0, 0, 0);
                    roundAngleImageView.setLayoutParams(layout);
                    Glide.with(mActivity).load(data.getHeadImg()).into(roundAngleImageView);
                    llZan.addView(roundAngleImageView);
                }
                zanNum++;
            }

            RecyclerView.LayoutManager mLayoutManager;

            if (momentDetailInfo.getData().getMomentMedia() != null) {
                if (momentDetailInfo.getData().getMomentMedia().getImgs() != null && momentDetailInfo.getData().getMomentMedia().getImgs().size() > 0) {
                    //图片动态
                    rlVideo.setVisibility(View.GONE);
                    rlVoice.setVisibility(View.GONE);
                    rvImage.setVisibility(View.VISIBLE);
//                    ivOneImage.setVisibility(View.GONE);
                    if (momentDetailInfo.getData().getMomentMedia().getImgs().size() > 4) {
                        mLayoutManager = new GridLayoutManager(mActivity, 3);
                    } else {
                        mLayoutManager = new GridLayoutManager(mActivity, 2);
                    }
                    rvImage.setLayoutManager(mLayoutManager);
                    rvImage.setHasFixedSize(true);

                    imageAdapter = new CommonAdapter<String>(mActivity, R.layout.item_find_image, momentDetailInfo.getData().getMomentMedia().getImgs()) {
                        @Override
                        protected void convert(ViewHolder holder, String s, final int position) {
                            ImageView ivVideo = holder.getView(R.id.iv_icon_video);
                            ivVideo.setVisibility(View.GONE);
                            ImageView ivImage = holder.getView(R.id.iv_item_img);
                            int width = 0;
                            int height = 0;
                            String imageUrl = s;
                            RelativeLayout rlAlbum = holder.getView(R.id.rl_album);
                            if (momentDetailInfo.getData().getMomentMedia().getImgs().size() > 4) {
                                threeWidth = AutoViwHeightUtil.setGridItemHeight(mActivity, rlAlbum, 1, 1, dip2px(mActivity, 46), 3);
                                imageUrl = ImageStringUtil.getThumb(imageUrl, threeWidth, threeWidth);
                            } else {
                                if (momentDetailInfo.getData().getMomentMedia().getImgs().size() == 1) {
                                    String[] urls = imageUrl.split("_");
                                    if (urls.length > 3) {
                                        Point sizeData = ImageStringUtil.getThumbSize(mActivity, imageUrl, urls[1] + "x" + urls[2]);
                                        width = sizeData.x;
                                        height = sizeData.y;
                                    }
                                    rlAlbum.getLayoutParams().width = width;
                                    rlAlbum.getLayoutParams().height = height;
                                    imageUrl = ImageStringUtil.getThumb(imageUrl, height, width);
                                } else {
                                    twoWidth = AutoViwHeightUtil.setGridItemHeight(mActivity, rlAlbum, 1, 1, dip2px(mActivity, 38), 2);
                                    imageUrl = ImageStringUtil.getThumb(imageUrl, twoWidth, twoWidth);
                                }
                            }
//                            if (!NormalUtil.isNull(imageUrl) && (BitmapUtil.checkImageExist(imageUrl))) {
//                                ImageLoader.getInstance().displayImage(imageUrl, ivImage);
//                            } else {
//                                ivImage.setImageResource(R.mipmap.page_default_detail);
//                            }
                            if (momentDetailInfo.getData().isIsPaid()) {
                                Glide.with(mActivity).load(imageUrl).into(ivImage);
                            } else {
                                Glide.with(mActivity).load(imageUrl + Const.OSS_IMAGE_BLURRY).into(ivImage);
                            }

                            final int finalWidth = width;
                            final int finalHeight = height;
                            ivImage.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(final View v) {
                                    if (!momentDetailInfo.getData().isIsPaid()) {
                                        commonDialog = new CommonDialog(mActivity, "提示", "是否支付" + momentDetailInfo.getData().getMoney() + "金币观看此动态的所有图片？", "是", "", false, new CommonDialog.OkListener() {
                                            @Override
                                            public void ok() {
                                                doType = 2;
                                                curPosition = position;
                                                curWidth = finalWidth;
                                                curHeight = finalHeight;
                                                curView = v;
                                                findDetailPresenter.buy();
                                            }
                                        });
                                        commonDialog.show();
                                        return;
                                    }
                                    Intent intent = new Intent(mContext, ShowPictureActivity.class);
                                    Bundle bundle = new Bundle();
                                    int[] location = new int[2];
                                    v.getLocationInWindow(location);
                                    int x = location[0];
                                    int y = location[1];
                                    bundle.putInt("x", x);
                                    bundle.putInt("y", y);

                                    if (momentDetailInfo.getData().getMomentMedia().getImgs().size() > 4) {
                                        bundle.putInt("width", threeWidth);
                                        bundle.putInt("hight", threeWidth);
                                        bundle.putInt("column_num", 3);
                                    } else {
                                        if (momentDetailInfo.getData().getMomentMedia().getImgs().size() == 1) {
                                            bundle.putInt("width", finalWidth);
                                            bundle.putInt("hight", finalHeight);
                                        } else {
                                            bundle.putInt("width", twoWidth);
                                            bundle.putInt("hight", twoWidth);
                                        }
                                        bundle.putInt("column_num", 2);
                                    }
                                    Gson gson = new Gson();
                                    bundle.putString("imgdatas", gson.toJson(momentDetailInfo.getData().getMomentMedia().getImgs()));
                                    bundle.putInt("position", position);

                                    bundle.putInt("horizontal_space", NormalUtil.dip2px(mContext, 8));
                                    bundle.putInt("vertical_space", NormalUtil.dip2px(mContext, 8));

                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                }
                            });
//                                        Glide.with(mActivity).load(s).into((ImageView) holder.getView(R.id.iv_item_img));

                        }
                    };

                    rvImage.setAdapter(imageAdapter);

                } else if (info.getData().getMomentType() == 4) {
                    //语音动态
                    rvImage.setVisibility(View.GONE);
//                    ivOneImage.setVisibility(View.GONE);
                    rlVideo.setVisibility(View.GONE);
                    rlVoice.setVisibility(View.VISIBLE);
                    tvVoice.setText(momentDetailInfo.getData().getMomentMedia().getVoice().getVoiceTime() + "s");
                } else if (info.getData().getMomentType() == 3) {
                    //视频动态
                    rvImage.setVisibility(View.GONE);
//                    ivOneImage.setVisibility(View.GONE);
                    rlVideo.setVisibility(View.VISIBLE);
                    rlVoice.setVisibility(View.GONE);
                    if (momentDetailInfo.getData().isIsPaid()) {
                        Glide.with(mActivity).load(momentDetailInfo.getData().getMomentMedia().getVideo().getFirstImg()).into(ivFirstImage);
                    } else {
                        Glide.with(mActivity).load(momentDetailInfo.getData().getMomentMedia().getVideo().getFirstImg() + Const.OSS_IMAGE_BLURRY).into(ivFirstImage);
                    }

                } else {
                    rvImage.setVisibility(View.GONE);
//                    ivOneImage.setVisibility(View.GONE);
                    rlVideo.setVisibility(View.GONE);
                    rlVoice.setVisibility(View.GONE);
                }
            } else {
                rvImage.setVisibility(View.GONE);
//                ivOneImage.setVisibility(View.GONE);
                rlVideo.setVisibility(View.GONE);
                rlVoice.setVisibility(View.GONE);
            }
            rlVoice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //播放语音
                    if (momentDetailInfo.getData().isIsPaid()) {
                        try {
                            mediaPlayer.reset();
                            mediaPlayer.setDataSource(momentDetailInfo.getData().getMomentMedia().getVoice().getVoiceUrl());
                            mediaPlayer.prepare();
                            mediaPlayer.start();
                            animationDrawable = (AnimationDrawable) ivVoiceAnimation.getDrawable();
                            animationDrawable.start();
                        } catch (IOException e) {
                            Log.e("PublishMomentActivity.class", "prepare() failed");
                        }
                    } else {
                        //购买弹窗
                        commonDialog = new CommonDialog(mActivity, "提示", "是否支付" + momentDetailInfo.getData().getMoney() + "金币听此语音？", "是", "", false, new CommonDialog.OkListener() {
                            @Override
                            public void ok() {
                                doType = 4;
                                findDetailPresenter.buy();
                            }
                        });
                        commonDialog.show();
                    }
                }
            });
            rlVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //视频播放
                    if (momentDetailInfo.getData().isIsPaid()) {
                        Intent intent = new Intent(mActivity, VideoPlayActivity.class);
                        intent.putExtra("videoPath", momentDetailInfo.getData().getMomentMedia().getVideo().getVideoUrl());
                        intent.putExtra("coverPath", momentDetailInfo.getData().getMomentMedia().getVideo().getFirstImg());
                        intent.putExtra("fromType", 2);
                        startActivity(intent);
                    } else {
                        //购买弹窗
                        commonDialog = new CommonDialog(mActivity, "提示", "是否支付" + momentDetailInfo.getData().getMoney() + "金币观看此视频？", "是", "", false, new CommonDialog.OkListener() {
                            @Override
                            public void ok() {
                                doType = 3;
                                findDetailPresenter.buy();
                            }
                        });
                        commonDialog.show();
                    }
                }
            });


            headerAndFooterWrapper.addHeaderView(headerView);
            rvComment.setAdapter(headerAndFooterWrapper);
        } else {
            headerAndFooterWrapper.notifyDataSetChanged();
        }
    }

    @Override
    public String getToken() {
        return spUtil.getStringValue("token", "");
    }

    @Override
    public String getMomentId() {
        return momentId + "";
    }

    @Override
    public String getContent() {
        return etComment.getText().toString().trim();
    }
}
