package com.xxl.hello.user.ui.login;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableField;

import com.xxl.hello.service.data.model.entity.LoginUserEntity;
import com.xxl.hello.service.data.repository.DataRepositoryKit;
import com.xxl.hello.service.ui.BaseViewModel;
import com.xxl.hello.user.data.model.api.UserLoginRequest;
import com.xxl.hello.user.data.repository.UserRepository;

import io.reactivex.rxjava3.disposables.Disposable;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * @author xxl.
 * @date 2021/7/16.
 */
@Accessors(prefix = "m")
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

    /**
     * 用户昵称
     */
    @Getter
    private ObservableField<String> mObservableUserName = new ObservableField<>();

    /**
     * 当前网络环境信息
     */
    @Getter
    private ObservableField<String> mObservableNetworkConfig = new ObservableField<>();

    /**
     * 用户信息
     */
    @Getter
    private LoginUserEntity mTargetLoginUserEntity;

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
                    getNavigator().onRequestLoginComplete(userLoginResponse);
                }, this::setResponseException);
        addCompositeDisposable(disposable);
    }

    /**
     * 获取当前登录用户的信息
     *
     * @return
     */
    LoginUserEntity requestGetCurrentLoginUserEntity() {
        return mUserRepository.getCurrentLoginUserEntity();
    }

    //endregion

    //region: get or set

    /**
     * 设置用户信息
     *
     * @param targetLoginUserEntity
     */
    void setTargetUserInfo(@Nullable final LoginUserEntity targetLoginUserEntity) {
        mTargetLoginUserEntity = targetLoginUserEntity;
        if (mTargetLoginUserEntity != null) {
            mObservableUserName.set(targetLoginUserEntity.getUserId());
        }
    }

    /**
     * 设置当前网络环境配置信息
     *
     * @param networkConfig
     */
    void setNetworkConfig(@NonNull final String networkConfig) {
        setObservableNetworkConfig(networkConfig);
    }

    /**
     * 设置当前网络环境配置信息
     *
     * @param networkConfig
     */
    void setObservableNetworkConfig(@NonNull final String networkConfig) {
        this.mObservableNetworkConfig.set(networkConfig);
    }

    //endregion

}