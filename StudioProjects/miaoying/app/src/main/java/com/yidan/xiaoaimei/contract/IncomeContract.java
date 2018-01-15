package com.yidan.xiaoaimei.contract;


import com.yidan.xiaoaimei.http.base.OnHttpCallBack;
import com.yidan.xiaoaimei.model.CommonEmptyInfo;
import com.yidan.xiaoaimei.model.mine.AuthWithdrawBean;
import com.yidan.xiaoaimei.model.mine.IncomeDetailInfo;
import com.yidan.xiaoaimei.model.mine.WithdrawInfo;
import com.yidan.xiaoaimei.model.user.VerifyCodeInfo;

/**
 * Created by jaydenma on 2017/7/17.
 */

public class IncomeContract {
    /**
     * V视图层
     */
    public interface IIncomeView {

        void showProgress();//可以显示进度条

        void hideProgress();//可以隐藏进度条

        void showInfo(String info);//提示用户,提升友好交互

        void showErrorMsg(String msg);//发生错误就显示错误信息

        void showData(WithdrawInfo info);   //展示提现信息

        AuthWithdrawBean getWithdrawInfo(); //获取提现信息

        String getToken();

        String getPhone();

        void showIncomeDetail(IncomeDetailInfo incomeDetailInfo); //展示收益详情数据

        void withdrawSuc(); //提现成功

        void timeStart(String msg); //按钮倒计时开始
    }

    /**
     * P视图与逻辑处理的连接层
     */
    public interface IIncomePresenter {
        void getWithdrawInfo();

        void authWithdraw();

        void withdraw();

        void getIncomeDetail();

        void getVerifyCode();//获取验证码
    }

    /**
     * 逻辑处理层
     */
    public interface IIncomeModel {
        void getWithdrawInfo(String token, OnHttpCallBack<WithdrawInfo> callBack);

        void authWithdraw(String token, AuthWithdrawBean authWithdrawBean, OnHttpCallBack<CommonEmptyInfo> callBack);

        void withdraw(String token, OnHttpCallBack<CommonEmptyInfo> callBack);

        void getIncomeDetail(String token, OnHttpCallBack<IncomeDetailInfo> callBack);

        void getVerifyCode(String phone, OnHttpCallBack<VerifyCodeInfo> callBack);
    }

}
