package com.xxl.hello.widget.ui.imageedit;

import android.view.View;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xxl.core.ui.activity.SingleFragmentBarActivity;
import com.xxl.core.widget.toolbar.OnToolbarProvider;
import com.xxl.hello.widget.R;
import com.xxl.hello.widget.data.router.WidgetRouterApi.ImageEdit;

/**
 * 图片编辑页面
 *
 * @author xxl
 * @date 2026/06/15
 */
@Route(path = ImageEdit.PATH)
public class ImageEditActivity extends SingleFragmentBarActivity<ImageEditFragment> implements OnToolbarProvider {

    //region: 页面生命周期

    @Override
    public ImageEditFragment createFragment() {
        return ImageEditFragment.newInstance(getExtras());
    }

    @Override
    protected int getToolbarTitle() {
        return R.string.resources_image_edit_title;
    }

    @Override
    public boolean isDisplayRightIcon() {
        return true;
    }

    @Override
    public int getRightIcon() {
        return R.drawable.resources_ic_more_white;
    }

    @Override
    public void onToolbarRightClick(@NonNull View view) {
        ImageEditFragment fragment = getCurrentFragment();
        if (fragment != null) {
            fragment.onToolbarRightClick();
        }
    }

    //endregion

}
