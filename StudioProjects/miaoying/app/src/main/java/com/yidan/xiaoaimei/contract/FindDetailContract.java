package com.yidan.xiaoaimei.contract;

import com.yidan.xiaoaimei.http.base.OnHttpCallBack;
import com.yidan.xiaoaimei.model.CommonEmptyInfo;
import com.yidan.xiaoaimei.model.find.MomentDetailInfo;
import com.yidan.xiaoaimei.model.find.MomentListInfo;


/**
 * 发现
 * Created by jaydenma on 2017/7/17.
 */

public class FindDetailContract {
    /**
     * V视图层
     */
    public interface IFindDetailView {

        void showProgress();//可以显示进度条

        void hideProgress();//可以隐藏进度条

        void showInfo(String info);//提示用户,提升友好交互

        void showError(String msg);//发生错误就显示错误信息

        void showMoments(MomentDetailInfo info);

        String getToken();

        String getMomentId();

        String getContent();
    }

    /**
     * P视图与逻辑处理的连接层
     */
    public interface IFindDetailPresenter {
        void getFindDetail();

        void comment();

        void praise();

        void buy();
    }

    /**
     * 逻辑处理层
     */
    public interface IFindDetailModel {
        void getFindDetail(String token, String momentId, OnHttpCallBack<MomentDetailInfo> callBack);

        void comment(String token, String momentId, String content, OnHttpCallBack<CommonEmptyInfo> callBack);

        void praise(String token, String momentId, OnHttpCallBack<CommonEmptyInfo> callBack);

        void buy(String token, String momentId, OnHttpCallBack<CommonEmptyInfo> callBack);
    }


}
