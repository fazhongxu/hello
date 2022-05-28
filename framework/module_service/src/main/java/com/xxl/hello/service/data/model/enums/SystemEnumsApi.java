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

    //endregion

    //region: 资源上传渠道

    @IntDef({ResoucesUploadChannel.QI_NIU,
            ResoucesUploadChannel.TENCENT,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface ResoucesUploadChannel {

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
}