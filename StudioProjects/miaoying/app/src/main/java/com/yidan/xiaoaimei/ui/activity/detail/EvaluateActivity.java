package com.yidan.xiaoaimei.ui.activity.detail;

import android.app.Dialog;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.miaokong.commonutils.utils.LoadingDialogUtil;
import com.yidan.xiaoaimei.R;
import com.yidan.xiaoaimei.base.BaseActivity;
import com.yidan.xiaoaimei.contract.EvaluateContract;
import com.yidan.xiaoaimei.model.detail.EvaluateInfo;
import com.yidan.xiaoaimei.model.user.TagInfo;
import com.yidan.xiaoaimei.presenter.EvaluatePresenter;
import com.yidan.xiaoaimei.utils.ToastUtils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 评价
 * Created by jaydenma on 2017/12/12.
 */

public class EvaluateActivity extends BaseActivity implements EvaluateContract.IEvaluateView {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ftl_tags)
    TagFlowLayout ftlTags;
    @BindView(R.id.tv_tag_count)
    TextView tvTagCount;
    @BindView(R.id.tv_finish)
    TextView tvFinish;

    @OnClick({R.id.iv_back, R.id.tv_finish})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_finish:
                evaluatePresenter.evaluate();
                break;
            default:
                break;
        }
    }


    private Dialog loadingDialog;

    private EvaluatePresenter evaluatePresenter;

    private TagAdapter<EvaluateInfo.DataBean> adapter;

    private List<EvaluateInfo.DataBean> evaluates = new ArrayList<EvaluateInfo.DataBean>();

    private LinkedList<Integer> selectTags = new LinkedList<Integer>();

    private int selectNum = 0;

    private List<Integer> tagIds = new ArrayList<Integer>();

    private Gson gson;

    private String uid;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_evaluate;
    }

    @Override
    public void initData() {
        super.initData();
        tvTitle.setText("评价用户");

        gson = new Gson();

        uid = getIntent().getStringExtra("uid");

        evaluatePresenter = new EvaluatePresenter(this);
        evaluatePresenter.getEvaluate();
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
        startActivity(new Intent(mActivity, EvaluateResultActivity.class));
        finish();
    }

    @Override
    public void showError(String msg) {
        ToastUtils.ShowError(mActivity, msg);
    }

    @Override
    public void showEvaluate(EvaluateInfo info) {
        evaluates = info.getData();
        checkCanSubmit();

        for (EvaluateInfo.DataBean data : evaluates) {
            data.setStatus(0);
        }

        final LayoutInflater mInflater = LayoutInflater.from(this);
        adapter = new TagAdapter<EvaluateInfo.DataBean>(evaluates) {

            @Override
            public View getView(FlowLayout parent, final int position, EvaluateInfo.DataBean dataBean) {
                CheckBox cbTag = (CheckBox) mInflater.inflate(R.layout.item_evaluate, ftlTags, false);
                cbTag.setText(dataBean.getEvaTagName());
                if (evaluates.get(position).getStatus() == 1) {
                    cbTag.setChecked(true);
                } else {
                    cbTag.setChecked(false);
                }
                cbTag.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //根据队列的原则判断tag选择与取消选择。最多选三个
//                        tagsStr = "";
                        if (evaluates.get(position).getStatus() == 0) {
                            if (selectNum == 3) {
                                evaluates.get(selectTags.getFirst()).setStatus(0);
                                selectTags.removeFirst();
                                selectTags.add(position);
                                evaluates.get(position).setStatus(1);
                            } else {
                                selectTags.add(position);
                                evaluates.get(position).setStatus(1);
                                selectNum++;
                            }
                        } else {
                            selectTags.remove((Integer) position);
                            selectNum--;
                            evaluates.get(position).setStatus(0);
                        }
                        adapter.notifyDataChanged();
                        tvTagCount.setText(Html.fromHtml("当前选择<font color='#FF2A2A'>" + selectNum + "</font>项"));
                        checkCanSubmit();
                        tagIds.clear();
                        for (EvaluateInfo.DataBean tag : evaluates) {
                            if (tag.getStatus() == 1) {
                                tagIds.add(tag.getEvaId());
                            }
                        }
                    }
                });
                return cbTag;
            }
        };
        ftlTags.setAdapter(adapter);

    }

    @Override
    public String getToken() {
        return spUtil.getStringValue("token", "");
    }

    @Override
    public String getEvaId() {
        return gson.toJson(tagIds);
    }

    @Override
    public String getUid() {
        return uid;
    }


    private void checkCanSubmit() {
        if (selectNum > 0) {
            tvFinish.setClickable(true);
            tvFinish.setBackgroundResource(R.drawable.common_red_button);
        } else {
            tvFinish.setClickable(false);
            tvFinish.setBackgroundResource(R.drawable.common_unclick_login_button);
        }
    }
}
