package com.xxl.hello.user.data.model.api;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * 滑块验证码数据（与后续服务端下发结构保持一致）
 *
 * <p>当前为本地写死数据，接入接口后由 Gson 反序列化填充即可，无需改动视图层。
 *
 * @author xxl.
 * @date 2026/8/26.
 */
@Keep
public class SwipeCaptchaData {

    //region: 成员变量

    /**
     * 本次验证码唯一标识（校验时回传）
     */
    @SerializedName("challenge")
    private String mChallenge;

    /**
     * 背景图 base64（data:image/...;base64,xxx）
     */
    @SerializedName("bgImage")
    private String mBgImage;

    /**
     * 滑块拼图 base64（data:image/...;base64,xxx）
     */
    @SerializedName("blockImage")
    private String mBlockImage;

    /**
     * 滑块在背景图中的初始 top 偏移量（单位：px）
     */
    @SerializedName("y")
    private int mY;

    /**
     * 滑块图宽度（单位：px）
     */
    @SerializedName("blockWidth")
    private int mBlockWidth;

    /**
     * 滑块图高度（单位：px）
     */
    @SerializedName("blockHeight")
    private int mBlockHeight;

    //endregion

    //region: 构造函数

    private SwipeCaptchaData() {
    }

    public final static SwipeCaptchaData obtain() {
        return new SwipeCaptchaData();
    }

    //endregion

    //region: get or set

    public String getChallenge() {
        return mChallenge;
    }

    public String getBgImage() {
        return mBgImage;
    }

    public String getBlockImage() {
        return mBlockImage;
    }

    public int getY() {
        return mY;
    }

    public int getBlockWidth() {
        return mBlockWidth;
    }

    public int getBlockHeight() {
        return mBlockHeight;
    }

    public SwipeCaptchaData setChallenge(@NonNull final String challenge) {
        mChallenge = challenge;
        return this;
    }

    public SwipeCaptchaData setBgImage(@NonNull final String bgImage) {
        mBgImage = bgImage;
        return this;
    }

    public SwipeCaptchaData setBlockImage(@NonNull final String blockImage) {
        mBlockImage = blockImage;
        return this;
    }

    public SwipeCaptchaData setY(final int y) {
        mY = y;
        return this;
    }

    public SwipeCaptchaData setBlockWidth(final int blockWidth) {
        mBlockWidth = blockWidth;
        return this;
    }

    public SwipeCaptchaData setBlockHeight(final int blockHeight) {
        mBlockHeight = blockHeight;
        return this;
    }

    //endregion

}
