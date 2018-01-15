package com.yidan.xiaoaimei.ui.activity.mine;

import android.app.Dialog;
import android.content.Intent;
import android.text.InputFilter;
import android.text.InputType;
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
 * 编辑姓名，身高，年龄
 * Created by jaydenma on 2017/7/26.
 */

public class PersonSetNameActivity extends BaseActivity implements InfomationContract.IInfomationView {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_button_right)
    TextView tvButtonRight;
    @BindView(R.id.et_name)
    EditText etName;

    @OnClick({R.id.iv_back, R.id.tv_button_right})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_button_right:
                if (StringUtil.isEmpty(etName.getText().toString().trim())) {
                    switch (fromType) {
                        case "name":
                            ToastUtils.ShowError(mActivity, "请输入昵称");
                            break;
                        case "age":
                            ToastUtils.ShowError(mActivity, "请输入年龄");
                            break;
                        case "height":
                            ToastUtils.ShowError(mActivity, "请输入身高");
                            break;
                        default:
                            break;
                    }
                } else {
                    infomationPresenter.setPersonal();
                }
                break;
            default:
                break;
        }

    }

    private Dialog loadingDialog;

    private InfomationPresenter infomationPresenter;

    private String fromType;   // name:编辑个人资料名字   age  height

    private String data;


    @Override
    public int getLayoutResId() {
        return R.layout.activity_set_name;
    }

    @Override
    public void initData() {
        super.initData();
        fromType = getIntent().getStringExtra("fromType");
        data = getIntent().getStringExtra("data");

        if (fromType.equals("name")) {
            etName.setText(data);
        } else {
            if (StringUtil.isEmpty(data)) {
                etName.setText("");
            } else {
                if (data.equals("0")) {
                    etName.setText("");
                } else {
                    etName.setText(data);
                }
            }
        }

        tvButtonRight.setText("完成");
        tvButtonRight.setVisibility(View.VISIBLE);
        if (fromType.equals("name")) {
            etName.setText(data);
            tvTitle.setText("昵称");
            etName.setInputType(InputType.TYPE_CLASS_TEXT);
            etName.setHint("请输入昵称");
        } else if (fromType.equals("age")) {
            tvTitle.setText("年龄");
            etName.setHint("请输入年龄");
            etName.setInputType(InputType.TYPE_CLASS_NUMBER);
            etName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});
        } else {
            tvTitle.setText("身高");
            etName.setHint("请输入身高");
            etName.setInputType(InputType.TYPE_CLASS_NUMBER);
            etName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});
        }

        infomationPresenter = new InfomationPresenter(this);
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
        MineFragment.isChange = 1;
        Intent intent = new Intent(mActivity, InfomationActivity.class);
        intent.putExtra("data", etName.getText().toString().trim());
        setResult(200, intent);
        finish();
    }

    @Override
    public UpdatePersonInfo.DataBean getData() {
        if (fromType.equals("name")) {
            return new UpdatePersonInfo.DataBean(etName.getText().toString().trim(), "", "", "", "", "", "", "");
        } else if (fromType.equals("age")) {
            return new UpdatePersonInfo.DataBean("", "", etName.getText().toString().trim(), "", "", "", "", "");
        } else {
            return new UpdatePersonInfo.DataBean("", "", "", "", etName.getText().toString().trim(), "", "", "");
        }
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
