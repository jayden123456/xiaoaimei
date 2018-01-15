package com.yidan.xiaoaimei.ui.fragment.main;

import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.miaokong.commonutils.utils.LoadingDialogUtil;
import com.yidan.xiaoaimei.R;
import com.yidan.xiaoaimei.base.BaseFragment;
import com.yidan.xiaoaimei.contract.MineContract;
import com.yidan.xiaoaimei.model.mine.PersonInfo;
import com.yidan.xiaoaimei.presenter.MinePresenter;
import com.yidan.xiaoaimei.ui.activity.mine.AttentionActivity;
import com.yidan.xiaoaimei.ui.activity.mine.HomepageActivity;
import com.yidan.xiaoaimei.ui.activity.mine.InfomationActivity;
import com.yidan.xiaoaimei.ui.activity.mine.MemberActivity;
import com.yidan.xiaoaimei.ui.activity.mine.MyAccountActivity;
import com.yidan.xiaoaimei.ui.activity.mine.MyGiftsActivity;
import com.yidan.xiaoaimei.ui.activity.mine.VideoVerifyActivity;
import com.yidan.xiaoaimei.ui.activity.mine.VisitorActivity;
import com.yidan.xiaoaimei.ui.activity.mine.VoiceVerifyActivity;
import com.yidan.xiaoaimei.ui.activity.mine.WechatSettingActivity;
import com.yidan.xiaoaimei.ui.activity.user.TagsActivity;
import com.yidan.xiaoaimei.ui.dialog.ImageUploadPopwindow;
import com.yidan.xiaoaimei.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by jaydenma on 2017/11/15.
 */

public class MineFragment extends BaseFragment implements MineContract.IMineView {

    @BindView(R.id.civ_head)
    CircleImageView civHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.tv_id)
    TextView tvId;
    @BindView(R.id.tv_attention)
    TextView tvAttention;
    @BindView(R.id.tv_fans)
    TextView tvFans;
    @BindView(R.id.iv_label_none)
    ImageView ivLabelNone;
    @BindView(R.id.iv_main_none)
    ImageView ivMainNone;
    @BindView(R.id.iv_verify_none)
    ImageView ivVerifyNone;
    @BindView(R.id.iv_contact_none)
    ImageView ivContactNone;
    @BindView(R.id.tv_trends)
    TextView tvTrends;
    @BindView(R.id.tv_get)
    TextView tvGet;
    @BindView(R.id.tv_pay)
    TextView tvPay;
    @BindView(R.id.tv_left)
    TextView tvLeft;

    @OnClick({R.id.tv_share,
            R.id.tv_attention,
            R.id.tv_fans,
            R.id.tv_visitor,
            R.id.rl_information,
            R.id.rl_label,
            R.id.rl_main,
            R.id.rl_verify,
            R.id.rl_contact,
            R.id.tv_trends,
            R.id.tv_gift,
            R.id.rl_account,
            R.id.rl_member,
            R.id.rl_safty,
            R.id.rl_set})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.tv_share:
                //分享主页

                break;
            case R.id.tv_attention:
                //关注列表
                Intent attentionIntent = new Intent(mActivity, AttentionActivity.class);
                attentionIntent.putExtra("fromType", 0);
                startActivity(attentionIntent);
                break;
            case R.id.tv_fans:
                //粉丝列表
                Intent fansIntent = new Intent(mActivity, AttentionActivity.class);
                fansIntent.putExtra("fromType", 1);
                startActivity(fansIntent);
                break;
            case R.id.tv_visitor:
                //访客列表
                Intent visitorIntent = new Intent(mActivity, VisitorActivity.class);
                startActivity(visitorIntent);
                break;
            case R.id.rl_information:
                //基本信息
                startActivity(new Intent(mActivity, InfomationActivity.class));
                break;
            case R.id.rl_label:
                //标签信息
                Intent tagIntent = new Intent(mActivity, TagsActivity.class);
                tagIntent.putExtra("fromType", 1);
                startActivity(tagIntent);
                break;
            case R.id.rl_main:
                //相册
                startActivity(new Intent(mActivity, HomepageActivity.class));
                break;
            case R.id.rl_verify:
                //认证
                imageUploadPopwindow.show();
                break;
            case R.id.rl_contact:
                //微信设置
                Intent wechatIntent = new Intent(mActivity, WechatSettingActivity.class);
                startActivity(wechatIntent);
                break;
            case R.id.tv_trends:
                //动态

                break;
            case R.id.tv_gift:
                //礼物列表
                startActivity(new Intent(mActivity, MyGiftsActivity.class));
                break;
            case R.id.rl_account:
                //充值中心
                startActivity(new Intent(mActivity, MyAccountActivity.class));
                break;
            case R.id.rl_member:
                //开通会员
                startActivity(new Intent(mActivity, MemberActivity.class));
                break;
            case R.id.rl_safty:
                //账号安全

                break;
            case R.id.rl_set:
                //设置

                break;
            default:
                break;
        }
    }

    public static int isChange = 0;

    private Dialog loadingDialog;

    private MinePresenter minePresenter;

    private ImageUploadPopwindow imageUploadPopwindow;


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initData() {
        super.initData();
        imageUploadPopwindow = new ImageUploadPopwindow(mActivity, "语音认证", "视频认证", new listener());

        minePresenter = new MinePresenter(this);
        minePresenter.userShow();
    }

    @Override
    protected void lazyLoad() {

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
    public String getToken() {
        return spUtil.getStringValue("token", "");
    }

    @Override
    public void userShow(PersonInfo personInfo) {
        Glide.with(mActivity).load(personInfo.getData().getHeadImg()).into(civHead);

        tvName.setText(personInfo.getData().getNickName());

        switch (personInfo.getData().getSex()) {
            case 0:
                tvAge.setText(personInfo.getData().getAge() + "");
                break;
            case -1:
                tvAge.setText("♀ " + personInfo.getData().getAge());
                break;
            case 1:
                tvAge.setText("♂ " + personInfo.getData().getAge());
                break;
            default:
                break;
        }

        tvId.setText("ID：" + personInfo.getData().getUid());

        tvAttention.setText("关注：" + personInfo.getData().getFollowCount());
        tvFans.setText("粉丝：" + personInfo.getData().getFansCount());

        tvTrends.setText("动态(" + personInfo.getData().getMomentNum() + ")");

        tvGet.setText(personInfo.getData().getIncome() + "");

        tvPay.setText(personInfo.getData().getExpenses() + "");

        tvLeft.setText(personInfo.getData().getBalance() + "");
    }


    @Override
    public void onResume() {
        super.onResume();
        if (isChange == 1) {
            minePresenter.userShow();
        }
    }

    class listener implements ImageUploadPopwindow.onSelectSexListener {

        @Override
        public void onSelect(int location) {
            if (location == 0) {
                startActivity(new Intent(mActivity, VoiceVerifyActivity.class));
            } else {
                startActivity(new Intent(mActivity, VideoVerifyActivity.class));
            }
        }
    }

}
