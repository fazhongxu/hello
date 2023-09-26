package com.xxl.hello.widget.ui.preview.item;

import android.content.Context;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.bumptech.glide.request.target.Target;
import com.xxl.core.image.loader.ImageLoader;
import com.xxl.core.ui.fragment.BaseViewModelFragment;
import com.xxl.hello.service.data.model.entity.media.MediaPreviewItemEntity;
import com.xxl.hello.widget.BR;
import com.xxl.hello.widget.R;
import com.xxl.hello.widget.data.router.WidgetRouterApi;
import com.xxl.hello.widget.databinding.WidgetFragmentMediaPreviewItemBinding;
import com.xxl.hello.widget.ui.preview.OnMediaPreViewListener;
import com.xxl.hello.widget.ui.view.DragDismissLayout;

/**
 * 多媒体预览页面
 *
 * @author xxl.
 * @date 2023/04/06.
 */
public class MediaPreviewItemFragment extends BaseViewModelFragment<MediaPreviewItemModel, WidgetFragmentMediaPreviewItemBinding>
        implements MediaPreviewItemNavigator, DragDismissLayout.OnDragDismissLayoutListener {

    //region: 成员变量

    /**
     * 多媒体数据
     */
    private static final String ARGS_KEY_MEDIA_PREVIEW_ENTITY = "args_key_media_preview_entity";

    /**
     * 是否可拖拽
     */
    private static final String ARGS_KEY_DRAG_ENABLE = "drag_enable";

    /**
     * 多媒体预览页面视图
     */
    private WidgetFragmentMediaPreviewItemBinding mMediaPreviewBinding;

    /**
     * 多媒体预览页面视图模型
     */
    private MediaPreviewItemModel mMediaPreviewModel;

    /**
     * 是否可以分享
     */
    @Autowired(name = WidgetRouterApi.MediaPreview.PARAMS_KEY_SHARE_ENABLE)
    boolean mShareEnable = true;

    /**
     * 当前图片索引
     */
    @Autowired(name = WidgetRouterApi.MediaPreview.PARAMS_KEY_CURRENT_POSITION)
    int mCurrentPosition;

    /**
     * 多媒体数据
     */
    @Autowired(name = ARGS_KEY_MEDIA_PREVIEW_ENTITY)
    MediaPreviewItemEntity mTargetMediaPreviewItemEntity;

    /**
     * 是否可拖拽
     */
    @Autowired(name = ARGS_KEY_DRAG_ENABLE)
    boolean mDragEnable;

    /**
     * 页面事件监听
     */
    private OnMediaPreViewListener mOnMediaPreViewListener;

    //endregion

    //region: 构造函数

    public static MediaPreviewItemFragment newInstance(@Nullable final MediaPreviewItemEntity mediaPreviewItemEntity,
                                                       final boolean dragEnable) {
        final MediaPreviewItemFragment fragment = new MediaPreviewItemFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARGS_KEY_MEDIA_PREVIEW_ENTITY, mediaPreviewItemEntity);
        args.putBoolean(ARGS_KEY_DRAG_ENABLE, dragEnable);
        fragment.setArguments(args);
        return fragment;
    }
    //endregion

    //region: 页面生命周期

    @Override
    protected int getLayoutRes() {
        return R.layout.widget_fragment_media_preview_item;
    }

    @Override
    protected MediaPreviewItemModel createViewModel() {
        mMediaPreviewModel = createViewModel(MediaPreviewItemModel.class);
        mMediaPreviewModel.setNavigator(this);
        return mMediaPreviewModel;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnMediaPreViewListener) {
            mOnMediaPreViewListener = (OnMediaPreViewListener) context;
        }
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
        mMediaPreviewBinding = getViewDataBinding();
        setupLayout();
    }

    //endregion

    //region: 页面视图渲染

    private void setupLayout() {
        mMediaPreviewBinding.dragDismissLayout.bindPhotoView(mMediaPreviewBinding.ivPhoto);
        mMediaPreviewBinding.dragDismissLayout.setEnableDrag(mDragEnable);
        if (mTargetMediaPreviewItemEntity == null) {
            return;
        }

        ImageLoader.with(this)
                .load(mTargetMediaPreviewItemEntity.getMediaUrl())
                .override(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL)
                .into(mMediaPreviewBinding.ivPhoto);
        if (mTargetMediaPreviewItemEntity.hasTargetViewAttributes()) {
            mMediaPreviewBinding.dragDismissLayout.setTargetData(mTargetMediaPreviewItemEntity.getStartX(), mTargetMediaPreviewItemEntity.getStartY(), mTargetMediaPreviewItemEntity.getPreviewWidth(), mTargetMediaPreviewItemEntity.getPreviewHeight());
        }

        mMediaPreviewBinding.ivPhoto.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                mOnMediaPreViewListener.onCloseClick();
                return true;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                if (mMediaPreviewBinding.ivPhoto.getScale() > mMediaPreviewBinding.ivPhoto.getMinimumScale()) {
                    mMediaPreviewBinding.ivPhoto.setScale(mMediaPreviewBinding.ivPhoto.getMinimumScale(), true);
                } else {
                    mMediaPreviewBinding.ivPhoto.setScale(mMediaPreviewBinding.ivPhoto.getMediumScale(), true);
                }
                return true;
            }

            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                return false;
            }
        });
        mMediaPreviewBinding.dragDismissLayout.setOnDragDismissLayoutListener(this);

    }

    //endregion

    //region: MediaPreviewNavigator

    //endregion

    //region: OnDragDismissLayoutListener

    @Override
    public void onComputeAlpha(int alpha) {
        if (isActivityFinishing()) {
            return;
        }
        if (mOnMediaPreViewListener != null) {
            mOnMediaPreViewListener.onComputeAlpha(alpha);
        }
    }

    @Override
    public void onDismiss() {
        if (isActivityFinishing()) {
            return;
        }
        if (mOnMediaPreViewListener != null) {
            mOnMediaPreViewListener.onDismiss();
        }
    }

    @Override
    public void onDragging() {
        if (isActivityFinishing()) {
            return;
        }
        if (mOnMediaPreViewListener != null) {
            mOnMediaPreViewListener.onDragging();
        }
    }

    @Override
    public void onEndDrag() {
        if (isActivityFinishing()) {
            return;
        }
        if (mOnMediaPreViewListener != null) {
            mOnMediaPreViewListener.onEndDrag();
        }
    }

    //endregion

    //region: Activity 操作

    //endregion

    //region: Fragment 操作

    /**
     * 关闭页面前调整photoView 状态
     *
     * @param targetAspectRatio
     */
    public void setDismissPhotoViewState(float targetAspectRatio) {
        if (isActivityFinishing()) {
            return;
        }
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mMediaPreviewBinding.ivPhoto.getLayoutParams();
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.width = mMediaPreviewBinding.flPhotoContainer.getMeasuredWidth();
        layoutParams.height = (int) (layoutParams.width / targetAspectRatio);
        mMediaPreviewBinding.ivPhoto.setLayoutParams(layoutParams);

        mMediaPreviewBinding.ivPhoto.setScaleType(ImageView.ScaleType.CENTER_CROP);
        mMediaPreviewBinding.ivPhoto.setDisplayMatrix(new Matrix());
    }
    //endregion


}