package com.xxl.hello.core.utils;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

/**
 * @Description 图片加载
 * @Author: xxl
 * @Date: 2021/8/29 1:06 AM
 **/
public class ImageLoader {

    public static RequestManager with(@NonNull final Activity activity) {
        return Glide.with(activity);
    }

    private ImageLoader(){

    }
}
