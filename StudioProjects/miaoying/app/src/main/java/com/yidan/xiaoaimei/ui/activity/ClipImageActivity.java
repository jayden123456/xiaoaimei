package com.yidan.xiaoaimei.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.view.View;

import com.miaokong.commonutils.utils.ImageUtils;
import com.miaokong.commonutils.utils.StringUtil;
import com.yidan.xiaoaimei.R;
import com.yidan.xiaoaimei.base.BaseActivity;
import com.yidan.xiaoaimei.ui.activity.mine.InfomationActivity;
import com.yidan.xiaoaimei.ui.activity.user.SetInformationActivity;
import com.yidan.xiaoaimei.ui.view.clipimage.ClipImageLayout;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 剪裁图片
 * Created by jaydenma on 2017/7/31.
 */

public class ClipImageActivity extends BaseActivity {

    @BindView(R.id.clipImageLayout)
    ClipImageLayout clipImageLayout;


    @OnClick({R.id.tv_ok, R.id.tv_cancel})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.tv_ok:
                bitmap = clipImageLayout.clip();
                String savePath = "";
                String storageState = Environment.getExternalStorageState();
                if (storageState.equals(Environment.MEDIA_MOUNTED)) {
                    savePath = Environment.getExternalStorageDirectory()
                            .getAbsolutePath() + "/miaokong/Image/";
                    File savedir = new File(savePath);
                    if (!savedir.exists()) {
                        savedir.mkdirs();
                    }
                }

                // 没有挂载SD卡，无法保存文件
                if (StringUtil.isEmpty(savePath)) {
                    //AppContext.showToastShort("无法保存照片，请检查SD卡是否挂载");
                    return;
                }
                String fileName = "cover" + System.currentTimeMillis() + ".jpg";// 照片命名
                File out = new File(savePath, fileName);
                try {
                    ImageUtils.saveImageToSD(mActivity, out.getPath(), bitmap, 100);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (fromType == 0) {
                    Intent intent = new Intent(mActivity, InfomationActivity.class);
                    intent.putExtra("path", out.getPath());
                    setResult(200, intent);
                    finish();
                } else {
                    Intent intent = new Intent(mActivity, SetInformationActivity.class);
                    intent.putExtra("path", out.getPath());
                    setResult(200, intent);
                    finish();
                }
                break;
            case R.id.tv_cancel:
                finish();
                break;
            default:
                break;
        }
    }

    private Bitmap bitmap;

    private int fromType;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_clipimage;
    }

    @Override
    public void initData() {
        super.initData();
        String path = getIntent().getStringExtra("path");
        clipImageLayout.setImagePath(path);
        fromType = getIntent().getIntExtra("fromType", 0);
    }


}
