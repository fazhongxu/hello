package com.xxl.hello.main.ui.main;

import android.app.Application;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.xxl.core.exception.ResponseListener;
import com.xxl.core.ui.BaseViewModel;
import com.xxl.hello.common.config.AppConfig;
import com.xxl.hello.main.ui.main.adapter.TestListEntity;
import com.xxl.hello.service.data.model.api.QueryUserInfoRequest;
import com.xxl.hello.service.data.model.api.QueryUserInfoResponse;
import com.xxl.hello.service.data.model.entity.user.LoginUserEntity;
import com.xxl.hello.service.data.repository.DataRepositoryKit;
import com.xxl.hello.service.data.repository.api.UserRepositoryApi;
import com.xxl.hello.service.upload.api.UploadService;
import com.xxl.kit.OnRequestCallBack;
import com.xxl.kit.TimeUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.disposables.Disposable;


/**
 * 首页数据模型
 *
 * @author xxl
 * @date 2021/07/16.
 */
public class MainViewModel extends BaseViewModel<MainNavigator> {

    //region: 成员变量

    /**
     * 数据服务接口集合
     */
    private final DataRepositoryKit mDataRepositoryKit;

    /**
     * 用户ID
     */
    private ObservableField<CharSequence> mObservableUserId = new ObservableField<>();

    /**
     * 用户信息
     */
    private ObservableField<CharSequence> mObservableUserInfo = new ObservableField<>();

    private final UploadService mUploadService;

    /**
     * 是否强制请求网络
     */
    private boolean mForceRequest = true;

    //endregion

    //region: 构造函数

    public MainViewModel(@NonNull final Application application,
                         @NonNull final DataRepositoryKit dataRepositoryKit,
                         @NonNull final UploadService uploadService) {
        super(application);
        mDataRepositoryKit = dataRepositoryKit;
        mUploadService = uploadService;
    }

    //endregion

    //region: 列表测试数据相关

    long mCurrentTimeMillis;

    public void requestListData(int page,
                                int pageSize,
                                @NonNull final OnRequestCallBack<List<TestListEntity>> callBack) {
        if (mCurrentTimeMillis <= 0) {
            mCurrentTimeMillis = TimeUtils.currentServiceTimeMillis();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final List<TestListEntity> list = new ArrayList<>();
                for (int i = 0; i < pageSize; i++) {
                    TestListEntity testListEntity = TestListEntity.obtain()
                            .setContent(String.format("测试数据 page %d index %d", page, i))
                            .setSortTime(mCurrentTimeMillis + 1)
                            .setHeader(i == 5);
                    list.add(testListEntity);
                }
                if (page > 3) {
                    list.remove(list.size() - 1);
                    callBack.onSuccess(list);
                    return;
                }
                callBack.onSuccess(list);
            }
        }, page == 1 ? 3000 : 1000);
    }

    //endregion

    //region: 与用户信息相关

    private int mRetryCount;

    /**
     * 请求查询用户信息
     */
    void requestQueryUserInfo(@NonNull final ResponseListener listener) {
        if (!mForceRequest) {
            getNavigator().onRequestQueryUserInfoComplete(QueryUserInfoResponse.obtain());
            return;
        }
        final QueryUserInfoRequest request = QueryUserInfoRequest.obtain()
                .setTargetUserName(AppConfig.User.GITHUB_USER_NAME);
        final UserRepositoryApi userRepositoryApi = mDataRepositoryKit.getUserRepositoryApi();
        final Disposable disposable = userRepositoryApi.queryUserInfo(request)
                .compose(applySchedulers())
                .subscribe(queryUserInfoResponse -> {
                    mRetryCount = 0;
                    getNavigator().onRequestQueryUserInfoComplete(queryUserInfoResponse);
                }, throwable -> {
                    if (mRetryCount >= 1) {
                        mForceRequest = false;
                    }
                    mRetryCount++;
                    setResponseException(throwable, listener);
                });
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

    public UploadService getUploadService() {
        return mUploadService;
    }

    public ObservableField<CharSequence> getObservableUserId() {
        return mObservableUserId;
    }

    public ObservableField<CharSequence> getObservableUserInfo() {
        return mObservableUserInfo;
    }

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
