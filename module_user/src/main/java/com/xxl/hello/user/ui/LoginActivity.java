package com.xxl.hello.user.ui;

import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModelProvider;

import com.xxl.hello.service.ui.BaseActivity;
import com.xxl.hello.user.R;

/**
 * @author xxl.
 * @date 2021/07/16.
 */
public class LoginActivity extends BaseActivity<LoginActivityViewModel> {

    //region: 成员变量

    /**
     * 登录按钮
     */
    private AppCompatButton mBtnLogin;

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
        return mLoginActivityViewModel = new ViewModelProvider(this, mViewModelProviderFactory).get(LoginActivityViewModel.class);
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
        mBtnLogin.setOnClickListener(v -> {
            mLoginActivityViewModel.requestLogin("123456", "abc");
        });
    }

    //endregion


}
