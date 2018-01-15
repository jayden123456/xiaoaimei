package com.yidan.xiaoaimei.ui.fragment.detail;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yidan.xiaoaimei.R;
import com.yidan.xiaoaimei.base.BaseFragment;
import com.yidan.xiaoaimei.model.detail.UserDetailInfo;
import com.yidan.xiaoaimei.ui.view.RoundAngleImageView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

import butterknife.BindView;

import static cn.finalteam.toolsfinal.DeviceUtils.dip2px;
import static cn.finalteam.toolsfinal.DeviceUtils.px2dip;

/**
 * Created by jaydenma on 2017/12/11.
 */

public class DetailPersonalFragment extends BaseFragment {

    @BindView(R.id.tv_detail_id)
    TextView tvDetailId;
    @BindView(R.id.tv_detail_city)
    TextView tvDetailCity;
    @BindView(R.id.tv_detail_fans_num)
    TextView tvDetailFansNum;
    @BindView(R.id.tv_detail_sign)
    TextView tvDetailSign;
    @BindView(R.id.tv_detail_personal_info)
    TextView tvDetailPersonalInfo;
    @BindView(R.id.tv_detail_tag1)
    TextView tvDetailTag1;
    @BindView(R.id.tv_detail_tag2)
    TextView tvDetailTag2;
    @BindView(R.id.tv_detail_tag3)
    TextView tvDetailTag3;
    @BindView(R.id.ftl_comment)
    TagFlowLayout ftlComment;
    @BindView(R.id.ll_gifts)
    LinearLayout llGifts;
    @BindView(R.id.ll_visitor)
    LinearLayout llVisitor;

    private TagAdapter<UserDetailInfo.DataBean.EvaTagsBean> adapter;

    private int showGiftCount = 0;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_detail_personal;
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void initData() {
        super.initData();
        //获取屏幕宽高
        DisplayMetrics dm = mActivity.getResources().getDisplayMetrics();
        float windowWidth = dm.widthPixels;
        int curDip = px2dip(mActivity, windowWidth);
        showGiftCount = curDip / 60 - 1;
    }

    public void setData(UserDetailInfo.DataBean data) {
        tvDetailId.setText("ID：" + data.getUid());
        tvDetailCity.setText(data.getCity());
        tvDetailFansNum.setText("粉丝数：" + data.getFollowCount());
        tvDetailSign.setText("签名：" + data.getSignature());
        if (data.getMeasurements().size() == 0) {
            tvDetailPersonalInfo.setText("年龄：" + data.getAge()
                    + "\n身高：" + data.getHeight()
                    + "\n三围：0/0/0cm");

        } else {
            tvDetailPersonalInfo.setText("年龄：" + data.getAge()
                    + "\n身高：" + data.getHeight()
                    + "\n三围：" + data.getMeasurements().get(0) + "/" + data.getMeasurements().get(1) + "/" + data.getMeasurements().get(2) + "cm");
        }

        switch (data.getTags().size()) {
            case 0:
                tvDetailTag1.setVisibility(View.GONE);
                tvDetailTag2.setVisibility(View.GONE);
                tvDetailTag3.setVisibility(View.GONE);
                break;
            case 1:
                tvDetailTag1.setVisibility(View.VISIBLE);
                tvDetailTag2.setVisibility(View.GONE);
                tvDetailTag3.setVisibility(View.GONE);
                tvDetailTag1.setText(data.getTags().get(0).getTagName());
                break;
            case 2:
                tvDetailTag1.setVisibility(View.VISIBLE);
                tvDetailTag2.setVisibility(View.VISIBLE);
                tvDetailTag3.setVisibility(View.GONE);
                tvDetailTag1.setText(data.getTags().get(0).getTagName());
                tvDetailTag2.setText(data.getTags().get(1).getTagName());
                break;
            case 3:
                tvDetailTag1.setVisibility(View.VISIBLE);
                tvDetailTag2.setVisibility(View.VISIBLE);
                tvDetailTag3.setVisibility(View.VISIBLE);
                tvDetailTag1.setText(data.getTags().get(0).getTagName());
                tvDetailTag2.setText(data.getTags().get(1).getTagName());
                tvDetailTag3.setText(data.getTags().get(2).getTagName());
                break;
            default:
                break;
        }

        final LayoutInflater mInflater = LayoutInflater.from(mActivity);
        adapter = new TagAdapter<UserDetailInfo.DataBean.EvaTagsBean>(data.getEvaTags()) {

            @Override
            public View getView(FlowLayout parent, final int position, UserDetailInfo.DataBean.EvaTagsBean dataBean) {
                CheckBox cbTag = (CheckBox) mInflater.inflate(R.layout.item_detail_comment_tag, ftlComment, false);
                cbTag.setText(dataBean.getEvaTagName());

                return cbTag;
            }
        };
        ftlComment.setAdapter(adapter);

        int giftCount = 0;
        for (UserDetailInfo.DataBean.GiftListBean giftListBean : data.getGiftList()) {
            if (giftCount < showGiftCount) {
                RoundAngleImageView roundAngleImageView = new RoundAngleImageView(mActivity);
                roundAngleImageView.setScaleType(ImageView.ScaleType.FIT_XY);
                LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(dip2px(mActivity, 50), dip2px(mActivity, 50));
                layout.setMargins(dip2px(mActivity, 10), 0, 0, 0);
                roundAngleImageView.setLayoutParams(layout);
                Glide.with(mActivity).load(giftListBean.getImgUrl()).into(roundAngleImageView);
                llGifts.addView(roundAngleImageView);
            }
            giftCount++;
        }

        int visitorCount = 0;
        for (UserDetailInfo.DataBean.VisitRecordBean visitRecordBean : data.getVisitRecord()) {
            if (visitorCount < showGiftCount) {
                RoundAngleImageView roundAngleImageView = new RoundAngleImageView(mActivity);
                roundAngleImageView.setScaleType(ImageView.ScaleType.FIT_XY);
                LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(dip2px(mActivity, 50), dip2px(mActivity, 50));
                layout.setMargins(dip2px(mActivity, 10), 0, 0, 0);
                roundAngleImageView.setLayoutParams(layout);
                Glide.with(mActivity).load(visitRecordBean.getHeadImg()).into(roundAngleImageView);
                llVisitor.addView(roundAngleImageView);
            }
            visitorCount++;
        }
    }


}
