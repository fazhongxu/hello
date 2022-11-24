package com.xxl.hello.widget.ui.view.share.impl;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.xxl.core.service.download.DownloadListener;
import com.xxl.core.service.download.DownloadOptions;
import com.xxl.core.service.download.DownloadService;
import com.xxl.core.service.download.DownloadServiceWrapper;
import com.xxl.core.service.download.DownloadTaskEntity;
import com.xxl.core.ui.activity.BaseActivity;
import com.xxl.hello.common.config.ShareConfig;
import com.xxl.hello.service.data.model.entity.share.BaseShareResourceEntity;
import com.xxl.hello.service.data.model.entity.share.ShareOperateItem;
import com.xxl.hello.service.data.model.enums.SystemEnumsApi.ShareOperateType;
import com.xxl.hello.service.data.repository.DataRepositoryKit;
import com.xxl.hello.widget.ui.view.share.OnShareItemOperate;
import com.xxl.hello.widget.ui.view.share.ResourcesShareWindow;
import com.xxl.hello.widget.ui.view.share.api.BaseSharePicker;
import com.xxl.kit.FileUtils;
import com.xxl.kit.ListUtils;
import com.xxl.kit.LogUtils;
import com.xxl.kit.OnRequestCallBack;
import com.xxl.kit.ShareUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xxl.
 * @date 2022/7/18.
 */
public abstract class BaseSharePickerImpl<T extends BaseShareResourceEntity> implements BaseSharePicker<T>, DownloadListener {

    //region: 成员变量

    /**
     * application
     */
    private Application mApplication;

    /**
     * 下载服务
     */
    private DownloadService mDownloadService;

    /**
     * 数据服务接口集合
     */
    private DataRepositoryKit mDataRepositoryKit;

    /**
     * fragment
     */
    private Fragment mFragment;

    /**
     * 下载包装类
     */
    private final DownloadServiceWrapper mDownloadServiceWrapper;

    //endregion

    //region: 构造函数

    public BaseSharePickerImpl(@NonNull Application application,
                               @NonNull Fragment fragment,
                               @NonNull DownloadService downloadService,
                               @NonNull DataRepositoryKit dataRepositoryKit) {
        mApplication = application;
        mDownloadService = downloadService;
        mDataRepositoryKit = dataRepositoryKit;
        mDownloadServiceWrapper = DownloadServiceWrapper.create(application, downloadService);
        register(fragment);
    }

    //endregion

    //region: 页面生命周期

    /**
     * 注册
     *
     * @param fragment
     */
    @Override
    public void register(@NonNull Fragment fragment) {
        mFragment = fragment;
        if (fragment != null && fragment.getActivity() instanceof BaseActivity) {
            ((BaseActivity) fragment.getActivity()).putOnActivityResultListener(ShareConfig.SHARE_PICKER_ACTIVITY_RESULT_KEY, (requestCode, resultCode, data) -> {
                LogUtils.d("分享监听activity Result 回调" + requestCode);
                ShareUtils.onActivityResult(fragment.getActivity(), requestCode, resultCode, data);
            });
        }
    }

    /**
     * 取消注册
     */
    @Override
    public void unregister() {
        mDownloadServiceWrapper.onCleared();
        if (mFragment != null && mFragment.getActivity() instanceof BaseActivity) {
            ((BaseActivity) mFragment.getActivity()).removeOnActivityResultListener(ShareConfig.SHARE_PICKER_ACTIVITY_RESULT_KEY);
        }
    }

    /**
     * 当前页面是否关闭
     *
     * @return
     */
    protected boolean isActivityFinishing() {
        synchronized (this) {
            return mFragment == null || mFragment.getActivity() == null || mFragment.getActivity().isFinishing();
        }
    }

    /**
     * 操作处理（用于页面单独写，功能想用统一实现的情况）
     *
     * @param fragment                   fragment
     * @param operateType                操作类型
     * @param targetShareResourcesEntity 资源分享实体
     */
    @Override
    public void operateHandle(@NonNull final Fragment fragment,
                              @ShareOperateType final int operateType,
                              @NonNull final T targetShareResourcesEntity) {
        register(fragment);
        final ShareOperateItem operateItem = buildOperateItem(operateType);
        if (operateItem != null) {
            operateItem.onClick(targetShareResourcesEntity);
        }
    }

    /**
     * 构建操作条目集合
     *
     * @param operateTypes
     * @return
     */
    protected List<ShareOperateItem> buildOperateItems(@NonNull List<Integer> operateTypes) {
        List<ShareOperateItem> operateItemEntities = new ArrayList<>();
        if (!ListUtils.isEmpty(operateTypes)) {
            for (Integer operateType : operateTypes) {
                final ShareOperateItem operateItemEntity = buildOperateItem(operateType);
                if (operateItemEntity != null) {
                    operateItemEntities.add(operateItemEntity);
                }
            }
        }
        return operateItemEntities;
    }

    /**
     * 构建操作条目
     *
     * @param operateType
     * @return
     */
    protected ShareOperateItem buildOperateItem(@ShareOperateType int operateType) {
        switch (operateType) {
            case ShareOperateType.WE_CHAT:
                return ResourcesShareWindow.buildWeChatAction(this::onWeChatActionClick);
            case ShareOperateType.WE_CHAT_CIRCLE:
                return ResourcesShareWindow.buildWeChatCircleAction(this::onWeChatCircleActionClick);
            default:
                return null;
        }
    }

    /**
     * 获取默认操作类型
     *
     * @param targetShareResourcesEntity
     * @return
     */
    public abstract List<Integer> getDefaultOperateTypes(@NonNull final T targetShareResourcesEntity);

    /**
     * 展示分享弹窗
     *
     * @param fragment
     * @param operateTypes
     * @param targetShareResourcesEntity
     */
    @Override
    public void showSharePicker(@NonNull Fragment fragment,
                                @NonNull List<Integer> operateTypes,
                                @NonNull T targetShareResourcesEntity) {
        showSharePicker(fragment, operateTypes, targetShareResourcesEntity, null);
    }

    /**
     * 展示分享弹窗
     *
     * @param fragment
     * @param targetShareResourcesEntity
     * @param operateTypes
     * @param operate
     */
    @Override
    public void showSharePicker(@NonNull final Fragment fragment,
                                @NonNull final List<Integer> operateTypes,
                                @NonNull final T targetShareResourcesEntity,
                                @Nullable final OnShareItemOperate operate) {
        if (ListUtils.isEmpty(operateTypes)) {
            showShareWindow(fragment, buildOperateItems(getDefaultOperateTypes(targetShareResourcesEntity)), targetShareResourcesEntity, operate);
        } else {
            showShareWindow(fragment, buildOperateItems(operateTypes), targetShareResourcesEntity, operate);
        }
    }

    /**
     * 展示分享弹窗
     *
     * @param fragment
     * @param targetShareResourcesEntity
     * @param operateItems
     * @param operate
     */
    private void showShareWindow(@NonNull final Fragment fragment,
                                 @NonNull final List<ShareOperateItem> operateItems,
                                 @NonNull final T targetShareResourcesEntity,
                                 @Nullable final OnShareItemOperate operate) {
        final OnShareItemOperate onShareItemOperate = new OnShareItemOperate() {

            @Override
            public boolean onClick(@NonNull ResourcesShareWindow window,
                                   @NonNull ShareOperateItem operateItem,
                                   @NonNull View targetView, int position) {
                if (operate != null && operate.onClick(window, operateItem, targetView, position)) {
                    return true;
                }
                operateItem.onClick(targetShareResourcesEntity);
                return false;
            }
        };
        ResourcesShareWindow.from(fragment)
                .addItems(operateItems)
                .setOnItemClickListener(onShareItemOperate)
                .show();
    }

    /**
     * 分享到微信点击
     *
     * @param targetShareResourcesEntity
     */
    public void onWeChatActionClick(@NonNull T targetShareResourcesEntity) {
        if (isActivityFinishing()) {
            return;
        }
    }

    /**
     * 分享到微信朋友圈点击
     *
     * @param targetShareResourcesEntity
     */
    public void onWeChatCircleActionClick(@NonNull T targetShareResourcesEntity) {
        if (isActivityFinishing()) {
            return;
        }
    }

    //endregion

    //region: 与下载相关

    public FragmentActivity getActivity() {
        return mFragment == null ? null : mFragment.getActivity();
    }

    /**
     * 下载完成
     *
     * @param taskEntity
     */
    @Override
    public void onTaskComplete(@NonNull DownloadTaskEntity taskEntity) {

    }

    /**
     * 下载失败
     *
     * @param taskEntity
     * @param throwable
     */
    @Override
    public void onTaskFail(@NonNull DownloadTaskEntity taskEntity,
                           @Nullable Throwable throwable) {

    }

    /**
     * 请求下载文件
     *
     * @param waitDownloadUrls  待下载的文件url
     * @param downloadFilePaths 已经下载的文件路径
     * @param cacheDir          缓存文件夹
     * @param callBack          回调
     */
    protected void requestDownloadFile(@NonNull final List<String> waitDownloadUrls,
                                       @NonNull final List<String> downloadFilePaths,
                                       @NonNull final String cacheDir,
                                       @NonNull final OnRequestCallBack<List<String>> callBack) {
        if (ListUtils.isEmpty(waitDownloadUrls)) {
            callBack.onSuccess(downloadFilePaths);
            return;
        }
        if (FileUtils.createOrExistsDir(cacheDir)) {
            final String targetUrl = waitDownloadUrls.remove(0);
            final DownloadOptions downloadOptions = DownloadOptions.create(targetUrl, cacheDir);
            mDownloadServiceWrapper.createDownloadTask(getActivity(), downloadOptions, new DownloadListener() {
                @Override
                public void onTaskComplete(@NonNull DownloadTaskEntity taskEntity) {
                    downloadFilePaths.add(taskEntity.getSavePath());
                    requestDownloadFile(waitDownloadUrls, downloadFilePaths, cacheDir, callBack);
                }

                @Override
                public void onTaskFail(@NonNull DownloadTaskEntity taskEntity,
                                       @Nullable Throwable throwable) {
                    LogUtils.e(throwable);
                    callBack.onFailure(throwable);
                }
            });
        } else {
            callBack.onFailure(new Throwable("创建文件夹失败"));
        }

    }

    //endregion

}