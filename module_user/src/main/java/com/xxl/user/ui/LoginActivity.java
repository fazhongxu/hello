package com.xxl.user.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.xxl.service.ui.BaseViewModel;
import com.xxl.service.ui.ViewModelProviderFactory;
import com.xxl.user.R;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

/**
 * @author xxl.
 * @date 2021/07/16.
 */
public class LoginActivity extends AppCompatActivity {

    //region: 成员变量

    /**
     * 登录按钮
     */
    private AppCompatButton mBtnLogin;

    @Inject
    ViewModelProvider.Factory mViewModelProviderFactory;

    LoginActivityViewModel mLoginActivityViewModel;

    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mBtnLogin = findViewById(R.id.btn_login);
        if (enableInjection()) {
            AndroidInjection.inject(this);
        }
        mLoginActivityViewModel = createViewModel();
        mBtnLogin.setOnClickListener(v -> {
            mLoginActivityViewModel.requestLogin("123456", "abc");
        });
    }

    LoginActivityViewModel createViewModel() {
        return new ViewModelProvider(this, mViewModelProviderFactory).get(LoginActivityViewModel.class);
    }

    /**
     * 是否需要依赖注入
     *
     * @return
     */
    private boolean enableInjection() {
        return true;
    }
}
