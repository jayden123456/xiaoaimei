package com.yidan.xiaoaimei.contract;

import com.yidan.xiaoaimei.http.base.OnHttpCallBack;
import com.yidan.xiaoaimei.model.user.CityInfo;
import com.yidan.xiaoaimei.model.user.SetPersonInfo;
import com.yidan.xiaoaimei.model.user.UserInfo;


/**
 * 设置资料
 * Created by jaydenma on 2017/7/17.
 */

public class SetInfoContract {
    /**
     * V视图层
     */
    public interface ISetInfoView {

        void showProgress();//可以显示进度条

        void hideProgress();//可以隐藏进度条

        void showInfo(String info);//提示用户,提升友好交互

        void showError(String msg);//发生错误就显示错误信息

        String getToken(); //获取登录token

        UserInfo getUserInfo();//获取用户信息

        void toNewPage();//跳转下一个页面

        void showCity(CityInfo cityInfo);
    }

    /**
     * P视图与逻辑处理的连接层
     */
    public interface ISetInfoPresenter {
        void getCity();

        void setPersonal();//设置资料
    }

    /**
     * 逻辑处理层
     */
    public interface ISetInfoModel {
        void getCity(OnHttpCallBack<CityInfo> callBack);

        void setPersonal(String token, UserInfo userInfo, OnHttpCallBack<SetPersonInfo> callBack);
    }


}
