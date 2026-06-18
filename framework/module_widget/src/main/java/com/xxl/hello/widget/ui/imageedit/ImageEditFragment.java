package com.xxl.hello.widget.ui.imageedit;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.xxl.core.ui.fragment.BaseStateViewModelFragment;
import com.xxl.hello.common.config.CacheDirConfig;
import com.xxl.hello.widget.BR;
import com.xxl.hello.widget.R;
import com.xxl.hello.widget.data.router.WidgetRouterApi;
import com.xxl.hello.widget.databinding.WidgetFragmentImageEditBinding;
import com.xxl.hello.widget.ui.view.ImageEditView;
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

    /**
     * 视图绑定
     */
    private WidgetFragmentImageEditBinding mBinding;

    /**
     * 视图模型
     */
    private ImageEditViewModel mViewModel;

    /**
     * 原始图片 Bitmap
     */
    private Bitmap mSourceBitmap;

    /**
     * 图片路径
     */
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
        mBinding.imageEditView.release();
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
        mSourceBitmap = BitmapFactory.decodeFile(mImagePath);
        if (mSourceBitmap != null) {
            mBinding.imageEditView.setImageBitmap(mSourceBitmap);
            setupImageEditView();
        }
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

    //endregion

    //region: 公共方法

    /**
     * 获取编辑后的图片
     */
    @Nullable
    public Bitmap getEditedBitmap() {
        return mBinding.imageEditView.getEditedBitmap();
    }

    //endregion

    //region: Activity 操作

    /**
     * 工具栏右侧点击
     */
    public void onToolbarRightClick() {
        Bitmap editedBitmap = getEditedBitmap();
        if (editedBitmap == null) {
            ToastUtils.error("没有可保存的图片").show();
            return;
        }
        File file = ImageUtils.save2Album(editedBitmap, CacheDirConfig.DEFAULT_ALBUM_NAME, Bitmap.CompressFormat.JPEG);
        if (file != null) {
            WidgetRouterApi.ImageEdit.setActivityResult(requireActivity(), file.getAbsolutePath());
        }
    }

    //endregion

    //region: Fragment 操作

    //endregion

}
