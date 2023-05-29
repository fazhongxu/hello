package com.xxl.hello.widget.ui.preview;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.xxl.core.image.loader.ImageLoader;
import com.xxl.core.ui.fragment.BaseViewModelFragment;
import com.xxl.core.utils.SharedElementUtils;
import com.xxl.hello.service.data.model.entity.media.MediaPreviewItemEntity;
import com.xxl.hello.widget.BR;
import com.xxl.hello.widget.R;
import com.xxl.hello.widget.data.router.WidgetRouterApi;
import com.xxl.hello.widget.databinding.WidgetFragmentMediaPreviewBinding;
import com.xxl.hello.widget.ui.view.DragDismissLayout;
import com.xxl.kit.ListUtils;

import java.util.List;

/**
 * 多媒体预览页面
 *
 * @author xxl.
 * @date 2023/04/06.
 */
public class MediaPreviewFragment extends BaseViewModelFragment<MediaPreviewModel, WidgetFragmentMediaPreviewBinding>
        implements MediaPreviewNavigator {

    //region: 成员变量

    /**
     * 多媒体预览页面视图
     */
    private WidgetFragmentMediaPreviewBinding mMediaPreviewBinding;

    /**
     * 多媒体预览页面视图模型
     */
    private MediaPreviewModel mMediaPreviewModel;

    private ColorDrawable mColorDrawable;

    /**
     * 是否可以分享
     */
    @Autowired(name = WidgetRouterApi.MediaPreview.PARAMS_KEY_SHARE_ENABLE)
    boolean mShareEnable = true;

    /**
     * 多媒体数据
     */
    private List<MediaPreviewItemEntity> mMediaPreviewItemEntities;

    //endregion

    //region: 构造函数

    public static MediaPreviewFragment newInstance(@NonNull final Bundle args) {
        final MediaPreviewFragment fragment = new MediaPreviewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    //endregion

    //region: 页面生命周期

    @Override
    protected int getLayoutRes() {
        return R.layout.widget_fragment_media_preview;
    }

    @Override
    protected MediaPreviewModel createViewModel() {
        mMediaPreviewModel = createViewModel(MediaPreviewModel.class);
        mMediaPreviewModel.setNavigator(this);
        return mMediaPreviewModel;
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
        mMediaPreviewItemEntities = WidgetRouterApi.MediaPreview.getMediaPreviewItemEntities();
        if (ListUtils.isEmpty(mMediaPreviewItemEntities)) {
            finishActivity();
            return;
        }
    }

    @Override
    public void setupLayout(@NonNull View view) {
        mMediaPreviewBinding = getViewDataBinding();
        setupLayout();
    }

    //endregion

    //region: 页面视图渲染

    private void setupLayout() {
        mColorDrawable = new ColorDrawable();
        mColorDrawable.setColor(Color.BLACK);
        mMediaPreviewBinding.flRootContainer.setBackground(mColorDrawable);
        mMediaPreviewBinding.dragDismissLayout.setDefaultColorDrawable();

        final MediaPreviewItemEntity mediaPreviewItemEntity = ListUtils.getFirst(mMediaPreviewItemEntities);
        if (mediaPreviewItemEntity != null) {
            ImageLoader.with(this)
                    .load(mediaPreviewItemEntity.getMediaUrl())
                    .into(mMediaPreviewBinding.ivPhoto);
        }
        mMediaPreviewBinding.ivPhoto.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                finishActivity();
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

        mMediaPreviewBinding.dragDismissLayout.setOnDragListener(new DragDismissLayout.OnDragListener() {

            @Override
            public void onDragStart() {
                SharedElementUtils.onMediaPreviewSharedElementDragStart();
            }

            @Override
            public void onDragging(int alpha) {
                mColorDrawable.setAlpha(alpha);
            }

            @Override
            public void onDragEnd() {
                finishActivity();
            }
        });
    }

    //endregion

    //region: MediaPreviewNavigator

    //endregion

    //region: Activity 操作

    /**
     * 返回按键处理
     *
     * @return
     */
    public boolean onBackPressed() {
        ActivityCompat.finishAfterTransition(getActivity());
        return true;
    }

    @Override
    public void finishActivity() {
        if (isActivityFinishing()) {
            return;
        }
        ActivityCompat.finishAfterTransition(getActivity());
    }

    //endregion

}