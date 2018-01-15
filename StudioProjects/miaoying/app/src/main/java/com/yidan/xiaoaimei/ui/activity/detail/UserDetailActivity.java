package com.yidan.xiaoaimei.ui.activity.detail;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.miaokong.commonutils.utils.LoadingDialogUtil;
import com.netease.nim.uikit.api.model.gift.GiftListInfo;
import com.yidan.xiaoaimei.R;
import com.yidan.xiaoaimei.base.BaseActivity;
import com.yidan.xiaoaimei.contract.UserDetailContract;
import com.yidan.xiaoaimei.model.detail.UserDetailInfo;
import com.yidan.xiaoaimei.presenter.UserDetailPresenter;
import com.yidan.xiaoaimei.ui.activity.video.VideoPlayActivity;
import com.yidan.xiaoaimei.ui.fragment.detail.DetailAlbumFragment;
import com.yidan.xiaoaimei.ui.fragment.detail.DetailPersonalFragment;
import com.yidan.xiaoaimei.ui.nimutil.session.SessionHelper;
import com.yidan.xiaoaimei.ui.view.MyViewPager;
import com.yidan.xiaoaimei.ui.view.ObservableScrollView;
import com.yidan.xiaoaimei.utils.ToastUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 个人详情
 * Created by jaydenma on 2017/12/11.
 */

public class UserDetailActivity extends BaseActivity implements ObservableScrollView.ScrollViewListener, UserDetailContract.IUserDetailView {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_more)
    ImageView ivMore;
    @BindView(R.id.rl_actionbar)
    RelativeLayout rlActionbar;
    @BindView(R.id.iv_detail_cover)
    ImageView ivDetailCover;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.ll_tablayout)
    LinearLayout llTablayout;
    @BindView(R.id.tv_get)
    TextView tvGet;
    @BindView(R.id.tv_pay)
    TextView tvPay;
    @BindView(R.id.tv_detail_video)
    TextView tvDetailVideo;
    @BindView(R.id.my_viewpager)
    MyViewPager myViewpager;
    @BindView(R.id.my_scrollview)
    ObservableScrollView myScrollview;
    @BindView(R.id.tablayout_top)
    TabLayout tablayoutTop;
    @BindView(R.id.ll_tablayout_top)
    LinearLayout llTablayoutTop;
    @BindView(R.id.rl_chat)
    RelativeLayout rlChat;
    @BindView(R.id.rl_comment)
    RelativeLayout rlComment;
    @BindView(R.id.tv_attention)
    TextView tvAttention;
    @BindView(R.id.rl_attention)
    RelativeLayout rlAttention;
    @BindView(R.id.tv_detail_voice_time)
    TextView tvDetailVoiceTime;
    @BindView(R.id.iv_voice_animation)
    ImageView ivVoiceAnimation;
    @BindView(R.id.ll_detail_voice)
    LinearLayout llDetailVoice;


    @OnClick({R.id.iv_back,
            R.id.iv_more,
            R.id.tv_detail_video,
            R.id.ll_detail_voice,
            R.id.rl_chat,
            R.id.rl_comment,
            R.id.rl_attention})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_more:

                break;
            case R.id.tv_detail_video:
                //播放认证视频
                Intent intent = new Intent(mActivity, VideoPlayActivity.class);
                intent.putExtra("videoPath", userDetailInfo.getData().getVideoCer().getVideoUrl());
                intent.putExtra("coverPath", userDetailInfo.getData().getVideoCer().getFirstImg());
                intent.putExtra("fromType", 2);
                startActivity(intent);
                break;
            case R.id.ll_detail_voice:
                //播放认证语音
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                    animationDrawable = (AnimationDrawable) ivVoiceAnimation.getDrawable();
                    animationDrawable.start();
                } else {
                    ToastUtils.ShowError(mActivity, "正在播放语音");
                }
                break;
            case R.id.rl_chat:
                //聊天
                SessionHelper.startP2PSession(mActivity, uid, gifts);
                break;
            case R.id.rl_comment:
                //评价
                Intent commentIntent = new Intent(mActivity, EvaluateActivity.class);
                commentIntent.putExtra("uid", uid);
                startActivity(commentIntent);
                break;
            case R.id.rl_attention:
                //关注
                userDetailPresenter.focus();
                break;
            default:
                break;
        }
    }

    private int topHeight;

    private List<Fragment> fragments = new ArrayList<Fragment>();

    private FragmentAdapter adapter;

    private DetailPersonalFragment detailPersonalFragment;

    private DetailPersonalFragment detailMomentFragment;

    private DetailAlbumFragment detailAlbumFragment;

    private DetailAlbumFragment detailVideoFragment;

    private String[] titles = {"资料", "动态", "相册", "视频"};

    private UserDetailPresenter userDetailPresenter;

    private Dialog loadingDialog;

    private String uid;

    private UserDetailInfo userDetailInfo = new UserDetailInfo();

    private Gson gson = new Gson();
    private List<Integer> userIds = new ArrayList<Integer>();

    private Drawable drawableAttention, drawableAttentioned;

    private MediaPlayer mediaPlayer;

    private AnimationDrawable animationDrawable;

    private List<GiftListInfo.DataBean> gifts = new ArrayList<GiftListInfo.DataBean>();

    @Override
    public int getLayoutResId() {
        return R.layout.activity_user_detail;
    }

    @Override
    public void initData() {
        super.initData();
        uid = getIntent().getStringExtra("uid");
        drawableAttention = getResources().getDrawable(R.mipmap.icon_detail_attention);
        drawableAttention.setBounds(0, 0, drawableAttention.getMinimumWidth(), drawableAttention.getMinimumHeight());
        drawableAttentioned = getResources().getDrawable(R.mipmap.icon_home_attentioned);
        drawableAttentioned.setBounds(0, 0, drawableAttentioned.getMinimumWidth(), drawableAttentioned.getMinimumHeight());

        //播放语音
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                animationDrawable.stop();
                ivVoiceAnimation.setImageResource(R.drawable.voice_animation);
            }
        });

        myScrollview.setScrollViewListener(this);

        detailPersonalFragment = new DetailPersonalFragment();

        detailMomentFragment = new DetailPersonalFragment();

        detailAlbumFragment = new DetailAlbumFragment(uid, 0);

        detailVideoFragment = new DetailAlbumFragment(uid, 1);

        fragments.add(detailPersonalFragment);
        fragments.add(detailMomentFragment);
        fragments.add(detailAlbumFragment);
        fragments.add(detailVideoFragment);

        adapter = new FragmentAdapter(getSupportFragmentManager(), fragments);
        myViewpager.setAdapter(adapter);
        myViewpager.setCurrentItem(0, true);
        myViewpager.setOffscreenPageLimit(4);

        tablayout.setupWithViewPager(myViewpager);
        tablayoutTop.setupWithViewPager(myViewpager);

        for (int i = 0; i < adapter.getCount(); i++) {
            TabLayout.Tab tab = tablayout.getTabAt(i);//获得每一个tab
            TabLayout.Tab tabTop = tablayoutTop.getTabAt(i);//获得每一个tab
            tab.setCustomView(R.layout.item_detail_tablayout);//给每一个tab设置view
            tabTop.setCustomView(R.layout.item_detail_tablayout);//给每一个tab设置view
            if (i == 0) {
                // 设置第一个tab的TextView是被选择的样式
                tab.getCustomView().findViewById(R.id.tab_text).setSelected(true);//第一个tab被选中
                tabTop.getCustomView().findViewById(R.id.tab_text).setSelected(true);//第一个tab被选中
            }
            TextView textView = (TextView) tab.getCustomView().findViewById(R.id.tab_text);
            textView.setText(titles[i]);//设置tab上的文字
            TextView textViewTop = (TextView) tabTop.getCustomView().findViewById(R.id.tab_text);
            textViewTop.setText(titles[i]);//设置tab上的文字
        }
        tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getCustomView().findViewById(R.id.tab_text).setSelected(true);
                myViewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getCustomView().findViewById(R.id.tab_text).setSelected(false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tablayoutTop.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getCustomView().findViewById(R.id.tab_text).setSelected(true);
                myViewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getCustomView().findViewById(R.id.tab_text).setSelected(false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        userDetailPresenter = new UserDetailPresenter(this);
        userDetailPresenter.getUserDetail();
        userDetailPresenter.getGifts();

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Rect frame = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;//状态栏高度
        int titleBarHeight = rlActionbar.getHeight();
        topHeight = titleBarHeight + statusBarHeight;
    }


    @Override
    public void onScroll(int oldy, int dy, boolean isUp) {

    }

    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        int[] location = new int[2];
        tablayout.getLocationOnScreen(location);
        int locationY = location[1];
//        Log.e("locationY", locationY + "   " + "topHeight的值是：" + topHeight);

        if (locationY <= topHeight && (llTablayoutTop.getVisibility() == View.GONE || llTablayoutTop.getVisibility() == View.INVISIBLE)) {
            llTablayoutTop.setVisibility(View.VISIBLE);
        }

        if (locationY > topHeight && llTablayoutTop.getVisibility() == View.VISIBLE) {
            llTablayoutTop.setVisibility(View.GONE);
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
        userDetailInfo.getData().setIsFollowed(userDetailInfo.getData().isIsFollowed() ? false : true);
        if (userDetailInfo.getData().isIsFollowed()) {
            tvAttention.setCompoundDrawables(null, drawableAttentioned, null, null);
            tvAttention.setText("已关注");
        } else {
            tvAttention.setCompoundDrawables(null, drawableAttention, null, null);
            tvAttention.setText("关注");
        }
        ToastUtils.ShowSucess(mActivity, info);
    }

    @Override
    public void showError(String msg) {
        ToastUtils.ShowError(mActivity, msg);
    }

    @Override
    public void showUserDetail(UserDetailInfo info) {
        userDetailInfo = info;
        tvTitle.setText(info.getData().getNickName());
        Glide.with(mActivity).load(info.getData().getHeadImg()).into(ivDetailCover);
        tvGet.setText(info.getData().getIncome() + "");
        tvPay.setText(info.getData().getExpenses() + "");

        if (userDetailInfo.getData().isIsFollowed()) {
            tvAttention.setCompoundDrawables(null, drawableAttentioned, null, null);
            tvAttention.setText("已关注");
        } else {
            tvAttention.setCompoundDrawables(null, drawableAttention, null, null);
            tvAttention.setText("关注");
        }

        if (info.getData().getVideoCer() != null) {
            tvDetailVideo.setVisibility(View.VISIBLE);
            tvDetailVideo.setText(info.getData().getVideoCer().getVideoTime() + "s");
        } else {
            tvDetailVideo.setVisibility(View.GONE);
        }
        if (info.getData().getVoiceCer() != null) {
            tvDetailVoiceTime.setText(info.getData().getVoiceCer().getVoiceTime() + "s");
            llDetailVoice.setVisibility(View.VISIBLE);
            try {
                mediaPlayer.setDataSource(userDetailInfo.getData().getVoiceCer().getVoiceUrl());
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            llDetailVoice.setVisibility(View.GONE);
        }
        detailPersonalFragment.setData(info.getData());


    }

    @Override
    public String getToken() {
        return spUtil.getStringValue("token", "");
    }

    @Override
    public String getFocusType() {
        if (userDetailInfo.getData().isIsFollowed()) {
            return "0";
        } else {
            return "1";
        }
    }

    @Override
    public String getUid() {
        return uid;
    }

    @Override
    public String getFocusUid() {
        userIds.clear();
        userIds.add(userDetailInfo.getData().getUid());
        return gson.toJson(userIds);
    }

    @Override
    public void showGifts(GiftListInfo info) {
        gifts = info.getData();
    }

    class FragmentAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragmentList;

        public FragmentAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

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
    protected void onResume() {
        super.onResume();
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
    }
}
