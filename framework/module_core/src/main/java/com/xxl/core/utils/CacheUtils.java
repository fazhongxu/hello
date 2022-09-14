package com.xxl.core.utils;

import android.app.Application;

import androidx.annotation.NonNull;

import com.tencent.mmkv.MMKV;

/**
 * @author xxl.
 * @date 2021/7/20.
 */
public class CacheUtils {

    //region: 成员变量

    /**
     * 是否是debug模式
     */
    private static boolean sIsDebug;

    /**
     * 默认名称
     */
    private static final String DEFAULT_NAME = "hello";

    //endregion

    //region: 构造函数

    private CacheUtils() {

    }

    //endregion

    //region: 提供方法

    /**
     * 初始化数据存储
     *
     * @param application
     */
    public static void init(@NonNull final Application application,
                            final boolean isDebug) {
        MMKV.initialize(application);
        sIsDebug = isDebug;
    }

    /**
     * 设置是否是debug模式
     *
     * @param isDebug
     */
    public static void setIsDebug(final boolean isDebug) {
        sIsDebug = isDebug;
    }

    /**
     * 保存数据
     *
     * @param key
     * @param value
     * @return
     */
    public static boolean encode(@NonNull final String key,
                                 @NonNull final String value) {
        final MMKV mmkv = MMKV.mmkvWithID(getDefaultName());
        return mmkv.encode(key, value);
    }

    /**
     * 保存数据
     *
     * @param key
     * @param value
     * @return
     */
    public static boolean encode(@NonNull final String mmapID,
                                 @NonNull final String key,
                                 @NonNull final String value) {
        final MMKV mmkv = MMKV.mmkvWithID(mmapID);
        return mmkv.encode(key, value);
    }

    /**
     * 获取数据
     *
     * @param key
     * @return
     */
    public static String decodeString(@NonNull final String mmapID,
                                      @NonNull final String key) {
        final MMKV mmkv = MMKV.mmkvWithID(mmapID);
        return mmkv.decodeString(key);
    }

    /**
     * 保存数据
     *
     * @param key
     * @param value
     * @return
     */
    public static boolean encode(@NonNull final String mmapID,
                                 @NonNull final String key,
                                 @NonNull final long value) {
        final MMKV mmkv = MMKV.mmkvWithID(mmapID);
        return mmkv.encode(key, value);
    }

    /**
     * 获取数据
     *
     * @param key
     * @return
     */
    public static long decodeLong(@NonNull final String mmapID,
                                  @NonNull final String key) {
        final MMKV mmkv = MMKV.mmkvWithID(mmapID);
        return mmkv.decodeLong(key);
    }


    /**
     * 保存数据
     *
     * @param key
     * @param value
     * @return
     */
    public static boolean encode(@NonNull final String mmapID,
                                 @NonNull final String key,
                                 @NonNull final boolean value) {
        final MMKV mmkv = MMKV.mmkvWithID(mmapID);
        return mmkv.encode(key, value);
    }


    /**
     * 保存数据
     *
     * @param key
     * @param value
     * @return
     */
    public static boolean encode(@NonNull final String key,
                                 @NonNull final boolean value) {
        final MMKV mmkv = MMKV.mmkvWithID(getDefaultName());
        return mmkv.encode(key, value);
    }


    /**
     * 获取数据
     *
     * @param key
     * @return
     */
    public static boolean decodeBool(@NonNull final String mmapID,
                                     @NonNull final String key) {
        final MMKV mmkv = MMKV.mmkvWithID(mmapID);
        return mmkv.decodeBool(key);
    }

    /**
     * 获取数据
     *
     * @param key
     * @return
     */
    public static boolean decodeBool(@NonNull final String key) {
        final MMKV mmkv = MMKV.mmkvWithID(getDefaultName());
        return mmkv.decodeBool(key);
    }

    /**
     * 移除数据
     *
     * @param key
     */
    public static void removeValueForKey(@NonNull final String key) {
        final MMKV mmkv = MMKV.mmkvWithID(getDefaultName());
        mmkv.removeValueForKey(key);
    }

    /**
     * 移除数据
     *
     * @param key
     */
    public static void removeValueForKey(@NonNull final String mmapID,
                                         @NonNull final String key) {
        final MMKV mmkv = MMKV.mmkvWithID(mmapID);
        mmkv.removeValueForKey(key);
    }

    //endregion

    //region: 内部辅助方法

    /**
     * 获取默认名称
     *
     * @return
     */
    private static String getDefaultName() {
        if (sIsDebug) {
            return DEFAULT_NAME + "_DEBUG";
        }
        return DEFAULT_NAME;
    }

    //endregion
}