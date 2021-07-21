package com.xxl.hello.user.ui;

import android.app.Application;

import androidx.annotation.NonNull;

import com.xxl.hello.service.data.repository.DataRepositoryKit;
import com.xxl.hello.service.ui.BaseViewModel;
import com.xxl.hello.user.data.model.api.UserLoginRequest;
import com.xxl.hello.user.data.repository.UserRepository;

import io.reactivex.rxjava3.disposables.Disposable;

/**
 * @author xxl.
 * @date 2021/7/16.
 */
public class LoginActivityViewModel extends BaseViewModel<LoginActivityNavigator> {

    //region: 成员变量

    /**
     * 数据服务接口集合
     */
    private final DataRepositoryKit mDataRepositoryKit;

    /**
     * 用户模块服务接口
     */
    private final UserRepository mUserRepository;

    //endregion

    //region: 构造函数

    public LoginActivityViewModel(@NonNull final Application application,
                                  @NonNull final DataRepositoryKit dataRepositoryKit,
                                  @NonNull final UserRepository userRepository) {
        super(application);
        mDataRepositoryKit = dataRepositoryKit;
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
                .compose(applySchedulers())
                .subscribe(userLoginResponse -> {
                    getNavigator().requestLoginComplete(userLoginResponse);
                }, this::setResponseException);
        addCompositeDisposable(disposable);
    }

    //endregion

}