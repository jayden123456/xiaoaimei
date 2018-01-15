package com.yidan.xiaoaimei.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.miaokong.commonutils.utils.DoubleClickExitHelper;
import com.miaokong.commonutils.utils.ScreenUtils;
import com.yidan.xiaoaimei.R;
import com.yidan.xiaoaimei.base.BaseActivity;
import com.yidan.xiaoaimei.ui.fragment.main.ChatFragment;
import com.yidan.xiaoaimei.ui.fragment.main.FindFragment;
import com.yidan.xiaoaimei.ui.fragment.main.HomeFragment;
import com.yidan.xiaoaimei.ui.fragment.main.MineFragment;
import com.yidan.xiaoaimei.ui.view.PublishPopwindow;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;
    @BindView(R.id.iv_home)
    ImageView ivHome;
    @BindView(R.id.tv_home)
    TextView tvHome;
    @BindView(R.id.rl_home)
    RelativeLayout rlHome;
    @BindView(R.id.iv_chat)
    ImageView ivChat;
    @BindView(R.id.tv_chat)
    TextView tvChat;
    @BindView(R.id.rl_chat)
    RelativeLayout rlChat;
    @BindView(R.id.iv_find)
    ImageView ivFind;
    @BindView(R.id.tv_find)
    TextView tvFind;
    @BindView(R.id.rl_find)
    RelativeLayout rlFind;
    @BindView(R.id.iv_mine)
    ImageView ivMine;
    @BindView(R.id.tv_mine)
    TextView tvMine;
    @BindView(R.id.rl_mine)
    RelativeLayout rlMine;
    @BindView(R.id.ll_tab)
    LinearLayout llTab;
    @BindView(R.id.view_bottom)
    View viewBottom;
    @BindView(R.id.iv_publish)
    ImageView ivPublish;

    @OnClick({R.id.rl_home, R.id.rl_find, R.id.rl_chat, R.id.rl_mine, R.id.iv_publish})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_home:
                setTabSelection(0);
                rlHome.setSelected(true);
                rlFind.setSelected(false);
                rlChat.setSelected(false);
                rlMine.setSelected(false);
                break;
            case R.id.rl_find:
                setTabSelection(1);
                rlHome.setSelected(false);
                rlFind.setSelected(true);
                rlChat.setSelected(false);
                rlMine.setSelected(false);
                break;
            case R.id.rl_chat:
                setTabSelection(2);
                rlHome.setSelected(false);
                rlFind.setSelected(false);
                rlChat.setSelected(true);
                rlMine.setSelected(false);
                break;
            case R.id.rl_mine:
                setTabSelection(3);
                rlHome.setSelected(false);
                rlFind.setSelected(false);
                rlChat.setSelected(false);
                rlMine.setSelected(true);
                break;
            case R.id.iv_publish:
                publishPopwindow.showMoreWindow(view);
                break;
            default:
                break;
        }
    }


    private HomeFragment homeFragment;

    private FindFragment findFragment;

    private ChatFragment chatFragment;

    private MineFragment mineFragment;

    private DoubleClickExitHelper doubleClickExit;

    private PublishPopwindow publishPopwindow;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }


    @Override
    public void initData() {
        super.initData();
        ScreenUtils.translateStatusBar(this);
        ScreenUtils.setStatusBarMode(this, true);

        doubleClickExit = new DoubleClickExitHelper(this);
        doubleClickExit.setTimeInterval(3000);
        doubleClickExit.setToastContent("再按一次退出喵空!");
        setTabSelection(0);
        rlHome.setSelected(true);
        rlFind.setSelected(false);
        rlChat.setSelected(false);
        rlMine.setSelected(false);

        publishPopwindow = new PublishPopwindow(this);

    }

    /**
     * 根据传入的index参数来设置选中的tab页。
     *
     * @param index 每个tab页对应的下标。
     */
    public void setTabSelection(int index) {
        // 开启一个Fragment事务
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        switch (index) {
            case 0:
//                setStatusBarTint(activity, R.color.commonWhite);
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    //transaction.add(R.id.fragment_container, mHomeFragment);
                    transaction.add(R.id.fragment_container, homeFragment);
                } else {
                    homeFragment.setUserVisibleHint(true);
                    transaction.show(homeFragment);
                }
                break;
            case 1:
//                setStatusBarTint(activity, R.color.commonWhite);
                if (findFragment == null) {
                    findFragment = new FindFragment();
                    //transaction.add(R.id.fragment_container, mHomeFragment);
                    transaction.add(R.id.fragment_container, findFragment);
                } else {
                    findFragment.setUserVisibleHint(true);
                    transaction.show(findFragment);
                }
                break;
            case 2:
//                setStatusBarTint(activity, R.color.commonWhite);
                if (chatFragment == null) {
                    chatFragment = new ChatFragment();
                    //transaction.add(R.id.fragment_container, mHomeFragment);
                    transaction.add(R.id.fragment_container, chatFragment);
                } else {
                    chatFragment.setUserVisibleHint(true);
                    transaction.show(chatFragment);
                }

                break;
            case 3:
//                setStatusBarTint(activity, R.color.distanceTextColor);
                if (mineFragment == null) {
                    mineFragment = new MineFragment();
                    //transaction.add(R.id.fragment_container, mHomeFragment);
                    transaction.add(R.id.fragment_container, mineFragment);
                } else {
                    mineFragment.setUserVisibleHint(true);
                    transaction.show(mineFragment);
                }
                break;
            default:
                break;
        }
        transaction.commitAllowingStateLoss();
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction 用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (homeFragment != null) {
            transaction.hide(homeFragment);
        }
        if (findFragment != null) {
            transaction.hide(findFragment);
        }
        if (chatFragment != null) {
            transaction.hide(chatFragment);
        }
        if (mineFragment != null) {
            transaction.hide(mineFragment);
        }
    }

    //防止重叠
    @Override
    public void onSaveInstanceState(Bundle outState) {
        //super.onSaveInstanceState(outState);
    }


    public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent) {
        return this.doubleClickExit.onKeyDown(paramInt, paramKeyEvent);
    }


}
