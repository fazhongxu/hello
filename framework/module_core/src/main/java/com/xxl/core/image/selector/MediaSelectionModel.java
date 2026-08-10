package com.xxl.core.image.selector;

import android.Manifest;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.luck.picture.lib.PictureSelectionModel;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.UCropOptions;
import com.luck.picture.lib.language.LanguageConfig;
import com.xxl.core.R;
import com.xxl.core.permission.PermissionHelper;
import com.xxl.kit.LanguageUtils;

import java.util.Locale;

/**
 * @author xxl.
 * @date 2021/11/25.
 */
public class MediaSelectionModel extends PictureSelectionModel {

    /**
     * 相册选择所需权限
     */
    private static final String[] REQUIRED_PERMISSIONS = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    //region: 成员变量

    private MediaSelector mMediaSelector;

    //endregion

    //region: 构造函数

    public MediaSelectionModel(PictureSelector selector, int chooseMode) {
        super(selector, chooseMode);
    }

    public MediaSelectionModel(PictureSelector selector, int chooseMode, boolean camera) {
        super(selector, chooseMode, camera);
    }

    public MediaSelectionModel(MediaSelector mediaSelector, PictureSelector pictureSelector, int chooseMode) {
        super(pictureSelector, chooseMode);
        mMediaSelector = mediaSelector;
        Locale appliedLanguage = LanguageUtils.getAppliedLanguage();
        if (appliedLanguage != null && "en".equals(appliedLanguage.getLanguage())) {
            setLanguage(LanguageConfig.ENGLISH);
        } else {
            setLanguage(LanguageConfig.CHINESE);
        }
    }

    @Override
    public MediaSelectionModel isCompress(boolean isCompress) {
        super.isCompress(isCompress);
        return this;
    }

    @Override
    public MediaSelectionModel maxSelectNum(int maxSelectNum) {
        super.maxSelectNum(maxSelectNum);
        return this;
    }

    @Override
    public MediaSelectionModel isEnableCrop(boolean enableCrop) {
        super.isEnableCrop(enableCrop);
        return this;
    }

    @Override
    public MediaSelectionModel withAspectRatio(int aspect_ratio_x, int aspect_ratio_y) {
        super.withAspectRatio(aspect_ratio_x, aspect_ratio_y);
        return this;
    }

    @Override
    public MediaSelectionModel freeStyleCropEnabled(boolean freeStyleCropEnabled) {
        super.freeStyleCropEnabled(freeStyleCropEnabled);
        return this;
    }

    @Override
    public MediaSelectionModel basicUCropConfig(UCropOptions uCropOptions) {
        super.basicUCropConfig(uCropOptions);
        return this;
    }

    public void forResult() {
        this.forResult(PictureConfig.CHOOSE_REQUEST);
    }

    @Override
    public void forResult(int requestCode) {
        final FragmentActivity activity = mMediaSelector.getActivity();
        final Fragment fragment = mMediaSelector.getFragment();
        if (activity == null && fragment == null) {
            super.forResult(requestCode);
            return;
        }
        final PermissionHelper helper = fragment != null
                ? PermissionHelper.from(fragment)
                : PermissionHelper.from(activity);
        helper.instruction(R.string.core_permission_photo_video_usage_instruction_title,
                        R.string.core_permission_photo_video_usage_instruction)
                .goToSettingsOnDenied(true)
                .request(REQUIRED_PERMISSIONS, new PermissionHelper.Callback() {
                    @Override
                    public void onGranted() {
                        MediaSelectionModel.super.forResult(requestCode);
                    }

                    @Override
                    public void onDenied() {

                    }
                });
    }

    //endregion
}
