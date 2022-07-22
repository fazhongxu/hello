package com.xxl.hello.widget.share.api;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.xxl.hello.service.data.model.entity.share.BaseShareResourceEntity;
import com.xxl.hello.service.data.model.enums.SystemEnumsApi.ShareOperateType;
import com.xxl.hello.widget.share.OnShareItemOperate;

import java.util.List;

/**
 * 资源分享器基础类
 *
 * @author xxl.
 * @date 2022/7/18.
 */
public interface BaseSharePicker<T extends BaseShareResourceEntity> {

    //region: 页面生命周期

    /**
     * 注册
     *
     * @param activity
     */
    void register(@NonNull Activity activity);

    /**
     * 取消注册
     */
    void unregister();

    /**
     * 操作处理（用于页面单独写，功能想用统一实现的情况）
     *
     * @param activity                   上下文
     * @param operateType                操作类型
     * @param targetShareResourcesEntity 资源分享实体
     */
    void operateHandle(@NonNull final Activity activity,
                       @ShareOperateType final int operateType,
                       @NonNull final T targetShareResourcesEntity);

    /**
     * 展示分享弹窗
     *
     * @param activity
     * @param targetShareResourcesEntity
     * @param operateTypes
     */
    void showSharePicker(@NonNull final Activity activity,
                         @NonNull final List<Integer> operateTypes,
                         @NonNull final T targetShareResourcesEntity);

    /**
     * 展示分享弹窗
     *
     * @param targetActivity
     * @param targetShareResourcesEntity
     * @param operateTypes
     * @param operate
     */
    void showSharePicker(@NonNull final Activity targetActivity,
                         @NonNull final List<Integer> operateTypes,
                         @NonNull final T targetShareResourcesEntity,
                         @Nullable final OnShareItemOperate operate);

    //endregion
}