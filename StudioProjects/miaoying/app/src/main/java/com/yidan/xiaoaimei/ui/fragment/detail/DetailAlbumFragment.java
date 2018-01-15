package com.yidan.xiaoaimei.ui.fragment.detail;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.miaokong.commonutils.utils.AutoViwHeightUtil;
import com.miaokong.commonutils.utils.LoadingDialogUtil;
import com.yidan.xiaoaimei.Const;
import com.yidan.xiaoaimei.R;
import com.yidan.xiaoaimei.base.BaseFragment;
import com.yidan.xiaoaimei.base.adapterutils.SpacesItemDecoration;
import com.yidan.xiaoaimei.contract.UserDetailAlbumContract;
import com.yidan.xiaoaimei.model.detail.AlbumShowInfo;
import com.yidan.xiaoaimei.presenter.UserDetailAlbumPresenter;
import com.yidan.xiaoaimei.ui.activity.image.DetailAlbumBigActivity;
import com.yidan.xiaoaimei.ui.activity.video.VideoPlayActivity;
import com.yidan.xiaoaimei.utils.ToastUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.BindView;

import static cn.finalteam.toolsfinal.DeviceUtils.dip2px;

/**
 * 个人详情相册／视频
 * Created by jaydenma on 2017/12/11.
 */

@SuppressLint("ValidFragment")
public class DetailAlbumFragment extends BaseFragment implements UserDetailAlbumContract.IUserDetailAlbumView {

    @BindView(R.id.rv_detail_album)
    RecyclerView rvDetailAlbum;

    // 标志位，标志已经初始化完成。
    private boolean isPrepared = false;

    private boolean isFirstIn = true;  //首次进入加载数据

    private Dialog loadingDialog;

    private String userId;

    private int type;

    private ArrayList<String> urls = new ArrayList<String>();

    private CommonAdapter<AlbumShowInfo.DataBean.ListsBean> adapter;

    private UserDetailAlbumPresenter userDetailAlbumPresenter;

    private RecyclerView.LayoutManager mLayoutManager;

    public DetailAlbumFragment(String userId, int type) {
        this.userId = userId;
        this.type = type;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_detail_album;
    }

    @Override
    public void initData() {
        super.initData();
        userDetailAlbumPresenter = new UserDetailAlbumPresenter(this);
        isPrepared = true;

        mLayoutManager = new GridLayoutManager(mActivity, 3);
        rvDetailAlbum.setLayoutManager(mLayoutManager);
        rvDetailAlbum.setHasFixedSize(true);
        rvDetailAlbum.addItemDecoration(new SpacesItemDecoration(false, dip2px(mActivity, (float) 2.5), dip2px(mActivity, (float) 2.5), dip2px(mActivity, 5), 0));

    }

    @Override
    protected void lazyLoad() {
        if (isFirstIn && isPrepared && isVisible) {
            isFirstIn = false;
            userDetailAlbumPresenter.getAlbum();
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
    }

    @Override
    public void showErrorMsg(String msg) {
        ToastUtils.ShowError(mActivity, msg);
    }

    @Override
    public String getUserId() {
        return userId;
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
    public void showAlbum(final AlbumShowInfo info) {
        adapter = new CommonAdapter<AlbumShowInfo.DataBean.ListsBean>(mActivity, R.layout.item_detail_album, info.getData().getLists()) {
            @Override
            protected void convert(ViewHolder holder, AlbumShowInfo.DataBean.ListsBean listsBean, int position) {
                RelativeLayout rlAlbum = holder.getView(R.id.rl_album);
                AutoViwHeightUtil.setGridItemHeight(mActivity, rlAlbum, 1, 1, dip2px(mActivity, 35), 3);

                ImageView ivVideo = holder.getView(R.id.iv_icon_video);

                if (type == 0) {
                    if (info.getData().getReadAll() == 0) {
                        if (position > 2) {
                            Glide.with(mActivity).load(listsBean.getImgUrl() + Const.OSS_IMAGE_BLURRY).into((ImageView) holder.getView(R.id.iv_item_img));
                        } else {
                            Glide.with(mActivity).load(listsBean.getImgUrl()).into((ImageView) holder.getView(R.id.iv_item_img));
                        }
                    } else {
                        Glide.with(mActivity).load(listsBean.getImgUrl()).into((ImageView) holder.getView(R.id.iv_item_img));
                    }
                    ivVideo.setVisibility(View.GONE);
                } else {
                    if (info.getData().getReadAll() == 0) {
                        if (position > 2) {
                            Glide.with(mActivity).load(listsBean.getFirstImg() + Const.OSS_IMAGE_BLURRY).into((ImageView) holder.getView(R.id.iv_item_img));
                        } else {
                            Glide.with(mActivity).load(listsBean.getFirstImg()).into((ImageView) holder.getView(R.id.iv_item_img));
                        }
                    } else {
                        Glide.with(mActivity).load(listsBean.getFirstImg()).into((ImageView) holder.getView(R.id.iv_item_img));
                    }
                    ivVideo.setVisibility(View.VISIBLE);
                }
            }
        };
        rvDetailAlbum.setAdapter(adapter);

        if (type == 0) {
            for (AlbumShowInfo.DataBean.ListsBean data : info.getData().getLists()) {
                urls.add(data.getImgUrl());
            }
        }
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if (info.getData().getReadAll() == 1 || position < 3) {
                    if (type == 0) {
                        Intent intent = new Intent(mActivity, DetailAlbumBigActivity.class);
                        intent.putExtra(DetailAlbumBigActivity.EXTRA_IMAGE_URLS, (Serializable) urls);
                        intent.putExtra(DetailAlbumBigActivity.EXTRA_IMAGE_INDEX, position);
                        intent.putExtra("readAll", info.getData().getReadAll());
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(mActivity, VideoPlayActivity.class);
                        intent.putExtra("videoPath", info.getData().getLists().get(position).getVideoUrl());
                        intent.putExtra("coverPath", info.getData().getLists().get(position).getFirstImg());
                        intent.putExtra("fromType", 2);
                        startActivity(intent);
                    }
                } else {
//                    commonDialog.show();
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }


}
