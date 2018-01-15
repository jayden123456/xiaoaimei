package com.yidan.xiaoaimei.contract;


import com.yidan.xiaoaimei.http.base.OnHttpCallBack;
import com.yidan.xiaoaimei.model.CommonInfo;
import com.yidan.xiaoaimei.model.mine.MemberOptionsInfo;

/**
 * Created by jaydenma on 2017/7/17.
 */

public class MemberContract {
    /**
     * V视图层
     */
    public interface IMemberView {

        void showProgress();//可以显示进度条

        void hideProgress();//可以隐藏进度条

        void showInfo(String info);//提示用户,提升友好交互

        void showErrorMsg(String msg);//发生错误就显示错误信息

        void showOptions(MemberOptionsInfo info); //显示会员选项

        String getToken();

        String getPayOptionId();   //充值数量id

        String getPayType();  //获取充值选项1微信，2支付宝，3ApplePay

        void toPay(String orderNo); //跳转平台支付
    }

    /**
     * P视图与逻辑处理的连接层
     */
    public interface IMemberPresenter {
        void getMemberOptions();  //获取会员选项

        void recharge();  //充值
    }

    /**
     * 逻辑处理层
     */
    public interface IMemberModel {
        void getMemberOptions(String token, OnHttpCallBack<MemberOptionsInfo> callBack);

        void recharge(String token, String payType, String payOptionId, OnHttpCallBack<CommonInfo> callBack);
    }


}
