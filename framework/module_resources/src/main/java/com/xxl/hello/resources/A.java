package com.xxl.hello.resources;

/**
 * @author xxl.
 * @date 2021/8/13.
 */
public class A {

    //region: 成员变量

    //endregion

    //region: 构造函数

    private A() {

    }

    public final static A obtain() {
        return new A();
    }

    //endregion

    //region: 提供方法

    //endregion

    //region: 内部辅助方法

    //endregion

}