package com.xxl.core.utils;

import android.text.TextUtils;

import com.tencent.vasdolly.helper.ChannelReaderUtil;
import com.xxl.kit.AppUtils;

/**
 * 渠道工具类
 *
 * @author xxl.
 * @date 2024/9/18.
 */
public class ChannelUtils {

    /**
     * 获取渠道
     *
     * @return
     */
    public static String getChannel() {
        return getChannel("common");
    }

    /**
     * 获取渠道
     *
     * @param defaultChannel 默认渠道
     * @return
     */
    public static String getChannel(String defaultChannel) {
        String channel = ChannelReaderUtil.getChannel(AppUtils.getApplication());
        if (TextUtils.isEmpty(channel)) {
            return defaultChannel;
        }
        return channel;
    }

    private ChannelUtils() {

    }

}