package com.xxl.hello.user.ui.setting;

import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xxl.hello.core.config.NetworkConfig;
import com.xxl.hello.router.paths.UserRouterApi;
import com.xxl.hello.service.data.model.entity.LoginUserEntity;
import com.xxl.hello.service.qunlifier.ForUserBaseUrl;
import com.xxl.hello.service.ui.DataBindingActivity;
import com.xxl.hello.user.BR;
import com.xxl.hello.user.R;
import com.xxl.hello.user.databinding.UserActivitySettingBinding;

import javax.inject.Inject;

/**
 * 用户设置页面
 *
 * @author xxl.
 * @date 2021/07/26.
 */
@Route(path = UserRouterApi.UserSetting.PATH)
public class UserSettingActivity extends DataBindingActivity<UserSettingViewModel, UserActivitySettingBinding> implements UserSettingNavigator {

    //region: 成员变量

    /**
     * 用户设置数据模型
     */
    private UserSettingViewModel mUserSettingViewModel;

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
        return R.layout.user_activity_setting;
    }

    /**
     * 创建ViewModel
     *
     * @return
     */
    @Override
    protected UserSettingViewModel createViewModel() {
        mUserSettingViewModel = new ViewModelProvider(this, mViewModelProviderFactory).get(UserSettingViewModel.class);
        mUserSettingViewModel.setNavigator(this);
        return mUserSettingViewModel;
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
        final LoginUserEntity loginUserEntity = mUserSettingViewModel.requestGetCurrentLoginUserEntity();
        mUserSettingViewModel.setTargetUserInfo(loginUserEntity);
    }

    //endregion

    //region: UserSettingNavigator

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
        mUserSettingViewModel.setNetworkConfig(networkConfigInfo);
    }

    //endregion

    //region: EventBus 操作


    //endregion


}
