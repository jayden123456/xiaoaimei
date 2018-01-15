package com.yidan.xiaoaimei.ui.activity.mine;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.miaokong.commonutils.utils.AutoViwHeightUtil;
import com.miaokong.commonutils.utils.LoadingDialogUtil;
import com.miaokong.commonutils.utils.StringUtil;
import com.yidan.xiaoaimei.R;
import com.yidan.xiaoaimei.base.BaseActivity;
import com.yidan.xiaoaimei.base.adapterutils.SpacesItemDecoration;
import com.yidan.xiaoaimei.contract.AlbumAndVideoContract;
import com.yidan.xiaoaimei.model.mine.AlbumAndVideoInfo;
import com.yidan.xiaoaimei.presenter.AlbumAndVideoPresenter;
import com.yidan.xiaoaimei.ui.view.RoundAngleImageView;
import com.yidan.xiaoaimei.utils.ToastUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 相册，视频集
 * Created by jaydenma on 2017/12/4.
 */

public class HomepageActivity extends BaseActivity implements AlbumAndVideoContract.IAlbumAndVideoView {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_album)
    RecyclerView rvAlbum;
    @BindView(R.id.rv_video)
    RecyclerView rvVideo;

    @OnClick({R.id.iv_back,
            R.id.ll_album,
            R.id.ll_video})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_album:
                Intent albumIntent = new Intent(mActivity, AlbumActivity.class);
                albumIntent.putExtra("fromType", 1);
                startActivity(albumIntent);
                break;
            case R.id.ll_video:
                Intent videoIntent = new Intent(mActivity, AlbumActivity.class);
                videoIntent.putExtra("fromType", 2);
                startActivity(videoIntent);
                break;
            default:
                break;
        }
    }

    public static int isChange = 0;

    private AlbumAndVideoPresenter albumAndVideoPresenter;

    private Dialog loadingDialog;

    private CommonAdapter<AlbumAndVideoInfo.DataBean.PhotoBean> albumAdapter;

    private List<AlbumAndVideoInfo.DataBean.PhotoBean> albums = new ArrayList<AlbumAndVideoInfo.DataBean.PhotoBean>();

    private CommonAdapter<AlbumAndVideoInfo.DataBean.VideoBean> videoAdapter;

    private List<AlbumAndVideoInfo.DataBean.VideoBean> videos = new ArrayList<AlbumAndVideoInfo.DataBean.VideoBean>();


    @Override
    public int getLayoutResId() {
        return R.layout.activity_homepage;
    }

    @Override
    public void initData() {
        super.initData();
        tvTitle.setText("相册");

        albumAndVideoPresenter = new AlbumAndVideoPresenter(this);
        albumAndVideoPresenter.getAlbumAndVideo();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isChange == 1) {
            isChange = 0;
            albums.clear();
            albumAndVideoPresenter.getAlbumAndVideo();
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
    public void showError(String msg) {
        ToastUtils.ShowError(mActivity, msg);
    }

    @Override
    public String getToken() {
        return spUtil.getStringValue("token", "");
    }

    @Override
    public void showData(AlbumAndVideoInfo info) {
        if (info.getData().getPhoto() != null) {
            if (info.getData().getPhoto().size() >= 4) {
                for (int i = 0; i < 4; i++) {
                    albums.add(info.getData().getPhoto().get(i));
                }
            } else {
                for (int i = 0; i < info.getData().getPhoto().size(); i++) {
                    albums.add(info.getData().getPhoto().get(i));
                }
                AlbumAndVideoInfo.DataBean.PhotoBean add = new AlbumAndVideoInfo.DataBean.PhotoBean();
                add.setImgUrl("addBg");
                albums.add(add);
            }
        } else {
            AlbumAndVideoInfo.DataBean.PhotoBean add = new AlbumAndVideoInfo.DataBean.PhotoBean();
            add.setImgUrl("addBg");
            albums.add(add);
        }
        if (info.getData().getVideo() != null) {
            if (info.getData().getVideo().size() >= 4) {
                for (int i = 0; i < 4; i++) {
                    videos.add(info.getData().getVideo().get(i));
                }
            } else {
                for (int i = 0; i < info.getData().getVideo().size(); i++) {
                    videos.add(info.getData().getVideo().get(i));
                }
                AlbumAndVideoInfo.DataBean.VideoBean addVideo = new AlbumAndVideoInfo.DataBean.VideoBean();
                addVideo.setFirstImg("addBg");
                videos.add(addVideo);
            }
        } else {
            AlbumAndVideoInfo.DataBean.VideoBean addVideo = new AlbumAndVideoInfo.DataBean.VideoBean();
            addVideo.setFirstImg("addBg");
            videos.add(addVideo);
        }

        rvAlbum.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
        rvAlbum.setHasFixedSize(true);
        rvAlbum.addItemDecoration(new SpacesItemDecoration(false, 5, 5, 0, 0));
        rvAlbum.setNestedScrollingEnabled(false);
        albumAdapter = new CommonAdapter<AlbumAndVideoInfo.DataBean.PhotoBean>(mActivity, R.layout.item_album, albums) {
            @Override
            protected void convert(ViewHolder holder, AlbumAndVideoInfo.DataBean.PhotoBean photoBean, int position) {
                RelativeLayout rl = holder.getView(R.id.rl_album);
                AutoViwHeightUtil.setGridItemHeight(mActivity, rl, 1, 1, 50, 4);
                RoundAngleImageView ivAlbum = holder.getView(R.id.iv_item_img);
                if (!StringUtil.isEmpty(photoBean.getImgUrl())) {
                    if (photoBean.getImgUrl().equals("addBg")) {
                        Glide.with(mActivity).load(R.mipmap.icon_add_album).into(ivAlbum);
                        ivAlbum.setScaleType(ImageView.ScaleType.FIT_XY);
                    } else {
                        Glide.with(mActivity).load(photoBean.getImgUrl()).into(ivAlbum);
                        ivAlbum.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    }
                } else {
                    //默认图

                }

                holder.getView(R.id.iv_icon_video).setVisibility(View.GONE);
            }
        };
        rvAlbum.setAdapter(albumAdapter);
        albumAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent albumIntent = new Intent(mActivity, AlbumActivity.class);
                albumIntent.putExtra("fromType", 1);
                startActivity(albumIntent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        rvVideo.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
        rvVideo.setHasFixedSize(true);
        rvVideo.addItemDecoration(new SpacesItemDecoration(false, 5, 5, 0, 0));
        rvVideo.setNestedScrollingEnabled(false);
        videoAdapter = new CommonAdapter<AlbumAndVideoInfo.DataBean.VideoBean>(mActivity, R.layout.item_album, videos) {
            @Override
            protected void convert(ViewHolder holder, AlbumAndVideoInfo.DataBean.VideoBean videoBean, int position) {
                RelativeLayout rl = holder.getView(R.id.rl_album);
                AutoViwHeightUtil.setGridItemHeight(mActivity, rl, 1, 1, 50, 4);
                RoundAngleImageView ivAlbum = holder.getView(R.id.iv_item_img);
                if (!StringUtil.isEmpty(videoBean.getFirstImg())) {
                    if (videoBean.getFirstImg().equals("addBg")) {
                        Glide.with(mActivity).load(R.mipmap.icon_add_album).into(ivAlbum);
                        holder.getView(R.id.iv_icon_video).setVisibility(View.GONE);
                        ivAlbum.setScaleType(ImageView.ScaleType.FIT_XY);
                    } else {
                        Glide.with(mActivity).load(videoBean.getFirstImg()).into(ivAlbum);
                        holder.getView(R.id.iv_icon_video).setVisibility(View.VISIBLE);
                        ivAlbum.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    }
                } else {
                    //默认图

                }


            }
        };
        rvVideo.setAdapter(videoAdapter);
        videoAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent videoIntent = new Intent(mActivity, AlbumActivity.class);
                videoIntent.putExtra("fromType", 2);
                startActivity(videoIntent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }
}
