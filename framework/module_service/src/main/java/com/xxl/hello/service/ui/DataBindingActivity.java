package com.xxl.hello.service.ui;

import androidx.databinding.DataBindingUtil;

/**
 * @author xxl.
 * @date 2021/7/19.
 */
public abstract class DataBindingActivity<V extends BaseViewModel> extends BaseActivity<V> {

    //region: 成员变量

    //endregion

    //region: 页面生命周期

    /**
     * 设置页面内容布局
     */
    @Override
    protected void setContentView() {
        super.setContentView();
        DataBindingUtil.setContentView(this, getLayoutRes());
    }

    //endregion
}