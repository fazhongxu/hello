package com.xxl.hello.widget.ui.browser;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xxl.core.ui.activity.SingleFragmentBarActivity;
import com.xxl.hello.widget.R;
import com.xxl.hello.widget.data.router.WidgetRouterApi.FileBrowser;

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

    //endregion

}