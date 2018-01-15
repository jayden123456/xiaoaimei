package com.yidan.xiaoaimei.base.interfaces;

import android.os.Bundle;

/**
 * 规范Activity接口协议
 *
 * @author 续写经典
 * @date 2015/11/4
 */
public interface I_Activity {
    int DESTROY = 0x00;
    int STOP = 0x01;
    int PAUSE = 0x02;
    int RESUME = 0x03;

    /**
     * 获取布局文件
     */
    int getLayoutResId();


    /**
     * 第一次启动会执行此方法
     */
    void onFirst();

    /**
     * 此方法会在setContentView之前调用
     */
    void initPre();

    /**
     * 处理savedInstanceState
     */
    void initInstanceState(Bundle savedInstanceState);

    /**
     * 初始化数据
     */
    void initData();

    /**
     * 初始化控件
     */
    void initView();


}
