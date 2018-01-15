package com.yidan.xiaoaimei.ui.fragment.home;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.miaokong.commonutils.utils.AutoViwHeightUtil;
import com.miaokong.commonutils.utils.LoadingDialogUtil;
import com.yidan.xiaoaimei.R;
import com.yidan.xiaoaimei.base.BaseFragment;
import com.yidan.xiaoaimei.base.adapterutils.SpacesItemDecoration;
import com.yidan.xiaoaimei.contract.HomeContract;
import com.yidan.xiaoaimei.model.home.HomeCommonInfo;
import com.yidan.xiaoaimei.presenter.HomePresenter;
import com.yidan.xiaoaimei.ui.activity.detail.UserDetailActivity;
import com.yidan.xiaoaimei.ui.view.RoundAngleImageView;
import com.yidan.xiaoaimei.utils.ToastUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.LoadMoreWrapper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 关注
 * Created by jaydenma on 2017/12/7.
 */

public class AttentionFragment extends BaseFragment implements HomeContract.IHomeView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.rv_recommend)
    RecyclerView rvRecommend;
    @BindView(R.id.swiperefreshlayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private Dialog loadingDialog;

    private HomePresenter homePresenter;

    private LinearLayoutManager layoutManager;

    private CommonAdapter<HomeCommonInfo.DataBean.ListsBean> adapter;

    private List<HomeCommonInfo.DataBean.ListsBean> actors = new ArrayList<HomeCommonInfo.DataBean.ListsBean>();

    private HomeCommonInfo homeCommonInfo = new HomeCommonInfo();

    private LoadMoreWrapper loadMoreWrapper;

    private ProgressBar loadingView;

    private TextView tvLoadmore;

    private int pageNum = 1; //分页

    private int allPageNum = 1;

    // 标志位，标志已经初始化完成。
    private boolean isPrepared = false;

    private boolean isFirstIn = true;  //首次进入加载数据

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_attention;
    }

    @Override
    protected void lazyLoad() {
        if (isFirstIn && isPrepared && isVisible) {
            isFirstIn = false;
            homePresenter.getHome();
        }
    }


    @Override
    public void initData() {
        super.initData();
        swipeRefreshLayout.setOnRefreshListener(this);

        homePresenter = new HomePresenter(this);
        isPrepared = true;
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
    public void showHomeData(HomeCommonInfo info) {
        homeCommonInfo = info;
        if (info.getData().getLists() != null && info.getData().getLists().size() > 0) {
            actors.addAll(info.getData().getLists());
            if (pageNum == 1) {
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
            rvRecommend.addItemDecoration(new SpacesItemDecoration(false, 0, 0, 20, 0));
            rvRecommend.setLayoutManager(layoutManager);
            rvRecommend.setHasFixedSize(true);
            adapter = new CommonAdapter<HomeCommonInfo.DataBean.ListsBean>(mActivity, R.layout.item_home_common, actors) {
                @Override
                protected void convert(ViewHolder holder, HomeCommonInfo.DataBean.ListsBean listsBean, int position) {
                    Glide.with(mActivity).load(listsBean.getHeadImg()).into((RoundAngleImageView) holder.getView(R.id.iv_item_head));

                    ImageView ivCover = holder.getView(R.id.iv_item_cover);
                    AutoViwHeightUtil.setViewHeight(mActivity, ivCover, 1, 1, 60);
                    Glide.with(mActivity).load(listsBean.getHeadImg()).into(ivCover);

                    holder.setText(R.id.tv_item_name, listsBean.getNickName());

                    holder.setText(R.id.tv_item_sign, listsBean.getSignature());

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

                    TextView tvTag1 = holder.getView(R.id.tv_tag1);
                    TextView tvTag2 = holder.getView(R.id.tv_tag2);
                    TextView tvTag3 = holder.getView(R.id.tv_tag3);
                    if (listsBean.getTags() != null && listsBean.getTags().size() > 0) {
                        switch (listsBean.getTags().size()) {
                            case 1:
                                tvTag1.setVisibility(View.VISIBLE);
                                tvTag2.setVisibility(View.GONE);
                                tvTag3.setVisibility(View.GONE);
                                tvTag1.setText(listsBean.getTags().get(0).getTagName());
                                break;
                            case 2:
                                tvTag1.setVisibility(View.VISIBLE);
                                tvTag2.setVisibility(View.VISIBLE);
                                tvTag3.setVisibility(View.GONE);
                                tvTag1.setText(listsBean.getTags().get(0).getTagName());
                                tvTag2.setText(listsBean.getTags().get(1).getTagName());
                                break;
                            case 3:
                                tvTag1.setVisibility(View.VISIBLE);
                                tvTag2.setVisibility(View.VISIBLE);
                                tvTag3.setVisibility(View.VISIBLE);
                                tvTag1.setText(listsBean.getTags().get(0).getTagName());
                                tvTag2.setText(listsBean.getTags().get(1).getTagName());
                                tvTag3.setText(listsBean.getTags().get(2).getTagName());
                                break;
                            default:
                                break;
                        }
                    } else {
                        tvTag1.setVisibility(View.GONE);
                        tvTag2.setVisibility(View.GONE);
                        tvTag3.setVisibility(View.GONE);
                    }
                    TextView tvService1 = holder.getView(R.id.tv_service1);
                    TextView tvService2 = holder.getView(R.id.tv_service2);
                    TextView tvService3 = holder.getView(R.id.tv_service3);
                    if (listsBean.getServiceTags() != null && listsBean.getServiceTags().size() > 0) {
                        switch (listsBean.getServiceTags().size()) {
                            case 1:
                                tvService1.setVisibility(View.VISIBLE);
                                tvService2.setVisibility(View.GONE);
                                tvService3.setVisibility(View.GONE);
                                tvService1.setText(listsBean.getServiceTags().get(0).getTagName());
                                break;
                            case 2:
                                tvService1.setVisibility(View.VISIBLE);
                                tvService2.setVisibility(View.VISIBLE);
                                tvService3.setVisibility(View.GONE);
                                tvService1.setText(listsBean.getServiceTags().get(0).getTagName());
                                tvService2.setText(listsBean.getServiceTags().get(1).getTagName());
                                break;
                            case 3:
                                tvService1.setVisibility(View.VISIBLE);
                                tvService2.setVisibility(View.VISIBLE);
                                tvService3.setVisibility(View.VISIBLE);
                                tvService1.setText(listsBean.getServiceTags().get(0).getTagName());
                                tvService2.setText(listsBean.getServiceTags().get(1).getTagName());
                                tvService3.setText(listsBean.getServiceTags().get(2).getTagName());
                                break;
                            default:
                                break;
                        }
                    } else {
                        tvService1.setVisibility(View.GONE);
                        tvService2.setVisibility(View.GONE);
                        tvService3.setVisibility(View.GONE);
                    }

                }
            };
            adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                    Intent intent = new Intent(mActivity, UserDetailActivity.class);
                    intent.putExtra("uid", actors.get(position).getUid() + "");
                    startActivity(intent);
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });
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
                    homePresenter.getHome();
//                    }
                }
            });
            rvRecommend.setAdapter(loadMoreWrapper);
        }
    }

    @Override
    public String getToken() {
        return spUtil.getStringValue("token", "");
    }

    @Override
    public String getType() {
        return "1";
    }

    @Override
    public int getPageNum() {
        return pageNum;
    }

    @Override
    public String getFocusType() {
        return null;
    }

    @Override
    public String getUid() {
        return null;
    }


    @Override
    public void onRefresh() {
        actors.clear();
        pageNum = 1;
        homePresenter.getHome();
    }

}
