package com.xxl.core.image.thumb;

import android.text.TextUtils;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * 缩略图
 *
 * @author xxl.
 * @date 2023/7/24.
 */
public class ImageThumb {

    /**
     * 默认缩略图宽度
     */
    private static final int DEFAULT_THUMBNAIL_WIDTH = 180;

    /**
     * 原始路径
     */
    private final String mOriginalUrl;

    /**
     * 资源来源类型
     */
    private int mOriginalType;

    /**
     * 宽度
     */
    private int mWidth = DEFAULT_THUMBNAIL_WIDTH;

    private ImageThumb(@NonNull final String originalUrl) {
        mOriginalUrl = originalUrl;
        mOriginalType = getOriginalType(originalUrl);
    }

    public final static ImageThumb obtain(@NonNull final String originalUrl) {
        return new ImageThumb(originalUrl);
    }

    /**
     * 设置宽度
     *
     * @param width
     * @return
     */
    public ImageThumb setWidth(int width) {
        mWidth = width;
        return this;
    }

    /**
     * 获取URL
     *
     * @return
     */
    public String getUrl() {
        if (mOriginalType == OriginalType.QINIU) {
            return buildQiNiuUrl();
        }

        if (mOriginalType == OriginalType.TENCENT) {
            return buildTencentUrl();
        }

        return mOriginalUrl;
    }

    /**
     * 构建七牛URL
     *
     * @return
     */
    private String buildQiNiuUrl() {
        if (TextUtils.isEmpty(mOriginalUrl)) {
            return "";
        }
        if (!mOriginalUrl.contains("?")) {
            mOriginalUrl.concat("?");
        }
        return mOriginalUrl.concat("&from=qiniu")
                .concat("&width=" + mWidth);
    }

    /**
     * 构建七牛URL
     *
     * @return
     */
    private String buildTencentUrl() {
        if (TextUtils.isEmpty(mOriginalUrl)) {
            return "";
        }
        if (!mOriginalUrl.contains("?")) {
            mOriginalUrl.concat("?");
        }
        return mOriginalUrl.concat("&from=tencent")
                .concat("&width=" + mWidth);
    }

    /**
     * 获取资源来源
     *
     * @param originalUrl
     * @return
     */
    @OriginalType
    private int getOriginalType(@NonNull final String originalUrl) {
        if (!TextUtils.isEmpty(originalUrl)) {
            if (originalUrl.contains("aaa.com")) {
                return OriginalType.QINIU;
            }
            if (originalUrl.contains("bbb.com")) {
                return OriginalType.TENCENT;
            }
        }
        return OriginalType.UNKNOW;
    }

    /**
     * 资源来源类型
     */
    @IntDef({OriginalType.UNKNOW,
            OriginalType.QINIU,
            OriginalType.TENCENT,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface OriginalType {

        /**
         * 未知
         */
        int UNKNOW = 0;

        /**
         * 七牛
         */
        int QINIU = 1;

        /**
         * 腾讯
         */
        int TENCENT = 2;
    }

}