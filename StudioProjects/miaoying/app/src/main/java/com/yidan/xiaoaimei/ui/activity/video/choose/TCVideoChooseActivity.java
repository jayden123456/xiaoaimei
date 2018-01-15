package com.yidan.xiaoaimei.ui.activity.video.choose;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.miaokong.commonutils.oss.Constant;
import com.miaokong.commonutils.oss.ImageFactory;
import com.miaokong.commonutils.oss.OssService;
import com.miaokong.commonutils.utils.LoadingDialogUtil;
import com.miaokong.commonutils.utils.ToastUtil;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.ugc.TXVideoEditConstants;
import com.tencent.ugc.TXVideoEditer;
import com.tencent.ugc.TXVideoInfoReader;
import com.yidan.xiaoaimei.R;
import com.yidan.xiaoaimei.base.BaseActivity;
import com.yidan.xiaoaimei.contract.AlbumContract;
import com.yidan.xiaoaimei.model.mine.AlbumInfo;
import com.yidan.xiaoaimei.presenter.AlbumPresenter;
import com.yidan.xiaoaimei.ui.activity.mine.AlbumActivity;
import com.yidan.xiaoaimei.ui.activity.mine.HomepageActivity;
import com.yidan.xiaoaimei.ui.activity.mine.VideoVerifyActivity;
import com.yidan.xiaoaimei.ui.activity.moment.PublishMomentActivity;
import com.yidan.xiaoaimei.ui.view.VideoWorkProgressFragment;
import com.yidan.xiaoaimei.utils.TCConstants;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class TCVideoChooseActivity extends BaseActivity implements ActivityCompat.OnRequestPermissionsResultCallback, TXVideoEditer.TXVideoGenerateListener, AlbumContract.IAlbumView {
    private static final String TAG = TCVideoChooseActivity.class.getSimpleName();

    public static final int TYPE_SINGLE_CHOOSE = 0;
    public static final int TYPE_MULTI_CHOOSE = 1;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @OnClick({R.id.iv_back, R.id.btn_ok})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_ok:
                doSelect();
                break;
            default:
                break;
        }
    }

    private FrameLayout mVideoView;

    private int mType;

    private VideoWorkProgressFragment mWorkProgressDialog;

    private TCVideoEditerListAdapter mAdapter;
    private TCVideoEditerMgr mTCVideoEditerMgr;
    private TXVideoEditer mTXVideoEditer;

    private TXVideoInfoReader mTXVideoInfoReader;

    private String mVideoOutputPath;

    private Handler mHandler;
    private HandlerThread mHandlerThread;

    private TCVideoFileInfo fileInfo = new TCVideoFileInfo();

    private Dialog loadingDialog;

    private AlbumPresenter albumPresenter;

    private List<Video> videos = new ArrayList<Video>();

    private String mVideoUrl;
    private String mCoverUrl;

    private int count = 0;

    private int type;

    //阿里oss
    public OssService ossService;

    private Handler mMainHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            ArrayList<TCVideoFileInfo> fileInfoArrayList = (ArrayList<TCVideoFileInfo>) msg.obj;
            mAdapter.addAll(fileInfoArrayList);
        }
    };

    // 处理oss上传后的回调
    public Handler mossHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //上传成功
            if (msg.what == Constant.UPLOADOK) {
                if (count == 0) {
                    albumPresenter.addVedio();
                    count++;
                }
            }
        }

    };

    @Override
    public int getLayoutResId() {
        return R.layout.activity_ugc_video_list;
    }

    @Override
    public void initData() {
        super.initData();
        tvTitle.setText("选择视频");

        //初始化oss
        ossService = ImageFactory.initOSS(this, Constant.ENDPOINT, Constant.BUCKET, null);
        ossService.setHandler(mossHandler);


        albumPresenter = new AlbumPresenter(this);

        mTXVideoEditer = new TXVideoEditer(this);
        mTXVideoInfoReader = TXVideoInfoReader.getInstance();

        mTCVideoEditerMgr = TCVideoEditerMgr.getInstance(this);
        mHandlerThread = new HandlerThread("LoadList");
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper());

        type = getIntent().getIntExtra("type", 0);

        mType = getIntent().getIntExtra("CHOOSE_TYPE", TYPE_SINGLE_CHOOSE);

        init();
        loadVideoList();
    }


    @Override
    protected void onDestroy() {
        mHandlerThread.getLooper().quit();
        mHandlerThread.quit();
        super.onDestroy();
    }

    private void loadVideoList() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    ArrayList<TCVideoFileInfo> fileInfoArrayList = mTCVideoEditerMgr.getAllVideo();

                    Message msg = new Message();
                    msg.obj = fileInfoArrayList;
                    mMainHandler.sendMessage(msg);
                }
            });
        } else {
            if (Build.VERSION.SDK_INT >= 23) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults != null && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            loadVideoList();
        }
    }

    private void init() {

        mVideoView = (FrameLayout) findViewById(R.id.video_view);

        initWorkProgressPopWin();
//        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));

        mAdapter = new TCVideoEditerListAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        if (mType == TYPE_SINGLE_CHOOSE) {
            mAdapter.setMultiplePick(false);
        } else {
            mAdapter.setMultiplePick(true);
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        Glide.with(this).pauseRequests();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Glide.with(this).resumeRequests();
    }

    private void doSelect() {
        fileInfo = mAdapter.getSingleSelected();
        if (fileInfo == null) {
            TXCLog.d(TAG, "select file null");
            return;
        }
        if (isVideoDamaged(fileInfo)) {
            showErrorDialog("该视频文件已经损坏");
            return;
        }
        File file = new File(fileInfo.getFilePath());
        if (!file.exists()) {
            showErrorDialog("选择的文件不存在");
            return;
        }

        TXVideoEditConstants.TXPreviewParam param = new TXVideoEditConstants.TXPreviewParam();
        param.videoView = mVideoView;
        param.renderMode = TXVideoEditConstants.PREVIEW_RENDER_MODE_FILL_EDGE;
        mTXVideoEditer.setVideoPath(fileInfo.getFilePath());
        mTXVideoEditer.initWithPreview(param);

//        TXVideoEditConstants.TXVideoInfo txVideoInfo = TXVideoInfoReader.getInstance().getVideoFileInfo(fileInfo.getFilePath());
        mWorkProgressDialog.setProgress(0);
        mWorkProgressDialog.setCancelable(false);
        mWorkProgressDialog.show(getFragmentManager(), "progress_dialog");

        try {
            mTXVideoEditer.setCutFromTime(0, (int) fileInfo.getDuration());

            String outputPath = Environment.getExternalStorageDirectory() + File.separator + TCConstants.DEFAULT_MEDIA_PACK_FOLDER;
            File outputFolder = new File(outputPath);

            if (!outputFolder.exists()) {
                outputFolder.mkdirs();
            }
            String current = String.valueOf(System.currentTimeMillis() / 1000);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String time = sdf.format(new Date(Long.valueOf(current + "000")));
            String saveFileName = String.format("TXVideo_%s.mp4", time);
            mVideoOutputPath = outputFolder + "/" + saveFileName;
            mTXVideoEditer.setVideoGenerateListener(this);
            mTXVideoEditer.generateVideo(TXVideoEditConstants.VIDEO_COMPRESSED_720P, mVideoOutputPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 检测视频是否损坏
     *
     * @param info
     * @return
     */
    private boolean isVideoDamaged(TCVideoFileInfo info) {
        if (info.getDuration() == 0) {
            //数据库获取到的时间为0，使用Retriever再次确认是否损坏
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            try {
                retriever.setDataSource(info.getFilePath());
            } catch (Exception e) {
                return true;//无法正常打开，也是错误
            }
            String duration = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            if (TextUtils.isEmpty(duration))
                return true;
            return Integer.valueOf(duration) == 0;
        }
        return false;
    }

    private boolean isVideoDamaged(List<TCVideoFileInfo> list) {
        for (TCVideoFileInfo info : list) {
            if (isVideoDamaged(info)) {
                return true;
            }
        }
        return false;
    }

    private void showErrorDialog(String msg) {
        AlertDialog.Builder normalDialog = new AlertDialog.Builder(TCVideoChooseActivity.this, R.style.ConfirmDialogStyle);
        normalDialog.setMessage(msg);
        normalDialog.setCancelable(false);
        normalDialog.setPositiveButton("知道了", null);
        normalDialog.show();
    }


    private void initWorkProgressPopWin() {
        if (mWorkProgressDialog == null) {
            mWorkProgressDialog = new VideoWorkProgressFragment();
            mWorkProgressDialog.setOnClickStopListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mWorkProgressDialog.dismiss();
                    ToastUtil.showToast(mActivity, "取消视频生成");
                    mWorkProgressDialog.setProgress(0);
                    if (mTXVideoEditer != null) {
                        mTXVideoEditer.cancel();
                    }
                }
            });
        }
        mWorkProgressDialog.setProgress(0);
    }

    @Override
    public void onGenerateProgress(final float progress) {
        final int prog = (int) (progress * 100);
        mWorkProgressDialog.setProgress(prog);
    }

    @Override
    public void onGenerateComplete(TXVideoEditConstants.TXGenerateResult txGenerateResult) {
        Log.e("outPath", mVideoOutputPath + fileInfo.getThumbPath());
        createThumbFile();
        mWorkProgressDialog.dismiss();
    }


    private void createThumbFile() {
        AsyncTask<Void, String, String> task = new AsyncTask<Void, String, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                File outputVideo = new File(mVideoOutputPath);
                if (outputVideo == null || !outputVideo.exists())
                    return null;
                Bitmap bitmap = mTXVideoInfoReader.getSampleImage(0, mVideoOutputPath);
                if (bitmap == null)
                    return null;
                String mediaFileName = outputVideo.getAbsolutePath();
                if (mediaFileName.lastIndexOf(".") != -1) {
                    mediaFileName = mediaFileName.substring(0, mediaFileName.lastIndexOf("."));
                }
                String folder = Environment.getExternalStorageDirectory() + File.separator + TCConstants.DEFAULT_MEDIA_PACK_FOLDER + File.separator + mediaFileName;
                File appDir = new File(folder);
                if (!appDir.exists()) {
                    appDir.mkdirs();
                }

                String fileName = "thumbnail" + ".jpg";
                File file = new File(appDir, fileName);
                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.flush();
                    fos.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (fileInfo.getThumbPath() == null) {
                    fileInfo.setThumbPath(file.getAbsolutePath());
                }

                if (type == 1) {
                    VideoVerifyActivity.isAddVideo = true;
                    VideoVerifyActivity.mVideoPath = mVideoOutputPath;
                    VideoVerifyActivity.mCoverPath = fileInfo.getThumbPath();
                    VideoVerifyActivity.mTime = fileInfo.getDuration() / 1000 + "";
                    finish();
                } else if (type == 2) {
                    PublishMomentActivity.isAddVideo = true;
                    PublishMomentActivity.mVideoPath = mVideoOutputPath;
                    PublishMomentActivity.mCoverPath = fileInfo.getThumbPath();
                    PublishMomentActivity.mTime = fileInfo.getDuration() / 1000 + "";
                    finish();
                } else {
                    mVideoUrl = "video/" + System.currentTimeMillis() + ".mp4";
                    mCoverUrl = "image/" + System.currentTimeMillis() + ".jpg";
                    loadingDialog = LoadingDialogUtil.createLoadingDialog(mActivity, "");
                    ossService.asyncPutImage(mVideoUrl, mVideoOutputPath);
                    ossService.asyncPutImage(mCoverUrl, fileInfo.getThumbPath());
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
//                startPreviewActivity(mResult);
            }
        };
        task.execute();
    }

    @Override
    public void showProgress() {
//        loadingDialog = createLoadingDialog(mActivity, "");
    }

    @Override
    public void hideProgress() {
        LoadingDialogUtil.closeDialog(loadingDialog);
    }

    @Override
    public void showInfo(String info) {
        ToastUtil.showToast(mActivity, info);
    }

    @Override
    public void showErrorMsg(String msg) {
        ToastUtil.showToast(mActivity, msg);
    }

    @Override
    public void showData(List<AlbumInfo.DataBean> data) {

    }


    @Override
    public void uploadSuc() {
        AlbumActivity.changed = 1;
        HomepageActivity.isChange = 1;
        LoadingDialogUtil.closeDialog(loadingDialog);
        finish();
    }

    @Override
    public void deleteSuc() {

    }

    @Override
    public String getToken() {
        return spUtil.getStringValue("token", "");
    }

    @Override
    public String getAlbumType() {
        return "";
    }

    @Override
    public String getDataJsonStr() {
        Video video = new Video();
        video.setVideoUrl(mVideoUrl);
        video.setFirstImg(mCoverUrl);
        video.setVideoTime(fileInfo.getDuration() / 1000 + "");
        videos.add(video);
        Gson gson = new Gson();
        return gson.toJson(videos);
    }

    @Override
    public String getImageId() {
        return null;
    }

    public class Video {
        private String firstImg;
        private String videoUrl;
        private String videoTime;

        public String getFirstImg() {
            return firstImg;
        }

        public void setFirstImg(String firstImg) {
            this.firstImg = firstImg;
        }

        public String getVideoUrl() {
            return videoUrl;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }

        public String getVideoTime() {
            return videoTime;
        }

        public void setVideoTime(String videoTime) {
            this.videoTime = videoTime;
        }
    }
}
