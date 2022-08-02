package com.xxl.hello.widget.view.share.impl;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.xxl.core.service.download.DownloadService;
import com.xxl.hello.service.data.model.entity.share.BaseShareResourceEntity;
import com.xxl.hello.service.data.model.entity.share.ImageShareResourceEntity;
import com.xxl.hello.service.data.model.entity.share.VideoShareResourceEntity;
import com.xxl.hello.service.data.model.enums.SystemEnumsApi.ShareOperateType;
import com.xxl.hello.service.data.model.enums.SystemEnumsApi.ShareResourcesType;
import com.xxl.hello.service.data.repository.DataRepositoryKit;
import com.xxl.hello.widget.view.share.OnShareItemOperate;
import com.xxl.hello.widget.view.share.api.BaseSharePicker;
import com.xxl.hello.widget.view.share.api.ImageSharePicker;
import com.xxl.hello.widget.view.share.api.ResourcesSharePickerKit;
import com.xxl.hello.widget.view.share.api.VideoSharePicker;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 资源分享器
 *
 * @author xxl.
 * @date 2022/7/18.
 */
public class ResourcesSharePickerKitImpl implements ResourcesSharePickerKit {

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
     * 分享器集合
     */
    private LinkedHashMap<Integer, BaseSharePicker> mSharePickerMap = new LinkedHashMap<>();

    /***
     * 图片分享器
     */
    private ImageSharePicker mImageSharePicker;

    /***
     * 视频分享器
     */
    private VideoSharePicker mVideoSharePicker;

    //endregion

    //region: 构造函数

    public ResourcesSharePickerKitImpl(@NonNull Application application,
                                       @NonNull DownloadService downloadService,
                                       @NonNull DataRepositoryKit dataRepositoryKit) {
        mApplication = application;
        mDownloadService = downloadService;
        mDataRepositoryKit = dataRepositoryKit;
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
        synchronized (this) {
            mFragment = fragment;
        }
    }

    /**
     * 取消注册
     */
    @Override
    public void unregister() {
        // TODO: 2022/7/19  取消网络请求,销毁分享数据，ShareUtils#release
        synchronized (this) {
            for (BaseSharePicker picker : mSharePickerMap.values()) {
                picker.unregister();
            }
            mSharePickerMap.clear();
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
                              @NonNull final BaseShareResourceEntity targetShareResourcesEntity) {
        final BaseSharePicker sharePicker = getSharePicker(targetShareResourcesEntity);
        if (sharePicker != null) {
            sharePicker.operateHandle(fragment, operateType, targetShareResourcesEntity);
        }
    }

    /**
     * 展示分享弹窗
     *
     * @param fragment                   fragment
     * @param targetShareResourcesEntity 资源分享实体
     */
    @Override
    public void showSharePicker(@NonNull Fragment fragment,
                                @NonNull final BaseShareResourceEntity targetShareResourcesEntity) {
        showSharePicker(fragment, targetShareResourcesEntity, null);
    }


    /**
     * 展示分享弹窗
     *
     * @param fragment                   fragment
     * @param targetShareResourcesEntity 资源分享实体
     * @param operate                    自定义操作事件，根据类型，return true则自己处理事件
     */
    @Override
    public void showSharePicker(@NonNull Fragment fragment,
                                @NonNull BaseShareResourceEntity targetShareResourcesEntity,
                                @Nullable OnShareItemOperate operate) {
        showSharePicker(fragment, new ArrayList<>(), targetShareResourcesEntity, operate);
    }

    /**
     * 展示分享弹窗
     *
     * @param fragment                   fragment
     * @param operateTypes               操作类型
     * @param targetShareResourcesEntity 资源分享实体
     */
    @Override
    public void showSharePicker(@NonNull Fragment fragment,
                                @NonNull final List<Integer> operateTypes,
                                @NonNull final BaseShareResourceEntity targetShareResourcesEntity) {
        showSharePicker(fragment, operateTypes, targetShareResourcesEntity, null);
    }

    /**
     * 展示分享弹窗
     *
     * @param fragment                   fragment
     * @param operateTypes               操作类型
     * @param targetShareResourcesEntity 资源分享实体
     * @param operate                    自定义操作事件
     */
    @Override
    public void showSharePicker(@NonNull Fragment fragment,
                                @NonNull List<Integer> operateTypes,
                                @NonNull BaseShareResourceEntity targetShareResourcesEntity,
                                @Nullable OnShareItemOperate operate) {
        final BaseSharePicker sharePicker = getSharePicker(targetShareResourcesEntity);
        if (sharePicker != null) {
            sharePicker.showSharePicker(fragment, operateTypes, targetShareResourcesEntity, operate);
        }
    }

    //endregion

    //region: 提供方法

    private BaseSharePicker getSharePicker(@NonNull final BaseShareResourceEntity targetShareResourcesEntity) {
        if (targetShareResourcesEntity instanceof ImageShareResourceEntity) {
            return getImageSharePicker();
        } else if (targetShareResourcesEntity instanceof VideoShareResourceEntity) {
            return getVideoSharePicker();
        }
        return null;
    }

    /**
     * 获取图片分享器
     *
     * @return
     */
    @Override
    public ImageSharePicker getImageSharePicker() {
        synchronized (this) {
            if (mImageSharePicker == null) {
                mImageSharePicker = ImageSharePickerImpl.create(mApplication, mFragment, mDownloadService, mDataRepositoryKit);
                mSharePickerMap.put(ShareResourcesType.IMAGE, mImageSharePicker);
            }
            return mImageSharePicker;
        }
    }

    /**
     * 获取视频分享器
     *
     * @return
     */
    @Override
    public VideoSharePicker getVideoSharePicker() {
        synchronized (this) {
            if (mVideoSharePicker == null) {
                mVideoSharePicker = VideoSharePickerImpl.create(mApplication,mFragment, mDownloadService, mDataRepositoryKit);
                mSharePickerMap.put(ShareResourcesType.VIDEO, mImageSharePicker);
            }
            return mVideoSharePicker;
        }
    }


    //endregion
}