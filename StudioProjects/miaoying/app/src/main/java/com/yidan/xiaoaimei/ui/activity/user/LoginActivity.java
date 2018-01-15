package com.yidan.xiaoaimei.ui.activity.user;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.miaokong.commonutils.utils.CheckUtils;
import com.miaokong.commonutils.utils.CountDownTimerUtilsNew;
import com.miaokong.commonutils.utils.LoadingDialogUtil;
import com.miaokong.commonutils.utils.StringUtil;
import com.mob.tools.utils.UIHandler;
import com.yidan.xiaoaimei.R;
import com.yidan.xiaoaimei.base.BaseActivity;
import com.yidan.xiaoaimei.contract.LoginQuickContract;
import com.yidan.xiaoaimei.model.user.AdverInfo;
import com.yidan.xiaoaimei.model.user.UserInfo;
import com.yidan.xiaoaimei.presenter.LoginQuickPresenter;
import com.yidan.xiaoaimei.utils.ToastUtilError;
import com.yidan.xiaoaimei.utils.ToastUtils;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;

/**
 * 登录
 * Created by jaydenma on 2017/10/30.
 */

public class LoginActivity extends BaseActivity implements LoginQuickContract.ILoginQuickView, Handler.Callback {

    @BindView(R.id.et_mobile)
    EditText etMobile;
    @BindView(R.id.iv_mobile_clear)
    ImageView ivMobileClear;
    @BindView(R.id.et_verify_code)
    EditText etVerifyCode;
    @BindView(R.id.iv_code_clear)
    ImageView ivCodeClear;
    @BindView(R.id.tv_send_verify_code)
    TextView tvSendVerifyCode;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.privacy_claim)
    TextView privacyClaim;

    @OnClick({R.id.iv_mobile_clear,
            R.id.iv_code_clear,
            R.id.tv_send_verify_code,
            R.id.tv_login,
            R.id.tv_login_by_wechat,
            R.id.privacy_claim
    })
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_mobile_clear:
                etMobile.setText("");
                break;
            case R.id.iv_code_clear:
                etVerifyCode.setText("");
                break;
            case R.id.tv_send_verify_code:
                mobile = etMobile.getText().toString().trim();
                if (!CheckUtils.checkMobileNum(this, mobile)) {
                    return;
                }
                loginQuickPresenter.getVerifyCode();
                break;
            case R.id.tv_login:
                if (!CheckUtils.checkMobileNum(this, etMobile.getText().toString().trim())) {
                    return;
                }
                if (StringUtil.isEmpty(etVerifyCode.getText().toString().trim())) {
                    ToastUtilError toastUtilError = new ToastUtilError(mActivity, "请输入验证码");
                    toastUtilError.show();
                    return;
                }
                loginQuickPresenter.login();
                break;
            case R.id.tv_login_by_wechat:
                //微信登录

                break;
            case R.id.privacy_claim:
                //隐私条款

                break;
            default:
                break;
        }
    }

    private static final int MSG_AUTH_CANCEL = 1;
    private static final int MSG_AUTH_ERROR = 2;

    private Dialog loadingDialog;

//    private TpLoginPresenter tpLoginPresenter;

    private LoginQuickPresenter loginQuickPresenter;

    private String type;

    private String userId;

    private String data;

    private String mobile;


    @Override
    public int getLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    public void initData() {
        super.initData();

        privacyClaim.setText(Html.fromHtml("登录即代表你同意<font color=#FF2C55>《喵空服务和隐私条款》</font>"));


//        tpLoginPresenter = new TpLoginPresenter(this);
        loginQuickPresenter = new LoginQuickPresenter(this);
        tvLogin.setClickable(false);
        tvSendVerifyCode.setClickable(false);

        etMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 0) {
                    tvSendVerifyCode.setClickable(false);
                    tvSendVerifyCode.setBackgroundResource(R.drawable.common_unclick_button);
                    ivMobileClear.setVisibility(View.GONE);
                } else {
                    tvSendVerifyCode.setClickable(true);
                    tvSendVerifyCode.setBackgroundResource(R.drawable.common_red_button);
                    ivMobileClear.setVisibility(View.VISIBLE);
                }
            }
        });

        etVerifyCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!StringUtil.isEmpty(etMobile.getText().toString().trim())) {
                    if (s.toString().length() == 0) {
                        tvLogin.setBackgroundResource(R.drawable.common_unclick_login_button);
                        ivCodeClear.setVisibility(View.GONE);
                        tvLogin.setClickable(false);
                    } else {
                        tvLogin.setBackgroundResource(R.drawable.common_red_button);
                        ivCodeClear.setVisibility(View.VISIBLE);
                        tvLogin.setClickable(true);
                    }
                } else {
                    tvLogin.setBackgroundResource(R.drawable.common_unclick_login_button);
                    tvLogin.setClickable(false);
                }
            }
        });
    }

    @Override
    public Context getCurContext() {
        return mActivity;
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
    public void showMsg(String info) {
        ToastUtils.ShowSucess(mActivity, info);
    }

    @Override
    public void showError(String msg) {
        ToastUtils.ShowError(mActivity, msg);
    }

    @Override
    public void toMain() {
        Intent intent = new Intent(mActivity, SetInformationActivity.class);
        intent.putExtra("fromType", 0);
        startActivity(intent);
    }

    @Override
    public void toLogin() {

    }

    @Override
    public void toNext() {

    }


    @Override
    public UserInfo getUserLoginInfo() {
        return new UserInfo(etMobile.getText().toString().trim(), etVerifyCode.getText().toString().trim(), spUtil.getStringValue("location", ""));
    }

    @Override
    public String getToken() {
        return spUtil.getStringValue("token", "");
    }

    @Override
    public void timeStart() {
        //获取验证码按钮倒计时开始
        CountDownTimerUtilsNew countDownTimerUtils = new CountDownTimerUtilsNew(tvSendVerifyCode, 60000, 1000);
        countDownTimerUtils.start();
    }

    @Override
    public void showAdverPage(AdverInfo info) {

    }


    private void authorize(final Platform plat) {

        plat.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

                //授权成功
                PlatformDb db = platform.getDb();
                data = db.exportData();
                userId = platform.getDb().getUserId();
                Log.i("logindata", data);

                //登录请求
//                tpLoginPresenter.login();
                plat.removeAccount(true);

            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                if (i == Platform.ACTION_USER_INFOR) {
                    Log.i("loginstatus", "三方登录授权失败");
                    UIHandler.sendEmptyMessage(MSG_AUTH_ERROR, LoginActivity.this);
                }
                throwable.printStackTrace();
            }

            @Override
            public void onCancel(Platform platform, int i) {
                //取消登录
                if (i == Platform.ACTION_USER_INFOR) {
                    UIHandler.sendEmptyMessage(MSG_AUTH_CANCEL, LoginActivity.this);
                }
            }
        });
//        plat.authorize();
        plat.SSOSetting(false);
        plat.showUser(null);

    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case MSG_AUTH_CANCEL:
                //取消登录
                LoadingDialogUtil.closeDialog(loadingDialog);
                break;
            case MSG_AUTH_ERROR:
                //授权失败
                LoadingDialogUtil.closeDialog(loadingDialog);
                break;
            default:
                break;
        }
        return false;
    }
}
