package com.xxl.hello.widget.data.router;

import android.Manifest;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tbruyelle.rxpermissions3.RxPermissions;
import com.xxl.hello.service.data.model.entity.media.MediaPreviewItemEntity;
import com.xxl.hello.widget.R;
import com.xxl.kit.ListUtils;
import com.xxl.kit.RouterUtils;
import com.xxl.kit.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.disposables.Disposable;

/**
 * 系统模块路由
 *
 * @author xxl.
 * @date 2022/6/23.
 */
public final class WidgetRouterApi {

    /**
     * 自定义组件模块名称
     */
    public static final String WIDGET_MODULE_NAME = "/widget_module_name";

    private WidgetRouterApi() {

    }

    //region: 多媒体预览路由相关

    public static class MediaPreview {

        /**
         * 多媒体预览页面路径地址
         */
        public static final String PATH = WIDGET_MODULE_NAME + "/media_preview";

        /**
         * 是否可以分享
         */
        public static final String PARAMS_KEY_SHARE_ENABLE = "params_key_share_enable";

        /**
         * 当前位置索引
         */
        public static final String PARAMS_KEY_CURRENT_POSITION = "params_key_current_position";

        /**
         * 多媒体数据
         */
        private static final List<MediaPreviewItemEntity> sMediaPreviewItemEntities = new ArrayList<>();

        /**
         * 获取多媒体预览条目信息
         *
         * @return
         */
        public static List<MediaPreviewItemEntity> getMediaPreviewItemEntities() {
            final List<MediaPreviewItemEntity> mediaPreviewItemEntities = new ArrayList<>();
            if (!ListUtils.isEmpty(sMediaPreviewItemEntities)) {
                mediaPreviewItemEntities.addAll(sMediaPreviewItemEntities);
                sMediaPreviewItemEntities.clear();
            }
            return mediaPreviewItemEntities;
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public static class Builder {

            private Bundle mParams = new Bundle();

            public Builder() {

            }


            /**
             * 设置多媒体预览条目数据
             *
             * @param mediaPreviewItemEntity
             * @return
             */
            public Builder setMediaPreviewItemEntity(@Nullable final MediaPreviewItemEntity mediaPreviewItemEntity) {
                final List<MediaPreviewItemEntity> mediaPreviewItemEntities = new ArrayList<>();
                if (mediaPreviewItemEntity != null) {
                    mediaPreviewItemEntities.add(mediaPreviewItemEntity);
                }
                setMediaPreviewItemEntities(mediaPreviewItemEntities);
                return this;
            }

            /**
             * 设置多媒体预览条目数据
             *
             * @param mediaPreviewItemEntities
             * @return
             */
            public Builder setMediaPreviewItemEntities(@NonNull final List<MediaPreviewItemEntity> mediaPreviewItemEntities) {
                if (!ListUtils.isEmpty(mediaPreviewItemEntities)) {
                    sMediaPreviewItemEntities.clear();
                    sMediaPreviewItemEntities.addAll(mediaPreviewItemEntities);
                }
                return this;
            }

            /**
             * 设置当前索引
             *
             * @param position
             * @return
             */
            public Builder setCurrentPosition(final int position) {
                mParams.putInt(PARAMS_KEY_CURRENT_POSITION, position);
                return this;
            }

            /**
             * 跳转到多媒体预览
             */
            public void navigation() {
                RouterUtils.navigation(PATH, mParams);
            }
        }
    }

    //endregion

    //region: 文件浏览路由相关

    public static class FileBrowser {

        /**
         * 多媒体预览页面路径地址
         */
        public static final String PATH = WIDGET_MODULE_NAME + "/file_browser";

        /**
         * 文件路径
         */
        public static final String PARAMS_KEY_FILE_PATH = "params_key_file_path";

        /**
         * 是否可以分享
         */
        public static final String PARAMS_KEY_SHARE_ENABLE = "params_key_share_enable";


        public static Builder newBuilder() {
            return new Builder();
        }

        public static class Builder {

            private Bundle mParams = new Bundle();

            public Builder() {

            }

            /**
             * 设置文件路径
             *
             * @param filePath
             * @return
             */
            public Builder setFilePath(@NonNull final String filePath) {
                mParams.putString(PARAMS_KEY_FILE_PATH, filePath);
                return this;
            }

            /**
             * 设置是否可分享
             *
             * @param shareEnable
             * @return
             */
            public Builder setShareEnable(final boolean shareEnable) {
                mParams.putBoolean(PARAMS_KEY_SHARE_ENABLE, shareEnable);
                return this;
            }

            /**
             * 跳转到多媒体预览
             */
            public void navigation() {
                RouterUtils.navigation(PATH, mParams);
            }
        }
    }

    //endregion

    //region: 二维码路由相关

    public static class QRCode {

        /**
         * 二维码页面路径地址
         */
        public static final String PATH = WIDGET_MODULE_NAME + "/qrcode";

        public static Builder newBuilder() {
            return new Builder();
        }

        public static class Builder {

            private Bundle mParams = new Bundle();

            public Builder() {

            }

            /**
             * 跳转到多媒体预览
             */
            public void navigation(@NonNull final Fragment fragment) {
                final RxPermissions rxPermissions = new RxPermissions(fragment);
                final Disposable disposable = rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)
                        .subscribe(isSuccess -> {
                            if (isSuccess) {
                                RouterUtils.navigation(PATH, mParams);
                            } else {
                                ToastUtils.error(R.string.core_permission_read_of_white_external_storage_failure_tips).show();
                            }
                        }, throwable -> {
                            ToastUtils.error(R.string.core_permission_read_of_white_external_storage_failure_tips).show();
                        });
            }
        }
    }

    //endregion
}