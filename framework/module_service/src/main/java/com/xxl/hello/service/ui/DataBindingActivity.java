package com.xxl.hello.service.ui;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

/**
 * @author xxl.
 * @date 2021/7/19.
 */
public abstract class DataBindingActivity<T extends ViewDataBinding> extends BaseActivity {

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
        mViewDataBinding =  DataBindingUtil.inflate(getLayoutInflater(),getLayoutRes(),null,false);
        mViewDataBinding.executePendingBindings();
        setContentView(mViewDataBinding.getRoot());
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
     * 获取data binding 内的 ViewModel
     *
     * @return
     */
    public abstract int getViewModelVariable();

    /**
     * 获取data binding 内的 Navigator
     *
     * @return
     */
    public abstract int getViewNavigatorVariable();

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
        mViewDataBinding.setVariable(getViewNavigatorVariable(),this);
        mViewDataBinding.executePendingBindings();
    }

    //endregion
}