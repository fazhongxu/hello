package com.xxl.hello.main.ui.splash;

import android.os.Handler;
import android.view.View;

import androidx.annotation.NonNull;

import com.xxl.core.ui.fragment.BaseViewModelFragment;
import com.xxl.hello.main.BR;
import com.xxl.hello.main.R;
import com.xxl.hello.main.databinding.MainFragmentBinding;
import com.xxl.kit.AppRouterApi;

/**
 * @author xxl.
 * @date 2022/7/29.
 */
public class SplashFragment extends BaseViewModelFragment<SplashViewModel, MainFragmentBinding> {

    //region: 构造函数

    public final static SplashFragment newInstance() {
        return new SplashFragment();
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
        return R.layout.main_fragment_splash;
    }

    /**
     * 创建ViewModel数据模型
     *
     * @return
     */
    @Override
    protected SplashViewModel createViewModel() {
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
        return BR.viewModel;
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
    protected void setupLayout(@NonNull final View view) {
        new Handler().postDelayed(() -> {
            if (isActivityFinishing()) {
                return;
            }
            // TODO: 2022/4/2  模拟数据请求
            AppRouterApi.Main.newBuilder().navigationWithFinish(getActivity());
        }, 200);
    }

    //endregion


}