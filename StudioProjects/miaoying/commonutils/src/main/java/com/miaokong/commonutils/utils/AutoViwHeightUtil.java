package com.miaokong.commonutils.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import static android.R.attr.width;

/**
 * 根据屏幕宽高设置view高度
 * Created by jaydenma on 2017/4/10.
 */

public class AutoViwHeightUtil {

    /**
     * 动态设置宽度等于屏幕宽度的控件高度
     *
     * @param context
     * @param view
     * @param width
     * @param height
     */
    public static void setViewHeight(Context context, View view, int width, int height, int distance) {
        //获取屏幕宽高
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        float windowWidth = dm.widthPixels;
        float WindowHeight = dm.heightPixels;

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
        float percent = (windowWidth - distance) / width;
        float currentHeight = percent * height;
        params.height = (int) currentHeight;//设置当前控件布局的高度
        view.setLayoutParams(params);//将设置好的布局参数应用到控件中
    }

    /**
     * 动态设置gridview item宽高
     *
     * @param context
     * @param view
     * @param width
     * @param height
     */
    public static int setGridItemHeight(Context context, View view, int width, int height, int distance, int column) {
        //获取屏幕宽高
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        float windowWidth = dm.widthPixels;
        float WindowHeight = dm.heightPixels;

        ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) view.getLayoutParams();
        float currentwidth = (windowWidth - distance) / column;
        float currentHeight = currentwidth / width * height;
        params.height = (int) currentHeight;//设置当前控件布局的高度
        params.width = (int) currentwidth;//设置当前控件布局的宽度
        view.setLayoutParams(params);//将设置好的布局参数应用到控件中

        return (int)currentwidth;
    }


    /**
     * 设置封面控件宽高
     *
     * @param context
     * @param view
     * @param distance
     * @param column
     */
    public static void setCoverHeight(Context context, View view, int distance, int column) {
        //获取屏幕宽高
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        float windowWidth = dm.widthPixels;
        float WindowHeight = dm.heightPixels;

        ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) view.getLayoutParams();
        float currentwidth = (windowWidth - distance) / column;
        float currentHeight = currentwidth / windowWidth * WindowHeight;
        params.height = (int) currentHeight;//设置当前控件布局的高度
        params.width = (int) currentwidth;//设置当前控件布局的宽度
        view.setLayoutParams(params);//将设置好的布局参数应用到控件中
    }

}
