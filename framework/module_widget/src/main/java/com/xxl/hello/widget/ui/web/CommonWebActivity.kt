package com.xxl.hello.widget.ui.web

import com.alibaba.android.arouter.facade.annotation.Route
import com.xxl.core.data.router.SystemRouterApi
import com.xxl.core.ui.activity.SingleFragmentBarActivity

/**
 * 通用的WebView页面
 *
 * @author xxl.
 * @date 2025/7/7.
 */
@Route(path = SystemRouterApi.WebView.COMMON_WEB_PATH)
class CommonWebActivity : SingleFragmentBarActivity<CommonWebFragment>() {

    //region: 页面生命周期

    override fun createFragment(): CommonWebFragment {
        return CommonWebFragment.newInstance(extras)
    }

    override fun getToolbarTitle(): Int {
        return 0
    }

    override fun onBackPressed() {
        if (currentFragment != null) {
            if (currentFragment.onBackPressed()) {
                return
            }
        }
        super.onBackPressed()
    }

    //endregion
}