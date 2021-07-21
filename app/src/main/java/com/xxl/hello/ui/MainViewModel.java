package com.xxl.hello.ui;

import android.app.Application;

import androidx.annotation.NonNull;

import com.xxl.hello.service.data.model.api.QueryUserInfoRequest;
import com.xxl.hello.service.data.model.api.QueryUserInfoResponse;
import com.xxl.hello.service.data.model.entity.LoginUserEntity;
import com.xxl.hello.service.data.repository.DataRepositoryKit;
import com.xxl.hello.service.data.repository.api.UserRepositoryApi;
import com.xxl.hello.service.ui.BaseViewModel;

import io.reactivex.rxjava3.disposables.Disposable;

/**
 * 首页数据模型
 *
 * @author xxl
 * @date 2021/07/16.
 */
public class MainViewModel extends BaseViewModel<MainActivityNavigator> {

    //region: 成员变量

    /**
     * 数据服务接口集合
     */
    private final DataRepositoryKit mDataRepositoryKit;

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
        final QueryUserInfoRequest request = QueryUserInfoRequest.obtain();
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

}
