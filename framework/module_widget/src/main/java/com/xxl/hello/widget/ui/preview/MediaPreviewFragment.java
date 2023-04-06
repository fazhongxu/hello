package com.xxl.hello.widget.ui.preview;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.xxl.core.data.router.SystemRouterApi;
import com.xxl.core.ui.fragment.BaseViewModelFragment;
import com.xxl.hello.widget.BR;
import com.xxl.hello.widget.R;
import com.xxl.hello.widget.databinding.WidgetFragmentMediaPreviewBinding;

/**
 * 多媒体预览页面
 *
 * @author xxl.
 * @date 2023/04/06.
 */
public class MediaPreviewFragment extends BaseViewModelFragment<MediaPreviewModel, WidgetFragmentMediaPreviewBinding>
        implements MediaPreviewNavigator{

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
    @Autowired(name = SystemRouterApi.WebView.PARAMS_KEY_SHARE_ENABLE)
    boolean mShareEnable = true;

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

    }

    @Override
    public void setupLayout(@NonNull View view) {
        mMediaPreviewBinding = getViewDataBinding();
    }

    //endregion

    //region: 页面视图渲染


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
        return false;
    }

    //endregion

}