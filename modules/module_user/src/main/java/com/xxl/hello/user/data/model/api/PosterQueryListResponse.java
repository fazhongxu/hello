package com.xxl.hello.user.data.model.api;

import androidx.annotation.Keep;

/**
 * 海报查询列表响应数据
 *
 * @author xxl.
 * @date 2025/6/10.
 */
@Keep
public class PosterQueryListResponse {

    //region: 成员变量

    // TODO: 根据实际接口返回数据结构添加字段

    //endregion

    //region: 构造函数

    private PosterQueryListResponse() {

    }

    public final static PosterQueryListResponse obtain() {
        return new PosterQueryListResponse();
    }

    //endregion

    //region: 提供方法

    //endregion

    //region: 内部辅助方法

    //endregion
}
