package com.xxl.hello.service.utils;

/**
 * // TODO: 2023/2/15 放一些实体信息在service，不能用common的工具类
 * @author xxl.
 * @date 2023/2/15.
 */
public class TestUtils {

    //region: 成员变量

    //endregion

    //region: 构造函数

    private TestUtils() {

    }

    public final static TestUtils obtain() {
        return new TestUtils();
    }

    //endregion

    //region: 提供方法

    //endregion

    //region: 内部辅助方法

    //endregion

}