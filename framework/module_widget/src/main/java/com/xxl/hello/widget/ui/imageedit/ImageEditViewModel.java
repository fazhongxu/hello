package com.xxl.hello.widget.ui.imageedit;

import android.app.Application;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import com.xxl.core.ui.BaseViewModel;
import com.xxl.hello.service.data.repository.DataRepositoryKit;
import com.xxl.hello.widget.ui.view.ImageEditView;

/**
 * 图片编辑页面视图模型
 *
 * @author xxl
 * @date 2026/06/15
 */
public class ImageEditViewModel extends BaseViewModel<ImageEditNavigator> {

    /**
     * 当前编辑模式
     */
    public ObservableInt editMode = new ObservableInt(ImageEditView.EditMode.RECT_SELECT);

    /**
     * 画笔大小
     */
    public ObservableInt brushSize = new ObservableInt(30);

    /**
     * 是否可以撤销
     */
    public ObservableField<Boolean> canUndo = new ObservableField<>(false);

    /**
     * 是否可以重做
     */
    public ObservableField<Boolean> canRedo = new ObservableField<>(false);

    private DataRepositoryKit mDataRepositoryKit;

    private Bitmap mSourceBitmap;
    private ImageEditView mImageEditView;

    public ImageEditViewModel(@NonNull Application application,
                              @NonNull DataRepositoryKit dataRepositoryKit) {
        super(application);
        mDataRepositoryKit = dataRepositoryKit;
    }

    /**
     * 设置图片 Bitmap
     */
    public void setImageBitmap(@Nullable Bitmap bitmap) {
        mSourceBitmap = bitmap;
    }

    /**
     * 绑定 ImageEditView
     */
    public void bindImageEditView(@NonNull ImageEditView imageEditView) {
        mImageEditView = imageEditView;
        imageEditView.setOnEditListener((canUndo, canRedo) -> {
            this.canUndo.set(canUndo);
            this.canRedo.set(canRedo);
        });

        if (mSourceBitmap != null) {
            imageEditView.setImageBitmap(mSourceBitmap);
        }
    }

    /**
     * 切换到矩形圈选模式
     */
    public void setRectSelectMode() {
        editMode.set(ImageEditView.EditMode.RECT_SELECT);
        if (mImageEditView != null) {
            mImageEditView.setEditMode(ImageEditView.EditMode.RECT_SELECT);
        }
    }

    /**
     * 切换到涂抹模式
     */
    public void setBrushMode() {
        editMode.set(ImageEditView.EditMode.BRUSH);
        if (mImageEditView != null) {
            mImageEditView.setEditMode(ImageEditView.EditMode.BRUSH);
        }
    }

    /**
     * 设置画笔大小
     */
    public void setBrushSize(int size) {
        brushSize.set(size);
        if (mImageEditView != null) {
            mImageEditView.setBrushSize(size);
        }
    }

    /**
     * 撤销
     */
    public void undo() {
        if (mImageEditView != null) {
            mImageEditView.undo();
        }
    }

    /**
     * 重做
     */
    public void redo() {
        if (mImageEditView != null) {
            mImageEditView.redo();
        }
    }

    /**
     * 重画（清除所有）
     */
    public void redraw() {
        if (mImageEditView != null) {
            mImageEditView.clear();
        }
    }

    /**
     * 完成编辑
     */
    public void completeEdit() {
        if (mImageEditView != null) {
            Bitmap editedBitmap = mImageEditView.getEditedBitmap();
            if (getNavigator() != null) {
                getNavigator().onEditComplete(editedBitmap);
            }
        }
    }

    /**
     * 取消编辑
     */
    public void cancelEdit() {
        if (getNavigator() != null) {
            getNavigator().onEditCancel();
        }
    }

    /**
     * 释放资源
     */
    public void release() {
        if (mImageEditView != null) {
            mImageEditView.release();
            mImageEditView = null;
        }
        if (mSourceBitmap != null && !mSourceBitmap.isRecycled()) {
            mSourceBitmap.recycle();
            mSourceBitmap = null;
        }
    }
}
