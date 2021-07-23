package com.xxl.hello.common.utils;

import android.app.Application;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

/**
 * @author xxl.
 * @date 2021/7/20.
 */
public class GsonUtils {

    //region: 成员变量

    private static final Gson GSON = new Gson();

    //endregion

    //region: 构造函数

    private GsonUtils() {

    }

    //endregion

    //region: 提供方法

    /**
     * 对象转换为Json
     *
     * @param object
     * @return
     */
    public static String toJson(@NonNull final Object object) {
        try {
            return GSON.toJson(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Json转换为对象
     *
     * @param clazz
     * @return
     */
    public static <T> T fromJson(@NonNull final String json,
                                 @NonNull final Class<T> clazz) {
        try {
            return GSON.fromJson(json, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //endregion

}