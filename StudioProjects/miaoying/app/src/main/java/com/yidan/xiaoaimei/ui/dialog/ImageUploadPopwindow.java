package com.yidan.xiaoaimei.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yidan.xiaoaimei.R;


/**
 * 上传图片选择相册或相机弹窗
 * Created by jaydenma on 2017/7/21.
 */

public class ImageUploadPopwindow extends Dialog {


    private Context context;

    private RelativeLayout rlPctureAlbum;

    private TextView tvPctureAlbum;

    private RelativeLayout rlPhotoGraph;

    private TextView tvPhotoGraph;

    private RelativeLayout rlCancle;

    private String text1, text2;

    private onSelectSexListener listener;

    public interface onSelectSexListener {
        void onSelect(int location);
    }


    public ImageUploadPopwindow(@NonNull Context context, String text1, String text2, onSelectSexListener listener) {
        super(context, R.style.DialogTheme);
        this.context = context;
        this.listener = listener;
        this.text1 = text1;
        this.text2 = text2;

        findViews();

        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.gravity = Gravity.BOTTOM;
        attributes.alpha = 0.9f;
        attributes.width = ViewPager.LayoutParams.MATCH_PARENT;
        attributes.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(attributes);
        getWindow().setWindowAnimations(R.style.dialogstyle);
        setCanceledOnTouchOutside(true);

    }

    private void findViews() {
        View view = LayoutInflater.from(context).inflate(R.layout.photo_update_pop, null);
        //相册
        rlPctureAlbum = (RelativeLayout) view.findViewById(R.id.rl_picture_album);
        tvPctureAlbum = (TextView) view.findViewById(R.id.tv_picture_album);
        //拍摄
        rlPhotoGraph = (RelativeLayout) view.findViewById(R.id.rl_photograph);
        tvPhotoGraph = (TextView) view.findViewById(R.id.tv_photograph);
        //取消
        rlCancle = (RelativeLayout) view.findViewById(R.id.rl_cancle);
        tvPctureAlbum.setText(text1);
        tvPhotoGraph.setText(text2);

        setContentView(view);
        rlPctureAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSelect(0);
                dismiss();
            }
        });
        rlPhotoGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSelect(1);
                dismiss();
            }
        });
        rlCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }


}
