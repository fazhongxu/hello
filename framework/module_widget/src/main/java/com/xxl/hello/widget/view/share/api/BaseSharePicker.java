package com.xxl.hello.widget.view.share.api;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.xxl.hello.service.data.model.entity.share.BaseShareResourceEntity;
import com.xxl.hello.service.data.model.enums.SystemEnumsApi.ShareOperateType;
import com.xxl.hello.widget.view.share.OnShareItemOperate;

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
     * @param fragment
     */
    void register(@NonNull Fragment fragment);

    /**
     * 取消注册
     */
    void unregister();

    /**
     * 操作处理（用于页面单独写，功能想用统一实现的情况）
     *
     * @param fragment                   fragment
     * @param operateType                操作类型
     * @param targetShareResourcesEntity 资源分享实体
     */
    void operateHandle(@NonNull final Fragment fragment,
                       @ShareOperateType final int operateType,
                       @NonNull final T targetShareResourcesEntity);

    /**
     * 展示分享弹窗
     *
     * @param fragment
     * @param targetShareResourcesEntity
     * @param operateTypes
     */
    void showSharePicker(@NonNull final Fragment fragment,
                         @NonNull final List<Integer> operateTypes,
                         @NonNull final T targetShareResourcesEntity);

    /**
     * 展示分享弹窗
     *
     * @param fragment
     * @param targetShareResourcesEntity
     * @param operateTypes
     * @param operate
     */
    void showSharePicker(@NonNull final Fragment fragment,
                         @NonNull final List<Integer> operateTypes,
                         @NonNull final T targetShareResourcesEntity,
                         @Nullable final OnShareItemOperate operate);

    //endregion
}