package com.yidan.xiaoaimei.base.adapterutils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 设置RecyclerView item间距
 * Created by jaydenma on 2017/4/11.
 */

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private boolean hasDecoration;
    private int left;
    private int right;
    private int top;
    private int bottom;

    public SpacesItemDecoration(boolean hasDecoration, int left, int right, int top, int bottom) {
        this.hasDecoration = hasDecoration;
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = left;
        outRect.right = right;
        outRect.bottom = bottom;
        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildLayoutPosition(view) == 0 && hasDecoration) {
            outRect.top = 0;
        } else {
            outRect.top = top;
        }
        if (parent.getChildLayoutPosition(view) == 0 && hasDecoration) {
            outRect.left = 0;
        } else {
            outRect.left = left;
        }
    }
}
