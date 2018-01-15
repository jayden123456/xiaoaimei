package com.yidan.xiaoaimei.ui.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.yidan.xiaoaimei.R;
import com.yidan.xiaoaimei.ui.activity.moment.PublishMomentActivity;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by jaydenma on 2017/8/21.
 */

public class PublishPopwindow extends PopupWindow implements View.OnClickListener {

    private View rootView;
    private LinearLayout contentView;
    private ImageView ivClose;
    private Activity mContext;

    public PublishPopwindow(Activity context) {
        this.mContext = context;
    }

    public void showMoreWindow(View anchor) {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rootView = inflater.inflate(R.layout.popwindow_publish, null);
        int h = mContext.getWindowManager().getDefaultDisplay().getHeight();
        int w = mContext.getWindowManager().getDefaultDisplay().getWidth();
        setContentView(rootView);
        this.setWidth(w);
        this.setHeight(h);

        contentView = (LinearLayout) rootView.findViewById(R.id.ll_publish);
        ivClose = (ImageView) rootView.findViewById(R.id.iv_close);
        ivClose.setOnClickListener(this);
        showAnimation(contentView);
//        setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.translucence_with_white));
        setOutsideTouchable(true);
        setFocusable(true);
        showAtLocation(anchor, Gravity.BOTTOM, 0, 0);

    }


    /**
     * 显示进入动画效果
     *
     * @param layout
     */
    private void showAnimation(ViewGroup layout) {
//        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.publish_add_animation);
//        LinearInterpolator lin = new LinearInterpolator();//设置动画匀速运动
//        animation.setInterpolator(lin);
//
//        ivClose.startAnimation(animation);

        ObjectAnimator animator = ObjectAnimator.ofFloat(ivClose, "rotation", 0f, 90.0f);
        animator.setDuration(300);
        animator.setInterpolator(new LinearInterpolator());//不停顿
        animator.setRepeatCount(0);//设置动画重复次数
        animator.start();

        //遍历根试图下的一级子试图
        for (int i = 0; i < layout.getChildCount(); i++) {
            final View child = layout.getChildAt(i);
            //忽略关闭组件
//            if (child.getId() == R.id.ll_close) {
//                continue;
//            }
            //设置所有一级子试图的点击事件
            child.setOnClickListener(this);
            child.setVisibility(View.INVISIBLE);
            //延迟显示每个子试图(主要动画就体现在这里)
            Observable.timer(i * 50, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Long>() {
                        @Override
                        public void call(Long aLong) {
                            child.setVisibility(View.VISIBLE);
                            ValueAnimator fadeAnim = ObjectAnimator.ofFloat(child, "translationY", 600, 0);
                            fadeAnim.setDuration(300);
                            KickBackAnimator kickAnimator = new KickBackAnimator();
                            kickAnimator.setDuration(150);
                            fadeAnim.setEvaluator(kickAnimator);
                            fadeAnim.start();
                        }
                    });
        }

    }

    /**
     * 关闭动画效果
     *
     * @param layout
     */
    private void closeAnimation(ViewGroup layout) {
        for (int i = 0; i < layout.getChildCount(); i++) {
            final View child = layout.getChildAt(i);
//            if (child.getId() == R.id.ll_close) {
//                continue;
//            }
            Observable.timer((layout.getChildCount() - i - 1) * 30, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Long>() {
                        @Override
                        public void call(Long aLong) {
                            child.setVisibility(View.VISIBLE);
                            ValueAnimator fadeAnim = ObjectAnimator.ofFloat(child, "translationY", 0, 600);
                            fadeAnim.setDuration(200);
                            KickBackAnimator kickAnimator = new KickBackAnimator();
                            kickAnimator.setDuration(100);
                            fadeAnim.setEvaluator(kickAnimator);
                            fadeAnim.start();
                            fadeAnim.addListener(new Animator.AnimatorListener() {

                                @Override
                                public void onAnimationStart(Animator animation) {
                                }

                                @Override
                                public void onAnimationRepeat(Animator animation) {
                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    child.setVisibility(View.INVISIBLE);
                                }

                                @Override
                                public void onAnimationCancel(Animator animation) {
                                }
                            });
                        }
                    });


//            if (child.getId() == R.id.video_window) {
//                Observable.timer((layout.getChildCount() - i) * 30 + 80, TimeUnit.MILLISECONDS)
//                        .subscribeOn(Schedulers.newThread())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new Action1<Long>() {
//                            @Override
//                            public void call(Long aLong) {
//                                dismiss();
//                            }
//                        });
//            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close:
                if (isShowing()) {
                    closeAnimation(contentView);
                    dismiss();
                }
                break;
            case R.id.tv_publish_text:
                //文字
                Intent textIntent = new Intent(mContext, PublishMomentActivity.class);
                textIntent.putExtra("type", 1);
                mContext.startActivity(textIntent);
                dismiss();
                break;
            case R.id.tv_publish_photo:
                //图片
                Intent photoIntent = new Intent(mContext, PublishMomentActivity.class);
                photoIntent.putExtra("type", 2);
                mContext.startActivity(photoIntent);
                dismiss();
                break;
            case R.id.tv_publish_voice:
                //声音
                Intent voiceIntent = new Intent(mContext, PublishMomentActivity.class);
                voiceIntent.putExtra("type", 4);
                mContext.startActivity(voiceIntent);
                dismiss();
                break;
            case R.id.tv_publish_video:
                //视频
                Intent videoIntent = new Intent(mContext, PublishMomentActivity.class);
                videoIntent.putExtra("type", 3);
                mContext.startActivity(videoIntent);
                dismiss();
                break;
            default:
                break;
        }
    }

}
