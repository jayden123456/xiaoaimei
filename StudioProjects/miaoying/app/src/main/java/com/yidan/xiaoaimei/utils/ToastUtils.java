package com.yidan.xiaoaimei.utils;

import android.content.Context;

/**
 * 吐司工具类
 * Created by jaydenma on 2017/11/1.
 */

public final class ToastUtils {

    public static void ShowSucess(Context context, String msg) {
        ToastUtilSuccess toastUtilSuccess = new ToastUtilSuccess(context, msg);
        toastUtilSuccess.show();
    }

    public static void ShowError(Context context, String msg) {
        ToastUtilError toastUtilError = new ToastUtilError(context, msg);
        toastUtilError.show();
    }

}
