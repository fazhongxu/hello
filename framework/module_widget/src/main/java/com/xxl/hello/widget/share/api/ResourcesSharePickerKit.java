package com.xxl.hello.widget.share.api;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.xxl.hello.service.data.model.entity.share.BaseShareResourceEntity;
import com.xxl.hello.service.data.model.enums.SystemEnumsApi.ShareOperateType;
import com.xxl.hello.widget.share.OnShareItemOperate;

import java.util.List;

/**
 * 资源分享器
 *
 * @author xxl.
 * @date 2022/7/18.
 */
public interface ResourcesSharePickerKit {

    //region: 页面生命周期

    /**
     * 注册
     *
     * @param fragment
     */
    void register(@NonNull final Fragment fragment);

    /**
     * 取消注册
     */
    void unregister();

    //endregion

    //region: 提供方法

    /**
     * 操作处理（用于页面单独写，功能想用统一实现的情况）
     *
     * @param fragment                   fragment
     * @param operateType                操作类型
     * @param targetShareResourcesEntity 资源分享实体
     */
    void operateHandle(@NonNull final Fragment fragment,
                       @ShareOperateType final int operateType,
                       @NonNull final BaseShareResourceEntity targetShareResourcesEntity);

    /**
     * 展示分享弹窗
     *
     * @param fragment                fragment
     * @param targetShareResourcesEntity 资源分享实体
     */
    void showSharePicker(@NonNull final Fragment fragment,
                         @NonNull final BaseShareResourceEntity targetShareResourcesEntity);

    /**
     * 展示分享弹窗
     *
     * @param fragment                fragment
     * @param targetShareResourcesEntity 资源分享实体
     * @param operate                    自定义操作事件
     */
    void showSharePicker(@NonNull final Fragment fragment,
                         @NonNull final BaseShareResourceEntity targetShareResourcesEntity,
                         @Nullable final OnShareItemOperate operate);

    /**
     * 展示分享弹窗
     *
     * @param fragment                fragment
     * @param operateTypes               操作类型
     * @param targetShareResourcesEntity 资源分享实体
     */
    void showSharePicker(@NonNull final Fragment fragment,
                         @NonNull final List<Integer> operateTypes,
                         @NonNull final BaseShareResourceEntity targetShareResourcesEntity);

    /**
     * 展示分享弹窗
     *
     * @param fragment                fragment
     * @param operateTypes               操作类型
     * @param targetShareResourcesEntity 资源分享实体
     * @param operate                    自定义操作事件
     */
    void showSharePicker(@NonNull final Fragment fragment,
                         @NonNull final List<Integer> operateTypes,
                         @NonNull final BaseShareResourceEntity targetShareResourcesEntity,
                         @Nullable final OnShareItemOperate operate);

    /**
     * 获取图片分享器
     *
     * @return
     */
    ImageSharePicker getImageSharePicker();

    /**
     * 获取视频分享器
     *
     * @return
     */
    VideoSharePicker getVideoSharePicker();

    //endregion


}