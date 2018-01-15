package com.yidan.xiaoaimei.base;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.miaokong.commonutils.utils.SharedPreferencesUtil;
import com.yidan.xiaoaimei.Const;
import com.yidan.xiaoaimei.base.interfaces.I_Fragment;
import com.yidan.xiaoaimei.base.interfaces.I_Register;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Fragment父类
 *
 * @author Jayden
 * @date 2015/12/16
 */
public abstract class BaseFragment extends Fragment implements I_Fragment, I_Register {
    private static final Handler handler = new Handler();

    protected Activity mActivity;
    protected View parentView;
    protected Unbinder unbinder;
    protected SharedPreferencesUtil spUtil;
    protected boolean isVisible;

    @Override
    public void onFirst() {
    }

    @Override
    public void initInstanceState(Bundle savedInstanceState) {
    }

    @Override
    public void initData() {
    }

    @Override
    public void onChange() {
    }

    @Override
    public void onHidden() {
    }

    @Override
    public void register() {
    }

    @Override
    public void unRegister() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mActivity = getActivity();
        parentView = inflater.inflate(getLayoutResId(), container, false);
        unbinder = ButterKnife.bind(this, parentView);
        spUtil = new SharedPreferencesUtil(getActivity(), Const.SP_NAME);
        final String simpleName = this.getClass().getSimpleName();
        if (spUtil.getBooleanValue(simpleName, true)) {
            onFirst();
            spUtil.putBooleanValue(simpleName, false);
        }
        //隐藏状态栏
//        if (isHideStatusBar()) {
//            translateStatusBar(activity);
//        }
        initInstanceState(savedInstanceState);
        initData();
        return parentView;
    }


    protected final Handler getHandler() {
        return handler;
    }

//    /**
//     * 状态栏透明
//     *
//     * @param activity activity
//     */
//    public static void translateStatusBar(Activity activity) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            View decorview = activity.getWindow().getDecorView();
//            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//            decorview.setSystemUiVisibility(option);
//            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
//        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            WindowManager.LayoutParams localLayoutParams = activity.getWindow().getAttributes();
//            localLayoutParams.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags;
//        }
//    }

    /**
     * 在这里实现Fragment数据的缓加载.
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible(){
        lazyLoad();
    }

    protected abstract void lazyLoad();

    protected void onInvisible(){}

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        register();
    }

    @Override
    public void onDestroyView() {
        unRegister();
        super.onDestroyView();
        unbinder.unbind();
    }
}
