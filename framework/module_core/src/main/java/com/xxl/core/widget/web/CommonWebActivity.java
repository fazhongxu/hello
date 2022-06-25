package com.xxl.core.widget.web;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.xxl.core.data.router.SystemRouterApi;

/**
 * 通用的WebView页面
 *
 * @author xxl.
 * @date 2022/6/25.
 */
@Route(path = SystemRouterApi.WebView.COMMON_WEB_PATH)
public class CommonWebActivity extends BaseWebActivity {

    //region: 成员变量

    /**
     * url
     */
    @Autowired(name = SystemRouterApi.WebView.PARAMS_KEY_URL)
    String mUrl;

    /**
     * 是否可以分享
     */
    @Autowired(name = SystemRouterApi.WebView.PARAMS_KEY_SHARE_ENABLE)
    boolean mShareEnable;

    //endregion

    //region: 页面生命周期

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected String getUrl() {
        return mUrl;
    }

    //endregion

}