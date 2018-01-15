package com.yidan.xiaoaimei.ui.activity.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.miaokong.commonutils.utils.ImageUtils;
import com.miaokong.commonutils.utils.ScreenUtils;
import com.miaokong.commonutils.utils.ToastUtil;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.yidan.xiaoaimei.R;
import com.yidan.xiaoaimei.contract.AlbumContract;
import com.yidan.xiaoaimei.model.mine.AlbumInfo;
import com.yidan.xiaoaimei.presenter.AlbumPresenter;
import com.yidan.xiaoaimei.ui.activity.mine.AlbumActivity;
import com.yidan.xiaoaimei.ui.activity.mine.HomepageActivity;
import com.yidan.xiaoaimei.ui.dialog.ImageUploadPopwindow;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * 查看大图
 * Created by jaydenma on 2017/7/31.
 */

public class AlbumBigActivity extends FragmentActivity implements AlbumContract.IAlbumView {
    private static final String STATE_POSITION = "STATE_POSITION";
    public static final String EXTRA_IMAGE_INDEX = "image_index";
    public static final String EXTRA_IMAGE_URLS = "image_urls";

    private HackyViewPager mPager;
    private int pagerPosition;
    private TextView indicator;
    private TextView tvEdit;

    private ArrayList<String> urls = new ArrayList<String>();

    private List<AlbumInfo.DataBean> tempAlbums = new ArrayList<AlbumInfo.DataBean>();

    private ImagePagerAdapter mAdapter;

    private String token;

    private ImageUploadPopwindow imageUploadPopwindow;

    private AlbumPresenter albumPresenter;

    private int delPosition;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_detail_pager);

        ScreenUtils.translateStatusBar(this);
        token = getIntent().getStringExtra("token");
        tempAlbums = (List<AlbumInfo.DataBean>) getIntent().getSerializableExtra("data");
        tvEdit = (TextView) findViewById(R.id.tv_edit);
        pagerPosition = getIntent().getIntExtra(EXTRA_IMAGE_INDEX, 0);
        urls = getIntent().getStringArrayListExtra(EXTRA_IMAGE_URLS);


        delPosition = pagerPosition;
        mPager = (HackyViewPager) findViewById(R.id.pager);
        mAdapter = new ImagePagerAdapter(getSupportFragmentManager(), urls);
        mPager.setAdapter(mAdapter);
        mPager.setOffscreenPageLimit(tempAlbums.size());
        indicator = (TextView) findViewById(R.id.indicator);

        CharSequence text = getString(R.string.viewpager_indicator, 1, mPager.getAdapter().getCount());
        indicator.setText(text);
        // 更新下标
        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageSelected(int arg0) {
                delPosition = arg0;
                CharSequence text = getString(R.string.viewpager_indicator, arg0 + 1, mPager.getAdapter().getCount());
                indicator.setText(text);
            }

        });
        if (savedInstanceState != null) {
            pagerPosition = savedInstanceState.getInt(STATE_POSITION);
        }

        mPager.setCurrentItem(pagerPosition);

        tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageUploadPopwindow.show();
            }
        });
        albumPresenter = new AlbumPresenter(this);

        imageUploadPopwindow = new ImageUploadPopwindow(this, "保存", "删除", new SelectListener());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_POSITION, mPager.getCurrentItem());
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showInfo(String info) {
        ToastUtil.showToast(this, info);
    }

    @Override
    public void showErrorMsg(String msg) {
        ToastUtil.showToast(this, msg);
    }

    @Override
    public void showData(List<AlbumInfo.DataBean> data) {

    }

    @Override
    public void uploadSuc() {

    }

    @Override
    public void deleteSuc() {
        AlbumActivity.changed = 1;
        HomepageActivity.isChange = 1;
//        mPager.removeViewAt(delPosition);
        urls.remove(delPosition);
        mAdapter.setData(urls);
        mAdapter.notifyDataSetChanged();
        mPager.setCurrentItem(delPosition);
        //更改下标
        CharSequence text = getString(R.string.viewpager_indicator, delPosition + 1, mPager.getAdapter().getCount());
        indicator.setText(text);
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public String getAlbumType() {
        return null;
    }

    @Override
    public String getDataJsonStr() {
        return null;
    }

    @Override
    public String getImageId() {
        return tempAlbums.get(delPosition).getImgId() + "";
    }

    private class ImagePagerAdapter extends FragmentStatePagerAdapter {

        public ArrayList<String> fileList;

        public ImagePagerAdapter(FragmentManager fm, ArrayList<String> fileList) {
            super(fm);
            this.fileList = fileList;
        }

        public void setData(ArrayList<String> fileList) {
            this.fileList = fileList;
        }


        @Override
        public int getItemPosition(Object object) {
            return PagerAdapter.POSITION_NONE;
        }

        @Override
        public int getCount() {
            return fileList == null ? 0 : fileList.size();
        }

        @Override
        public Fragment getItem(int position) {
            String url = fileList.get(position);
            return ImageDetailFragment.newInstance(url);
        }

    }

    class SelectListener implements ImageUploadPopwindow.onSelectSexListener {

        @Override
        public void onSelect(int location) {
            if (location == 0) {
                //保存
                String path = Environment.getExternalStorageDirectory()
                        .getAbsolutePath() + "/miaoying/DownloadImage/";
                downImage(urls.get(delPosition), path, AlbumBigActivity.this);
            } else {
                //删除
                albumPresenter.deleteImg();
            }
        }
    }


    /**
     * 下载图片
     */
    public static void downImage(final String url, final String path, final Context context) {
        final String filename = new Md5FileNameGenerator().generate(url) + ".jpg";
        if (new File(url + filename).exists()) {
            Toast.makeText(context, "保存成功,路径：" + path + filename, Toast.LENGTH_LONG).show();
        } else {
            ImageLoader.getInstance().loadImage(url, new SimpleImageLoadingListener() {
                @Override
                public void onLoadingComplete(String imageUri, View view, final Bitmap loadedImage) {
                    super.onLoadingComplete(imageUri, view, loadedImage);
                    Thread thread = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            ImageUtils.saveBitmap(loadedImage, path, filename);
                            ImageUtils.scanPhotos(path + filename, context);
                        }

                    });
                    thread.start();
                    Toast.makeText(context, "保存成功,路径：" + path + filename, Toast.LENGTH_LONG).show();
                }
            });
        }
    }


}
