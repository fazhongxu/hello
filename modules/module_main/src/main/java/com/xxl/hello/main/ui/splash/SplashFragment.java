package com.xxl.hello.main.ui.splash;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.xxl.core.ui.fragment.BaseViewModelFragment;
import com.xxl.hello.main.BR;
import com.xxl.hello.main.R;
import com.xxl.hello.main.databinding.MainFragmentBinding;
import com.xxl.hello.main.ui.main.MainFragment;
import com.xxl.kit.AppRouterApi;

/**
 * @author xxl.
 * @date 2022/7/29.
 */
public class SplashFragment extends BaseViewModelFragment<SplashViewModel, MainFragmentBinding> {

    //region: 构造函数

    /**
     * 下一个跳转路径
     */
    @Autowired(name = AppRouterApi.Splash.PARAMS_KEY_NEXT_PATH)
    String mNextPath;

    /**
     * 数据
     */
    @Autowired(name = AppRouterApi.Splash.PARAMS_KEY_EXTRA_DATA)
    String mExtraData;

    //endregion

    //region: 构造函数

    public final static SplashFragment newInstance(@NonNull final Bundle bundle) {
        final SplashFragment splashFragment = new SplashFragment();
        splashFragment.setArguments(bundle);
        return splashFragment;
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

    @Override
    protected boolean enableRouterInject() {
        return true;
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
            AppRouterApi.Main.newBuilder()
                    .setNextPath(mNextPath)
                    .setExtraData(mExtraData)
                    .navigationWithFinish(getActivity());
        }, 200);
    }

    //endregion


}