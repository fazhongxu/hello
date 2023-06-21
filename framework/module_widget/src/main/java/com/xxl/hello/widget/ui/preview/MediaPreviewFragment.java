package com.xxl.hello.widget.ui.preview;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.github.florent37.viewanimator.ViewAnimator;
import com.xxl.core.ui.CommonFragmentAdapter;
import com.xxl.core.ui.fragment.BaseViewModelFragment;
import com.xxl.hello.service.data.model.entity.media.MediaPreviewItemEntity;
import com.xxl.hello.widget.BR;
import com.xxl.hello.widget.R;
import com.xxl.hello.widget.data.router.WidgetRouterApi;
import com.xxl.hello.widget.databinding.WidgetFragmentMediaPreviewBinding;
import com.xxl.hello.widget.ui.preview.item.MediaPreviewItemFragment;
import com.xxl.kit.DisplayUtils;
import com.xxl.kit.ImageUtils;
import com.xxl.kit.ListUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 多媒体预览页面
 *
 * @author xxl.
 * @date 2023/04/06.
 */
public class MediaPreviewFragment extends BaseViewModelFragment<MediaPreviewViewModel, WidgetFragmentMediaPreviewBinding>
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
    private MediaPreviewViewModel mMediaPreviewViewModel;

    /**
     * adapter
     */
    private CommonFragmentAdapter mFragmentAdapter;

    /**
     * handler
     */
    private Handler mHandler = new Handler();

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
    protected MediaPreviewViewModel createViewModel() {
        mMediaPreviewViewModel = createViewModel(MediaPreviewViewModel.class);
        mMediaPreviewViewModel.setNavigator(this);
        return mMediaPreviewViewModel;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
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
        setupViewPager();
        onEnterAnimator();
    }

    private void setupViewPager() {
        ViewPager2 viewpager = mMediaPreviewBinding.viewpager;
        final List<Fragment> fragments = new ArrayList<>();
        for (MediaPreviewItemEntity mediaPreviewItemEntity : mMediaPreviewItemEntities) {
            MediaPreviewItemFragment mediaPreviewItemFragment = MediaPreviewItemFragment.newInstance(mediaPreviewItemEntity, true);
            fragments.add(mediaPreviewItemFragment);
        }
        mFragmentAdapter = new CommonFragmentAdapter(getActivity(), fragments);
        viewpager.setAdapter(mFragmentAdapter);
        viewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mCurrentPosition = position;
            }
        });

        if (mCurrentPosition < mFragmentAdapter.getItemCount()) {
            viewpager.setCurrentItem(mCurrentPosition, false);
        }
    }

    /**
     * 进入动画效果
     */
    private void onEnterAnimator() {
        synchronized (this) {
            MediaPreviewItemEntity mediaPreviewItemEntity = getCurrentItem(mCurrentPosition);
            if (mediaPreviewItemEntity == null || mediaPreviewItemEntity.getPreviewWidth() < 0 || mediaPreviewItemEntity.getPreviewHeight() < 0) {
                ViewAnimator.animate(mMediaPreviewBinding.viewpager)
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
                if (aspectRatio == 1) {
                    aspectRatio = 0.999F;
                }
                animStartHeight = DisplayUtils.getScreenHeight(getActivity()) * aspectRatio;
                pivotX = targetViewX / (1 - aspectRatio);
                pivotY = (targetViewY - (animStartHeight - targetViewHeight) / 2) / (1 - aspectRatio);
            } else {
                aspectRatio = targetViewHeight * 1.0f / DisplayUtils.getScreenHeight(getContext());
                if (aspectRatio == 1) {
                    aspectRatio = 0.999F;
                }
                animStartWidth = DisplayUtils.getScreenWidth(getContext()) * aspectRatio;
                pivotX = (targetViewX - (animStartWidth - targetViewWidth) / 2) / (1 - aspectRatio);
                pivotY = targetViewY / (1 - aspectRatio);
            }

            ViewAnimator.animate(mMediaPreviewBinding.viewpager)
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
            if (aspectRatio == 1) {
                aspectRatio = 0.999F;
            }
            animStartHeight = DisplayUtils.getScreenHeight(getContext()) * aspectRatio;
            pivotX = targetViewStatX / (1 - aspectRatio);
            pivotY = (targetViewStartY - (animStartHeight - targetViewHeight) / 2) / (1 - aspectRatio);
        } else {
            aspectRatio = targetViewHeight * 1.0f / DisplayUtils.getScreenHeight(getContext());
            if (aspectRatio == 1) {
                aspectRatio = 0.999F;
            }
            animStartWidth = DisplayUtils.getScreenWidth(getContext()) * aspectRatio;
            pivotX = (targetViewStatX - (animStartWidth - targetViewWidth) / 2) / (1 - aspectRatio);
            pivotY = targetViewStartY / (1 - aspectRatio);
        }

        float translationX = mMediaPreviewBinding.viewpager.getTranslationX() + (DisplayUtils.getScreenWidth(getContext()) / 2.f * (1 - mMediaPreviewBinding.viewpager.getScaleX()) - mMediaPreviewBinding.viewpager.getPivotX() * (1 - mMediaPreviewBinding.viewpager.getScaleX()));
        float translationY = mMediaPreviewBinding.viewpager.getTranslationY() + (DisplayUtils.getScreenHeight(getContext()) / 2.f * (1 - mMediaPreviewBinding.viewpager.getScaleY()) - mMediaPreviewBinding.viewpager.getPivotY() * (1 - mMediaPreviewBinding.viewpager.getScaleY()));

        mHandler.postDelayed(() -> {
            if (isActivityFinishing()) {
                return;
            }
            final Fragment fragment = mFragmentAdapter.getFragment(mCurrentPosition);
            if (fragment instanceof MediaPreviewItemFragment) {
                ((MediaPreviewItemFragment) fragment).setDismissPhotoViewState(targetAspectRatio);
            }
        }, 50);

        ViewAnimator.animate(mMediaPreviewBinding.viewpager)
                .pivotX(pivotX)
                .pivotY(pivotY)
                .scaleX(mMediaPreviewBinding.viewpager.getScaleX(), aspectRatio)
                .scaleY(mMediaPreviewBinding.viewpager.getScaleY(), aspectRatio)
                .translationX(translationX, 0)
                .translationY(translationY, 0)
                .onStop(() -> {
                    if (isActivityFinishing()) {
                        return;
                    }
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

    public void onComputeAlpha(int alpha) {
        if (isActivityFinishing()) {
            return;
        }
        mColorDrawable.setAlpha(alpha);
    }

    public void onDismiss() {
        if (isActivityFinishing()) {
            return;
        }
    }

    public void onDragging() {
        if (isActivityFinishing()) {
            return;
        }
        mMediaPreviewBinding.viewpager.setUserInputEnabled(false);
    }

    public void onEndDrag() {
        if (isActivityFinishing()) {
            return;
        }
        mMediaPreviewBinding.viewpager.setUserInputEnabled(true);
    }

    //endregion


}