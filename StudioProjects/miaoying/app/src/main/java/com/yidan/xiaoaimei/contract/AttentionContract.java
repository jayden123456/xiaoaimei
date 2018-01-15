package com.yidan.xiaoaimei.contract;

import com.yidan.xiaoaimei.http.base.OnHttpCallBack;
import com.yidan.xiaoaimei.model.mine.AttentionInfo;


/**
 * 关注，粉丝列表
 * Created by jaydenma on 2017/7/17.
 */

public class AttentionContract {
    /**
     * V视图层
     */
    public interface IAttentionView {

        void showProgress();//可以显示进度条

        void hideProgress();//可以隐藏进度条

        void showInfo(String info);//提示用户,提升友好交互

        void showError(String msg);//发生错误就显示错误信息

        String getToken(); //获取登录token

        String getType();  //0=关注列表,1=粉丝列表

        int getPageNum();

        void showAttention(AttentionInfo info);

    }

    /**
     * P视图与逻辑处理的连接层
     */
    public interface IAttentionPresenter {
        void getAttention();
    }

    /**
     * 逻辑处理层
     */
    public interface IAttentionModel {
        void getAttention(String token, String type, String pageNum, OnHttpCallBack<AttentionInfo> callBack);
    }


}
