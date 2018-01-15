package com.yidan.xiaoaimei.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;


import com.miaokong.commonutils.utils.KeyBoardUtil;
import com.miaokong.commonutils.utils.SharedPreferencesUtil;
import com.netease.nim.uikit.common.fragment.*;
import com.netease.nim.uikit.common.fragment.TFragment;
import com.yidan.xiaoaimei.Const;
import com.yidan.xiaoaimei.base.interfaces.I_Activity;
import com.yidan.xiaoaimei.base.interfaces.I_Register;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;


/**
 * Created by Administrator on 2016/7/22.
 */
public abstract class BaseActivity extends AppCompatActivity implements I_Activity, I_Register {

    /**
     * Activity状态
     */
    public int activityState = DESTROY;
    protected Activity mActivity;
    protected SharedPreferencesUtil spUtil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
//        new ZTLUtils(mActivity).setTranslucentStatus();
        initPre();
        BaseActivityStack.getInstance().addActivity(this);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(getLayoutResId());
        ButterKnife.bind(this);
        spUtil = new SharedPreferencesUtil(this, Const.SP_NAME);
        final String simpleName = this.getClass().getSimpleName();
        if (spUtil.getBooleanValue(simpleName, true)) {
            onFirst();
            spUtil.putBooleanValue(simpleName, false);
        }
        initInstanceState(savedInstanceState);
        initData();
        initView();
        register();
    }

    @Override
    public void register() {

    }

    @Override
    public void unRegister() {

    }


    @Override
    public void onFirst() {

    }

    @Override
    public void initPre() {

    }

    @Override
    public void initInstanceState(Bundle savedInstanceState) {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        activityState = RESUME;
//        MobclickAgent.onPageStart(getClass().getSimpleName());
//        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        activityState = PAUSE;
//        MobclickAgent.onPageEnd(getClass().getSimpleName());
//        MobclickAgent.onPause(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        activityState = STOP;
    }

    @Override
    protected void onDestroy() {
        unRegister();
        super.onDestroy();
        activityState = DESTROY;
        BaseActivityStack.getInstance().finishActivity(this);
    }

    @Override
    public void finish() {
        KeyBoardUtil.hide(getWindow().getDecorView());
        super.finish();
    }

    /**
     * fragment management
     */
    public com.netease.nim.uikit.common.fragment.TFragment addFragment(com.netease.nim.uikit.common.fragment.TFragment fragment) {
        List<TFragment> fragments = new ArrayList<TFragment>(1);
        fragments.add(fragment);

        List<com.netease.nim.uikit.common.fragment.TFragment> fragments2 = addFragments(fragments);
        return fragments2.get(0);
    }

    public List<com.netease.nim.uikit.common.fragment.TFragment> addFragments(List<com.netease.nim.uikit.common.fragment.TFragment> fragments) {
        List<com.netease.nim.uikit.common.fragment.TFragment> fragments2 = new ArrayList<com.netease.nim.uikit.common.fragment.TFragment>(fragments.size());

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        boolean commit = false;
        for (int i = 0; i < fragments.size(); i++) {
            // install
            com.netease.nim.uikit.common.fragment.TFragment fragment = fragments.get(i);
            int id = fragment.getContainerId();

            // exists
            com.netease.nim.uikit.common.fragment.TFragment fragment2 = (com.netease.nim.uikit.common.fragment.TFragment) fm.findFragmentById(id);

            if (fragment2 == null) {
                fragment2 = fragment;
                transaction.add(id, fragment);
                commit = true;
            }

            fragments2.add(i, fragment2);
        }

        if (commit) {
            try {
                transaction.commitAllowingStateLoss();
            } catch (Exception e) {

            }
        }

        return fragments2;
    }

    public com.netease.nim.uikit.common.fragment.TFragment switchContent(com.netease.nim.uikit.common.fragment.TFragment fragment) {
        return switchContent(fragment, false);
    }

    protected com.netease.nim.uikit.common.fragment.TFragment switchContent(com.netease.nim.uikit.common.fragment.TFragment fragment, boolean needAddToBackStack) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(fragment.getContainerId(), fragment);
        if (needAddToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        try {
            fragmentTransaction.commitAllowingStateLoss();
        } catch (Exception e) {

        }

        return fragment;
    }

}
