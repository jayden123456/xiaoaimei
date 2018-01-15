package com.yidan.xiaoaimei.ui.activity.mine;

import android.app.Dialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.miaokong.commonutils.utils.LoadingDialogUtil;
import com.yidan.xiaoaimei.R;
import com.yidan.xiaoaimei.base.BaseActivity;
import com.yidan.xiaoaimei.contract.GiftsContract;
import com.yidan.xiaoaimei.model.mine.GiftListInfo;
import com.yidan.xiaoaimei.presenter.GiftsPresenter;
import com.yidan.xiaoaimei.ui.view.RoundAngleImageView;
import com.yidan.xiaoaimei.utils.ToastUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 礼物列表
 * Created by jaydenma on 2017/11/24.
 */

public class MyGiftsActivity extends BaseActivity implements GiftsContract.IGiftsView {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_gift)
    RecyclerView rvGift;

    @OnClick(R.id.iv_back)
    public void OnClick() {
        finish();
    }

    private Dialog loadingDialog;

    private GiftsPresenter giftsPresenter;

    private CommonAdapter<GiftListInfo.DataBean> adapter;

    private List<GiftListInfo.DataBean> gifts = new ArrayList<GiftListInfo.DataBean>();

    @Override
    public int getLayoutResId() {
        return R.layout.activity_gifts;
    }

    @Override
    public void initData() {
        super.initData();
        tvTitle.setText("礼物列表");

        giftsPresenter = new GiftsPresenter(this);
        giftsPresenter.getGifts();
    }

    @Override
    public void showProgress() {
        loadingDialog = LoadingDialogUtil.createLoadingDialog(mActivity, "");
    }

    @Override
    public void hideProgress() {
        LoadingDialogUtil.closeDialog(loadingDialog);
    }

    @Override
    public void showInfo(String info) {
        ToastUtils.ShowSucess(mActivity, info);
    }

    @Override
    public void showError(String msg) {
        ToastUtils.ShowError(mActivity, msg);
    }


    @Override
    public String getUid() {
        return spUtil.getStringValue("userId", "");
    }

    @Override
    public void showGifts(GiftListInfo info) {
        gifts = info.getData();
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(mActivity, 4);
        rvGift.setLayoutManager(mLayoutManager);
        rvGift.setHasFixedSize(true);
        adapter = new CommonAdapter<GiftListInfo.DataBean>(mActivity, R.layout.item_gift, gifts) {
            @Override
            protected void convert(ViewHolder holder, GiftListInfo.DataBean dataBean, int position) {
                Glide.with(mActivity).load(dataBean.getImgUrl()).into((RoundAngleImageView) holder.getView(R.id.iv_gift));

                holder.setText(R.id.tv_name, dataBean.getGiftName());

                holder.setText(R.id.tv_num, "数量：" + dataBean.getCount());
            }
        };
        rvGift.setAdapter(adapter);
    }
}
