package com.yidan.xiaoaimei.ui.activity.user;

import android.app.Dialog;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.gson.Gson;
import com.miaokong.commonutils.utils.LoadingDialogUtil;
import com.yidan.xiaoaimei.R;
import com.yidan.xiaoaimei.base.BaseActivity;
import com.yidan.xiaoaimei.contract.TagsContract;
import com.yidan.xiaoaimei.model.user.TagInfo;
import com.yidan.xiaoaimei.presenter.TagsPresenter;
import com.yidan.xiaoaimei.ui.activity.MainActivity;
import com.yidan.xiaoaimei.ui.fragment.main.MineFragment;
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
 * 标签信息
 * Created by jaydenma on 2017/11/14.
 */

public class TagsActivity extends BaseActivity implements TagsContract.ITagsView {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ftl_tags)
    TagFlowLayout ftlTags;
    @BindView(R.id.tv_tag_count)
    TextView tvTagCount;
    @BindView(R.id.ftl_service)
    TagFlowLayout ftlService;
    @BindView(R.id.tv_service_count)
    TextView tvServiceCount;
    @BindView(R.id.tv_finish)
    TextView tvFinish;
    @BindView(R.id.tv_button_right)
    TextView tvButtonRight;


    @OnClick({R.id.iv_back, R.id.tv_finish, R.id.tv_button_right})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_finish:
                tagsPresenter.selectTags();
                break;
            case R.id.tv_button_right:
                tagsPresenter.selectTags();
                break;
            default:
                break;
        }
    }

    private Dialog loadingDialog;

    private TagsPresenter tagsPresenter;

    private List<TagInfo.DataBean.TagsBean> tags = new ArrayList<TagInfo.DataBean.TagsBean>();

    private List<TagInfo.DataBean.ServiceTagsBean> services = new ArrayList<TagInfo.DataBean.ServiceTagsBean>();

    private LinkedList<Integer> selectTags = new LinkedList<Integer>();

    private int selectNum = 0;

    private int selectServiceNum = 0;

    private List<Integer> tagIds = new ArrayList<Integer>();

    private List<Integer> serviceIds = new ArrayList<Integer>();

    private TagAdapter<TagInfo.DataBean.TagsBean> adapter;

    private TagAdapter<TagInfo.DataBean.ServiceTagsBean> serviceAdapter;

    private Gson gson;

    private int fromType = 0;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_tags;
    }

    @Override
    public void initData() {
        super.initData();
        tvTitle.setText("标签设置");

        if (getIntent().hasExtra("fromType")) {
            fromType = getIntent().getIntExtra("fromType", 0);
        }

        if (fromType == 1) {
            tvButtonRight.setVisibility(View.VISIBLE);
            tvFinish.setVisibility(View.GONE);
            tvButtonRight.setTextColor(getResources().getColor(R.color.commonWhite));
            tvButtonRight.setText("保存");
        } else {
            tvButtonRight.setVisibility(View.GONE);
            tvFinish.setVisibility(View.VISIBLE);
        }

        gson = new Gson();

        tagsPresenter = new TagsPresenter(this);
        tagsPresenter.getAllTags();
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
    public String getTagStr() {
        return gson.toJson(tagIds);
    }

    @Override
    public String getServiceStr() {
        return gson.toJson(serviceIds);
    }

    @Override
    public void showTags(TagInfo info) {
        tags = info.getData().getTags();
        services = info.getData().getServiceTags();

        for (int i = 0; i < tags.size(); i++) {
            if (tags.get(i).getStatus() == 1) {
                selectNum++;
                selectTags.add(i);
                tagIds.add(tags.get(i).getTagId());
            }
        }
        tvTagCount.setText(Html.fromHtml("当前选择<font color='#FF2A2A'>" + selectNum + "</font>项"));

        final LayoutInflater mInflater = LayoutInflater.from(this);
        adapter = new TagAdapter<TagInfo.DataBean.TagsBean>(tags) {

            @Override
            public View getView(FlowLayout parent, final int position, TagInfo.DataBean.TagsBean dataBean) {
                CheckBox cbTag = (CheckBox) mInflater.inflate(R.layout.item_tag, ftlTags, false);
                cbTag.setText(dataBean.getTagName());
                if (tags.get(position).getStatus() == 1) {
                    cbTag.setChecked(true);
                } else {
                    cbTag.setChecked(false);
                }
                cbTag.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //根据队列的原则判断tag选择与取消选择。最多选三个
//                        tagsStr = "";
                        if (tags.get(position).getStatus() == 0) {
                            if (selectNum == 3) {
                                tags.get(selectTags.getFirst()).setStatus(0);
                                selectTags.removeFirst();
                                selectTags.add(position);
                                tags.get(position).setStatus(1);
                            } else {
                                selectTags.add(position);
                                tags.get(position).setStatus(1);
                                selectNum++;
                            }
                        } else {
                            selectTags.remove((Integer) position);
                            selectNum--;
                            tags.get(position).setStatus(0);
                        }
                        adapter.notifyDataChanged();
                        tvTagCount.setText(Html.fromHtml("当前选择<font color='#FF2A2A'>" + selectNum + "</font>项"));
                        checkCanSubmit();
                        tagIds.clear();
                        for (TagInfo.DataBean.TagsBean tag : tags) {
                            if (tag.getStatus() == 1) {
                                tagIds.add(tag.getTagId());
                            }
                        }
                    }
                });
                return cbTag;
            }
        };
        ftlTags.setAdapter(adapter);


        for (TagInfo.DataBean.ServiceTagsBean data : services) {
            if (data.getStatus() == 1) {
                selectServiceNum++;
                serviceIds.add(data.getTagId());
            }
        }
        tvServiceCount.setText(Html.fromHtml("当前选择<font color='#FF2A2A'>" + selectServiceNum + "</font>项"));
        checkCanSubmit();
        final LayoutInflater mServiceInflater = LayoutInflater.from(this);
        serviceAdapter = new TagAdapter<TagInfo.DataBean.ServiceTagsBean>(services) {

            @Override
            public View getView(FlowLayout parent, final int position, final TagInfo.DataBean.ServiceTagsBean dataBean) {
                CheckBox cbTag = (CheckBox) mServiceInflater.inflate(R.layout.item_tag, ftlService, false);
                cbTag.setText(dataBean.getTagName());
                if (dataBean.getStatus() == 1) {
                    cbTag.setChecked(true);
                } else {
                    cbTag.setChecked(false);
                }
                cbTag.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dataBean.getStatus() == 1) {
                            services.get(position).setStatus(0);
                            selectServiceNum--;
                        } else {
                            selectServiceNum++;
                            services.get(position).setStatus(1);
                        }
                        serviceAdapter.notifyDataChanged();
                        tvServiceCount.setText(Html.fromHtml("当前选择<font color='#FF2A2A'>" + selectServiceNum + "</font>项"));
                        checkCanSubmit();
                        serviceIds.clear();
                        for (TagInfo.DataBean.ServiceTagsBean tag : services) {
                            if (tag.getStatus() == 1) {
                                serviceIds.add(tag.getTagId());
                            }
                        }
                    }
                });
                return cbTag;
            }
        };
        ftlService.setAdapter(serviceAdapter);

    }

    @Override
    public void toNewPage() {
        if (fromType == 1) {
            MineFragment.isChange = 1;
            finish();
        } else {
            startActivity(new Intent(mActivity, MainActivity.class));
        }
    }


    private void checkCanSubmit() {
        if (selectNum > 0 && selectServiceNum > 0) {
            tvFinish.setClickable(true);
            tvFinish.setBackgroundResource(R.drawable.common_red_button);
            tvButtonRight.setClickable(true);
            tvButtonRight.setBackgroundResource(R.drawable.common_red_button);
        } else {
            tvFinish.setClickable(false);
            tvFinish.setBackgroundResource(R.drawable.common_unclick_login_button);
            tvButtonRight.setClickable(false);
            tvButtonRight.setBackgroundResource(R.drawable.common_unclick_login_button);
        }
    }

}
