package com.yidan.xiaoaimei.ui.activity.user;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.miaokong.commonutils.utils.SharedPreferencesUtil;
import com.miaokong.commonutils.utils.StringUtil;
import com.yidan.xiaoaimei.Const;
import com.yidan.xiaoaimei.R;
import com.yidan.xiaoaimei.contract.LoginQuickContract;
import com.yidan.xiaoaimei.model.user.AdverInfo;
import com.yidan.xiaoaimei.model.user.UserInfo;
import com.yidan.xiaoaimei.presenter.LoginQuickPresenter;
import com.yidan.xiaoaimei.ui.activity.CommonWebActivity;
import com.yidan.xiaoaimei.ui.activity.MainActivity;
import com.yidan.xiaoaimei.utils.ToastUtilError;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jaydenma on 2017/11/1.
 */

public class SplashActivity extends Activity implements LoginQuickContract.ILoginQuickView {

    private ImageView ivStart;

    private TextView tvTime;

    private LoginQuickPresenter mLoginPresenter;

    private List<String> permissionList = new ArrayList<String>();

    private final int REQUEST_PHONE_PERMISSIONS_MAIN = 1;
    private final int REQUEST_PHONE_PERMISSIONS_LOGIN = 2;

    private SharedPreferencesUtil spUtil;

    private int isToMain = 1;

    private int s = 3; // 时间Delay

    private Handler mHandler = new Handler();

    private Runnable runnable;

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        spUtil = new SharedPreferencesUtil(this, Const.SP_NAME);
        //初始化登录presenter
        mLoginPresenter = new LoginQuickPresenter(this);
//        versionPresenter = new VersionPresenter(this);
//        versionPresenter.getVersion();
        //定位
//        location();


        ivStart = (ImageView) findViewById(R.id.iv_start);
        tvTime = (TextView) findViewById(R.id.tv_time);
        tvTime.setText(String.valueOf("跳过\n" + s + "秒"));
        tvTime.setVisibility(View.GONE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkPermissions();
                goToLoginGuideActivity();
            }
        }, 2000);


    }


    @Override
    public Context getCurContext() {
        return SplashActivity.this;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showMsg(String info) {

    }

    @Override
    public void showError(String msg) {
        ToastUtilError toastUtilError = new ToastUtilError(this, msg);
        toastUtilError.show();
    }

    @Override
    public void toMain() {
        if (permissionList != null && permissionList.size() > 0) {
            getPermissions(REQUEST_PHONE_PERMISSIONS_MAIN);
        } else {
            location();
            isToMain = 1;
//            mLoginPresenter.getAdverPage();
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }
    }

    @Override
    public void toLogin() {
        if (permissionList != null && permissionList.size() > 0) {
            getPermissions(REQUEST_PHONE_PERMISSIONS_LOGIN);
        } else {
            location();
            isToMain = 0;
//            mLoginPresenter.getAdverPage();
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            finish();
        }
    }

    @Override
    public void toNext() {

    }

    @Override
    public UserInfo getUserLoginInfo() {
        return new UserInfo(spUtil.getStringValue("user", ""), spUtil.getStringValue("password", ""));
    }

    @Override
    public String getToken() {
        return spUtil.getStringValue("token", "");
    }

    @Override
    public void timeStart() {

    }

    @Override
    public void showAdverPage(final AdverInfo info) {
        RequestOptions options = new RequestOptions();
        options.placeholder(R.mipmap.start);
        options.error(R.mipmap.start);
        Glide.with(this).load(info.getData().getStartpage()).into(ivStart);
        tvTime.setVisibility(View.VISIBLE);
        //3s倒计时
        final Handler handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                // handler自带方法实现定时器
                try {
                    handler.postDelayed(this, 1000);

                    // 计时器为0时, 开始跳转
                    if (s == 0) {
                        tvTime.setText(String.valueOf("跳过\n0秒"));
                        handler.removeCallbacks(this);
                        if (isToMain == 1) {
                            startActivity(new Intent(SplashActivity.this, MainActivity.class));
                            finish();
                        } else {
                            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                            finish();
                        }
                    } else {
                        s--;
                        tvTime.setText(String.valueOf("跳过\n"
                                + s + "秒"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        mHandler.post(runnable);

        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeCallbacks(runnable);
                if (isToMain == 1) {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }
            }
        });

        ivStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!StringUtil.isEmpty(info.getData().getUrl())) {
                    handler.removeCallbacks(runnable);
                    Intent intent = new Intent(SplashActivity.this, CommonWebActivity.class);
                    intent.putExtra("fromType", 2);
                    intent.putExtra("toWhere", isToMain);
                    intent.putExtra("url", info.getData().getUrl());
                    intent.putExtra("title", "广告页");
                    startActivity(intent);
                    finish();
                }
            }
        });
    }


    /**
     * 有保存用户名自动登录否则
     */
    private void goToLoginGuideActivity() {
        if (!StringUtil.isEmpty(spUtil.getStringValue("token", ""))) {
            mLoginPresenter.refreshToken();
        } else {
            if (permissionList != null && permissionList.size() > 0) {
                getPermissions(REQUEST_PHONE_PERMISSIONS_LOGIN);
            } else {
                isToMain = 0;
//                mLoginPresenter.getAdverPage();
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
//                Intent intent = new Intent(this, LoginGuideActivity.class);
//                startActivity(intent);
//                finish();
            }
        }
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
                        spUtil.putStringValue("location", aMapLocation.getLongitude() + "," + aMapLocation.getLatitude());
                        mLocationClient.stopLocation();
                    }

                } else {
                    //定位失败
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

    @TargetApi(Build.VERSION_CODES.M)
    private void getPermissions(int requestCode) {
        requestPermissions(permissionList.toArray(new String[permissionList.size()]), requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PHONE_PERMISSIONS_MAIN) {
            isToMain = 1;
//            mLoginPresenter.getAdverPage();
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        } else {
            isToMain = 0;
//            mLoginPresenter.getAdverPage();
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            finish();
        }
        location();
    }

    /**
     * 检查权限
     */
    private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.CAMERA);
            }
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
            if (checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.RECORD_AUDIO);
            }
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.READ_PHONE_STATE);
            }
            if (checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.READ_CONTACTS);
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(runnable);
    }

}


