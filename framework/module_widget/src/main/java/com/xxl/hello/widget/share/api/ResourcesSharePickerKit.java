package com.xxl.hello.widget.share.api;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.xxl.hello.service.data.model.entity.share.BaseShareResourcesEntity;
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
     * @param activity
     */
    void register(@NonNull final Activity activity);

    /**
     * 取消注册
     */
    void unregister();

    //endregion

    //region: 提供方法

    /**
     * 展示分享弹窗
     *
     * @param activity                   上下文
     * @param targetShareResourcesEntity 资源分享实体
     */
    void showSharePicker(@NonNull final Activity activity,
                         @NonNull final BaseShareResourcesEntity targetShareResourcesEntity);

    /**
     * 展示分享弹窗
     *
     * @param activity                   上下文
     * @param targetShareResourcesEntity 资源分享实体
     * @param operate                    自定义操作事件
     */
    void showSharePicker(@NonNull final Activity activity,
                         @NonNull final BaseShareResourcesEntity targetShareResourcesEntity,
                         @Nullable final OnShareItemOperate operate);

    /**
     * 展示分享弹窗
     *
     * @param activity                   上下文
     * @param operateTypes               操作类型
     * @param targetShareResourcesEntity 资源分享实体
     */
    void showSharePicker(@NonNull final Activity activity,
                         @NonNull final List<Integer> operateTypes,
                         @NonNull final BaseShareResourcesEntity targetShareResourcesEntity);

    /**
     * 展示分享弹窗
     *
     * @param activity                   上下文
     * @param operateTypes               操作类型
     * @param targetShareResourcesEntity 资源分享实体
     * @param operate                    自定义操作事件
     */
    void showSharePicker(@NonNull final Activity activity,
                         @NonNull final List<Integer> operateTypes,
                         @NonNull final BaseShareResourcesEntity targetShareResourcesEntity,
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