package com.xxl.hello.user.ui.login;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xxl.core.utils.TestUtils;
import com.xxl.hello.router.UserRouterApi;
import com.xxl.hello.service.data.model.entity.LoginUserEntity;
import com.xxl.hello.service.ui.BaseEventBusWrapper;
import com.xxl.hello.service.ui.DataBindingActivity;
import com.xxl.hello.user.BR;
import com.xxl.hello.user.R;
import com.xxl.hello.user.data.model.api.UserLoginResponse;
import com.xxl.hello.user.databinding.UserActivityLoginBinding;

import javax.inject.Inject;

/**
 * @author xxl.
 * @date 2021/07/16.
 */
@Route(path = UserRouterApi.Login.PATH)
public class LoginActivity extends DataBindingActivity<LoginActivityViewModel, UserActivityLoginBinding> implements LoginActivityNavigator {

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

    //endregion

    //region: 页面生命周期

    /**
     * 获取视图资源ID
     */
    @Override
    protected int getLayoutRes() {
        return R.layout.user_activity_login;
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

    /**
     * 获取EventBus事件监听类
     *
     * @return
     */
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
     * 设置按钮点击
     */
    @Override
    public void onSettingClick() {
        UserRouterApi.UserSetting.navigation();
    }

    //endregion

    //region: Fragment 操作


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
