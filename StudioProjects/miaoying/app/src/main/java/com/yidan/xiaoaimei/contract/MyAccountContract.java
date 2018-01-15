package com.yidan.xiaoaimei.contract;

import com.yidan.xiaoaimei.http.base.OnHttpCallBack;
import com.yidan.xiaoaimei.model.CommonInfo;
import com.yidan.xiaoaimei.model.mine.BalanceDetailInfo;
import com.yidan.xiaoaimei.model.mine.BalanceInfo;
import com.yidan.xiaoaimei.model.mine.PayOptionsInfo;

/**
 * Created by jaydenma on 2017/7/17.
 */

public class MyAccountContract {
    /**
     * V视图层
     */
    public interface IMyAccountView {

        void showProgress();//可以显示进度条

        void hideProgress();//可以隐藏进度条

        void showInfo(String info);//提示用户,提升友好交互

        void showErrorMsg(String msg);//发生错误就显示错误信息

        String getToken();

        String getPayOptionId();   //充值数量id

        String getPayType();  //获取充值选项1微信，2支付宝，3ApplePay

        void showOptions(PayOptionsInfo info);

        void showBalance(BalanceInfo info);

        void toPay(String orderNo); //跳转平台支付

        void showBalanceDetail(BalanceDetailInfo info);
    }

    /**
     * P视图与逻辑处理的连接层
     */
    public interface IMyAccountPresenter {
        void recharge();  //充值

        void getPayOptions();  //获取充值选项

        void getBalance();   //获取余额

        void getBalanceDetail();   //获取购买记录
    }

    /**
     * 逻辑处理层
     */
    public interface IMyAccountModel {
        void recharge(String token, String payType, String payOptionId, OnHttpCallBack<CommonInfo> callBack);

        void getPayOptions(OnHttpCallBack<PayOptionsInfo> callBack);

        void getBalance(String token, OnHttpCallBack<BalanceInfo> callBack);

        void getBalanceDetail(String token, OnHttpCallBack<BalanceDetailInfo> callBack);
    }


}
