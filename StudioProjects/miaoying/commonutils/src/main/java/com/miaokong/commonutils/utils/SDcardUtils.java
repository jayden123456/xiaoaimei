package com.miaokong.commonutils.utils;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;


/**
 * @author Jayden
 * @date 2017年7月31日 上午9:15:48
 */
public class SDcardUtils {
    private static SDcardUtils instance;

    private static Context context;

    private static final String[] checkPath = new String[]{
            "/mnt/sdcard1",
            "/mnt/sdcard2",
            "/mnt/sdcard-ext",
            "/mnt/extSdCard"
    };

    private static final long M = 1024 * 1024;
    private static final long K = 1024;

    /**
     * 存储卡默认预警临界值
     */
    private static final long DEF_SDCARD_WARNNING_LIMIT_SPACE_SIZE = 100 * M;

    /**
     * 手机默认预警临界值
     */
    private static final long DEF_PHONE_WARNNING_LIMIT_SPACE_SIZE = 50 * M;

    /**
     * 本地主目录
     */
    public static final String HOME_dir = "miaokong";

    /**
     * 下载目录
     */
    public static final String download_dir = "download/";

    /**
     * 日志目录
     */
    public static final String log_dir = "log/";

    /**
     * 临时目录
     */
    public static final String temp_dir = "temp/";

    /**
     * 图片存储目录
     */
    public static final String image_dir = "image/";

    /**
     * 内置存储大小
     */
    private static long InternalSDCardSpace = 0;

    /**
     * 外置存储大小
     */
    private static long ExternalSDCardSpace = 0;

    /**
     * 内置SD卡路径
     */
    private static String InternalSDCardPath = "";

    /**
     * 外置SD卡路径
     */
    private static String ExternalSDCardPath = "";

    /**
     * 主存储SD卡路径
     */
    private static String SDCardPath = "";

    private static ArrayList<String> ExternalSDCardPath_check = new ArrayList<String>();

    private static final int WAIT_TIME = 3000;
    private static long lastTextChangedTime = 0;
    private static Thread thread;

    private SDcardUtils() {

    }

    public static void init() {
        getInstance();
        initSDcard();
    }

    public static SDcardUtils getInstance() {
        if (instance == null) {
            instance = new SDcardUtils();
        }
        return instance;
    }


    private static void initSDcard() {
        long start = System.currentTimeMillis();
        //内置sd卡
        InternalSDCardPath = Environment.getExternalStorageDirectory().getPath();
        //#debug
        System.out.println("InternalSDCardPath=" + InternalSDCardPath);
        //外置sd卡
        for (String path : checkPath) {
            try {
                StatFs fs = getStatFs(path);
                if (fs != null) {
                    ExternalSDCardPath_check.add(path);
                    //#debug
                    System.out.println("ExternalSDCardPath=" + path);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        InternalSDCardSpace = getInternalSDCardSpace();
        ExternalSDCardSpace = getExternalSDCardSpace();
        SDCardPath = (InternalSDCardSpace > 0 ? InternalSDCardPath : (ExternalSDCardSpace > 0 ? ExternalSDCardPath : null));
        WarnningLimitSpace();
        //#debug
        System.out.println("InternalSDCardSpace:" + InternalSDCardPath + "(" + InternalSDCardSpace + ") " + " ExternalSDCardSpace=" + ExternalSDCardPath + "(" + ExternalSDCardSpace + ") ");
        //#debug
        System.out.println("用时：" + ((System.currentTimeMillis() - start) / 1000) + "秒  SDCardPath=" + SDCardPath);
    }

    private static void WarnningLimitSpace() {
        if (SDCardPath != null) {
            if (context != null && (SDCardPath.equals(InternalSDCardPath) ? InternalSDCardSpace : ExternalSDCardSpace) < DEF_SDCARD_WARNNING_LIMIT_SPACE_SIZE) {
                Toast.makeText(context, SDCardPath + "存储空间不足，请及时清理！", Toast.LENGTH_LONG).show();
            }
        }
    }


    /**
     * 判断SDCARD是否有效
     *
     * @return
     */
    public static boolean isSDCARDMounted() {
        if (isInternalSDCardExist()) return true;
        if (isExternalSDCardExist()) return true;
        return false;
    }

    //	/**
    //	 * 判断SDCARD是否有效
    //	 *
    //	 * @return
    //	 */
    //	public static boolean isSDCARDMounted() {
    //		String status = Environment.getExternalStorageState();
    //		if (Environment.MEDIA_MOUNTED.equals(status)) return true;
    //		return false;
    //	}

    public static String getMainSDCARD() {
        return SDCardPath;
    }

    /**
     * @param @param  context
     * @param @return 设定文件
     * @return File 返回类型
     * @throws
     * @Title: getHomePath
     * @Description: 获取主目录
     * @date 2014年2月12日 下午3:15:37
     */
    public static File getHomePath() {
        File file = new File(getMainSDCARD(), HOME_dir);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    public static File getTempPath() {
        File file = new File(getHomePath(), temp_dir);
        if (!file.exists()) {
            file.mkdir();
        }
        return file;
    }

    public static File getDownloadPath() {
        File file = new File(getHomePath(), download_dir);
        if (!file.exists()) {
            file.mkdir();
        }
        return file;
    }

    public static File getLogPath() {
        File file = new File(getHomePath(), log_dir);
        if (!file.exists()) {
            file.mkdir();
        }
        return file;
    }

    public static File getImagePath() {
        File file = new File(getTempPath(), image_dir);
        if (!file.exists()) {
            file.mkdir();
        }
        return file;
    }


    /**
     * 内置存储卡是否存在
     *
     * @return
     */
    private static boolean isInternalSDCardExist() {
        boolean bExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (!bExist) return false;
        if ((InternalSDCardSpace > 0 ? InternalSDCardSpace : getInternalSDCardSpace()) > 0) { //存在,并且空间大于0
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param @return 设定文件
     * @return long 返回类型
     * @throws
     * @Title: getInternalSDCardSpace
     * @Description: 获取内置存储大小
     * @date 2014年2月12日 上午10:48:04
     */
    private static long getInternalSDCardSpace() {
        StatFs fs = getStatFs(InternalSDCardPath);
        return getSDCardSpace(fs);
    }

    /**
     * 外置存储卡是否存在
     *
     * @return
     */
    private static boolean isExternalSDCardExist() {
        boolean bExist = ExternalSDCardPath_check != null && ExternalSDCardPath_check.size() > 0;
        if (!bExist) return false;
        if ((ExternalSDCardSpace > 0 ? ExternalSDCardSpace : getExternalSDCardSpace()) > 0) { //存在,并且空间大于0
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param @return 设定文件
     * @return long 返回类型
     * @throws
     * @Title: getExternalSDCardSpace
     * @Description: 获取外置存储大小，返回最大的
     * @date 2014年2月12日 上午10:48:04
     */
    private static long getExternalSDCardSpace() {
        long size = 0;
        for (String path : ExternalSDCardPath_check) {

            long space = getSDCardSpace(getStatFs(path));
            if (space > size) {
                size = space;
                ExternalSDCardPath = path;
            }
        }
        return size;
    }

    /**
     * @param path 文件路径
     * @return 文件路径的StatFs对象
     * @throws Exception 路径为空或非法异常抛出
     */
    private static StatFs getStatFs(String path) {
        try {
            return new StatFs(path);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取存储卡剩余空间
     *
     * @return
     */
    private static long getSDCardSpace(StatFs fs) {
        return getResidualSpace(fs);
    }

    /**
     * 获取目录剩余空间
     *
     * @param sf
     * @return
     */
    private static long getResidualSpace(StatFs sf) {
        try {
            long blockSize = sf.getBlockSize();
            long availCount = sf.getAvailableBlocks();
            long availCountByte = availCount * blockSize;
            //LogUtil.d("shmily480", "剩余空间:" + availCountByte / (1024 * 1024) + "M");
            return availCountByte;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static File mkDir(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return file.mkdir() ? file : null;
        }
        return file;
    }
}
