package com.xxl.hello.user.ui;

import android.app.Application;

import androidx.annotation.NonNull;

import com.xxl.hello.service.ui.BaseViewModel;
import com.xxl.hello.user.data.model.api.UserLoginRequest;
import com.xxl.hello.user.data.repository.UserRepository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * @author xxl.
 * @date 2021/7/16.
 */
public class LoginActivityViewModel extends BaseViewModel<LoginActivityNavigator> {

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
        final UserLoginRequest request = UserLoginRequest.obtain()
                .setPhoneNumber(phoneNumber)
                .setVerifyCode(verifyCode);
        final Disposable disposable = mUserRepository.login(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userLoginResponse -> {
                    getNavigator().requestLoginComplete(userLoginResponse);
                }, this::setResponseException);
        addCompositeDisposable(disposable);
    }

    //endregion

}