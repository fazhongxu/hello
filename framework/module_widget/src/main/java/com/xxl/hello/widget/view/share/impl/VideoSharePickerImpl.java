package com.xxl.hello.widget.view.share.impl;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.xxl.core.service.download.DownloadService;
import com.xxl.hello.service.data.model.entity.share.VideoShareResourceEntity;
import com.xxl.hello.service.data.model.enums.SystemEnumsApi.ShareOperateType;
import com.xxl.hello.service.data.repository.DataRepositoryKit;
import com.xxl.hello.widget.view.share.api.VideoSharePicker;

import java.util.Arrays;
import java.util.List;

/**
 * 视频分享器实现类
 *
 * @author xxl.
 * @date 2022/7/18.
 */
public class VideoSharePickerImpl extends BaseSharePickerImpl<VideoShareResourceEntity> implements VideoSharePicker {

    //region: 构造函数

    protected VideoSharePickerImpl(@NonNull Application application,
                                   @NonNull Fragment fragment,
                                   @NonNull DownloadService downloadService,
                                   @NonNull DataRepositoryKit dataRepositoryKit) {
        super(application, fragment, downloadService, dataRepositoryKit);
    }

    /**
     * 构建图片分享器
     *
     * @param fragment
     * @return
     */
    public static VideoSharePickerImpl create(@NonNull final Application application,
                                              @NonNull final Fragment fragment,
                                              @NonNull final DownloadService downloadService,
                                              @NonNull DataRepositoryKit dataRepositoryKit) {
        return new VideoSharePickerImpl(application, fragment, downloadService, dataRepositoryKit);
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
    public List<Integer> getDefaultOperateTypes(@NonNull final VideoShareResourceEntity targetShareResourcesEntity) {
        return Arrays.asList(
                ShareOperateType.WE_CHAT,
                ShareOperateType.WE_CHAT_SEND_TO_FRIEND,
                ShareOperateType.WE_CHAT_CIRCLE,
                ShareOperateType.DOWNLOAD,
                ShareOperateType.MORE);
    }

    //endregion

}