package com.yidan.xiaoaimei.ui.nimutil.session.action;

import com.netease.nim.uikit.business.session.actions.PickImageAction;
import com.yidan.xiaoaimei.R;

import java.io.File;

/**
 * 付费私照
 * Created by jaydenma on 2018/1/12.
 */

public class PhotoPayAction extends PickImageAction {

    public PhotoPayAction() {
        super(R.drawable.message_plus_photo_pay, R.string.input_panel_photo_pay, false);
    }

    @Override
    protected void onPicked(File file) {

    }

}
