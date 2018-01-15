package com.yidan.xiaoaimei.ui.activity.mine;

import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.miaokong.commonutils.utils.LoadingDialogUtil;
import com.yidan.xiaoaimei.R;
import com.yidan.xiaoaimei.base.BaseActivity;
import com.yidan.xiaoaimei.contract.MyAccountContract;
import com.yidan.xiaoaimei.model.mine.BalanceDetailInfo;
import com.yidan.xiaoaimei.model.mine.BalanceInfo;
import com.yidan.xiaoaimei.model.mine.PayOptionsInfo;
import com.yidan.xiaoaimei.presenter.MyAccountPresenter;
import com.yidan.xiaoaimei.utils.ToastUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import butterknife.BindView;
import butterknife.OnClick;

import static android.media.CamcorderProfile.get;


/**
 * 我的账户
 * Created by jaydenma on 2017/7/21.
 */

public class MyAccountActivity extends BaseActivity implements MyAccountContract.IMyAccountView {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_button_right)
    TextView tvButtonRight;
    @BindView(R.id.tv_balance)
    TextView tvBalance;
    @BindView(R.id.rv_recharge_options)
    RecyclerView rvRechargeOptions;
    @BindView(R.id.ll_wechatpay)
    LinearLayout llWechatpay;
    @BindView(R.id.ll_alipay)
    LinearLayout llAlipay;


    @OnClick({R.id.iv_back,
            R.id.tv_button_right,
            R.id.tv_buy,
            R.id.ll_wechatpay,
            R.id.ll_alipay,
            R.id.tv_pay})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_button_right:
                //我的收益
                startActivity(new Intent(mActivity, MyIncomeActivity.class));
                break;
            case R.id.tv_buy:
                //购买记录
//                startActivity(new Intent(mActivity, MyAccountDetailActivity.class));
                break;
            case R.id.ll_wechatpay:
                //微信支付
                payWay = 1;
                llWechatpay.setSelected(true);
                llAlipay.setSelected(false);
                break;
            case R.id.ll_alipay:
                //支付宝支付
                payWay = 2;
                llWechatpay.setSelected(false);
                llAlipay.setSelected(true);
                break;
            case R.id.tv_pay:
                //支付
                myAccountPresenter.recharge();
                break;
            default:
                break;
        }
    }

    private static final int SDK_PAY_FLAG = 1;

    private Dialog loadingDialog;

    private int payWay;  //1微信，2支付宝，3ApplePay

    private String payOptionId;

    private int selectPosition; //充值选项选择的位置

    private CommonAdapter<PayOptionsInfo.DataBean> adapter;

    private LinearLayoutManager layoutManager;

    private MyAccountPresenter myAccountPresenter;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
//                    @SuppressWarnings("unchecked")
//                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
//                    /**
//                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
//                     */
//                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
//                    String resultStatus = payResult.getResultStatus();
//                    // 判断resultStatus 为9000则代表支付成功
//                    if (TextUtils.equals(resultStatus, "9000")) {
//                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
//                        ToastUtil.showToast(mActivity, "充值成功");
//                    } else {
//                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
//                        ToastUtil.showToast(mActivity, "充值失败");
//                    }
//                    myAccountPresenter.getBalance();
                    break;
                }
                default:
                    break;
            }
        }

    };

    @Override
    public int getLayoutResId() {
        return R.layout.activity_my_account;
    }

    @Override
    public void initData() {
        super.initData();
        tvTitle.setText("我的账户");
        tvButtonRight.setVisibility(View.VISIBLE);
        tvButtonRight.setText("我的收益");

        //初始化支付方式
        payWay = 2;
        llWechatpay.setSelected(false);
        llAlipay.setSelected(true);

        //默认选中第一个
        selectPosition = 0;

        myAccountPresenter = new MyAccountPresenter(this);
        myAccountPresenter.getPayOptions();
        myAccountPresenter.getBalance();
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
    public String getPayOptionId() {
        return payOptionId;
    }

    @Override
    public String getPayType() {
        return payWay + "";
    }

    @Override
    public void showOptions(final PayOptionsInfo info) {
        payOptionId = info.getData().get(selectPosition).getId() + "";
        layoutManager = new LinearLayoutManager(mActivity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvRechargeOptions.setLayoutManager(layoutManager);
        rvRechargeOptions.setHasFixedSize(true);
        adapter = new CommonAdapter<PayOptionsInfo.DataBean>(mActivity, R.layout.item_options, info.getData()) {
            @Override
            protected void convert(ViewHolder holder, PayOptionsInfo.DataBean listsBean, int position) {
                holder.setText(R.id.tv_item_num, listsBean.getPayCoin() + "");
                holder.setText(R.id.tv_item_pay, "¥" + listsBean.getPayMoney());
                if (position == selectPosition) {
                    holder.getView(R.id.tv_item_pay).setSelected(true);
                } else {
                    holder.getView(R.id.tv_item_pay).setSelected(false);
                }
            }
        };
        rvRechargeOptions.setAdapter(adapter);

        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if (position != selectPosition) {
                    selectPosition = position;
                    payOptionId = info.getData().get(position).getId() + "";
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    @Override
    public void showBalance(BalanceInfo info) {
        tvBalance.setText(info.getData() + "");
    }

    @Override
    public void toPay(final String orderNo) {
//        Runnable payRunnable = new Runnable() {
//
//            @Override
//            public void run() {
//                PayTask alipay = new PayTask(MyAccountActivity.this);
//                Map<String, String> result = alipay.payV2(orderNo, true);
//
//                Message msg = new Message();
//                msg.what = SDK_PAY_FLAG;
//                msg.obj = result;
//                mHandler.sendMessage(msg);
//            }
//        };
//        // 必须异步调用
//        Thread payThread = new Thread(payRunnable);
//        payThread.start();
    }

    @Override
    public void showBalanceDetail(BalanceDetailInfo info) {

    }


}
