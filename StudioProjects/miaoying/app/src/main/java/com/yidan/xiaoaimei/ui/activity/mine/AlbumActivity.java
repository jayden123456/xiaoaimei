package com.yidan.xiaoaimei.ui.activity.mine;

import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.miaokong.commonutils.oss.Constant;
import com.miaokong.commonutils.oss.ImageFactory;
import com.miaokong.commonutils.oss.OssService;
import com.miaokong.commonutils.utils.AutoViwHeightUtil;
import com.miaokong.commonutils.utils.LoadingDialogUtil;
import com.miaokong.commonutils.utils.StringUtil;
import com.yidan.xiaoaimei.R;
import com.yidan.xiaoaimei.base.BaseActivity;
import com.yidan.xiaoaimei.base.adapterutils.SpacesItemDecoration;
import com.yidan.xiaoaimei.contract.AlbumContract;
import com.yidan.xiaoaimei.model.mine.AlbumInfo;
import com.yidan.xiaoaimei.presenter.AlbumPresenter;
import com.yidan.xiaoaimei.ui.activity.image.AlbumBigActivity;
import com.yidan.xiaoaimei.ui.activity.video.RecordActivity;
import com.yidan.xiaoaimei.ui.activity.video.VideoPlayActivity;
import com.yidan.xiaoaimei.ui.activity.video.choose.TCVideoChooseActivity;
import com.yidan.xiaoaimei.ui.dialog.ImageUploadPopwindow;
import com.yidan.xiaoaimei.ui.view.RoundAngleImageView;
import com.yidan.xiaoaimei.utils.ToastUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import xxsh.neu.android.com.mylibrary.Luban;
import xxsh.neu.android.com.mylibrary.OnCompressListener;

import static cn.finalteam.toolsfinal.DeviceUtils.dip2px;

/**
 * 相册/视频集
 * Created by jaydenma on 2017/12/4.
 */

public class AlbumActivity extends BaseActivity implements AlbumContract.IAlbumView {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_button_right)
    TextView tvButtonRight;
    @BindView(R.id.rv_album)
    RecyclerView rvAlbum;

    @OnClick({R.id.iv_back, R.id.tv_button_right})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_button_right:
                //上传
                imageUploadPopwindow.show();
                break;
            default:
                break;
        }
    }

    public static int changed = 0;

    private static final int GALLERY_REQUEST_REPLACE_CODE = 1;
    private static final int GALLERY_REQUEST_TAKE_CODE = 2;

    private AlbumPresenter albumPresenter;

    private Dialog loadingDialog;

    private ImageUploadPopwindow imageUploadPopwindow;

    private int fromType;  //1:相册2:视频

    private List<String> images = new ArrayList<String>();

    private List<String> lookBigs = new ArrayList<String>();

    private String mImageUrl = "";

    private int uploadNo = 0;

    private int imageCount;

    private CommonAdapter<AlbumInfo.DataBean> albumAdapter;

    private List<AlbumInfo.DataBean> albums = new ArrayList<AlbumInfo.DataBean>();

    private RecyclerView.LayoutManager mLayoutManager;

    //阿里oss
    public OssService ossService;

    // 处理oss上传后的回调
    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //上传成功
            if (msg.what == Constant.UPLOADOK) {
                if (uploadNo == imageCount) {
//                    LoadingDialogUtil.closeDialog(loadingDialog);
                    uploadNo = 0;
                    albumPresenter.addImage();
                }
            }
        }

    };

    @Override
    public int getLayoutResId() {
        return R.layout.activity_album;
    }


    @Override
    public void initData() {
        super.initData();
        fromType = getIntent().getIntExtra("fromType", 1);

        //初始化oss
        ossService = ImageFactory.initOSS(this, Constant.ENDPOINT, Constant.BUCKET, null);
        ossService.setHandler(mHandler);

        if (fromType == 1) {
            tvTitle.setText("相册");
            imageUploadPopwindow = new ImageUploadPopwindow(mActivity, "上传图片", "点击拍摄", new listener());
        } else {
            tvTitle.setText("视频集");
            imageUploadPopwindow = new ImageUploadPopwindow(mActivity, "上传视频", "点击拍摄", new listener());
        }

        tvButtonRight.setVisibility(View.VISIBLE);
        tvButtonRight.setText("上传");
        tvButtonRight.setTextColor(getResources().getColor(R.color.commonWhite));
        tvButtonRight.setBackgroundResource(R.drawable.common_red_button);

        mLayoutManager = new GridLayoutManager(mActivity, 4);
        rvAlbum.setLayoutManager(mLayoutManager);
        rvAlbum.setHasFixedSize(true);
        rvAlbum.addItemDecoration(new SpacesItemDecoration(false, dip2px(mActivity, (float) 2.5), dip2px(mActivity, (float) 2.5), dip2px(mActivity, 5), 0));

        albumPresenter = new AlbumPresenter(this);
        albumPresenter.getAlbums();
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
    public void showData(List<AlbumInfo.DataBean> data) {
        lookBigs.clear();
        albums = data;

        if (fromType == 1) {
            for (AlbumInfo.DataBean dataBean : albums) {
                lookBigs.add(dataBean.getImgUrl());
            }
        } else {

        }
        albumAdapter = new CommonAdapter<AlbumInfo.DataBean>(mActivity, R.layout.item_album, albums) {
            @Override
            protected void convert(ViewHolder holder, AlbumInfo.DataBean dataBean, int position) {
                RelativeLayout rl = holder.getView(R.id.rl_album);
                AutoViwHeightUtil.setGridItemHeight(mActivity, rl, 1, 1, dip2px(mActivity, 25), 4);
                RoundAngleImageView ivAlbum = holder.getView(R.id.iv_item_img);
                if (fromType == 1) {
                    if (!StringUtil.isEmpty(dataBean.getImgUrl())) {
                        Glide.with(mActivity).load(dataBean.getImgUrl()).into(ivAlbum);
                        ivAlbum.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    } else {
                        //默认图
                        Glide.with(mActivity).load(R.mipmap.page_default_detail).into(ivAlbum);
                    }

                    holder.getView(R.id.iv_icon_video).setVisibility(View.GONE);
                } else {
                    if (!StringUtil.isEmpty(dataBean.getFirstImg())) {
                        Glide.with(mActivity).load(dataBean.getFirstImg()).into(ivAlbum);
                        ivAlbum.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    } else {
                        //默认图
                        Glide.with(mActivity).load(R.mipmap.page_default_detail).into(ivAlbum);
                    }

                    holder.getView(R.id.iv_icon_video).setVisibility(View.VISIBLE);
                }

            }
        };
        rvAlbum.setAdapter(albumAdapter);
        albumAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if (fromType == 1) {
                    Intent intent = new Intent(mActivity, AlbumBigActivity.class);
                    intent.putExtra(AlbumBigActivity.EXTRA_IMAGE_URLS, (Serializable) lookBigs);
                    intent.putExtra(AlbumBigActivity.EXTRA_IMAGE_INDEX, position);
                    intent.putExtra("data", (Serializable) albums);
                    intent.putExtra("token", spUtil.getStringValue("token", ""));
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(mActivity, VideoPlayActivity.class);
                    intent.putExtra("videoPath", albums.get(position).getVideoUrl());
                    intent.putExtra("coverPath", albums.get(position).getFirstImg());
                    intent.putExtra("fromType", 2);
                    startActivity(intent);
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (changed == 1) {
            changed = 0;
            albums.clear();
            lookBigs.clear();
            albumPresenter.getAlbums();
        }
    }

    @Override
    public void uploadSuc() {
        albums.clear();
        albumPresenter.getAlbums();
    }

    @Override
    public void deleteSuc() {

    }

    @Override
    public String getToken() {
        return spUtil.getStringValue("token", "");
    }

    @Override
    public String getAlbumType() {
        if (fromType == 1) {
            return "0";
        } else {
            return "1";
        }
    }

    @Override
    public String getDataJsonStr() {
        Gson gson = new Gson();
        return gson.toJson(images);
    }

    @Override
    public String getImageId() {
        return null;
    }


    class listener implements ImageUploadPopwindow.onSelectSexListener {

        @Override
        public void onSelect(int location) {
            if (fromType == 1) {
                images.clear();
                if (location == 0) {
                    //打开相册
                    FunctionConfig functionConfig = new FunctionConfig.Builder()
                            .setMutiSelectMaxSize(9)
                            .setEnableCamera(true)//开启相机功能
                            .setEnableEdit(false)//开启编辑功能
                            .setEnableCrop(false)//开启裁剪功能
                            .setEnableRotate(false)//开启旋转功能
                            .setEnablePreview(false)//是否开启预览功能
                            .build();
                    GalleryFinal.openGalleryMuti(GALLERY_REQUEST_REPLACE_CODE, functionConfig, mOnHanlderResultCallback);
                } else {
                    //打开相机
                    FunctionConfig functionConfig = new FunctionConfig.Builder()
                            .setMutiSelectMaxSize(9)
                            .setEnableCamera(true)//开启相机功能
                            .setEnableEdit(false)//开启编辑功能
                            .setEnableCrop(false)//开启裁剪功能
                            .setEnableRotate(false)//开启旋转功能
                            .setEnablePreview(false)//是否开启预览功能
                            .build();
                    GalleryFinal.openCamera(GALLERY_REQUEST_TAKE_CODE, functionConfig, mOnHanlderResultCallback);
                }
            } else {
                if (location == 0) {
                    //上传本地视频
                    Intent localIntent = new Intent(mActivity, TCVideoChooseActivity.class);
                    startActivity(localIntent);

                } else {
                    //录制视频
                    Intent intent = new Intent(mActivity, RecordActivity.class);
                    startActivity(intent);
                }
            }
        }
    }


    //相册监听
    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            imageCount = resultList.size();
//            loadingDialog = LoadingDialogUtil.createLoadingDialog(mActivity, "");
            switch (reqeustCode) {
                case GALLERY_REQUEST_REPLACE_CODE://相册
                    upPhotoOss(resultList);
                    break;
                case GALLERY_REQUEST_TAKE_CODE://拍照
                    upPhotoOss(resultList);
                    break;
            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
        }
    };


    /**
     * 对图片进行压缩后上传到oss
     *
     * @param resultList
     */
    private void upPhotoOss(List<PhotoInfo> resultList) {

        if (resultList != null && resultList.size() > 0) {
            loadingDialog = LoadingDialogUtil.createLoadingDialog(mActivity, "");
            for (PhotoInfo photo : resultList) {
                String photoPath = photo.getPhotoPath();
                Compress(photoPath);
            }
        } else {
            ToastUtils.ShowError(mActivity, "你没有选择图片");
        }
    }


    /**
     * 图片压缩上传
     *
     * @param path
     */
    private void Compress(String path) {
        Luban.get(this).load(new File(path)).putGear(Luban.THIRD_GEAR)
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onSuccess(File file) {
                        //图片压缩成功后，发布图片
                        mImageUrl = "image/" + System.currentTimeMillis() + uploadNo + ".jpg";
                        uploadNo++;
                        images.add(mImageUrl);
//                        actorImages.add(mImageUrl);
//                        imagePaths.add(file.getPath());
                        //开始上传
                        ossService.asyncPutImage(mImageUrl, file.getPath());
                    }

                    @Override
                    public void onError(Throwable e) {
//                        LoadingDialogUtil.closeDialog(loadingDialog);
                        //发布失败
                        ToastUtils.ShowError(mActivity, "上传失败");
                    }
                }).launch();

    }

}
