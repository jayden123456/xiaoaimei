package com.yidan.xiaoaimei.contract;

import com.yidan.xiaoaimei.http.base.OnHttpCallBack;
import com.yidan.xiaoaimei.model.CommonEmptyInfo;
import com.yidan.xiaoaimei.model.detail.EvaluateInfo;


/**
 * 评价
 * Created by jaydenma on 2017/7/17.
 */

public class EvaluateContract {
    /**
     * V视图层
     */
    public interface IEvaluateView {

        void showProgress();//可以显示进度条

        void hideProgress();//可以隐藏进度条

        void showInfo(String info);//提示用户,提升友好交互

        void showError(String msg);//发生错误就显示错误信息

        void showEvaluate(EvaluateInfo info);

        String getToken();

        String getEvaId();

        String getUid();
    }

    /**
     * P视图与逻辑处理的连接层
     */
    public interface IEvaluatePresenter {
        void getEvaluate();

        void evaluate();
    }

    /**
     * 逻辑处理层
     */
    public interface IEvaluateModel {
        void getEvaluate(OnHttpCallBack<EvaluateInfo> callBack);

        void evaluate(String token, String evaId, String uid, OnHttpCallBack<CommonEmptyInfo> callBack);
    }


}
