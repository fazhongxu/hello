package com.xxl.hello.main.ui.main;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xxl.hello.core.aop.annotation.CheckLogin;
import com.xxl.hello.core.aop.annotation.CheckNetwork;
import com.xxl.hello.core.aop.annotation.Delay;
import com.xxl.hello.core.aop.annotation.Safe;
import com.xxl.hello.core.data.router.AppRouterApi;
import com.xxl.hello.core.listener.OnAppStatusChangedListener;
import com.xxl.hello.core.utils.AppExpandUtils;
import com.xxl.hello.core.utils.AppUtils;
import com.xxl.hello.core.utils.DisplayUtils;
import com.xxl.hello.core.utils.LogUtils;
import com.xxl.hello.core.utils.StatusBarUtil;
import com.xxl.hello.core.utils.TestUtils;
import com.xxl.hello.core.utils.ToastUtils;
import com.xxl.hello.main.BR;
import com.xxl.hello.main.R;
import com.xxl.hello.main.databinding.ActivityMainBinding;
import com.xxl.hello.main.ui.main.window.PrivacyPolicyPopupWindow;
import com.xxl.hello.service.data.model.api.QueryUserInfoResponse;
import com.xxl.hello.service.data.model.entity.LoginUserEntity;
import com.xxl.hello.service.ui.BaseEventBusWrapper;
import com.xxl.hello.service.ui.DataBindingActivity;
import com.xxl.hello.widget.paths.UserRouterApi;

import javax.inject.Inject;

/**
 * @author xxl
 * @date 2021/07/16.
 */
@Route(path = AppRouterApi.MAIN_PATH)
public class MainActivity extends DataBindingActivity<MainViewModel, ActivityMainBinding> implements MainActivityNavigator, OnAppStatusChangedListener {

    //region: 成员变量

    /**
     * 首页数据模型
     */
    private MainViewModel mMainViewModel;

    /**
     * 首页EventBus事件监听
     */
    @Inject
    MainEventBusWrapper mMainEventBusWrapper;

    //endregion

    //region: 页面视图渲染

    /**
     * 设置标题栏布局
     */
    private void setupToolbarLayout() {
        StatusBarUtil.setDarkMode(this);
        final int statusBarHeight = StatusBarUtil.getStatusBarHeight(this);
        mViewDataBinding.appBar.setPadding(DisplayUtils.dp2px(this, 10), statusBarHeight, 0, 0);
    }

    //endregion

    //region: 页面生命周期

    /**
     * 获取视图资源ID
     */
    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    /**
     * 创建ViewModel数据模型
     *
     * @return
     */
    @Override
    protected MainViewModel createViewModel() {
        mMainViewModel = new ViewModelProvider(this, mViewModelProviderFactory).get(MainViewModel.class);
        mMainViewModel.setNavigator(this);
        return mMainViewModel;
    }

    /**
     * 获取EventBus事件监听类
     *
     * @return
     */
    @Override
    protected BaseEventBusWrapper getEventBusWrapper() {
        return mMainEventBusWrapper;
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

    @Override
    protected void beforeSetContentView() {
        super.beforeSetContentView();
        DisplayUtils.setFullScreen(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterAppStatusChangedListener(this);
    }

    /**
     * 设置数据
     */
    @Override
    protected void setupData() {
        LogUtils.d("当前登录用户ID..." + AppExpandUtils.getCurrentUserId());
        if (!AppExpandUtils.isAgreePrivacyPolicy()) {
            showPrivacyPolicyPopupWindow();
        }
    }

    /**
     * 设置页面视图
     */
    @Override
    protected void setupLayout() {
        setupToolbarLayout();
        registerAppStatusChangedListener(this);
        mMainViewModel.setObservableUserId(String.valueOf(TestUtils.currentTimeMillis()));
    }

    @Override
    protected void requestData() {
        if (!AppExpandUtils.isAgreePrivacyPolicy()) {
            return;
        }
        mMainViewModel.requestQueryUserInfo();
    }

    //endregion

    //region: MainActivityNavigator

    @CheckNetwork
    @Delay(delay = 200)
    @Override
    public void onTestClick() {
        UserRouterApi.Login.navigation();
    }

    /**
     * 请求查询用户信息完成
     *
     * @param response
     */
    @Override
    public void onRequestQueryUserInfoComplete(@NonNull final QueryUserInfoResponse response) {
        if (response != null) {
            mMainViewModel.setObservableUserInfo(response.getUserId().concat("\n").concat(response.getAvatarUrl()));
        }

        final LoginUserEntity loginUserEntity = mMainViewModel.requestGetCurrentLoginUserEntity();
        if (loginUserEntity != null) {
            mMainViewModel.setObservableUserId(loginUserEntity.getUserId());
        } else {
            mMainViewModel.setObservableUserId(String.valueOf(TestUtils.currentTimeMillis()));
        }
    }

    //endregion

    //region: OnAppStatusChangedListener

    @Override
    public void onForeground(Activity activity) {
        ToastUtils.show(getString(R.string.resources_app_is_foreground_tips));
    }

    @Override
    public void onBackground(Activity activity) {
        ToastUtils.show(getString(R.string.resources_app_is_background_tips));
    }

    //endregion

    //region: Fragment 操作

    /**
     * 弹出隐私政策弹窗
     */
    private void showPrivacyPolicyPopupWindow() {
        PrivacyPolicyPopupWindow.from(this)
                .setOnDisagreeClickListener(v -> {
                    AppUtils.exitApp();
                })
                .setOnAgreeClickListener(v -> {
                    mMainViewModel.setAgreePrivacyPolicyStatus(true);
                    AppExpandUtils.initPluginsAfterAgreePrivacyPolicy();
                    requestData();
                })
                .showPopupWindow();
    }

    /**
     * 测试按钮点击
     */
    @Safe(callBack = "onErrorCallback")
    private void testCrash() {
        int a = 1 / 0;
        Log.e("aaa", "错了错了");
    }

    private void onErrorCallback(@Nullable final Throwable throwable) {
        ToastUtils.show("我是错误回调" + throwable.getMessage());
        Log.e("aaav", "错了错了v");
    }

    //endregion

    //region: EventBus 操作

    /**
     * 刷新用户信息
     *
     * @param targetUserEntity
     */
    public void refreshUserInfo(@Nullable final LoginUserEntity targetUserEntity) {
        if (targetUserEntity == null) {
            return;
        }
        mMainViewModel.setObservableUserId(targetUserEntity.getUserId());
    }
    //endregion

}


