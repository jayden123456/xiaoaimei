package com.yidan.xiaoaimei.contract;

import com.yidan.xiaoaimei.http.base.OnHttpCallBack;
import com.yidan.xiaoaimei.model.mine.PersonInfo;
import com.yidan.xiaoaimei.model.mine.UpdatePersonInfo;
import com.yidan.xiaoaimei.model.user.CityInfo;


/**
 * 基本资料
 * Created by jaydenma on 2017/7/17.
 */

public class InfomationContract {
    /**
     * V视图层
     */
    public interface IInfomationView {

        void showProgress();//可以显示进度条

        void hideProgress();//可以隐藏进度条

        void showInfo(String info);//提示用户,提升友好交互

        void showError(String msg);//发生错误就显示错误信息

        void back();  //返回设置资料页面

        UpdatePersonInfo.DataBean getData();

        String getToken();

        void showData(PersonInfo info);

        void showCity(CityInfo cityInfo);
    }

    /**
     * P视图与逻辑处理的连接层
     */
    public interface IInfomationPresenter {
        void getInfo();

        void setPersonal();//设置资料

        void getCity();
    }

    /**
     * 逻辑处理层
     */
    public interface IInfomationModel {
        void getInfo(String token, OnHttpCallBack<PersonInfo> callBack);

        void setPersonal(String token, UpdatePersonInfo.DataBean userInfo, OnHttpCallBack<UpdatePersonInfo> callBack);

        void getCity(OnHttpCallBack<CityInfo> callBack);
    }


}
