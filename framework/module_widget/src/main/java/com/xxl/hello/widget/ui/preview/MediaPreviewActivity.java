package com.xxl.hello.widget.ui.preview;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xxl.core.ui.activity.SingleFragmentActivity;
import com.xxl.hello.widget.R;
import com.xxl.hello.widget.data.router.WidgetRouterApi.MediaPreview;
import com.xxl.hello.widget.ui.view.DragDismissLayout;

/**
 * 多媒体预览页面
 *
 * @author xxl.
 * @date 2023/04/06.
 */
@Route(path = MediaPreview.PATH)
public class MediaPreviewActivity extends SingleFragmentActivity<MediaPreviewFragment> implements OnMediaPreViewListener {

    //region: 页面生命周期

    @Override
    protected int getLayoutRes() {
        return R.layout.core_layout_transparent;
    }

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

    //region: OnDragDismissLayoutListener

    @Override
    public void onComputeAlpha(int alpha) {
        MediaPreviewFragment fragment = getCurrentFragment();
        if (fragment != null) {
            fragment.onComputeAlpha(alpha);
        }
    }

    @Override
    public void onDismiss() {
        MediaPreviewFragment fragment = getCurrentFragment();
        if (fragment != null) {
            fragment.onDismiss();
        }
    }

    @Override
    public void onDragging() {
        MediaPreviewFragment fragment = getCurrentFragment();
        if (fragment != null) {
            fragment.onDragging();
        }
    }

    @Override
    public void onEndDrag() {
        MediaPreviewFragment fragment = getCurrentFragment();
        if (fragment != null) {
            fragment.onEndDrag();
        }
    }

    @Override
    public void onCloseClick() {
        onBackPressed();
    }

    //endregion

}