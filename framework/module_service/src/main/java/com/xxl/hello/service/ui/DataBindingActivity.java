package com.xxl.hello.service.ui;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

/**
 * @author xxl.
 * @date 2021/7/19.
 */
public abstract class DataBindingActivity<V extends BaseViewModel, T extends ViewDataBinding> extends BaseActivity<V> {

    /**
     * 页面绑定视图
     */
    protected T mViewDataBinding;

    //region: 成员变量

    //endregion

    //region: 页面生命周期

    /**
     * 设置页面内容布局
     */
    @Override
    protected void setContentView() {
        super.setContentView();
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutRes());
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
        mViewDataBinding.setVariable(getViewModelVariable(),getViewModel());
        mViewDataBinding.setVariable(getViewNavigatorVariable(),this);
        mViewDataBinding.executePendingBindings();
    }

    //endregion
}