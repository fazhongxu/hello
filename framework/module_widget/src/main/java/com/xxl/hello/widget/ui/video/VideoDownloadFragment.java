package com.xxl.hello.widget.ui.video;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.xxl.core.ui.fragment.BaseViewModelFragment;
import com.xxl.hello.widget.BR;
import com.xxl.hello.widget.R;
import com.xxl.hello.widget.data.router.WidgetRouterApi;
import com.xxl.hello.widget.databinding.WidgetFragmentVideoDownloadBinding;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * 视频下载页面
 *
 * @author xxl.
 * @date 2026/05/21.
 */
@AndroidEntryPoint
public class VideoDownloadFragment extends BaseViewModelFragment<VideoDownloadViewModel, WidgetFragmentVideoDownloadBinding>
        implements VideoDownloadNavigator {

    //region: 成员变量

    private WidgetFragmentVideoDownloadBinding mBinding;

    private VideoDownloadViewModel mViewModel;

    @Autowired(name = WidgetRouterApi.VideoDownload.PARAMS_KEY_VIDEO_URL)
    String mVideoUrl;

    @Autowired(name = WidgetRouterApi.VideoDownload.PARAMS_KEY_VIDEO_TITLE)
    String mVideoTitle;

    //endregion

    //region: 构造函数

    public static VideoDownloadFragment newInstance(@NonNull final Bundle args) {
        final VideoDownloadFragment fragment = new VideoDownloadFragment();
        fragment.setArguments(args);
        return fragment;
    }

    //endregion

    //region: 页面生命周期

    @Override
    protected int getLayoutRes() {
        return R.layout.widget_fragment_video_download;
    }

    @Override
    protected VideoDownloadViewModel createViewModel() {
        mViewModel = createViewModel(VideoDownloadViewModel.class);
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
        mViewModel.setVideoUrl(mVideoUrl);
        mViewModel.videoTitle.set(mVideoTitle);
    }

    @Override
    public void setupLayout(@NonNull View view) {
        mBinding = getViewDataBinding();
        setupLayoutView();
    }

    //endregion

    //region: 页面视图渲染

    private void setupLayoutView() {
        mBinding.btnDownload.setOnClickListener(v -> {
            mViewModel.startDownload();
        });
    }

    //endregion

    //region: VideoDownloadNavigator

    @Override
    public void onDownloadProgress(int progress) {
        if (mBinding != null) {
            mBinding.progressBar.setProgress(progress);
        }
    }

    @Override
    public void onDownloadComplete(String filePath) {
        Toast.makeText(getContext(), "视频已保存到: " + filePath, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDownloadFail(String message) {
        Toast.makeText(getContext(), "下载失败: " + message, Toast.LENGTH_SHORT).show();
    }

    //endregion

    //region: Activity 操作

    //endregion

    //region: Fragment 操作

    //endregion

}
