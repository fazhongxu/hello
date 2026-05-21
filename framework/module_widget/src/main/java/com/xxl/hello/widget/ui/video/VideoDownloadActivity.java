package com.xxl.hello.widget.ui.video;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xxl.core.ui.activity.SingleFragmentBarActivity;
import com.xxl.hello.widget.R;
import com.xxl.hello.widget.data.router.WidgetRouterApi.VideoDownload;

/**
 * 视频下载页面
 *
 * @author xxl.
 * @date 2026/05/21.
 */
@Route(path = VideoDownload.PATH)
public class VideoDownloadActivity extends SingleFragmentBarActivity<VideoDownloadFragment> {

    //region: 页面生命周期

    @Override
    public VideoDownloadFragment createFragment() {
        return VideoDownloadFragment.newInstance(getExtras());
    }

    @Override
    protected int getToolbarTitle() {
        return R.string.resources_video_download_title;
    }

    //endregion

}
