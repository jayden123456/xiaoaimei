package com.yidan.xiaoaimei.contract;

import com.yidan.xiaoaimei.http.base.OnHttpCallBack;
import com.yidan.xiaoaimei.model.CommonEmptyInfo;
import com.yidan.xiaoaimei.model.user.TagInfo;


/**
 * 标签设置
 * Created by jaydenma on 2017/7/17.
 */

public class TagsContract {
    /**
     * V视图层
     */
    public interface ITagsView {

        void showProgress();//可以显示进度条

        void hideProgress();//可以隐藏进度条

        void showInfo(String info);//提示用户,提升友好交互

        void showError(String msg);//发生错误就显示错误信息

        String getToken(); //获取登录token

        String getTagStr();

        String getServiceStr();

        void showTags(TagInfo info);

        void toNewPage();//跳转下一个页面
    }

    /**
     * P视图与逻辑处理的连接层
     */
    public interface ITagsPresenter {
        void getAllTags();

        void selectTags();
    }

    /**
     * 逻辑处理层
     */
    public interface ITagsModel {
        void getAllTags(String token, OnHttpCallBack<TagInfo> callBack);

        void selectTags(String token, String tagStr, String serviceStr, OnHttpCallBack<CommonEmptyInfo> callBack);
    }


}
