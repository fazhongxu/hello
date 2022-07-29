package com.xxl.hello.main.ui.main;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xxl.hello.main.R;
import com.xxl.hello.service.ui.SingleFragmentBarActivity;
import com.xxl.kit.AppRouterApi;

/**
 * @author xxl
 * @date 2021/07/16.
 */
@Route(path = AppRouterApi.MAIN_PATH)
public class MainActivity extends SingleFragmentBarActivity<MainFragment> {

    //region: 页面生命周期

    /**
     * 创建Fragment
     *
     * @return
     */
    @Override
    public MainFragment createFragment() {
        return MainFragment.newInstance();
    }

    @Override
    protected int getToolbarTitle() {
        return R.string.main_title;
    }

    //endregion
}


