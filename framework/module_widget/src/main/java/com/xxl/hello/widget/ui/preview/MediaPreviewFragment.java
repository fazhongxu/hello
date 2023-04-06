package com.xxl.hello.widget.ui.preview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.xxl.core.image.loader.ImageLoader;
import com.xxl.core.ui.fragment.BaseViewModelFragment;
import com.xxl.hello.service.data.model.entity.media.MediaPreviewItemEntity;
import com.xxl.hello.widget.BR;
import com.xxl.hello.widget.R;
import com.xxl.hello.widget.data.router.WidgetRouterApi;
import com.xxl.hello.widget.databinding.WidgetFragmentMediaPreviewBinding;
import com.xxl.kit.DisplayUtils;
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

        final MediaPreviewItemEntity mediaPreviewItemEntity = ListUtils.getFirst(mMediaPreviewItemEntities);
        if (mediaPreviewItemEntity != null) {
            ImageLoader.with(this)
                    .load(mediaPreviewItemEntity.getMediaUrl())
                    .into(mMediaPreviewBinding.ivPhoto);
        }

        openAnim(getActivity(),mMediaPreviewBinding.ivPhoto,mediaPreviewItemEntity);
    }

    //endregion

    //region: 页面视图渲染

    private void openAnim(@NonNull final Activity activity,
                          @NonNull final ImageView targetImageView,
                          @NonNull final MediaPreviewItemEntity mediaPreviewItemEntity) {
        if (mediaPreviewItemEntity == null) {
            return;
        }
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(mediaPreviewItemEntity.getPreviewWidth(), mediaPreviewItemEntity.getPreviewHeight());
        layoutParams.leftMargin = mediaPreviewItemEntity.getStartX();
        layoutParams.topMargin = mediaPreviewItemEntity.getStartY();
        targetImageView.setLayoutParams(layoutParams);

        ValueAnimator animator = ValueAnimator.ofFloat(1F, 0);
        animator.setDuration(1400);
        animator.setInterpolator(new AccelerateInterpolator());

        final int screenWidth = DisplayUtils.getScreenWidth(getActivity());
        final int screenHeight = DisplayUtils.getScreenHeight(getActivity());
        animator.addUpdateListener(animation -> {
            if (isActivityFinishing()){
                return;
            }
             float value = (float) animation.getAnimatedValue();
            final ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) targetImageView.getLayoutParams();
            params.leftMargin = (int) (mediaPreviewItemEntity.getStartX() * value);
            params.topMargin = (int) (mediaPreviewItemEntity.getStartY() * value);
            params.width = (int) (mediaPreviewItemEntity.getPreviewWidth() + ((screenWidth - mediaPreviewItemEntity.getPreviewWidth()) * (1F - value)));
            params.height = (int) (mediaPreviewItemEntity.getPreviewHeight() + ((screenHeight - mediaPreviewItemEntity.getPreviewHeight()) * (1F - value)));
            targetImageView.setLayoutParams(params);
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {

            }
        });
        animator.start();
    }

    private void closeAnim(@NonNull final Activity activity,
                          @NonNull final ImageView targetImageView,
                          @NonNull final MediaPreviewItemEntity mediaPreviewItemEntity) {
        if (mediaPreviewItemEntity == null) {
            return;
        }

        ValueAnimator animator = ValueAnimator.ofFloat(0, 1F);
        animator.setDuration(1400);
        animator.setInterpolator(new AccelerateInterpolator());

        final int screenWidth = DisplayUtils.getScreenWidth(activity);
        final int screenHeight = DisplayUtils.getScreenHeight(activity);
        animator.addUpdateListener(animation -> {
            if (isActivityFinishing()) {
                return;
            }
            float value = (float) animation.getAnimatedValue();
            final ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) targetImageView.getLayoutParams();
            params.leftMargin = (int) (mediaPreviewItemEntity.getStartX() * value);
            params.topMargin = (int) (mediaPreviewItemEntity.getStartY() * value);
            params.width = (int) (screenWidth - ((screenWidth - mediaPreviewItemEntity.getPreviewWidth()) * value));
            params.height = (int) (screenHeight - ((screenHeight - mediaPreviewItemEntity.getPreviewHeight()) * value));
            targetImageView.setLayoutParams(params);
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (activity.isFinishing()) {
                    return;
                }
                activity.finish();
//                activity.overridePendingTransition(R.anim.serviceui_image_scan_in_0, R.anim.serviceui_image_scan_out_0)
            }
        });
        animator.start();

//        var screenWidth= ScreenUtils.getScreenWidth(activity)
//        var screenHeight= ScreenUtils.getScreenHeight(activity)
//        animator.addUpdateListener { valueAnimator: ValueAnimator ->
//                val value = valueAnimator.animatedValue as Float
//            var p : CoordinatorLayout.LayoutParams = imageView.layoutParams as CoordinatorLayout.LayoutParams
//            p.leftMargin= ((bean.startX)*value).toInt()
//            p.topMargin=((bean.startY)*value).toInt()
//            p.width=screenWidth- (((screenWidth-bean.width))*value).toInt()
//            p.height=screenHeight-(((screenHeight-bean.height))*value).toInt()
//
//            imageView.layoutParams=p
//        }
//        animator.start()
//        animator.addListener(object : Animator.AnimatorListener {
//            override fun onAnimationStart(animation: Animator) {}
//            override fun onAnimationEnd(animation: Animator) {
//                activity.finish()
//                //发现如果先关闭activity,图片缩放可能受到影响,导致中间状态消失,所以这里结束的时候执行一个非常短的动画
//                activity.overridePendingTransition(R.anim.serviceui_image_scan_in_0, R.anim.serviceui_image_scan_out_0)
//            }
//            override fun onAnimationCancel(animation: Animator) {}
//            override fun onAnimationRepeat(animation: Animator) {}
//        })
//    }
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
        if (isActivityFinishing()) {
            return false;
        }
        // TODO: 2023/4/6
        closeAnim(getActivity(),mMediaPreviewBinding.ivPhoto,ListUtils.getFirst(mMediaPreviewItemEntities));

        return true;
    }

    //endregion

}