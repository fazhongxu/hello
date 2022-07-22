package com.xxl.hello.service.data.model.entity.share;

/**
 * 图片分享数据
 *
 * @author xxl.
 * @date 2022/7/18.
 */
public class ImageShareResourceEntity extends BaseShareResourceEntity<ImageShareResourceEntity> {

    //region: 成员变量

    //endregion

    //region: 构造函数

    private ImageShareResourceEntity() {

    }

    public final static ImageShareResourceEntity obtain() {
        return new ImageShareResourceEntity();
    }

    //endregion

    //region: 提供方法

    //endregion

    //region: 内部辅助方法

    //endregion

}