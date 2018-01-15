package com.yidan.xiaoaimei.ui.activity.mine;

import android.app.Dialog;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.miaokong.commonutils.utils.LoadingDialogUtil;
import com.miaokong.commonutils.utils.StringUtil;
import com.yidan.xiaoaimei.R;
import com.yidan.xiaoaimei.base.BaseActivity;
import com.yidan.xiaoaimei.contract.InfomationContract;
import com.yidan.xiaoaimei.model.mine.PersonInfo;
import com.yidan.xiaoaimei.model.mine.UpdatePersonInfo;
import com.yidan.xiaoaimei.model.user.CityInfo;
import com.yidan.xiaoaimei.presenter.InfomationPresenter;
import com.yidan.xiaoaimei.ui.fragment.main.MineFragment;
import com.yidan.xiaoaimei.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 个性签名设置
 * Created by jaydenma on 2017/8/1.
 */

public class PersonSetSignatureActivity extends BaseActivity implements InfomationContract.IInfomationView {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_button_right)
    TextView tvButtonRight;
    @BindView(R.id.et_signature)
    EditText etSignature;
    @BindView(R.id.tv_num)
    TextView tvNum;

    @OnClick({R.id.iv_back, R.id.tv_button_right})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_button_right:
                if (StringUtil.isEmpty(etSignature.getText().toString().trim())) {
                    ToastUtils.ShowError(mActivity, "请输入个性签名");
                    return;
                }
                infomationPresenter.setPersonal();
                break;
            default:
                break;
        }
    }


    private Dialog loadingDialog;

    private InfomationPresenter infomationPresenter;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_set_signature;
    }

    @Override
    public void initData() {
        super.initData();
        tvTitle.setText("修改签名");
        tvButtonRight.setText("完成");
        tvButtonRight.setVisibility(View.VISIBLE);

        infomationPresenter = new InfomationPresenter(this);

        String signature = getIntent().getStringExtra("data");
        if (!StringUtil.isEmpty(signature)) {
            etSignature.setText(signature);
            etSignature.setSelection(signature.length());
            tvNum.setText("还能输入" + etSignature.length() + "/50 个文字");
        }


        etSignature.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tvNum.setText("还能输入" + s.length() + "/50 个文字");
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
    public void showError(String msg) {

    }


    @Override
    public void back() {
        MineFragment.isChange = 1;
        Intent intent = new Intent(mActivity, InfomationActivity.class);
        intent.putExtra("data", etSignature.getText().toString().trim());
        setResult(200, intent);
        finish();
    }

    @Override
    public UpdatePersonInfo.DataBean getData() {
        return new UpdatePersonInfo.DataBean("", "", "", etSignature.getText().toString().trim(), "", "", "", "");
    }

    @Override
    public String getToken() {
        return spUtil.getStringValue("token", "");
    }

    @Override
    public void showData(PersonInfo info) {

    }

    @Override
    public void showCity(CityInfo cityInfo) {

    }
}
