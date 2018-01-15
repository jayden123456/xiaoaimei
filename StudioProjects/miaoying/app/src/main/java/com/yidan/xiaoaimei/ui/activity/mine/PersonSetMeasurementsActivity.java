package com.yidan.xiaoaimei.ui.activity.mine;

import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by jaydenma on 2017/7/26.
 */

public class PersonSetMeasurementsActivity extends BaseActivity implements InfomationContract.IInfomationView {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_button_right)
    TextView tvButtonRight;
    @BindView(R.id.et_chest)
    EditText etChest;
    @BindView(R.id.et_waist)
    EditText etWaist;
    @BindView(R.id.et_nates)
    EditText etNates;

    @OnClick({R.id.iv_back, R.id.tv_button_right})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_button_right:
                String chest = etChest.getText().toString().trim();
                String waist = etWaist.getText().toString().trim();
                String nates = etNates.getText().toString().trim();

                if (StringUtil.isEmpty(chest)) {
                    ToastUtils.ShowError(mActivity, "请输入胸围");
                    return;
                }
                if (StringUtil.isEmpty(waist)) {
                    ToastUtils.ShowError(mActivity, "请输入腰围");
                    return;
                }
                if (StringUtil.isEmpty(nates)) {
                    ToastUtils.ShowError(mActivity, "请输入臀围");
                    return;
                }

                sizeStr = chest + "/" + waist + "/" + nates;
                Gson gson = new Gson();
                List<Integer> sizes = new ArrayList<Integer>();
                sizes.add(Integer.parseInt(chest));
                sizes.add(Integer.parseInt(waist));
                sizes.add(Integer.parseInt(nates));
                sizeJsonStr = gson.toJson(sizes);


                infomationPresenter.setPersonal();
                break;
            default:
                break;
        }
    }

    private Dialog loadingDialog;

    private InfomationPresenter infomationPresenter;

    private String data;

    private String sizeStr;
    private String sizeJsonStr;


    @Override
    public int getLayoutResId() {
        return R.layout.activity_set_measurements;
    }

    @Override
    public void initData() {
        super.initData();
        data = getIntent().getStringExtra("data");
        tvButtonRight.setText("完成");
        tvButtonRight.setVisibility(View.VISIBLE);
        tvTitle.setText("三围");

        infomationPresenter = new InfomationPresenter(this);

        if (!StringUtil.isEmpty(data) && !data.equals("未设置")) {
            String[] sizeStr = data.split("/");
            etChest.setText(sizeStr[0]);
            etWaist.setText(sizeStr[1]);
            etNates.setText(sizeStr[2]);
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
    public void back() {
        MineFragment.isChange = 1;
        Intent intent = new Intent(mActivity, InfomationActivity.class);
        intent.putExtra("data", sizeStr);
        setResult(200, intent);
        finish();
    }

    @Override
    public UpdatePersonInfo.DataBean getData() {
        return new UpdatePersonInfo.DataBean("", "", "", "", "", "", "", sizeJsonStr);
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
