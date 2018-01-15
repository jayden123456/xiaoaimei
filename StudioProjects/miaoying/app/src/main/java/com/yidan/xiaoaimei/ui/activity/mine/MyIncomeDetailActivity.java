package com.yidan.xiaoaimei.ui.activity.mine;

import android.app.Dialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.miaokong.commonutils.utils.LoadingDialogUtil;
import com.yidan.xiaoaimei.R;
import com.yidan.xiaoaimei.base.BaseActivity;
import com.yidan.xiaoaimei.contract.IncomeContract;
import com.yidan.xiaoaimei.model.mine.AuthWithdrawBean;
import com.yidan.xiaoaimei.model.mine.IncomeDetailInfo;
import com.yidan.xiaoaimei.model.mine.WithdrawInfo;
import com.yidan.xiaoaimei.presenter.IncomePresenter;
import com.yidan.xiaoaimei.utils.ToastUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 收益详情
 * Created by jaydenma on 2017/8/3.
 */

public class MyIncomeDetailActivity extends BaseActivity implements IncomeContract.IIncomeView {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_account_detail)
    RecyclerView rvAccountDetail;
    @BindView(R.id.tv_empty)
    TextView tvEmpty;

    @OnClick(R.id.iv_back)
    public void OnClick() {
        finish();
    }

    private TextView tvIncomeNum;

    private Dialog loadingDialog;

    private IncomePresenter incomePresenter;

    private HeaderAndFooterWrapper headerAndFooterWrapper;

    private CommonAdapter<IncomeDetailInfo.DataBean.ListsBean> adapter;

    private LinearLayoutManager layoutManager;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_account_detail;
    }

    @Override
    public void initData() {
        super.initData();
        tvTitle.setText("收益明细");

        incomePresenter = new IncomePresenter(this);
        incomePresenter.getIncomeDetail();

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
        layoutManager = new LinearLayoutManager(mActivity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvAccountDetail.setLayoutManager(layoutManager);
        rvAccountDetail.setHasFixedSize(true);
        adapter = new CommonAdapter<IncomeDetailInfo.DataBean.ListsBean>(mActivity, R.layout.item_account_detail, incomeDetailInfo.getData().getLists()) {
            @Override
            protected void convert(ViewHolder holder, IncomeDetailInfo.DataBean.ListsBean dataBean, int position) {

                holder.setText(R.id.tv_use, dataBean.getPayType());
                holder.setText(R.id.tv_date, dataBean.getCreatedAt());
                holder.setText(R.id.tv_detail, dataBean.getPayAmount() + "");

            }
        };
        headerAndFooterWrapper = new HeaderAndFooterWrapper(adapter);
        View headerView = getLayoutInflater().inflate(R.layout.head_income_detail, null);
        tvIncomeNum = (TextView) headerView.findViewById(R.id.tv_withdraw_num);
        headerAndFooterWrapper.addHeaderView(headerView);
        tvIncomeNum.setText(incomeDetailInfo.getData().getWithdrawalsSum() + "金币");
        rvAccountDetail.setAdapter(headerAndFooterWrapper);
        headerAndFooterWrapper.notifyDataSetChanged();

        tvEmpty.setText("暂无收益明细～");
        if (incomeDetailInfo.getData().getLists() == null || incomeDetailInfo.getData().getLists().size() == 0) {
            tvEmpty.setVisibility(View.VISIBLE);
        } else {
            tvEmpty.setVisibility(View.GONE);
        }
    }

    @Override
    public void withdrawSuc() {

    }

    @Override
    public void timeStart(String msg) {

    }


}
