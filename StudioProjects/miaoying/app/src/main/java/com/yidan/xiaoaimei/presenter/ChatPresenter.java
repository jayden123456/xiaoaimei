package com.yidan.xiaoaimei.presenter;

import com.netease.nim.uikit.api.model.gift.GiftListInfo;
import com.yidan.xiaoaimei.contract.ChatContract;
import com.yidan.xiaoaimei.http.base.OnHttpCallBack;
import com.yidan.xiaoaimei.model.chat.ChatModel;


/**
 * 聊天presenter
 * Created by jaydenma on 2017/7/17.
 */

public class ChatPresenter implements ChatContract.IChatPresenter {

    private ChatContract.IChatView mChatView;
    private ChatContract.IChatModel mChatModel;


    public ChatPresenter(ChatContract.IChatView mChatView) {
        this.mChatView = mChatView;
        mChatModel = new ChatModel();
    }

    @Override
    public void getGifts() {
        mChatView.showProgress();
        mChatModel.getGifts(new OnHttpCallBack<GiftListInfo>() {
            @Override
            public void onSuccessful(GiftListInfo giftListInfo) {
                mChatView.hideProgress();
                mChatView.showGifts(giftListInfo);
            }

            @Override
            public void onFaild(String errorMsg) {
                mChatView.hideProgress();
                mChatView.showError(errorMsg);
            }
        });
    }
}
