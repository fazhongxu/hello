package com.xxl.core.image.selector;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.app.PictureAppMaster;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.style.PictureSelectorUIStyle;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 多媒体资源选择器
 * @Author: xxl
 * @Date: 2021/8/29 1:26 AM
 **/
public class MediaSelector {

    /**
     * 初始化图片加载库
     *
     * @param selectorApp
     */
    public static void init(@NonNull MediaSelectorApp selectorApp) {
        PictureAppMaster.getInstance().setApp(selectorApp);
    }

    /**
     * Start MediaSelector for Activity.
     *
     * @param activity
     * @return MediaSelector instance.
     */
    public static MediaSelector create(FragmentActivity activity) {
        return new MediaSelector(activity);
    }

    /**
     * Start MediaSelector for Fragment.
     *
     * @param fragment
     * @return MediaSelector instance.
     */
    public static MediaSelector create(Fragment fragment) {
        return new MediaSelector(fragment);
    }

    /**
     * Start PictureSelector for Activity.
     *
     * @param activity
     * @return PictureSelector instance.
     */
    public static PictureSelector createPictureSelector(Activity activity) {
        return PictureSelector.create(activity);
    }

    /**
     * Start PictureSelector for Fragment.
     *
     * @param fragment
     * @return PictureSelector instance.
     */
    public static PictureSelector createPictureSelector(Fragment fragment) {
        return PictureSelector.create(fragment);
    }

    /**
     * @param chooseMode Select the type of picture you want，all or Picture or Video .
     * @return LocalMedia PictureSelectionModel
     */
    public MediaSelectionModel openGallery(int chooseMode) {
        return new MediaSelectionModel(this, getFragment() != null ? createPictureSelector(getFragment()) : createPictureSelector(getActivity()), chooseMode);
    }

    /**
     * 默认样式
     *
     * @return
     */
    public PictureSelectorUIStyle ofDefaultStyle() {
        final PictureSelectorUIStyle pictureSelectorStyle = PictureSelectorUIStyle.ofDefaultStyle();
        return pictureSelectorStyle;
    }

    /**
     * isVideo
     *
     * @param mimeType
     * @return
     */
    public static boolean isVideo(String mimeType) {
        return PictureMimeType.isHasVideo(mimeType);
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
     * 判断是否是图片选择页面请求码
     *
     * @param requestCode
     * @return
     */
    public static boolean isMediaRequestCode(final int requestCode) {
        return PictureConfig.CHOOSE_REQUEST == requestCode;
    }

    private final WeakReference<FragmentActivity> mActivity;
    private final WeakReference<Fragment> mFragment;

    /**
     * @return Activity.
     */
    @Nullable
    FragmentActivity getActivity() {
        return mActivity.get();
    }

    /**
     * @return Fragment.
     */
    @Nullable
    Fragment getFragment() {
        return mFragment != null ? mFragment.get() : null;
    }

    private MediaSelector(FragmentActivity activity) {
        this(activity, null);
    }

    private MediaSelector(Fragment fragment) {
        this(fragment.getActivity(), fragment);
    }

    private MediaSelector(FragmentActivity activity, Fragment fragment) {
        mActivity = new WeakReference<>(activity);
        mFragment = new WeakReference<>(fragment);
    }

}
