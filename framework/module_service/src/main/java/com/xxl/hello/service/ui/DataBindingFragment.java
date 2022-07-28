package com.xxl.hello.service.ui;

import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

/**
 * @author xxl.
 * @date 2021/7/19.
 */
public abstract class DataBindingFragment<V extends BaseViewModel, T extends ViewDataBinding> extends BaseFragment<V> {

    /**
     * 页面绑定视图
     */
    protected T mViewDataBinding;

    //region: 成员变量

    //endregion

    //region: 页面生命周期

    @Override
    protected void setupData() {

    }

    @Override
    protected void setupLayout(View view) {
        mViewDataBinding = DataBindingUtil.inflate(getLayoutInflater(), getLayoutRes(), null, false);
        mViewDataBinding.executePendingBindings();
    }

    /**
     * 获取页面绑定视图
     *
     * @return
     */
    public T getViewDataBinding() {
        return mViewDataBinding;
    }

    /**
     * 创建ViewModel数据模型后
     */
    @Override
    protected void afterCreateVieModel() {
        setVariable();
    }

    /**
     * 设置data binding 绑定数据和事件所需参数
     */
    protected void setVariable() {
        mViewDataBinding.setVariable(getViewModelVariable(), getViewModel());
        mViewDataBinding.setVariable(getViewNavigatorVariable(), this);
        mViewDataBinding.executePendingBindings();
    }

    //endregion
}