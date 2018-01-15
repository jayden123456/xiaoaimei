package com.yidan.xiaoaimei.ui.activity.mine;

import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.miaokong.commonutils.utils.LoadingDialogUtil;
import com.miaokong.commonutils.utils.StringUtil;
import com.yidan.xiaoaimei.R;
import com.yidan.xiaoaimei.base.BaseActivity;
import com.yidan.xiaoaimei.contract.IncomeContract;
import com.yidan.xiaoaimei.model.mine.AuthWithdrawBean;
import com.yidan.xiaoaimei.model.mine.IncomeDetailInfo;
import com.yidan.xiaoaimei.model.mine.WithdrawInfo;
import com.yidan.xiaoaimei.presenter.IncomePresenter;
import com.yidan.xiaoaimei.ui.dialog.CommonDialog;
import com.yidan.xiaoaimei.utils.MoneyTransformUtil;
import com.yidan.xiaoaimei.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 我的收益
 * Created by jaydenma on 2017/7/25.
 */

public class MyIncomeActivity extends BaseActivity implements IncomeContract.IIncomeView {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_income_num)
    TextView tvIncomeNum;
    @BindView(R.id.tv_income_money)
    TextView tvIncomeMoney;
    @BindView(R.id.tv_alipay)
    TextView tvAlipay;
    @BindView(R.id.tv_button_right)
    TextView tvButtonRight;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.tv_prompt)
    TextView tvPrompt;

    @OnClick({R.id.iv_back, R.id.tv_submit, R.id.tv_button_right})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_button_right:
                //收益明细
                startActivity(new Intent(mActivity, MyIncomeDetailActivity.class));
                break;
            case R.id.tv_submit:
                //提交
                if (StringUtil.isEmpty(withdrawInfo.getData().getAliPayAccount())) {
                    //跳转认证提现页面
                    Intent intent = new Intent(mActivity, WithdrawVerifyActivity.class);
                    startActivityForResult(intent, 1000);
                } else {
                    if (withdrawInfo.getData().getCanCash() < 20000) {
                        //小于200元不能提现
                        dialog = new CommonDialog(mActivity, "提示", "收益余额不足200，无法提现", "确认","",false, new CommonDialog.OkListener() {
                            @Override
                            public void ok() {

                            }
                        });
                        dialog.show();
                    } else {
                        //提现
                        incomePresenter.withdraw();
                    }
                }
                break;
            default:
                break;
        }
    }

    private Dialog loadingDialog;

    private IncomePresenter incomePresenter;

    private WithdrawInfo withdrawInfo = new WithdrawInfo();

    private CommonDialog dialog;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_income;
    }

    @Override
    public void initData() {
        super.initData();
        tvTitle.setText("我的收益");
        tvButtonRight.setText("收益明细");
        tvButtonRight.setVisibility(View.VISIBLE);

        incomePresenter = new IncomePresenter(this);
        incomePresenter.getWithdrawInfo();
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
    public void showErrorMsg(String msg) {
        ToastUtils.ShowError(mActivity, msg);
    }

    @Override
    public void showData(WithdrawInfo info) {
        //显示提现信息
        withdrawInfo = info;

        if (StringUtil.isEmpty(withdrawInfo.getData().getAliPayAccount())) {
            tvSubmit.setText("提交资料");
            tvAlipay.setVisibility(View.INVISIBLE);
        } else {
            tvSubmit.setText("提现");
            tvAlipay.setVisibility(View.VISIBLE);
            tvAlipay.setText(withdrawInfo.getData().getAliPayAccount());
        }

        tvIncomeNum.setText(withdrawInfo.getData().getCanCash() + "金币");
        tvIncomeMoney.setText(MoneyTransformUtil.goldToMoney(withdrawInfo.getData().getCanCash() + "") + "元");

        tvPrompt.setText("提示：提现金额大于200元时可提现，实际到帐金额为提现金额扣除相应所得税后的金额。如需修改提现账号，请联系客服微信" + withdrawInfo.getData().getServiceWechat() + "进行修改。");
    }

    @Override
    public AuthWithdrawBean getWithdrawInfo() {
        return null;
    }

    @Override
    public String getToken() {
        return spUtil.getStringValue("token", "");
    }

    @Override
    public String getPhone() {
        return null;
    }

    @Override
    public void showIncomeDetail(IncomeDetailInfo incomeDetailInfo) {

    }

    @Override
    public void withdrawSuc() {
        //提现成功回调
        dialog = new CommonDialog(mActivity, "提示", "提现成功！将会在7个工作日内审核后打款至绑定支付宝。", "确认","",false, new CommonDialog.OkListener() {
            @Override
            public void ok() {
                incomePresenter.getWithdrawInfo();
            }
        });
        dialog.show();
    }

    @Override
    public void timeStart(String msg) {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 200) {
            if (requestCode == 1000) {
                incomePresenter.getWithdrawInfo();
            }
        }
    }
}
