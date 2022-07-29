package com.xxl.hello.widget.share.impl;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.xxl.core.service.download.DownloadService;
import com.xxl.hello.service.data.model.entity.share.BaseShareResourceEntity;
import com.xxl.hello.service.data.model.entity.share.ShareOperateItem;
import com.xxl.hello.service.data.model.enums.SystemEnumsApi.ShareOperateType;
import com.xxl.hello.service.data.repository.DataRepositoryKit;
import com.xxl.hello.widget.share.OnShareItemOperate;
import com.xxl.hello.widget.share.ResourcesShareWindow;
import com.xxl.hello.widget.share.api.BaseSharePicker;
import com.xxl.kit.ListUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xxl.
 * @date 2022/7/18.
 */
public abstract class BaseSharePickerImpl<T extends BaseShareResourceEntity> implements BaseSharePicker<T> {

    //region: 页面生命周期

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

    //endregion

    //region: 构造函数

    public BaseSharePickerImpl(@NonNull Application application,
                               @NonNull Fragment fragment,
                               @NonNull DownloadService downloadService,
                               @NonNull DataRepositoryKit dataRepositoryKit) {
        mApplication = application;
        mDownloadService = downloadService;
        mDataRepositoryKit = dataRepositoryKit;
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

        // TODO: 2022/7/20 分享onActivityResult 回调注册 
    }

    /**
     * 取消注册
     */
    @Override
    public void unregister() {
        // TODO: 2022/7/19  取消网络请求啥的
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

}