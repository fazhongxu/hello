package com.xxl.hello.core.utils;

import android.content.Context;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

/**
 * @Description 图片加载
 * @Author: xxl
 * @Date: 2021/8/29 1:06 AM
 **/
public class ImageLoader {

    public static RequestManager with(@NonNull final Context context) {
        return Glide.with(context);
    }

    private ImageLoader(){

    }
}
