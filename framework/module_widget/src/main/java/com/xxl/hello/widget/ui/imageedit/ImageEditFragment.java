package com.xxl.hello.widget.ui.imageedit;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.xxl.core.image.loader.ImageLoader;
import com.xxl.core.ui.fragment.BaseStateViewModelFragment;
import com.xxl.hello.widget.BR;
import com.xxl.hello.widget.R;
import com.xxl.hello.widget.data.router.WidgetRouterApi;
import com.xxl.hello.widget.databinding.WidgetFragmentImageEditBinding;
import com.xxl.hello.widget.ui.view.ImageEditView;
import com.xxl.kit.AppUtils;
import com.xxl.kit.ImageUtils;
import com.xxl.kit.ToastUtils;

import java.io.File;

/**
 * 图片编辑页面
 *
 * @author xxl
 * @date 2026/06/15
 */
public class ImageEditFragment extends BaseStateViewModelFragment<ImageEditViewModel, WidgetFragmentImageEditBinding>
        implements ImageEditNavigator {

    //region: 成员变量

    private WidgetFragmentImageEditBinding mBinding;
    private ImageEditViewModel mViewModel;
    private Bitmap mSourceBitmap;

    @Autowired(name = WidgetRouterApi.ImageEdit.PARAMS_KEY_IMAGE_PATH)
    String mImagePath;

    //endregion

    //region: 构造函数

    public static ImageEditFragment newInstance(@NonNull final Bundle args) {
        final ImageEditFragment fragment = new ImageEditFragment();
        fragment.setArguments(args);
        return fragment;
    }

    //endregion

    //region: 页面生命周期

    @Override
    protected int getLayoutRes() {
        return R.layout.widget_fragment_image_edit;
    }

    @Override
    protected ImageEditViewModel createViewModel() {
        mViewModel = createViewModel(ImageEditViewModel.class);
        mViewModel.setNavigator(this);
        return mViewModel;
    }

    @Override
    protected boolean enableRouterInject() {
        return true;
    }

    @Override
    public int getViewModelVariable() {
        return BR.viewModel;
    }

    @Override
    public int getViewNavigatorVariable() {
        return BR.navigator;
    }

    @Override
    protected void setupData() {

    }

    @Override
    public void setupLayout(@NonNull View view) {
        mBinding = getViewDataBinding();
        loadImage();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mBinding != null && mBinding.imageEditView != null) {
            mBinding.imageEditView.release();
        }
        if (mSourceBitmap != null && !mSourceBitmap.isRecycled()) {
            mSourceBitmap.recycle();
            mSourceBitmap = null;
        }
    }

    //endregion

    //region: 页面视图渲染

    /**
     * 更新模式按钮状态
     */
    private void updateModeButton(int mode) {
        if (mode == ImageEditView.EditMode.RECT_SELECT) {
            mBinding.btnRectSelect.setSelected(true);
            mBinding.btnBrush.setSelected(false);
        } else if (mode == ImageEditView.EditMode.BRUSH) {
            mBinding.btnRectSelect.setSelected(false);
            mBinding.btnBrush.setSelected(true);
        }
    }

    /**
     * 加载图片
     */
    private void loadImage() {
        ImageLoader.with(AppUtils.getApplication())
                .asBitmap()
                .load(mImagePath)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        mSourceBitmap = resource;
                        mBinding.imageEditView.setImageBitmap(resource);
                        setupImageEditView();
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }

    /**
     * 设置 ImageEditView
     */
    private void setupImageEditView() {
        mBinding.imageEditView.setOnEditListener((canUndo, canRedo) -> {
            mViewModel.updateEditState(canUndo, canRedo);
        });
    }

    //endregion

    //region: ImageEditNavigator

    @Override
    public void onRectSelectClick() {
        updateModeButton(ImageEditView.EditMode.RECT_SELECT);
        mViewModel.updateEditMode(ImageEditView.EditMode.RECT_SELECT);
        mBinding.imageEditView.setEditMode(ImageEditView.EditMode.RECT_SELECT);
    }

    @Override
    public void onBrushClick() {
        updateModeButton(ImageEditView.EditMode.BRUSH);
        mViewModel.updateEditMode(ImageEditView.EditMode.BRUSH);
        mBinding.imageEditView.setEditMode(ImageEditView.EditMode.BRUSH);
    }

    @Override
    public void onRedrawClick() {
        mBinding.imageEditView.clear();
    }

    @Override
    public void onUndoClick() {
        mBinding.imageEditView.undo();
    }

    @Override
    public void onRedoClick() {
        mBinding.imageEditView.redo();
    }

    @Override
    public void onEditComplete(@Nullable Bitmap editedBitmap) {
        if (editedBitmap != null) {
            ToastUtils.success("编辑完成").show();
        }
        if (getActivity() != null) {
            getActivity().setResult(android.app.Activity.RESULT_OK);
            getActivity().finish();
        }
    }

    @Override
    public void onEditCancel() {
        if (getActivity() != null) {
            getActivity().finish();
        }
    }

    //endregion

    //region: 公共方法

    /**
     * 获取编辑后的图片
     */
    @Nullable
    public Bitmap getEditedBitmap() {
        if (mBinding != null && mBinding.imageEditView != null) {
            return mBinding.imageEditView.getEditedBitmap();
        }
        return null;
    }


    //endregion

    //region: Activity 操作

    public void onToolbarRightClick() {
        Bitmap editedBitmap = getEditedBitmap();
        if (editedBitmap == null) {
            ToastUtils.error("没有可保存的图片").show();
            return;
        }

        try {
            File file = ImageUtils.save2Album(editedBitmap, Bitmap.CompressFormat.JPEG);
            if (file != null) {
                ToastUtils.success("图片已保存").show();
            } else {
                ToastUtils.error("保存失败").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.error("保存失败").show();
        }
    }

    //endregion

    //region: Fragment 操作

    //endregion

}
