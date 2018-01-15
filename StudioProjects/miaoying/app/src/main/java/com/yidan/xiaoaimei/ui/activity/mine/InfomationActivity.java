package com.yidan.xiaoaimei.ui.activity.mine;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.miaokong.commonutils.oss.Constant;
import com.miaokong.commonutils.oss.ImageFactory;
import com.miaokong.commonutils.oss.OssService;
import com.miaokong.commonutils.utils.ImageUtils;
import com.miaokong.commonutils.utils.LoadingDialogUtil;
import com.miaokong.commonutils.utils.StringUtil;
import com.yalantis.ucrop.UCrop;
import com.yidan.xiaoaimei.R;
import com.yidan.xiaoaimei.base.BaseActivity;
import com.yidan.xiaoaimei.contract.InfomationContract;
import com.yidan.xiaoaimei.model.mine.PersonInfo;
import com.yidan.xiaoaimei.model.mine.UpdatePersonInfo;
import com.yidan.xiaoaimei.model.user.CityInfo;
import com.yidan.xiaoaimei.presenter.InfomationPresenter;
import com.yidan.xiaoaimei.ui.dialog.ImageUploadPopwindow;
import com.yidan.xiaoaimei.ui.view.RoundAngleImageView;
import com.yidan.xiaoaimei.utils.MiPictureHelper;
import com.yidan.xiaoaimei.utils.ToastUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.miaokong.commonutils.utils.ImageUtils.REQUEST_CODE_GETIMAGE_BYCAMERA;

/**
 * 基本信息
 * Created by jaydenma on 2017/11/20.
 */

public class InfomationActivity extends BaseActivity implements InfomationContract.IInfomationView {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_head)
    RoundAngleImageView ivHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.tv_height)
    TextView tvHeight;
    @BindView(R.id.tv_size)
    TextView tvSize;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.tv_sign)
    TextView tvSign;

    @OnClick({R.id.iv_back,
            R.id.ll_head,
            R.id.ll_name,
            R.id.ll_sex,
            R.id.ll_age,
            R.id.ll_height,
            R.id.ll_size,
            R.id.ll_city,
            R.id.ll_sign})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_head:
                //头像
                imageUploadPopwindow.show();
                break;
            case R.id.ll_name:
                //名字
                Intent nameIntent = new Intent(mActivity, PersonSetNameActivity.class);
                nameIntent.putExtra("fromType", "name");
                nameIntent.putExtra("data", mPersonInfo.getData().getNickName());
                startActivityForResult(nameIntent, 1000);
                break;
            case R.id.ll_sex:
                //性别
                initSexOptionsPicker();
                break;
            case R.id.ll_age:
                //年龄
                Intent ageIntent = new Intent(mActivity, PersonSetNameActivity.class);
                ageIntent.putExtra("fromType", "age");
                ageIntent.putExtra("data", mPersonInfo.getData().getAge() + "");
                startActivityForResult(ageIntent, 1001);
                break;
            case R.id.ll_height:
                //身高
                Intent heightIntent = new Intent(mActivity, PersonSetNameActivity.class);
                heightIntent.putExtra("fromType", "height");
                heightIntent.putExtra("data", mPersonInfo.getData().getHeight() + "");
                startActivityForResult(heightIntent, 1002);
                break;
            case R.id.ll_size:
                //三围
                Intent sizeIntent = new Intent(mActivity, PersonSetMeasurementsActivity.class);
                if (StringUtil.isEmpty(tvSize.getText().toString().trim()) || tvSize.getText().toString().trim().equals("未设置")) {
                    sizeIntent.putExtra("data", "");
                } else {
                    sizeIntent.putExtra("data", tvSize.getText().toString().trim().substring(0, tvSize.getText().toString().trim().length() - 2));
                }
                startActivityForResult(sizeIntent, 1003);
                break;
            case R.id.ll_city:
                //位置
                initOptionPicker();
                break;
            case R.id.ll_sign:
                //个性签名
                Intent signIntent = new Intent(mActivity, PersonSetSignatureActivity.class);
                signIntent.putExtra("data", mPersonInfo.getData().getSignature());
                startActivityForResult(signIntent, 1004);
                break;
            default:
                break;
        }
    }

    private Dialog loadingDialog;

    private InfomationPresenter infomationPresenter;

    private PersonInfo mPersonInfo = new PersonInfo();

    private int updateType;   //1:性别 2:城市 3:头像

    private ImageUploadPopwindow imageUploadPopwindow;

    //性别
    private ArrayList<String> sex = new ArrayList<String>();

    private String mUpdateSex;

    private String sexStr;  //上传的性别

    //城市相关
    private List<CityInfo.DataBean> city = new ArrayList<CityInfo.DataBean>();

    private ArrayList<String> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();

    private String mCityHeader;
    private String mCityFooter;

    private String cityId; //上传的城市id

    //阿里oss
    public OssService ossService;
    private String mImageUrl = "";      //头像地址
    private String filePath;

    private Uri cameraUri;

    private String theLarge;

    // 处理oss上传后的回调
    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //上传成功
            if (msg.what == Constant.UPLOADOK) {
                LoadingDialogUtil.closeDialog(loadingDialog);
                RequestOptions options = new RequestOptions();
                options.placeholder(R.mipmap.icon_no_head);
                options.error(R.mipmap.icon_no_head);

                Glide.with(mActivity).load("file://" + filePath).apply(options).into(ivHead);

                updateType = 3;
                infomationPresenter.setPersonal();
            }
        }

    };

    @Override
    public int getLayoutResId() {
        return R.layout.activity_infomation;
    }


    @Override
    public void initData() {
        super.initData();
        tvTitle.setText("基本信息");

        //初始化oss
        ossService = ImageFactory.initOSS(this, Constant.ENDPOINT, Constant.BUCKET, null);
        ossService.setHandler(mHandler);

        imageUploadPopwindow = new ImageUploadPopwindow(mActivity, "相册", "拍照", new SelectListener());

        sex.add("女");
        sex.add("男");

        infomationPresenter = new InfomationPresenter(this);
        infomationPresenter.getInfo();
        infomationPresenter.getCity();


    }

    @Override
    public void showProgress() {
        loadingDialog = LoadingDialogUtil.createLoadingDialog(mActivity, "");
    }

    @Override
    public void hideProgress() {
        LoadingDialogUtil.closeDialog(loadingDialog);
    }

    @Override
    public void showInfo(String info) {
        ToastUtils.ShowSucess(mActivity, info);
    }

    @Override
    public void showError(String msg) {
        ToastUtils.ShowError(mActivity, msg);
    }

    @Override
    public void back() {
        ToastUtils.ShowSucess(mActivity, "修改成功");
    }

    @Override
    public UpdatePersonInfo.DataBean getData() {
        UpdatePersonInfo.DataBean data = null;
        switch (updateType) {
            case 1:
                data = new UpdatePersonInfo.DataBean("", sexStr, "", "", "", "", "", "");
                break;
            case 2:
                data = new UpdatePersonInfo.DataBean("", "", "", "", "", "", cityId, "");
                break;
            case 3:
                data = new UpdatePersonInfo.DataBean("", "", "", "", "", mImageUrl, "", "");
                break;
            default:
                break;
        }
        return data;
    }

    @Override
    public String getToken() {
        return spUtil.getStringValue("token", "");
    }

    @Override
    public void showData(PersonInfo info) {
        mPersonInfo = info;

        if (StringUtil.isEmpty(info.getData().getHeadImg())) {
            Glide.with(mActivity).load(R.mipmap.icon_no_head).into(ivHead);
        } else {
            Glide.with(mActivity).load(info.getData().getHeadImg()).into(ivHead);
        }

        if (StringUtil.isEmpty(info.getData().getNickName())) {
            tvName.setText("未设置");
            tvName.setTextColor(getResources().getColor(R.color.commonRed));
        } else {
            tvName.setText(info.getData().getNickName());
            tvName.setTextColor(getResources().getColor(R.color.color9A));
        }
        if (StringUtil.isEmpty(info.getData().getSignature())) {
            tvSign.setText("未设置");
            tvSign.setTextColor(getResources().getColor(R.color.commonRed));
        } else {
            tvSign.setText(info.getData().getSignature());
            tvSign.setTextColor(getResources().getColor(R.color.color9A));
        }

        if (info.getData().getSex() == 1) {
            tvSex.setText("男");
        } else {
            tvSex.setText("女");
        }

        if (!StringUtil.isEmpty(info.getData().getCity())) {
            tvCity.setText(info.getData().getCity());
        } else {
            tvCity.setText("火星");
        }

        if (info.getData().getAge() == 0) {
            tvAge.setText("未设置");
            tvAge.setTextColor(getResources().getColor(R.color.commonRed));
        } else {
            tvAge.setText(info.getData().getAge() + "岁");
            tvAge.setTextColor(getResources().getColor(R.color.color9A));
        }
        if (info.getData().getHeight() == 0) {
            tvHeight.setText("未设置");
            tvHeight.setTextColor(getResources().getColor(R.color.commonRed));
        } else {
            tvHeight.setText(info.getData().getHeight() + "cm");
            tvHeight.setTextColor(getResources().getColor(R.color.color9A));
        }

        if (info.getData().getMeasurements().size() == 0) {
            tvSize.setText("未设置");
            tvSize.setTextColor(getResources().getColor(R.color.commonRed));
        } else {
            if (info.getData().getMeasurements().get(0) == 0 && info.getData().getMeasurements().get(1) == 0 && info.getData().getMeasurements().get(2) == 0) {
                tvSize.setText("未设置");
                tvSize.setTextColor(getResources().getColor(R.color.commonRed));
            } else {
                tvSize.setText(info.getData().getMeasurements().get(0) + "/" + info.getData().getMeasurements().get(1) + "/" + info.getData().getMeasurements().get(2) + "cm");
                tvSize.setTextColor(getResources().getColor(R.color.color9A));
            }
        }

        tvCity.setText(info.getData().getCityInfo().getCityName());


    }

    @Override
    public void showCity(CityInfo cityInfo) {
        city = cityInfo.getData();
        for (CityInfo.DataBean data : city) {
            options1Items.add(data.getArea());
            ArrayList<String> cityList = new ArrayList<>();//该省的城市列表（第二级）
            for (CityInfo.DataBean.SonBean son : data.getSon()) {
                cityList.add(son.getArea());
            }
            options2Items.add(cityList);
        }
    }

    /**
     * 地址选择器
     */
    private void initOptionPicker() {
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                mCityHeader = options1Items.get(options1);
                mCityFooter = options2Items.get(options1).get(options2);
                int id = 0;
                String cityName = "";
                for (int i = 0; i < city.size(); i++) {
                    CityInfo.DataBean dataBean = city.get(i);
                    String area = dataBean.getArea();
                    if (mCityHeader.equals(area)) {
                        List<CityInfo.DataBean.SonBean> son = dataBean.getSon();
                        for (int j = 0; j < son.size(); j++) {
                            CityInfo.DataBean.SonBean sonBean = son.get(j);
                            if (mCityFooter.equals(sonBean.getArea())) {
                                id = sonBean.getId();
                                cityName = sonBean.getArea();
                            }
                        }
                        break;
                    }
                }
                updateType = 2;
                cityId = id + "";
                tvCity.setText(cityName);
                infomationPresenter.setPersonal();
            }
        })
                .setDividerColor(0xFF9A9A9A)
                .setTitleBgColor(0xFFFFFFFF)
                .setCancelColor(0xFF565656)
                .setSubmitColor(0xFF565656)
                .setTextColorCenter(0xFF333333) //设置选中项文字颜色
                .setTextColorOut(0xFFC1C1C1)
                .setContentTextSize(16)
                .setOutSideCancelable(false)// default is true
                .build();
        pvOptions.setPicker(options1Items, options2Items);//二级选择器
        pvOptions.show();
    }


    /**
     * 性别选择器
     */
    private void initSexOptionsPicker() {// 不联动的多级选项
        OptionsPickerView SexOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String index = "";
                mUpdateSex = sex.get(options1);
                tvSex.setText(mUpdateSex);
                if (mUpdateSex.endsWith("男")) {
                    index = "1";
                } else if (mUpdateSex.endsWith("女")) {
                    index = "-1";
                } else {
                    index = "0";
                }
                sexStr = index;
                updateType = 1;
                infomationPresenter.setPersonal();
            }
        }).setCancelColor(Color.WHITE)
                .setSubmitColor(Color.WHITE)
                .setDividerColor(0xFF9A9A9A)
                .setTitleBgColor(0xFFFFFFFF)
                .setCancelColor(0xFF565656)
                .setSubmitColor(0xFF565656)
                .setTextColorCenter(0xFF333333) //设置选中项文字颜色
                .setTextColorOut(0xFFC1C1C1)
                .setContentTextSize(16)
                .build();
        SexOptions.setNPicker(sex, null, null);
        SexOptions.show();
    }

    class SelectListener implements ImageUploadPopwindow.onSelectSexListener {

        @Override
        public void onSelect(int location) {
            Intent intent;
            if (location == 0) {
                //相册
                if (Build.VERSION.SDK_INT < 19) {
                    intent = new Intent();
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent, "选择图片"),
                            ImageUtils.REQUEST_CODE_GETIMAGE_BYSDCARD);

                } else {
                    intent = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent, "选择图片"),
                            ImageUtils.REQUEST_CODE_GETIMAGE_BYSDCARD);
                }

            } else {
                //相机
                // 判断是否挂载了SD卡
                String savePath = "";
                String storageState = Environment.getExternalStorageState();
                if (storageState.equals(Environment.MEDIA_MOUNTED)) {
                    savePath = Environment.getExternalStorageDirectory()
                            .getAbsolutePath() + "/miaokong/Camera/";
                    File savedir = new File(savePath);
                    if (!savedir.exists()) {
                        savedir.mkdirs();
                    }
                }

                // 没有挂载SD卡，无法保存文件
                if (StringUtil.isEmpty(savePath)) {
                    //AppContext.showToastShort("无法保存照片，请检查SD卡是否挂载");
                    return;
                }

                String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss")
                        .format(new Date());
                String fileName = "osc_" + timeStamp + ".jpg";// 照片命名
                File out = new File(savePath, fileName);
                cameraUri = Uri.fromFile(out);

                theLarge = savePath + fileName;// 该照片的绝对路径

                int currentapiVersion = android.os.Build.VERSION.SDK_INT;

                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (currentapiVersion < 24) {
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
                    startActivityForResult(intent,
                            REQUEST_CODE_GETIMAGE_BYCAMERA);
                } else {
                    ContentValues contentValues = new ContentValues(1);
                    contentValues.put(MediaStore.Images.Media.DATA, out.getAbsolutePath());
                    Uri uri2 = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri2);
                    startActivityForResult(intent, REQUEST_CODE_GETIMAGE_BYCAMERA);
                }
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 200) {
            switch (requestCode) {
                case 1000:
                    mPersonInfo.getData().setNickName(data.getStringExtra("data"));
                    tvName.setText(data.getStringExtra("data"));
                    break;
                case 1001:
                    mPersonInfo.getData().setAge(Integer.parseInt(data.getStringExtra("data")));
                    tvAge.setText(data.getStringExtra("data") + "岁");
                    tvAge.setTextColor(getResources().getColor(R.color.color9A));
                    break;
                case 1002:
                    mPersonInfo.getData().setHeight(Integer.parseInt(data.getStringExtra("data")));
                    tvHeight.setText(data.getStringExtra("data") + "cm");
                    tvHeight.setTextColor(getResources().getColor(R.color.color9A));
                    break;
                case 1003:
                    tvSize.setText(data.getStringExtra("data") + "cm");
                    tvSize.setTextColor(getResources().getColor(R.color.color9A));
                    break;
                case 1004:
                    mPersonInfo.getData().setSignature(data.getStringExtra("data"));
                    tvSign.setText(data.getStringExtra("data"));
                    tvSign.setTextColor(getResources().getColor(R.color.color9A));
                    break;
                case 2000:
                    loadingDialog = LoadingDialogUtil.createLoadingDialog(mActivity, "");
                    mImageUrl = "image/" + System.currentTimeMillis() + ".jpg";
                    //开始上传
                    filePath = data.getStringExtra("path");
                    ossService.asyncPutImage(mImageUrl, filePath);
                    break;
                default:
                    break;
            }
        } else if (resultCode == RESULT_OK) {
            if (requestCode == ImageUtils.REQUEST_CODE_GETIMAGE_BYSDCARD) {
                //选择图片
                Uri selectedImage = data.getData();
                String imagePath = MiPictureHelper.getPath(mActivity, selectedImage);
                //裁剪后保存到文件中
                String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss")
                        .format(new Date());
                Uri destinationUri = Uri.fromFile(new File(getCacheDir(), "miaoyingHeadImage" + timeStamp + ".png"));
                UCrop.Options options = new UCrop.Options();
                //开始设置
//                options.setToolbarColor(getResources().getColor(R.color.colorPrimary));
//                options.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
                options.setCompressionFormat(Bitmap.CompressFormat.PNG);
                //结束设置
                DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
                int screenWidth = dm.widthPixels;
                UCrop uCrop = UCrop.of(selectedImage, destinationUri);
                uCrop.withOptions(options);
                uCrop.withAspectRatio(1, 1).withMaxResultSize(screenWidth, screenWidth).start(this);
//                Intent intent = new Intent(mActivity, ClipImageActivity.class);
//                intent.putExtra("path", imagePath);
//                intent.putExtra("fromType", 0);
//                startActivityForResult(intent, 2000);
            } else if (requestCode == ImageUtils.REQUEST_CODE_GETIMAGE_BYCAMERA) {

                //裁剪后保存到文件中
                String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss")
                        .format(new Date());
                Uri destinationUri = Uri.fromFile(new File(getCacheDir(), "miaoyingHeadImage" + timeStamp + ".png"));
                UCrop.Options options = new UCrop.Options();
                //开始设置
//                options.setToolbarColor(getResources().getColor(R.color.colorPrimary));
//                options.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
                options.setCompressionFormat(Bitmap.CompressFormat.PNG);
                //结束设置
                DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
                int screenWidth = dm.widthPixels;
                UCrop uCrop = UCrop.of(cameraUri, destinationUri);
                uCrop.withOptions(options);
                uCrop.withAspectRatio(1, 1).withMaxResultSize(screenWidth, screenWidth).start(this);
            } else if (requestCode == UCrop.REQUEST_CROP) {
                Uri croppedFileUri = UCrop.getOutput(data);
                filePath = MiPictureHelper.getPath(mActivity, croppedFileUri);
                loadingDialog = LoadingDialogUtil.createLoadingDialog(mActivity, "");
                mImageUrl = "image/" + System.currentTimeMillis() + ".jpg";
                //开始上传
                ossService.asyncPutImage(mImageUrl, filePath);
            }

        }
    }


}
