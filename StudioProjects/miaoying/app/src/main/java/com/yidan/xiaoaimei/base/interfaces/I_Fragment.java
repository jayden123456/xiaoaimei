package com.yidan.xiaoaimei.base.interfaces;

import android.os.Bundle;

/**
 * 规范Fragment接口协议
 * @author 续写经典
 * @date 2015/11/12
 */
public interface I_Fragment {

  /**
   * 获取布局文件
   */
  int getLayoutResId();

  /**
   * 是否隐藏状态栏
   *
   * @return
   */
//  boolean isHideStatusBar();

  /**
   * Fragment被切换到前台时调用
   */
  void onChange();
  /**
   * Fragment被切换到后台时调用
   */
  void onHidden();

  /** 第一次启动会执行此方法 */
  void onFirst();

  /**
   * 处理savedInstanceState
   */
  void initInstanceState(Bundle savedInstanceState);

  /**
   * 初始化数据
   */
  void initData();


}
