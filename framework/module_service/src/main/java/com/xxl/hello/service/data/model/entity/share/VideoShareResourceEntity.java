package com.xxl.hello.service.data.model.entity.share;

/**
 * 视频分享数据
 *
 * @author xxl.
 * @date 2022/7/18.
 */
public class VideoShareResourceEntity extends BaseShareResourceEntity<VideoShareResourceEntity> {

    //region: 成员变量

    //endregion

    //region: 构造函数

    private VideoShareResourceEntity() {

    }

    public final static VideoShareResourceEntity obtain() {
        return new VideoShareResourceEntity();
    }

    //endregion

    //region: 提供方法

    //endregion

    //region: 内部辅助方法

    //endregion

}