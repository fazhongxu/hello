package com.xxl.hello.service.data.model.entity.user;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import com.xxl.hello.common.config.NetworkConfig;
import com.xxl.hello.service.data.model.enums.UserEnumsApi.UserSex;
import com.xxl.kit.TimeUtils;

import java.io.Serializable;
import java.util.Random;

/**
 * 登录用户信息
 *
 * @author xxl.
 * @date 2021/7/16.
 */
@Keep
public class LoginUserEntity implements Serializable {

    //region: 成员变量

    /**
     * 用户ID
     */
    private String mUserId;

    /**
     * 用户昵称
     */
    private String mNickname;

    /**
     * 用户头像
     */
    private String mAvatar;

    /**
     * token
     */
    private String mAccessToken;

    /**
     * 年龄
     */
    private int mAge;

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

    /**
     * 当前用户是否是VIP
     *
     * @return
     */
    public boolean isVip() {
        return new Random().nextInt(2) == 1;
    }

    public String getUserId() {
        return mUserId;
    }

    public String getUserName() {
        return mNickname;
    }

    public String getAvatar() {
        return mAvatar;
    }

    public String getAccessToken() {
        return mAccessToken;
    }

    /**
     * 设置用户ID
     *
     * @param userId
     * @return
     */
    public LoginUserEntity setUserId(@NonNull final String userId) {
        mUserId = userId;
        return this;
    }

    /**
     * 设置用户名称
     *
     * @param nickname
     * @return
     */
    public LoginUserEntity setNickname(@NonNull final String nickname) {
        mNickname = nickname;
        return this;
    }

    /**
     * 设置用户头像
     *
     * @param avatar
     * @return
     */
    public LoginUserEntity setAvatar(@NonNull final String avatar) {
        mAvatar = avatar;
        return this;
    }

    /**
     * 设置年龄
     *
     * @param age
     * @return
     */
    public LoginUserEntity setAge(final int age) {
        this.mAge = age;
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
        if (NetworkConfig.Companion.isNetworkDebug()) {
            return LoginUserEntity.obtain()
                    .setUserId(String.valueOf(TimeUtils.currentServiceTimeMillis()))
                    .setNickname("six six");
        }
        throw new RuntimeException("仅测试环境可用");
    }

    //endregion

    //region: 内部辅助方法

    //endregion

}