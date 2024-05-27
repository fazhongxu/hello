package com.xxl.hello.widget.ui.browser;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xxl.core.ui.activity.SingleFragmentBarActivity;
import com.xxl.hello.widget.R;
import com.xxl.hello.widget.data.router.WidgetRouterApi.FileBrowser;
import com.xxl.kit.ToastUtils;

/**
 * 文件浏览页面
 *
 * @author xxl.
 * @date 2023/06/21.
 */
@Route(path = FileBrowser.PATH)
public class FileBrowserActivity extends SingleFragmentBarActivity<FileBrowserFragment> {

    //region: 页面生命周期

    /**
     * 创建Fragment
     *
     * @return
     */
    @Override
    public FileBrowserFragment createFragment() {
        return FileBrowserFragment.newInstance(getExtras());
    }

    @Override
    protected int getToolbarTitle() {
        return R.string.resources_file_text;
    }

    @Override
    public boolean isDisplayRightCustom() {
        return true;
    }

    @Override
    public View getRightCustomLayout() {
        View targetView = LayoutInflater.from(this).inflate(R.layout.widget_layout_file_browser_custom_right, null);
        ImageView ivRightIcon = targetView.findViewById(R.id.iv_right_icon);
        TextView tvRightText = targetView.findViewById(R.id.tv_right_text);
        ivRightIcon.setImageResource(R.drawable.kit_ic_check_white_24dp);
        tvRightText.setText(getString(R.string.resources_share_text));
        tvRightText.setOnClickListener(v -> {
            ToastUtils.success("点击了自定义视图的分享").show();
        });
        return targetView;
    }

    //endregion

}