package com.xxl.hello.main.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.xxl.hello.common.AppConfig;
import com.xxl.hello.service.data.model.api.QueryUserInfoRequest;
import com.xxl.hello.service.data.model.entity.LoginUserEntity;
import com.xxl.hello.service.data.repository.DataRepositoryKit;
import com.xxl.hello.service.data.repository.api.UserRepositoryApi;
import com.xxl.hello.service.ui.BaseViewModel;

import io.reactivex.rxjava3.disposables.Disposable;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * 首页数据模型
 *
 * @author xxl
 * @date 2021/07/16.
 */
@Accessors(prefix = "m")
public class MainViewModel extends BaseViewModel<MainActivityNavigator> {

    //region: 成员变量

    /**
     * 数据服务接口集合
     */
    private final DataRepositoryKit mDataRepositoryKit;

    /**
     * 用户ID
     */
    @Getter
    private ObservableField<String> mObservableUserId = new ObservableField<>();

    /**
     * 用户信息
     */
    @Getter
    private ObservableField<String> mObservableUserInfo = new ObservableField<>();

    //endregion

    //region: 构造函数

    public MainViewModel(@NonNull final Application application,
                         @NonNull final DataRepositoryKit dataRepositoryKit) {
        super(application);
        mDataRepositoryKit = dataRepositoryKit;
    }

    //endregion

    //region: 与用户信息相关

    /**
     * 请求查询用户信息
     */
    void requestQueryUserInfo() {
        final QueryUserInfoRequest request = QueryUserInfoRequest.obtain()
                .setTargetUserName(AppConfig.User.GITHUB_USER_NAME);
        final UserRepositoryApi userRepositoryApi = mDataRepositoryKit.getUserRepositoryApi();
        final Disposable disposable = userRepositoryApi.queryUserInfo(request)
                .compose(applySchedulers())
                .subscribe(queryUserInfoResponse -> {
                    getNavigator().onRequestQueryUserInfoComplete(queryUserInfoResponse);
                }, this::setResponseException);
        addCompositeDisposable(disposable);
    }

    /**
     * 获取当前登录用户的信息
     *
     * @return
     */
    LoginUserEntity requestGetCurrentLoginUserEntity() {
        final UserRepositoryApi userRepositoryApi = mDataRepositoryKit.getUserRepositoryApi();
        return userRepositoryApi.getCurrentLoginUserEntity();
    }

    //endregion

    //region: 与隐私政策相关

    /**
     * 设置用户同意"隐私协议"的状态
     *
     * @param isAgree
     */
    void setAgreePrivacyPolicyStatus(final boolean isAgree) {
        final UserRepositoryApi userRepositoryApi = mDataRepositoryKit.getUserRepositoryApi();
        userRepositoryApi.setAgreePrivacyPolicyStatus(isAgree);
    }

    //endregion

    //region: get or set

    /**
     * 设置用户ID
     *
     * @param targetUserId
     */
    public void setObservableUserId(@NonNull final String targetUserId) {
        this.mObservableUserId.set(targetUserId);
    }

    /**
     * 设置用户信息
     *
     * @param targetUserInfo
     */
    public void setObservableUserInfo(@NonNull final String targetUserInfo) {
        this.mObservableUserInfo.set(targetUserInfo);
    }

    //endregion

}
