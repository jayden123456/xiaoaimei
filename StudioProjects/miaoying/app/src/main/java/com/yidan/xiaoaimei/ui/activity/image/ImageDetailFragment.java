package com.yidan.xiaoaimei.ui.activity.image;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.miaokong.commonutils.utils.LoadingDialogUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.yidan.xiaoaimei.R;
import com.yidan.xiaoaimei.base.BaseFragment;

import butterknife.BindView;
import cn.finalteam.galleryfinal.widget.zoonview.PhotoViewAttacher;


/**
 * Created by jaydenma on 2017/7/31.
 */

public class ImageDetailFragment extends BaseFragment {
    @BindView(R.id.image)
    ImageView mImageView;
    @BindView(R.id.loading)
    ProgressBar loading;

    // 标志位，标志已经初始化完成。
    private boolean isPrepared = false;

    private boolean isFirstIn = true;  //首次进入加载数据

    private boolean unShow = false;


    private String mImageUrl;
    //    private ProgressBar progressBar;
    private PhotoViewAttacher mAttacher;
    private Dialog loadingDialog;

    public static ImageDetailFragment newInstance(String imageUrl) {
        final ImageDetailFragment f = new ImageDetailFragment();

        final Bundle args = new Bundle();
        args.putString("url", imageUrl);
        f.setArguments(args);

        return f;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.image_detail_fragment;
    }

    @Override
    public void initData() {
        super.initData();
        mImageUrl = getArguments() != null ? getArguments().getString("url") : null;

        mAttacher = new PhotoViewAttacher(mImageView);

        mAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {

            @Override
            public void onPhotoTap(View arg0, float arg1, float arg2) {
                getActivity().finish();
            }
        });
        mAttacher.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
//                Toast.makeText(getActivity().getApplicationContext(), "保存", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        isPrepared = true;

        if (unShow) {
            loadImage();
        }
    }

    @Override
    protected void lazyLoad() {
        if (isFirstIn && isPrepared && isVisible) {
            isFirstIn = false;
            loadImage();
        } else if (!isPrepared) {
            unShow = true;
        }
    }


    private void loadImage() {
//        Glide.with(mActivity).load(mImageUrl).into(mImageView);
        ImageLoader.getInstance().displayImage(mImageUrl, mImageView, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                loadingDialog = LoadingDialogUtil.createLoadingDialog(getContext(), "");
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                String message = null;
                switch (failReason.getType()) {
                    case IO_ERROR:
                        message = "下载错误";
                        break;
                    case DECODING_ERROR:
                        message = "图片无法显示";
                        break;
                    case NETWORK_DENIED:
                        message = "网络有问题，无法下载";
                        break;
                    case OUT_OF_MEMORY:
                        message = "图片太大无法显示";
                        break;
                    case UNKNOWN:
                        message = "未知的错误";
                        break;
                }
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                LoadingDialogUtil.closeDialog(loadingDialog);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                LoadingDialogUtil.closeDialog(loadingDialog);
                mAttacher.update();
            }
        });
    }

//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//
//
//        ImageLoader.getInstance().displayImage(mImageUrl, mImageView, new SimpleImageLoadingListener() {
//            @Override
//            public void onLoadingStarted(String imageUri, View view) {
//                loadingDialog = LoadingDialogUtil.createLoadingDialog(getContext(), "");
//            }
//
//            @Override
//            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
//                String message = null;
//                switch (failReason.getType()) {
//                    case IO_ERROR:
//                        message = "下载错误";
//                        break;
//                    case DECODING_ERROR:
//                        message = "图片无法显示";
//                        break;
//                    case NETWORK_DENIED:
//                        message = "网络有问题，无法下载";
//                        break;
//                    case OUT_OF_MEMORY:
//                        message = "图片太大无法显示";
//                        break;
//                    case UNKNOWN:
//                        message = "未知的错误";
//                        break;
//                }
//                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
//                LoadingDialogUtil.closeDialog(loadingDialog);
//            }
//
//            @Override
//            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                LoadingDialogUtil.closeDialog(loadingDialog);
//                mAttacher.update();
//            }
//        });
//    }

}
