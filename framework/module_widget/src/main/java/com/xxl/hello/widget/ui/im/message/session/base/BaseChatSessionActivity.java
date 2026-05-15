package com.xxl.hello.widget.ui.im.message.session.base;

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

}