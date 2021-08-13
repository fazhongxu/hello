package com.xxl.hello.service.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;

/**
 * @author xxl.
 * @date 2021/7/19.
 */
public abstract class SingleActivity<V extends BaseViewModel> extends BaseActivity<V> {

    //region: 成员变量

    //endregion

    //region: 页面生命周期

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutRes());
    }

    //endregion
}