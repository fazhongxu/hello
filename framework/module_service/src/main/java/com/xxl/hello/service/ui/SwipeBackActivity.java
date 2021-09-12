package com.xxl.hello.service.ui;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.Utils;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityBase;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper;

/**
 * @Description 支持侧滑页面
 * @Author: xxl
 * @Date: 2021/9/12 11:17 PM
 **/
public class SwipeBackActivity extends AppCompatActivity implements SwipeBackActivityBase {

    //region: 成员变量

    /**
     * 页面策划辅助类
     */
    private SwipeBackActivityHelper mHelper;

    //endregion

    //region: 页面生命周期

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHelper = new SwipeBackActivityHelper(this);
        mHelper.onActivityCreate();
        setSwipeBackEnable(swipeBackEnable());
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    /**
     * 是否启动侧滑
     *
     * @return
     */
    protected boolean swipeBackEnable() {
        return true;
    }

    //endregion

    //region: SwipeBackActivityBase

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }

    //endregion
}
