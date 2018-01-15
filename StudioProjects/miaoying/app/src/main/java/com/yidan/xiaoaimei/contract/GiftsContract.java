package com.yidan.xiaoaimei.contract;

import com.yidan.xiaoaimei.http.base.OnHttpCallBack;
import com.yidan.xiaoaimei.model.mine.GiftListInfo;


/**
 * 礼物列表
 * Created by jaydenma on 2017/7/17.
 */

public class GiftsContract {
    /**
     * V视图层
     */
    public interface IGiftsView {

        void showProgress();//可以显示进度条

        void hideProgress();//可以隐藏进度条

        void showInfo(String info);//提示用户,提升友好交互

        void showError(String msg);//发生错误就显示错误信息

        void showGifts(GiftListInfo info);

        String getUid();

    }

    /**
     * P视图与逻辑处理的连接层
     */
    public interface IGiftsPresenter {
        void getGifts();
    }

    /**
     * 逻辑处理层
     */
    public interface IGiftsModel {
        void getGifts(String uid, OnHttpCallBack<GiftListInfo> callBack);
    }


}
