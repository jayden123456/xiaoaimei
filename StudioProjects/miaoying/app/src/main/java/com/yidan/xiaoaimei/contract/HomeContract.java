package com.yidan.xiaoaimei.contract;

import com.yidan.xiaoaimei.http.base.OnHttpCallBack;
import com.yidan.xiaoaimei.model.CommonEmptyInfo;
import com.yidan.xiaoaimei.model.home.HomeCommonInfo;


/**
 * 首页
 * Created by jaydenma on 2017/7/17.
 */

public class HomeContract {
    /**
     * V视图层
     */
    public interface IHomeView {

        void showProgress();//可以显示进度条

        void hideProgress();//可以隐藏进度条

        void showInfo(String info);//提示用户,提升友好交互

        void showError(String msg);//发生错误就显示错误信息

        void showHomeData(HomeCommonInfo info);

        String getToken();

        String getType();  //1关注,2推荐,3热门,4最新

        int getPageNum();

        String getFocusType();  //1关注，0取消关注

        String getUid();
    }

    /**
     * P视图与逻辑处理的连接层
     */
    public interface IHomePresenter {
        void getHome();

        void focus();
    }

    /**
     * 逻辑处理层
     */
    public interface IHomeModel {
        void getHome(String token, String type, String pageNum, OnHttpCallBack<HomeCommonInfo> callBack);

        void focus(String token, String type, String uid, OnHttpCallBack<CommonEmptyInfo> callBack);
    }


}
