package com.xxl.hello.widget.ui.imageedit;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import com.xxl.core.ui.BaseViewModel;
import com.xxl.hello.service.data.repository.DataRepositoryKit;
import com.xxl.hello.widget.ui.view.ImageEditView;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

/**
 * 图片编辑页面视图模型
 *
 * @author xxl
 * @date 2026/06/15
 */
@HiltViewModel
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

    @Inject
    public ImageEditViewModel(@NonNull Application application,
                              @NonNull DataRepositoryKit dataRepositoryKit) {
        super(application);
        mDataRepositoryKit = dataRepositoryKit;
    }

    /**
     * 更新撤销/重做状态
     */
    public void updateEditState(boolean canUndo, boolean canRedo) {
        this.canUndo.set(canUndo);
        this.canRedo.set(canRedo);
    }

    /**
     * 更新编辑模式
     */
    public void updateEditMode(int mode) {
        editMode.set(mode);
    }

    /**
     * 更新画笔大小
     */
    public void updateBrushSize(int size) {
        brushSize.set(size);
    }
}
