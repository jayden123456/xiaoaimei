package com.yidan.xiaoaimei.ui.activity.user;

import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bigkoo.pickerview.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.miaokong.commonutils.oss.Constant;
import com.miaokong.commonutils.oss.ImageFactory;
import com.miaokong.commonutils.oss.OssService;
import com.miaokong.commonutils.utils.LoadingDialogUtil;
import com.miaokong.commonutils.utils.StringUtil;
import com.yidan.xiaoaimei.R;
import com.yidan.xiaoaimei.base.BaseActivity;
import com.yidan.xiaoaimei.contract.SetInfoContract;
import com.yidan.xiaoaimei.model.user.CityInfo;
import com.yidan.xiaoaimei.model.user.UserInfo;
import com.yidan.xiaoaimei.presenter.SetInfoPresenter;
import com.yidan.xiaoaimei.ui.dialog.ImageUploadPopwindow;
import com.yidan.xiaoaimei.utils.ToastUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import xxsh.neu.android.com.mylibrary.Luban;
import xxsh.neu.android.com.mylibrary.OnCompressListener;

/**
 * 设置个人资料
 * Created by jaydenma on 2017/10/31.
 */

public class SetInformationActivity extends BaseActivity implements SetInfoContract.ISetInfoView {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.et_nickname)
    EditText etNickname;
    @BindView(R.id.iv_nickname_clear)
    ImageView ivNicknameClear;
    @BindView(R.id.cb_woman)
    RadioButton cbWoman;
    @BindView(R.id.tv_woman)
    TextView tvWoman;
    @BindView(R.id.cb_man)
    RadioButton cbMan;
    @BindView(R.id.tv_man)
    TextView tvMan;
    @BindView(R.id.rl_sex)
    RelativeLayout rlSex;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_next)
    TextView tvNext;
    @BindView(R.id.tv_change_head)
    TextView tvChangeHead;

    @OnClick({R.id.iv_head, R.id.tv_city, R.id.tv_next, R.id.iv_nickname_clear})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:

                break;
            case R.id.iv_head:
                //上传头像
                imageUploadPopwindow.show();
                break;
            case R.id.tv_city:
                initOptionPicker();
                break;
            case R.id.tv_next:
                setInfoPresenter.setPersonal();
                break;
            case R.id.iv_nickname_clear:
                etNickname.setText("");
                break;
            default:
                break;
        }
    }

    private static final int GALLERY_REQUEST_REPLACE_CODE = 1;
    private static final int GALLERY_REQUEST_TAKE_CODE = 2;

    private int fromType;

    private ImageUploadPopwindow imageUploadPopwindow;

    private SetInfoPresenter setInfoPresenter;

    //阿里oss
    public OssService ossService;
    private String mImageUrl = "";      //头像地址
    private String name = "";           //昵称
    private int sex = 0;             //性别
    private String code;    //邀请码

    private String filePath;

    private Dialog loadingDialog;


    //城市相关
    private List<CityInfo.DataBean> city = new ArrayList<CityInfo.DataBean>();

    private ArrayList<String> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();

    private String mCityHeader;
    private String mCityFooter;

    private String cityId; //上传的城市id
    private String location;

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;


    // 处理oss上传后的回调
    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //上传成功
            if (msg.what == Constant.UPLOADOK) {
                LoadingDialogUtil.closeDialog(loadingDialog);
                RequestOptions options = new RequestOptions();
//                options.placeholder(R.drawable.default_icon);
//                options.error(R.drawable.default_icon);
                Glide.with(mActivity).load("file://" + filePath).apply(options).into(ivHead);
                checkInputAllInfo();
            }
        }

    };

    @Override
    public int getLayoutResId() {
        return R.layout.activity_set_information;
    }


    @Override
    public void initData() {
        super.initData();
        tvTitle.setText("个人资料设置");

        fromType = getIntent().getIntExtra("fromType", 0);

        if (!StringUtil.isEmpty(spUtil.getStringValue("headImg", ""))) {
            Glide.with(mActivity).load(spUtil.getStringValue("headImg", "")).into(ivHead);
            tvChangeHead.setVisibility(View.GONE);
        } else {
            tvChangeHead.setVisibility(View.VISIBLE);
        }

        if (!StringUtil.isEmpty(spUtil.getStringValue("nickname", ""))) {
            etNickname.setText(spUtil.getStringValue("nickname", ""));
        }

        //初始化oss
        ossService = ImageFactory.initOSS(this, Constant.ENDPOINT, Constant.BUCKET, null);
        ossService.setHandler(mHandler);

        imageUploadPopwindow = new ImageUploadPopwindow(mActivity, "相册", "拍照", new SelectListener());


        setInfoPresenter = new SetInfoPresenter(this);
        setInfoPresenter.getCity();

        location();

        cbMan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sex = 1;
                    cbWoman.setChecked(!isChecked);
                    checkInputAllInfo();
                }
            }
        });
        cbWoman.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sex = -1;
                    cbMan.setChecked(!isChecked);
                    checkInputAllInfo();
                }
            }
        });

        etNickname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 0) {
                    ivNicknameClear.setVisibility(View.GONE);
                } else {
                    ivNicknameClear.setVisibility(View.VISIBLE);
                }
                checkInputAllInfo();
                name = etNickname.getText().toString().trim();
            }
        });

    }

    /**
     * 判断下一步按钮是否可点击
     */
    private void checkInputAllInfo() {
        if (sex != 0 && !StringUtil.isEmpty(etNickname.getText().toString().trim())) {
            if (!StringUtil.isEmpty(location) || !StringUtil.isEmpty(cityId)) {
                if (!StringUtil.isEmpty(mImageUrl) || !StringUtil.isEmpty(spUtil.getStringValue("headImg", ""))) {
                    tvNext.setClickable(true);
                    tvNext.setBackgroundResource(R.drawable.common_red_button);
                } else {
                    tvNext.setClickable(false);
                    tvNext.setBackgroundResource(R.drawable.common_unclick_login_button);
                }
            } else {
                tvNext.setClickable(false);
                tvNext.setBackgroundResource(R.drawable.common_unclick_login_button);
            }
        } else {
            tvNext.setClickable(false);
            tvNext.setBackgroundResource(R.drawable.common_unclick_login_button);
        }
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
    public String getToken() {
        return spUtil.getStringValue("token", "");
    }

    @Override
    public UserInfo getUserInfo() {
        code = etCode.getText().toString().trim();
        return new UserInfo(mImageUrl, name, sex, code, location, cityId);
    }

    @Override
    public void toNewPage() {
        startActivity(new Intent(mActivity, TagsActivity.class));
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
                cityId = id + "";
                tvCity.setText(cityName);
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


    class SelectListener implements ImageUploadPopwindow.onSelectSexListener {

        @Override
        public void onSelect(int location) {
            if (location == 0) {
                //相册
                GalleryFinal.openGallerySingle(GALLERY_REQUEST_REPLACE_CODE, mOnHanlderResultCallback);
            } else {
                //相机
                GalleryFinal.openCamera(GALLERY_REQUEST_TAKE_CODE, mOnHanlderResultCallback);
            }
        }
    }

    //相册监听
    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            switch (reqeustCode) {
                case GALLERY_REQUEST_REPLACE_CODE://相册
                    upPhotoOss(resultList);
                    break;
                case GALLERY_REQUEST_TAKE_CODE://拍照
                    upPhotoOss(resultList);
                    break;
            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
        }
    };

    /**
     * 对图片进行压缩后上传到oss
     *
     * @param resultList
     */
    private void upPhotoOss(List<PhotoInfo> resultList) {

        if (resultList != null && resultList.size() > 0) {
            loadingDialog = LoadingDialogUtil.createLoadingDialog(mActivity, "上传中");
            for (PhotoInfo photo : resultList) {
                String photoPath = photo.getPhotoPath();
                Compress(photoPath);
            }
        } else {
            ToastUtils.ShowError(mActivity, "你没有选择图片");
        }
    }


    /**
     * 图片压缩上传
     *
     * @param path
     */
    private void Compress(String path) {
        Luban.get(this).load(new File(path)).putGear(Luban.THIRD_GEAR)
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onSuccess(File file) {
                        //图片压缩成功后，发布图片
                        mImageUrl = "image/" + System.currentTimeMillis() + ".jpg";
                        //开始上传
                        filePath = file.getPath();
                        ossService.asyncPutImage(mImageUrl, file.getPath());
                    }

                    @Override
                    public void onError(Throwable e) {
                        LoadingDialogUtil.closeDialog(loadingDialog);
                        //发布失败
                        ToastUtils.ShowError(mActivity, "发布失败");
                    }
                }).launch();

    }

    /**
     * 定位
     */
    private void location() {
        mLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        tvCity.setText(aMapLocation.getCity());
                        spUtil.putStringValue("location", aMapLocation.getLongitude() + "," + aMapLocation.getLatitude());
                        location = aMapLocation.getLongitude() + "," + aMapLocation.getLatitude();
                        mLocationClient.stopLocation();
                    }

                } else {
                    //定位失败
                    tvCity.setText("请选择地址");
                    location = "";
                }

            }
        };
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);

        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();

        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

        //获取一次定位结果：
        mLocationOption.setOnceLocation(true);
        mLocationClient.startLocation();
    }


}
