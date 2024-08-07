package com.xxl.hello.im;

/**
 * im 工具 负责初始化SDK，监听各种状态等
 *
 * @author xxl.
 * @date 2024/6/14.
 */
public class IMClientKit {

    //region: 成员变量

    //endregion

    //region: 构造函数

    private IMClientKit() {

    }

    public final static IMClientKit obtain() {
        return new IMClientKit();
    }

    //endregion

    //region: 提供方法

    //endregion

    //region: 内部辅助方法

    //endregion

}