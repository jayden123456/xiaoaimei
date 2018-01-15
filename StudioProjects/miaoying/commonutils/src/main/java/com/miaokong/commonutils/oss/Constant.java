package com.miaokong.commonutils.oss;

import android.net.Uri;

/**
 * @ClassName: Constant
 * @Description:
 */
public class Constant {

    private Constant() {
    }

    public static final int    UPLOADOK   = 0xFF11;
    public static final int    UPPROGRESS = UPLOADOK + 1;
    public static final String ENDPOINT   = "http://oss-cn-hangzhou.aliyuncs.com";
    public static final String BUCKET     = "miaoying-shejiao";

    public static final String ACCESSKEYID     = "LTAIbOaBYoUPwUiN";
    public static final String ACCESSKEYSECRET = "PHj2QTGdQ9ARzKSNIebo2xzDcFHMQj";

    public static final String BUCKET_HEAD  = "user/";
    /**
     * 新浪微博APP_ID
     */
    public static final String SINA_APP_ID  = "2395468653";
    public static final String REDIRECT_URL = "http://114.215.189.7";

    /**
     * 新浪微博APP_SECRET
     */
    public static final String SINA_APP_SECRET = "1091767a00539fbc42fcf177e6472495";

    /**
     * 微信 APP_ID
     */
    public static final String WECHAT_APP_ID     = "wxf91a7e689f98d15c";
    /**
     * 微信 APP_SECRETFF
     */
    public static final String WECHAT_APP_SECRET = "0680cbdae4c7ecef8f92c9fc17654af2";//"0680cbdae4c7ecef8f92c9fc17654af2";
    /**
     * QQ APP_ID
     **/
    public static final String QQ_APP_ID         = "1105405817";
    /**
     * QQ APP_SECRET
     **/
    public static final String QQ_APP_SECRET     = "JlKpvmuUcGTTiWz6";


    public static Uri imageUri = null;


}
