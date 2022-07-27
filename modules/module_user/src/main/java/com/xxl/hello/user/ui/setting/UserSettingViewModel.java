package com.xxl.hello.user.ui.setting;

import android.app.Application;
import android.net.Uri;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableField;

import com.luck.picture.lib.entity.LocalMedia;
import com.xxl.core.image.selector.MediaSelector;
import com.xxl.core.listener.OnResourcesCompressListener;
import com.xxl.hello.common.config.CacheDirConfig;
import com.xxl.hello.service.data.local.db.entity.ResourcesUploadQueueDBEntity;
import com.xxl.hello.service.data.model.entity.user.LoginUserEntity;
import com.xxl.hello.service.data.model.enums.SystemEnumsApi;
import com.xxl.hello.service.data.repository.DataRepositoryKit;
import com.xxl.hello.service.data.repository.api.ResourceRepositoryApi;
import com.xxl.hello.user.R;
import com.xxl.hello.user.data.repository.UserRepository;
import com.xxl.hello.widget.ui.model.resource.BaseResourceQueueViewModel;
import com.xxl.kit.ImageUtils;
import com.xxl.kit.ListUtils;
import com.xxl.kit.LogUtils;
import com.xxl.kit.PathUtils;
import com.xxl.kit.ToastUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.disposables.Disposable;

/**
 * 用户设置数据模型
 *
 * @author xxl.
 * @date 2021/7/16.
 */
public class UserSettingViewModel extends BaseResourceQueueViewModel<UserSettingNavigator> {

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
    private ObservableField<String> mObservableUserName = new ObservableField<>();

    /**
     * 用户头像地址
     */
    private ObservableField<String> mObservableUserAvatarUrl = new ObservableField<>();

    /**
     * 当前网络环境信息
     */
    private ObservableField<String> mObservableNetworkConfig = new ObservableField<>();

    /**
     * 用户信息
     */
    private LoginUserEntity mTargetLoginUserEntity;

    //endregion

    //region: 构造函数

    public UserSettingViewModel(@NonNull final Application application,
                                @NonNull final DataRepositoryKit dataRepositoryKit,
                                @NonNull final UserRepository userRepository) {
        super(application, dataRepositoryKit);
        mDataRepositoryKit = dataRepositoryKit;
        mUserRepository = userRepository;
    }

    //endregion

    //region: 与用户信息相关

    /**
     * 请求添加上传资源到数据库
     *
     * @param targetMedias
     */
    public void requestPutResourcesUploadQueueDBEntities(@NonNull final ArrayList<LocalMedia> targetMedias) {
        if (ListUtils.isEmpty(targetMedias)) {
            return;
        }
        final List<ResourcesUploadQueueDBEntity> targetResourcesUploadQueueDBEntities = new ArrayList<>();
        for (LocalMedia localMedia : targetMedias) {
            final ResourcesUploadQueueDBEntity resourcesUploadQueueDBEntity = new ResourcesUploadQueueDBEntity();
            resourcesUploadQueueDBEntity.setSubmitTaskId(getTaskId())
                    .setMediaType(MediaSelector.isVideo(localMedia.getMimeType()) ? SystemEnumsApi.MediaType.VIDEO : SystemEnumsApi.MediaType.IMAGE)
                    .setWaitUploadUrl(PathUtils.getFilePathByUri(Uri.parse(localMedia.getPath())));
            targetResourcesUploadQueueDBEntities.add(resourcesUploadQueueDBEntity);
        }
        requestPutResourcesUploadQueueDBEntities(targetResourcesUploadQueueDBEntities);
    }

    /**
     * 请求添加上传资源到数据库
     *
     * @param resourcesUploadQueueDBEntities
     */
    public void requestPutResourcesUploadQueueDBEntities(@NonNull final List<ResourcesUploadQueueDBEntity> resourcesUploadQueueDBEntities) {
        final ResourceRepositoryApi resourceRepositoryApi = getDataRepositoryKit().getResourceRepositoryApi();
        final Disposable disposable = resourceRepositoryApi.putResourcesUploadQueueDBEntities(resourcesUploadQueueDBEntities)
                .compose(applySchedulers())
                .subscribe(isSuccess -> {
                    LogUtils.d("添加资源到队列完成" + isSuccess);
                    getNavigator().onRequestPutResourcesUploadQueueDBEntities(isSuccess);
                }, throwable -> {
                    setResponseException(throwable);
                });
        addCompositeDisposable(disposable);
    }

    /**
     * 请求更新用户信息
     *
     * @param avatarPath 头像路径
     */
    void requestUpdateUserInfo(@NonNull final String avatarPath) {
        setViewLoading(true);
        final OnResourcesCompressListener listener = new OnResourcesCompressListener() {

            @Override
            public void onComplete(String filePath,
                                   long width,
                                   long height) {
                final LoginUserEntity loginUserEntity = LoginUserEntity.obtain()
                        .setUserAvatar(filePath);
                submitUserInfoToService(loginUserEntity);
            }

            @Override
            public void onFailure(Throwable throwable) {
                setResponseException(throwable);
            }
        };
        handleImageCompress(avatarPath, listener);
    }

    /**
     * 提交用户信息到服务器
     *
     * @param targetUserEntity
     */
    void submitUserInfoToService(@NonNull final LoginUserEntity targetUserEntity) {
        // FIXME: 2021/8/29  模拟网络请求
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setViewLoading(false);
                getNavigator().onUpdateUserInfoComplete(targetUserEntity);
            }
        }, 3000);
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

    //region: 图片压缩相关

    /**
     * 图片压缩长距离
     *
     * @param imagePath 图片路径
     */
    public void handleImageCompress(@NonNull final String imagePath,
                                    @NonNull final OnResourcesCompressListener listener) {
        ImageUtils.compress(imagePath, CacheDirConfig.COMPRESSION_FILE_DIR,
                new ImageUtils.OnSimpleCompressListener() {
                    @Override
                    public void onSuccess(File file) {
                        listener.onComplete(file.getAbsolutePath(), 0, 0);
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.error(R.string.resources_compress_failure_text).show();
                        listener.onFailure(e);
                    }
                });
    }

    //endregion

    //region: get or set

    public LoginUserEntity getTargetLoginUserEntity() {
        return mTargetLoginUserEntity;
    }

    public ObservableField<String> getObservableUserName() {
        return mObservableUserName;
    }

    public ObservableField<String> getObservableUserAvatarUrl() {
        return mObservableUserAvatarUrl;
    }

    public ObservableField<String> getObservableNetworkConfig() {
        return mObservableNetworkConfig;
    }

    /**
     * 设置用户信息
     *
     * @param targetLoginUserEntity
     */
    void setTargetUserInfo(@Nullable final LoginUserEntity targetLoginUserEntity) {
        mTargetLoginUserEntity = targetLoginUserEntity;
        if (mTargetLoginUserEntity != null) {
            mObservableUserName.set(targetLoginUserEntity.getUserName() + "--" + targetLoginUserEntity.getUserId());
            setObservableUserAvatarUrl(targetLoginUserEntity.getAvatar());
        }
    }

    /**
     * 设置用户头像地址
     *
     * @param avatarUrl
     */
    void setObservableUserAvatarUrl(@NonNull final String avatarUrl) {
        mObservableUserAvatarUrl.set(avatarUrl);
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
        mObservableNetworkConfig.set(networkConfig);
    }

    //endregion

}