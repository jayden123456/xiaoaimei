package com.yidan.xiaoaimei.ui.activity.mine;

import android.app.Dialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.miaokong.commonutils.utils.LoadingDialogUtil;
import com.miaokong.commonutils.utils.StringUtil;
import com.miaokong.commonutils.utils.TextUtils;
import com.yidan.xiaoaimei.R;
import com.yidan.xiaoaimei.base.BaseActivity;
import com.yidan.xiaoaimei.contract.VisitorContract;
import com.yidan.xiaoaimei.model.mine.VisitorInfo;
import com.yidan.xiaoaimei.presenter.VisitorPresenter;
import com.yidan.xiaoaimei.ui.view.RoundAngleImageView;
import com.yidan.xiaoaimei.utils.ToastUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.LoadMoreWrapper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 访客列表
 * Created by jaydenma on 2017/11/22.
 */

public class VisitorActivity extends BaseActivity implements VisitorContract.IVisitorView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_attention)
    RecyclerView rvAttention;
    @BindView(R.id.swiperefreshlayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @OnClick(R.id.iv_back)
    public void OnClick() {
        finish();
    }

    private Dialog loadingDialog;

    private VisitorPresenter visitorPresenter;

    private CommonAdapter<VisitorInfo.DataBean> adapter;

    private LinearLayoutManager layoutManager;

    private List<VisitorInfo.DataBean> attentions = new ArrayList<VisitorInfo.DataBean>();

    private int pageNum = 1; //分页

    private LoadMoreWrapper loadMoreWrapper;

    private ProgressBar loadingView;

    private TextView tvLoadmore;

    private int allPageNum = 1;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_attention;
    }


    @Override
    public void initData() {
        super.initData();

        tvTitle.setText("访客列表");

        swipeRefreshLayout.setOnRefreshListener(this);

        visitorPresenter = new VisitorPresenter(this);
        visitorPresenter.getVisitors();
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
    }

    @Override
    public void showError(String msg) {
        ToastUtils.ShowError(mActivity, msg);
    }

    @Override
    public String getToken() {
        return spUtil.getStringValue("token", "");
    }

    @Override
    public int getPageNum() {
        return pageNum;
    }

    @Override
    public void showVisitor(VisitorInfo info) {
        if (info.getData() != null && info.getData().size() > 0) {
//            rlEmpty.setVisibility(View.GONE);
            if (pageNum == 1) {
                attentions.addAll(info.getData());
                if (tvLoadmore != null) {
                    if (allPageNum > 1) {
                        tvLoadmore.setVisibility(View.GONE);
                        loadingView.setVisibility(View.VISIBLE);
                    }
                }
                swipeRefreshLayout.setRefreshing(false);
                if (adapter != null) {
                    loadMoreWrapper.notifyDataSetChanged();
                }
            } else {
//            refreshlayout.finishLoadmore();
                attentions.addAll(info.getData());
                loadMoreWrapper.notifyDataSetChanged();
            }
        } else {
            if (pageNum != 1) {
                if (attentions.size() != 0) {
                    tvLoadmore.setText("--------我也是有底线的--------");
                    tvLoadmore.setVisibility(View.VISIBLE);
                    loadingView.setVisibility(View.GONE);
//                ToastUtil.showToast(activity, "我也是有底线的！");
                    pageNum--;
                    allPageNum = pageNum;
                } else {
                    tvLoadmore.setVisibility(View.GONE);
                    loadingView.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);
                }
            } else {
                //页面空
//                rlEmpty.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setRefreshing(false);
                if (tvLoadmore != null) {
                    tvLoadmore.setVisibility(View.GONE);
                    loadingView.setVisibility(View.GONE);
                }
            }
        }
        if (adapter == null) {
            layoutManager = new LinearLayoutManager(mActivity);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            rvAttention.setLayoutManager(layoutManager);
            rvAttention.setHasFixedSize(true);
            adapter = new CommonAdapter<VisitorInfo.DataBean>(mActivity, R.layout.item_attention, attentions) {
                @Override
                protected void convert(ViewHolder holder, final VisitorInfo.DataBean listsBean, int position) {

                    Glide.with(mActivity).load(listsBean.getHeadImg()).into((RoundAngleImageView) holder.getView(R.id.iv_item_head));

                    holder.setText(R.id.tv_item_name, listsBean.getNickName());
                    TextUtils.TextBold((TextView) holder.getView(R.id.tv_item_name));

                    TextView tvSign = holder.getView(R.id.tv_item_sign);
                    if (StringUtil.isEmpty(listsBean.getSignature())) {
                        tvSign.setVisibility(View.INVISIBLE);
                    } else {
                        tvSign.setVisibility(View.VISIBLE);
                        tvSign.setText(listsBean.getSignature());
                    }


                    if (listsBean.getOfficialCer() == 1) {
                        holder.getView(R.id.tv_item_verify).setVisibility(View.VISIBLE);
                    } else {
                        holder.getView(R.id.tv_item_verify).setVisibility(View.INVISIBLE);
                    }

                    holder.setText(R.id.tv_item_get, listsBean.getIncome() + "");
                    holder.setText(R.id.tv_item_pay, listsBean.getExpenses() + "");

                    holder.getView(R.id.tv_item_attention).setVisibility(View.GONE);

                    holder.getView(R.id.tv_item_time).setVisibility(View.VISIBLE);

                    holder.setText(R.id.tv_item_time, listsBean.getTime());

                }
            };
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
                    pageNum++;
                    visitorPresenter.getVisitors();
                }
            });
            rvAttention.setAdapter(loadMoreWrapper);
            adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {

                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRefresh() {
        pageNum = 1;
        attentions.clear();
        visitorPresenter.getVisitors();
    }
}
