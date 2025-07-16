package com.xxl.hello.main.ui.main;

import android.app.Application;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import com.xxl.core.response.ResponseListener;
import com.xxl.core.service.upload.UploadListener;
import com.xxl.core.ui.BaseViewModel;
import com.xxl.hello.common.config.AppConfig;
import com.xxl.hello.main.ui.main.adapter.TestListEntity;
import com.xxl.hello.service.data.model.api.user.QueryUserInfoRequest;
import com.xxl.hello.service.data.model.api.user.QueryUserInfoResponse;
import com.xxl.hello.service.data.model.entity.user.LoginUserEntity;
import com.xxl.hello.service.data.model.enums.SystemEnumsApi;
import com.xxl.hello.service.data.repository.DataRepositoryKit;
import com.xxl.hello.service.data.repository.api.UserRepositoryApi;
import com.xxl.hello.service.upload.api.UploadService;
import com.xxl.kit.OnRequestCallBack;
import com.xxl.kit.TimeUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

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

    public ObservableBoolean getObservable1() {
        return new ObservableBoolean(true);
    }

    public ObservableBoolean getObservable2() {
        return new ObservableBoolean(false);
    }

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

                List<String> urls = Arrays.asList("https://wx3.sinaimg.cn/mw1024/005PbyNrgy1hu221wp83rj30uv0u07n4.jpg", "https://pic.rmb.bdstatic.com/bjh/240824/b886bc30e7322702af5fb0e83c3bbc727002.png",
                        "https://t15.baidu.com/it/u=2677963247,2964041049&fm=225&app=113&f=JPEG?w=2163&h=1620&s=FB0FB044CC02C0D624A230800300E098", "https://i0.hdslb.com/bfs/archive/1dce32b0b77620f9f956b365fe23e6500cd031da.jpg",
                        "https://img0.baidu.com/it/u=3591172426,37535292&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=750", "https://i0.hdslb.com/bfs/archive/115d82e813da27df8c1d68e7dd15508f147e62eb.jpg",
                        "https://pics7.baidu.com/feed/5366d0160924ab1870d8fdc7bc85a0c279890b86.jpeg@f_auto?token=668d13f5d2d9af9d1a189555574901ec");
                Random random = new Random();
                for (int i = 0; i < pageSize; i++) {
                    TestListEntity testListEntity = TestListEntity.obtain()
                            .setContent(String.format("测试数据 page %d index %d", page, i))
//                            .setMediaType(i % 2 == 0 ? SystemEnumsApi.CircleMediaType.IMAGE : SystemEnumsApi.CircleMediaType.TEXT)
                            .setMediaType(SystemEnumsApi.CircleMediaType.IMAGE)
                            .setUrl(urls.get(random.nextInt(urls.size())))
                            .setSortTime(mCurrentTimeMillis += 1)
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

    //region: 资源上传相关

    /**
     * 测试上传
     *
     * @param file
     */
    void testUpload(File file) {
        mUploadService.upload(file, new UploadListener() {
            @Override
            public void onUploadStart(String key) {

            }

            @Override
            public void onUploadComplete(String key, String url) {

            }

            @Override
            public void onUploadFailure(String key, Throwable e) {

            }
        });
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
    public void setObservableUserId(@NonNull final CharSequence targetUserId) {
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
