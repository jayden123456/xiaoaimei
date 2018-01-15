package com.yidan.xiaoaimei.ui.fragment.find;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.miaokong.commonutils.utils.AutoViwHeightUtil;
import com.miaokong.commonutils.utils.LoadingDialogUtil;
import com.miaokong.commonutils.utils.StringUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yidan.xiaoaimei.Const;
import com.yidan.xiaoaimei.R;
import com.yidan.xiaoaimei.SystemConfig;
import com.yidan.xiaoaimei.base.BaseFragment;
import com.yidan.xiaoaimei.base.adapterutils.SpacesItemDecoration;
import com.yidan.xiaoaimei.contract.FindContract;
import com.yidan.xiaoaimei.model.find.MomentListInfo;
import com.yidan.xiaoaimei.presenter.FindPresenter;
import com.yidan.xiaoaimei.ui.activity.CommonWebActivity;
import com.yidan.xiaoaimei.ui.activity.moment.MomentDetailActivity;
import com.yidan.xiaoaimei.ui.activity.showPicture.ShowPictureActivity;
import com.yidan.xiaoaimei.ui.activity.video.VideoPlayActivity;
import com.yidan.xiaoaimei.ui.dialog.CommonDialog;
import com.yidan.xiaoaimei.ui.view.RoundAngleImageView;
import com.yidan.xiaoaimei.utils.BitmapUtil;
import com.yidan.xiaoaimei.utils.ImageStringUtil;
import com.yidan.xiaoaimei.utils.NormalUtil;
import com.yidan.xiaoaimei.utils.ToastUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;
import com.zhy.adapter.recyclerview.wrapper.LoadMoreWrapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static android.R.attr.width;
import static android.media.CamcorderProfile.get;
import static cn.finalteam.toolsfinal.DeviceUtils.dip2px;
import static cn.finalteam.toolsfinal.DeviceUtils.px2dip;
import static com.miaokong.commonutils.utils.AutoViwHeightUtil.setGridItemHeight;

/**
 * 发现热门/最新/关注
 * Created by jaydenma on 2017/12/19.
 */

@SuppressLint("ValidFragment")
public class FindHotFragment extends BaseFragment implements FindContract.IFindView, OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.rv_find)
    RecyclerView rvFind;
    @BindView(R.id.swiperefreshlayout)
    SwipeRefreshLayout swiperefreshlayout;

    private int type;

    private Dialog loadingDialog;

    private FindPresenter findPresenter;

    private HeaderAndFooterWrapper headerAndFooterWrapper;

    private ConvenientBanner cBanner;

    private LinearLayoutManager layoutManager;

    private CommonAdapter<MomentListInfo.DataBean.ListBean> adapter;

    private List<MomentListInfo.DataBean.ListBean> moments = new ArrayList<MomentListInfo.DataBean.ListBean>();

    private MomentListInfo momentListInfo = new MomentListInfo();

    private LoadMoreWrapper loadMoreWrapper;

    private ProgressBar loadingView;

    private TextView tvLoadmore;

    private int pageNum = 1; //分页

    private int allPageNum = 1;

    private int curPosition, curPosition2;

    private int curWidth, curHeight;

    private String curMomentId;

    private View curView;

    // 标志位，标志已经初始化完成。
    private boolean isPrepared = false;

    private boolean isFirstIn = true;  //首次进入加载数据

    private Drawable drawableZan, drawableZaned;

    private int showZanNum = 0;

    private CommonAdapter<String> imageAdapter;

    private int twoWidth, threeWidth;

    private int screenWidth, screenHeigh;

    private int doType = 0;//1:点赞，2:购买图片动态，3:购买视频动态，4:购买语音动态

    private CommonDialog commonDialog;

    private AnimationDrawable animationDrawable;

    private ImageView mivVoiceAnimation;

    private MediaPlayer mediaPlayer;

    public FindHotFragment(int type) {
        this.type = type;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_findhot;
    }

    @Override
    public void initData() {
        super.initData();
        swiperefreshlayout.setOnRefreshListener(this);

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
                mivVoiceAnimation.setImageResource(R.drawable.voice_animation);
            }
        });


        findPresenter = new FindPresenter(this);
        isPrepared = true;
        if (type == 1) {
            findPresenter.getFind();
        }
    }

    @Override
    protected void lazyLoad() {
        if (type != 1) {
            if (isFirstIn && isPrepared && isVisible) {
                isFirstIn = false;
                findPresenter.getFind();
            }
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
        if (doType == 1) {
            if (type == 1) {
                moments.get(curPosition - 1).setIsPraise(true);
                List<MomentListInfo.DataBean.ListBean.PraiseListBean> mPraises = moments.get(curPosition - 1).getPraiseList();
                mPraises.add(new MomentListInfo.DataBean.ListBean.PraiseListBean(Integer.parseInt(spUtil.getStringValue("userId", "")), spUtil.getStringValue("headImg", "")));
                moments.get(curPosition - 1).setPraiseList(mPraises);
                moments.get(curPosition - 1).setPraiseNum(moments.get(curPosition - 1).getPraiseNum() + 1);
            } else {
                moments.get(curPosition).setIsPraise(true);
                List<MomentListInfo.DataBean.ListBean.PraiseListBean> mPraises = moments.get(curPosition).getPraiseList();
                mPraises.add(new MomentListInfo.DataBean.ListBean.PraiseListBean(Integer.parseInt(spUtil.getStringValue("userId", "")), spUtil.getStringValue("headImg", "")));
                moments.get(curPosition).setPraiseList(mPraises);
                moments.get(curPosition).setPraiseNum(moments.get(curPosition).getPraiseNum() + 1);
            }
            loadMoreWrapper.notifyDataSetChanged();
        } else if (doType == 2) {
            moments.get(type == 1 ? curPosition - 1 : curPosition).setIsPaid(true);
            Intent intent = new Intent(mActivity, ShowPictureActivity.class);
            Bundle bundle = new Bundle();
            int[] location = new int[2];
            curView.getLocationInWindow(location);
            int x = location[0];
            int y = location[1];
            bundle.putInt("x", x);
            bundle.putInt("y", y);

            if (moments.get(type == 1 ? curPosition - 1 : curPosition).getMomentMedia().getImgs().size() > 4) {
                bundle.putInt("width", threeWidth);
                bundle.putInt("hight", threeWidth);
                bundle.putInt("column_num", 3);
            } else {
                if (moments.get(type == 1 ? curPosition - 1 : curPosition).getMomentMedia().getImgs().size() == 1) {
                    bundle.putInt("width", curWidth);
                    bundle.putInt("hight", curHeight);
                } else {
                    bundle.putInt("width", twoWidth);
                    bundle.putInt("hight", twoWidth);
                }
                bundle.putInt("column_num", 2);
            }
            Gson gson = new Gson();
            bundle.putString("imgdatas", gson.toJson(moments.get(type == 1 ? curPosition - 1 : curPosition).getMomentMedia().getImgs()));
            bundle.putInt("position", curPosition2);

            bundle.putInt("horizontal_space", NormalUtil.dip2px(mActivity, 8));
            bundle.putInt("vertical_space", NormalUtil.dip2px(mActivity, 8));

            intent.putExtras(bundle);
            startActivity(intent);
            loadMoreWrapper.notifyDataSetChanged();
        } else if (doType == 3) {
            moments.get(type == 1 ? curPosition - 1 : curPosition).setIsPaid(true);
            Intent intent = new Intent(mActivity, VideoPlayActivity.class);
            intent.putExtra("videoPath", moments.get(type == 1 ? curPosition - 1 : curPosition).getMomentMedia().getVideo().getVideoUrl());
            intent.putExtra("coverPath", moments.get(type == 1 ? curPosition - 1 : curPosition).getMomentMedia().getVideo().getFirstImg());
            intent.putExtra("fromType", 2);
            startActivity(intent);
            loadMoreWrapper.notifyDataSetChanged();
        } else {
            moments.get(type == 1 ? curPosition - 1 : curPosition).setIsPaid(true);
            loadMoreWrapper.notifyDataSetChanged();
            try {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(moments.get(type == 1 ? curPosition - 1 : curPosition).getMomentMedia().getVoice().getVoiceUrl());
                mediaPlayer.prepare();
                mediaPlayer.start();
                animationDrawable = (AnimationDrawable) mivVoiceAnimation.getDrawable();
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
    public void showMoments(final MomentListInfo info) {
        momentListInfo = info;
        if (info.getData().getList() != null && info.getData().getList().size() > 0) {
            moments.addAll(info.getData().getList());
            if (pageNum == 1) {
                if (tvLoadmore != null) {
                    if (allPageNum > 1) {
                        tvLoadmore.setVisibility(View.GONE);
                        loadingView.setVisibility(View.VISIBLE);
                    }
                }
                swiperefreshlayout.setRefreshing(false);

                if (adapter != null) {
                    loadMoreWrapper.notifyDataSetChanged();
                }
            } else {
                loadMoreWrapper.notifyDataSetChanged();
            }
        } else {
            if (pageNum != 1) {
//                ToastUtil.showToast(activity, "我也是有底线的！");
                tvLoadmore.setText("--------我也是有底线的--------");
                tvLoadmore.setVisibility(View.VISIBLE);
                loadingView.setVisibility(View.GONE);
                pageNum--;
                allPageNum = pageNum;
            } else {
                //页面空

            }
        }
        if (adapter == null) {
            layoutManager = new LinearLayoutManager(mActivity);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            rvFind.addItemDecoration(new SpacesItemDecoration(false, 0, 0, dip2px(mActivity, 10), 0));
            rvFind.setLayoutManager(layoutManager);
            rvFind.setHasFixedSize(true);
            adapter = new CommonAdapter<MomentListInfo.DataBean.ListBean>(mActivity, R.layout.item_findhot, moments) {
                @Override
                protected void convert(final ViewHolder holder, final MomentListInfo.DataBean.ListBean listBean, final int position) {

                    Glide.with(mActivity).load(listBean.getUserInfo().getHeadImg()).into((RoundAngleImageView) holder.getView(R.id.iv_item_head));

                    holder.setText(R.id.tv_item_name, listBean.getUserInfo().getNickName());


                    if (listBean.getUserInfo().getOfficialCer() == 1) {
                        holder.getView(R.id.iv_item_verify).setVisibility(View.VISIBLE);
                    } else {
                        holder.getView(R.id.iv_item_verify).setVisibility(View.GONE);
                    }

                    TextView tvAge = holder.getView(R.id.tv_item_age);
                    if (listBean.getUserInfo().getSex() == 1) {
                        tvAge.setText("♂ " + listBean.getUserInfo().getAge());
                        tvAge.setBackgroundResource(R.drawable.bg_sex_man);
                    } else {
                        tvAge.setText("♀ " + listBean.getUserInfo().getAge());
                        tvAge.setBackgroundResource(R.drawable.bg_tag_selected);
                    }

                    LinearLayout llPay = holder.getView(R.id.ll_pay);
                    if (listBean.getPaidNum() == 0) {
                        llPay.setVisibility(View.GONE);
                    } else {
                        llPay.setVisibility(View.VISIBLE);
                        holder.setText(R.id.tv_item_pay_num, "已付费观看人数：" + listBean.getPaidNum() + "人");
                    }

                    ImageView ivVoice = holder.getView(R.id.iv_item_voice);
                    ImageView ivVideo = holder.getView(R.id.iv_item_video);
                    if (listBean.getUserInfo().getVoiceStatus() == 2) {
                        ivVoice.setImageResource(R.mipmap.icon_find_voice);
                    } else {
                        ivVoice.setImageResource(R.mipmap.icon_find_voice_none);
                    }

                    if (listBean.getUserInfo().getVideoStatus() == 2) {
                        ivVideo.setImageResource(R.mipmap.icon_find_video);
                    } else {
                        ivVideo.setImageResource(R.mipmap.icon_find_video_none);
                    }

                    holder.setText(R.id.tv_get, listBean.getUserInfo().getIncome() + "");
                    holder.setText(R.id.tv_pay, listBean.getUserInfo().getExpenses() + "");

                    holder.setText(R.id.tv_item_time, listBean.getCreatedAt());

                    if (StringUtil.isEmpty(listBean.getContent())) {
                        holder.getView(R.id.tv_item_content).setVisibility(View.GONE);
                    } else {
                        holder.getView(R.id.tv_item_content).setVisibility(View.VISIBLE);
                        holder.setText(R.id.tv_item_content, listBean.getContent());
                    }
                    TextView tvLocation = holder.getView(R.id.tv_item_location);
                    if (StringUtil.isEmpty(listBean.getLocation())) {
                        tvLocation.setVisibility(View.GONE);
                    } else {
                        tvLocation.setVisibility(View.VISIBLE);
                        tvLocation.setText(listBean.getLocation());
                    }
                    holder.setText(R.id.tv_item_topic, "#" + listBean.getTopicTags().get(0).getTagName() + "#");

                    holder.setText(R.id.tv_zan_num, listBean.getPraiseNum() + "");
                    TextView tvZan = holder.getView(R.id.tv_item_zan);

                    if (listBean.isIsPraise()) {
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
                            if (!listBean.isIsPraise()) {
                                doType = 1;
                                curPosition = position;
                                curMomentId = listBean.getMomentId() + "";
                                findPresenter.praise();
                            }
                        }
                    });

                    holder.setText(R.id.tv_item_comment_num, "全部" + listBean.getCommentNum() + "条评论");

                    LinearLayout llZan = holder.getView(R.id.ll_zans);
                    int zanNum = 0;
                    llZan.removeAllViews();
                    for (MomentListInfo.DataBean.ListBean.PraiseListBean data : listBean.getPraiseList()) {
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

                    TextView tvComment = holder.getView(R.id.tv_item_comment);
                    if (listBean.getCommentNum() == 0) {
                        holder.getView(R.id.tv_item_comment_num).setVisibility(View.GONE);
                        tvComment.setVisibility(View.GONE);
                    } else {
                        int commentCount = 0;
                        String commentStr = "";
                        holder.getView(R.id.tv_item_comment_num).setVisibility(View.VISIBLE);
                        tvComment.setVisibility(View.VISIBLE);
                        for (MomentListInfo.DataBean.ListBean.CommentBean commentBean : listBean.getComment()) {
                            if (commentCount < 3) {
                                if (commentCount == 0) {
                                    commentStr += commentBean.getNickName() + ":<font color=\"#9A9A9A\">" + commentBean.getContent() + "</font>";
                                } else {
                                    commentStr += "<br />" + commentBean.getNickName() + ":<font color=\"#9A9A9A\">" + commentBean.getContent() + "</font>";
                                }
                            }
                            commentCount++;
                        }
                        tvComment.setText(Html.fromHtml(commentStr));
                    }


                    RelativeLayout rlVideo = holder.getView(R.id.rl_item_video);
                    TextView tvVoice = holder.getView(R.id.tv_item_content_voice);
                    RelativeLayout rlVoice = holder.getView(R.id.rl_item_voice);
                    ImageView ivOneImage = holder.getView(R.id.iv_item_oneimage);
                    RecyclerView rvImage = holder.getView(R.id.rv_images);

                    RecyclerView.LayoutManager mLayoutManager;

                    if (listBean.getMomentMedia() != null) {
                        if (listBean.getMomentMedia().getImgs() != null && listBean.getMomentMedia().getImgs().size() > 0) {
                            //图片动态
                            rlVideo.setVisibility(View.GONE);
                            rlVoice.setVisibility(View.GONE);
                            rvImage.setVisibility(View.VISIBLE);
                            ivOneImage.setVisibility(View.GONE);
                            if (listBean.getMomentMedia().getImgs().size() > 4) {
                                mLayoutManager = new GridLayoutManager(mActivity, 3);
                            } else {
                                mLayoutManager = new GridLayoutManager(mActivity, 2);
                            }
//                                rvImage.addItemDecoration(new SpacesItemDecoration(false, dip2px(mActivity, 4), dip2px(mActivity, 4), 0, 0));

                            rvImage.setLayoutManager(mLayoutManager);
                            rvImage.setHasFixedSize(true);

                            imageAdapter = new CommonAdapter<String>(mActivity, R.layout.item_find_image, listBean.getMomentMedia().getImgs()) {
                                @Override
                                protected void convert(ViewHolder holder, String s, final int position2) {
                                    ImageView ivVideo = holder.getView(R.id.iv_icon_video);
                                    ivVideo.setVisibility(View.GONE);
                                    ImageView ivImage = holder.getView(R.id.iv_item_img);
                                    int width = 0;
                                    int height = 0;
                                    String imageUrl = s;
                                    RelativeLayout rlAlbum = holder.getView(R.id.rl_album);
                                    if (listBean.getMomentMedia().getImgs().size() > 4) {
                                        threeWidth = AutoViwHeightUtil.setGridItemHeight(mActivity, rlAlbum, 1, 1, dip2px(mActivity, 46), 3);
                                        imageUrl = ImageStringUtil.getThumb(imageUrl, threeWidth, threeWidth);
                                    } else {
                                        if (listBean.getMomentMedia().getImgs().size() == 1) {
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
//                                    if (!NormalUtil.isNull(imageUrl) && (BitmapUtil.checkImageExist(imageUrl))) {
//                                        ImageLoader.getInstance().displayImage(imageUrl, ivImage);
//                                    } else {
//                                        ivImage.setImageResource(R.mipmap.page_default_detail);
//                                    }
                                    if (listBean.isIsPaid()) {
                                        Glide.with(mActivity).load(imageUrl).into(ivImage);
                                    } else {
                                        Glide.with(mActivity).load(imageUrl + Const.OSS_IMAGE_BLURRY).into(ivImage);
                                    }
                                    final int finalWidth = width;
                                    final int finalHeight = height;
                                    ivImage.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(final View v) {
                                            if (!listBean.isIsPaid()) {
                                                commonDialog = new CommonDialog(mActivity, "提示", "是否支付" + listBean.getMoney() + "金币观看此动态的所有图片？", "是", "", false, new CommonDialog.OkListener() {
                                                    @Override
                                                    public void ok() {
                                                        doType = 2;
                                                        curPosition = position;
                                                        curPosition2 = position2;
                                                        curWidth = finalWidth;
                                                        curHeight = finalHeight;
                                                        curView = v;
                                                        curMomentId = listBean.getMomentId() + "";
                                                        findPresenter.buy();
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

                                            if (listBean.getMomentMedia().getImgs().size() > 4) {
                                                bundle.putInt("width", threeWidth);
                                                bundle.putInt("hight", threeWidth);
                                                bundle.putInt("column_num", 3);
                                            } else {
                                                if (listBean.getMomentMedia().getImgs().size() == 1) {
                                                    bundle.putInt("width", finalWidth);
                                                    bundle.putInt("hight", finalHeight);
                                                } else {
                                                    bundle.putInt("width", twoWidth);
                                                    bundle.putInt("hight", twoWidth);
                                                }
                                                bundle.putInt("column_num", 2);
                                            }
                                            Gson gson = new Gson();
                                            bundle.putString("imgdatas", gson.toJson(listBean.getMomentMedia().getImgs()));
                                            bundle.putInt("position", position2);

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

                        } else if (listBean.getMomentType() == 4) {
                            //语音动态
                            rvImage.setVisibility(View.GONE);
                            ivOneImage.setVisibility(View.GONE);
                            rlVideo.setVisibility(View.GONE);
                            rlVoice.setVisibility(View.VISIBLE);
                            tvVoice.setText(listBean.getMomentMedia().getVoice().getVoiceTime() + "s");
                        } else if (listBean.getMomentType() == 3) {
                            //视频动态
                            rvImage.setVisibility(View.GONE);
                            ivOneImage.setVisibility(View.GONE);
                            rlVideo.setVisibility(View.VISIBLE);
                            rlVoice.setVisibility(View.GONE);
                            if (listBean.isIsPaid()) {
                                Glide.with(mActivity).load(listBean.getMomentMedia().getVideo().getFirstImg()).into((ImageView) holder.getView(R.id.iv_item_firstimage));
                            } else {
                                Glide.with(mActivity).load(listBean.getMomentMedia().getVideo().getFirstImg() + Const.OSS_IMAGE_BLURRY).into((ImageView) holder.getView(R.id.iv_item_firstimage));
                            }
                        } else {
                            rvImage.setVisibility(View.GONE);
                            ivOneImage.setVisibility(View.GONE);
                            rlVideo.setVisibility(View.GONE);
                            rlVoice.setVisibility(View.GONE);
                        }
                    } else {
                        rvImage.setVisibility(View.GONE);
                        ivOneImage.setVisibility(View.GONE);
                        rlVideo.setVisibility(View.GONE);
                        rlVoice.setVisibility(View.GONE);
                    }

                    rlVoice.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //播放语音
                            if (listBean.isIsPaid()) {
                                mivVoiceAnimation = holder.getView(R.id.iv_voice_animation);
                                try {
                                    mediaPlayer.reset();
                                    mediaPlayer.setDataSource(listBean.getMomentMedia().getVoice().getVoiceUrl());
                                    mediaPlayer.prepare();
                                    mediaPlayer.start();
                                    animationDrawable = (AnimationDrawable) mivVoiceAnimation.getDrawable();
                                    animationDrawable.start();
                                } catch (IOException e) {
                                    Log.e("PublishMomentActivity.class", "prepare() failed");
                                }
                            } else {
                                //购买弹窗
                                commonDialog = new CommonDialog(mActivity, "提示", "是否支付" + listBean.getMoney() + "金币听此语音？", "是", "", false, new CommonDialog.OkListener() {
                                    @Override
                                    public void ok() {
                                        doType = 4;
                                        mivVoiceAnimation = holder.getView(R.id.iv_voice_animation);
                                        curPosition = position;
                                        curMomentId = listBean.getMomentId() + "";
                                        findPresenter.buy();
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
                            if (listBean.isIsPaid()) {
                                Intent intent = new Intent(mActivity, VideoPlayActivity.class);
                                intent.putExtra("videoPath", listBean.getMomentMedia().getVideo().getVideoUrl());
                                intent.putExtra("coverPath", listBean.getMomentMedia().getVideo().getFirstImg());
                                intent.putExtra("fromType", 2);
                                startActivity(intent);
                            } else {
                                //购买弹窗
                                commonDialog = new CommonDialog(mActivity, "提示", "是否支付" + listBean.getMoney() + "金币观看此视频？", "是", "", false, new CommonDialog.OkListener() {
                                    @Override
                                    public void ok() {
                                        doType = 3;
                                        curPosition = position;
                                        curMomentId = listBean.getMomentId() + "";
                                        findPresenter.buy();
                                    }
                                });
                                commonDialog.show();
                            }
                        }
                    });


                }
            };
            adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                    //动态详情
                    Intent intent = new Intent(mActivity, MomentDetailActivity.class);
                    if (type == 1) {
                        intent.putExtra("momentId", moments.get(position - 1).getMomentId());
                    } else {
                        intent.putExtra("momentId", moments.get(position).getMomentId());
                    }
                    startActivity(intent);
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });
            if (type == 1) {
                headerAndFooterWrapper = new HeaderAndFooterWrapper(adapter);
                //headview
                View headerView = mActivity.getLayoutInflater().inflate(R.layout.head_common_banner, null);
                cBanner = (ConvenientBanner) headerView.findViewById(R.id.cbanner);
                AutoViwHeightUtil.setViewHeight(mActivity, cBanner, 720, 336, dip2px(mActivity, 15));
                headerAndFooterWrapper.addHeaderView(headerView);
                loadMoreWrapper = new LoadMoreWrapper(headerAndFooterWrapper);
                View view = View.inflate(mActivity, R.layout.defaut_loading, null);
                //要设置一下的布局参数,因为布局填充到包装器的时候,自己的一些属性会无效
                LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                view.setLayoutParams(mLayoutParams);
                loadingView = (ProgressBar) view.findViewById(R.id.pb_loading);
                tvLoadmore = (TextView) view.findViewById(R.id.loading_text);
                tvLoadmore.setVisibility(View.GONE);
                loadMoreWrapper.setLoadMoreView(view);
                loadMoreWrapper.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
                    @Override
                    public void onLoadMoreRequested() {
                        pageNum++;
                        findPresenter.getFind();
                    }
                });
                cBanner.startTurning(2000);
                initBanner();
            } else {
                loadMoreWrapper = new LoadMoreWrapper(adapter);
                View view = View.inflate(mActivity, R.layout.defaut_loading, null);
                //要设置一下的布局参数,因为布局填充到包装器的时候,自己的一些属性会无效
                LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                view.setLayoutParams(mLayoutParams);
                loadingView = (ProgressBar) view.findViewById(R.id.pb_loading);
//            loadingView.startAnim();
                tvLoadmore = (TextView) view.findViewById(R.id.loading_text);
                tvLoadmore.setVisibility(View.GONE);
                loadMoreWrapper.setLoadMoreView(view);
                loadMoreWrapper.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
                    @Override
                    public void onLoadMoreRequested() {
//                    if (onRefresh == 0) {
                        pageNum++;
                        findPresenter.getFind();
//                    }
                    }
                });
            }
            rvFind.setAdapter(loadMoreWrapper);

        }
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
    public int getPageNum() {
        return pageNum;
    }

    @Override
    public String getMomentId() {
        return curMomentId;
    }


    /**
     * banner设置
     */
    private void initBanner() {
        cBanner.setPages(
                new CBViewHolderCreator<ImageHolderView>() {
                    @Override
                    public ImageHolderView createHolder() {
                        return new ImageHolderView();
                    }
                }, momentListInfo.getData().getBanner())
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
                .setOnItemClickListener(this)
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(mActivity, CommonWebActivity.class);
        intent.putExtra("url", momentListInfo.getData().getBanner().get(position).getJumpUrl());
        intent.putExtra("title", momentListInfo.getData().getBanner().get(position).getTitle());
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        moments.clear();
        pageNum = 1;
        findPresenter.getFind();
    }

    public class ImageHolderView implements Holder<MomentListInfo.DataBean.BannerBean> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, final int position, MomentListInfo.DataBean.BannerBean data) {
            Glide.with(mActivity).load(data.getBannerUrl()).into(imageView);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (cBanner != null) {
            cBanner.stopTurning();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (cBanner != null) {
            cBanner.stopTurning();
        }
    }

}
