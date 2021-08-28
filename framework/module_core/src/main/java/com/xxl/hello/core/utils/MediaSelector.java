package com.xxl.hello.core.utils;

import android.app.Activity;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 多媒体资源选择器
 * @Author: xxl
 * @Date: 2021/8/29 1:26 AM
 **/
public class MediaSelector {

    /**
     * Start PictureSelector for Activity.
     *
     * @param activity
     * @return PictureSelector instance.
     */
    public static PictureSelector create(Activity activity) {
        return PictureSelector.create(activity);
    }

    /**
     * Start PictureSelector for Fragment.
     *
     * @param fragment
     * @return PictureSelector instance.
     */
    public static PictureSelector create(Fragment fragment) {
        return PictureSelector.create(fragment);
    }

    /**
     * @param data
     * @return Selector Multiple LocalMedia
     */
    public static List<LocalMedia> obtainMultipleResult(Intent data) {
        if (data != null) {
            List<LocalMedia> result = data.getParcelableArrayListExtra(PictureConfig.EXTRA_RESULT_SELECTION);
            return result == null ? new ArrayList<>() : result;
        }
        return new ArrayList<>();
    }

    /**
     * 判断事发是图片选择页面请求码
     * @param requestCode
     * @return
     */
    public static boolean isPictureRequestCode(final int requestCode) {
        return PictureConfig.CHOOSE_REQUEST == requestCode;
    }

    private MediaSelector() {

    }
}
