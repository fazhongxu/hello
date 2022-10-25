package com.xxl.hello.main.ui.main.adapter;

import com.xxl.hello.service.data.model.enums.SystemEnumsApi.CircleMediaType;

/**
 * 测试多条目实体信息
 *
 * @author xxl.
 * @date 2022/10/25.
 */
public class TestProviderMultiEntity {

    //region: 成员变量

    /**
     * 条目类型
     */
    @CircleMediaType
    private int mMediaType;

    //endregion

    //region: 构造函数

    private TestProviderMultiEntity() {

    }

    public final static TestProviderMultiEntity obtain() {
        return new TestProviderMultiEntity();
    }

    //endregion

    //region: 提供方法

    /**
     * 获取类型
     *
     * @return
     */
    public int getMediaType() {
        return mMediaType;
    }

    /**
     * 设置类型
     *
     * @param mediaType
     * @return
     */
    public TestProviderMultiEntity setMediaType(int mediaType) {
        this.mMediaType = mediaType;
        return this;
    }

    //endregion

    //region: 内部辅助方法

    //endregion

}