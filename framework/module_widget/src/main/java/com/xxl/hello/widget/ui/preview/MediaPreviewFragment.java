package com.xxl.hello.widget.ui.preview;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.github.florent37.viewanimator.ViewAnimator;
import com.xxl.core.image.loader.ImageLoader;
import com.xxl.core.ui.fragment.BaseViewModelFragment;
import com.xxl.hello.service.data.model.entity.media.MediaPreviewItemEntity;
import com.xxl.hello.widget.BR;
import com.xxl.hello.widget.R;
import com.xxl.hello.widget.data.router.WidgetRouterApi;
import com.xxl.hello.widget.databinding.WidgetFragmentMediaPreviewBinding;
import com.xxl.hello.widget.ui.view.DragDismissLayout;
import com.xxl.kit.DisplayUtils;
import com.xxl.kit.ImageUtils;
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
     * 动画时长
     */
    private static final int ANIMATOR_DURATION = 300;

    /**
     * 多媒体预览页面视图
     */
    private WidgetFragmentMediaPreviewBinding mMediaPreviewBinding;

    /**
     * 多媒体预览页面视图模型
     */
    private MediaPreviewModel mMediaPreviewModel;

    /**
     * 背景
     */
    private ColorDrawable mColorDrawable;

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
        mMediaPreviewBinding.dragDismissLayout.bindPhotoView(mMediaPreviewBinding.ivPhoto);

        MediaPreviewItemEntity mediaPreviewItemEntity = getCurrentItem(mCurrentPosition);
        if (mediaPreviewItemEntity != null) {
            ImageLoader.with(this)
                    .load(mediaPreviewItemEntity.getMediaUrl())
                    .into(mMediaPreviewBinding.ivPhoto);
            if (mediaPreviewItemEntity.hasTargetViewAttributes()) {
                mMediaPreviewBinding.dragDismissLayout.setTargetData(mediaPreviewItemEntity.getStartX(), mediaPreviewItemEntity.getStartY(), mediaPreviewItemEntity.getPreviewWidth(), mediaPreviewItemEntity.getPreviewHeight());
            }
        }
        mMediaPreviewBinding.ivPhoto.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                onExitAnim();
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
        mMediaPreviewBinding.dragDismissLayout.setOnDragDismissLayoutListener(new DragDismissLayout.OnDragDismissLayoutListener() {

            @Override
            public void onComputeAlpha(int alpha) {
                mColorDrawable.setAlpha(alpha);
            }

            @Override
            public void onDismiss() {

            }

            @Override
            public void onDragging() {

            }

            @Override
            public void onEndDrag() {

            }
        });

        onEnterAnimator();
    }

    /**
     * 进入动画效果
     * https://github.com/VicJcc/BrowseImg/blob/c42239826cc98e50bcf75b88b76cb5df103a9ff7/app/src/main/java/jcc/example/com/browseimg/JBrowseImgActivity.java#L196
     */
    private void onEnterAnimator() {
        synchronized (this) {
            MediaPreviewItemEntity mediaPreviewItemEntity = getCurrentItem(mCurrentPosition);
            if (mediaPreviewItemEntity == null || mediaPreviewItemEntity.getPreviewWidth() < 0 || mediaPreviewItemEntity.getPreviewHeight() < 0) {
                ViewAnimator.animate(mMediaPreviewBinding.ivPhoto)
                        .zoomIn()
                        .duration(ANIMATOR_DURATION)
                        .custom((view, value) -> setColorDrawableAlpha(value), 0, 1.f)
                        .start();
                return;
            }
            int targetViewWidth = mediaPreviewItemEntity.getPreviewWidth();
            int targetViewHeight = mediaPreviewItemEntity.getPreviewHeight();

            int targetViewX = mediaPreviewItemEntity.getStartX();
            int targetViewY = mediaPreviewItemEntity.getStartY();

            float pivotX;
            float pivotY;
            float animStartHeight;
            float animStartWidth;

            float windowAspectRatio = DisplayUtils.getScreenAspectRatio(getContext());
            float targetAspectRatio = ImageUtils.getImageAspectRatio(targetViewWidth, targetViewHeight);

            float aspectRatio;
            if (targetAspectRatio >= windowAspectRatio) {
                aspectRatio = targetViewWidth * 1.0f / DisplayUtils.getScreenWidth(getContext());
                animStartHeight = DisplayUtils.getScreenHeight(getActivity()) * aspectRatio;
                pivotX = targetViewX / (1 - aspectRatio);
                pivotY = (targetViewY - (animStartHeight - targetViewHeight) / 2) / (1 - aspectRatio);
            } else {
                aspectRatio = targetViewHeight * 1.0f / DisplayUtils.getScreenHeight(getContext());
                animStartWidth = DisplayUtils.getScreenWidth(getContext()) * aspectRatio;
                pivotX = (targetViewX - (animStartWidth - targetViewWidth) / 2) / (1 - aspectRatio);
                pivotY = targetViewY / (1 - aspectRatio);
            }

            ViewAnimator.animate(mMediaPreviewBinding.ivPhoto)
                    .pivotX(pivotX)
                    .pivotY(pivotY)
                    .scale(aspectRatio, 1.0f)
                    .fadeIn()
                    .duration(ANIMATOR_DURATION)
                    .custom((view, value) -> setColorDrawableAlpha(value), 0, 1.f)
                    .start();
        }
    }

    /**
     * 退出activity动画
     */
    private void onExitAnim() {
        // TODO: 2023/5/31 索引获取当前图片，这里没做ViewPager，模拟滑动过位置了
        mCurrentPosition = 0;
        if (ListUtils.getSize(mMediaPreviewItemEntities) > 1) {
            mCurrentPosition = 1;
        }
        MediaPreviewItemEntity mediaPreviewItemEntity = getCurrentItem(mCurrentPosition);
        if (mediaPreviewItemEntity == null) {
            finishActivity();
            getActivity().overridePendingTransition(0, 0);
            return;
        }

        int targetViewWidth = mediaPreviewItemEntity.getPreviewWidth();
        int targetViewHeight = mediaPreviewItemEntity.getPreviewHeight();

        int targetViewStatX = mediaPreviewItemEntity.getStartX();
        int targetViewStartY = mediaPreviewItemEntity.getStartY();

        float pivotX;
        float pivotY;
        float animStartHeight;
        float animStartWidth;

        float windowAspectRatio = DisplayUtils.getScreenAspectRatio(getContext());
        float targetAspectRatio = ImageUtils.getImageAspectRatio(targetViewWidth, targetViewHeight);

        float aspectRatio;
        if (targetAspectRatio >= windowAspectRatio) {
            aspectRatio = targetViewWidth * 1.0f / DisplayUtils.getScreenWidth(getContext());
            animStartHeight = DisplayUtils.getScreenHeight(getContext()) * aspectRatio;
            pivotX = targetViewStatX / (1 - aspectRatio);
            pivotY = (targetViewStartY - (animStartHeight - targetViewHeight) / 2) / (1 - aspectRatio);
        } else {
            aspectRatio = targetViewHeight * 1.0f / DisplayUtils.getScreenHeight(getContext());
            animStartWidth = DisplayUtils.getScreenWidth(getContext()) * aspectRatio;
            pivotX = (targetViewStatX - (animStartWidth - targetViewWidth) / 2) / (1 - aspectRatio);
            pivotY = targetViewStartY / (1 - aspectRatio);
        }

        float translationX = mMediaPreviewBinding.ivPhoto.getTranslationX() + (DisplayUtils.getScreenWidth(getContext()) / 2.f * (1 - mMediaPreviewBinding.ivPhoto.getScaleX()) - mMediaPreviewBinding.ivPhoto.getPivotX() * (1 - mMediaPreviewBinding.ivPhoto.getScaleX()));
        float translationY = mMediaPreviewBinding.ivPhoto.getTranslationY() + (DisplayUtils.getScreenHeight(getContext()) / 2.f * (1 - mMediaPreviewBinding.ivPhoto.getScaleY()) - mMediaPreviewBinding.ivPhoto.getPivotY() * (1 - mMediaPreviewBinding.ivPhoto.getScaleY()));

        ViewAnimator.animate(mMediaPreviewBinding.ivPhoto)
                .pivotX(pivotX)
                .pivotY(pivotY)
                .scaleX(mMediaPreviewBinding.ivPhoto.getScaleX(), aspectRatio)
                .scaleY(mMediaPreviewBinding.ivPhoto.getScaleY(), aspectRatio)
                .translationX(translationX, 0)
                .translationY(translationY, 0)
                .onStop(() -> {
                    finishActivity();
                    getActivity().overridePendingTransition(0, 0);
                })
                .interpolator(new AccelerateDecelerateInterpolator())
                .duration(ANIMATOR_DURATION)
                .custom((view, value) -> setColorDrawableAlpha(value), 1, 0)
                .start();
    }

    private void setColorDrawableAlpha(float alpha) {
        synchronized (this) {
            if (mColorDrawable == null) {
                return;
            }
            if (alpha >= 1) {
                alpha = 1f;
            }
            if (alpha <= 0) {
                alpha = 0.0f;
            }
            mColorDrawable.setAlpha((int) (255 * alpha));
        }
    }

    /**
     * 获取当前条目
     *
     * @param position
     * @return
     */
    private MediaPreviewItemEntity getCurrentItem(final int position) {
        if (ListUtils.getSize(mMediaPreviewItemEntities) > position) {
            return ListUtils.getItem(mMediaPreviewItemEntities, position);
        }
        return null;
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
        onExitAnim();
        return true;
    }

    //endregion


}