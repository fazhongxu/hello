package com.xxl.hello.widget.share.impl;

import android.app.Activity;
import android.app.Application;

import androidx.annotation.NonNull;

import com.xxl.core.service.download.DownloadService;
import com.xxl.hello.service.data.model.entity.share.VideoShareResoucesEntity;
import com.xxl.hello.service.data.model.enums.SystemEnumsApi.ShareOperateType;
import com.xxl.hello.service.data.repository.DataRepositoryKit;
import com.xxl.hello.widget.share.api.VideoSharePicker;

import java.util.Arrays;
import java.util.List;

/**
 * 视频分享器实现类
 *
 * @author xxl.
 * @date 2022/7/18.
 */
public class VideoSharePickerImpl extends BaseSharePickerImpl<VideoShareResoucesEntity> implements VideoSharePicker {

    //region: 构造函数

    protected VideoSharePickerImpl(@NonNull Application application,
                                   @NonNull final Activity activity,
                                   @NonNull DownloadService downloadService,
                                   @NonNull DataRepositoryKit dataRepositoryKit) {
        super(application,activity,downloadService, dataRepositoryKit);
    }

    /**
     * 构建图片分享器
     *
     * @param activity
     * @return
     */
    public static VideoSharePickerImpl create(@NonNull final Application application,
                                              @NonNull final Activity activity,
                                              @NonNull final DownloadService downloadService,
                                              @NonNull DataRepositoryKit dataRepositoryKit) {
        return new VideoSharePickerImpl(application,activity, downloadService, dataRepositoryKit);
    }

    //endregion

    //region: 页面生命周期

    /**
     * 获取默认操作类型
     *
     * @param targetShareResourcesEntity
     * @return
     */
    @Override
    public List<Integer> getDefaultOperateTypes(@NonNull final VideoShareResoucesEntity targetShareResourcesEntity) {
        return Arrays.asList(
                ShareOperateType.WE_CHAT,
                ShareOperateType.WE_CHAT_SEND_TO_FRIEND,
                ShareOperateType.WE_CHAT_CIRCLE,
                ShareOperateType.DOWNLOAD,
                ShareOperateType.MORE);
    }

    //endregion

}