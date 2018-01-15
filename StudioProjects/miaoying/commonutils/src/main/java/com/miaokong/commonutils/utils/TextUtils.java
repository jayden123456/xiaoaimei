package com.miaokong.commonutils.utils;

import android.text.TextPaint;
import android.widget.TextView;

/**
 * @author JaydenMa
 */
public final class TextUtils {
    private TextUtils() {
    }

    /**
     * 字体加粗
     *
     * @param textView
     */
    public static void TextBold(TextView textView) {
        TextPaint tp = textView.getPaint();
        tp.setFakeBoldText(true);
    }

    /**
     * 取消加粗
     *
     * @param textView
     */
    public static void TextunBold(TextView textView) {
        TextPaint tp = textView.getPaint();
        tp.setFakeBoldText(false);
    }
}
