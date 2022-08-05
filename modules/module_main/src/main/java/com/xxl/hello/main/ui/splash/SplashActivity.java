package com.xxl.hello.main.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xxl.hello.main.R;
import com.xxl.core.ui.activity.SingleFragmentActivity;
import com.xxl.kit.AppRouterApi;

/**
 * 启动页面
 *
 * @author xxl.
 * @date 2021/8/13.
 */
@Route(path = AppRouterApi.SPLASH_PATH)
public class SplashActivity extends SingleFragmentActivity<SplashFragment> {

    //region: 页面生命周期

    /**
     * 创建Fragment
     *
     * @return
     */
    @Override
    public SplashFragment createFragment() {
        return SplashFragment.newInstance();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 判断当前activity是不是所在任务栈的根
        Log.e("aaa", "onCreate: "+isTaskRoot() );
        if (!this.isTaskRoot()) {
            final Intent intent = getIntent();
            if (intent != null) {
                String action = intent.getAction();
                if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN.equals(action)) {
                    finish();
                }
            }
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.core_layout_transparent;
    }

    @Override
    protected boolean canDragBack() {
        return false;
    }

    //endregion
}