package com.xxl.hello.main.ui.main;

import android.view.View;

import com.xxl.hello.service.ui.BaseFragment;
import com.xxl.hello.service.ui.BaseViewModel;

/**
 * @author xxl.
 * @date 2022/4/8.
 */
public class MainFragment extends BaseFragment {

    //region: 成员变量

    //endregion

    //region: 构造函数

    public final static MainFragment newInstance() {
        return new MainFragment();
    }

    /**
     * 获取视图资源ID
     *
     * @return
     */
    @Override
    protected int getLayoutRes() {
        return 0;
    }

    /**
     * 创建ViewModel数据模型
     *
     * @return
     */
    @Override
    protected BaseViewModel createViewModel() {
        return null;
    }

    /**
     * 获取data binding 内的 ViewModel
     *
     * @return
     */
    @Override
    public int getViewModelVariable() {
        return 0;
    }

    /**
     * 获取data binding 内的 Navigator
     *
     * @return
     */
    @Override
    public int getViewNavigatorVariable() {
        return 0;
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

    //region: 页面生命周期

    //endregion

    //region: 提供方法

    //endregion

    //region: 内部辅助方法

    //endregion

}