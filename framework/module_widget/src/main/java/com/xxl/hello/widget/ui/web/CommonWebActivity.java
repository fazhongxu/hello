package com.xxl.hello.widget.ui.web;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xxl.core.data.router.SystemRouterApi;
import com.xxl.core.ui.activity.SingleFragmentBarActivity;

/**
 * 通用的WebView页面
 *
 * @author xxl.
 * @date 2022/6/25.
 */
@Route(path = SystemRouterApi.WebView.COMMON_WEB_PATH)
public class CommonWebActivity extends SingleFragmentBarActivity<CommonWebFragment> {

    //region: 页面生命周期

    /**
     * 创建Fragment
     *
     * @return
     */
    @Override
    public CommonWebFragment createFragment() {
        return CommonWebFragment.newInstance(getExtras());
    }

    /**
     * 获取toolbar标题
     *
     * @return
     */
    @Override
    protected int getToolbarTitle() {
        return 0;
    }

    //endregion

}