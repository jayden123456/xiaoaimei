package com.yidan.xiaoaimei.ui.activity.mine;

import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.miaokong.commonutils.utils.CheckUtils;
import com.miaokong.commonutils.utils.CountDownTimerUtilsNew;
import com.miaokong.commonutils.utils.LoadingDialogUtil;
import com.miaokong.commonutils.utils.StringUtil;
import com.yidan.xiaoaimei.R;
import com.yidan.xiaoaimei.base.BaseActivity;
import com.yidan.xiaoaimei.contract.IncomeContract;
import com.yidan.xiaoaimei.model.mine.AuthWithdrawBean;
import com.yidan.xiaoaimei.model.mine.IncomeDetailInfo;
import com.yidan.xiaoaimei.model.mine.WithdrawInfo;
import com.yidan.xiaoaimei.presenter.IncomePresenter;
import com.yidan.xiaoaimei.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 提现提交资料
 * Created by jaydenma on 2017/8/3.
 */

public class WithdrawVerifyActivity extends BaseActivity implements IncomeContract.IIncomeView {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_identity_code)
    EditText etIdentityCode;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_verify_code)
    EditText etVerifyCode;
    @BindView(R.id.tv_get_verifycode)
    TextView tvGetVerifycode;
    @BindView(R.id.et_alipay_code)
    EditText etAlipayCode;

    @OnClick({R.id.iv_back, R.id.tv_submit,R.id.tv_get_verifycode})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_submit:
                //提交
                if (StringUtil.isEmpty(etName.getText().toString().trim())) {
                    ToastUtils.ShowError(mActivity, "名字不能为空");
                    return;
                }
//                if (ImageStringUtil.isEmpty(etIdentityCode.getText().toString().trim())) {
//                    ToastUtils.ShowError(mActivity, "身份证不能为空");
//                    return;
//                }
                if (StringUtil.isEmpty(etPhone.getText().toString().trim())) {
                    ToastUtils.ShowError(mActivity, "手机号码不能为空");
                    return;
                }
                if (StringUtil.isEmpty(etVerifyCode.getText().toString().trim())) {
                    ToastUtils.ShowError(mActivity, "验证码不能为空");
                    return;
                }
                if (StringUtil.isEmpty(etAlipayCode.getText().toString().trim())) {
                    ToastUtils.ShowError(mActivity, "支付宝账号不能为空");
                    return;
                }
                incomePresenter.authWithdraw();

                break;
            case R.id.tv_get_verifycode:
                //获取验证码
                if (!CheckUtils.checkMobileNum(this, etPhone.getText().toString().trim())) {
                    return;
                }
                incomePresenter.getVerifyCode();
                break;
            default:
                break;
        }
    }

    private Dialog loadingDialog;

    private IncomePresenter incomePresenter;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_withdraw_verify;
    }

    @Override
    public void initData() {
        super.initData();
        tvTitle.setText("提现认证");
        incomePresenter = new IncomePresenter(this);
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
        //提交成功返回收益页面
        Intent intent = new Intent(mActivity, MyIncomeActivity.class);
        setResult(200, intent);
        finish();
    }

    @Override
    public void showErrorMsg(String msg) {
        ToastUtils.ShowError(mActivity, msg);
    }

    @Override
    public void showData(WithdrawInfo info) {

    }

    @Override
    public AuthWithdrawBean getWithdrawInfo() {
        return new AuthWithdrawBean(etName.getText().toString().trim(), etIdentityCode.getText().toString().trim(), etAlipayCode.getText().toString().trim(), etPhone.getText().toString().trim(), etVerifyCode.getText().toString().trim());
    }

    @Override
    public String getToken() {
        return spUtil.getStringValue("token", "");
    }

    @Override
    public String getPhone() {
        return etPhone.getText().toString().trim();
    }

    @Override
    public void showIncomeDetail(IncomeDetailInfo incomeDetailInfo) {

    }

    @Override
    public void withdrawSuc() {

    }

    @Override
    public void timeStart(String msg) {
        //获取验证码按钮倒计时开始
        CountDownTimerUtilsNew countDownTimerUtils = new CountDownTimerUtilsNew(tvGetVerifycode, 60000, 1000);
        countDownTimerUtils.start();
    }


}
