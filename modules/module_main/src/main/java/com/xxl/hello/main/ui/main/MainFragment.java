package com.xxl.hello.main.ui.main;

import android.view.View;

import com.xxl.hello.main.BR;
import com.xxl.hello.main.R;
import com.xxl.hello.main.databinding.MainFragmentBinding;
import com.xxl.hello.service.ui.DataBindingFragment;

/**
 * @author xxl.
 * @date 2022/4/8.
 */
public class MainFragment extends DataBindingFragment<MainViewModel, MainFragmentBinding> {

    //region: 成员变量

    //endregion

    //region: 构造函数

    public final static MainFragment newInstance() {
        return new MainFragment();
    }

    //endregion

    //region: 页面生命周期

    /**
     * 获取视图资源ID
     *
     * @return
     */
    @Override
    protected int getLayoutRes() {
        return R.layout.main_fragment;
    }

    /**
     * 创建ViewModel数据模型
     *
     * @return
     */
    @Override
    protected MainViewModel createViewModel() {
        return null;
    }

    /**
     * 获取data binding 内的 ViewModel
     *
     * @return
     */
    @Override
    public int getViewModelVariable() {
        return BR.viewModel;
    }

    /**
     * 获取data binding 内的 Navigator
     *
     * @return
     */
    @Override
    public int getViewNavigatorVariable() {
        return BR.navigator;
    }

    /**
     * 设置数据
     */
    @Override
    protected void setupData() {

    }

    /**
     * 设置页面视图
     *
     * @param view
     */
    @Override
    protected void setupLayout(View view) {

    }

    //endregion

    //region: 提供方法

    //endregion

    //region: 内部辅助方法

    //endregion

}