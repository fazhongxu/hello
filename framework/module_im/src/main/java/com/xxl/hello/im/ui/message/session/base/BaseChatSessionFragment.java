package com.xxl.hello.im.ui.message.session.base;

import android.view.View;

import androidx.annotation.NonNull;

import com.xxl.core.ui.BaseViewModel;
import com.xxl.core.ui.fragment.BaseViewModelFragment;
import com.xxl.hello.im.BR;

/**
 * 会话基础类
 *
 * @author xxl.
 * @date 2024/6/14.
 */
public class BaseChatSessionFragment extends BaseViewModelFragment {

    //region: 成员变量

    //endregion

    //region: 页面生命周期

    @Override
    protected int getLayoutRes() {
        return 0;
    }

    @Override
    protected BaseViewModel createViewModel() {
        return null;
    }

    @Override
    public int getViewModelVariable() {
        return BR.viewModel;
    }

    @Override
    public int getViewNavigatorVariable() {
        return BR.listener;
    }

    @Override
    protected void setupData() {

    }

    @Override
    protected void setupLayout(@NonNull View rootView) {

    }

    //endregion

    //region: 提供方法

    //endregion

    //region: 内部辅助方法

    //endregion

}