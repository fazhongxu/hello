package com.xxl.hello.main.ui.splash;

import android.os.Handler;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xxl.hello.core.data.router.AppRouterApi;
import com.xxl.hello.main.R;
import com.xxl.hello.service.ui.SingleActivity;

/**
 * 启动页面
 *
 * @author xxl.
 * @date 2021/8/13.
 */
@Route(path = AppRouterApi.SPLASH_PATH)
public class SplashActivity extends SingleActivity<SplashViewModel> {

    //region: 页面生命周期

    /**
     * 获取视图资源ID
     *
     * @return
     */
    @Override
    protected int getLayoutRes() {
        return R.layout.activity_splash;
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
     */
    @Override
    protected void setupLayout() {
        new Handler().postDelayed(() -> AppRouterApi.navigationWithFinish(this), 2000);
    }

    //endregion
}