package com.xxl.hello.user.data.model.entity;

import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * 登录用户信息
 *
 * @author xxl.
 * @date 2021/7/16.
 */
@Getter
@Accessors(prefix = "m")
public class LoginUserEntity {

    //region: 成员变量

    /**
     * 用户ID
     */
    private String mUserId;

    /**
     * 用户名
     */
    private String mUserName;

    /**
     * 年龄
     */
    private String mAge;

    /**
     * 性别
     */
    private int mSex;

    //endregion

    //region: 构造函数

    private LoginUserEntity() {

    }

    public final static LoginUserEntity obtain() {
        return new LoginUserEntity();
    }

    //endregion

    //region: 提供方法

    //endregion

    //region: 内部辅助方法

    //endregion

}