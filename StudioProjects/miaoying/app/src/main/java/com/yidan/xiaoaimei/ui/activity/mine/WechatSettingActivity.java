package com.yidan.xiaoaimei.ui.activity.mine;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.miaokong.commonutils.utils.LoadingDialogUtil;
import com.miaokong.commonutils.utils.StringUtil;
import com.yidan.xiaoaimei.R;
import com.yidan.xiaoaimei.base.BaseActivity;
import com.yidan.xiaoaimei.contract.WechatSettingContract;
import com.yidan.xiaoaimei.model.mine.WechatPayInfo;
import com.yidan.xiaoaimei.presenter.WechatSettingPresenter;
import com.yidan.xiaoaimei.ui.dialog.CommonDialog;
import com.yidan.xiaoaimei.ui.fragment.main.MineFragment;
import com.yidan.xiaoaimei.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

import static android.media.CamcorderProfile.get;


/**
 * 设置微信
 * Created by jaydenma on 2017/11/3.
 */

public class WechatSettingActivity extends BaseActivity implements WechatSettingContract.IWechatSettingView {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_wechat)
    EditText etWechat;
    @BindView(R.id.tv_select_text)
    TextView tvSelectText;
    @BindView(R.id.tv_money1)
    TextView tvMoney1;
    @BindView(R.id.tv_money2)
    TextView tvMoney2;
    @BindView(R.id.tv_money3)
    TextView tvMoney3;
    @BindView(R.id.tv_money4)
    TextView tvMoney4;
    @BindView(R.id.tv_money5)
    TextView tvMoney5;
    @BindView(R.id.tv_money6)
    TextView tvMoney6;
    @BindView(R.id.tv_rate)
    TextView tvRate;
    @BindView(R.id.tv_next)
    TextView tvNext;
    @BindView(R.id.iv_wechat_clear)
    ImageView ivWechatClear;


    @OnClick({R.id.iv_back,
            R.id.tv_next,
            R.id.iv_wechat_clear,
            R.id.tv_money1,
            R.id.tv_money2,
            R.id.tv_money3,
            R.id.tv_money4,
            R.id.tv_money5,
            R.id.tv_money6})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_next:
                wechatSettingPresenter.setWechat();
                break;
            case R.id.iv_wechat_clear:
                etWechat.setText("");
                break;
            case R.id.tv_money1:
                tvMoney1.setSelected(true);
                tvMoney2.setSelected(false);
                tvMoney3.setSelected(false);
                tvMoney4.setSelected(false);
                tvMoney5.setSelected(false);
                tvMoney6.setSelected(false);

                optionId = wechatPayInfo.getData().getOptions().get(0).getOptionId() + "";
                tvRate.setText(Html.fromHtml("该出售价格的用户购买概率为<font color=#FF402D>" + wechatPayInfo.getData().getOptions().get(0).getChance() + "</font>哦！"));
                checkCanClick();
                break;
            case R.id.tv_money2:
                tvMoney1.setSelected(false);
                tvMoney2.setSelected(true);
                tvMoney3.setSelected(false);
                tvMoney4.setSelected(false);
                tvMoney5.setSelected(false);
                tvMoney6.setSelected(false);

                optionId = wechatPayInfo.getData().getOptions().get(1).getOptionId() + "";
                tvRate.setText(Html.fromHtml("该出售价格的用户购买概率为<font color=#FF402D>" + wechatPayInfo.getData().getOptions().get(1).getChance() + "</font>哦！"));
                checkCanClick();
                break;
            case R.id.tv_money3:
                tvMoney1.setSelected(false);
                tvMoney2.setSelected(false);
                tvMoney3.setSelected(true);
                tvMoney4.setSelected(false);
                tvMoney5.setSelected(false);
                tvMoney6.setSelected(false);

                optionId = wechatPayInfo.getData().getOptions().get(2).getOptionId() + "";
                tvRate.setText(Html.fromHtml("该出售价格的用户购买概率为<font color=#FF402D>" + wechatPayInfo.getData().getOptions().get(2).getChance() + "</font>哦！"));
                checkCanClick();
                break;
            case R.id.tv_money4:
                tvMoney1.setSelected(false);
                tvMoney2.setSelected(false);
                tvMoney3.setSelected(false);
                tvMoney4.setSelected(true);
                tvMoney5.setSelected(false);
                tvMoney6.setSelected(false);

                optionId = wechatPayInfo.getData().getOptions().get(3).getOptionId() + "";
                tvRate.setText(Html.fromHtml("该出售价格的用户购买概率为<font color=#FF402D>" + wechatPayInfo.getData().getOptions().get(3).getChance() + "</font>哦！"));
                checkCanClick();
                break;
            case R.id.tv_money5:
                tvMoney1.setSelected(false);
                tvMoney2.setSelected(false);
                tvMoney3.setSelected(false);
                tvMoney4.setSelected(false);
                tvMoney5.setSelected(true);
                tvMoney6.setSelected(false);

                optionId = wechatPayInfo.getData().getOptions().get(4).getOptionId() + "";
                tvRate.setText(Html.fromHtml("该出售价格的用户购买概率为<font color=#FF402D>" + wechatPayInfo.getData().getOptions().get(4).getChance() + "</font>哦！"));
                checkCanClick();
                break;
            case R.id.tv_money6:
                tvMoney1.setSelected(false);
                tvMoney2.setSelected(false);
                tvMoney3.setSelected(false);
                tvMoney4.setSelected(false);
                tvMoney5.setSelected(false);
                tvMoney6.setSelected(true);

                optionId = wechatPayInfo.getData().getOptions().get(5).getOptionId() + "";
                tvRate.setText(Html.fromHtml("该出售价格的用户购买概率为<font color=#FF402D>" + wechatPayInfo.getData().getOptions().get(5).getChance() + "</font>哦！"));
                checkCanClick();
                break;
            default:
                break;
        }
    }


    private Dialog loadingDialog;

    private WechatSettingPresenter wechatSettingPresenter;

    private WechatPayInfo wechatPayInfo = new WechatPayInfo();

    private String optionId;

    private CommonDialog commonDialog; //提示弹窗

    private ClipboardManager myClipboard; //剪贴板管理

    private String payNum;

    private int curSelect = 0;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_wechat_setting;
    }

    @Override
    public void initData() {
        super.initData();
        myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        wechatSettingPresenter = new WechatSettingPresenter(this);

        tvNext.setBackgroundResource(R.drawable.common_unclick_login_button);
        tvNext.setClickable(false);

        Intent intent = getIntent();
        tvTitle.setText("微信设置");

//        if (intent.hasExtra("userId")) {
//            userId = intent.getStringExtra("userId");
//        }
//
//        if (fromType == 0) {
//            tvTitle.setText("微信设置");
//            tvButtonRight.setVisibility(View.GONE);
////            tvButtonRight.setText("申诉");
//            type = "1";
//        } else if (fromType == 2) {
//            tvTitle.setText("微信设置");
//            tvButtonRight.setVisibility(View.VISIBLE);
//            tvButtonRight.setText("跳过");
//            ivBack.setVisibility(View.GONE);
//            type = "2";
//        } else if (fromType == 4) {
//            tvTitle.setText("修改微信号");
//            tvButtonRight.setVisibility(View.GONE);
//            ivBack.setVisibility(View.VISIBLE);
//            type = "2";
//        } else {
//            //首次登录设置微信
//            tvTitle.setText("联系方式");
//            tvButtonRight.setVisibility(View.VISIBLE);
//            tvButtonRight.setText("跳过");
//            ivBack.setVisibility(View.GONE);
//            type = "1";
//        }

        wechatSettingPresenter.getWechatPay();


        tvSelectText.setText(Html.fromHtml("请选择<font color=#FF402D>诚意金价格</font>(100金币=1元)"));


        tvMoney1.setSelected(false);
        tvMoney2.setSelected(false);
        tvMoney3.setSelected(false);
        tvMoney4.setSelected(false);
        tvMoney5.setSelected(false);
        tvMoney6.setSelected(false);


        etWechat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 0) {
                    ivWechatClear.setVisibility(View.GONE);
                    tvNext.setBackgroundResource(R.drawable.common_unclick_login_button);
                    tvNext.setClickable(false);
                } else {
                    if (!StringUtil.isEmpty(optionId)) {
                        ivWechatClear.setVisibility(View.VISIBLE);
                        tvNext.setBackgroundResource(R.drawable.common_red_button);
                        tvNext.setClickable(true);
                    } else {
                        ivWechatClear.setVisibility(View.GONE);
                        tvNext.setBackgroundResource(R.drawable.common_unclick_login_button);
                        tvNext.setClickable(false);
                    }
                }
            }
        });

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
    public String getToken() {
        return spUtil.getStringValue("token", "");
    }

    @Override
    public String getWechatNum() {
        return etWechat.getText().toString().trim();
    }

    @Override
    public String getOptionId() {
        return optionId;
    }

    @Override
    public void showWechatPay(WechatPayInfo info) {
        wechatPayInfo = info;

        if (!StringUtil.isEmpty(info.getData().getWechatNum())) {
            etWechat.setText(info.getData().getWechatNum());
            ivWechatClear.setVisibility(View.VISIBLE);
        } else {
            ivWechatClear.setVisibility(View.GONE);
        }
        payNum = info.getData().getWechatPay() + "";

        switch (info.getData().getOptions().size()) {
            case 3:
                tvMoney1.setText(wechatPayInfo.getData().getOptions().get(0).getWechatPay() + "金币");
                tvMoney2.setText(wechatPayInfo.getData().getOptions().get(1).getWechatPay() + "金币");
                tvMoney3.setText(wechatPayInfo.getData().getOptions().get(2).getWechatPay() + "金币");
                tvMoney4.setVisibility(View.INVISIBLE);
                tvMoney5.setVisibility(View.INVISIBLE);
                tvMoney6.setVisibility(View.INVISIBLE);
                break;
            case 4:
                tvMoney1.setText(wechatPayInfo.getData().getOptions().get(0).getWechatPay() + "金币");
                tvMoney2.setText(wechatPayInfo.getData().getOptions().get(1).getWechatPay() + "金币");
                tvMoney3.setText(wechatPayInfo.getData().getOptions().get(2).getWechatPay() + "金币");
                tvMoney4.setText(wechatPayInfo.getData().getOptions().get(3).getWechatPay() + "金币");
                tvMoney5.setVisibility(View.INVISIBLE);
                tvMoney6.setVisibility(View.INVISIBLE);
                break;
            case 5:
                tvMoney1.setText(wechatPayInfo.getData().getOptions().get(0).getWechatPay() + "金币");
                tvMoney2.setText(wechatPayInfo.getData().getOptions().get(1).getWechatPay() + "金币");
                tvMoney3.setText(wechatPayInfo.getData().getOptions().get(2).getWechatPay() + "金币");
                tvMoney4.setText(wechatPayInfo.getData().getOptions().get(3).getWechatPay() + "金币");
                tvMoney5.setText(wechatPayInfo.getData().getOptions().get(4).getWechatPay() + "金币");
                tvMoney6.setVisibility(View.INVISIBLE);
                break;
            case 6:
                tvMoney1.setText(wechatPayInfo.getData().getOptions().get(0).getWechatPay() + "金币");
                tvMoney2.setText(wechatPayInfo.getData().getOptions().get(1).getWechatPay() + "金币");
                tvMoney3.setText(wechatPayInfo.getData().getOptions().get(2).getWechatPay() + "金币");
                tvMoney4.setText(wechatPayInfo.getData().getOptions().get(3).getWechatPay() + "金币");
                tvMoney5.setText(wechatPayInfo.getData().getOptions().get(4).getWechatPay() + "金币");
                tvMoney6.setText(wechatPayInfo.getData().getOptions().get(5).getWechatPay() + "金币");
                break;
            default:
                break;
        }

        if (!StringUtil.isEmpty(payNum) && !payNum.equals("0")) {
            for (int i = 0; i < wechatPayInfo.getData().getOptions().size(); i++) {
                if (payNum.equals(wechatPayInfo.getData().getOptions().get(i).getWechatPay())) {
                    curSelect = i + 1;
                    tvRate.setText(Html.fromHtml("该出售价格的用户购买概率为<font color=#FF402D>" + wechatPayInfo.getData().getOptions().get(i).getChance() + "</font>哦！"));
                }
            }
        } else {
            curSelect = 0;
        }
        switch (curSelect) {
            case 0:
                tvRate.setText(Html.fromHtml("该出售价格的用户购买概率为<font color=#FF402D>0%</font>哦！"));
                tvMoney1.setSelected(false);
                tvMoney2.setSelected(false);
                tvMoney3.setSelected(false);
                tvMoney4.setSelected(false);
                tvMoney5.setSelected(false);
                tvMoney6.setSelected(false);
                break;
            case 1:
                optionId = wechatPayInfo.getData().getOptions().get(0).getOptionId() + "";
                tvMoney1.setSelected(true);
                tvMoney2.setSelected(false);
                tvMoney3.setSelected(false);
                tvMoney4.setSelected(false);
                tvMoney5.setSelected(false);
                tvMoney6.setSelected(false);
                checkCanClick();
                break;
            case 2:
                optionId = wechatPayInfo.getData().getOptions().get(1).getOptionId() + "";
                tvMoney1.setSelected(false);
                tvMoney2.setSelected(true);
                tvMoney3.setSelected(false);
                tvMoney4.setSelected(false);
                tvMoney5.setSelected(false);
                tvMoney6.setSelected(false);
                checkCanClick();
                break;
            case 3:
                optionId = wechatPayInfo.getData().getOptions().get(2).getOptionId() + "";
                tvMoney1.setSelected(false);
                tvMoney2.setSelected(false);
                tvMoney3.setSelected(true);
                tvMoney4.setSelected(false);
                tvMoney5.setSelected(false);
                tvMoney6.setSelected(false);
                checkCanClick();
                break;
            case 4:
                optionId = wechatPayInfo.getData().getOptions().get(3).getOptionId() + "";
                tvMoney1.setSelected(false);
                tvMoney2.setSelected(false);
                tvMoney3.setSelected(false);
                tvMoney4.setSelected(true);
                tvMoney5.setSelected(false);
                tvMoney6.setSelected(false);
                checkCanClick();
                break;
            case 5:
                optionId = wechatPayInfo.getData().getOptions().get(4).getOptionId() + "";
                tvMoney1.setSelected(false);
                tvMoney2.setSelected(false);
                tvMoney3.setSelected(false);
                tvMoney4.setSelected(false);
                tvMoney5.setSelected(true);
                tvMoney6.setSelected(false);
                checkCanClick();
                break;
            case 6:
                optionId = wechatPayInfo.getData().getOptions().get(5).getOptionId() + "";
                tvMoney1.setSelected(false);
                tvMoney2.setSelected(false);
                tvMoney3.setSelected(false);
                tvMoney4.setSelected(false);
                tvMoney5.setSelected(false);
                tvMoney6.setSelected(true);
                checkCanClick();
                break;
            default:
                break;
        }


    }

    @Override
    public void sellSuc(int code) {
        if (code == 26) {
            commonDialog = new CommonDialog(mActivity, "提示", "该微信号已被他人出售，建议提交申诉，取回本人微信号使用权！", "复制微信并申诉", "",false, new appealListener());
            commonDialog.show();

        } else {
            commonDialog = new CommonDialog(mActivity, "设置成功", "您的微信号设置成功", "确认", "",false, new okListener());
            commonDialog.show();
        }
    }

    class okListener implements CommonDialog.OkListener {

        @Override
        public void ok() {
            MineFragment.isChange = 1;
//            ContactActivity.isChange = 1;
            commonDialog.dismiss();
            finish();

        }
    }

    class appealListener implements CommonDialog.OkListener {

        @Override
        public void ok() {
            //复制微信号到申诉页面
            ClipData myClip;
            myClip = ClipData.newPlainText("text", etWechat.getText().toString().trim());//text是内容
            myClipboard.setPrimaryClip(myClip);
//            Intent intent = new Intent(mActivity, WechatAppealActivity.class);
//            intent.putExtra("userId", userId);
//            startActivity(intent);
        }
    }

    private void checkCanClick() {
        if (!StringUtil.isEmpty(etWechat.getText().toString().trim())) {
            ivWechatClear.setVisibility(View.VISIBLE);
            tvNext.setBackgroundResource(R.drawable.common_red_button);
            tvNext.setClickable(true);
        } else {
            ivWechatClear.setVisibility(View.GONE);
            tvNext.setBackgroundResource(R.drawable.common_unclick_login_button);
            tvNext.setClickable(false);
        }
    }
}
