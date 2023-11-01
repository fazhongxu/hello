package com.xxl.core.image.selector;

import android.Manifest;

import com.luck.picture.lib.PictureSelectionModel;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.UCropOptions;
import com.tbruyelle.rxpermissions3.RxPermissions;
import com.xxl.core.R;
import com.xxl.kit.ToastUtils;

import io.reactivex.rxjava3.disposables.Disposable;

/**
 * @author xxl.
 * @date 2021/11/25.
 */
public class MediaSelectionModel extends PictureSelectionModel {

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
        if (mMediaSelector.getActivity() == null && mMediaSelector.getFragment() == null) {
            super.forResult(requestCode);
            return;
        }
        RxPermissions rxPermissions;
        if (mMediaSelector.getFragment() != null) {
            rxPermissions = new RxPermissions(mMediaSelector.getFragment());
        } else {
            rxPermissions = new RxPermissions(mMediaSelector.getActivity());
        }
        final Disposable disposable = rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA)
                .subscribe(isSuccess -> {
                    if (isSuccess) {
                        super.forResult(requestCode);
                    } else {
                        ToastUtils.error(R.string.core_permission_read_of_white_external_storage_failure_tips).show();
                    }
                }, throwable -> {
                    ToastUtils.error(throwable.getMessage()).show();
                });
    }

    //endregion
}