package com.yidan.xiaoaimei.contract;

import com.yidan.xiaoaimei.http.base.OnHttpCallBack;
import com.yidan.xiaoaimei.model.CommonEmptyInfo;
import com.yidan.xiaoaimei.model.find.MomentPublishInfo;
import com.yidan.xiaoaimei.model.find.PriceOptionInfo;
import com.yidan.xiaoaimei.model.find.TopicInfo;

/**
 * 发布动态
 * Created by jaydenma on 2017/7/17.
 */

public class MomentPublishContract {
    /**
     * V视图层
     */
    public interface IMomentPublishView {

        void showProgress();//可以显示进度条

        void hideProgress();//可以隐藏进度条

        void showInfo(String info);//提示用户,提升友好交互

        void showError(String msg);//发生错误就显示错误信息

        void showTopics(TopicInfo info);

        void showPrices(PriceOptionInfo info);

        String getToken();

        String getType();  //动态类型 1文字2图片3视频4音频

        MomentPublishInfo getData();

        String getTagId();

        String getOptionId();
    }

    /**
     * P视图与逻辑处理的连接层
     */
    public interface IMomentPublishPresenter {
        void publishMoment();

        void getTopic();

        void getPriceOptions();
    }

    /**
     * 逻辑处理层
     */
    public interface IMomentPublishModel {
        void publishMoment(String token, String type, String tagId, String optionId, MomentPublishInfo momentPublishInfo, OnHttpCallBack<CommonEmptyInfo> callBack);

        void getTopic(OnHttpCallBack<TopicInfo> callBack);

        void getPriceOptions(OnHttpCallBack<PriceOptionInfo> callBack);
    }


}
