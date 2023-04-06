package com.xxl.hello.widget.ui.preview;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xxl.core.data.router.SystemRouterApi.MediaPreview;
import com.xxl.core.ui.activity.SingleFragmentActivity;

/**
 * 多媒体预览页面
 *
 * @author xxl.
 * @date 2023/04/06.
 */
@Route(path = MediaPreview.PATH)
public class MediaPreviewActivity extends SingleFragmentActivity<MediaPreviewFragment> {

    //region: 页面生命周期

    /**
     * 创建Fragment
     *
     * @return
     */
    @Override
    public MediaPreviewFragment createFragment() {
        return MediaPreviewFragment.newInstance(getExtras());
    }

    @Override
    public void onBackPressed() {
        final MediaPreviewFragment fragment = getCurrentFragment();
        if (fragment != null) {
            if (fragment.onBackPressed()) {
                return;
            }
        }
        super.onBackPressed();
    }

    //endregion

}