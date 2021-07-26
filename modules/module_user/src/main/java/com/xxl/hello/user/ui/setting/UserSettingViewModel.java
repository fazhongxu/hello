package com.xxl.hello.user.ui.setting;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableField;

import com.xxl.hello.service.data.model.entity.LoginUserEntity;
import com.xxl.hello.service.data.repository.DataRepositoryKit;
import com.xxl.hello.service.ui.BaseViewModel;
import com.xxl.hello.user.data.repository.UserRepository;

import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * 用户设置数据模型
 *
 * @author xxl.
 * @date 2021/7/16.
 */
@Accessors(prefix = "m")
public class UserSettingViewModel extends BaseViewModel<UserSettingNavigator> {

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

    public UserSettingViewModel(@NonNull final Application application,
                                @NonNull final DataRepositoryKit dataRepositoryKit,
                                @NonNull final UserRepository userRepository) {
        super(application);
        mDataRepositoryKit = dataRepositoryKit;
        mUserRepository = userRepository;
    }

    //endregion

    //region: 与用户信息相关

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
            mObservableUserName.set(targetLoginUserEntity.getUserName() + "--" + targetLoginUserEntity.getUserId());
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