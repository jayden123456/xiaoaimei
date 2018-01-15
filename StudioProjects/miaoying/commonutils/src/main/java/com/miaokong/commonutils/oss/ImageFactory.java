package com.miaokong.commonutils.oss;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;


import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageFactory {
    public static File scal(Uri fileUri) {
        String path = fileUri.getPath();
        File outputFile = new File(path);
        long fileSize = outputFile.length();
        final long fileMaxSize = 500 * 500;
        if (fileSize >= fileMaxSize) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options);
            int height = options.outHeight;
            int width = options.outWidth;

            double scale = Math.sqrt((float) fileSize / fileMaxSize);
            options.outHeight = (int) (height / scale);
            options.outWidth = (int) (width / scale);
            options.inSampleSize = (int) (scale + 0.5);
            options.inJustDecodeBounds = false;

            Bitmap bitmap = BitmapFactory.decodeFile(path, options);
            outputFile = new File(createImageFile().getPath());
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(outputFile);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fos);
                fos.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
//             MiboLog.println("len" + outputFile.length());
            if (!bitmap.isRecycled()) {
                bitmap.recycle();
            } else {
                File tempFile = outputFile;
                outputFile = new File(createImageFile().getPath());
                copyFileUsingFileChannels(tempFile, outputFile);
            }

        }
        return outputFile;

    }

    public static Uri createImageFile() {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        storageDir.mkdirs();
        File image = null;
        try {
            image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Save a file: path for use with ACTION_VIEW intents
        return Uri.fromFile(image);
    }

    public static void copyFileUsingFileChannels(File source, File dest) {
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            try {
                inputChannel = new FileInputStream(source).getChannel();
                outputChannel = new FileOutputStream(dest).getChannel();
                outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } finally {
            try {
                inputChannel.close();
                outputChannel.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * 初始化一个OssService用来上传下载
     *
     * @param endpoint
     * @param bucket
     * @param displayer
     * @return
     */
    public static OssService initOSS(Context context, String endpoint, String bucket, UIDisplayer displayer) {
//        MiboLog.d("ossURL:", endpoint + "    " + bucket);
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(
                Constant.ACCESSKEYID, Constant.ACCESSKEYSECRET);
        //bucket = "mblive-demo";
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        OSS oss = new OSSClient(context.getApplicationContext(), endpoint,
                credentialProvider, conf);
        return new OssService(oss, bucket, displayer);
    }

    /**
     * 打开相机
     *
     * @param activity
     */
    public static void takePhoto(Activity activity) {
        ImageConstant.PhotoClassflag = ImageConstant.HEAD;
        File outputImage = new File(Environment.getExternalStorageDirectory(), "tempImage.jpg");
        File destDir = new File(ImageConstant.ROOT_DIR);
        if (!destDir.exists()) {
            if (!destDir.mkdirs()) {
                Log.d(activity.getLocalClassName(), "DEBUG mkdirs fail");
            }
        }
        try {
            if (outputImage.exists() && outputImage.delete()) {
                Log.d(activity.getLocalClassName(), "DEBUG deletedirs success");
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ImageConstant.imageuri = Uri.fromFile(outputImage);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, ImageConstant.imageuri);
        activity.startActivityForResult(intent, ImageConstant.TAKE_PHOTO);
    }

    /**
     * 从相册选择
     *
     * @param activity
     */
    public static void getUserHeadImage(Activity activity) {
        File outputImage = new File(Environment.getExternalStorageDirectory(), "output.jpg");
        File destDir = new File(ImageConstant.ROOT_DIR);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
//        MiboLog.d("destDir----", destDir.toString());
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ImageConstant.imageuri = Uri.fromFile(outputImage);
        Intent intent = new Intent("android.intent.action.PICK");
        intent.setType("image/*");
        intent.putExtra("crop", true);
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, ImageConstant.imageuri);
        activity.startActivityForResult(intent, ImageConstant.SELECT_PHOTO);
    }

    /**
     * 裁剪照片
     *
     * @param uri
     */
    public static void startPhotoZoom(Activity activity, Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 400);
        intent.putExtra("outputY", 400);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());// 图片格式
        intent.putExtra("scaleUpIfNeeded", true);// 去黑边
        intent.putExtra("return-data", false);
        String fileName = getFileName();
        File destDir = new File(fileName);
        Constant.imageUri = Uri.fromFile(destDir);
        intent.putExtra("output", Constant.imageUri);
//        MiboLog.d("ImageConstant.imageuri-----", ImageConstant.imageuri.toString());
        activity.startActivityForResult(intent, ImageConstant.CROP_PHOTO);
    }

    /**
     * 存储处理结果
     *
     * @return
     */
    public static String saveBitmapFile(Activity activity, Uri uri) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String name = formatter.format(System.currentTimeMillis()) + ".jpg";
        Bitmap bitmap = decodeUriAsBitmap(activity, uri);
        if (bitmap == null) {
//            MiboLog.d("bitmap---", "null");
        }
        FileOutputStream b = null;
        String path = Environment.getExternalStorageDirectory().getPath();
        File file = new File(path + "/myImage/");
        /** 检测文件夹是否存在，不存在则创建文件夹 **/
        if (!file.exists() && !file.isDirectory())
            file.mkdirs();
        String fileName = file.getPath() + "/" + name;
        try {
            b = new FileOutputStream(fileName);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (b != null) {
                    b.flush();
                    b.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileName;
    }
    /**
     * 存储处理结果
     *
     * @return
     */
    public static String saveBitmapFile(Activity activity, Bitmap bitmap) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String name = formatter.format(System.currentTimeMillis()) + ".jpg";
        if (bitmap == null) {
//            MiboLog.d("bitmap---", "null");
        }
        FileOutputStream b = null;
        String path = Environment.getExternalStorageDirectory().getPath();
        File file = new File(path + "/myImage/");
        /** 检测文件夹是否存在，不存在则创建文件夹 **/
        if (!file.exists() && !file.isDirectory())
            file.mkdirs();
        String fileName = file.getPath() + "/" + name;
//        MiboLog.d("BitmapFile---", fileName);
        try {
            b = new FileOutputStream(fileName);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (b != null) {
                    b.flush();
                    b.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        MiboLog.d("fileName", fileName);

        // 其次把文件插入到系统图库
        try {

            MediaStore.Images.Media.insertImage(activity.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
        return fileName;
    }
    private static String getFileName() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String name = formatter.format(System.currentTimeMillis()) + ".jpg";
        String path = Environment.getExternalStorageDirectory().getPath();
        File file = new File(path + "/myImage/");
        /** 检测文件夹是否存在，不存在则创建文件夹 **/
        if (!file.exists() && !file.isDirectory())
            file.mkdirs();
        return file.getPath() + "/" + name;
    }

    /**
     * 把Uri 转换成bitmap
     *
     * @param uri
     * @return
     */
    private static Bitmap decodeUriAsBitmap(Activity activity, Uri uri) {
        Bitmap bitmap = null;
        try {
            // 先通过getContentResolver方法获得一个ContentResolver实例，
            // 调用openInputStream(Uri)方法获得uri关联的数据流stream
            // 把上一步获得的数据流解析成为bitmap
            bitmap = BitmapFactory.decodeStream(activity.getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

}
