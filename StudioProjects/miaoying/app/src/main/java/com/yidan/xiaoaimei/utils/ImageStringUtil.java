package com.yidan.xiaoaimei.utils;

import android.content.Context;
import android.graphics.Point;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import static cn.finalteam.toolsfinal.DeviceUtils.dip2px;


public class ImageStringUtil {

    protected static String Tag = "ImageStringUtil";


    /**
     * 获取缩略图   根据项目需求获取
     * 这里是在淘宝找的图片做的对应的一些处理
     *
     * @param str
     * @param hight
     * @param width
     * @return
     */
    public static String getThumb(String str, int hight, int width) {
        if (TextUtils.isEmpty(str) || str.equals("null")) {
            return str;
        }
        if (str.startsWith("file://")) {
            return str;
        }
        if (str.contains("alicdn.com/imgextra")) {
            int indexStart = str.indexOf(".jpg_") + 5;
            int indexEnd = str.lastIndexOf(".jpg");
            String sizeSrc = str.substring(indexStart, indexEnd);
            String widthAndHight[] = sizeSrc.split("x");
            if (widthAndHight.length == 2) {
                try {
                    int widthSrc = Integer.parseInt(widthAndHight[0]);
                    int hightSrc = Integer.parseInt(widthAndHight[1]);
                    int minSize = Math.min(widthSrc, hightSrc);
                    int size = Math.max(hight, width);

                    int returnSize = Math.min(minSize, size) / 100 == 0 ? 100 : Math.min(minSize, size) / 100 * 100;


                    String newStr = str.substring(0, indexStart) + returnSize + "x" + returnSize + str.substring(indexEnd, str.length());
                    return newStr;
                } catch (NumberFormatException e) {

                }
            }
        }
        return str;
    }

    public static Point getThumbSize(Context context, String str, String size) {
        //获取屏幕宽高
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int windowWidth = dm.widthPixels;
        int WindowHeight = dm.heightPixels;
        int defaultWidth = windowWidth - dip2px(context, 30);
        int defaultHight = windowWidth - dip2px(context, 30);
        Point sizeData = new Point(defaultWidth, defaultHight);
        if (TextUtils.isEmpty(str) || str.equals("null")) {
            return sizeData;
        }
        if (str.startsWith("file://")) {
            return sizeData;
        }
//		if (str.contains("alicdn.com/imgextra")) {
//        int indexStart = str.indexOf(".jpg_") + 5;
//        int indexEnd = str.lastIndexOf(".jpg");
//        String sizeSrc = str.substring(indexStart, indexEnd);
        String sizeSrc = size;
        String widthAndHight[] = sizeSrc.split("x");
        int curWidth = 400, curHeight = 400;
        if (widthAndHight.length == 2) {
            try {
                int widthSrc = Integer.parseInt(widthAndHight[0]);
                int hightSrc = Integer.parseInt(widthAndHight[1]);

                curWidth = widthSrc > defaultWidth ? defaultWidth : widthSrc;
                curHeight = hightSrc * curWidth / widthSrc;
                if (curHeight > defaultWidth && curHeight < WindowHeight) {
                    curHeight = defaultWidth;
                    curWidth = widthSrc * curHeight / hightSrc;
                } else if (curHeight > WindowHeight) {
                    curWidth = defaultWidth / 2;
                    curHeight = curWidth * 4 / 3;
                }
                sizeData.y = curHeight;
                sizeData.x = curWidth;

                return sizeData;
            } catch (NumberFormatException e) {

            }
        }
//		}
        return sizeData;
    }

    /**
     * 获取原图   根据项目需求获取
     * 这里是在淘宝找的图片做的对应的一些处理
     *
     * @param str
     * @return
     */
    public static String getOrg(String str) {
        if (TextUtils.isEmpty(str) || str.equals("null")) {
            return str;
        }
        if (str.startsWith("file://")) {
            return str;
        }
        if (str.contains("alicdn.com/imgextra")) {
            int indexStart = str.indexOf(".jpg_");
            int indexEnd = str.lastIndexOf(".jpg");

            String newStr = str.substring(0, indexStart) + str.substring(indexEnd, str.length());
            return newStr;
        }
        return str;
    }
}
