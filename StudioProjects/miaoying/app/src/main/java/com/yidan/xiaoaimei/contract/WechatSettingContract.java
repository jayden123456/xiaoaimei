package com.yidan.xiaoaimei.contract;

import com.yidan.xiaoaimei.http.base.OnHttpCallBack;
import com.yidan.xiaoaimei.model.CommonEmptyInfo;
import com.yidan.xiaoaimei.model.mine.WechatPayInfo;

/**
 * Created by jaydenma on 2017/7/17.
 */

public class WechatSettingContract {
    /**
     * V视图层
     */
    public interface IWechatSettingView {

        void showProgress();//可以显示进度条

        void hideProgress();//可以隐藏进度条

        void showInfo(String info);//提示用户,提升友好交互

        void showErrorMsg(String msg);//发生错误就显示错误信息

        String getToken();

        String getWechatNum();

        String getOptionId();

        void showWechatPay(WechatPayInfo info);

        void sellSuc(int code); //出售成功
    }

    /**
     * P视图与逻辑处理的连接层
     */
    public interface IWechatSettingPresenter {
        void getWechatPay();

        void setWechat();
    }

    /**
     * 逻辑处理层
     */
    public interface IWechatSettingModel {
        void getWechatPay(String token, OnHttpCallBack<WechatPayInfo> callBack);

        void setWechat(String token, String optionId, String wechatNum, OnHttpCallBack<CommonEmptyInfo> callBack);
    }


}
