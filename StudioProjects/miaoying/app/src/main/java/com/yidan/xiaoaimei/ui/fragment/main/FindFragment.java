package com.yidan.xiaoaimei.ui.fragment.main;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.miaokong.commonutils.utils.ScreenUtils;
import com.yidan.xiaoaimei.R;
import com.yidan.xiaoaimei.base.BaseFragment;
import com.yidan.xiaoaimei.ui.fragment.find.FindHotFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by jaydenma on 2017/11/15.
 */

public class FindFragment extends BaseFragment {

    @BindView(R.id.view_top)
    View viewTop;
    @BindView(R.id.iv_screen)
    ImageView ivScreen;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    private FindHotFragment findHotFragment;

    private FindHotFragment findNewFragment;

    private FindHotFragment findAttentionFragment;

    private List<Fragment> fragments = new ArrayList<Fragment>();

    private FragmentAdapter adapter;

    private String[] titles = {"热门", "最新", "关注"};

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_find;
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void initData() {
        super.initData();
        //设置状态栏
        ViewGroup.LayoutParams layoutParams = viewTop.getLayoutParams();
        layoutParams.height = ScreenUtils.getStatusHeight(mActivity);
        viewTop.setLayoutParams(layoutParams);

        findHotFragment = new FindHotFragment(1);
        findNewFragment = new FindHotFragment(2);
        findAttentionFragment = new FindHotFragment(3);

        fragments.add(findHotFragment);
        fragments.add(findNewFragment);
        fragments.add(findAttentionFragment);

        adapter = new FragmentAdapter(getChildFragmentManager(), fragments);
        viewpager.setAdapter(adapter);
        viewpager.setCurrentItem(0, true);
        viewpager.setOffscreenPageLimit(4);

        tablayout.setupWithViewPager(viewpager);

        for (int i = 0; i < adapter.getCount(); i++) {
            TabLayout.Tab tab = tablayout.getTabAt(i);//获得每一个tab
            tab.setCustomView(R.layout.item_home_tablayout);//给每一个tab设置view
            if (i == 0) {
                // 设置第一个tab的TextView是被选择的样式
                tab.getCustomView().findViewById(R.id.tab_text).setSelected(true);//第一个tab被选中
            }
            TextView textView = (TextView) tab.getCustomView().findViewById(R.id.tab_text);
            textView.setText(titles[i]);//设置tab上的文字
        }
        tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getCustomView().findViewById(R.id.tab_text).setSelected(true);
                viewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getCustomView().findViewById(R.id.tab_text).setSelected(false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
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
}
