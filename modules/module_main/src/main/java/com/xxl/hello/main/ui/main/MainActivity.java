package com.xxl.hello.main.ui.main;

import android.content.Intent;
import android.view.KeyEvent;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xxl.core.ui.activity.SingleFragmentBarActivity;
import com.xxl.hello.main.R;
import com.xxl.hello.router.api.MainRouterApi;
import com.xxl.hello.router.api.UserRouterApi;
import com.xxl.kit.AppRouterApi;

/**
 * @author xxl
 * @date 2021/07/16.
 */
@Route(path = MainRouterApi.Main.PATH)
public class MainActivity extends SingleFragmentBarActivity<MainFragment> {

    //region: 页面生命周期

    /**
     * 创建Fragment
     *
     * @return
     */
    @Override
    public MainFragment createFragment() {
        return MainFragment.newInstance(getExtras());
    }

    @Override
    protected int getToolbarTitle() {
        return 0;
    }

    @Override
    public boolean isDisplayLeft() {
        return false;
    }

    @Override
    protected boolean canDragBack() {
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        final MainFragment fragment = getCurrentFragment();
        fragment.handleActivityResult(requestCode,data);
    }

    //endregion
}


