package com.xxl.hello.user.ui;

import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModelProvider;

import com.xxl.hello.common.utils.TestUtils;
import com.xxl.hello.service.data.model.entity.LoginUserEntity;
import com.xxl.hello.service.ui.BaseActivity;
import com.xxl.hello.user.R;
import com.xxl.hello.user.data.model.api.UserLoginResponse;

/**
 * @author xxl.
 * @date 2021/07/16.
 */
public class LoginActivity extends BaseActivity<LoginActivityViewModel> implements LoginActivityNavigator {

    //region: 成员变量

    /**
     * 登录按钮
     */
    private AppCompatButton mBtnLogin;

    /**
     * 用户信息控件
     */
    private TextView mTvUserInfo;

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
        mBtnLogin = findViewById(R.id.btn_login);
        mTvUserInfo = findViewById(R.id.tv_user_info);
        mBtnLogin.setOnClickListener(v -> {
            mLoginActivityViewModel.requestLogin("123456", String.valueOf(TestUtils.currentTimeMillis()));
        });
    }

    @Override
    protected void requestData() {
        super.requestData();
        final LoginUserEntity loginUserEntity = mLoginActivityViewModel.requestGetCurrentLoginUserEntity();
        if (loginUserEntity != null) {
            if (!TextUtils.isEmpty(loginUserEntity.getUserId())) {
                mTvUserInfo.setText(loginUserEntity.getUserId());
            }
        }
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
    }

    //endregion


}
