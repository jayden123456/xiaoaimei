package com.yidan.xiaoaimei.ui.activity.detail;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.miaokong.commonutils.utils.AutoViwHeightUtil;
import com.miaokong.commonutils.utils.LoadingDialogUtil;
import com.yidan.xiaoaimei.R;
import com.yidan.xiaoaimei.base.BaseActivity;
import com.yidan.xiaoaimei.base.adapterutils.SpacesItemDecoration;
import com.yidan.xiaoaimei.contract.HomeContract;
import com.yidan.xiaoaimei.model.home.HomeCommonInfo;
import com.yidan.xiaoaimei.presenter.HomePresenter;
import com.yidan.xiaoaimei.utils.ToastUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static cn.finalteam.toolsfinal.DeviceUtils.dip2px;


/**
 * 评价结果
 * Created by jaydenma on 2017/12/14.
 */

public class EvaluateResultActivity extends BaseActivity implements HomeContract.IHomeView {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_evaluate_result)
    RecyclerView rvEvaluateResult;

    @OnClick(R.id.iv_back)
    public void OnClick() {
        finish();
    }


    private Dialog loadingDialog;

    private HomePresenter homePresenter;

    private HeaderAndFooterWrapper headerAndFooterWrapper;

    private CommonAdapter<HomeCommonInfo.DataBean.ListsBean> adapter;

    private List<HomeCommonInfo.DataBean.ListsBean> actors = new ArrayList<HomeCommonInfo.DataBean.ListsBean>();

    private String focusType;

    private String focusUid;

    private Gson gson = new Gson();
    private List<Integer> userIds = new ArrayList<Integer>();

    private int doType = 1;//1关注

    private int curPosition;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_evaluate_result;
    }

    @Override
    public void initData() {
        super.initData();
        tvTitle.setText("评价成功");

        homePresenter = new HomePresenter(this);
        homePresenter.getHome();
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
            actors.get(curPosition - 1).setIsFollowed(actors.get(curPosition - 1).getIsFollowed() == 1 ? 0 : 1);
            headerAndFooterWrapper.notifyDataSetChanged();
        }
    }

    @Override
    public void showError(String msg) {
        ToastUtils.ShowError(mActivity, msg);
    }

    @Override
    public void showHomeData(HomeCommonInfo info) {
        actors.addAll(info.getData().getLists());
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(mActivity, 2);
        rvEvaluateResult.setLayoutManager(mLayoutManager);
        rvEvaluateResult.setHasFixedSize(true);
        rvEvaluateResult.addItemDecoration(new SpacesItemDecoration(false, dip2px(mActivity, 3), dip2px(mActivity, 3), 0, dip2px(mActivity, 10)));
        rvEvaluateResult.setNestedScrollingEnabled(false);
        adapter = new CommonAdapter<HomeCommonInfo.DataBean.ListsBean>(mActivity, R.layout.item_evaluate_result, actors) {
            @Override
            protected void convert(ViewHolder holder, final HomeCommonInfo.DataBean.ListsBean listsBean, final int position) {
                ImageView ivHead = holder.getView(R.id.iv_item_head);
                AutoViwHeightUtil.setGridItemHeight(mActivity, ivHead, 1, 1, dip2px(mActivity, 12), 2);
                Glide.with(mActivity).load(listsBean.getHeadImg()).into(ivHead);
                holder.setText(R.id.tv_item_name, listsBean.getNickName());

                TextView tvAttention = holder.getView(R.id.tv_item_attention);
                Drawable drawableAttention = getResources().getDrawable(R.mipmap.icon_home_attention);
                drawableAttention.setBounds(0, 0, drawableAttention.getMinimumWidth(), drawableAttention.getMinimumHeight());
                Drawable drawableAttentioned = getResources().getDrawable(R.mipmap.icon_home_attentioned);
                drawableAttentioned.setBounds(0, 0, drawableAttentioned.getMinimumWidth(), drawableAttentioned.getMinimumHeight());
                if (listsBean.getIsFollowed() == 1) {
                    tvAttention.setCompoundDrawables(null, drawableAttentioned, null, null);
                    tvAttention.setText("已关注");
                    tvAttention.setTextColor(getResources().getColor(R.color.commonRedClick));
                } else {
                    tvAttention.setCompoundDrawables(null, drawableAttention, null, null);
                    tvAttention.setText("关注");
                    tvAttention.setTextColor(getResources().getColor(R.color.color9A));
                }
                tvAttention.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        curPosition = position;
                        if (listsBean.getIsFollowed() == 1) {
                            //取消关注
                            focusType = "0";
                            userIds.clear();
                            userIds.add(listsBean.getUid());
                            focusUid = gson.toJson(userIds);
                            homePresenter.focus();
                        } else {
                            //关注
                            focusType = "1";
                            userIds.clear();
                            userIds.add(listsBean.getUid());
                            focusUid = gson.toJson(userIds);
                            homePresenter.focus();
                        }
                    }
                });
            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent = new Intent(mActivity, UserDetailActivity.class);
                intent.putExtra("uid", actors.get(position - 1).getUid() + "");
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        headerAndFooterWrapper = new HeaderAndFooterWrapper(adapter);
        //headview
        View headerView = mActivity.getLayoutInflater().inflate(R.layout.head_evaluate_result, null);
        headerAndFooterWrapper.addHeaderView(headerView);
        rvEvaluateResult.setAdapter(headerAndFooterWrapper);
    }

    @Override
    public String getToken() {
        return spUtil.getStringValue("token", "");
    }

    @Override
    public String getType() {
        return "2";
    }

    @Override
    public int getPageNum() {
        return 1;
    }

    @Override
    public String getFocusType() {
        return focusType;
    }

    @Override
    public String getUid() {
        return focusUid;
    }


}
