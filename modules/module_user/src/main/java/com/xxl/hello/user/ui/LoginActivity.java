package com.xxl.hello.user.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.xxl.hello.common.config.NetworkConfig;
import com.xxl.hello.common.utils.TestUtils;
import com.xxl.hello.service.data.model.entity.LoginUserEntity;
import com.xxl.hello.service.qunlifier.ForUserBaseUrl;
import com.xxl.hello.service.ui.BaseEventBusWrapper;
import com.xxl.hello.service.ui.DataBindingActivity;
import com.xxl.hello.user.BR;
import com.xxl.hello.user.R;
import com.xxl.hello.user.data.model.api.UserLoginResponse;
import com.xxl.hello.user.databinding.ActivityLoginBinding;

import javax.inject.Inject;

/**
 * @author xxl.
 * @date 2021/07/16.
 */
public class LoginActivity extends DataBindingActivity<LoginActivityViewModel, ActivityLoginBinding> implements LoginActivityNavigator {

    //region: 成员变量

    /**
     * 登录页面ViewModel数据模型
     */
    private LoginActivityViewModel mLoginActivityViewModel;

    /**
     * 登录页面EventBus通知事件监听
     */
    @Inject
    LoginActivityEventBusWrapper mLoginActivityEventBusWrapper;

    /**
     * 用户模块主机地址
     */
    @ForUserBaseUrl
    @Inject
    String mBaseUrl;

    //endregion

    //region: 页面生命周期

    /**
     * 获取视图资源ID
     */
    @Override
    protected int getLayoutRes() {
        return R.layout.activity_login;
    }

    /**
     * 创建ViewModel
     *
     * @return
     */
    @Override
    protected LoginActivityViewModel createViewModel() {
        mLoginActivityViewModel = new ViewModelProvider(this, mViewModelProviderFactory).get(LoginActivityViewModel.class);
        mLoginActivityViewModel.setNavigator(this);
        return mLoginActivityViewModel;
    }

    @Override
    protected BaseEventBusWrapper getEventBusWrapper() {
        return mLoginActivityEventBusWrapper;
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
        mViewDataBinding = getViewDataBinding();
        setNetworkConfig();
    }

    /**
     * 设置页面视图
     */
    @Override
    protected void setupLayout() {

    }


    @Override
    protected void requestData() {
        super.requestData();
        final LoginUserEntity loginUserEntity = mLoginActivityViewModel.requestGetCurrentLoginUserEntity();
        mLoginActivityViewModel.setTargetUserInfo(loginUserEntity);
    }

    //endregion

    //region: LoginActivityNavigator

    /**
     * 请求登录完成
     *
     * @param loginResponse
     */
    @Override
    public void onRequestLoginComplete(@NonNull final UserLoginResponse loginResponse) {
        mLoginActivityViewModel.setTargetUserInfo(loginResponse.getLoginUserEntity());
    }

    /**
     * 登录按钮点击
     */
    @Override
    public void onLoginClick() {
        mLoginActivityViewModel.requestLogin("123456", String.valueOf(TestUtils.currentTimeMillis()));
    }

    /**
     * 切换网络环境点击
     */
    @Override
    public void onSwitchEnvironmentClick() {
        NetworkConfig.switchEnvironment();
    }

    //endregion

    //region: Fragment 操作

    /**
     * 设置网络配置环境信息
     */
    private void setNetworkConfig() {
        final String networkConfigInfo = getString(R.string.resources_is_develop_environment_format, String.valueOf(NetworkConfig.isNetworkDebug()))
                .concat("\n")
                .concat(getString(R.string.resources_host_format, mBaseUrl));
        mLoginActivityViewModel.setNetworkConfig(networkConfigInfo);
    }

    //endregion

    //region: EventBus 操作

    /**
     * 刷新用户信息
     *
     * @param targetUserEntity
     */
    public void refreshUserInfo(@NonNull final LoginUserEntity targetUserEntity) {

    }

    //endregion


}
