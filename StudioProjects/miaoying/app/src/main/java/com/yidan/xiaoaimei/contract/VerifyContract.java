package com.yidan.xiaoaimei.contract;

import com.yidan.xiaoaimei.http.base.OnHttpCallBack;
import com.yidan.xiaoaimei.model.CommonEmptyInfo;
import com.yidan.xiaoaimei.model.mine.VerifyInfo;


/**
 * 认证
 * Created by jaydenma on 2017/7/17.
 */

public class VerifyContract {
    /**
     * V视图层
     */
    public interface IVerifyView {

        void showProgress();//可以显示进度条

        void hideProgress();//可以隐藏进度条

        void showInfo(String info);//提示用户,提升友好交互

        void showError(String msg);//发生错误就显示错误信息

        void showVerify(VerifyInfo info);

        String getToken();

        String getType();

        String getTime();

        String getUrl();

        String getFirstImg();

    }

    /**
     * P视图与逻辑处理的连接层
     */
    public interface IVerifyPresenter {
        void getVerify();

        void submitVoice();

        void submitVideo();
    }

    /**
     * 逻辑处理层
     */
    public interface IVerifyModel {
        void getVerify(String token, String type, OnHttpCallBack<VerifyInfo> callBack);

        void submitVoice(String token, String voiceUrl, String voiceTime, OnHttpCallBack<CommonEmptyInfo> callBack);

        void submitVideo(String token, String videoUrl, String videoTime, String firstImg, OnHttpCallBack<CommonEmptyInfo> callBack);
    }


}
