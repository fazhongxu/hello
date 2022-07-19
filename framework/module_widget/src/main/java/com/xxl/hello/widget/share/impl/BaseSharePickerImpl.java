package com.xxl.hello.widget.share.impl;

import android.app.Activity;
import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.xxl.core.service.download.DownloadService;
import com.xxl.hello.service.data.model.entity.share.BaseShareResourcesEntity;
import com.xxl.hello.service.data.model.entity.share.ShareOperateItem;
import com.xxl.hello.service.data.model.entity.share.ShareOperateItem.OnItemHandle;
import com.xxl.hello.service.data.model.enums.SystemEnumsApi.ShareOperateType;
import com.xxl.hello.service.data.repository.DataRepositoryKit;
import com.xxl.hello.widget.R;
import com.xxl.hello.widget.share.OnShareItemOperate;
import com.xxl.hello.widget.share.ResourcesShareWindow;
import com.xxl.hello.widget.share.api.BaseSharePicker;
import com.xxl.kit.ListUtils;
import com.xxl.kit.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xxl.
 * @date 2022/7/18.
 */
public abstract class BaseSharePickerImpl<T extends BaseShareResourcesEntity> implements BaseSharePicker<T> {

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
     * activity
     */
    private Activity mActivity;

    //endregion

    //region: 构造函数

    public BaseSharePickerImpl(@NonNull Application application,
                               @NonNull final Activity activity,
                               @NonNull DownloadService downloadService,
                               @NonNull DataRepositoryKit dataRepositoryKit) {
        mApplication = application;
        mDownloadService = downloadService;
        mDataRepositoryKit = dataRepositoryKit;
        register(activity);
    }

    //endregion

    //region: 页面生命周期

    /**
     * 注册
     *
     * @param activity
     */
    @Override
    public void register(@NonNull Activity activity) {
        mActivity = activity;
    }

    /**
     * 取消注册
     */
    @Override
    public void unregister() {
        // TODO: 2022/7/19  取消网络请求啥的
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
                return buildWeChatAction(this::onWeChatActionClick);
            case ShareOperateType.WE_CHAT_CIRCLE:
                return buildWeChatCircleAction(this::onWeChatCircleActionClick);
            default:
                return null;
        }
    }

    /**
     * 构建微信分享操作
     *
     * @param handle
     * @return
     */
    public ShareOperateItem buildWeChatAction(@NonNull final OnItemHandle<T> handle) {
        return ShareOperateItem.obtain()
                .setOperateType(ShareOperateType.WE_CHAT)
                .setTitle(StringUtils.getString(R.string.resources_we_chat))
                .setIcon(R.drawable.resources_ic_we_chat)
                .setOnItemHandle(handle);
    }

    /**
     * 构建微信朋友圈分享操作
     *
     * @param handle
     * @return
     */
    public ShareOperateItem buildWeChatCircleAction(@NonNull final OnItemHandle<T> handle) {
        return ShareOperateItem.obtain()
                .setOperateType(ShareOperateType.WE_CHAT_CIRCLE)
                .setTitle(StringUtils.getString(R.string.resources_friend_circle))
                .setIcon(R.drawable.resources_ic_we_chat_circle)
                .setOnItemHandle(handle);
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
     * @param activity
     * @param operateTypes
     * @param targetShareResourcesEntity
     */
    @Override
    public void showSharePicker(@NonNull Activity activity,
                                @NonNull List<Integer> operateTypes,
                                @NonNull T targetShareResourcesEntity) {
        showSharePicker(activity, operateTypes, targetShareResourcesEntity, null);
    }

    /**
     * 展示分享弹窗
     *
     * @param activity
     * @param targetShareResourcesEntity
     * @param operateTypes
     * @param operate
     */
    @Override
    public void showSharePicker(@NonNull final Activity activity,
                                @NonNull final List<Integer> operateTypes,
                                @NonNull final T targetShareResourcesEntity,
                                @Nullable final OnShareItemOperate operate) {
        if (ListUtils.isEmpty(operateTypes)) {
            showShareWindow(activity, buildOperateItems(getDefaultOperateTypes(targetShareResourcesEntity)), targetShareResourcesEntity, operate);
        } else {
            showShareWindow(activity, buildOperateItems(operateTypes), targetShareResourcesEntity, operate);
        }
    }

    /**
     * 展示分享弹窗
     *
     * @param activity
     * @param targetShareResourcesEntity
     * @param operateItems
     * @param operate
     */
    private void showShareWindow(@NonNull final Activity activity,
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
        ResourcesShareWindow.from(activity)
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

    }

    /**
     * 分享到微信朋友圈点击
     *
     * @param targetShareResourcesEntity
     */
    public void onWeChatCircleActionClick(@NonNull T targetShareResourcesEntity) {

    }

    //endregion

}