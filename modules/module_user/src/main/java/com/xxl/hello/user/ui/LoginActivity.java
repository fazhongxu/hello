package com.xxl.hello.user.ui;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.xxl.hello.common.utils.TestUtils;
import com.xxl.hello.service.data.model.entity.LoginUserEntity;
import com.xxl.hello.service.ui.DataBindingActivity;
import com.xxl.hello.user.R;
import com.xxl.hello.user.data.model.api.UserLoginResponse;

/**
 * @author xxl.
 * @date 2021/07/16.
 */
public class LoginActivity extends DataBindingActivity<LoginActivityViewModel> implements LoginActivityNavigator {

    //region: 成员变量

    /**
     * 登录页面ViewModel数据模型
     */
    private LoginActivityViewModel mLoginActivityViewModel;

    //endregion

    //region: 页面生命周期

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
     * 获取视图资源ID
     */
    @Override
    protected int getLayoutRes() {
        return R.layout.activity_login;
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
        Toast.makeText(this, loginResponse.getLoginUserEntity().getUserName(), Toast.LENGTH_SHORT).show();
        mLoginActivityViewModel.setTargetUserInfo(loginResponse.getLoginUserEntity());
    }

    /**
     * 登录按钮点击
     */
    @Override
    public void onLoginClick() {
        // FIXME: 2021/7/22 引入了databinding 但是 点击事件和数据绑定未生效，待处理
        mLoginActivityViewModel.requestLogin("123456", String.valueOf(TestUtils.currentTimeMillis()));
    }

    //endregion

    //region: Fragment 操作


    //endregion


}
