package com.yidan.xiaoaimei.ui.activity.user;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.miaokong.commonutils.utils.CheckUtils;
import com.miaokong.commonutils.utils.StringUtil;
import com.yidan.xiaoaimei.R;
import com.yidan.xiaoaimei.base.BaseActivity;
import com.yidan.xiaoaimei.utils.ToastUtilError;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 绑定手机号
 * Created by jaydenma on 2017/10/31.
 */

public class BindMobileActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
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
    @BindView(R.id.tv_bind)
    TextView tvBind;

    @OnClick({R.id.tv_send_verify_code,
            R.id.tv_bind,
            R.id.iv_mobile_clear,
            R.id.iv_code_clear,})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.tv_send_verify_code:
                if (!CheckUtils.checkMobileNum(this, etMobile.getText().toString().trim())) {
                    return;
                }

                break;
            case R.id.tv_bind:
                //绑定手机号
                if (!CheckUtils.checkMobileNum(this, etMobile.getText().toString().trim())) {
                    return;
                }
                if (StringUtil.isEmpty(etVerifyCode.getText().toString().trim())) {
                    ToastUtilError toastUtilError = new ToastUtilError(mActivity, "请输入验证码");
                    toastUtilError.show();
                    return;
                }

                break;
            case R.id.iv_mobile_clear:
                etMobile.setText("");
                break;
            case R.id.iv_code_clear:
                etVerifyCode.setText("");
                break;
            default:
                break;
        }
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_bindmobile;
    }

    @Override
    public void initData() {
        super.initData();
        tvTitle.setText("绑定电话");

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
                    tvSendVerifyCode.setBackgroundResource(R.drawable.common_unclick_button);
                    ivMobileClear.setVisibility(View.GONE);
                } else {
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
                        tvBind.setBackgroundResource(R.drawable.common_unclick_login_button);
                        ivCodeClear.setVisibility(View.GONE);
                    } else {
                        tvBind.setBackgroundResource(R.drawable.common_red_button);
                        ivCodeClear.setVisibility(View.VISIBLE);
                    }
                } else {
                    tvBind.setBackgroundResource(R.drawable.common_unclick_login_button);
                }
            }
        });
    }
}
