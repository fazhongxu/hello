package com.xxl.user.ui;

import android.app.Application;

import androidx.annotation.NonNull;

import com.xxl.service.ui.BaseViewModel;
import com.xxl.user.data.repository.UserRepository;

/**
 * @author xxl.
 * @date 2021/7/16.
 */
public class LoginActivityViewModel extends BaseViewModel {

    //region: 成员变量

    private final UserRepository mUserRepository;

    //endregion

    //region: 构造函数

    public LoginActivityViewModel(@NonNull final Application application,
                                  @NonNull final UserRepository userRepository) {
        super(application);
        mUserRepository = userRepository;
    }

    //endregion

    //region: 与用户登录相关

    /**
     * 请求登录
     *
     * @param phoneNumber 手机号
     * @param verifyCode  验证码
     */
    void requestLogin(@NonNull final String phoneNumber,
                      @NonNull final String verifyCode) {
        mUserRepository.login(phoneNumber, verifyCode);
    }

    //endregion

}