package com.yidan.xiaoaimei.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.miaokong.commonutils.utils.StringUtil;
import com.yidan.xiaoaimei.R;

/**
 * 公用弹窗
 * Created by jaydenma on 2017/7/31.
 */

public class CommonDialog extends Dialog {

    private Context context;

    private TextView tvTitle;

    private TextView tvContent;

    private TextView tvOk, tvCancel, tvOkOne;

    private ImageView ivClose;

    private String title, content, okStr, cancelStr;

    private boolean toCancel;

    private LinearLayout llTwobutton;

    private OkListener listener;

    public interface OkListener {
        void ok();
    }

    public CommonDialog(@NonNull Context context, String title, String content, String okStr, String cancelStr, boolean toCancel, OkListener listener) {
        super(context, R.style.common_dialog);
        this.context = context;
        this.title = title;
        this.content = content;
        this.okStr = okStr;
        this.cancelStr = cancelStr;
        this.listener = listener;
        this.toCancel = toCancel;

        setCanceledOnTouchOutside(true);

        findViews();
        setListeners();


    }

    public CommonDialog(@NonNull Context context, String title, String content, String okStr) {
        super(context, R.style.common_dialog);
        this.context = context;
        this.title = title;
        this.content = content;
        this.okStr = okStr;

        setCanceledOnTouchOutside(true);

        findViews();
        setListeners();


    }


    private void findViews() {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_common, null);

        tvTitle = (TextView) view.findViewById(R.id.tv_dialog_title);
        tvContent = (TextView) view.findViewById(R.id.tv_dialog_content);
        tvOk = (TextView) view.findViewById(R.id.tv_dialog_ok);
        tvOkOne = (TextView) view.findViewById(R.id.tv_dialog_one_ok);
        tvCancel = (TextView) view.findViewById(R.id.tv_dialog_cancel);
        ivClose = (ImageView) view.findViewById(R.id.iv_dialog_close);
        llTwobutton = (LinearLayout) view.findViewById(R.id.ll_two_button);

        tvTitle.setText(title);
        tvContent.setText(Html.fromHtml(content));
        tvOk.setText(okStr);
        tvOkOne.setText(okStr);
        if (StringUtil.isEmpty(cancelStr)) {
            llTwobutton.setVisibility(View.GONE);
            tvOkOne.setVisibility(View.VISIBLE);
        } else {
            tvOkOne.setVisibility(View.GONE);
            llTwobutton.setVisibility(View.VISIBLE);
            tvCancel.setText(cancelStr);
        }

        if (toCancel) {
            tvOk.setBackgroundResource(R.drawable.common_cancel_button);
            tvCancel.setBackgroundResource(R.drawable.common_red_button);
        }

        setContentView(view);
    }

    private void setListeners() {
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.ok();
                }
                dismiss();
            }
        });
        tvOkOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.ok();
                }
                dismiss();
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }


}
