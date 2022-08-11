package com.xxl.hello.main.ui.jump;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.xxl.core.ui.fragment.BaseViewModelFragment;
import com.xxl.hello.main.BR;
import com.xxl.hello.main.R;
import com.xxl.hello.main.databinding.MainFragmentSchemeJumpBinding;

/**
 * scheme跳转处理页
 *
 * @author xxl.
 * @date 2022/8/11.
 */
public class SchemeJumpFragment extends BaseViewModelFragment<SchemeJumpViewModel, MainFragmentSchemeJumpBinding>
        implements SchemeJumpNavigator {

    //region: 成员变量

    /**
     * scheme跳转处理页视图模型
     */
    private SchemeJumpViewModel mSchemeJumpViewModel;

    //endregion

    //region: 构造函数

    public final static SchemeJumpFragment newInstance(@NonNull final Bundle bundle) {
        final SchemeJumpFragment mainFragment = new SchemeJumpFragment();
        mainFragment.setArguments(bundle);
        return mainFragment;
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
        return R.layout.main_fragment_scheme_jump;
    }

    /**
     * 创建ViewModel数据模型
     *
     * @return
     */
    @Override
    protected SchemeJumpViewModel createViewModel() {
        mSchemeJumpViewModel = createViewModel(SchemeJumpViewModel.class);
        mSchemeJumpViewModel.setNavigator(this);
        return mSchemeJumpViewModel;
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
     * @param rootView
     */
    @Override
    protected void setupLayout(@NonNull final View rootView) {

    }

    //endregion

    //region: MainNavigator


    //endregion

    //region: Fragment 操作


    //endregion


}