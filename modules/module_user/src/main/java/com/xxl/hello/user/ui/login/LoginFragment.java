package com.xxl.hello.user.ui.login;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.xxl.core.ui.fragment.BaseViewModelFragment;
import com.xxl.core.utils.AppExpandUtils;
import com.xxl.hello.common.config.AppOptions.VipConfig;
import com.xxl.hello.common.config.CacheDirConfig;
import com.xxl.hello.router.api.MainRouterApi;
import com.xxl.hello.router.api.UserRouterApi;
import com.xxl.hello.service.data.model.entity.user.LoginUserEntity;
import com.xxl.hello.service.data.repository.DataRepositoryKit;
import com.xxl.hello.user.BR;
import com.xxl.hello.user.R;
import com.xxl.hello.user.data.model.api.UserLoginResponse;
import com.xxl.hello.user.databinding.UserFragmentLoginBinding;
import com.xxl.hello.user.ui.login.window.PrivacyPolicyPopupWindow;
import com.xxl.hello.widget.data.router.WidgetRouterApi;
import com.xxl.hello.widget.ui.listener.OnVipInterceptListener;
import com.xxl.hello.widget.ui.model.aop.annotation.VipIntercept;
import com.xxl.kit.AppUtils;
import com.xxl.kit.FileUtils;
import com.xxl.kit.LogUtils;
import com.xxl.kit.ResourceUtils;
import com.xxl.kit.RouterUtils;
import com.xxl.kit.TimeUtils;
import com.xxl.kit.ToastUtils;

import java.io.File;

import javax.inject.Inject;

/**
 * 登录页
 *
 * @author xxl.
 * @date 2022/7/29.
 */
public class LoginFragment extends BaseViewModelFragment<LoginViewModel, UserFragmentLoginBinding> implements LoginNavigator, OnVipInterceptListener {

    //region: 成员变量

    /**
     * 登录页面ViewModel数据模型
     */
    private LoginViewModel mLoginViewModel;

    /**
     * 下一个跳转路径
     */
    @Autowired(name = UserRouterApi.Login.PARAMS_KEY_NEXT_PATH)
    String mNextPath;

    /**
     * 数据
     */
    @Autowired(name = UserRouterApi.Login.PARAMS_KEY_EXTRA_DATA)
    String mExtraData;

    /**
     * 登录页面EventBus通知事件监听
     */
    @Inject
    LoginEventBusWrapper mLoginEventBusWrapper;

    //endregion

    //region: 构造函数

    public final static LoginFragment newInstance(@NonNull final Bundle args) {
        final LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
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
    protected boolean enableRouterInject() {
        return true;
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
        LogUtils.d("当前登录用户ID..." + AppExpandUtils.getCurrentUserId());
        if (!AppExpandUtils.isAgreePrivacyPolicy()) {
            showPrivacyPolicyPopupWindow();
        }
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
        if (RouterUtils.hasActivity(MainRouterApi.Main.PATH)) {
            UserRouterApi.Login.setActivityResult(getActivity());
        } else {
            MainRouterApi.Main.newBuilder()
                    .setNextPath(mNextPath)
                    .setExtraData(mExtraData)
                    .navigationWithFinish(getActivity());
        }
    }

    /**
     * 登录按钮点击
     */
    @Override
    public void onLoginClick() {
        if (!AppExpandUtils.isAgreePrivacyPolicy()) {
            ToastUtils.warning(R.string.resources_privacy_policy_agree_tips).show();
            return;
        }
        mLoginViewModel.requestLogin("123456", String.valueOf(TimeUtils.currentServiceTimeMillis()));
    }

    /**
     * 设置按钮点击
     */
    @Override
    public void onSettingClick() {
        UserRouterApi.UserSetting.navigation();
    }

    /**
     * 设置按钮长按点击
     *
     * @return
     */
    @Override
    public boolean onSettingLongClick() {
        navigationToFileBrowser();
        return true;
    }

    /**
     * 导航到文件浏览页面
     * 记录下，AOP 直接作用在长按点击事件上无效，原因暂时不知道，所以拆了个方法出来，点击事件生效
     */
    @VipIntercept(functionId = VipConfig.LONG_CLICK_USER_SETTING_VIP_FUNCTION_ID)
    private void navigationToFileBrowser() {
        final String targetFilePath = CacheDirConfig.CACHE_DIR + File.separator + "alibaba_doc.pdf";
        if (!FileUtils.isFileExists(targetFilePath)) {
            boolean isSuccess = ResourceUtils.copyFileFromAssets("alibaba_doc.pdf", targetFilePath);
            if (!isSuccess) {
                ToastUtils.warning(R.string.resources_file_copy_failure_text).show();
                return;
            }
        }
        WidgetRouterApi.FileBrowser.newBuilder()
                .setFilePath(targetFilePath)
                .navigation();
    }

    //endregion

    //region: OnVipInterceptListener

    /**
     * 获取数据服务接口集合
     *
     * @return
     */
    @Override
    public DataRepositoryKit getDataRepositoryKit() {
        return mViewModel.getDataRepositoryKit();
    }

    //endregion

    //region: Fragment 操作

    /**
     * 弹出隐私政策弹窗
     */
    private void showPrivacyPolicyPopupWindow() {
        PrivacyPolicyPopupWindow.from(getActivity())
                .setOnDisagreeClickListener(v -> {
                    AppUtils.exitApp();
                })
                .setOnAgreeClickListener(v -> {
                    mLoginViewModel.setAgreePrivacyPolicyStatus(true);
                    AppExpandUtils.initPluginsAfterAgreePrivacyPolicy();
                    requestData();
                })
                .showPopupWindow();
    }

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