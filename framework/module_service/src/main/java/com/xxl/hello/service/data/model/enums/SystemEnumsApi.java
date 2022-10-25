package com.xxl.hello.service.data.model.enums;

import androidx.annotation.IntDef;
import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 系统模块枚举
 *
 * @author xxl.
 * @date 2022/05/28.
 */
public class SystemEnumsApi {

    //region: 媒体数据类型

    @StringDef({MediaType.NONE,
            MediaType.IMAGE,
            MediaType.VIDEO,
            MediaType.AUDIO,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface MediaType {

        /**
         * 未知
         */
        String NONE = "none";

        /**
         * 图片
         */
        String IMAGE = "image";

        /**
         * 视频
         */
        String VIDEO = "video";

        /**
         * 音频
         */
        String AUDIO = "audio";
    }


    @IntDef({CircleMediaType.TEXT,
            CircleMediaType.IMAGE,
            CircleMediaType.VIDEO,
            CircleMediaType.AUDIO,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface CircleMediaType {

        /**
         * 文本
         */
        int TEXT = 0;

        /**
         * 图片
         */
        int IMAGE = 1;

        /**
         * 视频
         */
        int VIDEO = 2;

        /**
         * 音频
         */
        int AUDIO = 3;
    }

    //endregion

    //region: 资源分享操作类型

    @IntDef({ShareOperateType.WE_CHAT,
            ShareOperateType.WE_CHAT_SEND_TO_FRIEND,
            ShareOperateType.WE_CHAT_CIRCLE,
            ShareOperateType.WE_CHAT_URL,
            ShareOperateType.WE_CHAT_SEND_TO_MINI_PROGRAM,
            ShareOperateType.WE_CHAT_OPEN_MINI_PROGRAM,
            ShareOperateType.WE_CHAT_COPY_MINI_PROGRAM_PATH,
            ShareOperateType.WE_CHAT_MINI_PROGRAM_QR_CODE,
            ShareOperateType.QQ,
            ShareOperateType.QQ_ZONE,
            ShareOperateType.WEI_BO,
            ShareOperateType.COPY,
            ShareOperateType.COPY_LINK,
            ShareOperateType.DOWNLOAD,
            ShareOperateType.MORE,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface ShareOperateType {

        /**
         * 微信
         */
        int WE_CHAT = 100;

        /**
         * 微信发送给好友
         */
        int WE_CHAT_SEND_TO_FRIEND = 101;

        /**
         * 微信朋友圈
         */
        int WE_CHAT_CIRCLE = 102;

        /**
         * 发送网页到微信
         */
        int WE_CHAT_URL = 103;

        /**
         * 微信发送给小程序
         */
        int WE_CHAT_SEND_TO_MINI_PROGRAM = 104;

        /**
         * 打开小程序微信
         */
        int WE_CHAT_OPEN_MINI_PROGRAM = 105;

        /**
         * 复制小程序路径
         */
        int WE_CHAT_COPY_MINI_PROGRAM_PATH = 106;

        /**
         * 小程序二维码
         */
        int WE_CHAT_MINI_PROGRAM_QR_CODE = 107;

        /**
         * 分享到QQ
         */
        int QQ = 130;

        /**
         * 分享到QQ空间
         */
        int QQ_ZONE = 131;

        /**
         * 微博
         */
        int WEI_BO = 140;

        /**
         * 复制
         */
        int COPY = 150;

        /**
         * 复制链接
         */
        int COPY_LINK = 151;

        /**
         * 下载
         */
        int DOWNLOAD = 160;

        /**
         * 更多
         */
        int MORE = 3000;
    }

    /**
     * 分享资源类型
     */
    @IntDef({ShareResourcesType.TEXT,
            ShareResourcesType.IMAGE,
            ShareResourcesType.VIDEO,
            ShareResourcesType.LINK,

    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface ShareResourcesType {

        /**
         * 文本
         */
        int TEXT = 1;

        /**
         * 图片
         */
        int IMAGE = 2;

        /**
         * 视频
         */
        int VIDEO = 3;

        /**
         * 链接
         */
        int LINK = 4;
    }

    //endregion

    //region: 资源上传渠道

    @IntDef({ResourcesUploadChannel.QI_NIU,
            ResourcesUploadChannel.TENCENT,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface ResourcesUploadChannel {

        /**
         * 七牛云
         */
        int QI_NIU = 0;

        /**
         * 腾讯云
         */
        int TENCENT = 1;
    }

    //endregion

    //region: 资源提交类型

    @StringDef({ResourcesSubmitType.MATERIAL_ADD,
            ResourcesSubmitType.MATERIAL_EDIT,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface ResourcesSubmitType {

        /**
         * 素材添加
         */
        String MATERIAL_ADD = "material_add";

        /**
         * 素材编辑
         */
        String MATERIAL_EDIT = "material_edit";
    }

    //endregion

    //region: 资源上传的状态

    @IntDef({
            ResourceUploadStatus.FAILURE,
            ResourceUploadStatus.NONE,
            ResourceUploadStatus.UPLOADING,
            ResourceUploadStatus.WAIT_SUBMIT,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface ResourceUploadStatus {

        /**
         * 上传失败
         */
        int FAILURE = -1;

        /**
         * 默认状态，未开始
         */
        int NONE = 0;

        /**
         * 正在上传中
         */
        int UPLOADING = 1;

        /**
         * 等待提交
         */
        int WAIT_SUBMIT = 2;


    }

    //endregion

    //region: 队列运行状态

    @IntDef({ServiceQueueRunningStatus.NULL,
            ServiceQueueRunningStatus.IDLE,
            ServiceQueueRunningStatus.RUNNING,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface ServiceQueueRunningStatus {

        /**
         * 空状态(队列未运行），需要重新检查设置队列状态
         */
        int NULL = -1;

        /**
         * 空闲，可直接使用
         */
        int IDLE = 0;

        /**
         * 队列运行中
         */
        int RUNNING = 1;
    }

    //endregion


}