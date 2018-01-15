package com.yidan.xiaoaimei.contract;

import com.yidan.xiaoaimei.http.base.OnHttpCallBack;
import com.yidan.xiaoaimei.model.mine.VisitorInfo;


/**
 * 访客列表
 * Created by jaydenma on 2017/7/17.
 */

public class VisitorContract {
    /**
     * V视图层
     */
    public interface IVisitorView {

        void showProgress();//可以显示进度条

        void hideProgress();//可以隐藏进度条

        void showInfo(String info);//提示用户,提升友好交互

        void showError(String msg);//发生错误就显示错误信息

        String getToken(); //获取登录token

        int getPageNum();

        void showVisitor(VisitorInfo info);

    }

    /**
     * P视图与逻辑处理的连接层
     */
    public interface IVisitorPresenter {
        void getVisitors();
    }

    /**
     * 逻辑处理层
     */
    public interface IVisitorModel {
        void getVisitors(String token, String pageNum, OnHttpCallBack<VisitorInfo> callBack);
    }


}
