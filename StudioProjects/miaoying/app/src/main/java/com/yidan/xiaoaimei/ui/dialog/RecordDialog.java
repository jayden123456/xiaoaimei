package com.yidan.xiaoaimei.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.yidan.xiaoaimei.R;


/**
 * 录音弹窗
 * Created by jaydenma on 2017/7/31.
 */

public class RecordDialog extends Dialog {

    private Context context;

    private TextView tvTime;

    private TextView tvBottom;

    private TimeCount timeUtils;

    private FinishListener listener;

    private long allTime = 60000;

    public interface FinishListener {
        void finishRecord();

        void onTime(String time);
    }

    public RecordDialog(@NonNull Context context, FinishListener listener) {
        super(context, R.style.common_dialog);
        this.context = context;
        this.listener = listener;

        setCanceledOnTouchOutside(true);

        findViews();


    }


    private void findViews() {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_record, null);
        tvTime = (TextView) view.findViewById(R.id.tv_time);
        tvBottom = (TextView) view.findViewById(R.id.tv_bottom);

        tvBottom.setBackgroundColor(context.getResources().getColor(R.color.colorCD));

        setContentView(view);

        startRecord();
    }

    public void resetTime() {
        timeUtils.cancel();
        timeUtils = new TimeCount(allTime, 1000);
        timeUtils.start();
    }

    public void setBottom(int type) {
        if (type == 0) {
            tvBottom.setBackgroundColor(context.getResources().getColor(R.color.commonRed));
        } else {
            tvBottom.setBackgroundColor(context.getResources().getColor(R.color.colorCD));
        }
    }


    private void startRecord() {
        timeUtils = new TimeCount(allTime, 1000);
        timeUtils.start();
    }


    class TimeCount extends CountDownTimer {

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            if (allTime - millisUntilFinished + 1000 < 10000) {
                tvTime.setText("00 : 0" + (allTime - millisUntilFinished + 1000) / 1000);
            } else {
                tvTime.setText("00 : " + (allTime - millisUntilFinished + 1000) / 1000);
            }
            listener.onTime((allTime - millisUntilFinished + 1000) / 1000 + "");
        }

        @Override
        public void onFinish() {
            tvTime.setText("00 : " + allTime / 1000);
            listener.onTime(allTime / 1000 + "");
            listener.finishRecord();
            dismiss();
        }
    }

}
