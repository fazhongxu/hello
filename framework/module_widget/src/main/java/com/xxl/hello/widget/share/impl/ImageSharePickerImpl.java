package com.xxl.hello.widget.share.impl;

import android.app.Activity;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.xxl.core.data.model.entity.MediaEntity;
import com.xxl.core.service.download.DownloadService;
import com.xxl.hello.service.data.model.entity.share.ImageShareResourceEntity;
import com.xxl.hello.service.data.model.entity.share.ShareMediaEntity;
import com.xxl.hello.service.data.model.enums.SystemEnumsApi.ShareOperateType;
import com.xxl.hello.service.data.repository.DataRepositoryKit;
import com.xxl.hello.widget.share.api.ImageSharePicker;
import com.xxl.kit.ToastUtils;

import java.util.Arrays;
import java.util.List;

/**
 * 图片分享器实现类
 *
 * @author xxl.
 * @date 2022/7/18.
 */
public class ImageSharePickerImpl extends BaseSharePickerImpl<ImageShareResourceEntity> implements ImageSharePicker {

    //region: 构造函数

    protected ImageSharePickerImpl(@NonNull Application application,
                                   @NonNull Fragment activity,
                                   @NonNull DownloadService downloadService,
                                   @NonNull DataRepositoryKit dataRepositoryKit) {
        super(application, activity, downloadService, dataRepositoryKit);
    }

    /**
     * 构建图片分享器
     *
     * @param fragment
     * @return
     */
    public static ImageSharePickerImpl create(@NonNull final Application application,
                                              @NonNull final Fragment fragment,
                                              @NonNull final DownloadService downloadService,
                                              @NonNull DataRepositoryKit dataRepositoryKit) {
        return new ImageSharePickerImpl(application, fragment, downloadService, dataRepositoryKit);
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
    public List<Integer> getDefaultOperateTypes(@NonNull final ImageShareResourceEntity targetShareResourcesEntity) {
        return Arrays.asList(
                ShareOperateType.WE_CHAT,
                ShareOperateType.WE_CHAT_SEND_TO_FRIEND,
                ShareOperateType.WE_CHAT_CIRCLE,
                ShareOperateType.DOWNLOAD,
                ShareOperateType.MORE);
    }

    /**
     * 分享到微信点击
     *
     * @param targetShareResourcesEntity
     */
    @Override
    public void onWeChatActionClick(@NonNull ImageShareResourceEntity targetShareResourcesEntity) {
        if (isActivityFinishing()) {
            return;
        }
        // TODO: 2022/7/19
        ToastUtils.success("点击了微信分享").show();
        final List<ShareMediaEntity> shareMediaEntities = targetShareResourcesEntity.getShareMediaEntities();
    }

    /**
     * 分享到微信朋友圈点击
     *
     * @param targetShareResourcesEntity
     */
    @Override
    public void onWeChatCircleActionClick(@NonNull ImageShareResourceEntity targetShareResourcesEntity) {
        if (isActivityFinishing()) {
            return;
        }
        // TODO: 2022/7/19
        ToastUtils.success("点击了朋友圈分享").show();
    }

    //endregion

}