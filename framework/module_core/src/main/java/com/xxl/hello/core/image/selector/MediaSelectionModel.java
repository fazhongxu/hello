package com.xxl.hello.core.image.selector;

import android.Manifest;

import com.luck.picture.lib.PictureSelectionModel;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.tbruyelle.rxpermissions3.RxPermissions;
import com.xxl.hello.core.R;
import com.xxl.hello.core.utils.StringUtils;
import com.xxl.hello.core.utils.ToastUtils;

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

    public void forResult() {
        this.forResult(PictureConfig.CHOOSE_REQUEST);
    }

    @Override
    public void forResult(int requestCode) {
        if (mMediaSelector.getActivity() == null) {
            super.forResult(requestCode);
            return;
        }
        final RxPermissions rxPermissions = new RxPermissions(mMediaSelector.getActivity());
        final Disposable disposable = rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA)
                .subscribe(isSuccess -> {
                    if (isSuccess) {
                        super.forResult(requestCode);
                    } else {
                        ToastUtils.show(StringUtils.getString(R.string.core_permission_read_of_white_external_storage_failure_tips));
                    }
                }, throwable -> {
                    ToastUtils.show(throwable.getMessage());
                });
    }

    //endregion
}