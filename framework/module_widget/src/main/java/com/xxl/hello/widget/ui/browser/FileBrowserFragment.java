package com.xxl.hello.widget.ui.browser;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.tencent.smtt.sdk.TbsReaderView;
import com.xxl.core.ui.fragment.BaseViewModelFragment;
import com.xxl.hello.widget.BR;
import com.xxl.hello.widget.R;
import com.xxl.hello.widget.data.router.WidgetRouterApi;
import com.xxl.hello.widget.databinding.WidgetFragmentFileBrowserBinding;
import com.xxl.kit.FileUtils;
import com.xxl.kit.LogUtils;

import java.io.File;

/**
 * 文件浏览页面
 *
 * @author xxl.
 * @date 2023/07/21.
 */
public class FileBrowserFragment extends BaseViewModelFragment<FileBrowserViewModel, WidgetFragmentFileBrowserBinding>
        implements FileBrowserNavigator {

    //region: 成员变量

    /**
     * 文件预览页面视图
     */
    private WidgetFragmentFileBrowserBinding mMediaPreviewBinding;

    /**
     * 文件预览页面视图模型
     */
    private FileBrowserViewModel mFileBrowserViewModel;

    /**
     * Tbs显示视图
     */
    private TbsReaderView mTbsReaderView;

    /**
     * 文件路径
     */
    @Autowired(name = WidgetRouterApi.FileBrowser.PARAMS_KEY_FILE_PATH)
    String mFilePath;

    /**
     * 是否可以分享
     */
    @Autowired(name = WidgetRouterApi.FileBrowser.PARAMS_KEY_SHARE_ENABLE)
    boolean mShareEnable = true;

    //endregion

    //region: 构造函数

    public static FileBrowserFragment newInstance(@NonNull final Bundle args) {
        final FileBrowserFragment fragment = new FileBrowserFragment();
        fragment.setArguments(args);
        return fragment;
    }

    //endregion

    //region: 页面生命周期

    @Override
    protected int getLayoutRes() {
        return R.layout.widget_fragment_file_browser;
    }

    @Override
    protected FileBrowserViewModel createViewModel() {
        mFileBrowserViewModel = createViewModel(FileBrowserViewModel.class);
        mFileBrowserViewModel.setNavigator(this);
        return mFileBrowserViewModel;
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
        if (!FileUtils.isFileExists(mFilePath)) {
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
        mMediaPreviewBinding.pdfView.fromFile(new File(mFilePath))
                .pageFitPolicy()
                .onLoad(pagesCount -> {
                    if (isActivityFinishing()) {
                        return;
                    }
                    LogUtils.d("loadComplete " + pagesCount);
                })
                .load();
    }

    //endregion

    //region: MediaPreviewNavigator

    //endregion

    //region: Activity 操作

    //endregion

    //region: Fragment 操作

    //endregion


}