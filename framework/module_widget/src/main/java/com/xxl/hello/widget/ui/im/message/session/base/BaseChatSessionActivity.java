package com.xxl.hello.widget.ui.im.message.session.base;

import android.content.Intent;
import android.view.View;

import androidx.annotation.Nullable;

import com.xxl.core.ui.activity.SingleFragmentBarActivity;

/**
 * @author xxl.
 * @date 2026/5/15.
 */
public abstract class BaseChatSessionActivity<F extends BaseChatSessionFragment> extends SingleFragmentBarActivity<F> {

    @Override
    public void onBackPressed() {
        F fragment = getCurrentFragment();
        if (fragment.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        F fragment = getCurrentFragment();
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onToolbarLeftClick(View view) {
        F fragment = getCurrentFragment();
        if (fragment != null && fragment.onBackPressed()) {
            return;
        }
        super.onToolbarLeftClick(view);
    }

}