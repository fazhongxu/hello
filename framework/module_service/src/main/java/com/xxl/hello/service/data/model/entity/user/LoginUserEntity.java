package com.xxl.hello.service.data.model.entity.user;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import com.xxl.core.utils.TestUtils;
import com.xxl.hello.common.NetworkConfig;
import com.xxl.hello.service.data.model.enums.UserEnumsApi.UserSex;

/**
 * 登录用户信息
 *
 * @author xxl.
 * @date 2021/7/16.
 */
@Keep
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
     * 用户头像
     */
    private String mAvatar;

    /**
     * 年龄
     */
    private String mAge;

    /**
     * 性别
     */
    @UserSex
    private int mSex;

    //endregion

    //region: 构造函数

    private LoginUserEntity() {

    }

    public final static LoginUserEntity obtain() {
        return new LoginUserEntity();
    }

    //endregion

    //region: get or set

    /**
     * 判断性别是否是男
     *
     * @return
     */
    public boolean isMale() {
        return mSex == UserSex.MALE;
    }

    public String getUserId(){
        return mUserId;
    }

    public String getUserName() {
        return mUserName;
    }

    public String getAvatar() {
        return mAvatar;
    }

    /**
     * 设置用户ID
     *
     * @param targetUserId
     * @return
     */
    public LoginUserEntity setUserId(@NonNull final String targetUserId) {
        this.mUserId = targetUserId;
        return this;
    }

    /**
     * 设置用户名称
     *
     * @param targetUserName
     * @return
     */
    public LoginUserEntity setUserName(@NonNull final String targetUserName) {
        this.mUserName = targetUserName;
        return this;
    }

    /**
     * 设置用户头像
     *
     * @param targetUserAvatar
     * @return
     */
    public LoginUserEntity setUserAvatar(@NonNull final String targetUserAvatar) {
        this.mAvatar = targetUserAvatar;
        return this;
    }

    //endregion

    //region: 测试数据

    /**
     * 构建测试用户信息
     * 仅测试环境有效
     *
     * @return
     */
    public static final LoginUserEntity obtainTestUserEntity() {
        if (NetworkConfig.isNetworkDebug()) {
            return LoginUserEntity.obtain()
                    .setUserId(String.valueOf(TestUtils.getRandom()))
                    .setUserName("six six");
        }
        throw new RuntimeException("仅测试环境可用");
    }

    //endregion

    //region: 内部辅助方法

    //endregion

}