package com.yidan.xiaoaimei.utils;

import android.app.Activity;
import android.graphics.drawable.Drawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import cn.finalteam.galleryfinal.ImageLoader;
import cn.finalteam.galleryfinal.widget.GFImageView;

/**
 * 裁剪图片时图片加载
 */
public class GlideImageLoader implements ImageLoader {
    @Override
    public void displayImage(Activity activity, String path, final GFImageView imageView, Drawable defaultDrawable, int width, int height) {
        RequestOptions options = new RequestOptions();
        options.placeholder(defaultDrawable);
        options.override(width, height);
        options.diskCacheStrategy(DiskCacheStrategy.NONE);
        options.skipMemoryCache(true);
        options.error(defaultDrawable);
        Glide.with(activity).load("file://" + path).apply(options).into(imageView);
    }

    @Override
    public void clearMemoryCache() {

    }

}
