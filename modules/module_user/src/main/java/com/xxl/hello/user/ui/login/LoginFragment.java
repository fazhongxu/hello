package com.xxl.hello.user.ui.login;

import android.view.View;

import androidx.annotation.NonNull;

import com.xxl.core.ui.fragment.BaseViewModelFragment;
import com.xxl.hello.router.api.UserRouterApi;
import com.xxl.hello.service.data.model.entity.user.LoginUserEntity;
import com.xxl.hello.user.BR;
import com.xxl.hello.user.R;
import com.xxl.hello.user.data.model.api.UserLoginResponse;
import com.xxl.hello.user.databinding.UserFragmentLoginBinding;
import com.xxl.kit.AppRouterApi;
import com.xxl.kit.RouterUtils;
import com.xxl.kit.TimeUtils;

import javax.inject.Inject;

/**
 * 登录页
 *
 * @author xxl.
 * @date 2022/7/29.
 */
public class LoginFragment extends BaseViewModelFragment<LoginViewModel, UserFragmentLoginBinding> implements LoginNavigator {

    //region: 成员变量

    /**
     * 登录页面ViewModel数据模型
     */
    private LoginViewModel mLoginViewModel;

    /**
     * 登录页面EventBus通知事件监听
     */
    @Inject
    LoginEventBusWrapper mLoginEventBusWrapper;

    //endregion

    //region: 构造函数

    public final static LoginFragment newInstance() {
        return new LoginFragment();
    }

    //endregion

    //region: 页面生命周期

    @Override
    protected int getLayoutRes() {
        return R.layout.user_fragment_login;
    }

    @Override
    protected LoginViewModel createViewModel() {
        mLoginViewModel = createViewModel(LoginViewModel.class);
        mLoginViewModel.setNavigator(this);
        return mLoginViewModel;
    }

    @Override
    protected LoginEventBusWrapper getEventBusWrapper() {
        return mLoginEventBusWrapper;
    }

    @Override
    public int getViewModelVariable() {
        return BR.viewModel;
    }

    @Override
    public int getViewNavigatorVariable() {
        return BR.navigator;
    }

    @Override
    protected void setupData() {

    }

    @Override
    protected void setupLayout(@NonNull View view) {

    }

    //endregion

    //region: LoginNavigator

    /**
     * 请求登录完成
     *
     * @param loginResponse
     */
    @Override
    public void onRequestLoginComplete(@NonNull final UserLoginResponse loginResponse) {
        mLoginViewModel.setTargetUserInfo(loginResponse.getLoginUserEntity());
        if (RouterUtils.hasActivity(AppRouterApi.MAIN_PATH)) {
            AppRouterApi.Login.setActivityResult(getActivity());
        }else {
            AppRouterApi.Main.newBuilder().navigationWithFinish(getActivity());
        }
    }

    /**
     * 登录按钮点击
     */
    @Override
    public void onLoginClick() {
        mLoginViewModel.requestLogin("123456", String.valueOf(TimeUtils.currentServiceTimeMillis()));
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
        // do something
    }

    //endregion

}