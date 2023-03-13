package com.xxl.core.ui.fragment;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.databinding.ViewDataBinding;

import com.xxl.core.ui.BaseViewModel;
import com.xxl.core.widget.toolbar.OnToolbarProvider;
import com.xxl.core.widget.toolbar.ToolbarWrapper;

/**
 * 带Toolbar 监听的ViewModel的Fragment基础类
 *
 * @author xxl.
 * @date 2022/4/8.
 */
public abstract class BaseToolbarListenerViewModelFragment<V extends BaseViewModel, T extends ViewDataBinding> extends BaseViewModelFragment<V, T>
        implements OnToolbarProvider {

    //region: 成员变量

    /**
     * Toolbar包装类
     */
    private ToolbarWrapper mToolbarWrapper;

    //endregion

    //region: 页面生命周期

    @Override
    public void setupLayout(@NonNull View rootView) {
        mToolbarWrapper = ToolbarWrapper.create(getActivity(),this);
        mToolbarWrapper.setupToolbar(rootView);
    }

    /**
     * 设置标题
     *
     * @param title
     */
    @Override
    public void setToolbarTitle(@NonNull CharSequence title) {
        if (mToolbarWrapper != null) {
            mToolbarWrapper.setToolbarTitle(title);
        }
    }

    /**
     * 设置标题
     *
     * @param resId
     */
    @Override
    public void setToolbarTitle(@StringRes int resId) {
        if (mToolbarWrapper != null) {
            mToolbarWrapper.setToolbarTitle(resId);
        }
    }

    //endregion


}